package com.chy.boot.common.component.validate;

/**
 * 使用jsr-bean验证规范
 *
 * @author Henry.Yu
 * @date 2020/1/15
 */
public interface ValidGroup {

    /**
     * 新增
     */
    interface Add {
    }

    /**
     * 删除
     */
    interface Delete {
    }

    /**
     * 批量删除
     */
    interface BatDelete {
    }

    /**
     * 批量查询
     */
    interface BatSelect {
    }

    /**
     * 查询单条记录
     */
    interface Get {
    }

    /**
     * 列表查询
     */
    interface List {
    }

    /**
     * 更新
     */
    interface Update {
    }

    /**
     * 签名
     */
    interface Sign {
    }

    /**
     * 检查用户是否存在
     */
    interface CheckMemberExist {
    }

    /**
     * 禁用,启用
     */
    interface SetStatus {
    }
}
