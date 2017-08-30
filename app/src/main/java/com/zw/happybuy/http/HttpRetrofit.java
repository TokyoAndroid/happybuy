package com.zw.happybuy.http;

import com.zw.happybuy.common.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tok on 2017/8/24.
 */

public class HttpRetrofit {
    private static final int DEFAULT_TIMEOUT = 10;

    private final APIService apiService;

    HttpRetrofit(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        //对所有请求添加请求头
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                return originalResponse.newBuilder().addHeader("key1","header1")
                        .addHeader("key2","header2").build();
            }
        });

        Cache cache = new Cache(App.getAppContext().getCacheDir(),10 * 1024 * 1024);
        builder.cache(cache);
        builder.addInterceptor(new CacheInterceptor());
        builder.addNetworkInterceptor(new CacheInterceptor());

        apiService = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APIService.base_url)
                .build().create(APIService.class);
    }

    APIService getApiService(){
        return apiService;
    }
}
