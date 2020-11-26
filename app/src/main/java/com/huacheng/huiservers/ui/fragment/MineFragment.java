package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.ui.base.MyFragment;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.vip.PersonalSettingActivity;
import com.huacheng.huiservers.utils.LogUtils;
import com.huacheng.huiservers.utils.LoginUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */
public class MineFragment extends MyFragment {


    @BindView(R.id.status_bar)
    View statusBar;
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
    @BindView(R.id.goodslike)
    LinearLayout goodslike;
    @BindView(R.id.storelike)
    LinearLayout storelike;
    @BindView(R.id.article_collect)
    LinearLayout articleCollect;
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
        loginView.setVisibility(LoginUtils.hasLoginUser() ? View.GONE : View.VISIBLE);
        userView.setVisibility(LoginUtils.hasLoginUser() ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();

        loginView.setVisibility(LoginUtils.hasLoginUser() ? View.GONE : View.VISIBLE);
        userView.setVisibility(LoginUtils.hasLoginUser() ? View.VISIBLE : View.GONE);

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

                break;
            //登录
            case R.id.login:

                intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                startActivity(intent);

                break;
            //头像
            case R.id.avator:

                break;
            //昵称
            case R.id.name:

                break;
            // 会员等级
            case R.id.level:

                break;
            //积分
            case R.id.point:

                break;
            //康养老人
            case R.id.kyold:

                break;
            //签到
            case R.id.sign:

                break;
            //认证
            case R.id.renzheng:

                break;
            //商品服务关注
            case R.id.goodslike:

                break;
            //店铺关注
            case R.id.storelike:

                break;
            // 文章收藏
            case R.id.article_collect:

                break;
            //会员
            case R.id.vip:

                break;
            //积分商城
            case R.id.point_mall:

                break;
            // 优惠券
            case R.id.coupon:

                break;
            //等待付款
            case R.id.wait_pay:

                break;
            //等待收获
            case R.id.wait_receive:

                break;
            //待评价-商品
            case R.id.shop_wait_comment:

                break;
            //退款售后-商品
            case R.id.shop_after_sale:

                break;
            //全部订单
            case R.id.shop_order_all:

                break;
            //待服务
            case R.id.wait_serve:

                break;
            //服务中
            case R.id.in_serve:

                break;
            //待评价-服务
            case R.id.serve_wait_comment:

                break;
            //售后-服务
            case R.id.serve_after_sale:

                break;
            //物业工单
            case R.id.work_order:

                break;
            //购物车
            case R.id.cart:

                break;
            //缴费账单
            case R.id.bill:

                break;
            //租售房
            case R.id.rent_sell:

                break;
            //我的帖子
            case R.id.my_article:

                break;
            //收货地址
            case R.id.address:

                break;
            //邀请注册
            case R.id.user_invite:

                break;
            //我要加盟
            case R.id.join:

                break;
            //访客邀请
            case R.id.visitor_invite:

                break;
            // 关于惠生活
            case R.id.aboat:

                break;

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
