package com.zw.happybuy.bean;

import java.util.List;

/**
 * Created by Tok on 2017/9/2.
 */

public class HomeBean {

    public List<MovieBean> movieBeanList;
    public List<GoodsBean.GoodlistBean> goodlistBean;

    public HomeBean(List<MovieBean> movieBeanList, List<GoodsBean.GoodlistBean> goodlistBean) {
        this.movieBeanList = movieBeanList;
        this.goodlistBean = goodlistBean;
    }
}
