package com.huacheng.huiservers.ui.index.oldservice;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelFenceList;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 围栏详情
 * created by wangxiaotao
 * 2020/10/3 0003 18:08
 */
public class FenceDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_container)
    RelativeLayout rl_container;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_radius)
    TextView tvRadius;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    private TextView txt_right1;
    private AMap aMap;
    private UiSettings mUiSettings;
    private ModelFenceList modelFenceList;
    private LatLng latLng;
    private Marker localMark;
    private String par_uid="";
    private int jump_type = 0;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fence_detail;
    }

    @Override
    protected void initIntentData() {
        modelFenceList = (ModelFenceList) getIntent().getSerializableExtra("model");
        par_uid=getIntent().getStringExtra("par_uid");
        jump_type=getIntent().getIntExtra("jump_type",0);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("查看围栏");
        txt_right1 = findViewById(R.id.txt_right1);
        txt_right1.setVisibility(View.VISIBLE);
        txt_right1.setText("删除");
        mMapView.onCreate(savedInstanceState);
        tvTitle.setText(modelFenceList.getTitle());
        tvRadius.setText("半径"+modelFenceList.getRadius()+"米内");
        tvLocation.setText(modelFenceList.getAddress()+"");
        rl_container.setVisibility(View.INVISIBLE);
        txt_right1.setOnClickListener(this);

        initMap();
        showMap();
    }

    /**
     * 显示地图
     */
    private void showMap() {
        rl_container.setVisibility(View.VISIBLE);
        double last_latitude = Double.parseDouble(modelFenceList.getLat());
        double last_longitude =Double.parseDouble(modelFenceList.getLon()) ;
        //定位到当前位置
        latLng = new LatLng(last_latitude, last_longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, (float) 13, 0, 0));
        aMap.moveCamera(mCameraUpdate);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        View markerView = LayoutInflater.from(mContext).inflate(R.layout.item_marker_view_fence, mMapView, false);
//        Bitmap companyIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_collection);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(companyIcon));
        markerOptions.icon(BitmapDescriptorFactory.fromView(markerView));
        //设置marker锚点偏移量
        markerOptions.anchor(0.5f, 0.5f);
        localMark = aMap.addMarker(markerOptions);

        drawCircle(Integer.parseInt(modelFenceList.getRadius()), latLng);
    }

    private void drawCircle(int radius, LatLng latLng) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.strokeWidth(1);
        circleOptions.strokeColor(Color.parseColor("#45A0e5"));
        circleOptions.fillColor(Color.parseColor("#4D45A0e5"));
        circleOptions.radius(radius);
        aMap.addCircle(circleOptions);
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



    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }

        mUiSettings.setZoomControlsEnabled(false);//设置地图默认缩放控件是否显示
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_right1:
                // 删除围栏
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确定删除围栏？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            delFence();
                            dialog.dismiss();
                        }
                    }
                }).show();
                break;
        }
    }

    /**
     * 删除围栏
     */
    private void delFence() {

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        String url = "";
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid", par_uid + "");
        }
        if (jump_type==0){
            url=ApiHttpClient.DEL_ENCLOSURE;
        }else {
            url= ApiHttpClient.DEL_ENCLOSURE1;
        }
        params.put("e_id",modelFenceList.getId()+"");
        MyOkHttp.get().post(url , params, new JsonResponseHandler() {


            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"成功"));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"获取数据失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }
}
