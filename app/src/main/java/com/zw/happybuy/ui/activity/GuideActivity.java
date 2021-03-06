package com.zw.happybuy.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.zw.happybuy.R;
import com.zw.happybuy.common.App;
import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.ui.MainActivity;
import com.zw.happybuy.ui.adapter.GuidePagerAdapter;
import com.zw.happybuy.ui.base.BaseActivity;
import com.zw.happybuy.utils.DensityUtils;
import com.zw.happybuy.utils.StatusViewUtils;
import com.zw.happybuy.utils.rxbus.RxBindingUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.circle_container)
    LinearLayout circleContainer;
    @BindView(R.id.btn)
    Button btn;

    private View[] views = new View[4];
    private int[] bgColors = new int[]{
            R.color.green,R.color.punk,
            R.color.yellow,R.color.lightBlue
    };
    private GuidePagerAdapter adpater;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_guide;
    }

    @Override
    public void initPresenter() {
        //此界面没有用MVP模式
    }

    @Override
    public void afterCreated(@Nullable Bundle savedInstanceState) {
        btn.setVisibility(View.GONE);
        //1.设置Button跳转Activity
        RxBindingUtils.click(btn, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                AppManager.getAppManager().finishCurrentActivity();
            }
        });
        //2. 设置ViewPager内容
        initViewPager();
        //3.添加底部滑动ViewPager的圆点
        addCircles();
    }

    private void initViewPager(){
        adpater = new GuidePagerAdapter(this,bgColors);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 3){
                    btn.setVisibility(View.GONE);
                } else{
                    btn.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < 4; i++) {
                    if(position == i){
                        views[i].setBackgroundResource(R.drawable.shape_guide_circle_current);
                    }else{
                        views[i].setBackgroundResource(R.drawable.shape_guide_circle);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setAdapter(adpater);
        //为了让Fragment进行状态栏沉浸，移除StatusBar
        StatusViewUtils.removeStatusBar(this);
    }

    private void addCircles(){
        for (int i = 0; i < 4; i++) {
            View view = new View(App.getAppContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(this,10),DensityUtils.dip2px(this,10));
            if(i != 0){
                view.setBackgroundResource(R.drawable.shape_guide_circle);
                params.leftMargin = DensityUtils.dip2px(this,10);
            } else {
                view.setBackgroundResource(R.drawable.shape_guide_circle_current);
            }
            view.setLayoutParams(params);
            views[i] = view;
            circleContainer.addView(view);
        }
    }

}
