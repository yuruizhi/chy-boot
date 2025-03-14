-- 测试数据库表结构

-- 套件主表
DROP TABLE IF EXISTS uc_wxqy_suite;
CREATE TABLE uc_wxqy_suite (
    qys_suiteid VARCHAR(50) PRIMARY KEY,
    qys_suite_secret VARCHAR(100),
    qys_suite_aeskey VARCHAR(100),
    qys_token VARCHAR(100),
    qys_ticket VARCHAR(100),
    qys_ips VARCHAR(255),
    qys_suite_access_token VARCHAR(255),
    qys_access_token_expires TIMESTAMP,
    qys_pre_auth_code VARCHAR(100),
    qys_auth_code_expires TIMESTAMP,
    qys_provider VARCHAR(50),
    qys_status INT,
    qys_created TIMESTAMP,
    qys_updated TIMESTAMP,
    qys_deleted TIMESTAMP
);

-- 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
); 