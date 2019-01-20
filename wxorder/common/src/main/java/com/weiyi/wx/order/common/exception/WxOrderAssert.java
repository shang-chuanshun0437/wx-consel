package com.weiyi.wx.order.common.exception;

public class WxOrderAssert {

    public static void isTrue(boolean expression, int code,String msg) {
        if (!expression) {
            throw new WxOrderException(code,msg);
        }
    }

    public static void isTrue(boolean expression, int code) {
        if (!expression) {
            throw new WxOrderException(code);
        }
    }

}
