package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterServiceCatOne;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterServiceCatTwo;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceCat;
import com.huacheng.huiservers.ui.servicenew.ui.MerchantServiceListActivity;
import com.huacheng.huiservers.ui.servicenew.ui.search.ServicexSearchActivity;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:服务分类fragment
 * created by wangxiaotao
 * 2019/11/14 0014 上午 9:28
 */
public class ServiceFragmentCat extends BaseFragment implements View.OnClickListener, AdapterServiceCatTwo.OnClickGridCatListener {
    SharePrefrenceUtil prefrenceUtil;
    private ListView list_one, list_two;
    private LinearLayout ll_list_left;
    private LinearLayout lin_search;
    View mStatusBar;
    private AdapterServiceCatOne adapterOne;
    private AdapterServiceCatTwo adapterTwo;
    private List<ModelServiceCat> mDatas = new ArrayList<>();
    private int firstVisibleItem = 0;
    private Banner banner;
    private MyCornerImageLoader myImageLoader;
    private List<ModelAds> listAds=new ArrayList<>();
    private View ll_second_root;


    @Override
    public void initView(View view) {
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float) 1);
        lin_search = (LinearLayout) view.findViewById(R.id.lin_search);
        banner = view.findViewById(R.id.banner);
        setBanner();
        lin_search.setOnClickListener(this);
        list_one = (ListView) view.findViewById(R.id.list_one);
        ll_list_left=view.findViewById(R.id.ll_list_left);
        adapterOne = new AdapterServiceCatOne(mActivity, R.layout.shop_cate_one_item, mDatas);
        list_one.setAdapter(adapterOne);
        list_two = (ListView) view.findViewById(R.id.list_two);
        adapterTwo = new AdapterServiceCatTwo(mActivity, R.layout.shop_cate_item, mDatas, this);
        list_two.setAdapter(adapterTwo);
        ll_second_root = view.findViewById(R.id.ll_second_root);
        ll_second_root.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        list_two.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mDatas.size() > 0 && ServiceFragmentCat.this.firstVisibleItem != firstVisibleItem) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (firstVisibleItem == i) {
                            mDatas.get(i).setChecked(true);
                        } else {
                            mDatas.get(i).setChecked(false);
                        }
                    }
                    adapterOne.notifyDataSetChanged();
                    list_one.setSelection(firstVisibleItem);
                    ServiceFragmentCat.this.firstVisibleItem = firstVisibleItem;
                }
            }
        });
        list_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                list_two.setSelection(position);
                list_two.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < mDatas.size(); i++) {
                            if (position == i) {
                                mDatas.get(i).setChecked(true);
                            } else {
                                mDatas.get(i).setChecked(false);
                            }
                        }
                        adapterOne.notifyDataSetChanged();
                    }
                }, 50);

            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //banner
        getBanner();

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        // params.put("community_id",prefrenceUtil.getXiaoQuId());
        MyOkHttp.get().get(ApiHttpClient.SERVICE_CLASSIF, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ll_second_root.setVisibility(View.VISIBLE);
                    List<ModelServiceCat> list = (List<ModelServiceCat>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelServiceCat.class);
                    if (list.size() > 0) {
                        ll_list_left.setBackgroundColor(getResources().getColor(R.color.all_gray));
                        mDatas.clear();
                        list.get(0).setChecked(true);
                        mDatas.addAll(list);
                        adapterOne.notifyDataSetChanged();
                        adapterTwo.notifyDataSetChanged();
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        });
    }

    /**
     * 服务首页bannner
     */
    private void getBanner() {
        HashMap<String, String> params = new HashMap<>();
        // params.put("community_id",prefrenceUtil.getXiaoQuId());
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.put("c_id",prefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().get(ApiHttpClient.INDEX_SERVICE_AD_TOP, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                   List <ModelAds>list = (List<ModelAds>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelAds.class);
                   listAds.clear();
                   listAds.addAll(list);
                   if (list !=null&& list.size() > 0) {
                        banner.setVisibility(View.VISIBLE);

                        ArrayList<String> mDatas_img1 = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            mDatas_img1.add(ApiHttpClient.IMG_URL+ list.get(i).getImg() + "");
                        }
                        banner.update(mDatas_img1);
                    }else {
                        banner.setVisibility(View.GONE);
                    }
                } else {
//                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
//                    SmartToast.showInfo(msg);
                    banner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
//                hideDialog(smallDialog);
//                SmartToast.showInfo("网络异常，请检查网络设置");
                banner.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_cat;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_search://搜索
                Intent intent = new Intent();
                intent.setClass(mContext, ServicexSearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
    private void setBanner() {
        myImageLoader= new MyCornerImageLoader();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(myImageLoader);
        banner.isAutoPlay(true);//设置是否轮播
        banner.setIndicatorGravity(BannerConfig.CENTER);//小圆点位置
        banner.setDelayTime(4500);
        banner.setImageLoader(myImageLoader).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //点击banner
                if (listAds.size()>0){
                    ModelAds ads = listAds.get(position);
                    if (TextUtils.isEmpty(ads.getUrl())) {
                        if ("0".equals(ads.getUrl_type()) || TextUtils.isEmpty(ads.getUrl_type())) {
                            new Jump(getActivity(), ads.getType_name(), ads.getAdv_inside_url());
                        } else {
                            new Jump(getActivity(), ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
                        }
                    } else {//URL不为空时外链
                        new Jump(getActivity(), ads.getUrl());

                    }
                }
            }
        }).start();

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }
    @Override
    public void onClickGrid(int position_parent, int position_child) {
        ModelServiceCat modelServiceCat = mDatas.get(position_parent);
        ModelServiceCat.GridBean gridBean = modelServiceCat.getList().get(position_child);
        getArea(gridBean);
    }

    /**
     * 判断服务是否在区域内
     *
     * @param gridBean
     */
    private void getArea(final ModelServiceCat.GridBean gridBean) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.put("region_cn", prefrenceUtil.getProvince_cn() + prefrenceUtil.getCity_cn() + prefrenceUtil.getRegion_cn());
        }
        params.put("category", gridBean.getId());
        MyOkHttp.get().get(ApiHttpClient.GET_SERVICEMATCHING, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent(mContext, MerchantServiceListActivity.class);
                    intent.putExtra("top_id", gridBean.getP_id());
                    intent.putExtra("sub_id", gridBean.getId());
                    intent.putExtra("sub_name", gridBean.getName());
                    startActivity(intent);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
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
