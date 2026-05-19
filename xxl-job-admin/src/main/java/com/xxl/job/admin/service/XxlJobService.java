package com.xxl.job.admin.service;

import com.xxl.job.admin.model.XxlJobInfo;
import com.xxl.sso.core.model.LoginInfo;
import com.xxl.tool.response.PageModel;
import com.xxl.tool.response.Response;

import java.util.Date;
import java.util.Map;

/**
 * core job action for xxl-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface XxlJobService {

    /**
     * page list
     */
    Response<PageModel<XxlJobInfo>> pageList(int offset, int pagesize, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * add job
     */
    Response<String> add(XxlJobInfo jobInfo, LoginInfo loginInfo);

    /**
     * update job
     */
    Response<String> update(XxlJobInfo jobInfo, LoginInfo loginInfo);

    /**
     * remove job
     */
    Response<String> remove(int id, LoginInfo loginInfo);

    /**
     * start job
     */
    Response<String> start(int id, LoginInfo loginInfo);

    /**
     * stop job
     */
    Response<String> stop(int id, LoginInfo loginInfo);

    /**
     * trigger
     */
    Response<String> trigger(LoginInfo loginInfo, int jobId, String executorParam, String addressList);

    /**
     * dashboard info
     */
    Map<String, Object> dashboardInfo();

    /**
     * chart info
     */
    Response<Map<String, Object>> chartInfo(Date startDate, Date endDate);

    /**
     * get jobId
     * @param jobGroup 执行器主键ID
     * @param jobDesc 任务描述
     */
    Integer getJobId(int jobGroup, String jobDesc);

}
