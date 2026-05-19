package com.xxl.job.core.openapi;

import com.xxl.job.core.openapi.model.CallbackRequest;
import com.xxl.job.core.openapi.model.JobInfoRequest;
import com.xxl.job.core.openapi.model.RegistryRequest;
import com.xxl.tool.response.Response;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminBiz {


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackRequestList
     * @return
     */
    public Response<String> callback(List<CallbackRequest> callbackRequestList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryRequest
     * @return
     */
    public Response<String> registry(RegistryRequest registryRequest);

    /**
     * registry remove
     *
     * @param registryRequest
     * @return
     */
    public Response<String> registryRemove(RegistryRequest registryRequest);


    // ---------------------- biz (custome) ----------------------
    // group、job ... manage

    /**
     * add job
     *
     * @param jobInfo 任务信息(约定:任务描述保证唯一,为了后面停止删除等操作方便查询)
     * @return
     */
    public Response<String> addJob(JobInfoRequest jobInfo);

    /**
     * remove job
     *
     * @param jobGroup 任务组
     * @param jobName 任务名称
     * @return
     */
    public Response<String> removeJob(int jobGroup, String jobName);

    /**
     * start job
     *
     * @param jobGroup 任务组
     * @param jobName 任务名称
     * @return
     */
    public Response<String> startJob(int jobGroup, String jobName);

    /**
     * stop job
     *
     * @param jobGroup 任务组
     * @param jobName 任务名称
     * @return
     */
    public Response<String> stopJob(int jobGroup, String jobName);
}
