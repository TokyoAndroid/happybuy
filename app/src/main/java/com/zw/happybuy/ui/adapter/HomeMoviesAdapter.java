package com.zw.happybuy.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zw.happybuy.R;
import com.zw.happybuy.bean.MovieBean;
import com.zw.happybuy.utils.ImageUtils;

import java.util.List;

/**
 * Created by Tok on 2017/9/2.
 */

public class HomeMoviesAdapter extends BaseQuickAdapter<MovieBean,BaseViewHolder>{

    public HomeMoviesAdapter(@LayoutRes int layoutResId, @Nullable List<MovieBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieBean item) {
        holder.setText(R.id.tv_movie_name,item.getFilmName())
                .setText(R.id.tv_movie_fenshu,item.getGrade() + "分");
        ImageUtils.loadImageWithPlaceHolder(mContext,(ImageView)holder.getView(R.id.iv_movie),item.getImageUrl());
    }
}
