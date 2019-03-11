package com.weiyi.wx.order.common.utils;

import java.util.*;

public class SalesUtil
{
    //按月统计，初始化数组
    public static void statisticsByMonthInit(Map<String,Double> statistics)
    {
        List<String> moths = TimeUtil.getMonthBetween();
        for (String moth : moths){
            statistics.put(moth,0.0);
        }
    }

    //按月统计，初始化数组
    public static void statisticsByDayInit(Map<String,Double> statistics)
    {
        List<String> days = TimeUtil.getDayBetween();
        for (String moth : days){
            statistics.put(moth,0.0);
        }
    }
}
