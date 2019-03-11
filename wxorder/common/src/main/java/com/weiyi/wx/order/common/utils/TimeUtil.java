package com.weiyi.wx.order.common.utils;

import java.text.SimpleDateFormat;
import java.util.*;

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

    //初始化日期map
    public static void formatMapDouble(String beginTime, String endTime, Map<String,Double> map)
    {
        //
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        try {
            long beginTimeMillions = simpleDateFormat.parse(beginTime).getTime();
            long endTimeMillions = simpleDateFormat.parse(endTime).getTime();

            for (;beginTimeMillions < endTimeMillions;beginTimeMillions += 1000*24*3600){
                map.put(getDate(transferLongToDate(beginTimeMillions)),0.0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取从当前时间开始往前6个月的集合
     * @return 日期集合 格式为 年-月
     * @throws Exception
     */
    public static List<String> getMonthBetween(){
        List<String> mothList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");//格式化为2017-10

        for(int i = 0;i < 6;i++){
            Calendar calendar = Calendar.getInstance();//得到Calendar实例
            calendar.add(Calendar.MONTH, -i);
            Date starDate = calendar.getTime();//得到时间赋给Data
            String stardtr = formatter.format(starDate);//使用格式化Data
            mothList.add(stardtr);
        }
        return mothList;
    }

    /**
     * 获取从当前时间开始往前7天的集合
     * @return 日期集合 格式为 年-月
     * @throws Exception
     */
    public static List<String> getDayBetween(){
        List<String> dayList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//格式化为2017-10-10

        for(int i = 0;i < 7;i++){
            Calendar calendar = Calendar.getInstance();//得到Calendar实例
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date starDate = calendar.getTime();//得到时间赋给Data
            String stardtr = formatter.format(starDate);//使用格式化Data
            dayList.add(stardtr);
        }
        return dayList;
    }
}
