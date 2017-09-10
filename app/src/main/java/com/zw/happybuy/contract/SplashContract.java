package com.zw.happybuy.contract;

import com.zw.happybuy.ui.base.BasePresenter;
import com.zw.happybuy.ui.base.BaseView;

/**
 * Created by Tok on 2017/8/21.
 */

public interface SplashContract {

    public interface SplashView extends BaseView<SplashPresenter>{


        void startAnimation();

        void showNetworkError();
    }

    public interface SplashPresenter extends BasePresenter{


        void goToActivity();

        void startLocation();

        void stopLocation();

    }

}
