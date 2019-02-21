package com.weiyi.wx.order.common.constant;

public class Constant
{
    public static class User
    {
        //验证码前缀
        public static final String VERIFY_CODE = ".verify_code";

        //key : token
        public static final String TOKEN = "token";
    }

    //分页查询，每页的数量
    public static final int PAGE_SIZE = 10;

    //启用状态
    public static final int ENABLE = 0;

    //禁用状态
    public static final int DISABLE = 0;

    //成功
    public static final int SUCCESS = 1;

    //失败
    public static final int FAIL = 2;

    //餐桌状态 1 空闲
    public static final int IDLE = 1;

    //餐桌状态 3 正在就餐
    public static final int DINNERING = 2;

    //菜图片存放的虚拟根目录
    public static final String IMG_VIRTUAL_DIR_ROOT = "http://192.168.1.4:8080/images/foodimages/";

    //菜图片存放根目录
    public static final String FOOD_IMG_DIR_ROOT = "/usr/data/images/foodimages/";

    //二维码存放的虚拟根目录
    public static final String QRCODE_VIRTUAL_DIR_ROOT = "http://192.168.1.4:8080/images/qrcodeimages/";

    //二维码存放根目录
    public static final String QRCODE_IMG_DIR_ROOT = "/usr/data/images/qrcodeimages/";

    //手机app的URL
    public static final String QRCODE_APP_URL = "http://192.168.1.4:8080/app/";

    //食物状态 1 未售罄
    public static final int NOT_SELL_OUT = 1;

    //食物状态 2 售罄
    public static final int SELL_OUT = 2;

    //支付方式 1 未支付
    public static final int NO_PAY = 1;

    //支付方式 2 前台支付
    public static final int FRONT_DESK_PAY = 2;

    //支付方式 3 支付宝支付
    public static final int ZHI_FU_BAO_PAY = 3;

    //支付方式 4 微信支付
    public static final int WEI_XI_PAY = 4;

    //订单状态 2 已支付订单（通过手机支付，还在就餐）
    public static final int PAY_ORDER = 2;

    //支付状态 3 订单结束（客人已离店）
    public static final int FINISH_ORDER = 3;

    //点菜方式1 点菜   2加菜
    public static final int ORDER_TYPE_FIRST = 1;

    //点菜方式1 点菜   2加菜
    public static final int ORDER_TYPE_SECOND = 2;
}
