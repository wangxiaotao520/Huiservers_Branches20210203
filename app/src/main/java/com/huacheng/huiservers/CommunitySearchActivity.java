package com.huacheng.huiservers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCoummnityList;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.bean.ModelRegion;
import com.huacheng.huiservers.ui.common.AdapterCoummunityList;
import com.huacheng.huiservers.utils.GetJsonDataUtil;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 搜索小区
 * created by wangxiaotao
 * 2019/11/8 0008 下午 7:56
 */
public class CommunitySearchActivity extends BaseActivity implements AdapterCoummunityList.OnClickCommunityListListener, PoiSearch.OnPoiSearchListener {
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private List<ModelCoummnityList> mDatas = new ArrayList<>();
    private AdapterCoummunityList adapter;
    private TextView tv_district;
    private EditText et_search;
    private TextView tv_cancel;
    private ArrayList<ModelRegion> jsonBean;
    private ArrayList<String> options1Items = new ArrayList<>();//省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private int selected_options1, selected_options2, selected_options3;//默认选中
    private String location_provice, location_district, location_city,location_code;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread_parse_json;

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_SUCCESS:
                    hideDialog(smallDialog);
                    //   showCityWheel();
                    break;
                case MSG_LOAD_FAILED:
                    hideDialog(smallDialog);
                    SmartToast.showInfo("解析失败");
                    break;
                default:
                    break;
            }
        }
    };
    private String search_text;
    SharePrefrenceUtil prefrenceUtil;

    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        tv_district= findViewById(R.id.tv_district);
        if (NullUtil.isStringEmpty(location_district)){
            tv_district.setText("北京市");
        }else {
            tv_district.setText(location_district);
        }
        et_search= findViewById(R.id.et_search);
        tv_cancel= findViewById(R.id.tv_cancel);

        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        adapter = new AdapterCoummunityList(this, R.layout.item_community_list, mDatas,this,1);
        mListview.setAdapter(adapter);
        mRelNoData=findViewById(R.id.rel_no_data);
    }

    @Override
    protected void initData() {
        //解析json
        parseJson();
    }

    @Override
    protected void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (options1Items.size() > 0) {
                    showCityWheel();
                }
                new ToolUtils(et_search, mContext).closeInputMethod();
                et_search.clearFocus();
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {

                    case EditorInfo.IME_ACTION_SEARCH:
                        search_text = et_search.getText().toString().trim()+"";
                        if (NullUtil.isStringEmpty(search_text)){
                            SmartToast.showInfo("搜索内容不能为空");
                        }else {
                            currentPage = 0;
                            showDialog(smallDialog);
                            doAMapSearch(search_text);
                        }
                        break;
                    default:
                        isOK = false;
                        break;

                }
                return isOK;
            }});

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                doAMapSearch(search_text);
            }
        });
    }

    private void doAMapSearch(String search_text) {
        new ToolUtils(et_search, this).closeInputMethod();
        if (NullUtil.isStringEmpty(location_district)){
            query = new PoiSearch.Query(search_text, "商务住宅", "北京市");
        }else {
            query = new PoiSearch.Query(search_text, "商务住宅", location_district);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
         //   query = new PoiSearch.Query(search_text, "住宅区", location_code);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）

        }
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 显示三级城市联动
     */
    private void showCityWheel() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                selected_options1 = options1;
                selected_options2 = option2;
                selected_options3 = options3;
                if (jsonBean != null && jsonBean.size() > 0) {
                    if (jsonBean.get(options1).getS_list().get(option2).getSs_list().size()==0) {
                        return;
                    }
                    tv_district.setText( "" +
                            jsonBean.get(options1).getS_list().get(option2).getSs_list().get(options3).getRegion_name());

                    location_provice=jsonBean.get(options1).getRegion_name();
                    location_city=jsonBean.get(options1).getS_list().get(option2).getRegion_name();
                    location_district=jsonBean.get(options1).getS_list().get(option2).getSs_list().get(options3).getRegion_name();
                }
            }
        }).setSubmitColor(this.getResources().getColor(R.color.orange))//确定按钮文字颜色
                .setCancelColor(this.getResources().getColor(R.color.graynew))
                .build();//取消按钮文字颜色;
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.setSelectOptions(selected_options1, selected_options2, selected_options3);
        pvOptions.show();

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coummunity_search;
    }

    @Override
    protected void initIntentData() {
        location_provice= getIntent().getStringExtra("location_provice");
        location_city= getIntent().getStringExtra("location_city");
        location_district=getIntent().getStringExtra("location_district");
        location_code=getIntent().getStringExtra("location_code");


    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onListClickRelocation() {

    }

    @Override
    public void onClickListItem(ModelCoummnityList item, int position) {
      requestCommunityId(item);
    }

    /**
     * 根据小区名字请求小区id
     * @param item
     */
    private void requestCommunityId(final ModelCoummnityList item) {
        showDialog(smallDialog);
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
                            if (!NullUtil.isStringEmpty(data.getString("id"))){//匹配失败
                                prefrenceUtil.setXiaoQuId(data.getString("id"));
                                prefrenceUtil.setCompanyId(data.getString("company_id"));
                            }
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            EventBus.getDefault().post(new ModelEventHome(2));
                            setResult(RESULT_OK);
                            mListview.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentXiaoQuA = new Intent(mContext, HomeActivity.class);
                                    startActivity(intentXiaoQuA);
                                    finish();
                                }
                            },200);
                            getsubmitCommunityId(data.getString("id"));
                        }else {
                            //匹配失败
                            prefrenceUtil.clearPreference(mContext);
                            prefrenceUtil.setXiaoQuId("");
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            EventBus.getDefault().post(new ModelEventHome(2));
                            setResult(RESULT_OK);
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
                        EventBus.getDefault().post(new ModelEventHome(2));
                        setResult(RESULT_OK);
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
    /**
     * 解析cityjson
     */
    private void parseJson() {
        showDialog(smallDialog);
        if (thread_parse_json == null) {
            thread_parse_json = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 子线程中解析省市区数据
                    initJsonData();
                }
            });
        }
        thread_parse_json.start();
    }
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "region.json");//获取assets目录下的json文件数据

        //用Gson 转成实体
        jsonBean = parseData(JsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for (int i = 0; i < jsonBean.size(); i++) {
            options1Items.add(jsonBean.get(i).getRegion_name());
            if (jsonBean.get(i).getRegion_name().equals(location_provice)) {
                selected_options1 = i;
            }
        }
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getS_list().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getS_list().get(c).getRegion_name();
                CityList.add(CityName);//添加城市
                //默认选中
                if (CityName.equals(location_city)) {
                    selected_options2 = c;
                }
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getS_list().get(c).getSs_list() == null
                        || jsonBean.get(i).getS_list().get(c).getSs_list().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int i1 = 0; i1 < jsonBean.get(i).getS_list().get(c).getSs_list().size(); i1++) {
                        City_AreaList.add(jsonBean.get(i).getS_list().get(c).getSs_list().get(i1).getRegion_name());
                        if (jsonBean.get(i).getS_list().get(c).getSs_list().get(i1).getRegion_name().equals(location_district)) {
                            selected_options3 = i1;
                        }
                    }

                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<ModelRegion> parseData(String result) {//Gson 解析
        ArrayList<ModelRegion> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ModelRegion entity = gson.fromJson(data.optJSONObject(i).toString(), ModelRegion.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        hideDialog(smallDialog);// 隐藏对话框
        mRefreshLayout.finishLoadMore();
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        // 数据不为空
                        mRelNoData.setVisibility(View.GONE);
                        ArrayList<ModelCoummnityList> nearby_list = new ArrayList<>();
                        for (int i1 = 0; i1 < poiItems.size(); i1++) {
                            ModelCoummnityList modelCoummnityList_bean = new ModelCoummnityList();
                            modelCoummnityList_bean.setType(3);
                            modelCoummnityList_bean.setName(poiItems.get(i1).toString()+"");
                            modelCoummnityList_bean.setAddress(poiItems.get(i1).getSnippet()+"");
                            modelCoummnityList_bean.setId("");
                            modelCoummnityList_bean.setPosition(i1);
                            nearby_list.add(modelCoummnityList_bean);
                        }
                        if (currentPage==0){
                            mDatas.clear();
                        }
                        currentPage++;
                        if (nearby_list.size()<10){
                            mRefreshLayout.setEnableLoadMore(false);
                        }else {
                            mRefreshLayout.setEnableLoadMore(true);
                        }
                        mDatas.addAll(nearby_list);
                        adapter.notifyDataSetChanged();
                    } else {
                        // 数据为空
                        mRelNoData.setVisibility(View.VISIBLE);
                        if (currentPage==0){
                            mDatas.clear();
                        }
                        mRefreshLayout.setEnableLoadMore(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
              //  SmartToast.showInfo("网络异常");
            }


    }

    @Override
    public void onPoiItemSearched(PoiItem item, int i) {

    }

    @Override
    protected void onDestroy() {
        if (et_search!=null){
            new ToolUtils(et_search, mContext).closeInputMethod();
        }
        super.onDestroy();
    }
}
