package com.zw.happybuy.ui.fragment;

import android.os.Bundle;

import com.zw.happybuy.R;
import com.zw.happybuy.contract.MeContract;
import com.zw.happybuy.ui.base.BaseFragment;

/**
 * Created by Tok on 2017/9/8.
 */

public class MeFragment extends BaseFragment implements MeContract.View{

    @Override
    public void setPresenter(MeContract.Presenter presenter) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void afterCreated(Bundle savedInstanceState) {

    }
}
