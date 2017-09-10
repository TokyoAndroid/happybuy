package com.zw.happybuy.contract;

import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.bean.HomeBean;
import com.zw.happybuy.bean.MovieBean;
import com.zw.happybuy.ui.base.BasePresenter;
import com.zw.happybuy.ui.base.BaseView;

import java.util.List;

/**
 * Created by Tok on 2017/8/29.
 */

public interface HomeContract {

    public interface Presenter extends BasePresenter{
        void loadDatas(boolean isFirstLoad);

        void loadMore();

    }

    public interface View extends BaseView<Presenter>{
        void showLoadDataError(String message);

        void refreshRecyclerView(HomeBean homeBean);

        void afterLoadMore(boolean isSuccess);
    }

}
