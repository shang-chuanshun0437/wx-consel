
//商家支付参数配置表
CREATE TABLE pay_setting
(
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    app_id VARCHAR(32) NOT NULL comment "微信支付分配的公众账号ID（企业号corpid即为此appId）",
    mch_id VARCHAR (32) NOT NULL comment "微信支付分配的商户号",
    open_id VARCHAR (128) NOT NULL comment "微信用户在商户对应appid下的唯一标识",
    wx_key VARCHAR (128) NOT NULL comment "为商户平台设置的密钥key",
    pay_type int DEFAULT 1 comment "支付方式：1前台支付 2在线支付",
    create_time datetime DEFAULT NULL comment "参数设置时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (user_phone)
);
