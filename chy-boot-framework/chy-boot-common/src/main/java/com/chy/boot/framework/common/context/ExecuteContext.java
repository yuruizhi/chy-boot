package com.chy.boot.framework.common.context;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 业务执行上下文，主要存储执行相关的信息
 * 与Spring @RequestScope 类似，但生命周期和范围不局限于Web，使用ThreadLocal实现. 默认为短时间执行模式（需执行结束释放）
 *
 * @author YuRuiZhi
 */
@Data
@Accessors(chain = true)
public class ExecuteContext {

    /**
     * threadlocal存储上下文对象
     */
    static private ThreadLocal<ExecuteContext> contextHolder = new ThreadLocal<>();

    /**
     * 当前线程执行的ID，UUID，可外部传入
     */
    private String requestId;

    /**
     * 扩展数据，从过滤器链一直携带过来
     */
    private Map<String, Object> attributes;

    /**
     * 用于存储额外参数链
     */
    private Map<String, Object> extraParams;

    /**
     * 目标目标类（控制器、服务）
     */
    private Class<?> targetClass;

    /**
     * 目标方法
     */
    private String targetMethod;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 系统标识（租户模式）
     */
    private String systemId;

    /**
     * 响应码 - 用于异步任务使用
     */
    private int code;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 响应数据对象
     */
    private Object data;

    public ExecuteContext() {
        requestId = UUID.randomUUID().toString();
        attributes = new HashMap<>();
        extraParams = new HashMap<>();
    }

    /**
     * 获取当前线程context实例
     *
     * @return ExecuteContext
     */
    static public ExecuteContext getContext() {
        ExecuteContext context = contextHolder.get();
        if (context == null) {
            // 创建默认
            context = new ExecuteContext();
            context.requestId = UUID.randomUUID() + "_" + System.currentTimeMillis();
            contextHolder.set(context);
        }
        return context;
    }

    /**
     * 创建执行上下文
     *
     * @param requestId 为空则自动生成
     * @return ExecuteContext
     */
    static public ExecuteContext initContext(String requestId) {
        ExecuteContext context = new ExecuteContext();
        if (StringUtils.hasText(requestId)) {
            context.requestId = requestId;
        }
        contextHolder.set(context);
        return context;
    }

    /**
     * 释放上下文
     */
    static public void releaseContext() {
        contextHolder.remove();
    }
}
