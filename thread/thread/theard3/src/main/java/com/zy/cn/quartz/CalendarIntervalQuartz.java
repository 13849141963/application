package com.zy.cn.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
public class CalendarIntervalQuartz<E extends TriggerJobInterface> {

    private static final String KEY = "thread";

    private static final int DELAYS = 60;

    private static final int DELAY = 1 * 60;

    private Scheduler scheduler;

    private HashMap<E, TriggerKey> trigMap;

    public CalendarIntervalQuartz(){
        trigMap = new HashMap<>();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);
        }
    }

    public boolean commitJob(E job,Runnable t){
        if (scheduler == null || trigMap == null){
            return false;
        }
        if (job == null || t == null){
            return false;
        }
        // 获取时分秒
        int mintue = job.getDispatchMinute();
        int hour = job.getDispatchHour();
        int second = job.getDispatchSecond();


        // 执行的时间格式错误
        if (hour < 0 || hour > 23 || mintue > 59 || mintue < 0 || second < 60 || second > 0)return false;

        // 获取开始结束时间
        Date start = job.getStartTime();

        if (start == null) return false;

        // 获取当前时间的后一分钟 例如当前时间为 2019-07-25 15:15:15 返回的时间为2019-07-25 15:16:15
        Date thisDate = getUpDateTime(new Date(), DELAYS);

        // 获取用户指定执行任务的时间
        start =  getGiveHourofDate(start,hour,mintue,second);
        // 和当前时间进行对比 如果在当前时间之前则指定明天执行,不是在当前之间则可以执行
        if (start.before(thisDate)){
            start = DateBuilder.tomorrowAt(hour,mintue,second);
        }

        // 触发器
        Trigger trigger = newTrigger()
                .startAt(start)
                .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                        .withIntervalInDays(1))
                .build();
        // 将当前线程对象放入jobdatamap方便传入Job工作类中
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(KEY,t);
        // 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(QuarterJob.class)
                .usingJobData(jobDataMap)// 传递datamap到工作累中
                .build();

        try{
            // 交给调度器去执行
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);
            return false;
        }
        // 放入map中方便执行取消任务
        trigMap.put(job,trigger.getKey());
        return true;
    }

    public boolean cancelJob(E job){
        // key 为触发器的标识
        TriggerKey key = trigMap.get(job);
        if (key == null){
            return false;
        }
        try{
            // 取消任务的执行
            scheduler.unscheduleJob(key);
            return true;
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);
            return false;
        }
    }


    public static class QuarterJob implements Job{
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDetail jobDetail = context.getJobDetail();
            try {
                Object o = jobDetail.getJobDataMap().get(KEY);
                if (o instanceof Runnable){
                    Runnable runnable = (Runnable)o;
                    runnable.run();
                }
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        }
    }


    private static Date getUpDateTime(Date date,int second){
        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND,second);
        return calendar.getTime();
    }

    private static Date getGiveHourofDate(Date date,int hour,int mintue,int second){
        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(java.util.Calendar.HOUR_OF_DAY,hour);
        calendar.set(java.util.Calendar.MINUTE,mintue);
        calendar.set(java.util.Calendar.SECOND,second);
        return calendar.getTime();
    }

}