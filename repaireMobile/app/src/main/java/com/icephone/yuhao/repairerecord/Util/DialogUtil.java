package com.icephone.yuhao.repairerecord.Util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.icephone.yuhao.repairerecord.R;

import java.util.Calendar;
import java.util.List;

public class DialogUtil {


    public static void showAlertDialog(Context context,
                                       @LayoutRes int resId,
                                       String title,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("确定", positiveListener);
        builder.setNegativeButton("取消", negativeListener);
        AlertDialog dialog = builder.create();
        View view = View.inflate(context, resId, null);
        dialog.setTitle(title);
        dialog.setView(view);
        dialog.show();
    }

    public static void showAlertDialog(Context context,
                                       String title,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("确定", positiveListener);
        builder.setNegativeButton("取消", negativeListener);
        AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.show();
    }

    public static void showEditTextDialog(Context context,
                                       String title,
                                       View view,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("确定", positiveListener);
        builder.setNegativeButton("取消", negativeListener);
        AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setView(view);
        dialog.show();
    }



    public static void showSingleChooseDialog(Context context,
                                              String title,
                                              String[] item,
                                              DialogInterface.OnClickListener positiveListener,
                                              DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(item,-1,listener);
        builder.setPositiveButton("确定", positiveListener);
        AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.show();

    }

    public static void showMultiChooseDialog(Context context,
                                              String title,
                                              String[] item,
                                              boolean[] itemChecked,
                                              DialogInterface.OnClickListener positiveListener,
                                              DialogInterface.OnMultiChoiceClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMultiChoiceItems(item,itemChecked,listener);
        builder.setPositiveButton("确定", positiveListener);
        AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.show();

    }

    public static void showDateDialog(Context context, Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}
