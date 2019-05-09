package com.icephone.yuhao.repairerecord.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtil {


    public static String getUploadTime(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        return df.format(calendar.getTime());
    }

    public static String getShowTime(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat("时间：yyyy年MM月dd日", Locale.CHINA);
        return df.format(calendar.getTime());
    }

    public static String getCurTime(){
        Calendar calendar = Calendar.getInstance(); //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return df.format(calendar.getTime());
    }

    /**
     * 将时间转换成显示的时间
     * @param date 20180910
     * @return 2018年9月10日
     */
    public static String transferTimeToShow(String date) {
        StringBuilder str = new StringBuilder();
        str.append(date.substring(0, 4)).append("年");
        if (date.charAt(4) == '0') {
            str.append(date.substring(5, 6)).append("月");
        } else {
            str.append(date.substring(4, 6)).append("月");
        }

        if (date.charAt(6) == '0') {
            str.append(date.substring(7, 8)).append("日");
        } else {
            str.append(date.substring(6, 8)).append("日");
        }
        return str.toString();
    }

}
