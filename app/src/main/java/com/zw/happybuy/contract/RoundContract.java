package com.zw.happybuy.contract;

import com.zw.happybuy.ui.base.BasePresenter;
import com.zw.happybuy.ui.base.BaseView;

/**
 * Created by Tok on 2017/9/3.
 */

public interface RoundContract  {

    public interface Presenter extends BasePresenter{

        void searchRound();

        void goToLocation();
    }

    public interface View extends BaseView<Presenter>{
        void showPopWindow();

        void closeRefresh();

        void updateData(String data);
    }
}
