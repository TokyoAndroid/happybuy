package com.zw.happybuy.http;

import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.bean.MovieBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Tok on 2017/8/24.
 *
 * 1.新建接口APIService，所有的访问请求都在这里
 * 2.创建HttpRetrofit，创建Retrofit和OKHttpClient，主要是为了创建APIService
 *   待补充：OKHttp的NetworkInterceptor和interceptor、Cache、CookJar
 * 3.创建HttpFactory，提供公共方法获取APIService
 * 4.创建一个公共的请求结果类HttpResult，因为一般请求的开头都是一样的（成功与否的code信息），获取的实例类则各不相同
 * 5.创建一个HttpResultFunc类，这个是RxJava里面的转换类，用来将返回结果从HttpResult转换为真正的Result（Movie或Goods）
 * 6.Transformer是一个将Observable转换成另一个Observable的转换器，先对Observable<HttpResult>做判断，如果返回码不对直接
 *   调用Observable<HttpResult>的Error，否则返回我们真正的Observable<result>
 *   好处：1、重用了Observable  2、省略了我们每次写线程切换的动作
 * 7.定义一个ProgressDialogHandler继承Handler,用来显示Dialog和取消Dialog
 *   如果用户取消了Dialog，那么本次Http请求也要取消，用接口回调的形式进行
 * 8.写一个ProgressSubscriber，在onStart的时候showDialog,在请求结束的时候dismissDialog，返回后给出onSuccess和onFailed方法回调
 * 9.新建了CacheInterceptor类，用来判断去获取缓存数据还是新数据
 * 10.加入RxLifecycle，用来避免RxJava内存泄漏
 */

public interface APIService {

    String base_url = "http://7xij5m.com1.z0.glb.clouddn.com/";

    @GET("filmHot_refresh.txt")
    Observable<HttpResult<List<MovieBean>>> getMovie();

    @GET("spRecommend.txt")
    Observable<HttpResult<GoodsBean>> getGoods();

}
