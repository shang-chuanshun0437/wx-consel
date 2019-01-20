package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.request.GetStoreTablesRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.StoreTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/store/table")
public class StoreTableController
{
    private Logger logger = LoggerFactory.getLogger(StoreTableController.class);

    @Autowired
    private StoreTableService storeTableService;

    /*
    *添加餐桌
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddStoreTableResponse addStoreTable(@RequestBody AddStoreTableRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addStoreTable() func,phone num:{}",request.getUserPhone());
        }

        AddStoreTableResponse response = new AddStoreTableResponse();
        Result result = new Result();
        response.setResult(result);

        StoreTable storeTable = new StoreTable();
        CopyProperties.copy(storeTable,request);

        try {
            storeTableService.addStoreTable(storeTable);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }
    /*
     *查询餐桌列表
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryStoreTableResponse queryStoreTable(@RequestBody QueryStoreTableRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryStoreTable() func,phone num:{}",request.getUserPhone());
        }

        QueryStoreTableResponse response = new QueryStoreTableResponse();
        Result result = new Result();
        response.setResult(result);

        GetStoreTablesRequest getStoreTablesRequest = new GetStoreTablesRequest();
        CopyProperties.copy(getStoreTablesRequest,request);
        if (getStoreTablesRequest.getCurrentPage() != null){
            getStoreTablesRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }
        try {
            int total = storeTableService.queryStoreTableCount(getStoreTablesRequest);
            List<StoreTable> storeTables = storeTableService.queryStoreTable(getStoreTablesRequest);

            if (storeTables != null && storeTables.size() > 0){
                response.setStoreTables(storeTables.toArray(new StoreTable[storeTables.size()]));
            }
            response.setTotal(total);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *更新餐桌
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateStoreTableResponse updateStoreTable(@RequestBody UpdateStoreTableRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateStoreTable() func,phone num:{}",request.getUserPhone());
        }

        UpdateStoreTableResponse response = new UpdateStoreTableResponse();
        Result result = new Result();
        response.setResult(result);

        StoreTable storeTable = new StoreTable();
        CopyProperties.copy(storeTable,request);

        try {
            storeTableService.updateStoreTable(storeTable);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *删除餐桌
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteStoreTableResponse deleteStoreTable(@RequestBody DeleteStoreTableRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteStoreTable() func,phone num:{}",request.getUserPhone());
        }

        DeleteStoreTableResponse response = new DeleteStoreTableResponse();
        Result result = new Result();
        response.setResult(result);

        StoreTable storeTable = new StoreTable();
        CopyProperties.copy(storeTable,request);

        try {
            storeTableService.deleteStoreTable(storeTable);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }
}
