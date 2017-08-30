package com.zw.happybuy.http;


import android.os.SystemClock;

import com.zw.happybuy.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tok on 2017/8/24.
 *
 * 将获取的Unicode编码转换成中文
 */


public class CacheInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        //断网的情况下去获取缓存
        if(!NetworkUtils.isConnected()){
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            int maxAge = 60;  //在有网络的情况下，缓存失效的时间为60秒
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;  //没有网络的情况下，缓存失效的时间为4周
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

    }
}
