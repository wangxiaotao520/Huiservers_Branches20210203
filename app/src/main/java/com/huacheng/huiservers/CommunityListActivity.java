package com.huacheng.huiservers;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCoummnityList;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.common.AdapterCoummunityList;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description: 选择小区列表 v4.2.2
 * created by wangxiaotao
 * 2019/11/8 0008 上午 10:29
 */
public class CommunityListActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener, PoiSearch.OnPoiSearchListener, AdapterCoummunityList.OnClickCommunityListListener {
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private View iv_right;
    private List<ModelCoummnityList>mDatas = new ArrayList<>();
    private AdapterCoummunityList adapter;
    private LinearLayout ll_no_data;
    private TextView tv_setting_location;
    private TextView tv_empty_relocation;
    private ImageView iv_empty_relocation;
    private TextView tv_empty_community_name;
    private TextView tv_empty_community_address;
    private RxPermissions rxPermissions;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isInitLocaion = false;
    SharePrefrenceUtil prefrenceUtil;

    private String location_provice="", location_district="", location_city="",location_code="";//
    private int jump_type = 1;  //1从首页点进来 2.从商城选择收货地址点进来
    private String cat_search_name="商务住宅";
    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        findTitleViews();
        titleName.setText("选择小区");
        if (jump_type==2){
            titleName.setText("选择收货地址");
        }
        iv_right = findViewById(R.id.iv_right);
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        adapter = new AdapterCoummunityList(this, R.layout.item_community_list, mDatas,this,0,jump_type);
        mListview.setAdapter(adapter);
        ll_no_data = findViewById(R.id.ll_no_data);
        ll_no_data.setVisibility(View.GONE);
        tv_empty_community_name = findViewById(R.id.tv_empty_community_name);
        tv_empty_community_address = findViewById(R.id.tv_empty_community_address);
        iv_empty_relocation = findViewById(R.id.iv_empty_relocation);
        tv_empty_relocation = findViewById(R.id.tv_empty_relocation);
        tv_setting_location = findViewById(R.id.tv_setting_location);
        rxPermissions=new RxPermissions(this);
        initLocation();
    }
    private void initLocation() {
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

        }

    }
    @Override
    protected void initData() {
        //1.首先进来看有没有开定位
        //2.没开定位的情况 listview隐藏 空白显示 空白布局显示当前小区
        //3.开了定位后 高德定位附近小区  再判断登录情况 ，如果登录了请求我的小区 ，最后拼成一个集合显示
        rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted) throws Exception {
                        if (isGranted) {
                            //权限同意 ,开始定位
                            mListview.setVisibility(View.VISIBLE);
                            ll_no_data.setVisibility(View.GONE);
                            showDialog(smallDialog);
                            smallDialog.setTipTextView("定位中...");
                            mlocationClient.startLocation();

                        } else {
                            //权限拒绝 ,默认智慧小区
                            mListview.setVisibility(View.GONE);
                            ll_no_data.setVisibility(View.VISIBLE);
                            tv_empty_community_name.setText(prefrenceUtil.getXiaoQuName()+"");
                            // 还得加地址
                            if (!NullUtil.isStringEmpty(prefrenceUtil.getAddressName())){
                                tv_empty_community_address.setVisibility(View.VISIBLE);
                                tv_empty_community_address.setText(prefrenceUtil.getAddressName());
                            }else {
                                tv_empty_community_address.setVisibility(View.GONE);
                            }

                        }
                    }
                });


    }

    @Override
    protected void initListener() {
        iv_right.setOnClickListener(this);
        iv_empty_relocation.setOnClickListener(this);
        tv_empty_relocation.setOnClickListener(this);
        tv_setting_location.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coummunity_list;
    }

    @Override
    protected void initIntentData() {
        this.jump_type=this.getIntent().getIntExtra("jump_type",1);
        if (jump_type==2){
            cat_search_name="";
        }
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_right:
                // 搜索
                Intent intent = new Intent(this, CommunitySearchActivity.class);
                intent.putExtra("location_provice",location_provice+"");
                intent.putExtra("location_city",location_city+"");
                intent.putExtra("location_district",location_district+"");
                intent.putExtra("location_code",location_code+"");
                intent.putExtra("jump_type",jump_type);

                startActivityForResult(intent,111);
                break;
            case R.id.iv_empty_relocation:
            case R.id.tv_empty_relocation:
            case R.id.tv_setting_location:
                // 空白页重新定位
                rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isGranted) throws Exception {
                                if (isGranted) {
                                    //权限同意 ,开始定位
                                    showDialog(smallDialog);
                                    smallDialog.setTipTextView("定位中...");
                                    isInitLocaion=false;
                                    mlocationClient.startLocation();
                                } else {
                                    //权限拒绝

                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (null != location) {
            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");

                /*sharePrefrenceUtil.setLongitude(location.getLongitude() + "");
                sharePrefrenceUtil.setAtitude(location.getLatitude() + "");*/

                //定位完成的时间
                //  sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                if (!isInitLocaion) {
                    if (NullUtil.isStringEmpty(location.getDistrict())){
//                        hideDialog(smallDialog);
//                        // tvResult.setText("定位失败，loc is null");
//                        text_city.setText("定位失败...点击重新定位");
//                        text_city.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startLocation();
//                            }
//                        });
                    }else {
                        isInitLocaion = true;
                        location_provice = location.getProvince() + "";
                        location_city = location.getCity() + "";
                        location_district = location.getDistrict() + "";
                        location_code=location.getCityCode();
                        //默认选中
                        mlocationClient.stopLocation();
                        getPOIsearch(location.getLongitude(),location.getLatitude());

                    }
                }
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
                hideDialog(smallDialog);
                // tvResult.setText("定位失败，loc is null");
                //定位失败 显示智慧小区
                mListview.setVisibility(View.GONE);
                ll_no_data.setVisibility(View.VISIBLE);
                tv_empty_community_name.setText(prefrenceUtil.getXiaoQuName()+"");
                // 还得加地址
                if (!NullUtil.isStringEmpty(prefrenceUtil.getAddressName())){
                    tv_empty_community_address.setVisibility(View.VISIBLE);
                    tv_empty_community_address.setText(prefrenceUtil.getAddressName());
                }else {
                    tv_empty_community_address.setVisibility(View.GONE);
                }
            }

        } else {
            hideDialog(smallDialog);
            // tvResult.setText("定位失败，loc is null");
            //定位失败 显示智慧小区
            mListview.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
            tv_empty_community_name.setText(prefrenceUtil.getXiaoQuName()+"");
            // 还得加地址
            if (!NullUtil.isStringEmpty(prefrenceUtil.getAddressName())){
                tv_empty_community_address.setVisibility(View.VISIBLE);
                tv_empty_community_address.setText(prefrenceUtil.getAddressName());
            }else {
                tv_empty_community_address.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 调用高德地图搜索周边住宅
     * @param longitude
     * @param latitude
     */
    private void getPOIsearch(double longitude, double latitude) {
        PoiSearch.Query query = new PoiSearch.Query("", cat_search_name, "");
        query.setPageSize(15);
        PoiSearch search = new PoiSearch(this, query);
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int i) {
        PoiSearch.Query query = result.getQuery();
        ArrayList<PoiItem> pois = result.getPois();

        if (pois!=null&&pois.size()>0){

            // 如果用户登录了还得请求数据
            if (LoginUtils.hasLoginUser()){
                requestMyCommunity(pois);
            }else {
                hideDialog(smallDialog);
                mListview.setVisibility(View.VISIBLE);
                ll_no_data.setVisibility(View.GONE);
                mDatas.clear();
                //当前小区
                ModelCoummnityList modelCoummnityList = new ModelCoummnityList();
                modelCoummnityList.setType(1);
                modelCoummnityList.setName(prefrenceUtil.getXiaoQuName()+"");
                modelCoummnityList.setAddress(prefrenceUtil.getAddressName()+"");
                modelCoummnityList.setId(prefrenceUtil.getXiaoQuId()+"");
                modelCoummnityList.setPosition(0);
                mDatas.add(modelCoummnityList);
                //附近小区
                ArrayList<ModelCoummnityList> nearby_list = new ArrayList<>();
                for (int i1 = 0; i1 < pois.size(); i1++) {
                    ModelCoummnityList modelCoummnityList_bean = new ModelCoummnityList();
                    modelCoummnityList_bean.setType(3);
                    modelCoummnityList_bean.setName(pois.get(i1).toString()+"");
                    modelCoummnityList_bean.setAddress(pois.get(i1).getSnippet()+"");
                    modelCoummnityList_bean.setId("");
                    modelCoummnityList_bean.setPosition(i1);
                    nearby_list.add(modelCoummnityList_bean);
                }
                mDatas.addAll(nearby_list);
                adapter.notifyDataSetChanged();
            }
        }else {
            hideDialog(smallDialog);
            mListview.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
            tv_empty_community_name.setText(prefrenceUtil.getXiaoQuName()+"");
            // 还得加地址
            if (!NullUtil.isStringEmpty(prefrenceUtil.getAddressName())){
                tv_empty_community_address.setVisibility(View.VISIBLE);
                tv_empty_community_address.setText(prefrenceUtil.getAddressName());
            }else {
                tv_empty_community_address.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取我的小区
     * @param pois
     */
    private void requestMyCommunity(final ArrayList<PoiItem> pois) {
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.GET_MY_DISTRICT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                //   view_alpha.setAlpha(0.6f);
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelCoummnityList>data_my = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCoummnityList.class);
                    mDatas.clear();
                    //当前小区
                    ModelCoummnityList modelCoummnityList = new ModelCoummnityList();
                    modelCoummnityList.setType(1);
                    modelCoummnityList.setName(prefrenceUtil.getXiaoQuName()+"");
                    modelCoummnityList.setAddress(prefrenceUtil.getAddressName()+"");
                    modelCoummnityList.setId(prefrenceUtil.getXiaoQuId()+"");
                    modelCoummnityList.setPosition(0);
                    mDatas.add(modelCoummnityList);
                    //我的小区
                    List<ModelCoummnityList>data_my_list = new ArrayList<>();
                    if (data_my!=null&&data_my.size()>0){
                        for (int i = 0; i < data_my.size(); i++) {
                            ModelCoummnityList modelCoummnityList_bean = new ModelCoummnityList();
                            modelCoummnityList_bean.setType(2);
                            modelCoummnityList_bean.setName(data_my.get(i).getCommunity_name());
                            modelCoummnityList_bean.setAddress(data_my.get(i).getFull_address()+"");
                            modelCoummnityList_bean.setId("");
                            modelCoummnityList_bean.setPosition(i);
                            modelCoummnityList_bean.setProvince_name(data_my.get(i).getProvince_name());
                            modelCoummnityList_bean.setCity_name(data_my.get(i).getCity_name());
                            modelCoummnityList_bean.setArea_name(data_my.get(i).getArea_name());
                            data_my_list.add(modelCoummnityList_bean);
                        }
                    }
                    mDatas.addAll(data_my_list);
                    //附近小区
                    ArrayList<ModelCoummnityList> nearby_list = new ArrayList<>();
                    for (int i1 = 0; i1 < pois.size(); i1++) {
                        ModelCoummnityList modelCoummnityList_bean = new ModelCoummnityList();
                        modelCoummnityList_bean.setType(3);
                        modelCoummnityList_bean.setName(pois.get(i1).toString()+"");
                        modelCoummnityList_bean.setAddress(pois.get(i1).getSnippet()+"");
                        modelCoummnityList_bean.setId("");
                        modelCoummnityList_bean.setPosition(i1);
                        nearby_list.add(modelCoummnityList_bean);
                    }
                    mDatas.addAll(nearby_list);
                    adapter.notifyDataSetChanged();

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });


    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    @Override
    public void onListClickRelocation() {
        showDialog(smallDialog);
        smallDialog.setTipTextView("定位中...");
        isInitLocaion=false;
        mlocationClient.startLocation();
    }

    @Override
    public void onClickListItem(ModelCoummnityList item, int position) {

        if (jump_type==1){
            //切换小区
            if (item.getType()==1){//当前小区
                finish();
            } else if (item.getType()==2){
                //我的小区
                requestCommunityId(item,item.getType());

            }else {
                //附近小区
                requestCommunityId(item,item.getType());
            }
        }else if (jump_type==2){//收货地址
            if (item.getType()==1){//当前小区
                Intent intent = new Intent();
                intent.putExtra("location_provice",prefrenceUtil.getProvince_cn()+"");
                intent.putExtra("location_city",prefrenceUtil.getCity_cn()+"");
                intent.putExtra("location_district",prefrenceUtil.getRegion_cn());
                intent.putExtra("name",item.getName()+"");
                setResult(RESULT_OK,intent);
                finish();
            } else if (item.getType()==2){
                //我的小区
                Intent intent = new Intent();
                intent.putExtra("location_provice",item.getProvince_name()+"");
                intent.putExtra("location_city",item.getCity_name()+"");
                intent.putExtra("location_district",item.getArea_name());
                intent.putExtra("name",item.getName()+"");
                setResult(RESULT_OK,intent);
                finish();

            }else {
                //附近小区
                Intent intent = new Intent();
                intent.putExtra("location_provice",location_provice);
                intent.putExtra("location_city",location_city);
                intent.putExtra("location_district",location_district);
                intent.putExtra("name",item.getName()+"");
                setResult(RESULT_OK,intent);
                finish();
            }

        }

    }

    /**
     * 根据小区名字请求小区id
     * @param item
     */
    private void requestCommunityId(final ModelCoummnityList item, final int type) {
        showDialog(smallDialog);
        smallDialog.setTipTextView("加载中...");
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(item.getName())){
            params.put("community_name",item.getName()+"");
        }
        MyOkHttp.get().post(ApiHttpClient.GET_COMMUNITY_ID, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    try {
                        if (response.has("data")){
                            //匹配成功
                            JSONObject data = response.getJSONObject("data");
                            prefrenceUtil.clearPreference(mContext);
                            if (!NullUtil.isStringEmpty(data.getString("id"))){
                                prefrenceUtil.setXiaoQuId(data.getString("id"));
                                prefrenceUtil.setCompanyId(data.getString("company_id"));
                            }
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            //保存省市区
                            if (type==2){
                                // 说明点击的是我的小区
                                prefrenceUtil.setProvince_cn(item.getProvince_name()+"");
                                prefrenceUtil.setCity_cn(item.getCity_name()+"");
                                prefrenceUtil.setRegion_cn(item.getArea_name());
                            }else {
                                prefrenceUtil.setProvince_cn(location_provice);
                                prefrenceUtil.setCity_cn(location_city);
                                prefrenceUtil.setRegion_cn(location_district);
                            }
                            EventBus.getDefault().post(new ModelEventHome(2));
                            mListview.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentXiaoQuA = new Intent(mContext, HomeActivity.class);
                                    startActivity(intentXiaoQuA);
                                    finish();
                                }
                            },200);
                            //匹配成功提交一下
                            getsubmitCommunityId(data.getString("id"));
                        }else {
                            //匹配失败
                            prefrenceUtil.clearPreference(mContext);
                            prefrenceUtil.setXiaoQuId("");
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            //保存省市区
                            if (type==2){
                                // 说明点击的是我的小区
                                prefrenceUtil.setProvince_cn(item.getProvince_name()+"");
                                prefrenceUtil.setCity_cn(item.getCity_name()+"");
                                prefrenceUtil.setRegion_cn(item.getArea_name());
                            }else {
                                prefrenceUtil.setProvince_cn(location_provice);
                                prefrenceUtil.setCity_cn(location_city);
                                prefrenceUtil.setRegion_cn(location_district);
                            }

                            EventBus.getDefault().post(new ModelEventHome(2));
                            mListview.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentXiaoQuA = new Intent(mContext, HomeActivity.class);
                                    startActivity(intentXiaoQuA);
                                    finish();
                                }
                            },200);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //匹配失败
                      //  data==null
                        prefrenceUtil.clearPreference(mContext);
                        prefrenceUtil.setXiaoQuId("");
                        prefrenceUtil.setXiaoQuName(item.getName());
                        prefrenceUtil.setAddressName(item.getAddress());
                        //保存省市区
                        if (type==2){
                            // 说明点击的是我的小区
                            prefrenceUtil.setProvince_cn(item.getProvince_name()+"");
                            prefrenceUtil.setCity_cn(item.getCity_name()+"");
                            prefrenceUtil.setRegion_cn(item.getArea_name());
                        }else {
                            prefrenceUtil.setProvince_cn(location_provice);
                            prefrenceUtil.setCity_cn(location_city);
                            prefrenceUtil.setRegion_cn(location_district);
                        }

                        EventBus.getDefault().post(new ModelEventHome(2));
                        mListview.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intentXiaoQuA = new Intent(mContext, HomeActivity.class);
                                startActivity(intentXiaoQuA);
                                finish();
                            }
                        },200);
                    }

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }
    //提交小区id
    private void getsubmitCommunityId(String community_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("community_id", community_id+"");

        MyOkHttp.get().post(ApiHttpClient.SELECT_COMMUNITY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                if (jump_type==1){
                    finish();
                }else if (jump_type==2){
                    if (data!=null){
                        setResult(RESULT_OK,data);
                        finish();
                    }
                }
            }
        }
    }
}
