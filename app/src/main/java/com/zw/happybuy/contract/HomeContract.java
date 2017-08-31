package com.zw.happybuy.contract;

import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.ui.base.BasePresenter;
import com.zw.happybuy.ui.base.BaseView;

import java.util.List;

/**
 * Created by Tok on 2017/8/29.
 */

public interface HomeContract {

    public interface Presenter extends BasePresenter{
        void loadGoods();

        void loadMore();

        void refreshData();
    }

    public interface View extends BaseView<Presenter>{
        void showLoadDataError(String message);

        void refreshRecyclerView(List<GoodsBean.GoodlistBean> goodlistBeen);

        void afterLoadMore(boolean isSuccess);
    }

}
