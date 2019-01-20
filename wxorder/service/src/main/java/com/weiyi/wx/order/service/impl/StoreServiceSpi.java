package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.mapper.StoreMapper;
import com.weiyi.wx.order.dao.mapper.UserMapper;
import com.weiyi.wx.order.dao.request.GetStoresRequest;
import com.weiyi.wx.order.service.api.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceSpi implements StoreService
{
    private Logger logger = LoggerFactory.getLogger(StoreServiceSpi.class);

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    @Transactional
    public void addStore(Store store) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addStore() func,the user is:{}",store.getUserPhone());
        }

        //查询该商家的店铺数量是否超过设定值
        User dbUser = userMapper.queryUserByPhone(store.getUserPhone());
        WxOrderAssert.isTrue(dbUser.getShopTotal() > dbUser.getShopCount(),ErrorCode.STORE_NUM_LIMIT,"the stores much more.");

        dbUser.setShopCount(dbUser.getShopCount() + 1);
        userMapper.updateUser(dbUser);

        //生成店铺ID
        String id = String.valueOf(System.currentTimeMillis());
        store.setStoreId(id);

        store.setCreateTime(TimeUtil.getCurrentTime());
        store.setUpdateTime(TimeUtil.getCurrentTime());
        storeMapper.addStore(store);
    }

    public void deleteStore(Store store) {
        if (logger.isDebugEnabled()){
            logger.debug("inter deleteStore() func,the user is:{}",store.getUserPhone());
        }
        //查询店铺信息
        Store dbStore = queryStoreById(store.getStoreId());
        WxOrderAssert.isTrue(dbStore != null,ErrorCode.STORE_NOT_EXIST,"the store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(store.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong to the user.");

        storeMapper.deleteById(store.getStoreId());
    }

    @Transactional
    public void updateStore(Store store) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateStore() func,the user is:{}",store.getUserPhone());
        }

        //查询店铺信息
        Store dbStore = queryStoreById(store.getStoreId());

        //先删除记录，再插入新纪录
        deleteStore(store);

        dbStore.setUpdateTime(TimeUtil.getCurrentTime());
        dbStore.setStoreName(store.getStoreName());
        dbStore.setAddress(store.getAddress());

        addStore(dbStore);
    }

    public List<Store> queryStores(GetStoresRequest getStoresRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryStores() func,the user is:{}",getStoresRequest.getUserPhone());
        }
        return storeMapper.queryStores(getStoresRequest);
    }

    public Store queryStoreById(String id) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryStoreById() func,the store id is:{}",id);
        }
        return storeMapper.queryStoreById(id);
    }

    public int queryStoresCount(GetStoresRequest getStoresRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryStoresCount() func,the user is:{}",getStoresRequest.getUserPhone());
        }

        return storeMapper.queryStoresCount(getStoresRequest);
    }
}
