-- ----------------------------
-- Table structure for t_demo
-- ----------------------------
DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE IF NOT EXISTS `t_demo`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(100) NOT NULL COMMENT '名称',
    `description` varchar(500)          DEFAULT NULL COMMENT '描述',
    `status`      tinyint(4)   NOT NULL DEFAULT '0' COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`) COMMENT '名称索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='演示表';

-- ----------------------------
-- Records of t_demo
-- ----------------------------
INSERT INTO `t_demo` (`name`, `description`, `status`)
VALUES ('测试数据1', '这是一条测试数据的描述信息1', 1),
       ('测试数据2', '这是一条测试数据的描述信息2', 1),
       ('测试数据3', '这是一条测试数据的描述信息3', 0),
       ('测试数据4', '这是一条测试数据的描述信息4', 1),
       ('测试数据5', '这是一条测试数据的描述信息5', 0);