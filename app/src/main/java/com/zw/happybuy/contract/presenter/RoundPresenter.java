package com.zw.happybuy.contract.presenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.contract.RoundContract;
import com.zw.happybuy.ui.base.RxLifeTransformer;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Tok on 2017/9/3.
 */

public class RoundPresenter implements RoundContract.Presenter{


    private Context context;
    private RoundContract.View view;

    public RoundPresenter(Context context,RoundContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void searchRound() {
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .compose(new RxLifeTransformer<Long,Long>(view))
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.closeRefresh();
                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        view.updateData("没有获取到数据!");
                    }
                });
    }

    @Override
    public void goToLocation() {

    }
}
