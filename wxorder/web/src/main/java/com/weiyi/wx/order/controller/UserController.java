package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.SalesUtil;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.request.GetPeriodSalesRequest;
import com.weiyi.wx.order.dao.request.GetStoresRequest;
import com.weiyi.wx.order.dao.request.GetUserAllSalesRequest;
import com.weiyi.wx.order.domain.SalesStatistics;
import com.weiyi.wx.order.domain.Statistics;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.StoreService;
import com.weiyi.wx.order.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

/*
* 商家操作
*/
@Controller
@RequestMapping("/user")
public class UserController
{
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private RedisClient redisClient;
    /*
     *获取用户信息
     */
    @RequestMapping(value = "/query/userInfo",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryUserInfoResponse queryUserInfo(@RequestBody QueryUserInfoRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserInfo() func,phone num:{}",request.getUserPhone());
        }

        QueryUserInfoResponse response = new QueryUserInfoResponse();
        Result result = new Result();
        response.setResult(result);

        User dbUser = userService.queryUserByPhone(request.getUserPhone());
        if (dbUser == null){
            result.setRetCode(ErrorCode.USER_NOT_EXIST);
            result.setRetMsg("user not exist.");
            return response;
        }
        dbUser.setPassword(null);
        response.setUser(dbUser);
        return response;
    }

    /*
     *更新用户信息
     */
    @RequestMapping(value = "/update/userInfo",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateUserInfoResponse updateUserInfo(@RequestBody UpdateUserInfoRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUserInfo() func,phone num:{}",request.getUserPhone());
        }

        UpdateUserInfoResponse response = new UpdateUserInfoResponse();
        Result result = new Result();
        response.setResult(result);

        User user = new User();
        CopyProperties.copy(user,request);
        userService.updateUser(user);

        return response;
    }

    /*
     * 修改用户密码
     */
    @RequestMapping(value = "/modifyPassword",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public ModifyPasswordResponse modifyPassword(@RequestBody ModifyPasswordRequest request)
    {
        ModifyPasswordResponse response = new ModifyPasswordResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter modifyPassword() func ,the user is:{}", request.getUserPhone());
        }

        //根据手机号查询用户信息
        User dbUser = userService.queryUserByPhone(request.getUserPhone());
        if(dbUser == null || !dbUser.getPassword().equals(request.getOldPassword()))
        {
            result.setRetCode(ErrorCode.USER_NOT_EXIST);
            result.setRetMsg("user phone or password error.");
            return response;
        }

        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setPassword(request.getNewPassword());
        user.setToken(token);
        user.setUserPhone(request.getUserPhone());
        //将新密码和token存入数据库
        userService.updatePassword(user);

        //将token存入redis缓存
        redisClient.hset(request.getUserPhone() + "", Constant.User.TOKEN,user.getToken());

        response.setToken(token);

        return response;
    }
    /*
     *查询所有店铺的当月销售总额
     */
    @RequestMapping(value = "/query/all/sales",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryUserAllSalesResponse queryUserAllSales(@RequestBody QueryUserAllSalesRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserAllSales() func,phone num:{}",request.getUserPhone());
        }

        QueryUserAllSalesResponse response = new QueryUserAllSalesResponse();
        Result result = new Result();
        response.setResult(result);

        GetUserAllSalesRequest getUserAllSalesRequest = new GetUserAllSalesRequest();
        CopyProperties.copy(getUserAllSalesRequest,request);

        double total = userService.queryUserAllSales(getUserAllSalesRequest);
        response.setTotal(total);

        return response;
    }

    /*
     *查询店铺指定时间段的销售额度
     */
    @RequestMapping(value = "/query/time/period/sales",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryPeriodSalesResponse queryUserPeriodSales(@RequestBody QueryPeriodSalesRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserPeriodSales() func,phone num:{}",request.getUserPhone());
        }

        QueryPeriodSalesResponse response = new QueryPeriodSalesResponse();
        Result result = new Result();
        response.setResult(result);

        String beginTime = "";
        if (request.getFlag() == 1){
            beginTime = TimeUtil.getMonthBetween().get(5) + "-01 00:00:00";
        }else {
            beginTime = TimeUtil.getDayBetween().get(6) + " 00:00:00";
        }
        GetPeriodSalesRequest getPeriodSalesRequest = new GetPeriodSalesRequest();
        getPeriodSalesRequest.setUserPhone(request.getUserPhone());
        getPeriodSalesRequest.setBeginTime(beginTime);
        getPeriodSalesRequest.setEndTime(TimeUtil.transferLongToDate(System.currentTimeMillis()));

        //获取所有店铺的销售额度
        Map<String,Double> allSales = new HashMap<String, Double>();
        //初始化map
        if (request.getFlag() == 1){
            SalesUtil.statisticsByMonthInit(allSales);
        }else {
            SalesUtil.statisticsByDayInit(allSales);
        }
        List<StoreOrder> allStoreOrders = userService.queryPeriodSales(getPeriodSalesRequest);
        if (allStoreOrders != null && allStoreOrders.size() > 0){
            if (request.getFlag() == 1){
                statisticsByMonth(allSales,allStoreOrders);
            }else {
                statisticsByDay(allSales,allStoreOrders);
            }
        }
        List<Statistics<Double>> allSalesList = analysisMap(allSales);
        //按日期排序
        Collections.sort(allSalesList);
        SalesStatistics allSalesStatistics = new SalesStatistics();
        allSalesStatistics.setStatistics(allSalesList.toArray(new Statistics[allSalesList.size()]));
        response.setAllSales(allSalesStatistics);
        /*
        * 获取每个店铺的销售额度
        */
        //获取所有的店铺
        GetStoresRequest getStoresRequest = new GetStoresRequest();
        getStoresRequest.setUserPhone(request.getUserPhone());
        List<Store> stores = storeService.queryStores(getStoresRequest);
        if (stores != null && stores.size() > 0){
            List<SalesStatistics> eachSalesStatisticsList = new ArrayList<SalesStatistics>();
            for (Store store : stores){
                getPeriodSalesRequest.setStoreId(store.getStoreId());
                List<StoreOrder> eachStoreOrders = userService.queryPeriodSales(getPeriodSalesRequest);
                Map<String,Double> eachSales = new HashMap<String, Double>();

                if (request.getFlag() == 1){
                    SalesUtil.statisticsByMonthInit(eachSales);
                    statisticsByMonth(eachSales,eachStoreOrders);
                }else {
                    SalesUtil.statisticsByDayInit(eachSales);
                    statisticsByDay(eachSales,eachStoreOrders);
                }
                List<Statistics<Double>> eachSalesList = analysisMap(eachSales);
                Collections.sort(eachSalesList);
                SalesStatistics salesStatistics = new SalesStatistics();
                salesStatistics.setStoreName(store.getStoreName());
                salesStatistics.setStatistics(eachSalesList.toArray(new Statistics[eachSalesList.size()]));

                eachSalesStatisticsList.add(salesStatistics);
            }
            response.setEachStoreSales(eachSalesStatisticsList.toArray(new SalesStatistics[eachSalesStatisticsList.size()]));
        }

        return response;
    }

    //按照月统计，最近6个月的销售记录
    private void statisticsByMonth(Map<String,Double> statistics,List<StoreOrder> storeOrders){
        for (StoreOrder storeOrder : storeOrders){
            double money = statistics.get(storeOrder.getCreateTime().substring(0,7));
            statistics.put(storeOrder.getCreateTime().substring(0,7),money + storeOrder.getRealAmount());
        }
    }

    //按照天统计，最近7天的销售记录
    private void statisticsByDay(Map<String,Double> statistics,List<StoreOrder> storeOrders){
        for (StoreOrder storeOrder : storeOrders){
            double money = statistics.get(storeOrder.getCreateTime().substring(0,10));
            statistics.put(storeOrder.getCreateTime().substring(0,10),money + storeOrder.getRealAmount());
        }
    }

    //遍历map，转换成相应的类
    private List<Statistics<Double>> analysisMap(Map<String,Double> map)
    {
        Iterator iterator = map.entrySet().iterator();
        List<Statistics<Double>> statisticsList = new ArrayList<Statistics<Double>>();
        while(iterator.hasNext())
        {
            Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iterator.next();
            Statistics<Double> statistics = new Statistics<Double>();

            statistics.setYear(entry.getKey());
            statistics.setValue(entry.getValue());

            statisticsList.add(statistics);
        }
        return statisticsList;
    }
}
