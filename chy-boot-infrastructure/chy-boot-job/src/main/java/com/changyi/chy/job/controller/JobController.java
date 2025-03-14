package com.changyi.chy.job.controller;

import com.changyi.chy.common.core.domain.R;
import com.changyi.chy.job.entity.JobInfo;
import com.changyi.chy.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务管理控制器
 *
 * @author YuRuizhi
 */
@Slf4j
@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * 获取所有任务
     *
     * @return 任务列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:job:list')")
    public R<List<JobInfo>> listJobs() {
        try {
            List<JobInfo> jobs = jobService.listJobs();
            return R.ok(jobs);
        } catch (SchedulerException e) {
            log.error("获取任务列表失败", e);
            return R.fail("获取任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取任务详情
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 任务详情
     */
    @GetMapping("/detail")
    @PreAuthorize("hasAuthority('system:job:query')")
    public R<JobInfo> getJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        try {
            JobInfo job = jobService.getJob(jobName, jobGroup);
            if (job != null) {
                return R.ok(job);
            } else {
                return R.fail("任务不存在");
            }
        } catch (SchedulerException e) {
            log.error("获取任务详情失败", e);
            return R.fail("获取任务详情失败: " + e.getMessage());
        }
    }

    /**
     * 添加任务
     *
     * @param jobInfo 任务信息
     * @return 操作结果
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:job:add')")
    public R<Void> addJob(@RequestBody JobInfo jobInfo) {
        try {
            jobService.addJob(jobInfo);
            return R.ok();
        } catch (SchedulerException | ClassNotFoundException e) {
            log.error("添加任务失败", e);
            return R.fail("添加任务失败: " + e.getMessage());
        }
    }

    /**
     * 更新任务
     *
     * @param jobInfo 任务信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('system:job:edit')")
    public R<Void> updateJob(@RequestBody JobInfo jobInfo) {
        try {
            jobService.updateJob(jobInfo);
            return R.ok();
        } catch (SchedulerException e) {
            log.error("更新任务失败", e);
            return R.fail("更新任务失败: " + e.getMessage());
        }
    }

    /**
     * 删除任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('system:job:delete')")
    public R<Void> deleteJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        try {
            jobService.deleteJob(jobName, jobGroup);
            return R.ok();
        } catch (SchedulerException e) {
            log.error("删除任务失败", e);
            return R.fail("删除任务失败: " + e.getMessage());
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 操作结果
     */
    @PutMapping("/pause")
    @PreAuthorize("hasAuthority('system:job:edit')")
    public R<Void> pauseJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        try {
            jobService.pauseJob(jobName, jobGroup);
            return R.ok();
        } catch (SchedulerException e) {
            log.error("暂停任务失败", e);
            return R.fail("暂停任务失败: " + e.getMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 操作结果
     */
    @PutMapping("/resume")
    @PreAuthorize("hasAuthority('system:job:edit')")
    public R<Void> resumeJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        try {
            jobService.resumeJob(jobName, jobGroup);
            return R.ok();
        } catch (SchedulerException e) {
            log.error("恢复任务失败", e);
            return R.fail("恢复任务失败: " + e.getMessage());
        }
    }

    /**
     * 立即执行任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return 操作结果
     */
    @PutMapping("/run")
    @PreAuthorize("hasAuthority('system:job:edit')")
    public R<Void> runJobNow(@RequestParam String jobName, @RequestParam String jobGroup) {
        try {
            jobService.runJobNow(jobName, jobGroup);
            return R.ok();
        } catch (SchedulerException e) {
            log.error("立即执行任务失败", e);
            return R.fail("立即执行任务失败: " + e.getMessage());
        }
    }
} 