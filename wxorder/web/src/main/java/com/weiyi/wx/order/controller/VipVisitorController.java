package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.dao.request.GetAllVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipVisitorListRequest;
import com.weiyi.wx.order.domain.Statistics;
import com.weiyi.wx.order.domain.StatisticsPercent;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.MenuService;
import com.weiyi.wx.order.service.api.VipVisitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/store/vip")
public class VipVisitorController
{
    private Logger logger = LoggerFactory.getLogger(VipVisitorController.class);

    @Autowired
    private VipVisitorService vipVisitorService;

    /*
    *新增会员
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddVipVisitorResponse addVip(@RequestBody AddVipVisitorRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addVip() func,VipId num:{}",request.getVipId());
        }

        AddVipVisitorResponse response = new AddVipVisitorResponse();
        Result result = new Result();
        response.setResult(result);

        VipVisitor vipVisitor = new VipVisitor();
        CopyProperties.copy(vipVisitor,request);
        try {
            vipVisitorService.addVip(vipVisitor);
        }catch (WxOrderException e){
            logger.error("addVip error:{}",e.getMsg());
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }

        return response;
    }

    /*
     *删除会员
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteVipVisitorResponse deleteVip(@RequestBody DeleteVipVisitorRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteVip() func,VipId num:{}",request.getVipId());
        }

        DeleteVipVisitorResponse response = new DeleteVipVisitorResponse();
        Result result = new Result();
        response.setResult(result);

        VipVisitor vipVisitor = new VipVisitor();
        CopyProperties.copy(vipVisitor,request);

        vipVisitorService.deleteVip(vipVisitor);

        return response;
    }

    /*
     *更新会员
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateVipVisitorListResponse updateVip(@RequestBody UpdateVipVisitorRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateVip() func,user num:{}",request.getUserPhone());
        }

        UpdateVipVisitorListResponse response = new UpdateVipVisitorListResponse();
        Result result = new Result();
        response.setResult(result);

        VipVisitor vipVisitor = new VipVisitor();
        CopyProperties.copy(vipVisitor,request);

        vipVisitorService.updateVip(vipVisitor);
        return response;
    }
    /*
     *查询会员列表
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryVipVisitorListResponse queryVipList(@RequestBody QueryVipVisitorListRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVipList() func,user num:{}",request.getUserPhone());
        }

        QueryVipVisitorListResponse response = new QueryVipVisitorListResponse();
        Result result = new Result();
        response.setResult(result);

        if (request.getCurrentPage() != null){
            request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }

        GetVipVisitorListRequest getVipVisitorListRequest = new GetVipVisitorListRequest();
        CopyProperties.copy(getVipVisitorListRequest,request);

        int total = vipVisitorService.queryVipListCount(getVipVisitorListRequest);
        List<VipVisitor> vipVisitors = vipVisitorService.queryVipList(getVipVisitorListRequest);
        if (vipVisitors != null && vipVisitors.size() > 0){
            response.setTotal(total);
            response.setVipVisitors(vipVisitors.toArray(new VipVisitor[vipVisitors.size()]));
        }
        return response;
    }

    /*
     *查询会员数量
     */
    @RequestMapping(value = "/query/list/count",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryVipVisitorListCountResponse queryVipList(@RequestBody QueryVipVisitorListCountRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVipList() func,user num:{}",request.getUserPhone());
        }

        QueryVipVisitorListCountResponse response = new QueryVipVisitorListCountResponse();
        Result result = new Result();
        response.setResult(result);

        GetVipVisitorListRequest getVipVisitorListRequest = new GetVipVisitorListRequest();
        CopyProperties.copy(getVipVisitorListRequest,request);

        int total = vipVisitorService.queryVipListCount(getVipVisitorListRequest);
        response.setTotal(total);

        return response;
    }

    /*
     *查询会员消费记录
     */
    @RequestMapping(value = "/query/consume",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryVipConsumeResponse queryVipConsume(@RequestBody QueryVipConsumeRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVipConsume() func,user num:{}",request.getUserPhone());
        }

        QueryVipConsumeResponse response = new QueryVipConsumeResponse();
        Result result = new Result();
        response.setResult(result);

        if (request.getCurrentPage() != null){
            request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }

        GetVipConsumeRequest getVipConsumeRequest = new GetVipConsumeRequest();
        CopyProperties.copy(getVipConsumeRequest,request);

        int total = vipVisitorService.queryVipConsumeCount(getVipConsumeRequest);
        List<StoreOrder> storeOrders = vipVisitorService.queryVipConsumeList(getVipConsumeRequest);
        if (storeOrders != null && storeOrders.size() > 0){
            response.setTotal(total);
            response.setStoreOrders(storeOrders.toArray(new StoreOrder[storeOrders.size()]));
        }
        return response;
    }

    /*
     *查询会员新增数量
     */
    @RequestMapping(value = "/query/addNum",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryVipAddNumResponse queryVipAddNum(@RequestBody QueryVipAddNumRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVipAddNum() func,user num:{}",request.getUserPhone());
        }

        QueryVipAddNumResponse response = new QueryVipAddNumResponse();
        Result result = new Result();
        response.setResult(result);

        GetVipVisitorListRequest getVipVisitorListRequest = new GetVipVisitorListRequest();
        CopyProperties.copy(getVipVisitorListRequest,request);

        List<VipVisitor> vipVisitors = vipVisitorService.queryVipList(getVipVisitorListRequest);

        Map<String,Integer> map = new HashMap<String,Integer>();
        //初始化map
        TimeUtil.formatMap(request.getBeginTime(),request.getEndTime(),map);
        if (vipVisitors != null && vipVisitors.size() > 0){
            for (VipVisitor vipVisitor : vipVisitors){
                String date = TimeUtil.getDate(vipVisitor.getCreateTime());
                Integer total = map.get(date);
                if (total == null){
                    map.put(date,1);
                }else {
                    map.put(date,total + 1);
                }
            }
        }
        //遍历map
        Iterator iterator = map.entrySet().iterator();
        List<Statistics<Integer>> statisticsList = new ArrayList<Statistics<Integer>>();
        while(iterator.hasNext())
        {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
            Statistics<Integer> statistics = new Statistics<Integer>();

            statistics.setYear(entry.getKey());
            statistics.setValue(entry.getValue());

            statisticsList.add(statistics);
        }
        response.setStatistics(statisticsList.toArray(new Statistics[statisticsList.size()]));
        return response;
    }

    /*
     *查询所有会员在某段时间内的消费金额
     */
    @RequestMapping(value = "/query/allVip/consume",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryAllVipConsumeResponse queryAllVipConsume(@RequestBody QueryAllVipConsumeRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllVipConsume() func,user num:{}",request.getUserPhone());
        }

        QueryAllVipConsumeResponse response = new QueryAllVipConsumeResponse();
        Result result = new Result();
        response.setResult(result);

        GetAllVipConsumeRequest getAllVipConsumeRequest = new GetAllVipConsumeRequest();
        CopyProperties.copy(getAllVipConsumeRequest,request);

        List<StoreOrder> storeOrders = vipVisitorService.queryAllVipConsume(getAllVipConsumeRequest);

        Map<String,Double> map = new HashMap<String,Double>();
        //初始化map
        TimeUtil.formatMapDouble(request.getBeginTime(),request.getEndTime(),map);
        if (storeOrders != null && storeOrders.size() > 0){
            for (StoreOrder storeOrder : storeOrders){
                String date = TimeUtil.getDate(storeOrder.getCreateTime());
                Double total = map.get(date);
                if (total == null){
                    map.put(date,storeOrder.getRealAmount());
                }else {
                    map.put(date,total + storeOrder.getRealAmount());
                }
            }
        }
        //遍历map
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
        response.setStatistics(statisticsList.toArray(new Statistics[statisticsList.size()]));
        return response;
    }

    /*
     *查询所有会员在某段时间内的消费金额所占比例
     */
    @RequestMapping(value = "/query/allVip/consume/percent",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryAllVipConsumePercentResponse queryAllVipConsumePercent(@RequestBody QueryAllVipConsumePercentRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllVipConsumePercent() func,user num:{}",request.getUserPhone());
        }

        QueryAllVipConsumePercentResponse response = new QueryAllVipConsumePercentResponse();
        Result result = new Result();
        response.setResult(result);

        GetAllVipConsumeRequest getAllVipConsumeRequest = new GetAllVipConsumeRequest();
        CopyProperties.copy(getAllVipConsumeRequest,request);

        //获取会员消费比例，并保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double vipPercent = Double.valueOf(decimalFormat.format(vipVisitorService.queryAllVipConsumePercent(getAllVipConsumeRequest)));

        List<StatisticsPercent> statisticsPercentList = new ArrayList<StatisticsPercent>(2);

        StatisticsPercent statisticsPercentVip = new StatisticsPercent();
        statisticsPercentVip.setItem("会员消费");
        statisticsPercentVip.setPercent(vipPercent);

        StatisticsPercent statisticsPercentNormal = new StatisticsPercent();
        statisticsPercentNormal.setItem("普通消费");
        statisticsPercentNormal.setPercent(1 - vipPercent);

        statisticsPercentList.add(statisticsPercentVip);
        statisticsPercentList.add(statisticsPercentNormal);

        response.setStatisticsPercents(statisticsPercentList.toArray(new StatisticsPercent[statisticsPercentList.size()]));
        return response;
    }
}
