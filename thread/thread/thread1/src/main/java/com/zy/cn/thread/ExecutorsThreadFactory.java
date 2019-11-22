package com.zy.cn.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/****
 * 创建线程的类：线程工厂
 */
@Slf4j
public class ExecutorsThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable run) {
        String jobName = UUID.randomUUID().toString();
        if (run instanceof ExecutorFutureTask){
            jobName = ((ExecutorFutureTask) run).getJobName();
        }

        Thread thread = new Thread(run,jobName);

        log.debug("创建线程，线程的名字为：{}",Thread.currentThread().getName());

        return thread;
    }
}