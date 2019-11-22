package com.zy.cn.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
/***
 * 通过ExecutorFutureTask做转化
 * @param <T>
 */
@Slf4j
public class ExecutorFutureTask<T> extends FutureTask<T> {

    private String jobName;

    public ExecutorFutureTask(Callable<T> callable) {
        super(callable);

        if (callable instanceof ExecutorCallable){
            this.jobName = ((ExecutorCallable)callable).getJobName();
        }else {
            this.jobName = UUID.randomUUID().toString();
        }
    }

    public ExecutorFutureTask(Runnable runnable, T result) {
        super(runnable, result);
    }


    public String getJobName() {
        return jobName;
    }
}