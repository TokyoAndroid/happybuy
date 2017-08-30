package com.zw.happybuy.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zw.happybuy.R;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.utils.ImageUtils;

import java.util.List;

/**
 * Created by Tok on 2017/8/30.
 */

public class HomeGoodsAdapter extends BaseQuickAdapter<GoodsBean.GoodlistBean,BaseViewHolder> {

    public HomeGoodsAdapter(@LayoutRes int layoutResId, @Nullable List<GoodsBean.GoodlistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsBean.GoodlistBean item) {
        holder.setText(R.id.tv_goods_title,item.getProduct())
                .setText(R.id.tv_goods_dec,item.getTitle())
                .setText(R.id.tv_real_price,item.getPrice())
                .setText(R.id.tv_orl_price,item.getValue())
                .setText(R.id.tv_sales,"已售" + item.getBought() + "份");
        ImageUtils.loadImageWithPlaceHolder(mContext,(ImageView) holder.getView(R.id.iv_goods),item.getImages().get(0).getImage());
        //设置删除线
        ((TextView)holder.getView(R.id.tv_orl_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
