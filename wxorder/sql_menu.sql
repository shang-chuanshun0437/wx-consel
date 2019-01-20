
//店铺里的菜单
CREATE TABLE menu
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    store_id VARCHAR(48) NOT NULL comment "店铺id",
    food_id int NOT NULL comment "食物（菜）id",
    food_name VARCHAR(48) NOT NULL comment "食物（菜）名称",
    food_img VARCHAR(256) NOT NULL comment "商品图片",
    img_name VARCHAR(256) NOT NULL comment "商品图片名称",
    category int NOT NULL comment "菜所属的类目：1 小炒系列 ;2 凉菜系列 ； 3  海鲜系列； 4 汤系列；5 酒水饮料；6 主食 ；7 其它",
    old_price float(6,1) comment "原价",
    new_price float(6,1) comment "现价",
    vip_price float(6,1) comment "会员价",
    recommend int DEFAULT 1 comment "是否推荐 1 不推荐； 2 推荐",
    status int DEFAULT 1 comment "是否售罄：1 未售罄； 2 售罄",
    taste int DEFAULT 1 comment "味道：1 不辣； 2 微辣； 3 中辣；4 特辣",
    create_time datetime DEFAULT NULL comment "餐桌创建时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    UNIQUE KEY(store_id,food_id),
    KEY (category)
);
