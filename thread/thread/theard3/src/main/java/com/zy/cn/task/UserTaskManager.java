package com.zy.cn.task;
import com.zy.cn.config.CancelDispatch;
import com.zy.cn.config.Loader;
import com.zy.cn.quartz.CalendarIntervalQuartz;
import com.zy.cn.quartz.TriggerJobInterface;
import com.zy.cn.thread.AsynTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserTaskManager implements Loader<TaskModel>, CancelDispatch<TaskModel> {

    private AsynTaskManager<TaskModel> taskMa;

    private CalendarIntervalQuartz<RunTaskConfig> calScheduler;


    public UserTaskManager(){
        taskMa = new AsynTaskManager<>(this,this,10 * 60);
        calScheduler = new CalendarIntervalQuartz<>();
    }

    // 对外提供单个执行任务
    public boolean addRuntaskDispatcherJob(TaskModel taskModel){
        taskMa.receiveMsg(taskModel);
        return true;
    }


    public boolean cancelTask(TaskModel taskModel){
        taskMa.cancelJob(taskModel);
        return true;
    }

    @Override
    public boolean cancel(TaskModel taskModel) {
        RunTaskConfig config = new RunTaskConfig(taskModel);
        return calScheduler.cancelJob(config);
    }

    @Override
    public boolean canDispatch(TaskModel o) {
        return true;
    }

    // 生成定时器 在定时器中执行任务
    @Override
    public boolean dispatch(TaskModel taskModel) {
        RunnJob job = new RunnJob(taskModel);

        RunTaskConfig config = new RunTaskConfig(taskModel);

        return calScheduler.commitJob(config,job);
    }

    @Override
    public List<TaskModel> load() {
        // 从数据库加载需要执行的任务
        return null;
    }

    private class RunnJob implements Runnable{
        TaskModel task;
        public RunnJob(TaskModel task){
            this.task = task;
        }
        @Override
        public void run() {
            try {
                runTask(task);
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        }
    }


    private void runTask(TaskModel taskModel){
        //需要执行的具体任务
    }


    //通过该类组装任务具体的执行时间
    private class RunTaskConfig implements TriggerJobInterface {

        TaskModel task;

        public RunTaskConfig(TaskModel task){
            this.task = task;
        }

        @Override
        public Date getStartTime() {
            // 复杂的可以业务方法获取任务执行时间。
            return task.getStartDate();
        }

        @Override
        public int getDispatchHour() {
            if (task.getHour() == null){
                return 0;
            }
            return task.getHour();
        }

        @Override
        public int getDispatchMinute() {
            if (task.getMintue() == null){
                return 0;
            }
            return task.getMintue() ;
        }

        @Override
        public int getDispatchSecond() {
            if (task.getSecond() == null){
                return 0;
            }
            return task.getSecond();
        }
    }
}