package com.zw.happybuy.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zw.happybuy.R;
import com.zw.happybuy.common.App;
import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.listener.MyLocationListener;
import com.zw.happybuy.utils.SPUtils;
import com.zw.happybuy.utils.rxbus.RxBus;
import com.zw.happybuy.utils.rxbus.RxBusSubscriber;
import com.zw.happybuy.utils.rxbus.RxBusTransformer;
import com.zw.happybuy.utils.rxbus.event.RxBusLocationEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class LocationActivity extends RxAppCompatActivity {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.map_view)
    MapView mapView;
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private BDLocation location;
    private BaiduMap baiduMap;

    private boolean isFirstIn = true;
    private View addViewContent;
    private Marker locationMarker;
    private double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addToActivities(this);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initBaiduMap();
    }

    private void initBaiduMap() {
        addViewContent = LayoutInflater.from(this).inflate(R.layout.layout_baidu_marker,null);

        baiduMap = mapView.getMap();
        //普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener( myListener );
        initLocation();


        String latString = SPUtils.getString("lat","");
        String lonString = SPUtils.getString("lon","");
        if(!TextUtils.isEmpty(latString) && !TextUtils.isEmpty(lonString)){
            lat = Double.valueOf(latString);
            lon = Double.valueOf(lonString);
            setCenterLocation();
        }

        RxBus.getInstance().tObservable(RxBusLocationEvent.class)
                .compose(new RxBusTransformer<RxBusLocationEvent,RxBusLocationEvent>(this))
                .subscribe(new RxBusSubscriber<RxBusLocationEvent>() {
                    @Override
                    protected void onReceiveEvent(RxBusLocationEvent rxBusLocationEvent) {

                        location = rxBusLocationEvent.getLocation();
                        double latNew = location.getLatitude();
                        double lonNew = location.getLongitude();
                        if(!TextUtils.isEmpty(location.getCity()) && isFirstIn){

                            lat = latNew;
                            lon = lonNew;

                            setCenterLocation();
                            isFirstIn = false;
                            LatLng latLng = new LatLng(lat,lon);

                            //构建Marker图标
                            String title = location.getStreet() + location.getStreetNumber()
                                    + (TextUtils.isEmpty(location.getStreet()) ? "" : "号");
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.mipmap.now_location);
                            OverlayOptions options = new MarkerOptions()
                                    .position(latLng)  //设置marker的位置
                                    .zIndex(9)
                                    .icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(title)));  //设置marker所在层级

                            //将marker添加到地图上
                            locationMarker = (Marker) baiduMap.addOverlay(options);
                        } else if(!TextUtils.isEmpty(location.getCity()) && !isFirstIn){
                            if(locationMarker != null && (lat != latNew || lon != lonNew) ){
                                lat = latNew;
                                lon = lonNew;

                                locationMarker.remove();
                                //构建Marker图标
                                String title = location.getStreet() + location.getStreetNumber()
                                        + (TextUtils.isEmpty(location.getStreet()) ? "" : "号");
                                BitmapDescriptor bitmap = BitmapDescriptorFactory
                                        .fromResource(R.mipmap.now_location);
                                OverlayOptions options = new MarkerOptions()
                                        .position(new LatLng(lat,lon))  //设置marker的位置
                                        .zIndex(9)
                                        .icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(title)));  //设置marker所在层级
                                //将marker添加到地图上
                                locationMarker = (Marker) baiduMap.addOverlay(options);
                            }
                        }



                    }

                    @Override
                    protected void onFailed(Throwable e) {

                    }
                });

        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000); //请求间隔时间
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);

        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        AppManager.getAppManager().finishCurrentActivity();
    }

    private void setCenterLocation(){
        LatLng latLng = new LatLng(lat,lon);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    private Bitmap getViewBitmap(String title) {
        TextView tv = (TextView) addViewContent.findViewById(R.id.tv_marker);
        tv.setText(title);

        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();

        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            AppManager.getAppManager().finishCurrentActivity();
        }
        return super.onOptionsItemSelected(item);
    }
}
