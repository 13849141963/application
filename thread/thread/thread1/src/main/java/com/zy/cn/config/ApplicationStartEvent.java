package com.zy.cn.config;

import com.zy.cn.thread.ClearMapKay;
import javafx.concurrent.ScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/****
 * 应用启动完成后加载该方法
 */
@Slf4j
@Component
public class ApplicationStartEvent implements ApplicationRunner {

    @Autowired
    private ScheduledExecutorService scheduledPool;
    //指定延迟执行时间
    private static int INIT_DEJAY = 0;
    //周期执行间隔时间为1小时
    private static final int PERIOD = 1;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("application start event!");
        /****
         * 创建一个周期性线程池,该操作的在给定start时间后首先启用,随后在给定的周期轮训执行
         * TimeUnit.HOURS 小时
         * TimeUnit.MILLISECONDS 毫秒
         * TimeUnit.DAYS 天
         * TimeUnit.SECONDS 秒
         * TimeUnit.MINUTES 分钟
         */
        scheduledPool.scheduleAtFixedRate(new ClearMapKay(), INIT_DEJAY, PERIOD, TimeUnit.HOURS);
    }
}