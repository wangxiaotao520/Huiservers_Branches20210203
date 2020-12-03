package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyListActivity;
import com.huacheng.huiservers.ui.fragment.adapter.HomeListViewAdapter;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopSecKillListFragment;
import com.huacheng.huiservers.ui.shop.bean.ModelSeckill;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Created by changyadong on 2020/12/2
 * @description
 */
public class PointMallActivity extends MyListActivity {

    List<ModelShopIndex> mBeanALList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_point_mall;
    }

    HomeListViewAdapter adapter;

    @Override
    protected void initView() {
        super.initView();
        back();
        title("积分商城");

        adapter = new HomeListViewAdapter(mContext, R.layout.item_home_sec_kill, mBeanALList);
        listView.setAdapter(adapter);

        Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
        Bundle bundle = new Bundle();
        bundle.putString("shop_id", "");
        bundle.putInt("type",2);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }

    @Override
    protected void initData() {
        getData(mPage);
    }

    @Override
    public void getData(int page) {

        String url = null;
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                loadComplete();


            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                loadComplete();
            }
        });

    }
}
