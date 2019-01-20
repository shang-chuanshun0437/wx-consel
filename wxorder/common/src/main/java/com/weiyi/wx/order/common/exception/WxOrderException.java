package com.weiyi.wx.order.common.exception;

public class WxOrderException extends RuntimeException
{
    private String msg;

    private int code;

    public WxOrderException(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public WxOrderException(int code)
    {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "WxOrderException{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
