package com.zw.happybuy.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.bean.GoodsBean;
import com.zw.happybuy.contract.HomeContract;
import com.zw.happybuy.contract.presenter.HomePresenter;
import com.zw.happybuy.ui.adapter.HomeGoodsAdapter;
import com.zw.happybuy.ui.adapter.HomeViewPagerAdapter;
import com.zw.happybuy.ui.base.BaseFragment;
import com.zw.happybuy.utils.SnackBarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    Unbinder unbinder;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder1;
    private HomeContract.Presenter presenter;
    private HomeGoodsAdapter homeGoodsAdapter;

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

    private void initViewPager(){
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
        SnackBarUtils.showWithButtonAlways(rv, text, actionText, new View.OnClickListener() {
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

    private void initAdapter(List<GoodsBean.GoodlistBean> goodlistBeen) {
        homeGoodsAdapter = new HomeGoodsAdapter(R.layout.layout_home_goods_item, goodlistBeen);

        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty, null);
        homeGoodsAdapter.setEmptyView(emptyView);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(homeGoodsAdapter);

    }

    private void initRecyclerViewHeader(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
