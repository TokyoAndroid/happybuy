package com.zw.happybuy.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.ui.base.BaseActivity;
import com.zw.happybuy.ui.base.RxLifeTransformer;
import com.zw.happybuy.ui.fragment.HomeFragment;
import com.zw.happybuy.ui.fragment.MeFragment;
import com.zw.happybuy.ui.fragment.RoundFragment;
import com.zw.happybuy.utils.NetworkUtils;
import com.zw.happybuy.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_host)
    FragmentTabHost tabHost;

    private int[] tabIcons = new int[]{R.drawable.selector_tab_home,R.drawable.selector_tab_round,
                                        R.drawable.selector_tab_me,R.drawable.selector_tab_more};

    private String[] tabTitles;

    private Class[] fragments = new Class[]{HomeFragment.class,RoundFragment.class,
                                            MeFragment.class,HomeFragment.class};

    private AlertDialog downloadDialog;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        //这个页面没有用MVP模式
    }

    @Override
    public void afterCreated(@Nullable Bundle savedInstanceState) {
        tabTitles = this.getResources().getStringArray(R.array.tab_title);

        initTabWidget();

        checkAppVersionUpdate();

    }

    private void initTabWidget(){
        //设置Fragment的容器
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //设置有多少Item
        for (int i = 0; i < fragments.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.layout_tab,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_tab);
            iv.setImageResource(tabIcons[i]);
            TextView tv = (TextView) view.findViewById(R.id.tv_tab);
            tv.setText(tabTitles[i]);
            tabHost.addTab(tabHost.newTabSpec("" + i).setIndicator(view),fragments[i],null);
        }
        tabHost.setCurrentTab(0);
    }

    private void checkAppVersionUpdate(){
        if(NetworkUtils.isWifiConnected(this)){
            Observable.timer(3, TimeUnit.SECONDS)
                    .compose(new RxLifeTransformer<Long,Long>(this))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            showIsUpdateDialog();
                        }
                    });
        } else {

        }
    }

    private void showIsUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.isUpdateApp_message);
        builder.setTitle(R.string.isUpdateApp_title);
        builder.setIcon(R.mipmap.update_app);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downloadApp();
                ToastUtils.showShort("后台下载新版本中!");
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();
    }

    private void downloadApp(){
        //TODO 下载APP
    }

}
