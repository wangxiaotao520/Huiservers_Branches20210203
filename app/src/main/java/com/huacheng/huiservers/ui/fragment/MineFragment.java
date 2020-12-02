package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.GsonResponseHandler;
import com.huacheng.huiservers.model.UcenterIndex;
import com.huacheng.huiservers.ui.base.MyFragment;
import com.huacheng.huiservers.ui.center.AddressListActivity;
import com.huacheng.huiservers.ui.center.HeZuoActivity;
import com.huacheng.huiservers.ui.center.MyAboutActivity;
import com.huacheng.huiservers.ui.center.MyStoreFollowListActivity;
import com.huacheng.huiservers.ui.center.coupon.MyCouponListActivityNew;
import com.huacheng.huiservers.ui.index.houserent.MyHousePropertyActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldMessageActivity;
import com.huacheng.huiservers.ui.index.property.HouseListActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.servicenew1.ServiceOrderListActivityNew;
import com.huacheng.huiservers.ui.shop.ShopCartActivityNew;
import com.huacheng.huiservers.ui.shop.ShopOrderListActivityNew;
import com.huacheng.huiservers.ui.vip.MyDetailActivity;
import com.huacheng.huiservers.ui.vip.PersonalSettingActivity;
import com.huacheng.huiservers.ui.vip.RegisterActivity;
import com.huacheng.huiservers.ui.vip.VipIndexActivity;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideCircleTransform;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Created by changyadong on 2020/11/24
 * @description 我的-首页
 */
public class MineFragment extends MyFragment {


    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.msg)
    ImageView msg;
    @BindView(R.id.login)
    LinearLayout login;
    @BindView(R.id.avator)
    ImageView avator;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.level_bg)
    View levelBg;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.kyold)
    TextView kyold;
    @BindView(R.id.sign)
    TextView sign;
    @BindView(R.id.login_view)
    View loginView;
    @BindView(R.id.user_view)
    View userView;

    @BindView(R.id.renzheng)
    LinearLayout renzheng;
    @BindView(R.id.bindnum)
    TextView bindnum;

    @BindView(R.id.goodslike)
    LinearLayout goodslike;
    @BindView(R.id.shop_collect_num)
    TextView shopCollectNum;

    @BindView(R.id.storelike)
    LinearLayout storelike;
    @BindView(R.id.store_collect_num)
    TextView storeCollectNum;

    @BindView(R.id.article_collect)
    LinearLayout articleCollect;
    @BindView(R.id.article_collect_num)
    TextView articleCollectNum;


    @BindView(R.id.vip)
    LinearLayout vip;
    @BindView(R.id.point_mall)
    LinearLayout pointMall;
    @BindView(R.id.coupon)
    LinearLayout coupon;
    @BindView(R.id.shop_order_all)
    TextView shopOrderAll;
    @BindView(R.id.wait_pay)
    LinearLayout waitPay;
    @BindView(R.id.wait_receive)
    LinearLayout waitReceive;
    @BindView(R.id.shop_wait_comment)
    LinearLayout shopWaitComment;
    @BindView(R.id.shop_after_sale)
    LinearLayout shopAfterSale;
    @BindView(R.id.serve_order_all)
    TextView serveOrderAll;
    @BindView(R.id.wait_serve)
    LinearLayout waitServe;
    @BindView(R.id.in_serve)
    LinearLayout inServe;
    @BindView(R.id.serve_wait_comment)
    LinearLayout serveWaitComment;
    @BindView(R.id.serve_after_sale)
    LinearLayout serveAfterSale;
    @BindView(R.id.work_order)
    LinearLayout workOrder;
    @BindView(R.id.cart)
    LinearLayout cart;
    @BindView(R.id.bill)
    LinearLayout bill;
    @BindView(R.id.rent_sell)
    LinearLayout rentSell;
    @BindView(R.id.my_article)
    LinearLayout myArticle;
    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.user_invite)
    LinearLayout userInvite;
    @BindView(R.id.join)
    LinearLayout join;
    @BindView(R.id.visitor_invite)
    LinearLayout visitorInvite;
    @BindView(R.id.aboat)
    LinearLayout aboat;
    Unbinder unbinder;

    SharePrefrenceUtil prefrenceUtil;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        initStatubar();
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        loginView.setVisibility(LoginUtils.hasLoginUser() ? View.GONE : View.VISIBLE);
        userView.setVisibility(LoginUtils.hasLoginUser() ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();

        loginView.setVisibility(LoginUtils.hasLoginUser() ? View.GONE : View.VISIBLE);
        userView.setVisibility(LoginUtils.hasLoginUser() ? View.VISIBLE : View.GONE);

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        getData();
    }

    int[] levelBgArr = {R.drawable.level_bg_1, R.drawable.level_bg_1, R.drawable.level_bg_1, R.drawable.level_bg_2, R.drawable.level_bg_3, R.drawable.level_bg_1, R.drawable.level_bg_4};
    int[] levelTxColorArr = {R.color.level_1, R.color.level_1, R.color.level_1, R.color.level_2, R.color.level_3, R.color.level_3, R.color.level_4};
    UcenterIndex.DataBean userBean;

    public void getData() {
        smallDialog.show();
        Map<String, String> map = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.UCENTER_INDEX, map, new GsonResponseHandler<UcenterIndex>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                smallDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, UcenterIndex response) {
                Log.d("cyd", "onSuccess");
                smallDialog.dismiss();
                userBean = response.getData();
                name.setText(userBean.getNickname());
                Glide.with(mActivity).load(ApiHttpClient.IMG_URL + userBean.getAvatars()).transform(new GlideCircleTransform(mActivity)).into(avator);
                level.setText(userBean.getLevel().getName());
                point.setText("积分" + userBean.getPoints());
                kyold.setVisibility(userBean.getOld_type().equals("1") ? View.VISIBLE : View.GONE);

                bindnum.setText(userBean.getBind_num());
                shopCollectNum.setText(userBean.getShop_collection());
                storeCollectNum.setText(userBean.getMerchant_collection());
                articleCollectNum.setText(userBean.getSocial_collection());

                if (userBean.getSign_num().equals("0")) {
                    sign.setText("签到+10积分");
                    sign.setBackground(getResources().getDrawable(R.drawable.shape_left_round_orange));
                    sign.setTextColor(getResources().getColor(R.color.white));
                } else {
                    sign.setText(String.format("已连续签到%s天", userBean.getSign_num()));
                    sign.setBackground(getResources().getDrawable(R.drawable.shape_left_round_stroke_grey));
                    sign.setTextColor(getResources().getColor(R.color.title_color));
                }


                ((TextView) waitPay.findViewById(R.id.dot)).setText(userBean.getShop_order_1());
                waitPay.findViewById(R.id.dot).setVisibility(userBean.getShop_order_1().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) waitReceive.findViewById(R.id.dot)).setText(userBean.getShop_order_1());
                waitReceive.findViewById(R.id.dot).setVisibility(userBean.getShop_order_2().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) shopWaitComment.findViewById(R.id.dot)).setText(userBean.getShop_order_3());
                shopWaitComment.findViewById(R.id.dot).setVisibility(userBean.getShop_order_3().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) shopAfterSale.findViewById(R.id.dot)).setText(userBean.getShop_order_4());
                shopAfterSale.findViewById(R.id.dot).setVisibility(userBean.getShop_order_4().equals("0") ? View.GONE : View.VISIBLE);

                ((TextView) waitServe.findViewById(R.id.dot)).setText(userBean.getService_order_1());
                waitServe.findViewById(R.id.dot).setVisibility(userBean.getService_order_1().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) inServe.findViewById(R.id.dot)).setText(userBean.getService_order_2());
                inServe.findViewById(R.id.dot).setVisibility(userBean.getService_order_2().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) serveWaitComment.findViewById(R.id.dot)).setText(userBean.getService_order_3());
                serveWaitComment.findViewById(R.id.dot).setVisibility(userBean.getService_order_3().equals("0") ? View.GONE : View.VISIBLE);
                ((TextView) serveAfterSale.findViewById(R.id.dot)).setText(userBean.getService_order_4());
                serveAfterSale.findViewById(R.id.dot).setVisibility(userBean.getService_order_4().equals("0") ? View.GONE : View.VISIBLE);

                int levelIndex = Integer.valueOf(userBean.getLevel().getId());
                levelBg.setBackground(getResources().getDrawable(levelBgArr[levelIndex - 1]));
                level.setTextColor(getResources().getColor(levelTxColorArr[levelIndex - 1]));

            }
        });

    }

    @OnClick({R.id.setting, R.id.msg, R.id.login, R.id.avator, R.id.name, R.id.level, R.id.point, R.id.kyold, R.id.sign,
            R.id.renzheng, R.id.goodslike, R.id.storelike, R.id.article_collect, R.id.vip, R.id.point_mall, R.id.coupon,
            R.id.wait_pay, R.id.wait_receive, R.id.shop_wait_comment, R.id.shop_after_sale, R.id.shop_order_all,
            R.id.wait_serve, R.id.in_serve, R.id.serve_wait_comment, R.id.serve_after_sale, R.id.serve_order_all,
            R.id.work_order, R.id.cart, R.id.bill, R.id.rent_sell, R.id.my_article, R.id.address, R.id.user_invite, R.id.join, R.id.visitor_invite, R.id.aboat})
    public void onViewClicked(View view) {

        Intent intent = null;
        if (!LoginUtils.hasLoginUser()) {
            intent = new Intent(mContext, LoginVerifyCodeActivity.class);
            startActivity(intent);
            return;
        }
        switch (view.getId()) {

            //设置
            case R.id.setting:
                if (!LoginUtils.hasLoginUser()) {
                    intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, PersonalSettingActivity.class);
                    startActivity(intent);
                }

                break;
            //消息
            case R.id.msg:
                intent = new Intent(mContext, OldMessageActivity.class);
                startActivity(intent);
                break;
            //登录
            case R.id.login:

                intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                startActivity(intent);

                break;
            //头像
            case R.id.avator:

                //昵称
            case R.id.name:

                // 会员等级
            case R.id.level:
                intent = new Intent(mContext, MyDetailActivity.class);
                startActivity(intent);
                break;
            //积分
            case R.id.point:


                break;
            //康养老人
            case R.id.kyold:

                break;
            //签到
            case R.id.sign:
                if (userBean.getSign_num().equals("0")) {
                    intent = new Intent(mContext, RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            //认证
            case R.id.renzheng:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    // Intent intent = new Intent(getActivity(), PropertyNewActivity.class);
                    intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "bind");
                    startActivity(intent);

                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            //商品服务关注
            case R.id.goodslike:

                break;
            //店铺关注
            case R.id.storelike:
                if (!LoginUtils.hasLoginUser()) {
                    intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, MyStoreFollowListActivity.class);
                    startActivity(intent);
                }

                break;
            // 文章收藏
            case R.id.article_collect:

                break;
            //会员
            case R.id.vip:
                intent = new Intent(mContext, VipIndexActivity.class);
                startActivity(intent);
                break;
            //积分商城
            case R.id.point_mall:

                break;
            // 优惠券
            case R.id.coupon:
                if (!LoginUtils.hasLoginUser()) {
                    intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), MyCouponListActivityNew.class);
                    intent.putExtra("jump_type", 1);
                    startActivity(intent);
                }

                break;
            //等待付款
            case R.id.wait_pay:


                //等待收货
            case R.id.wait_receive:

                //待评价-商品
            case R.id.shop_wait_comment:


                //退款售后-商品
            case R.id.shop_after_sale:

                intent = new Intent(mContext, ShopOrderListActivityNew.class);
                startActivity(intent);

                break;
            //全部订单
            case R.id.shop_order_all:


                //待服务
            case R.id.wait_serve:


                //服务中
            case R.id.in_serve:


                //待评价-服务
            case R.id.serve_wait_comment:


                //售后-服务
            case R.id.serve_after_sale:
                intent = new Intent(mContext, ServiceOrderListActivityNew.class);
                startActivity(intent);
                break;
            //物业工单
            case R.id.work_order:


                break;
            //购物车
            case R.id.cart:
                intent = new Intent(mActivity, ShopCartActivityNew.class);
                startActivity(intent);
                break;
            //缴费账单
            case R.id.bill:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {

                    intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "property");
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            //租售房
            case R.id.rent_sell:
                intent = new Intent(mActivity, MyHousePropertyActivity.class);
                startActivity(intent);
                break;
            //我的帖子
            case R.id.my_article:

                break;
            //收货地址
            case R.id.address:
                intent = new Intent(mActivity, AddressListActivity.class);
                intent.putExtra("jump_type",3);
                startActivity(intent);
                break;
            //邀请注册
            case R.id.user_invite:

                break;
            //我要加盟
            case R.id.join:
                startActivity(new Intent(mActivity, HeZuoActivity.class));
                break;
            //访客邀请
            case R.id.visitor_invite:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    //Intent intent1 = new Intent(mContext, PropertyNewActivity.class);
                    intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "house_invite");
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开启此功能");
                }
                break;
            // 关于惠生活
            case R.id.aboat:
                startActivity(new Intent(mActivity, MyAboutActivity.class));
                break;

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
