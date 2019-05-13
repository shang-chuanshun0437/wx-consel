//用户角色表
CREATE TABLE role_user
(
    user_phone BIGINT NOT NULL comment "用户手机号",
    user_name VARCHAR(16) DEFAULT NULL comment "用户名称",
    role_name VARCHAR (16) NOT NULL comment "角色名称",
    create_time datetime comment "用户角色创建日期,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT CURRENT_TIMESTAMP  comment "用户角色更新日期,格式为2018-10-15 09:55:46",
    PRIMARY KEY (user_phone)
);
