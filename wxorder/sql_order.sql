
//订单
CREATE TABLE store_order
(
    order_id VARCHAR(48) NOT NULL comment "订单ID",
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    store_id VARCHAR(48) NOT NULL comment "店铺id",
    store_name VARCHAR(48) NOT NULL comment "店铺名称",
    table_id int NOT NULL comment "餐桌ID",
    person_num int NOT NULL comment "就餐人数",
    amount float(6,1) DEFAULT 0 comment "订单金额",
    vip_amount float(6,1) DEFAULT 0 comment "会员金额",
    real_amount float(6,1) DEFAULT 0 comment "实收金额",
    vip_num VARCHAR(48) DEFAULT NULL comment "会员编号",
    pay_type int DEFAULT 1 comment "1 未支付； 2 前台支付； 3 扫码支付-支付宝；4 扫码支付-微信（通过点餐页面支付）",
    source int DEFAULT 1 comment "点菜方式：1 扫描点餐 2 前台点餐 3 美团点餐  4 饿了吗点餐",
    order_status int DEFAULT 1 comment "订单是否被商家接收：1 订单状态为已接收 2 订单状态为未接收",
    order_temp int DEFAULT 1 comment "订单是否为临时订单（主要针对的是在线支付的预支付）：1 临时 2 非临时",
    create_time datetime DEFAULT NULL comment "订单创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (order_id),
    KEY (user_phone,store_id,table_id),
    KEY (create_time)
);
