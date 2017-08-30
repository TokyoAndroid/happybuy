package com.zw.happybuy.http;

/**
 * Created by Tok on 2017/8/24.
 */

public class HttpFactory {

    private static APIService apiService;

    private HttpFactory(){

    }

    public static APIService getApiService(){
        if(apiService == null){
            synchronized (HttpFactory.class){
                if(apiService == null){
                    apiService = new HttpRetrofit().getApiService();
                }
            }
        }
        return apiService;
    }
}
