package com.changyi.chy.job.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 任务信息实体类
 *
 * @author YuRuizhi
 */
@Data
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务类名（全限定类名）
     */
    private String jobClassName;

    /**
     * 任务描述
     */
    private String description;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务状态
     * NONE：不存在
     * NORMAL：正常
     * PAUSED：暂停
     * COMPLETE：完成
     * ERROR：错误
     * BLOCKED：阻塞
     */
    private String jobStatus;

    /**
     * 任务参数（JSON格式）
     */
    private Map<String, Object> jobDataMap;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次执行时间
     */
    private Date previousFireTime;

    /**
     * 下次执行时间
     */
    private Date nextFireTime;
} 