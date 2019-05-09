package com.icephone.yuhao.repairerecord.net;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiClient {

    /**
     * 使用示例
     *
     * ApiBuilder builder = new ApiBuilder(ApiClient.Api_BookInfo)
     .Headers("header1","this is request header1")
     .Headers("header2","this is request header2")
     .Params("params1","this is request params1")
     .Params("params2","this is request params2");
     ApiClient.getInstance().doGet(builder, new CallBack<FindContent>() {
    @Override
    public void onSuccess(Call<ResponseBody> call, FindContent response) {
    //Do Something
    }
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
    },FindContent.class);

     */

    private static ApiClient instance;

    /***
     *
     * 构建 retrofit请求
     * */
    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .build();


    /**
     * 获取service实例
     * @return
     * get或者post请求接口
     */
    public ApiService getService(){
        return retrofit.create(ApiService.class);
    }

    /**
     * 创建请求单例
     * @return
     */
    public static ApiClient getInstance(){

        if(instance == null){
            synchronized (ApiClient.class){
                if(instance ==null){
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    /**
     *
     * get请求
     * @param builder  request构建的参数 包含 url header params
     * @param onCallback  rquest 回调
     * @param classOf   指定请求的model类型
     *
     */

    public <T> void doPost(ApiBuilder builder ,final CallBack<T> onCallback,final Class classOf){

        ApiService service = getService();
        Call<ResponseBody> call = service.post(builder.headers, builder.url,builder.body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Object o = null;
                try {
                    if (response.body() == null) {
                        onCallback.onFail("失败");
                    }else{
                        o = new Gson().fromJson(response.body().string(), classOf);
                        onCallback.onResponse((T) o);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onCallback.onFail(t.getMessage());
                Log.d("NET---",t.getMessage());
            }
        });
    }

    /**
     *
     * get请求
     * @param builder  request构建的参数 包含 url header params
     * @param onCallback  rquest 回调
     * @param classOf   指定请求的model类型
     */

    public <T> void doGet(ApiBuilder builder ,final CallBack<T> onCallback,final Class classOf){

        ApiService service = getService();
        Call<ResponseBody> call = service.get(checkHeaders(builder.headers), builder.url, checkParams(builder.params));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Object o = null;
                try {
                    if (response.body() == null) {
                        String err = response.errorBody().string();
                        onCallback.onFail("失败");
                        Log.i("err", err);
                    }else{
                        o = new Gson().fromJson(response.body().string(), classOf);
                        onCallback.onResponse((T) o);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onCallback.onFail(t.getMessage());
                Log.d("NET---",t.getMessage());
            }
        });
    }

    public static Map<String, String> checkHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        //retrofit的headers的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getValue() == null) {
                headers.put(entry.getKey(), "");
            }
        }
        return headers;
    }

    public static Map<String, String> checkParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        //retrofit的params的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                params.put(entry.getKey(), "");
            }
        }
        return params;
    }

}
