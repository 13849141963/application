package com.zy.cn.quartz;

import java.util.Date;

public interface TriggerJobInterface {

    Date getStartTime();
    // 根据业务考虑是否有结束时间
    //Date getEndTime();

    int getDispatchHour();

    int getDispatchMinute();

    int getDispatchSecond();
}
