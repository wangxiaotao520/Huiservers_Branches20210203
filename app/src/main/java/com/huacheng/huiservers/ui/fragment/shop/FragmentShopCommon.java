package com.huacheng.huiservers.ui.fragment.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.fragment.adapter.HomeIndexGoodsCommonAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.servicenew.ui.shop.HeaderViewPagerFragment;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:商城首页下方fragment
 * created by wangxiaotao
 * 2018/12/21 0021 下午 1:14
 */
public class FragmentShopCommon extends HeaderViewPagerFragment implements HomeIndexGoodsCommonAdapter.OnClickCallback {
    private PagingListView listView;
    private View rel_no_data;
    private List<ModelShopIndex> mDatas=new ArrayList<>();//数据
    private HomeIndexGoodsCommonAdapter<ModelShopIndex> adapter;
    private int page=1;
    int type;
    String c_id="";
    String cat_id="";
    private boolean isInit=false;
    private SharePrefrenceUtil prefrenceUtil;

    @Override
    public void initView(View view) {
        listView=view.findViewById(R.id.listView);
        adapter = new HomeIndexGoodsCommonAdapter<>(mActivity,mDatas,this);

        listView.setAdapter(adapter);
        setEnableLoadMore(false);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        prefrenceUtil=new SharePrefrenceUtil(mActivity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        c_id = arguments.getString("c_id");
        cat_id = arguments.getString("cat_id");
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (type==0&&mDatas.size()>0){//第一页
            isInit=true;
            page++;
            if (page > Integer.parseInt(mDatas.get(0).getTotal_Pages())) {
                setEnableLoadMore(false);
            } else {
                setEnableLoadMore(true);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_common;
    }

    @Override
    public View getScrollableView() {
        return listView;
    }

    public void init(float alpha) {
        if (!isInit){
            showDialog(smallDialog);
            isInit=true;
            page=1;
            requestData();
        }else {
            if (alpha<1){//如果alpha<1说明上方头部露着
                if (listView!=null){
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.setSelection(0);
                        }
                    });
                }
            }
        }
    }


    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        //dsm->待上门  wfk->未付款 dpj->待评价 ypj->已评价 qxdd->取消订单
        HashMap<String, String> params = new HashMap<>();
      //  params.put("c_id",c_id+"");
        params.put("id",cat_id+"");
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.SHOP_INDEX_MORE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                hideDialog(smallDialog);
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelShopIndex> shopIndexList = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelShopIndex.class);

                    inflateContent(shopIndexList);

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                listView.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    setEnableLoadMore(false);
                }
            }
        });
    }

    /**
     * 填充数据
     *
     * @param
     */
    private void inflateContent(List<ModelShopIndex> shopIndexList) {
        if (shopIndexList != null && shopIndexList.size() > 0) {
            rel_no_data.setVisibility(View.GONE);
            List<ModelShopIndex> list_new = shopIndexList;
            if (page == 1) {
                mDatas.clear();
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(0);
                    }
                });
            }
            mDatas.addAll(list_new);
            page++;

            if (page > Integer.parseInt(mDatas.get(0).getTotal_Pages())) {
                setEnableLoadMore(false);
            } else {
                setEnableLoadMore(true);
            }
            adapter.notifyDataSetChanged();

        } else {
            if (page == 1) {
                // 占位图显示出来
                rel_no_data.setVisibility(View.VISIBLE);
                mDatas.clear();
            }
            setEnableLoadMore(false);
            adapter.notifyDataSetChanged();
        }

    }
    private void setEnableLoadMore(boolean isloadmore) {
        if (isloadmore){
            listView.setHasMoreItems(true);
        }else {
            listView.setHasMoreItems(false);
        }
    }


    public void setInit(boolean init) {
        isInit = init;
    }

    /**
     * 直接刷新
     */
    public void refreshIndeed() {
        isInit=true;
        page=1;
        requestData();
    }

    /**
     * 给第一页的fragment填充数据
     * @param datas
     */
    public void setFirstFragmentData(List<ModelShopIndex> datas){
        mDatas.clear();
        mDatas.addAll(datas);
    }

    @Override
    public void onClickShopCart(int position) {
        //点击购物车
        if ( ApiHttpClient.TOKEN==null||ApiHttpClient.TOKEN_SECRET==null) {
            Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
            mContext.startActivity(intent);

        } else {

            if (mDatas.get(position).getExist_hours().equals("2")) {
                SmartToast.showInfo("当前时间不在派送时间范围内");
            } else {
                if (mDatas.get(position) != null) {
                    new CommonMethod(mDatas.get(position), null, mContext).getShopLimitTag();
                }
            }

        }

    }

    @Override
    public void onClickImage(int position) {
        //点击图片
        Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
        Bundle bundle = new Bundle();
        bundle.putString("shop_id", mDatas.get(position).getId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
