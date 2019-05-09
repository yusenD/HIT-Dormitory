package com.icephone.yuhao.repairerecord.Util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    /**
     * Toast实例，用于对本页出现的所有Toast进行处理
     */
    private static Toast myToast;

    public static void showToastShort(Context context, String msg) {
        if (myToast != null) {
            myToast.cancel();
        }
        myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        myToast.show();
    }
}
