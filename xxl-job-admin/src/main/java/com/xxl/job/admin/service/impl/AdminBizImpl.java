package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.constant.Consts;
import com.xxl.job.admin.constant.TriggerStatus;
import com.xxl.job.admin.model.XxlJobInfo;
import com.xxl.job.admin.scheduler.config.XxlJobAdminBootstrap;
import com.xxl.job.admin.scheduler.type.ScheduleTypeEnum;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.openapi.AdminBiz;
import com.xxl.job.core.openapi.model.CallbackRequest;
import com.xxl.job.core.openapi.model.JobInfoRequest;
import com.xxl.job.core.openapi.model.RegistryRequest;
import com.xxl.sso.core.model.LoginInfo;
import com.xxl.tool.response.Response;
import jakarta.annotation.Resource;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {

    @Resource
    private XxlJobService xxlJobService;

    @Override
    public Response<String> callback(List<CallbackRequest> callbackRequestList) {
        return XxlJobAdminBootstrap.getInstance().getJobCompleteHelper().callback(callbackRequestList);
    }

    @Override
    public Response<String> registry(RegistryRequest registryRequest) {
        return XxlJobAdminBootstrap.getInstance().getJobRegistryHelper().registry(registryRequest);
    }

    @Override
    public Response<String> registryRemove(RegistryRequest registryRequest) {
        return XxlJobAdminBootstrap.getInstance().getJobRegistryHelper().registryRemove(registryRequest);
    }

    @Override
    public Response<String> addJob(JobInfoRequest jobInfoRequest) {
        // 转换为XxlJobInfo
        XxlJobInfo jobInfo = getXxlJobInfo(jobInfoRequest);
        LoginInfo loginInfo = getLoginInfo();
        return xxlJobService.add(jobInfo, loginInfo);
    }

    private @NonNull LoginInfo getLoginInfo() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId("1");
        loginInfo.setUserName("system-admin");
        loginInfo.setRoleList(List.of(Consts.ADMIN_ROLE));
        return loginInfo;
    }

    private @NonNull XxlJobInfo getXxlJobInfo(JobInfoRequest jobInfoRequest) {
        XxlJobInfo jobInfo = new XxlJobInfo();
        BeanUtils.copyProperties(jobInfo, jobInfoRequest);
        return jobInfo;
    }

    @Override
    public Response<String> removeJob(int jobId) {
        if (jobId <= 0) {
            return Response.ofFail("任务ID不能为空");
        }
        return xxlJobService.remove(jobId, getLoginInfo());
    }

    @Override
    public Response<String> startJob(int jobId) {
        if (jobId <= 0) {
            return Response.ofFail("任务ID不能为空");
        }
        return xxlJobService.start(jobId, getLoginInfo());
    }

    @Override
    public Response<String> stopJob(int jobId) {
        if (jobId <= 0) {
            return Response.ofFail("任务ID不能为空");
        }

        return xxlJobService.stop(jobId, getLoginInfo());
    }
}
