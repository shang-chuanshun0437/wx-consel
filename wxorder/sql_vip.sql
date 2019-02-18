
//会员
CREATE TABLE vip
(
    id int(11) auto_increment  comment "自增ID",
    vip_id VARCHAR(48) NOT NULL comment "会员编号，唯一标志",
    user_phone BIGINT NOT NULL comment "会员所属的商家",
    real_amount float(6,1) NOT NULL comment "会员实际充值金额",
    consum_count int DEFAULT 0 comment "会员余额",
    update_time datetime DEFAULT NULL comment "会员最近一次消费时间,格式为2018-10-15 09:55:46",
    valid_time datetime DEFAULT NULL comment "会员有效期,格式为2018-10-15 09:55:46",
    create_time datetime DEFAULT NULL comment "会员创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    UNIQUE KEY (user_phone,vip_id)
);
