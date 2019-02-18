package com.weiyi.wx.order.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TimeUtil
{
    //返回年月日时分秒字符串
    public static String getCurrentTime()
    {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String time = f.format(now);

        return time;
    }

    //去掉后面的.0
    public static String getDateTime(String timestamp)
    {
        if(timestamp == null)
        {
            return null;
        }
        if(!timestamp.endsWith(".0"))
        {
            return timestamp;
        }
        return timestamp.substring(0,timestamp.length() - 2);
    }

   /**
         * 把long 转换成 日期 再转换成String类型
         */
    public static String transferLongToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    //获取年月日
    public static String getDate(String time)
    {
        return time.substring(0,10);
    }

    //初始化日期map
    public static void formatMap(String beginTime, String endTime, Map<String,Integer> map)
    {
        //
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        try {
            long beginTimeMillions = simpleDateFormat.parse(beginTime).getTime();
            long endTimeMillions = simpleDateFormat.parse(endTime).getTime();

            for (;beginTimeMillions < endTimeMillions;beginTimeMillions += 1000*24*3600){
                map.put(getDate(transferLongToDate(beginTimeMillions)),0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
