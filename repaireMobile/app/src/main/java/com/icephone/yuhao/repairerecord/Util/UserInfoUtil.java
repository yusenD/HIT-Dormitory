package com.icephone.yuhao.repairerecord.Util;

import android.content.Context;

import com.icephone.yuhao.repairerecord.bean.LoginBean;
import com.icephone.yuhao.repairerecord.bean.PeopleBean;

public class UserInfoUtil {

    private final static String KEY_USER_ID = "id";
    private final static String KEY_USER_NICK_NAME = "nickname";
    private final static String KEY_USER_ACCOUNT = "account";
    private final static String KEY_USER_LIMIT = "limit";
    private final static String KEY_MANAGE_CENTER = "center";

    public final static String LIMIT_SUPER_MANAGER = "总管理员";
    public final static String LIMIT_MANAGER = "联社管理员";
    public final static String LIMIT_REPAIR_MAN = "维修人员";

    public static void saveUserInfo(Context context, LoginBean.DataBean bean) {
        SharedPerferenceUtils.setParam(context, KEY_USER_ID, bean.get_id());
        SharedPerferenceUtils.setParam(context, KEY_USER_ACCOUNT, bean.getAccount());
        SharedPerferenceUtils.setParam(context, KEY_USER_NICK_NAME, bean.getNick_name());
        SharedPerferenceUtils.setParam(context, KEY_USER_LIMIT, bean.getLimit());
        SharedPerferenceUtils.setParam(context,KEY_MANAGE_CENTER,bean.getManage_center());
    }

    public static String getUserName(Context context) {
        return (String) SharedPerferenceUtils.getParam(context, KEY_USER_NICK_NAME,"");
    }

    public static String getUserAccount(Context context) {
        return (String) SharedPerferenceUtils.getParam(context, KEY_USER_ACCOUNT, "");
    }

    public static String getManageCenter(Context context) {
        return (String) SharedPerferenceUtils.getParam(context, KEY_MANAGE_CENTER, "");
    }

    /**
     * 判断是不是超级管理员
     * @param context
     * @return
     */
    public static boolean isSuperManager(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_LIMIT, "");
        return limit.equals(LIMIT_SUPER_MANAGER) ;
    }

    /**
     * 判断是不是维修人员
     * @param context
     * @return
     */
    public static boolean isRepairMan(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_LIMIT, "");
        return limit.equals(LIMIT_REPAIR_MAN) ;
    }

    /**
     * 判断是不是联社管理员
     * @param context
     * @return
     */
    public static boolean isCenterManager(Context context) {
        String limit = (String) SharedPerferenceUtils.getParam(context, KEY_USER_LIMIT, "");
        return limit.equals(LIMIT_MANAGER) ;
    }

}
