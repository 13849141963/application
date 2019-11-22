package com.zy.cn.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.concurrent.*;

@Slf4j
@Component
public class ThreadPoolManager {


    //根据cpu的数量动态配置核心线程和最大线程数量
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小 = cpu核心数 + 1
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    //线程池最大容纳线程数
    private static final int MAXNUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    //线程空闲后的存活时长
    private static final int KEEP_ALIVE_TIME = 30;
    //指定队列的大小
    private static final int QUEUE_NUM = 2048;
    //任务过多后，存储任务的一个阻塞队列
    private BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(QUEUE_NUM);

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;


    /****
     * 创建线程池
     * @return ThreadPoolExecutor
     */
    @Bean
    public ThreadPoolExecutor  threadPoolExecutor() {
        //线程池任务满载后采取的任务拒绝策略
        RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.AbortPolicy();
        //线程池对象，创建线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,//核心线程数
                MAXNUM_POOL_SIZE,//最大线程数
                KEEP_ALIVE_TIME,//线程空闲存活时间
                TimeUnit.SECONDS,//秒
                blockingQueue,//队列
                Executors.defaultThreadFactory(),//线程工厂，通过工厂创建线程
                rejectHandler//队列满载策略
        );
        return executor;
    }



    /*****
     * 开启一个无返回结果的线程
     * @param callable
     */
    public void execute(Runnable callable){
        //把一个任务丢到线程池
        if (callable == null){
            return;
        }
        threadPoolExecutor.execute(callable);
    }


    /*****
     * 开启一个有返回结果的线程
     * @param r
     */
    public <T> Future<T> submit(Callable<T> r){
        //把一个任务丢到线程池
        if (r == null){
            return null;
        }
        return threadPoolExecutor.submit(r);
    }

    /*****
     * 把任务移除队列
     * @param r
     */
    public void cancel(Runnable r){
       if (r != null){
           threadPoolExecutor.getQueue().remove(r);
       }
    }


}