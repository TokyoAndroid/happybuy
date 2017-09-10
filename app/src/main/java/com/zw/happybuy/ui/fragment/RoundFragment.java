package com.zw.happybuy.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.R;
import com.zw.happybuy.contract.RoundContract;
import com.zw.happybuy.contract.presenter.RoundPresenter;
import com.zw.happybuy.ui.activity.LocationActivity;
import com.zw.happybuy.ui.adapter.HomeGoodsAdapter;
import com.zw.happybuy.ui.adapter.RoundPopwindowAdapter;
import com.zw.happybuy.ui.base.BaseFragment;
import com.zw.happybuy.utils.SnackBarUtils;
import com.zw.happybuy.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Tok on 2017/9/3.
 */

public class RoundFragment extends BaseFragment implements RoundContract.View {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.iv_subject)
    ImageView ivSubject;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.iv_rank)
    ImageView ivRank;
    @BindView(R.id.tv_huodong)
    TextView tvHuodong;
    @BindView(R.id.iv_huodong)
    ImageView ivHuodong;
    @BindView(R.id.select_container)
    CardView selectContainer;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private RoundContract.Presenter presenter;
    private PopupWindow popupWindow;
    private RoundPopwindowAdapter popListViewAdapter;
    private String[] subjects;
    private String[] ranks;
    private String[] huodongs;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_round;
    }

    @Override
    public void afterCreated(Bundle savedInstanceState) {
        initToolBar();
        initPopWindow();
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    @Override
    public void setPresenter(RoundContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initPresenter() {
        presenter = new RoundPresenter(getActivity(), this);
        presenter.start();
    }

    private void initToolBar() {
        toolBar.inflateMenu(R.menu.menu_round_toolbar);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.map){
                    Intent intent = new Intent(getActivity(), LocationActivity.class);
                    startActivityForResult(intent,0x0001);
                }
                return true;
            }
        });
    }

    private void initPopWindow() {
        subjects = getActivity().getResources().getStringArray(R.array.round_subject);
        ranks = getActivity().getResources().getStringArray(R.array.round_rank);
        huodongs = getActivity().getResources().getStringArray(R.array.round_huodong);

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_round_subject, null);
        ListView lv = (ListView) contentView.findViewById(R.id.lv);
        popListViewAdapter = new RoundPopwindowAdapter(getActivity(), null);
        lv.setAdapter(popListViewAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = popListViewAdapter.getData()[position];
                if(popListViewAdapter.getData() == subjects){
                    tvSubject.setText(title);
                } else if(popListViewAdapter.getData() == ranks){
                    tvRank.setText(title);
                } else if(popListViewAdapter.getData() == huodongs){
                    tvHuodong.setText(title);
                }
                popupWindow.dismiss();
                srl.setRefreshing(true);
                presenter.searchRound();
            }
        });

        popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置popupWindow消失时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }

    private void initRecyclerView() {
        HomeGoodsAdapter goodsAdapter = new HomeGoodsAdapter(R.layout.layout_home_goods_item, null);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        goodsAdapter.bindToRecyclerView(rv);
        goodsAdapter.setEmptyView(R.layout.layout_empty);


    }

    private void initSwipeRefreshLayout(){
        srl.setColorSchemeResources(R.color.colorPrimary);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.searchRound();
                srl.setRefreshing(true);
            }
        });


        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        presenter.searchRound();
    }

    @Override
    public void showPopWindow() {
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.N) {
            popupWindow.showAsDropDown(selectContainer, 0, 0);
        } else {
            int[] a = new int[2];
            selectContainer.getLocationInWindow(a);
            popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, selectContainer.getHeight() + a[1]);
            popupWindow.update();
        }
    }

    @Override
    public void closeRefresh() {
        if(srl.isRefreshing()){
            srl.setRefreshing(false);
        }
    }

    @Override
    public void updateData(String data) {
        if(srl.isRefreshing()){
            srl.setRefreshing(false);
        }
        SnackBarUtils.showWithoutButtonShortWithHeight(getActivity().findViewById(R.id.cardView),data);
    }


    @OnClick({R.id.tv_subject, R.id.tv_rank, R.id.tv_huodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_subject:
                popListViewAdapter.setData(subjects);
                popListViewAdapter.notifyDataSetChanged();
                showPopWindow();
                break;
            case R.id.tv_rank:
                popListViewAdapter.setData(ranks);
                popListViewAdapter.notifyDataSetChanged();
                showPopWindow();
                break;
            case R.id.tv_huodong:
                popListViewAdapter.setData(huodongs);
                popListViewAdapter.notifyDataSetChanged();
                showPopWindow();
                break;
        }
    }

}
