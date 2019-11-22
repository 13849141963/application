package com.zy.cn.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
@Configuration
public class QuartzConfiguraton {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(QuartzJobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        log.debug("创建SchedulerFactoryBean对象");
        // 交给spring 容器进行管理
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler schedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try{
            scheduler.start();
            log.debug("定时任务启动完成");
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);
        }
        return scheduler;
    }


}