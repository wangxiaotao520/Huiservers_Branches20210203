package com.huacheng.huiservers.ui.index.oldservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 我的手环页面
 * created by wangxiaotao
 * 2020/9/29 0029 11:00
 */
public class MyWristbandsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_last_time)
    TextView tvLastTime;
    @BindView(R.id.tv_battery)
    ImageView tvBattery;
    @BindView(R.id.tv_battery_percent)
    TextView tvBatteryPercent;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    private AMap aMap;
    private UiSettings mUiSettings;

    @BindView(R.id.ll_title_layout)
    LinearLayout llTitleLayout;
    @BindView(R.id.fl_footprint)
    FrameLayout flFootprint;
    @BindView(R.id.fl_foot_count)
    FrameLayout flFootCount;
    @BindView(R.id.fl_heart_count)
    FrameLayout flHeartCount;
    @BindView(R.id.fl_heart_presure)
    FrameLayout flHeartPresure;
    @BindView(R.id.fl_more)
    FrameLayout flMore;
    private TextView txt_right1;

    //位置 测试
    LatLng latLng = new LatLng(39.981167, 116.345103);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        findTitleViews();
        txt_right1 = findViewById(R.id.txt_right1);
        txt_right1.setVisibility(View.VISIBLE);
        txt_right1.setText("解绑");
        titleName.setText("我的手环");
//        rlTitleLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MyHardWareFunctionListActivity.class);
//                startActivity(intent);
//              //  overridePendingTransition(R.anim.slide_top_in,R.anim.slide_top_out);
//            }
//        });
        txt_right1.setOnClickListener(this);
        flFootprint.setOnClickListener(this);
        flFootCount.setOnClickListener(this);
        flHeartCount.setOnClickListener(this);
        flHeartPresure.setOnClickListener(this);
        flMore.setOnClickListener(this);
        llRefresh.setOnClickListener(this);
        initMap();
        requestData();
    }

    /**
     * 请求服务器数据
     */
    private void requestData() {
        //TODO 这个根据服务器数据返回
        latLng = new LatLng(37.68888, 112.728371);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 15, 0, 0));
        aMap.moveCamera(mCameraUpdate);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        View markerView = LayoutInflater.from(this).inflate(R.layout.item_marker_view, mMapView, false);
//        Bitmap companyIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_collection);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(companyIcon));
        markerOptions.icon(BitmapDescriptorFactory.fromView(markerView));
        //设置marker锚点偏移量
        markerOptions.anchor(0.5f, 0.5f);
        aMap.addMarker(markerOptions);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 自定义精度范围的圆形边框颜色
//        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));//圆圈的颜色,设为透明的时候就可以去掉圆圈区域了
//
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
//        aMap.setMyLocationStyle(myLocationStyle);

//        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
//        aMap.setMapTextZIndex(2);
        mUiSettings.setZoomControlsEnabled(false);//设置地图默认缩放控件是否显示
//        aMap.setMyLocationEnabled(true);//是否可触发定位并显示定位层e);
    }

    @Override
    protected void initView() {
        //useless
    }

    @Override
    protected void initData() {
        //useless
    }

    @Override
    protected void initListener() {
        // useless
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mywristbands;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_right1:
                //TODO 解绑
                break;
            case R.id.fl_footprint:
                //TODO 查看足迹
                break;
            case R.id.fl_foot_count:
                //TODO 计步统计
                break;
            case R.id.fl_heart_count:
                //TODO 心率
                break;
            case R.id.fl_heart_presure:
                //TODO 血压
                break;
            case R.id.fl_more:
                //TODO 查看更多
                break;
            case R.id.ll_refresh:
                //TODO 刷新
                break;

        }
    }
}
