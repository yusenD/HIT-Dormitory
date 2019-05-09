package com.icephone.yuhao.repairerecord.Util;

import java.util.List;

public class StringFormatUtil {

    public static String ListToString(List<String> stringList) {
        if (stringList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : stringList) {
            builder.append(str).append("，");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String ListToString(List<String> stringList,int[] num) {
        if (stringList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : stringList) {
            builder.append(str).append("，");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
