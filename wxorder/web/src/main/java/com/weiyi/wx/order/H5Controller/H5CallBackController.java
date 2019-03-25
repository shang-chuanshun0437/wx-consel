package com.weiyi.wx.order.H5Controller;

import com.alibaba.fastjson.JSONObject;
import com.weiyi.wx.order.common.rabbitmq.RabbitSendManage;
import com.weiyi.wx.order.service.api.StoreOrderService;
import com.weiyi.wx.order.service.impl.StoreOrderServiceSpi;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;
import com.weiyi.wx.order.service.wxpay.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/h5/callback")
public class H5CallBackController
{
    private Logger logger = LoggerFactory.getLogger(H5CallBackController.class);

    @Autowired
    private StoreOrderService storeOrderService;

    /*
    *微信扫码订单：微信支付回调函数
     * 此函数会被执行多次，如果支付状态已经修改为已支付，则下次再调的时候判断是否已经支付，
     * 如果已经支付了，则什么也执行
    */
    @RequestMapping(value = "/wx",method = {RequestMethod.POST})
    @ResponseBody
    public String wxCallBack(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter wxCallBack() func");
        }
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultXml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = WXPayUtil.xmlToMap(resultXml);
        outSteam.close();
        inStream.close();

        Map<String,String> return_data = new HashMap<String,String>();

        if ( params.get("return_code") == "SUCCESS"){
            //支付成功
            //根据订单号，获取订单
            String out_trade_no = params.get("out_trade_no");
            AddStoreOrderRequest addStoreOrderRequest = StoreOrderServiceSpi.addStoreOrderRequestMap.get(out_trade_no);
            if (addStoreOrderRequest != null){
                if (addStoreOrderRequest.getTotalAmount() == Double.valueOf(params.get("out_trade_no")) / 100){
                    String jsonString = JSONObject.toJSONString(addStoreOrderRequest);
                    //存入消息队列
                    RabbitSendManage.topicSendMsg(JSONObject.parseObject(jsonString));
                    //从map中移除订单
                    StoreOrderServiceSpi.addStoreOrderRequestMap.remove(out_trade_no);
                }
            }
        }
        return_data.put("return_code", "SUCCESS");
        return_data.put("return_msg", "OK");
        return WXPayUtil.mapToXml(return_data);
    }
}
