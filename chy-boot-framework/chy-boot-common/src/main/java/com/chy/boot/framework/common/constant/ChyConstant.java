package com.chy.boot.framework.common.constant;

/**
 * CHY 常量池
 *
 * @author YuRuiZhi
 */
public interface ChyConstant {

    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = "chy-boot-";

    /**
     * 成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";

    /**
     * 失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";

    /**
     * 空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";

    /**
     * 系统管理员用户ID
     */
    Long SYS_ADMIN_USER_ID = 1L;

    /**
     * 编码
     */
    String UTF_8 = "UTF-8";

    /**
     * contentType
     */
    String CONTENT_TYPE_NAME = "Content-type";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 角色前缀
     */
    String SECURITY_ROLE_PREFIX = "ROLE_";

    /**
     * 主键字段名
     */
    String DB_PRIMARY_KEY = "id";

    /**
     * 业务状态[1:正常]
     */
    int DB_STATUS_NORMAL = 1;
    int DB_STATUS_DELETE = 0;

    /**
     * 会员身份[1:访客]
     */
    int IS_VISITORS = 1;

    /**
     * 删除状态[0:正常,1:删除]
     */
    int DB_NOT_DELETED = 0;
    int DB_IS_DELETED = 1;

    /**
     * 用户锁定状态
     */
    int DB_ADMIN_NON_LOCKED = 0;
    int DB_ADMIN_LOCKED = 1;

    /**
     * 管理员对应的租户ID
     */
    String ADMIN_TENANT_ID = "000000";

    /**
     * 日志默认状态
     */
    String LOG_NORMAL_TYPE = "1";

    /**
     * 默认未授权消息
     */
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

}
