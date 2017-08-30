package com.zw.happybuy.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.ui.base.BaseActivity;
import com.zw.happybuy.ui.fragment.HomeFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_host)
    FragmentTabHost tabHost;

    private int[] tabIcons = new int[]{R.drawable.selector_tab_home,R.drawable.selector_tab_round,
                                        R.drawable.selector_tab_me,R.drawable.selector_tab_more};

    private String[] tabTitles;

    private Class[] fragments = new Class[]{HomeFragment.class,HomeFragment.class,
                                            HomeFragment.class,HomeFragment.class};

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

}
