package com.icephone.yuhao.repairerecord.net;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ApiBuilder {

    Map<String, String> params ;
    Map<String, String> headers ;
    RequestBody body;
    String url;

    public ApiBuilder Body(RequestBody  body){
        this.body = body;
        return this;
    }

    public ApiBuilder Params(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public ApiBuilder Params(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public ApiBuilder Headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public ApiBuilder Headers(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public ApiBuilder Url(String url) {
        this.url = url;
        return this;
    }

    public ApiBuilder(String url) {
        this.setParams(url);
    }

    public ApiBuilder() {
        this.setParams(null);
    }


    private void setParams(String url) {
        this.url = url;
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
    }

}
