//商家店铺
CREATE TABLE store
(
    store_id VARCHAR(48) NOT NULL comment "店铺ID，通过时间戳来生成",
    user_phone BIGINT NOT NULL comment "商家手机号，店铺的拥有者",
    address VARCHAR (48) DEFAULT NULL comment "店铺地址",
    store_name VARCHAR (48) DEFAULT NULL comment "店铺名称",
    table_num int DEFAULT 0 comment "店铺餐桌数量",
    food_num int DEFAULT 0 comment "菜类数量",
    create_time datetime DEFAULT NULL comment "店铺接入时间,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT NULL comment "店铺更新时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (store_id),
    KEY (user_phone)
);
