
//会员
CREATE TABLE vip
(
    vip_id VARCHAR(48) NOT NULL comment "会员编号，唯一标志",
    real_amount int NOT NULL comment "会员实际充值金额",
    total_amount int NOT NULL comment "会员总金额",
    balance int DEFAULT 0 comment "会员余额",
    update_time datetime DEFAULT NULL comment "会员最近一次消费时间,格式为2018-10-15 09:55:46",
    create_time datetime DEFAULT NULL comment "会员创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (vip_id)
);
