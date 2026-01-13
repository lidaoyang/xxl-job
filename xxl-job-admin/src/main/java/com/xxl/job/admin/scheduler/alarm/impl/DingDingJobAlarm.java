package com.xxl.job.admin.scheduler.alarm.impl;

import com.xxl.job.admin.scheduler.alarm.AlarmTypeEnum;
import com.xxl.job.admin.scheduler.alarm.msg.BaseMsg;
import com.xxl.job.admin.scheduler.alarm.msg.DingDingTextMsgReq;
import org.springframework.stereotype.Component;

/**
 * job alarm by ding ding
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/10 18:22
 */
@Component
public class DingDingJobAlarm extends AbstractJobAlarm {

    @Override
    protected AlarmTypeEnum getAlarmType() {
        return AlarmTypeEnum.DING_DING;
    }

    @Override
    protected BaseMsg createMsgRequest(String content) {
        DingDingTextMsgReq msgReq = new DingDingTextMsgReq();
        msgReq.withContent(content);
        return msgReq;
    }
}
