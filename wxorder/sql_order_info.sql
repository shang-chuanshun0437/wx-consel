
//订单详情
CREATE TABLE order_info
(
    id int(11) auto_increment  comment "自增ID",
    order_id VARCHAR(48) NOT NULL comment "订单ID",
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    store_id VARCHAR(48) NOT NULL comment "店铺id",
    food_id int NOT NULL comment "食物（菜）id",
    food_name VARCHAR(48) NOT NULL comment "食物（菜）名称",
    food_count int NOT NULL comment "食物（菜）份数",
    old_price float(6,1) DEFAULT 0 comment "原价",
    new_price float(6,1) DEFAULT 0 comment "现价",
    vip_price float(6,1) DEFAULT 0 comment "会员价",
    vip_num VARCHAR(48) DEFAULT NULL comment "会员编号",
    total_price float(6,1) DEFAULT 0 comment "总价",
    vip_total_price float(6,1) DEFAULT 0 comment "会员总价",
    real_price float(6,1) DEFAULT 0 comment "实收金额",
    order_type int DEFAULT 1 comment "点菜方式：1、点菜  2、加菜",
    category int DEFAULT 1 comment "菜所属的类目：1 小炒系列 ;2 凉菜系列 ； 3  海鲜系列； 4 汤系列；5 酒水饮料；6 主食 ；7 其它",
    create_time datetime DEFAULT NULL comment "订单创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    KEY (user_phone,store_id,food_id),
    KEY (create_time)
);
