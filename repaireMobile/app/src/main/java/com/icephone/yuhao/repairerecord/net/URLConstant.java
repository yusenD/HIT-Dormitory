package com.icephone.yuhao.repairerecord.net;

public class URLConstant {

    public final static String BASE_URL = "http:/10.0.2.2:5000/repair/";

    //登录成功
    public final static int SUCCUSS_CODE = 200;

    //登录
    public final static String USER_LOGIN = "app/login";

	//维修记录
    public final static String REPAIR_GET_LIST = "app/getrepairList";
    public final static String REPAIR_DELETE_RECORD = "app/deleterepairRecord";
    public final static String REPAIR_CHANGE_RECORD = "app/changerepairRecord";
    public final static String REPAIR_ADD_RECORD = "app/addrepairRecord";

    // 人
    public final static String PERSON_GET_LIST = "app/getPersonList";
    public final static String PERSON_ADD = "app/addPerson";
    public final static String PERSON_DELETE = "app/deletePerson";
    public final static String PERSON_CHANGE = "app/changePerson";

    //TODO 需要删除
    // 联社管理
    public final static String CENTER_GET_LIST = "app/getCenterList";
    public final static String CENTER_ADD = "app/addCenterRecord";
    public final static String CENTER_DELETE = "app/deleteCenterRecord";

    //设备管理
    public final static String DEVICE_GET_LIST = "app/getDeviceList";
    public final static String DEVICE_DELETE = "app/deleteDevice";
    public final static String DEVICE_ADD = "app/addDevice";

}
