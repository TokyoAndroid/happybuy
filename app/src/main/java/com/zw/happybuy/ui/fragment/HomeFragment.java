package com.zw.happybuy.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.zw.happybuy.R;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.bean.HomeGridViewBean;
import com.zw.happybuy.contract.HomeContract;
import com.zw.happybuy.contract.presenter.HomePresenter;
import com.zw.happybuy.ui.adapter.HomeGoodsAdapter;
import com.zw.happybuy.ui.adapter.HomeGridViewAdapter;
import com.zw.happybuy.ui.adapter.HomeViewPagerAdapter;
import com.zw.happybuy.ui.adapter.NormalViewPagerAdapter;
import com.zw.happybuy.ui.base.BaseFragment;
import com.zw.happybuy.utils.DensityUtils;
import com.zw.happybuy.utils.SnackBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Tok on 2017/8/28.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {


    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.vp)
    ViewPager vp;
    private HomeContract.Presenter presenter;
    private HomeGoodsAdapter homeGoodsAdapter;
    private int snackBarHeight;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void afterCreated(Bundle savedInstanceState) {
        initPresenter();

        initToolbar();

        initViewPager();

        initRecyclerView();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initPresenter() {
        presenter = new HomePresenter(getActivity(), this);
    }

    private void initToolbar() {
        toolBar.inflateMenu(R.menu.menu_home_toolbar);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
    }

    private void initViewPager() {
        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity());
        vp.setAdapter(homeViewPagerAdapter);
    }

    private void initRecyclerView() {
        presenter.loadGoods();

    }


    @OnClick({R.id.tv_location, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                break;
            case R.id.search:
                break;
        }
    }

    @Override
    public void showLoadDataError(String message) {
        if (homeGoodsAdapter == null) {
            initAdapter(null);
        }
        homeGoodsAdapter.notifyDataSetChanged();

        String text = message;
        String actionText = this.getResources().getString(R.string.reload);
        SnackBarUtils.showWithButtonAlways(getActivity().findViewById(R.id.cardView), text, actionText,
                snackBarHeight,new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter.loadGoods();

                            }
        });
    }


    @Override
    public void refreshRecyclerView(List<GoodsBean.GoodlistBean> goodlistBeen) {
        if (homeGoodsAdapter == null) {
            initAdapter(goodlistBeen);
        }
        homeGoodsAdapter.setNewData(goodlistBeen);
        homeGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterLoadMore(boolean isSuccess) {
        if(isSuccess){
            //加载成功
        }else{
            homeGoodsAdapter.loadMoreEnd(true);
            Logger.i("height : " + snackBarHeight);
            SnackBarUtils.showWithoutButtonLong(getActivity().findViewById(R.id.cardView),"没有更多数据加载了!",
                        snackBarHeight);
        }
    }

    private void initAdapter(List<GoodsBean.GoodlistBean> goodlistBeen) {
        snackBarHeight = getActivity().findViewById(R.id.cardView).getHeight();

        homeGoodsAdapter = new HomeGoodsAdapter(R.layout.layout_home_goods_item, goodlistBeen);

        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty, null);
        //设置空布局
        homeGoodsAdapter.setEmptyView(emptyView);
        //设置动画
        homeGoodsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        homeGoodsAdapter.isFirstOnly(false);
        //设置上拉加载
        homeGoodsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadMore();
            }
        },rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(homeGoodsAdapter);
        initRecyclerViewHeader();

    }

    private void initRecyclerViewHeader() {
        TypedArray ta = getResources().obtainTypedArray(R.array.home_bar_icon);
        String[] subjects = getResources().getStringArray(R.array.home_bar_labels);
        List<HomeGridViewBean> pageOneList = new ArrayList<>();
        List<HomeGridViewBean> pageTwoList = new ArrayList<>();
        for (int i = 0; i < subjects.length; i++) {
            HomeGridViewBean bean = new HomeGridViewBean(ta.getResourceId(i, 0), subjects[i]);
            if (i < 8) {
                pageOneList.add(bean);
            } else {
                pageTwoList.add(bean);
            }
        }

        int vpHeadHeight = DensityUtils.dip2px(getActivity(), 168);
        ViewPager vpHead = new ViewPager(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                vpHeadHeight);
        vpHead.setLayoutParams(params);

        GridView gridView1 = new GridView(getActivity());
        GridView gridView2 = new GridView(getActivity());
        gridView1.setNumColumns(4);
        gridView2.setNumColumns(4);
        LinearLayout.LayoutParams gvGarams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        gvGarams.gravity = Gravity.CENTER_VERTICAL;
        gridView1.setLayoutParams(gvGarams);
        gridView2.setLayoutParams(gvGarams);
        gridView1.setAdapter(new HomeGridViewAdapter(getActivity(), pageOneList));
        gridView2.setAdapter(new HomeGridViewAdapter(getActivity(), pageTwoList));
        List<View> gvs = new ArrayList<>();
        gvs.add(gridView1);
        gvs.add(gridView2);
        vpHead.setAdapter(new NormalViewPagerAdapter(gvs));
        homeGoodsAdapter.addHeaderView(vpHead);
    }

}