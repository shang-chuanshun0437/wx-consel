package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.request.GetStoresRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.AddStoreRequest;
import com.weiyi.wx.order.request.DeleteStoreRequest;
import com.weiyi.wx.order.request.QueryStoresRequest;
import com.weiyi.wx.order.request.UpdateStoreRequest;
import com.weiyi.wx.order.response.AddStoreResponse;
import com.weiyi.wx.order.response.DeleteStoreResponse;
import com.weiyi.wx.order.response.QueryStoresResponse;
import com.weiyi.wx.order.response.UpdateStoreResponse;
import com.weiyi.wx.order.service.api.StoreService;
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
@RequestMapping("/store")
public class StoreController
{
    private Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    /*
    *添加店铺
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddStoreResponse addStore(@RequestBody AddStoreRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addStore() func,phone num:{}",request.getUserPhone());
        }

        AddStoreResponse response = new AddStoreResponse();
        Result result = new Result();
        response.setResult(result);

        Store store = new Store();
        store.setAddress(request.getAddress());
        store.setUserPhone(request.getUserPhone());
        store.setStoreName(request.getStoreName());

        try {
            storeService.addStore(store);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *查询店铺
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryStoresResponse queryStore(@RequestBody QueryStoresRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryStore() func,phone num:{}",request.getUserPhone());
        }

        QueryStoresResponse response = new QueryStoresResponse();
        Result result = new Result();
        response.setResult(result);

        //填充查询参数
        if(request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0){
            request.setCurrentPage(1);
        }

        GetStoresRequest getStoresRequest = new GetStoresRequest();

        CopyProperties.copy(getStoresRequest,request);
        getStoresRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        //查询数量
        int total = storeService.queryStoresCount(getStoresRequest);

        List<Store> stores = storeService.queryStores(getStoresRequest);
        if(stores != null && stores.size() > 0){
            response.setStores(stores.toArray(new Store[stores.size()]));
        }
        response.setTotal(total);

        return response;
    }
    /*
     *更新店铺
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateStoreResponse updateStore(@RequestBody UpdateStoreRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateStore() func,phone num:{}",request.getUserPhone());
        }

        UpdateStoreResponse response = new UpdateStoreResponse();
        Result result = new Result();
        response.setResult(result);

        try {
            Store store = new Store();
            store.setUserPhone(request.getUserPhone());
            store.setStoreId(request.getStoreId());
            store.setAddress(request.getAddress());
            store.setStoreName(request.getStoreName());

            storeService.updateStore(store);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }
    /*
     *删除店铺
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteStoreResponse deleteStore(@RequestBody DeleteStoreRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteStore() func,phone num:{}",request.getUserPhone());
        }

        DeleteStoreResponse response = new DeleteStoreResponse();
        Result result = new Result();
        response.setResult(result);

        try {
            Store store = new Store();
            store.setUserPhone(request.getUserPhone());
            store.setStoreId(request.getStoreId());

            storeService.deleteStore(store);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }
}
