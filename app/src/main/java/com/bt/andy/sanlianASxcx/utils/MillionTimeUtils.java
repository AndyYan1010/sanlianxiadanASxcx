package com.bt.andy.sanlianASxcx.utils;

import java.util.Calendar;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/28 11:24
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MillionTimeUtils {
    //获取时间戳
    public static long getTime() {
        Calendar calendar = Calendar.getInstance();// 获取当前日历对象
        long unixTime = calendar.getTimeInMillis();// 获取当前时区下日期时间对应的时间戳
        return unixTime;
    }
}
