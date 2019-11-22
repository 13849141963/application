package com.zy.cn.thread;
import com.zy.cn.config.Dispatcher;
import com.zy.cn.config.Loader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*****
 * 该类由LoopAsynTaskmanagerBase进行构造创建、
 * 调度线程为守护线程,线程执行的起始时间为60秒后,周期执行时间由子类决定
 * AsynTaskDispatch 任务类就是在本类中,执行需要执行的任务
 * loader()方法就是加载子类需要执行的任务
 * 通过AsynTaskDispatch进行添加到任务队列中
 * @param <E>
 */
@Slf4j
public class LoopAsynTaskManager<E> {
    // 消息队列 存放任务
    private ConcurrentLinkedQueue<E> msgQueue;
    private Loader<E> loader;
    private Dispatcher<E> dispatcher;
    // 定义周期调度线程池,也可以考虑交给spring工厂管理,根据业务情况自定义
    private ScheduledExecutorService executorService;
    private final int DELAY = 1 * 60;
    private final String threadFactory = "Asyn-task-timerPool-%d";

    LoopAsynTaskManager(Loader<E> loader, Dispatcher<E> dispatcher, int second){
        msgQueue = new ConcurrentLinkedQueue<>();
        this.loader = loader;
        this.dispatcher = dispatcher;

        executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern(threadFactory).daemon(true).build());
                executorService.scheduleAtFixedRate(new AsynTaskDispatch(),DELAY,second,TimeUnit.SECONDS);
    }

    // 收到消息
    public void receiveMsg(E msg){
        // 根据规则是否可以将该任务放入队列中执行 在此根据该任务是否可以取消转发和转发成功来判断的
        if (!dispatcher.canDispatch(msg) || !dispatcher.dispatch(msg)){
           addmsg(msg);
        }
    }


    // 将任务添加到任务队列中
    private void addmsg(E e){
        if (!msgQueue.contains(e)){
            msgQueue.offer(e);
        }
    }

    private void loadTask(){
        // 需要执行的任务
        List<E> load = loader.load();

        for (E item : load) {
            // 添加到任务队列中
            addmsg(item);
        }
    }


    // 通过该方法判断任务是否执行完毕, 执行完毕将任务从消息队列移除
    private void dispatchTask(){
        Iterator<E> iterator = msgQueue.iterator();
        while (iterator.hasNext()){
            E item = iterator.next();
            if (dispatcher.canDispatch(item)){
                // 交给
                if (dispatcher.dispatch(item)) {
                    iterator.remove();
                }
            }
        }

    }


    class AsynTaskDispatch implements Runnable{
        // 最终的执行者
        @Override
        public void run() {
            try {
                // 加载需要执行的任务放入消息队列
                loadTask();
                // 通过该方法判断任务是否执行完毕, 执行完毕将任务从消息队列移除
                dispatchTask();
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        }
    }

}