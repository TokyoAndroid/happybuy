package com.zw.happybuy.contract.presenter;

import android.content.Context;

import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.contract.HomeContract;
import com.zw.happybuy.http.HttpFactory;
import com.zw.happybuy.http.HttpResult;
import com.zw.happybuy.http.HttpTransformer;
import com.zw.happybuy.http.ProgressSubscriber;
import com.zw.happybuy.utils.SnackBarUtils;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by Tok on 2017/8/29.
 */

public class HomePresenter implements HomeContract.Presenter {

    private Context context;
    private HomeContract.View view;

    private List<GoodsBean.GoodlistBean> goodlistBean;

    public HomePresenter(Context context, HomeContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadGoods() {
        HttpFactory.getApiService().getGoods()
                .compose(new HttpTransformer<HttpResult<GoodsBean>,GoodsBean>((ActivityLifecycleProvider)(context)))
                .subscribe(new ProgressSubscriber<GoodsBean>(context) {
                    @Override
                    protected void onSuccess(GoodsBean goodsBean) {
                        goodlistBean = goodsBean.getGoodlist();

                        view.refreshRecyclerView(goodlistBean);
                    }

                    @Override
                    protected void onFailed(String message) {
                        view.showLoadDataError(message);
                    }

                    @Override
                    protected void before() {
//                        showDialog();
                    }
                });


    }
}
