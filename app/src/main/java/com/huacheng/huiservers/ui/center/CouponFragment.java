package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.ui.base.BaseFragmentOld;
import com.huacheng.huiservers.ui.center.adapter.CouponListMyAdapter;
import com.huacheng.huiservers.ui.center.bean.CouponBean;
import com.huacheng.huiservers.ui.shop.ConfirmOrderActivityNew;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopListActivity;
import com.huacheng.huiservers.view.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class CouponFragment extends BaseFragmentOld {

    ScrollView scrollView;
    LinearLayout lin_coupon40, lin_unusedCoupon;
    MyListView listview_myCoupon40, listview_unusedCoupon;
    CouponListMyAdapter myAdapter, unusedAdapter;
    RelativeLayout rel_no_data;
    ImageView img_data;
    TextView tv_text;
    CouponBean couponBean;
    List<CouponBean> myCoupon40list, unusedCouponlist;
    private String tag, coupon_id, all_shop_id, all_shop_money, jump_url, url;
    String mID;
    CenterProtocol protocol = new CenterProtocol();
    Intent intent = new Intent();
    Bundle bundle = new Bundle();

    public CouponFragment() {
        super();
    }

    public CouponFragment(String id) {
        super();
        mID = id;
    }

    @Override
    protected void initView() {
   //     SetTransStatus.GetStatus(getActivity());//系统栏默认为黑色
        lin_coupon40 = findViewById(R.id.lin_coupon40);

        scrollView = findViewById(R.id.scrollView);
        lin_unusedCoupon = findViewById(R.id.lin_unusedCoupon);
        listview_myCoupon40 = findViewById(R.id.listview_myCoupon40);
        listview_unusedCoupon = findViewById(R.id.listview_unusedCoupon);
        rel_no_data = findViewById(R.id.rel_no_data);
        img_data = findViewById(R.id.img_data);
        tv_text = findViewById(R.id.tv_text);

        tag = getActivity().getIntent().getExtras().getString("tag");
        if (tag.equals("order")) {
            all_shop_id = getActivity().getIntent().getExtras().getString("all_id");
            all_shop_money = getActivity().getIntent().getExtras().getString("all_shop_money");
        } else if (tag.equals("jump")) {
            jump_url = getActivity().getIntent().getExtras().getString("url");
        }
        getdata();

        /*listview_myCoupon40.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tag.equals("center")) {
                    Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", myCoupon40list.get(position - 1).getId());
                    bundle.putString("c_id", myCoupon40list.get(position - 1).getC_id());
                    bundle.putString("categroy_id", myCoupon40list.get(position - 1).getCategory_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (tag.equals("jump")) {
                    Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", myCoupon40list.get(position - 1).getId());
                    bundle.putString("c_id", myCoupon40list.get(position - 1).getC_id());
                    bundle.putString("categroy_id", myCoupon40list.get(position - 1).getCategory_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    coupon_id = myCoupon40list.get(position - 1).getId();
                    getCoupon();
                }
            }
        });*/

    }

    private void getdata() {
        showDialog(smallDialog);
        String servers = MyCookieStore.SERVERADDRESS + "userCenter/my_coupon_40/";
        Url_info info = new Url_info(mActivity);
        if (tag.equals("center")) {
            url = servers;
        } else if (tag.equals("jump")) {
            url = jump_url+"/";
        } else {
            url = servers + "category_id/" + "54" +
                    "/shop_id_str/" + all_shop_id + "/amount/" + all_shop_money + "/";
        }
        new HttpHelper(url, mActivity) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                couponBean = protocol.getCoupon40List(json);
                if (couponBean != null) {
                    rel_no_data.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    myCoupon40list = couponBean.getMy_coupon_list();//领取的优惠券
                    if (myCoupon40list != null) {
                        if (myCoupon40list.size() > 0) {
                            lin_coupon40.setVisibility(View.VISIBLE);
//                            rel_no_data.setVisibility(View.GONE);

                            myAdapter = new CouponListMyAdapter(myCoupon40list, 1, getActivity());
                            listview_myCoupon40.setAdapter(myAdapter);

                            listview_myCoupon40.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    goTo(myCoupon40list.get(position));
                                }

                            });

                        } else {
                            lin_coupon40.setVisibility(View.GONE);
//                            rel_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        lin_coupon40.setVisibility(View.GONE);

//                        rel_no_data.setVisibility(View.VISIBLE);
                    }

                    unusedCouponlist = couponBean.getCoupon_list();//未领取的优惠券
                    if (unusedCouponlist != null) {
                        if (unusedCouponlist.size() > 0) {
                            lin_unusedCoupon.setVisibility(View.VISIBLE);
                            unusedAdapter = new CouponListMyAdapter(unusedCouponlist, 0, getActivity());
                            listview_unusedCoupon.setAdapter(unusedAdapter);

                            listview_unusedCoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    couponAdd(unusedCouponlist.get(position).getId());
//
                                }
                            });

                        } else {
                            lin_unusedCoupon.setVisibility(View.GONE);
//                            rel_no_data1.setVisibility(View.VISIBLE);
                        }
                    } else {
                        lin_unusedCoupon.setVisibility(View.GONE);
//                        rel_no_data1.setVisibility(View.VISIBLE);
                    }

                    if (myCoupon40list == null && unusedCouponlist == null) {
                        scrollView.setVisibility(View.GONE);
                        rel_no_data.setVisibility(View.VISIBLE);
                        img_data.setBackgroundResource(R.mipmap.bg_no_coupon_data);
                        tv_text.setText("暂无优惠券");
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    rel_no_data.setVisibility(View.VISIBLE);
                    img_data.setBackgroundResource(R.mipmap.bg_no_coupon_data);
                    tv_text.setText("暂无优惠券");
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    private void goTo(CouponBean couponBean) {
        if (tag.equals("center") || tag.equals("jump")) {
            if (!couponBean.getShop_id().equals("0")) {//跳转到商品详情页
                intent.setClass(getActivity(), ShopDetailActivityNew.class);
                bundle.putString("shop_id", couponBean.getShop_id());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            } else if (!couponBean.getShop_cate().equals("0")) {//跳转到商城列表相应的分类列表页
                intent.setClass(context, ShopListActivity.class);
                bundle.putString("cateID", couponBean.getShop_cate());
                /*bundle.putString("shop_name", couponBean.getCategory_name());
                bundle.putString("isbool", "cate");*/
                intent.putExtras(bundle);
                startActivity(intent);
            } else {//跳转到商城列表所有商品列表页
                intent.setClass(getActivity(), ShopListActivity.class);
                bundle.putString("cateID", "");
                /*bundle.putString("shop_name", "所有商品");
                bundle.putString("isbool", "zhuanqu");*/
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            coupon_id = couponBean.getM_c_id();
            getCoupon();
        }
    }

    private void couponAdd(String id) {//领取优惠券
        showDialog(smallDialog);
        Url_info info = new Url_info(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter("coupon_id", id);
        new HttpHelper(info.coupon_add, params, getActivity()) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                String str = new CenterProtocol().setStatus(json);
                if ("1".equals(str)) {
                    getdata();
                    unusedAdapter.notifyDataSetChanged();
//                    myAdapter.notifyDataSetChanged();
                    SmartToast.showInfo("领取优惠券成功");
                } else {
                    SmartToast.showInfo(str);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }


    private void getCoupon() {//选择优惠券
        Url_info info = new Url_info(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", coupon_id);
        new HttpHelper(info.select_poupon, params, getActivity()) {

            @Override
            protected void setData(String json) {
                couponBean = protocol.getOrderCouPon(json);
                Intent intent = new Intent(getActivity(), ConfirmOrderActivityNew.class);
                Bundle bundle = new Bundle();
                bundle.putString("coupon_id", coupon_id);
                bundle.putString("coupon_price", couponBean.getAmount());
                bundle.putString("coupon_name", couponBean.getName());
                intent.putExtras(bundle);
                getActivity().setResult(100, intent);
                getActivity().finish();
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_coupon;
    }
}
