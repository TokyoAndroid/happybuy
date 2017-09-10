package com.zw.happybuy.contract.presenter;

import android.content.Context;

import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.bean.HomeBean;
import com.zw.happybuy.bean.MovieBean;
import com.zw.happybuy.contract.HomeContract;
import com.zw.happybuy.http.HttpFactory;
import com.zw.happybuy.http.HttpResult;
import com.zw.happybuy.http.HttpTransformer;
import com.zw.happybuy.http.ProgressSubscriber;
import com.zw.happybuy.ui.base.RxLifeTransformer;
import com.zw.happybuy.utils.SnackBarUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Tok on 2017/8/29.
 */

public class HomePresenter implements HomeContract.Presenter {

    private Context context;
    private HomeContract.View view;

    private List<GoodsBean.GoodlistBean> goodlistBean;

    private boolean isFirstLoad = true;
    private Observable<GoodsBean> goodsObservable;
    private Observable<List<MovieBean>> movieBeanObservable;
    private ProgressSubscriber<HomeBean> subscriber;

    public HomePresenter(Context context, HomeContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);

    }

    @Override
    public void start() {
        goodsObservable = HttpFactory.getApiService().getGoods()
        .compose(new HttpTransformer<HttpResult<GoodsBean>, GoodsBean>((ActivityLifecycleProvider)(context)));

        movieBeanObservable = HttpFactory.getApiService().getMovie()
                .compose(new HttpTransformer<HttpResult<List<MovieBean>>, List<MovieBean>>((ActivityLifecycleProvider)(context)));

        subscriber = new ProgressSubscriber<HomeBean>(context) {
            @Override
            protected void before() {
                if(isFirstLoad){
                    showDialog();
                }
            }

            @Override
            protected void onSuccess(HomeBean homeBean) {
                view.refreshRecyclerView(homeBean);
            }

            @Override
            protected void onFailed(String message) {
                view.showLoadDataError(message);
            }
        };
    }

    @Override
    public void loadDatas(final boolean isFirstLoad) {

        Observable.zip(goodsObservable, movieBeanObservable, new Func2<GoodsBean, List<MovieBean>, HomeBean>() {
            @Override
            public HomeBean call(GoodsBean goodsBean, List<MovieBean> movieBeanList) {
                return new HomeBean(movieBeanList,goodsBean.getGoodlist());
            }
        }).subscribe(subscriber);

    }

    @Override
    public void loadMore() {
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .compose(new RxLifeTransformer<Long,Long>(view))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        view.afterLoadMore(false);
                    }
                });
    }

}
