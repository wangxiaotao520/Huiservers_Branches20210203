package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.shop.adapter.ShopCateOneAdapter;
import com.huacheng.huiservers.ui.shop.adapter.ShopCateTwoAdapter;
import com.huacheng.huiservers.ui.shop.bean.CateBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopCateActivity extends BaseActivity implements OnClickListener {
    private ListView list_one, list_two;
    private LinearLayout lin_left, lin_search;
    ShopProtocol protocol = new ShopProtocol();
    ShopCateTwoAdapter cateTwoAdapter;
    ShopCateOneAdapter cateOneAdapter;
    private List<CateBean> beans = new ArrayList<CateBean>();
    private List<CateBean> beans2 = new ArrayList<CateBean>();
    SharePrefrenceUtil prefrenceUtil;


    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        lin_left.setOnClickListener(this);
        lin_search = (LinearLayout) findViewById(R.id.lin_search);
        lin_search.setOnClickListener(this);
        list_one = (ListView) findViewById(R.id.list_one);
        list_two = (ListView) findViewById(R.id.list_two);

		/*	ShopCateOneAdapter cateOneAdapter=new ShopCateOneAdapter(this, mList);
        list_one.setAdapter(cateOneAdapter);
		ShopCateTwoAdapter cateTwoAdapter=new ShopCateTwoAdapter(this, mList);
		list_two.setAdapter(cateTwoAdapter);*/
        list_one.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                beans2.clear();
                getShopTwo(beans.get(position).getId());
                for (int i = 0; i < beans.size(); i++) {
                    if (position == i) {
                        beans.get(i).setSelect(true);
                    } else {
                        beans.get(i).setSelect(false);
                    }
                }
                cateOneAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        getShopOne();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_cate;
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

    private void getShopOne() {//一级
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
     //   params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }

        //pro_list_cate
        HttpHelper hh = new HttpHelper(info.area_topclass, params, ShopCateActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                beans = protocol.getcateOne(json);
                if (beans.size() != 0) {
                    for (int i = 0; i < beans.size(); i++) {
                        if (0 == i) {
                            beans.get(i).setSelect(true);
                        } else {
                            beans.get(i).setSelect(false);
                        }
                    }
                    cateOneAdapter = new ShopCateOneAdapter(ShopCateActivity.this, beans);
                    list_one.setAdapter(cateOneAdapter);
                    cateOneAdapter.notifyDataSetChanged();
                    getShopTwo(beans.get(0).getId());
                }

            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    /**
     * 前代二三级分类
     *
     * @param id
     */
    private void getShopTwo(String id) {//二级三级
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
      //  params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }

        HttpHelper hh = new HttpHelper(info.area_category, params, ShopCateActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                beans2 = protocol.getcateTwo(json);
                cateTwoAdapter = new ShopCateTwoAdapter(ShopCateActivity.this, beans2);
                list_two.setAdapter(cateTwoAdapter);
                cateTwoAdapter.notifyDataSetChanged();
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();

                break;
            case R.id.lin_search://搜索
                Intent intent = new Intent(this, SearchShopActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
