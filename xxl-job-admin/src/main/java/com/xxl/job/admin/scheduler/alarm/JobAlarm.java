package com.xxl.job.admin.scheduler.alarm;


import com.xxl.job.admin.model.XxlJobInfo;
import com.xxl.job.admin.model.XxlJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

    /**
     * 是否支持告警的类型
     *
     * @param info
     * @return
     */
    boolean accept(XxlJobInfo info);

}
