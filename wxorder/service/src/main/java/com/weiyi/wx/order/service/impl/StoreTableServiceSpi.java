package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.common.utils.QRCodeGenerate;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.mapper.StoreMapper;
import com.weiyi.wx.order.dao.mapper.StoreTableMapper;
import com.weiyi.wx.order.dao.mapper.UserMapper;
import com.weiyi.wx.order.dao.request.GetStoreTablesRequest;
import com.weiyi.wx.order.service.api.StoreTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreTableServiceSpi implements StoreTableService
{
    private Logger logger = LoggerFactory.getLogger(StoreTableServiceSpi.class);

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreTableMapper storeTableMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    public void addStoreTable(StoreTable storeTable) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addStoreTable() func.the user is:{}",storeTable.getUserPhone());
        }
        //校验该店铺是否属于该用户
        Store dbStore = storeMapper.queryStoreById(storeTable.getStoreId());
        WxOrderAssert.isTrue(dbStore != null, ErrorCode.STORE_NOT_EXIST,"store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(storeTable.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong the user.");

        //校验餐桌编号是否存在
        StoreTable dbStoreTable = storeTableMapper.queryByTableIdAndStoreId(storeTable);
        WxOrderAssert.isTrue(dbStoreTable == null,ErrorCode.STORE_TABLE_EXIST,"table already exist.");

        //生成二维码
        String qrCodeUrl = QRCodeGenerate.create(storeTable.getUserPhone(),storeTable.getStoreId(),storeTable.getTableId());

        //存入数据库
        storeTable.setCreateTime(TimeUtil.getCurrentTime());
        storeTable.setPersonNum(0);
        storeTable.setStatus(Constant.IDLE);
        storeTable.setQrCodeUrl(qrCodeUrl);
        storeTableMapper.addStoreTable(storeTable);
    }

    @Transactional
    public void updateStoreTable(StoreTable storeTable) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateStoreTable() func.the user is:{}",storeTable.getUserPhone());
        }

        //查询餐桌信息
        StoreTable dbStoreTable1 = storeTableMapper.queryByTableIdAndStoreId(storeTable);
        WxOrderAssert.isTrue(dbStoreTable1 != null,ErrorCode.STORE_TABLE_NOT_EXIST,"the table not exist.");

        //先删除后插入
        storeTableMapper.deleteTable(dbStoreTable1);

        storeTable.setCreateTime(dbStoreTable1.getCreateTime());
        storeTableMapper.addStoreTable(storeTable);
    }

    public void deleteStoreTable(StoreTable storeTable) {
        if (logger.isDebugEnabled()){
            logger.debug("inter deleteStoreTable() func.the user is:{}",storeTable.getUserPhone());
        }
        StoreTable dbStoreTable = storeTableMapper.queryByTableIdAndStoreId(storeTable);
        WxOrderAssert.isTrue(dbStoreTable != null,ErrorCode.STORE_TABLE_NOT_EXIST,"store table not exist.");

        //删除二维码文件
        String qrCodeUrl = dbStoreTable.getQrCodeUrl();
        FileFactory.delFile(Constant.QRCODE_IMG_DIR_ROOT + FileFactory.getFileNameWithTime(qrCodeUrl));

        storeTableMapper.deleteTable(storeTable);
    }

    public List<StoreTable> queryStoreTable(GetStoreTablesRequest getStoreTablesRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryStoreTable() func.the user is:{}",getStoreTablesRequest.getUserPhone());
        }
        //校验该店铺是否属于该用户
        Store dbStore = storeMapper.queryStoreById(getStoreTablesRequest.getStoreId());
        WxOrderAssert.isTrue(dbStore != null, ErrorCode.STORE_NOT_EXIST,"store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(getStoreTablesRequest.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong the user.");

        return storeTableMapper.queryTables(getStoreTablesRequest);
    }

    public int queryStoreTableCount(GetStoreTablesRequest getStoreTablesRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryStoreTableCount() func.the user is:{}",getStoreTablesRequest.getUserPhone());
        }
        //校验该店铺是否属于该用户
        Store dbStore = storeMapper.queryStoreById(getStoreTablesRequest.getStoreId());
        WxOrderAssert.isTrue(dbStore != null, ErrorCode.STORE_NOT_EXIST,"store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(getStoreTablesRequest.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong the user.");

        return storeTableMapper.queryTablesCount(getStoreTablesRequest);
    }

}
