package com.icephone.yuhao.repairerecord.Util;

import android.content.Context;

import com.icephone.yuhao.repairerecord.bean.LoginBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;

public class UserInfoUtil {

    private final static String KEY_USER_ID = "id";
    private final static String KEY_USER_NICK_NAME = "nickname";
    private final static String KEY_USER_ACCOUNT = "account";
    private final static String KEY_USER_PASSWORD = "password";
    private final static String KEY_USER_PERMISSION = "limit";
    private final static String KEY_DORMITORY = "dormitory"; //学生公寓名
    private final static String KEY_SITE_NAME = "site_name"; //学生宿舍号

    public final static String LIMIT_SUPER_MANAGER = "总管理员";
    public final static String LIMIT_STUDENT = "学生";
    public final static String LIMIT_REPAIR_MAN = "维修人员";

    public static void saveUserInfo(Context context, LoginBean.DataBean bean) {
        SharedPerferenceUtils.setParam(context, KEY_USER_ID, bean.get_id());
        SharedPerferenceUtils.setParam(context, KEY_USER_ACCOUNT, bean.getAccount());
        SharedPerferenceUtils.setParam(context,KEY_USER_PASSWORD,bean.getPassword());
        SharedPerferenceUtils.setParam(context, KEY_USER_NICK_NAME, bean.getNick_name());
        SharedPerferenceUtils.setParam(context, KEY_USER_PERMISSION, bean.getPermission());
        SharedPerferenceUtils.setParam(context,KEY_DORMITORY,bean.getDormitory_name());
        SharedPerferenceUtils.setParam(context,KEY_SITE_NAME,bean.getSite_name());
    }

    public static String getUserName(Context context) {
        return (String) SharedPerferenceUtils.getParam(context, KEY_USER_NICK_NAME,"");
    }

    public static String getUserAccount(Context context) {
        return (String) SharedPerferenceUtils.getParam(context, KEY_USER_ACCOUNT, "");
    }

    public static String getUserPassword(Context context){
        return (String) SharedPerferenceUtils.getParam(context, KEY_USER_PASSWORD, "");
    }

    public static String getDormitoryName(Context context){
        return (String) SharedPerferenceUtils.getParam(context, KEY_DORMITORY, "");
    }

    public static String getSiteName(Context context){
        return (String) SharedPerferenceUtils.getParam(context, KEY_SITE_NAME, "");
    }

    /**
     * 判断是不是超级管理员
     * @param context
     * @return
     */
    public static boolean isSuperManager(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_PERMISSION, "");
        return limit.equals(LIMIT_SUPER_MANAGER) ;
    }

    /**
     * 判断是不是维修人员
     * @param context
     * @return
     */
    public static boolean isRepairMan(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_PERMISSION, "");
        return limit.equals(LIMIT_REPAIR_MAN) ;
    }

    /**
     * 判断是不是学生
     * @param context
     * @return
     */
    public static boolean isStudent(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_PERMISSION, "");
        return limit.equals(LIMIT_STUDENT) ;
    }

}
