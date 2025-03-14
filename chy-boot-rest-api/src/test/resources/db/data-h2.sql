-- 测试数据

-- 套件表测试数据
INSERT INTO uc_wxqy_suite (
    qys_suiteid, qys_suite_secret, qys_suite_aeskey, qys_token, qys_ticket,
    qys_ips, qys_suite_access_token, qys_access_token_expires, qys_pre_auth_code,
    qys_auth_code_expires, qys_provider, qys_status, qys_created, qys_updated, qys_deleted
) VALUES (
    'test-suite-1', 'test-secret-1', 'test-aeskey-1', 'test-token-1', 'test-ticket-1',
    '127.0.0.1', 'test-access-token-1', CURRENT_TIMESTAMP, 'test-auth-code-1',
    CURRENT_TIMESTAMP, 'test-provider-1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);

INSERT INTO uc_wxqy_suite (
    qys_suiteid, qys_suite_secret, qys_suite_aeskey, qys_token, qys_ticket,
    qys_ips, qys_suite_access_token, qys_access_token_expires, qys_pre_auth_code,
    qys_auth_code_expires, qys_provider, qys_status, qys_created, qys_updated, qys_deleted
) VALUES (
    'test-suite-2', 'test-secret-2', 'test-aeskey-2', 'test-token-2', 'test-ticket-2',
    '192.168.1.1', 'test-access-token-2', CURRENT_TIMESTAMP, 'test-auth-code-2',
    CURRENT_TIMESTAMP, 'test-provider-2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);

-- 用户表测试数据
INSERT INTO user (username, password, nickname, email, phone, status)
VALUES ('admin', '$2a$10$uMaOcW/Xc5yJ2hd87Kl8j.PG5k/gWPZAYXb5aL5GlQyPOYZRaijAK', '管理员', 'admin@example.com', '13800138000', 1);

INSERT INTO user (username, password, nickname, email, phone, status)
VALUES ('user', '$2a$10$r5akw1oIAwdTgAOc5BXI/eEXaqrDrKRUJYh9K1JYwxBDXQR2Yg7mC', '普通用户', 'user@example.com', '13900139000', 1);

INSERT INTO user (username, password, nickname, email, phone, status)
VALUES ('test', '$2a$10$r5akw1oIAwdTgAOc5BXI/eEXaqrDrKRUJYh9K1JYwxBDXQR2Yg7mC', '测试用户', 'test@example.com', '13700137000', 0); 