package com.xxl.job.admin.scheduler.alarm.impl;

import com.xxl.job.admin.model.XxlJobInfo;
import com.xxl.job.admin.model.XxlJobLog;
import com.xxl.job.admin.scheduler.alarm.AlarmTypeEnum;
import com.xxl.job.admin.scheduler.alarm.JobAlarm;
import com.xxl.job.admin.scheduler.alarm.msg.BaseMsg;

/**
 * 抽象告警基类
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/10 18:22
 */
public abstract class AbstractJobAlarm extends BaseAlarm implements JobAlarm {

    /**
     * 告警类型枚举
     */
    protected abstract AlarmTypeEnum getAlarmType();

    /**
     * 创建消息请求对象
     */
    protected abstract BaseMsg createMsgRequest(String content);

    protected BaseMsg createMsgRequest(XxlJobInfo info, XxlJobLog jobLog){
        String content = parseContent(info, jobLog);
        return createMsgRequest(content);
    }

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        BaseMsg msgRequest = createMsgRequest(info, jobLog);
        return sendToAll(jobLog, info, msgRequest.toJson());
    }

    @Override
    public boolean accept(XxlJobInfo info) {
        return info != null
               && info.getAlarmType() == getAlarmType().getAlarmType()
               && info.getAlarmUrl() != null
               && !info.getAlarmUrl().trim().isEmpty();
    }
}
