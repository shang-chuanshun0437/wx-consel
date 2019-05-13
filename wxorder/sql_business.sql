
CREATE database weiyi;

CREATE database weiyi;
//商家接入表
CREATE TABLE business
(
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    password VARCHAR(48) NOT NULL comment "商家密码",
    token VARCHAR (48) DEFAULT NULL comment "商家token",
    shop_total int DEFAULT 0 comment "商家可以接入的店铺数量",
    shop_count int DEFAULT 0 comment "商家已接入的店铺数量",
    user_name VARCHAR (16) DEFAULT NULL comment "商家名称",
    user_address VARCHAR (126) DEFAULT NULL comment "商家地址",
    create_time datetime DEFAULT NULL comment "商家接入时间,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT NULL comment "商家更新时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (user_phone),
    KEY (user_name)
);
