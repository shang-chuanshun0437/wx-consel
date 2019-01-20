package com.weiyi.wx.order.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 解码成字节数组
 */
public class Base64
{
    //Base64解码
    public static byte[] decode(String base64Data)
    {
        byte[] bytes = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bytes = decoder.decodeBuffer(base64Data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }
}
