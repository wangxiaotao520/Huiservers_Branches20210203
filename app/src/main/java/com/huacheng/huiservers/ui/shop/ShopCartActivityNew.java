package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.shop.adapter.AdapterShopCartNew;
import com.huacheng.huiservers.ui.shop.adapter.OnClickShopCartListener;
import com.huacheng.huiservers.ui.shop.bean.ConfirmBean;
import com.huacheng.huiservers.ui.shop.bean.DataBean;
import com.huacheng.huiservers.ui.shop.bean.ShopCartBean;
import com.huacheng.huiservers.ui.shop.bean.SubmitOrderBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 新版购物车
 * created by wangxiaotao
 * 2019/11/16 0016 下午 4:12
 */
public class ShopCartActivityNew extends BaseActivity implements OnClickShopCartListener {
    ListView mListView ;
    private LinearLayout bottom_bar;
    private TextView tv_cart_Allprice;
    private TextView tv_cart_total;  //总价
    private TextView tv_cart_select_num;
    private LinearLayout lin_goumai;
    private List<DataBean> mDatas = new ArrayList<>();
    private AdapterShopCartNew adapterShopCartNew;
    private SharePrefrenceUtil prefrenceUtil;
    float totalPrice = (float) 0; //商品总价
    private String selected_m_id = "";//选中的商户id
    private int status = 1; //1.结算 2.编辑
    private TextView tv_cart_buy_or_del;
    private View cart_rl_allprie_total;
    private RelativeLayout rel_no_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        prefrenceUtil=new SharePrefrenceUtil(this);
        findTitleViews();
        tv_right = findViewById(R.id.right);
        tv_right.setText("编辑");
        tv_right.setVisibility(View.VISIBLE);
        titleName.setText("购物车");
        mListView=findViewById(R.id.listview);
        rel_no_data = findViewById(R.id.rel_no_data);
        adapterShopCartNew = new AdapterShopCartNew(this, R.layout.cart_list_item, mDatas,this,selected_m_id,status);
        mListView.setAdapter(adapterShopCartNew);
        bottom_bar = findViewById(R.id.bottom_bar);
        cart_rl_allprie_total = findViewById(R.id.cart_rl_allprie_total);
        tv_cart_Allprice=findViewById(R.id.tv_cart_Allprice);
        tv_cart_total = findViewById(R.id.tv_cart_total);
        tv_cart_select_num = findViewById(R.id.tv_cart_select_num);
        lin_goumai = findViewById(R.id.lin_goumai);
        tv_cart_buy_or_del = findViewById(R.id.tv_cart_buy_or_del);
    }



    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(Url_info.shopping_cart, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    List <ShopCartBean> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ShopCartBean.class);
                    if(data!=null&&data.size()>0){
                        rel_no_data.setVisibility(View.GONE);
                        mDatas.clear();
                        selected_m_id="";//选中为空
                        status=1;
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).getList().get(0).setFirstPosition(true);
                            mDatas.addAll(data.get(i).getList());
                        }
                        adapterShopCartNew.setStatus(status);
                        adapterShopCartNew.setSelected_m_id(selected_m_id);
                        adapterShopCartNew.notifyDataSetChanged();
                        calculatePrice();
                        cart_rl_allprie_total.setVisibility(View.VISIBLE);
                        tv_cart_buy_or_del.setText("去结算");
                        tv_right.setText("编辑");
                        mListView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mListView.setSelection(0);
                            }
                        },50);
                    }else {
                        mDatas.clear();
                        selected_m_id="";//选中为空
                        status=1;
                        adapterShopCartNew.setStatus(status);
                        adapterShopCartNew.setSelected_m_id(selected_m_id);
                        adapterShopCartNew.notifyDataSetChanged();
                        calculatePrice();
                        cart_rl_allprie_total.setVisibility(View.VISIBLE);
                        tv_cart_buy_or_del.setText("去结算");
                        tv_right.setText("编辑");
                        // 空数据
                        rel_no_data.setVisibility(View.VISIBLE);
                    }
                }else {
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
    protected void initListener() {
        lin_goumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status==1){//结算
//                    if (NullUtil.isStringEmpty(selected_m_id)){
//                        //没有选中
//                        SmartToast.showInfo("请选中商品");
//                    }else {
//                        goToConfirm();
//                    }
                    boolean flag =false;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).isChecked()){
                            flag=true;
                            break;
                        }
                    }
                   if (flag==false){
                       //没有选中
                       SmartToast.showInfo("请选中商品");
                   }else {
                       goToConfirm();
                   }

                }else {
                    //判断有没有选中
                    boolean checked = false;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).isChecked()){
                            checked=true;
                            break;
                        }
                    }
                    if (checked){
                        gotoDelete();
                    }

                }
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                //将所有选中状态清空
                if (mDatas.size()==0) {
                    return;
                }
                if (status==1){//当前是结算状态 进入编辑
                    status=2;
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setChecked(false);
                    }
                    calculatePrice();
                    selected_m_id="";
                    adapterShopCartNew.setSelected_m_id(selected_m_id);
                    adapterShopCartNew.notifyDataSetChanged();
                    adapterShopCartNew.setStatus(2);
                    cart_rl_allprie_total.setVisibility(View.INVISIBLE);
                    tv_cart_buy_or_del.setText("确认删除");
                    tv_right.setText("取消");
                }else {
                    selected_m_id="";//选中为空
                    status=1;
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setChecked(false);
                    }
                    adapterShopCartNew.setStatus(status);
                    adapterShopCartNew.setSelected_m_id(selected_m_id);
                    adapterShopCartNew.notifyDataSetChanged();
                    calculatePrice();
                    cart_rl_allprie_total.setVisibility(View.VISIBLE);
                    tv_cart_buy_or_del.setText("去结算");
                    tv_right.setText("编辑");
                }

            }
        });
    }

    /**
     * 删除商品
     */
    private void gotoDelete() {
        showDialog(smallDialog);
        String delete_str="";
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isChecked()){
                delete_str=delete_str+mDatas.get(i).getId()+",";
            }
        }
        String substring = delete_str.substring(0, delete_str.length() - 1);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", substring);
        MyOkHttp.get().post(Url_info.del_shopping_cart, params.getParams(), new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    //删除成功
                    requestData();

                }else {
                    hideDialog(smallDialog);
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "删除失败");
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
     * 去结算
     */
    public void goToConfirm() {//提交结算商品
        List<SubmitOrderBean> pro = new ArrayList<SubmitOrderBean>();

        for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isChecked()) {
                    SubmitOrderBean info = new SubmitOrderBean();
                    info.setTagid(mDatas.get(i).getTagid());
                    info.setP_id(mDatas.get(i).getP_id());
                    info.setP_title(mDatas.get(i).getTitle());
                    info.setP_title_img(mDatas.get(i).getTitle_img());
                    info.setPrice(String.valueOf(mDatas.get(i).getPrice()));
                    info.setNumber(String.valueOf(mDatas.get(i).getNumber()));
                    // str = mListData.get(i).getExist_hours();
                    pro.add(info);
                }

        }
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("all", totalPrice + "");
        bundle.putSerializable("pro", (Serializable) pro);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void finish() {
        if (mDatas .size()>0) {//保存购物车的数量
            saveShopNum();
        }
        super.finish();
    }

    /**
     * 退出的时候保存购物车数量
     */
    private void saveShopNum() {
        String str = "";
        Url_info info = new Url_info(this);
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < mDatas.size(); i++) {
            String id = mDatas.get(i).getId() + "";
            String number = mDatas.get(i).getNumber() + "";
            strBuilder.append(id + "." + number + ",");
        }
        int lastIndex = strBuilder.lastIndexOf(",");
        if (lastIndex != -1) {
            str = strBuilder.substring(0, lastIndex) + strBuilder.substring(lastIndex + 1, strBuilder.length());
//            Log.e("ShopCateStr", "===" + str);
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("cart_id_str", str);
        MyOkHttp.get().post(info.edit_shopping_cart, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {


            }
        });

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_cart_new;
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
    public void onClickItem(int position) {

        if (status==1){//结算状态
            //点击item
//            if (NullUtil.isStringEmpty(selected_m_id)){
//                //selected_m_id 为空 说明没有选过
//                mDatas.get(position).setChecked(true);
//                selected_m_id=mDatas.get(position).getM_id();
//                adapterShopCartNew.setSelected_m_id(selected_m_id);
//                adapterShopCartNew.notifyDataSetChanged();
//                calculatePrice();
//            }else {
//                //不为空说明有选中状态
//                if (selected_m_id.equals(mDatas.get(position).getM_id())){
//                    //可以选
//                    if (mDatas.get(position).isChecked()){//选中的 取消选中
//                        mDatas.get(position).setChecked(false);
//                        selected_m_id="";
//                        for (int i = 0; i < mDatas.size(); i++) {
//                            if (mDatas.get(i).isChecked()){
//                                selected_m_id =mDatas.get(i).getM_id();
//                                break;
//                            }
//                        }
//                    }else {
//                        mDatas.get(position).setChecked(true);  //没有选中的
//                        selected_m_id=mDatas.get(position).getM_id();
//                    }
//                    adapterShopCartNew.setSelected_m_id(selected_m_id);
//                    adapterShopCartNew.notifyDataSetChanged();
//                    calculatePrice();
//                }else {
//                    //不可以选
//                    SmartToast.showInfo("只能选择同一店铺的商品");
//                }
//            }

            if (mDatas.get(position).isChecked()){//选中的 取消选中
                        mDatas.get(position).setChecked(false);
                    }else {
                        mDatas.get(position).setChecked(true);  //没有选中的
                    }
                    adapterShopCartNew.setSelected_m_id(selected_m_id);
                    adapterShopCartNew.notifyDataSetChanged();
                    calculatePrice();
        }else {
            //删除状态
            if (mDatas.get(position).isChecked()){//选中的 取消选中
                mDatas.get(position).setChecked(false);
              //  selected_m_id="";
            }else {
                mDatas.get(position).setChecked(true);  //没有选中的
             //   selected_m_id=mDatas.get(position).getM_id();
            }
            adapterShopCartNew.notifyDataSetChanged();
        }

    }

    @Override
    public void onClickAdd(int position) {
        if (status==2){
            return;
        }
//        if (NullUtil.isStringEmpty(selected_m_id)){
//            //点击 +
//            if (Integer.valueOf(mDatas.get(position).getInventory()) < Integer.valueOf(mDatas.get(position).getNumber() + 1)) {
//                SmartToast.showInfo("此商品已超出库存数量");
//            } else {
//
//                //加入购物车
//                getShopLimit(position);
//            }
//        }else {
//            //不为空说明有选中状态
//            if (selected_m_id.equals(mDatas.get(position).getM_id())){
//                //可以选
//                if (Integer.valueOf(mDatas.get(position).getInventory()) < Integer.valueOf(mDatas.get(position).getNumber() + 1)) {
//                    SmartToast.showInfo("此商品已超出库存数量");
//                } else {
//
//                    //加入购物车
//                    getShopLimit(position);
//                }
//            }else {
//                //不可以选
//                SmartToast.showInfo("只能选择同一店铺的商品");
//            }
//        }

        //可以选
                if (Integer.valueOf(mDatas.get(position).getInventory()) < Integer.valueOf(mDatas.get(position).getNumber() + 1)) {
                    SmartToast.showInfo("此商品已超出库存数量");
                } else {
                    //加入购物车
                    getShopLimit(position);
                }
    }
    /**
     * 获取商品限购数量(0.4.1)（购物车+）
     * wangxiaotao
     */
    private void getShopLimit(final int position) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("p_id", mDatas.get(position).getP_id());
        params.put("tagid", mDatas.get(position).getTagid());
        params.put("num", (mDatas.get(position).getNumber() + 1) + "");

        MyOkHttp.get().post(Url_info.shop_limit, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    hideDialog(smallDialog);
                    mDatas.get(position).setNumber(mDatas.get(position).getNumber() + 1);
                    adapterShopCartNew.notifyDataSetChanged();
                    calculatePrice();

                } else {
                    if (smallDialog != null) {
                        smallDialog.dismiss();
                    }
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "超出限购");
                    SmartToast.showInfo(msg);
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (smallDialog != null) {
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
    @Override
    public void onClickReduce(int position) {
        if (status==2){
            return;
        }
//        if (NullUtil.isStringEmpty(selected_m_id)){
//            //点击 -
//            if (mDatas.get(position).getNumber()==1) {
//                return;
//            }
//            mDatas.get(position).setNumber(mDatas.get(position).getNumber() - 1);
//            adapterShopCartNew.notifyDataSetChanged();
//            calculatePrice();
//        }else {
//            if (selected_m_id.equals(mDatas.get(position).getM_id())){
//                if (mDatas.get(position).getNumber()==1) {
//                    return;
//                }
//                mDatas.get(position).setNumber(mDatas.get(position).getNumber() - 1);
//                adapterShopCartNew.notifyDataSetChanged();
//                calculatePrice();
//            }else {
//                //不可以选
//                SmartToast.showInfo("只能选择同一店铺的商品");
//            }
//        }
        if (mDatas.get(position).getNumber()==1) {
            return;
        }
        mDatas.get(position).setNumber(mDatas.get(position).getNumber() - 1);
        adapterShopCartNew.notifyDataSetChanged();
        calculatePrice();
    }

    /**
     * 计算商品价格
     */
    private void calculatePrice() {
        if (mDatas.size()>0){
            totalPrice= (float) 0;
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isChecked()){
                    totalPrice=totalPrice+mDatas.get(i).getNumber() * mDatas.get(i).getPrice();
                }

            }
            BigDecimal b = new BigDecimal(totalPrice);
            totalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }else {
            totalPrice= (float) 0;
        }
        tv_cart_total.setText("¥" + totalPrice + "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatecart(ConfirmBean bean) {
        //刷新购物车
        requestData();

    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
