package com.zy.cn.thread;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/***
 * 执行任务的线程
 */
@Slf4j
public class ExecutorCallable implements Callable {

    private Logger logger;
    // 执行的任务的唯一标识
    private String jobName;
    private Method method;
    private Object methodBelongToObject;
    private Object data;

    public ExecutorCallable(Logger logger, String jobName, Method method, Object methodBelongToObject, Object data) {
        this.logger = logger;//自己创建
        this.jobName = jobName;
        this.method = method;
        this.methodBelongToObject = methodBelongToObject;
        this.data = data;
    }

    public String getJobName() {
        return jobName;
    }

    @Override
    public Object call() {
        Object result = null;
        log.debug("执行任务");
        try {
            //反射执行方法
            result = this.method.invoke(methodBelongToObject,data);
        }catch (IllegalAccessException e){
            //异常信息
        }catch (InvocationTargetException e){
            //异常信息
        }
        return result;
    }
}