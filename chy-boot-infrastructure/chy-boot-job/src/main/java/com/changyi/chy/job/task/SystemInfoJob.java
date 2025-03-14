package com.changyi.chy.job.task;

import com.changyi.chy.job.core.AbstractJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;

/**
 * 系统信息收集定时任务
 * 用于收集和记录系统运行情况
 *
 * @author YuRuizhi
 */
@Slf4j
@Component
public class SystemInfoJob extends AbstractJob {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void doExecute(JobExecutionContext context) throws Exception {
        // 收集内存使用情况
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        // 堆内存使用率
        double heapUsedPercent = (double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax() * 100;
        // 非堆内存使用率
        double nonHeapUsedPercent = (double) nonHeapMemoryUsage.getUsed() / nonHeapMemoryUsage.getCommitted() * 100;

        // 收集CPU使用率
        double cpuLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();

        // 系统运行信息
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long uptimeInSeconds = uptime / 1000;
        long days = uptimeInSeconds / 86400;
        long hours = (uptimeInSeconds % 86400) / 3600;
        long minutes = (uptimeInSeconds % 3600) / 60;
        long seconds = uptimeInSeconds % 60;

        // 记录系统信息
        log.info("系统运行状态 - 运行时间: {}天{}小时{}分{}秒", days, hours, minutes, seconds);
        log.info("内存使用情况 - 堆内存: {}%, 已用: {}MB, 最大: {}MB", 
                df.format(heapUsedPercent),
                df.format(heapMemoryUsage.getUsed() / (1024.0 * 1024.0)),
                df.format(heapMemoryUsage.getMax() / (1024.0 * 1024.0)));
        log.info("内存使用情况 - 非堆内存: {}%, 已用: {}MB, 已提交: {}MB", 
                df.format(nonHeapUsedPercent),
                df.format(nonHeapMemoryUsage.getUsed() / (1024.0 * 1024.0)),
                df.format(nonHeapMemoryUsage.getCommitted() / (1024.0 * 1024.0)));
        log.info("系统负载: {}", cpuLoad >= 0 ? df.format(cpuLoad) : "不可用");

        // 在这里可以将系统信息保存到数据库或发送告警通知
    }
} 