package com.changyi.chy.job.core;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 抽象基础任务类
 * 所有定时任务都应该继承此类，实现doExecute方法
 *
 * @author YuRuizhi
 */
@Slf4j
public abstract class AbstractJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        log.info("任务[{}]开始执行，时间：{}", jobName, new Date());
        
        long startTime = System.currentTimeMillis();
        try {
            // 执行具体的任务
            doExecute(context);
            long costTime = System.currentTimeMillis() - startTime;
            log.info("任务[{}]执行完成，耗时：{}ms", jobName, costTime);
        } catch (Exception e) {
            long costTime = System.currentTimeMillis() - startTime;
            log.error("任务[{}]执行异常，耗时：{}ms", jobName, costTime, e);
            throw new JobExecutionException(e);
        }
    }

    /**
     * 执行具体的任务
     * 子类必须实现此方法，完成具体的任务逻辑
     *
     * @param context 任务执行上下文
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context) throws Exception;
} 