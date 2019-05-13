package com.weiyi.wx.order.common.constant;

public class ErrorCode
{
    //验证码不正确
    public static final int VERIFY_ERROR = 1000;

    //用户已存在
    public static final int USER_EXIST = 1001;

    //登录时，用户不存在
    public static final int USER_NOT_EXIST = 1002;

    //登录时，密码错误
    public static final int PASSWORD_ERROR = 1003;

    //用户角色已存在
    public static final int ROLE_USER_EXIST = 1004;

    //设备已存在
    public static final int DEVICE_EXIST = 1005;

    //设备已存在管理员，不允许后台维护人员修改
    public static final int OWNER_USER_EXIST = 1006;

    //设备不存在或设备已存在管理员，则不允许绑定
    public static final int BIND_DEVICE_ERROR = 1007;

    //解绑设备失败
    public static final int UNBIND_DEVICE_ERROR = 1008;

    //管理员解绑解绑设备，设备下还存在其他用户，解绑失败
    public static final int OTHER_USERS_EXIST = 1009;

    //设备不存在
    public static final int DEVICE_NOT_EXIST = 1010;

    //设备不存在
    public static final int DEVICE_NUM_ERROR = 1011;

    //参数错误
    public static final int PARAMETER_ERROR = 1012;

    //店铺数量已达到最大值
    public static final int STORE_NUM_LIMIT = 1013;

    //店铺不存在
    public static final int STORE_NOT_EXIST = 1014;

    //店铺不属于此用户
    public static final int STORE_NOT_RIGHT = 1015;

    //店铺中已存在该桌号
    public static final int STORE_TABLE_EXIST = 1016;

    //该餐桌正在使用中
    public static final int STORE_TABLE_USING = 1023;

    //店铺中不存在该桌号
    public static final int STORE_TABLE_NOT_EXIST = 1017;

    //店铺中菜单已存在该菜编号
    public static final int FOOD_EXIST = 1018;

    //上传图片失败
    public static final int UPLOAD_IMAGE_FAIL = 1019;

    //店铺中菜单不存在该菜编号
    public static final int FOOD_NOT_EXIST = 1020;

    //订单不存在
    public static final int ORDER_NOT_EXIST = 1021;

    //订单已付款
    public static final int ORDER_HAVE_PAY = 1022;

    //会员已存在
    public static final int VIP_EXIST = 1023;

    //会员不存在
    public static final int VIP_NOT_EXIST = 1024;

    //用户无支付设置
    public static final int PAY_SETTING_NOT_EXIST = 1025;
}
