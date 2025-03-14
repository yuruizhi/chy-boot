package com.changyi.chy.job.service.impl;

import com.changyi.chy.job.entity.JobInfo;
import com.changyi.chy.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 任务调度管理服务实现
 *
 * @author YuRuizhi
 */
@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public List<JobInfo> listJobs() throws SchedulerException {
        List<JobInfo> jobList = new ArrayList<>();
        // 获取调度器中所有的任务组
        for (String groupName : scheduler.getJobGroupNames()) {
            // 获取任务组下的所有任务Key
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                JobInfo jobInfo = getJobInfo(jobKey);
                if (jobInfo != null) {
                    jobList.add(jobInfo);
                }
            }
        }
        return jobList;
    }

    @Override
    public JobInfo getJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return getJobInfo(jobKey);
    }

    @Override
    public void addJob(JobInfo jobInfo) throws SchedulerException, ClassNotFoundException {
        // 构建任务类
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(jobInfo.getJobClassName());
        // 构建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                .withDescription(jobInfo.getDescription())
                .build();

        // 设置任务参数
        if (jobInfo.getJobDataMap() != null) {
            jobDetail.getJobDataMap().putAll(jobInfo.getJobDataMap());
        }

        // 构建Trigger
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                .withSchedule(scheduleBuilder)
                .build();

        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("任务[{}.{}]添加成功", jobInfo.getJobGroup(), jobInfo.getJobName());
    }

    @Override
    public void updateJob(JobInfo jobInfo) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName(), jobInfo.getJobGroup());
        // 获取触发器
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            throw new SchedulerException("任务不存在");
        }
        
        // 获取现有的cron表达式
        String oldCron = trigger.getCronExpression();
        if (!oldCron.equals(jobInfo.getCronExpression())) {
            // 更新cron表达式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
            // 重新构建触发器
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            // 更新触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        
        // 更新任务描述
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        JobBuilder jb = jobDetail.getJobBuilder();
        jb.withDescription(jobInfo.getDescription());
        
        // 更新任务参数
        if (jobInfo.getJobDataMap() != null) {
            // 清除旧的参数
            jobDetail.getJobDataMap().clear();
            // 设置新的参数
            jobDetail.getJobDataMap().putAll(jobInfo.getJobDataMap());
        }
        
        log.info("任务[{}.{}]更新成功", jobInfo.getJobGroup(), jobInfo.getJobName());
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
        log.info("任务[{}.{}]删除成功", jobGroup, jobName);
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
        log.info("任务[{}.{}]暂停成功", jobGroup, jobName);
    }

    @Override
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.resumeJob(jobKey);
        log.info("任务[{}.{}]恢复成功", jobGroup, jobName);
    }

    @Override
    public void runJobNow(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey);
        log.info("任务[{}.{}]立即执行", jobGroup, jobName);
    }

    /**
     * 获取任务详细信息
     *
     * @param jobKey 任务Key
     * @return 任务信息
     * @throws SchedulerException 调度器异常
     */
    private JobInfo getJobInfo(JobKey jobKey) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return null;
        }

        // 获取触发器
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        if (triggers == null || triggers.isEmpty()) {
            return null;
        }
        
        // 使用第一个触发器
        Trigger trigger = triggers.get(0);
        
        // 构建任务信息
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobName(jobKey.getName());
        jobInfo.setJobGroup(jobKey.getGroup());
        jobInfo.setJobClassName(jobDetail.getJobClass().getName());
        jobInfo.setDescription(jobDetail.getDescription());
        jobInfo.setJobStatus(scheduler.getTriggerState(trigger.getKey()).name());
        jobInfo.setJobDataMap(jobDetail.getJobDataMap().getWrappedMap());
        
        // 设置cron表达式
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            jobInfo.setCronExpression(cronTrigger.getCronExpression());
        }
        
        // 设置时间信息
        jobInfo.setCreateTime(new Date());
        jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
        jobInfo.setNextFireTime(trigger.getNextFireTime());

        return jobInfo;
    }
} 