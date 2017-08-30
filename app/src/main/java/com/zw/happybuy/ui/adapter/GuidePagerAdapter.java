package com.zw.happybuy.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.utils.LogUtils;

/**
 * Created by Tok on 2017/8/27.
 */

public class GuidePagerAdapter extends PagerAdapter{

    private int[] imgs = new int[]{
            R.mipmap.guide_food,R.mipmap.guide_movie,
            R.mipmap.guide_hospital,R.mipmap.guide_office,
            R.mipmap.guide_shopping,R.mipmap.guide_ktv,
            R.mipmap.guide_travel,R.mipmap.guide_beauty
    };
    private int[] bgColors = new int[]{
            R.color.indigo,R.color.punk,
            R.color.yellow,R.color.lightBlue
    };
    private int[] tvColors = new int[]{
            R.color.colorPrimary,R.color.blue,
            R.color.black1,R.color.zise
    };
    private RelativeLayout[] views = new RelativeLayout[4];

    private String[] subject;
    private Context context;
    private LayoutInflater inflate;

    public GuidePagerAdapter(Context context){
        this.context = context;
        subject = context.getResources().getStringArray(R.array.guide_subject);
        inflate = LayoutInflater.from(context);
        for (int i = 0; i < 8; i++) {
            if(i % 2 == 0){
                RelativeLayout parent = (RelativeLayout) inflate.inflate(R.layout.layout_guide_child,null);
                ImageView iv1 = (ImageView) parent.findViewById(R.id.iv1);
                ImageView iv2 = (ImageView) parent.findViewById(R.id.iv2);
                TextView tv1 = (TextView) parent.findViewById(R.id.tv1);
                TextView tv2 = (TextView) parent.findViewById(R.id.tv2);
                iv1.setImageResource(imgs[i]);
                iv2.setImageResource(imgs[i + 1]);
                tv1.setText(subject[i]);
                tv2.setText(subject[i + 1]);
                tv1.setTextColor(ContextCompat.getColor(context,tvColors[i/2]));
                tv2.setTextColor(ContextCompat.getColor(context,tvColors[i/2]));
                parent.setBackground(null);
                parent.setBackgroundColor(ContextCompat.getColor(context,bgColors[i/2]));
                views[i/2] = parent;
            }
        }
    }

    @Override
    public int getCount() {
        return views.length;
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
