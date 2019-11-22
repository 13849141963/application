package com.zy.cn.pool;

import com.zy.cn.thread.ExecutorCallable;
import com.zy.cn.thread.ExecutorFutureTask;
import com.zy.cn.thread.ExecutorsThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.*;

@Slf4j
@Component
public class ThreadPoolManager {


    //存储名称与线程对应的任务信息
    public static final  ConcurrentHashMap<String,RunnableFuture> threadMap = new ConcurrentHashMap<>();
    //存储正常获取方法返回返回值的，任务执行成功的，取消执行任务的key放入该map中
    public static final  ConcurrentHashMap<String, LocalTime> recordMoveTask = new ConcurrentHashMap<>();
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
                new ExecutorsThreadFactory(),//线程工厂，通过工厂创建线程
                rejectHandler//队列满载策略
        );
        return executor;
    }


    /*****
     * 开启一个有返回结果的线程
     * @param callable
     */
    public void submit(ExecutorCallable callable){
        //把一个任务丢到线程池
        if (callable == null){
            return;
        }

        RunnableFuture<String> futureTask = new ExecutorFutureTask<>(callable);

        //将执行的任务的放入ma中,key为唯一标识
        threadMap.put(callable.getJobName(),futureTask);

        threadPoolExecutor.execute(futureTask);
    }


    public void cancel(String jobName){
        if (threadMap.containsKey(jobName)){
            RunnableFuture runnableFuture = threadMap.get(jobName);
            runnableFuture.cancel(true);
            //方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true
            while (!runnableFuture.isCancelled()){
                threadMap.remove(jobName);
            }
            checkMapContainKey(jobName);
        }
    }

    /***
     * 获取任务的状态
     * @param jobName
     * @return
     */
    private int status(String jobName){
        if (!threadMap.containsKey(jobName)){
            return 0;//任务终止
        }else {
            RunnableFuture r = threadMap.get(jobName);
            //方法表示任务是否已经完成
            if (r.isDone()){
                checkMapContainKey(jobName);
                return 1;//成功
            }else if(r.isCancelled()) {
                checkMapContainKey(jobName);
                return 2;//任务停止
            } else {
                if (threadPoolExecutor.getQueue().contains(jobName)){
                    return 3;//队列中存在该任务，说明状态等待状态
                }else {
                    return 5;//运行状态
                }
            }
        }
    }


    /***
     * 获取任务执行的结果
     * @param jobName
     * @return
     */
    public Object getResult(String jobName){
        if (!threadMap.containsKey(jobName)){
            return null;
        }
        RunnableFuture t = threadMap.get(jobName);

        //任务没有执行完成
        if (!t.isDone()){
            return null;
        }
        try{
            checkMapContainKey(jobName);
            //用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null
            Object result = t.get(60L, TimeUnit.SECONDS);
            return result;
        }catch (Throwable e){
            log.error(e.getMessage(),e);
        }
        return null;
    }


    public static void checkMapContainKey(String jobName){
        if (!recordMoveTask.containsKey(jobName)){
            //放入需要移除任务的key,值为当前时间
            recordMoveTask.put(jobName,LocalTime.now());
        }
    }


}