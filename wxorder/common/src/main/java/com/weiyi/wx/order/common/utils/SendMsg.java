package com.weiyi.wx.order.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMsg
{
	//智能匹配模板发送接口的http地址
	private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

	//编码格式
	private static String ENCODING = "UTF-8";

	//APIKEY
	private static String APIKEY = "6ddc78caf0504dd5f611936deddbc666";

	/*入参:
	 * @phone 手机号
	 *@vipNo 会员编号
	 * @money 转账金额
	 * @cardNum 银行卡号
	 */
	public static String transferAccounts(String phone,String vipNo,float money,String cardNum)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】恭喜会员编号：" + vipNo +
				" 通过申请代理商复审，请于收到此短信当天24:00前，务必卡对卡转账USD " + money +
				"元到公司卡号（卡号：" + cardNum + "），逾时未转账者，将有200积分的罚款！";
		String gangAoTai = "【聯盈商城】恭喜會員編號：" + vipNo +
				" 通過申請代理商複審，請於收到此簡訊當天24:00前，務必卡對卡轉帳USD " + money +
				"元到公司卡號（卡號：" + cardNum + "），逾時未轉帳者，將有200積分的罰鍰！";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	/*入参:
	 * @phone 手机号
	 *@vipNo 会员编号
	 */
	public static String auditPass(String phone,String vipNo)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】恭喜会员编号：" + vipNo + " 升级代理商审核已通过，正式成为代理商。";
		String gangAoTai = "【聯盈商城】恭喜會員編號：" + vipNo + " 升級代理商審核已通過，正式成為代理商。";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	/*入参:
	 * @phone 手机号
	 *@vipNo 会员编号
	 */
	public static String receiveCard(String phone,String vipNo)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】恭喜会员编号：" + vipNo +
				" 会员基本资料审核通过，请注意近期将会收到卡片，收到卡片后，请立刻通知您的代理商，协助您完成KYC认证及绑定卡片作业。";
		String gangAoTai = "【聯盈商城】恭喜會員編號：" + vipNo +
				" 會員基本資料審核通過，請注意近期將會收到卡片，收到卡片後，請立刻通知您的代理商，協助您完成KYC認證及綁定卡片作業。";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	/*入参:
	 * @phone 手机号
	 *@vipNo 会员编
	 * *@vipNo 缴费金额
	 */
	public static String agent(String phone,String vipNo,float money)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】您复试的申请代理商资格已通过， 缴费" + money +
				"元未完成，会员编号：" + vipNo + " 请尽快缴纳。";
		String gangAoTai = "【聯盈商城】您複試的申請代理商資格已通過，繳費 " + money +
				"元未完成，會員編號：" + vipNo + " 請請儘速繳納。";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	/*入参:
	 * @phone 手机号
	 *@password 临时密码
	 */
	public static String temporaryPassword(String phone,String password)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】 您的临时密码为 " + password +
				"，请用您的身份证号及临时密码登录会员中心，登录后请立即更改您的密码。" +
				"提醒您：请勿将此临时密码提供给其他人以保障您的使用安全。";
		String gangAoTai = "【聯盈商城】您的臨時密碼為 " + password +
				"，請用您的身份証號及臨時密碼登入會員中心，登入後請立即更改您的密碼。" +
				"提醒您：請勿將此臨時密碼提供給其他人以保障您的使用安全。";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	/*入参:
	 * @phone 手机号
	 *@vipNo 会员编号
	 */
	public static String vip(String phone,String vipNo)
	{
		//发送的具体短信内容
		String mainLand = "【联盈商城】恭喜会员编号：" + vipNo + " 会员数据审核完成，正式成为会员，欢迎进入网站。";
		String gangAoTai = "【聯盈商城】恭喜會員編號：" + vipNo + " 會員資料審核完成，正式成為會員。歡迎進入網站。";
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phoneType = 1;
		}else {
			return "the phone num is error.";
		}

		//组装短信内容
		String content = "";

		if (phoneType == 0) {
			content = mainLand;
		}else if (phoneType == 1){
			content = gangAoTai;
		}
		try {
			String result = sendSms(content,phone);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "unknown error.";
	}

	private static String sendSms(String content,String mobile) throws IOException {
		Map< String, String > params = new HashMap< String, String >();
		params.put("apikey", APIKEY);
		params.put("text", content);
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}

	private static String post(String url, Map < String, String > paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair >();
				for (Map.Entry < String, String > param: paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(),param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList,ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}
