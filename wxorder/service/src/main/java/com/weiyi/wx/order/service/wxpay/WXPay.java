package com.weiyi.wx.order.service.wxpay;

import com.weiyi.wx.order.common.constant.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import static com.weiyi.wx.order.service.wxpay.WXPayConstants.USER_AGENT;

public class WXPay {
    private String appId;

    private String mchId;

    private String openId;

    private String orderId;

    private String key;

    private double totalFee;

    public WXPay(String appId,String mchId,String openId,String orderId,String key,double totalFee){
        this.appId = appId;
        this.mchId = mchId;
        this.openId = openId;
        this.orderId = orderId;
        this.key = key;
        this.totalFee = totalFee;
    }

    public Map<String, String> preparePay(){
        try {
            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            paraMap.put("appid", appId);//微信支付分配的公众账号ID
            paraMap.put("body", "阿甘科技-微信扫码点餐");//商品简单描述，该字段请按照规范传递
            paraMap.put("mch_id", mchId);//微信支付分配的商户号
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串，长度要求在32位以内。推荐随机数生成算法
            paraMap.put("openid", openId);//此参数为微信用户在商户对应appid下的唯一标识
            paraMap.put("out_trade_no", orderId);//订单号
            paraMap.put("spbill_create_ip", Constant.CONSOLE_SERVER_IP);//调用微信支付API的机器IP
            paraMap.put("total_fee", String.valueOf(totalFee));//订单总金额，单位为分
            paraMap.put("trade_type", "JSAPI");//JSAPI -JSAPI支付
            // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            paraMap.put("notify_url", Constant.NOTIFY_URL);
            //key为商户平台设置的密钥
            String sign = WXPayUtil.generateSignature(paraMap, key);//通过签名算法计算得出的签名值
            paraMap.put("sign", sign);
            String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式

            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(unifiedorder_url);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
            httpPost.setConfig(requestConfig);

            StringEntity postEntity = new StringEntity(xml, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("User-Agent", USER_AGENT + " " + mchId);
            httpPost.setEntity(postEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            String str = EntityUtils.toString(httpEntity, "UTF-8");
            Map<String, String> resultMap = WXPayUtil.xmlToMap(str);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
