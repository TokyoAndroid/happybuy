package com.zw.happybuy.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.zw.happybuy.common.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Tok on 2017/8/21.
 */

public abstract class BaseFragment extends RxFragment{

    private View rootView;
    private Unbinder unbinder;

    //第一次加载才初始化View，避免重复调用onCreateView初始化数据
    private boolean isFirstCreate = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(getLayoutRes(),container,false);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        unbinder = ButterKnife.bind(this,rootView);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isFirstCreate){
            initPresenter();
            afterCreated(savedInstanceState);
            isFirstCreate = false;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = App.getRefWatcher(getActivity());
        watcher.watch(this);
    }

    public abstract int getLayoutRes();
    public abstract void initPresenter();
    public abstract void afterCreated(Bundle savedInstanceState);

}
