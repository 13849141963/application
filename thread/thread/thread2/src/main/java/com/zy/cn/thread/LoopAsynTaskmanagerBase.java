package com.zy.cn.thread;

import com.zy.cn.config.Dispatcher;
import com.zy.cn.config.Loader;
import com.zy.cn.pool.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class LoopAsynTaskmanagerBase<T> implements Loader<T>, Dispatcher<T> {

    protected LoopAsynTaskManager<T> asynTask;

    protected ThreadPoolManager threadPoolManager;

    protected LoopAsynTaskmanagerBase(int second,ThreadPoolManager threadPoolManager){
        asynTask = new LoopAsynTaskManager<>(this,this,second);
        this.threadPoolManager = threadPoolManager;
    }

    @Override
    public abstract List<T> load();

    @Override
    public abstract boolean canDispatch(T e);


    // 转发就是构造任务将任务丢入线程池中
    @Override
    public boolean dispatch(T e){
        // 构造任务
        TaskJob job = new TaskJob(e);
        // 交给线程池执行
        threadPoolManager.execute(job);
        return true;
    }

    protected abstract void runTask(T e);



    // 任务类
    protected class TaskJob implements Runnable{
        T task;
        public TaskJob(T task){
            this.task = task;
        }

        @Override
        public void run() {
            try{
                // 子类中执行任务的方法
                runTask(task);
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        }
    }


}