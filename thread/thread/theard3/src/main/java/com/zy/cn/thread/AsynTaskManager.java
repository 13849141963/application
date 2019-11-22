package com.zy.cn.thread;

import com.zy.cn.config.CancelDispatch;
import com.zy.cn.config.Loader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/****
 * 通过调度线程池去执行AsynTaskDispatch类中的 loadTask();方法加载所有需要执行的任务
 *                 dispatchTask();通过该方法执行
 * @param <E>
 */

@Slf4j
public class AsynTaskManager<E> {

    //存放消息的队列
    private ConcurrentLinkedQueue<E> msgQueue;
    private ConcurrentLinkedQueue<E> dispatchedMsgQueue;
    private Loader<E> loader;
    private CancelDispatch<E> dispatcher;
    // 调度线程池
    private ScheduledExecutorService executorService;
    //周期执行起始时间
    private final int DELAY = 1 * 60;
    private final String threadFactory = "Asyn-task-timerPool-%d";

    public AsynTaskManager(Loader<E> loader, CancelDispatch<E> dispatcher, int second){
        msgQueue = new ConcurrentLinkedQueue<>();
        dispatchedMsgQueue = new ConcurrentLinkedQueue<>();
        this.loader = loader;
        this.dispatcher = dispatcher;
        // 守护线程 线程数量为1
        executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern(threadFactory).daemon(true).build());
        executorService.scheduleAtFixedRate(new AsynTaskDispatch(),DELAY,second, TimeUnit.SECONDS);
    }

    public void receiveMsg(E msg){
        if (dispatcher.canDispatch(msg) && dispatcher.dispatch(msg)){
            dispatchedMsgQueue.offer(msg);
        }else {
            addmsg(msg);
        }
    }

    public void cancelJob(E msg){
        if (dispatcher.cancel(msg)){
            dispatchedMsgQueue.remove(msg);
        }
    }

    //将加载的新的任务与转发队列中的任务作对比,如果不存在该任务就需要将转发队列中为执行的任务移除
    private void refreshRunningJob(List<E> tasks){
        Iterator<E> iterator = dispatchedMsgQueue.iterator();

        while (iterator.hasNext()){
            E item = iterator.next();
            if (!tasks.contains(item)){
                // 通知 quzrtz调度器取消执行该定时任务
                dispatcher.cancel(item);
            }
        }
    }

    private void loadTask(){
        List<E> tasks = loader.load();
        for (E item : tasks) {
            addmsg(item);
        }
        //转发队列中的放入的都定时准备执行的任务[实际上任务为执行].
        // 在通过加载数据库的任务发现用户移除任务，也需要在转发队列中的移除为执行的定时任务
        refreshRunningJob(tasks);
    }


    // 执行完的任务放入转发队列,并在消息队列中移除
    private void dispatchTask(){
        Iterator<E> iterator = msgQueue.iterator();
        while (iterator.hasNext()){
            E item = iterator.next();
            // 转发队列中放入的都是已经定时好的任务,转发队列中存在就不需要再重复放入定时任务
            if (dispatchedMsgQueue.contains(item)){
                continue;
            }
            if (dispatcher.canDispatch(item)){
                // 组装定时器 组装任务
                if (dispatcher.dispatch(item)){
                    //组装完成 定时任务交给quartz管理，并在消息队列中移除,并放入转发队列
                    // 转发队列中放入的都是定时好的未执行的任务
                    iterator.remove();
                    dispatchedMsgQueue.offer(item);
                }
            }
        }
    }



    private void addmsg(E msg){
        if (!msgQueue.contains(msg)){
            msgQueue.offer(msg);
        }
    }



    class AsynTaskDispatch implements  Runnable{
        @Override
        public void run() {
            try {
                loadTask();
                dispatchTask();
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        }
    }
}