package com.zy.cn.task;

import com.zy.cn.pool.ThreadPoolManager;
import com.zy.cn.thread.LoopAsynTaskmanagerBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/****
 * 需要执行周期性任务的类都需要继承LoopAsynTaskmanagerBase类并实现重写方法
 * // LoopAsynTaskmanagerBase<Object> object 就是需要执行的任务类的对象
 * 在这里我自定义字符串,使用时修改成任务类对象model即可
 */
@Slf4j
@Service
public class UserTaskManager extends LoopAsynTaskmanagerBase<String> {



    private static final int loopTime = 1 * 60;

    @Autowired
    public UserTaskManager(ThreadPoolManager threadPoolManager) {
        super(loopTime, threadPoolManager);
    }

    // 定义该方法可以手动添加一个任务放入队列执行，
    public boolean recievMsg(String msg){
        //根据规则自定义 任务是否符合执行的标准
      if (msg == null){
          return false;
      }
      // 将任务添加到队列中
      asynTask.receiveMsg(msg);
        return true;
    }

    @Override
    public List load() {
        // load()方法中就是取出数据库查询的需要执行的任务 LoopAsynTaskManager会加载load返回的任务
        return null;
    }

    @Override
    public boolean canDispatch(String e) {
        // 根据需求定义规则 什么任务才可以取消，什么任务不可以取消 默认true
        return true;
    }

    @Override
    protected void runTask(String e) {
        // 该方法是需要执行的具体任务
        // 最后都是交给LoopAsynTaskmanagerBase中的TaskJob线程，在线程池中并行执行
    }
}