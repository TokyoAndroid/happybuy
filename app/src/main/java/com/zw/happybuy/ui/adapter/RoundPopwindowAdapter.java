package com.zw.happybuy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.utils.DensityUtils;

/**
 * Created by Tok on 2017/9/3.
 */

public class RoundPopwindowAdapter extends BaseAdapter{
    private String[] data;
    private Context context;

    public RoundPopwindowAdapter(Context context,String[] data){
        this.data = data;
        this.context = context;
    }

    public void setData(String[] data){
        this.data = data;
    }

    public String[] getData(){
        return this.data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.length;
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
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_round_pop_listview_item,parent,false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(data[position]);
        return convertView;
    }
}
