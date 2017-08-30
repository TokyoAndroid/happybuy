package com.zw.happybuy.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.R;

import rx.Observable;

/**
 * Created by Tok on 2017/8/30.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private int[] imgs = new int[]{R.mipmap.banner01,R.mipmap.banner02,R.mipmap.banner03};

    private ImageView[] views;
    private Context context;

    public HomeViewPagerAdapter(Context context){
        this.context = context;
        views = new ImageView[imgs.length];
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageResource(imgs[i]);
            views[i] = iv;
        }
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position]);
    }
}
