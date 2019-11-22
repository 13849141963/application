package com.zy.cn.task;

import lombok.Data;

import java.util.Date;
@Data
public class TaskModel {
    private String id;
    private String taskName;
    private String jobId;
    // 指定执行任务的日期
    private Date startDate;
    // 时分秒
    private Integer hour;
    private Integer mintue;
    private Integer second;

}