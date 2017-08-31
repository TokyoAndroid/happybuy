package com.zw.happybuy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.bean.HomeGridViewBean;

import java.util.List;

/**
 * Created by Tok on 2017/8/31.
 */

public class HomeGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<HomeGridViewBean> datas;

    public HomeGridViewAdapter(Context context, List<HomeGridViewBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_home_gridview_item,parent,false);

        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(datas.get(position).getTitle());
        iv.setImageResource(datas.get(position).getResId());
        return convertView;
    }
}
