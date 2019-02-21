
//店铺里的餐桌
CREATE TABLE store_table
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "商家手机号，唯一标示这个商家",
    store_id VARCHAR(48) NOT NULL comment "店铺id",
    table_id int NOT NULL comment "餐桌id",
    capacity int DEFAULT 0 comment "餐桌容纳的就餐人数",
    address VARCHAR(48) DEFAULT null comment "餐桌位置",
    person_num int NOT NULL comment "就餐人数",
    status int DEFAULT 0 comment "餐桌使用状态：1 空闲；2就餐中",
    create_time datetime DEFAULT NULL comment "餐桌创建时间,格式为2018-10-15 09:55:46",
    qr_code_url VARCHAR(256) NOT NULL comment "二维码URL",
    PRIMARY KEY (id),
    UNIQUE KEY(table_id,store_id),
    KEY (status)
);
