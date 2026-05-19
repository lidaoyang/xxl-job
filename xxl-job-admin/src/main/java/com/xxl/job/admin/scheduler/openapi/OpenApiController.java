package com.xxl.job.admin.scheduler.openapi;

import com.xxl.job.admin.scheduler.config.XxlJobAdminBootstrap;
import com.xxl.job.core.constant.Const;
import com.xxl.job.core.openapi.AdminBiz;
import com.xxl.job.core.openapi.model.CallbackRequest;
import com.xxl.job.core.openapi.model.JobInfoRequest;
import com.xxl.job.core.openapi.model.RegistryRequest;
import com.xxl.sso.core.annotation.XxlSso;
import com.xxl.tool.core.StringTool;
import com.xxl.tool.json.GsonTool;
import com.xxl.tool.response.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxueli on 17/5/10.
 */
@Controller
public class OpenApiController {

    @Resource
    private AdminBiz adminBiz;

    /**
     * api
     */
    @RequestMapping("/api/{uri}")
    @ResponseBody
    @XxlSso(login = false)
    public Object api(HttpServletRequest request,
                      @PathVariable("uri") String uri,
                      @RequestHeader(value = Const.XXL_JOB_ACCESS_TOKEN, required = false) String accesstoken,
                      @RequestBody(required = false) String requestBody) {

        // valid
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return Response.ofFail("invalid request, HttpMethod not support.");
        }
        if (StringTool.isBlank(uri)) {
            return Response.ofFail("invalid request, uri-mapping empty.");
        }
        if (StringTool.isBlank(requestBody)) {
            return Response.ofFail("invalid request, requestBody empty.");
        }

        // valid token
        if (StringTool.isNotBlank(XxlJobAdminBootstrap.getInstance().getAccessToken())
            && !XxlJobAdminBootstrap.getInstance().getAccessToken().equals(accesstoken)) {
            return Response.ofFail("The access token is wrong.");
        }

        // dispatch request
        try {
            return switch (uri) {
                case "callback" -> {
                    List<CallbackRequest> callbackParamList = GsonTool.fromJson(requestBody, List.class, CallbackRequest.class);
                    yield adminBiz.callback(callbackParamList);
                }
                case "registry" -> {
                    RegistryRequest registryParam = GsonTool.fromJson(requestBody, RegistryRequest.class);
                    yield adminBiz.registry(registryParam);
                }
                case "registryRemove" -> {
                    RegistryRequest registryParam = GsonTool.fromJson(requestBody, RegistryRequest.class);
                    yield adminBiz.registryRemove(registryParam);
                }
                case "addJob" -> {
                    JobInfoRequest jobInfoParam = GsonTool.fromJson(requestBody, JobInfoRequest.class);
                    yield adminBiz.addJob(jobInfoParam);
                }
                case "removeJob" -> {
                    Map<String, Object> jsonMap = GsonTool.fromJsonMap(requestBody, String.class, Object.class);
                    String jobName = jsonMap.get("jobName").toString();
                    int jobGroup = Integer.parseInt(jsonMap.get("jobGroup").toString());
                    yield adminBiz.removeJob(jobGroup, jobName);
                }
                case "startJob" -> {
                    Map<String, Object> jsonMap = GsonTool.fromJsonMap(requestBody, String.class, Object.class);
                    String jobName = jsonMap.get("jobName").toString();
                    int jobGroup = Integer.parseInt(jsonMap.get("jobGroup").toString());
                    yield adminBiz.startJob(jobGroup, jobName);
                }
                case "stopJob" -> {
                    Map<String, Object> jsonMap = GsonTool.fromJsonMap(requestBody, String.class, Object.class);
                    String jobName = jsonMap.get("jobName").toString();
                    int jobGroup = Integer.parseInt(jsonMap.get("jobGroup").toString());
                    yield adminBiz.stopJob(jobGroup, jobName);
                }
                default -> Response.ofFail("invalid request, uri-mapping(" + uri + ") not found.");
            };
        } catch (Exception e) {
            return Response.ofFail("openapi invoke error: " + e.getMessage());
        }

    }

}
