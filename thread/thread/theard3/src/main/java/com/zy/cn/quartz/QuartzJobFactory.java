package com.zy.cn.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component("quartzJobFactory")
public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory factory;


    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入 (spring容器进行管理该bean)
        factory.autowireBean(jobInstance);
        return jobInstance;
    }

}