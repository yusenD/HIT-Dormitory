package com.icephone.yuhao.repairerecord.net;



public interface CallBack<T> {
    public void onResponse(T data);
    public void onFail(String msg);
}
