package com.zw.happybuy.ui.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zw.happybuy.R;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.bean.HomeBean;
import com.zw.happybuy.bean.HomeGridViewBean;
import com.zw.happybuy.contract.HomeContract;
import com.zw.happybuy.contract.presenter.HomePresenter;
import com.zw.happybuy.ui.adapter.HomeGoodsAdapter;
import com.zw.happybuy.ui.adapter.HomeGridViewAdapter;
import com.zw.happybuy.ui.adapter.HomeMoviesAdapter;
import com.zw.happybuy.ui.adapter.HomeViewPagerAdapter;
import com.zw.happybuy.ui.adapter.NormalViewPagerAdapter;
import com.zw.happybuy.ui.base.BaseFragment;
import com.zw.happybuy.utils.DensityUtils;
import com.zw.happybuy.utils.SPUtils;
import com.zw.happybuy.utils.SnackBarUtils;
import com.zw.happybuy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.appbar_Layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.up_toolbar_layout)
    LinearLayout upToolbarLayout;

    private HomeContract.Presenter presenter;
    private HomeGoodsAdapter homeGoodsAdapter;
    private RecyclerView rvMovies;
    private HomeMoviesAdapter homeMoviesAdapter;
    private LinearLayoutManager rvLaoutManager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void afterCreated(Bundle savedInstanceState) {
        initToolbar();

        initAppBarLayout();

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
        presenter.start();
    }

    private void initToolbar() {
        toolBar.inflateMenu(R.menu.menu_home_toolbar);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });

        String location = SPUtils.getString("location","");
        if(!TextUtils.isEmpty(location)){
            tvLocation.setText(location);
        } else{
            tvLocation.setText("武汉");
        }
    }


    private void initAppBarLayout() {
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int appbaHeight = appBarLayout.getTotalScrollRange();
            }
        });

    }

    private void initViewPager() {
        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity());
        vp.setAdapter(homeViewPagerAdapter);

    }

    private void initRecyclerView() {
        initAdapter(null);

        rvLaoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(rvLaoutManager);

        initRecyclerViewHeader();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int visiblePosition = rvLaoutManager.findFirstCompletelyVisibleItemPosition();
                    if (visiblePosition == 0) {
                        appbarLayout.setExpanded(true, true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        presenter.loadDatas(true);

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

        String text = message;
        String actionText = this.getResources().getString(R.string.reload);
        SnackBarUtils.showWithButtonAlwaysWithHeight(getActivity().findViewById(R.id.cardView), text, actionText
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.loadDatas(false);
                        }
                    });
    }


    @Override
    public void refreshRecyclerView(HomeBean homeBean) {
        homeGoodsAdapter.setNewData(homeBean.goodlistBean);
        homeMoviesAdapter.setNewData(homeBean.movieBeanList);
        homeGoodsAdapter.notifyDataSetChanged();
        homeMoviesAdapter.notifyDataSetChanged();
    }


    @Override
    public void afterLoadMore(boolean isSuccess) {
        if (isSuccess) {
            //加载成功
        } else {
            homeGoodsAdapter.loadMoreEnd(true);
            SnackBarUtils.showWithoutButtonLongWithHeight(getActivity().findViewById(R.id.cardView), "没有更多数据加载了!");
        }
    }

    private void initAdapter(List<GoodsBean.GoodlistBean> goodlistBeen) {
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
        }, rv);

        //设置点击事件
        homeGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        rv.setAdapter(homeGoodsAdapter);


    }

    private void initRecyclerViewHeader() {
        //GridViewPager
        ViewPager vp_gridview = new ViewPager(getActivity());
        ViewGroup.LayoutParams vp_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(getActivity(),150));
        vp_gridview.setLayoutParams(vp_params);

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
        ta.recycle();

        GridView gridView1 = new GridView(getActivity());
        GridView gridView2 = new GridView(getActivity());
        gridView1.setNumColumns(4);
        gridView2.setNumColumns(4);
        gridView1.setGravity(Gravity.CENTER);
        gridView2.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams gvGarams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        gridView1.setLayoutParams(gvGarams);
        gridView2.setLayoutParams(gvGarams);
        gridView1.setAdapter(new HomeGridViewAdapter(getActivity(), pageOneList));
        gridView2.setAdapter(new HomeGridViewAdapter(getActivity(), pageTwoList));
        List<View> gvs = new ArrayList<>();
        gvs.add(gridView1);
        gvs.add(gridView2);
        vp_gridview.setAdapter(new NormalViewPagerAdapter(gvs));


        //横向RecyclerView
        rvMovies = new RecyclerView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
        rvMovies.setLayoutParams(params);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        homeMoviesAdapter = new HomeMoviesAdapter(R.layout.layout_home_movie_item,null);

        View movieEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_movie_empty,null);
        homeMoviesAdapter.setEmptyView(movieEmptyView);

        rvMovies.setAdapter(homeMoviesAdapter);
        //解决横向嵌套RecyclerView不响应AppbarLayout的滑动问题
        rvMovies.setNestedScrollingEnabled(false);

        homeGoodsAdapter.addHeaderView(vp_gridview);
        homeGoodsAdapter.addHeaderView(rvMovies);


    }

   /* private void initRecyclerViewOnTouch() {
        rv.setOnTouchListener(new View.OnTouchListener() {

            int appbarLayoutRealHeight = appbarLayoutHeight - toorbarHeight - vpGridViewHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = (int) (event.getRawX() - downX);
                        moveY = (int) (event.getRawY() - downY);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(moveY) - Math.abs(moveX) < 0){
                            appbarLayout.setExpanded(true, true);
                        }else //向上滑moveY为负
                            if (appbarLayoutOffset != -appbarLayoutRealHeight) {
                                if ((moveY > 0 && moveY < appbarLayoutRealHeight / 3) ||
                                        (moveY < 0 && -moveY > appbarLayoutRealHeight / 3)) {
                                    appbarLayout.setExpanded(false, true);
                                } else {
                                    appbarLayout.setExpanded(true, true);
                                }
                            }

                        break;
                }
                return false;
            }
        });
    }*/

}