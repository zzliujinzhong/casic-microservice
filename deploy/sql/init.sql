-- ============================================
-- CASIC 微服务框架 - 数据库初始化脚本
-- Database: MySQL 8.0+
-- ============================================

CREATE DATABASE IF NOT EXISTS casic DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE casic;

-- ==================== 用户表 ====================
DROP TABLE IF EXISTS t_sys_user;
CREATE TABLE t_sys_user (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    username        VARCHAR(64)  NOT NULL COMMENT '用户名',
    password        VARCHAR(128) NOT NULL COMMENT '密码 (BCrypt)',
    nick_name       VARCHAR(64)  DEFAULT NULL COMMENT '昵称',
    email           VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    phone           VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    avatar          VARCHAR(512) DEFAULT NULL COMMENT '头像 URL',
    status          TINYINT      DEFAULT 1 COMMENT '状态 (1-正常 0-禁用)',
    create_by       VARCHAR(64)  DEFAULT NULL COMMENT '创建人',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT NULL COMMENT '更新人',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ==================== 合同表 ====================
DROP TABLE IF EXISTS t_contract;
CREATE TABLE t_contract (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    contract_no     VARCHAR(64)     NOT NULL COMMENT '合同编号',
    contract_name   VARCHAR(256)    NOT NULL COMMENT '合同名称',
    contract_type   TINYINT         DEFAULT 1 COMMENT '合同类型 (1-采购合同 2-销售合同 3-框架协议)',
    amount          DECIMAL(18, 2)  DEFAULT 0 COMMENT '合同金额',
    sign_date       DATE            DEFAULT NULL COMMENT '签订日期',
    effective_date  DATE            DEFAULT NULL COMMENT '生效日期',
    expire_date     DATE            DEFAULT NULL COMMENT '到期日期',
    status          TINYINT         DEFAULT 0 COMMENT '合同状态 (0-草稿 1-待审批 2-已生效 3-已终止)',
    party_a         VARCHAR(256)    DEFAULT NULL COMMENT '甲方名称',
    party_b         VARCHAR(256)    DEFAULT NULL COMMENT '乙方名称',
    remark          VARCHAR(1024)   DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL COMMENT '创建人',
    create_time     DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT NULL COMMENT '更新人',
    update_time     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT         DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_contract_no (contract_no),
    KEY idx_status (status),
    KEY idx_sign_date (sign_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- ==================== 字典表 ====================
DROP TABLE IF EXISTS t_sys_dict;
CREATE TABLE t_sys_dict (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    dict_type       VARCHAR(64)  NOT NULL COMMENT '字典类型',
    dict_code       VARCHAR(64)  NOT NULL COMMENT '字典编码',
    dict_name       VARCHAR(128) NOT NULL COMMENT '字典名称',
    sort_order      INT          DEFAULT 0 COMMENT '排序',
    status          TINYINT      DEFAULT 1 COMMENT '状态',
    remark          VARCHAR(512) DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)  DEFAULT NULL COMMENT '创建人',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT NULL COMMENT '更新人',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_type_code (dict_type, dict_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ==================== 初始化数据 ====================
-- 默认用户 (密码: admin123, BCrypt)
INSERT INTO t_sys_user (username, password, nick_name, status, create_by) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '超级管理员', 1, 'system');

-- 合同类型字典
INSERT INTO t_sys_dict (dict_type, dict_code, dict_name, sort_order, create_by) VALUES
('contract_type', '1', '采购合同', 1, 'system'),
('contract_type', '2', '销售合同', 2, 'system'),
('contract_type', '3', '框架协议', 3, 'system');

-- 合同状态字典
INSERT INTO t_sys_dict (dict_type, dict_code, dict_name, sort_order, create_by) VALUES
('contract_status', '0', '草稿', 1, 'system'),
('contract_status', '1', '待审批', 2, 'system'),
('contract_status', '2', '已生效', 3, 'system'),
('contract_status', '3', '已终止', 4, 'system');

-- 示例合同
INSERT INTO t_contract (contract_no, contract_name, contract_type, amount, sign_date, effective_date, expire_date, status, party_a, party_b, create_by) VALUES
('HT-2024-0001', 'XX设备采购合同', 1, 500000.00, '2024-01-15', '2024-01-20', '2025-01-19', 2, '中国航天科工集团', 'XX科技有限公司', 'admin'),
('HT-2024-0002', 'XX技术服务合同', 2, 1200000.00, '2024-02-20', '2024-03-01', '2025-02-28', 1, 'XX公司', '中国航天科工集团', 'admin'),
('HT-2024-0003', '长期框架采购协议', 3, 0.00, '2024-03-10', '2024-03-15', '2027-03-14', 2, '中国航天科工集团', 'YY供应商', 'admin');
