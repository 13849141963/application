package com.zy.cn.thread;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;

import static com.zy.cn.pool.ThreadPoolManager.recordMoveTask;
import static com.zy.cn.pool.ThreadPoolManager.threadMap;

/****
 * 该线程的作用是清楚存储任务的map,当获取执行状态为成功，获取正常方法返回值的，取消执行任务的情况下，删除CurrconHashMapd的任务。
 */
@Slf4j
public class ClearMapKay implements Runnable {
    /***
     * 初试时间为1小时
     */
    private int initTimeHour = 1;

    @Override
    public void run() {
        log.debug("周期性执行清楚map的失效,成功,正确返回结果的任务");
        try{
            recordMoveTask.forEach((key,value) ->{
                if (Duration.between(value, LocalTime.now()).toHours() >= initTimeHour){
                    threadMap.remove(key);
                    recordMoveTask.remove(key);
                    log.debug("成功清除");
                }
            });
        }catch (Throwable e){
            log.error(e.getMessage(),e);
        }
    }
}