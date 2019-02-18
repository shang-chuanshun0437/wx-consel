package com.weiyi.wx.order.common.utils;

public class TestSendMsg
{
    public static void main(String[] args) throws Exception
    {

        String phone = "+8618753137390"; //大陆手机号
        //String phone = "+85261386341"; //港澳台手机号

        String result = SendMsg.transferAccounts(phone,"53233123123d",4123123135.0f,"1131231234534532");
        if (result.contains("发送成功")){
            //短信发送成功
            System.out.println("**********************发送成功*******************");
        }
        //String result2 = SendMsg.auditPass(phone,"111");
        //String result3 = SendMsg.receiveCard(phone,"111");
        //String result4 = SendMsg.agent(phone,"111",25.0f);
        //String result5 = SendMsg.temporaryPassword(phone,"123456");
        //String result6 = SendMsg.vip(phone,"111");
        System.out.println(result);
        //System.out.println(result2);
        //System.out.println(result3);
        //System.out.println(result4);
        //System.out.println(result5);
        //System.out.println(result6);
    }
}
