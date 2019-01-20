
//订单
CREATE TABLE store_order
(
    order_id VARCHAR(48) NOT NULL comment "订单ID",
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    store_id VARCHAR(48) NOT NULL comment "店铺id",
    table_id int NOT NULL comment "餐桌ID",
    person_num int NOT NULL comment "就餐人数",
    amount float(6,1) DEFAULT 0 comment "订单金额",
    vip_amount float(6,1) DEFAULT 0 comment "会员金额",
    real_amount float(6,1) DEFAULT 0 comment "实收金额",
    vip_num VARCHAR(48) DEFAULT NULL comment "会员编号",
    pay_type int DEFAULT 1 comment "1 未支付； 2 前台支付； 3 支付宝支付（通过点餐页面支付）；4 微信支付（通过点餐页面支付）",
    source int DEFAULT 1 comment "点菜方式：1 扫描点餐 2 前台点餐 3 美团点餐  4 饿了吗点餐",
    create_time datetime DEFAULT NULL comment "订单创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (order_id),
    KEY (user_phone,store_id,table_id),
    KEY (create_time)
);
