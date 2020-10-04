package com.huacheng.huiservers.ui.index.oldservice;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Description: 新增围栏页面
 * created by wangxiaotao
 * 2020/10/2 0002 11:14
 */
public class NewAddFenceActivity extends BaseActivity implements AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.et_name)
    EditText etFenceName;
    @BindView(R.id.tv_location_name)
    TextView tvLocationName;
    @BindView(R.id.tv_circle_metre)
    TextView tvCircleMetre;
    @BindView(R.id.ll_circle)
    LinearLayout llCircle;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    private AMap aMap;
    private UiSettings mUiSettings;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    double latitude;
    double longitutd;
    String address="";
    String newAddress="";
    private RxPermissions rxPermissions;
    private Circle circle;
    private Marker localMark;
    private int radius= 1000;
    private GeocodeSearch geocoderSearch;

    private ArrayList<String> options1Item3 = new ArrayList<>();//半径
    private int selected_option= 9;
    private LatLng latLng;
    String title;
    private String[] radius_str = new String[]{};
    private String par_uid="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("新增围栏");
        mMapView.onCreate(savedInstanceState);
        initMap();
        gotoLocation();
        initRadius();
        initListenerNew();
    }

    private void initListenerNew() {
        llCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadiusDialog();
            }
        });
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etFenceName.getText().toString();
                if (NullUtil.isStringEmpty(title)){
                    SmartToast.showInfo("请输入围栏名称");
                    return;
                }
                requestDataCommit();
            }
        });

    }

    /**
     * 请求接口
     */
    private void requestDataCommit() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid",par_uid);
        params.put("title",title);
        params.put("address",newAddress);
        params.put("lon",latLng.longitude+"");
        params.put("lat",latLng.latitude+"");
        params.put("radius",radius+"");
        MyOkHttp.get().post( ApiHttpClient.SET_ENCLOSURE, params, new JsonResponseHandler() {

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

    private void initRadius() {
        radius_str=new String[]{"100","200","300","400","500","600","700","800","900",
                "1000","1100","1200","1300","1400","1500","1600","1700","1800","1900",
                "2000","2100","2200","2300","2400","2500","2600","2700", "2800","2900","3000"};
        for (int i = 0; i < radius_str.length; i++) {
            options1Item3.add(radius_str[i]);
        }

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

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    private void gotoLocation() {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted) throws Exception {
                        if (isGranted) {
                            //权限同意 ,开始定位
                            startLocation();

                        } else {
                            //权限拒绝 ,默认智慧小区
                            new CommomDialog(NewAddFenceActivity.this, R.style.my_dialog_DimEnabled,
                                    "请开启定位权限,否则无法获取经纬度信息", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        startLocation();
                                        dialog.dismiss();
                                    } else {
                                        initData();
                                    }
                                }
                            }).show();

                        }
                    }
                });
    }

    private void startLocation() {
        showDialog(smallDialog);
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();

            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置需要地理位置信息
            mLocationOption.isNeedAddress();
            //     mLocationOption.setOnceLocation(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mLocationOption.setOnceLocation(true);
            mlocationClient.startLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
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
        return R.layout.activity_add_fence;
    }

    @Override
    protected void initIntentData() {
        par_uid=getIntent().getStringExtra("par_uid");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        {
            if (null != amapLocation) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (amapLocation.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + amapLocation.getLocationType() + "\n");
                    sb.append("经    度    : " + amapLocation.getLongitude() + "\n");
                    sb.append("纬    度    : " + amapLocation.getLatitude() + "\n");
                    sb.append("精    度    : " + amapLocation.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + amapLocation.getProvider() + "\n");

                    sb.append("速    度    : " + amapLocation.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + amapLocation.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + amapLocation.getSatellites() + "\n");
                    sb.append("国    家    : " + amapLocation.getCountry() + "\n");
                    sb.append("省            : " + amapLocation.getProvince() + "\n");
                    sb.append("市            : " + amapLocation.getCity() + "\n");
                    sb.append("城市编码 : " + amapLocation.getCityCode() + "\n");
                    sb.append("区            : " + amapLocation.getDistrict() + "\n");
                    sb.append("区域 码   : " + amapLocation.getAdCode() + "\n");
                    sb.append("地    址    : " + amapLocation.getAddress() + "\n");
                    sb.append("兴趣点    : " + amapLocation.getPoiName() + "\n");
                    //定位完成的时间
                    // sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                    if (latitude == 0) {
                        latitude = amapLocation.getLatitude();
                        longitutd = amapLocation.getLongitude();
                        address=amapLocation.getAddress();
                        newAddress=address;
                        tvLocationName.setText(address+"");
                        // showDialog(smallDialog);
                        initFirstLocation();
                    }

                    //  privLocation = amapLocation;
                } else {
                    hideDialog(smallDialog);
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + amapLocation.getErrorCode() + "\n");
                    sb.append("错误信息:" + amapLocation.getErrorInfo() + "\n");
                    sb.append("错误描述:" + amapLocation.getLocationDetail() + "\n");
                    new CommomDialog(NewAddFenceActivity.this, R.style.my_dialog_DimEnabled,
                            "定位失败请重新定位", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                                finish();
                            } else {

                            }
                        }
                    }).show();


                }

            } else {
                // tvResult.setText("定位失败，loc is null");
                hideDialog(smallDialog);
                new CommomDialog(NewAddFenceActivity.this, R.style.my_dialog_DimEnabled,
                        "定位失败请重新定位", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            finish();
                        } else {

                        }
                    }
                }).show();

            }
        }
    }

    /**
     * 刚进页面时初次定位
     */
    private void initFirstLocation() {
        hideDialog(smallDialog);
        double last_latitude = latitude;
        double last_longitude = longitutd;
        //定位到当前位置
        latLng = new LatLng(last_latitude, last_longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, (float) 14, 0, 0));
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

        drawCircle(radius, latLng);
        initCircleListener();
    }

    private void initCircleListener() {
        //地图移动，自定义Maker始终处于地图中心点
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (localMark != null) {
                    LatLng latLng = cameraPosition.target;
                    localMark.setPosition(latLng);
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                removeMarksFromMap();//移动结束的时候，先移除地图上Maker(除了处于地图中心的Maker)
                //重新定位到
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(cameraPosition.target);
                View markerView = LayoutInflater.from(mContext).inflate(R.layout.item_marker_view_fence, mMapView, false);
                markerOptions.icon(BitmapDescriptorFactory.fromView(markerView));
                //设置marker锚点偏移量
                markerOptions.anchor(0.5f, 0.5f);
                localMark = aMap.addMarker(markerOptions);
                latLng=cameraPosition.target;
                drawCircle(radius, cameraPosition.target);
                getGeocodeSearch(cameraPosition.target);
            }
        });
    }

    /**
     * 根据经纬度获取地址
     * @param target
     */
    private void getGeocodeSearch(LatLng target) {

        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(target.latitude,target.longitude), 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    /**
     * 画圆
     *
     * @param metre
     */
    private void drawCircle(int metre, LatLng latLng) {
        //添加圆形面范围标识
        tvCircleMetre.setText(metre+"");
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.strokeWidth(1);
        circleOptions.strokeColor(Color.parseColor("#45A0e5"));
        circleOptions.fillColor(Color.parseColor("#4D45A0e5"));
        circleOptions.radius(metre);
        circle = aMap.addCircle(circleOptions);
    }


    //移除地图上除中心点的所有Maker
    private void removeMarksFromMap() {
        List<Marker> saveMarkerList = aMap.getMapScreenMarkers();//获得所有地图上所有Maker.
        for (Marker marker : saveMarkerList) {
            marker.remove();
        }
        circle.remove();
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                newAddress = result.getRegeocodeAddress().getFormatAddress();
                tvLocationName.setText(newAddress+"");

            } else {
              //  ToastUtil.show(ReGeocoderActivity.this, R.string.no_result);
                SmartToast.showInfo("获取地址信息失败");
            }
        } else {
            SmartToast.showInfo("获取地址信息失败");
        }
    }
    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

    }

    /**
     * 半径选择
     */
    private void showRadiusDialog() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(NewAddFenceActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Item3.get(options1);
                int parseInt = Integer.parseInt(tx);
                radius=parseInt;
                selected_option=options1;
                circle.remove();
                drawCircle(radius,latLng);

            }
        }).setTitleText("请选择")//标题文字
                .setTitleColor(this.getResources().getColor(R.color.title_color))
                .setSubmitColor(this.getResources().getColor(R.color.orange))//确定按钮文字颜色
                .setCancelColor(this.getResources().getColor(R.color.title_sub_color))
                .setContentTextSize(18).setSelectOptions(selected_option).build();//取消按钮文字颜色;
        pvOptions.setPicker(options1Item3);
        pvOptions.show();
    }

}
