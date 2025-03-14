package com.changyi.chy.job.service;

import com.changyi.chy.job.entity.JobInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 任务调度管理服务接口
 *
 * @author YuRuizhi
 */
public interface JobService {

    /**
     * 获取所有任务列表
     *
     * @return 任务列表
     * @throws SchedulerException 调度器异常
     */
    List<JobInfo> listJobs() throws SchedulerException;

    /**
     * 获取任务详情
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 任务详情
     * @throws SchedulerException 调度器异常
     */
    JobInfo getJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 添加任务
     *
     * @param jobInfo 任务信息
     * @throws SchedulerException 调度器异常
     * @throws ClassNotFoundException 类不存在异常
     */
    void addJob(JobInfo jobInfo) throws SchedulerException, ClassNotFoundException;

    /**
     * 更新任务
     *
     * @param jobInfo 任务信息
     * @throws SchedulerException 调度器异常
     */
    void updateJob(JobInfo jobInfo) throws SchedulerException;

    /**
     * 删除任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    void deleteJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    void pauseJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    void resumeJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 立即执行任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @throws SchedulerException 调度器异常
     */
    void runJobNow(String jobName, String jobGroup) throws SchedulerException;
} 