package com.huacheng.huiservers.ui.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */
public class MineFragment extends MyFragment {


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
        ButterKnife.bind(view);
    }


    @OnClick({R.id.setting, R.id.msg, R.id.login, R.id.avator, R.id.name, R.id.level, R.id.point, R.id.kyold, R.id.sign,
            R.id.renzheng, R.id.goodslike, R.id.storelike, R.id.article_collect, R.id.vip, R.id.point_mall, R.id.coupon,
            R.id.wait_pay, R.id.wait_receive, R.id.shop_wait_comment, R.id.shop_after_sale, R.id.shop_order_all,
            R.id.wait_serve, R.id.in_serve, R.id.serve_wait_comment, R.id.serve_after_sale, R.id.serve_order_all,
            R.id.work_order, R.id.cart, R.id.bill, R.id.rent_sell, R.id.my_article, R.id.address, R.id.user_invite, R.id.join, R.id.visitor_invite, R.id.aboat})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            //设置
            case R.id.setting:

                break;
                //消息
            case R.id.msg:

                break;
                //登录
            case R.id.login:

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


}
