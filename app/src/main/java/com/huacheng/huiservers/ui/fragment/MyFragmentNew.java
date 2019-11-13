package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.CenterMoneyActivity;
import com.huacheng.huiservers.ui.center.CouponListActivity;
import com.huacheng.huiservers.ui.center.MyInfoActivity;
import com.huacheng.huiservers.ui.center.MyRenvationActivity;
import com.huacheng.huiservers.ui.center.SetActivity;
import com.huacheng.huiservers.ui.center.ShopOrderListActivity;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.ui.circle.MyCircleActivity;
import com.huacheng.huiservers.ui.index.houserent.MyHousePropertyActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldMessageActivity;
import com.huacheng.huiservers.ui.index.property.PropertyNewActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.ShopCartActivityTwo;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 我的Fragment
 * created by wangxiaotao
 * 2018/9/14 0014 下午 4:23
 */
public class MyFragmentNew extends BaseFragment implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private SimpleDraweeView sdv_head;
    private TextView tv_user_identify;
    private TextView tv_user_name;
    private TextView tv_user_phone;
    private RelativeLayout rl_community_bill_list, rl_baoxiu_order_list, rl_service_order_list, rl_shop_order_list,
            ry_set, ry, ry_my_renvation, ry_fang, rel_gouwuche, rel_xiaofejilu, rel_coupon, rel_myCircle, rel_house;
    private TextView tv_guest_num, tv_coupon_num, tv_decoration_num, tv_social_num, tv_cart_num;
    SharePrefrenceUtil prefrenceUtil;
    SharedPreferences preferencesLogin;
    private String login_type;
    PersoninfoBean bean;
    View mStatusBar;
    private ImageView iv_message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        getLinshi();
        refreshLayout = view.findViewById(R.id.refreshLayout);
        sdv_head = view.findViewById(R.id.sdv_head);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_identify = view.findViewById(R.id.tv_user_identify);
        tv_user_phone = view.findViewById(R.id.tv_user_phone);
        rl_community_bill_list = view.findViewById(R.id.rl_community_bill_list);
        rl_baoxiu_order_list = view.findViewById(R.id.rl_baoxiu_order_list);
        rl_service_order_list = view.findViewById(R.id.rl_service_order_list);
        rl_shop_order_list = view.findViewById(R.id.rl_shop_order_list);
        ry_fang = view.findViewById(R.id.ry_fang);
        tv_guest_num = view.findViewById(R.id.tv_guest_num);
        rel_coupon = view.findViewById(R.id.rel_coupon);
        rel_house = view.findViewById(R.id.rel_house);

        tv_coupon_num = view.findViewById(R.id.tv_coupon_num);
        ry_my_renvation = view.findViewById(R.id.ry_my_renvation);
        tv_decoration_num = view.findViewById(R.id.tv_decoration_num);
        rel_myCircle = view.findViewById(R.id.rel_myCircle);
        tv_social_num = view.findViewById(R.id.tv_social_num);
        rel_gouwuche = view.findViewById(R.id.rel_gouwuche);
        tv_cart_num = view.findViewById(R.id.tv_cart_num);
        rel_xiaofejilu = view.findViewById(R.id.rel_xiaofejilu);
        ry_set = view.findViewById(R.id.ry_set);
        refreshLayout.setEnableLoadMore(false);
        //状态栏
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)1);

        iv_message = view.findViewById(R.id.iv_message);

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        sdv_head.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        tv_user_phone.setOnClickListener(this);
        rl_community_bill_list.setOnClickListener(this);
        rl_baoxiu_order_list.setOnClickListener(this);
        rl_service_order_list.setOnClickListener(this);
        rl_shop_order_list.setOnClickListener(this);
        rel_coupon.setOnClickListener(this);
        rel_house.setOnClickListener(this);
        ry_set.setOnClickListener(this);
        ry_fang.setOnClickListener(this);
        ry_my_renvation.setOnClickListener(this);
        rel_gouwuche.setOnClickListener(this);
        rel_xiaofejilu.setOnClickListener(this);
        rel_myCircle.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        iv_message.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showDialog(smallDialog);
        requestData();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.put("c_id", prefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().post(Url_info.center_index, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    bean = (PersoninfoBean) JsonUtil.getInstance().parseJsonFromResponse(response, PersoninfoBean.class);
                    inflateContent(bean);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                refreshLayout.finishRefresh();
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void inflateContent(PersoninfoBean bean) {
        if (bean != null) {
            //昵称为空显示手机号
            if (NullUtil.isStringEmpty(bean.getNickname())) {
                // txt_name.setText("昵称：请填写昵称");
                tv_user_name.setText(bean.getNickname());
            } else {
                tv_user_name.setText(bean.getNickname());
            }
            tv_user_phone.setText(bean.getUsername());
            if (bean.getGuest_num().equals("0")) {
                tv_guest_num.setVisibility(View.GONE);
            } else {
                tv_guest_num.setText(bean.getGuest_num());
                tv_guest_num.setVisibility(View.VISIBLE);
            }
            if (bean.getDecoration_num().equals("0")) {
                tv_decoration_num.setVisibility(View.GONE);
            } else {
                tv_decoration_num.setText(bean.getDecoration_num());
                tv_decoration_num.setVisibility(View.VISIBLE);
            }
            if (bean.getSocial_num().equals("0")) {
                tv_social_num.setVisibility(View.GONE);
            } else {
                tv_social_num.setText(bean.getSocial_num());
                tv_social_num.setVisibility(View.VISIBLE);
            }
            if (bean.getCart_num().equals("0")) {
                tv_cart_num.setVisibility(View.GONE);
            } else {
                tv_cart_num.setText(bean.getCart_num());
                tv_cart_num.setVisibility(View.VISIBLE);
            }


            //头像显示
            if (!StringUtils.isEmpty(bean.getAvatars())) {
                FrescoUtils.getInstance().setImageUri(sdv_head, StringUtils.getImgUrl(bean.getAvatars()));
            }
            //判断是否是业主 物业住宅绑定
            if ("1".equals(bean.getIs_bind_property())) {//未绑定
                tv_user_identify.setText("请绑定住宅 >     ");

                tv_user_identify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PropertyNewActivity.class);
                        intent.putExtra("wuye_type", "bind");
                        startActivity(intent);

                    }
                });
            } else {
                tv_user_identify.setText("已认证业主");

            }
        }
    }

    @Override
    public void onClick(View v) {
        getLinshi();
        Intent intent;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.sdv_head:// 个人资料
            case R.id.tv_user_name:// 个人资料
                intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_community_bill_list:// 生活账单
                intent = new Intent(getActivity(), PropertyNewActivity.class);
                intent.putExtra("wuye_type", "property");
                startActivity(intent);
                break;
            case R.id.rl_baoxiu_order_list:// 报修账单
                /*Intent intent1 = new Intent();
                intent1.setClass(getActivity(), ServiceParticipateActivity.class);
                intent1.putExtra("type", "0");
                intent1.putExtra("itag", "1");
                startActivity(intent1);*/

               /* intent = new Intent(getActivity(), WorkOrderListActivity.class);
                startActivity(intent);*/
                intent = new Intent(getActivity(), WorkOrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_service_order_list:// 保修账单
//                intent = new Intent(getActivity(), FragmentOrderListActivity.class);
//                startActivity(intent);
                intent = new Intent(mActivity, MyHousePropertyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_shop_order_list:// 商城订单
                intent = new Intent(getActivity(), ShopOrderListActivity.class);
                bundle.putString("type", "1111");
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.rel_coupon:// 优惠券
                intent = new Intent(getActivity(), CouponListActivity.class);//CouponListActivity
                bundle.putString("tag", "center");
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.ry_set:// 设置
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.rel_myCircle:// 我的圈子
                startActivity(new Intent(getActivity(), MyCircleActivity.class));
                break;
            case R.id.ry_my_renvation:// 我的装修
                if (prefrenceUtil.getIsNew().equals("1")) {
                    startActivity(new Intent(getActivity(), MyRenvationActivity.class));
                } else {
                    SmartToast.showInfo("该小区暂未开启此功能");
                }

                break;
            case R.id.ry_fang:// 访客邀请
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(getActivity(), LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {

                    Intent intent1 = new Intent(mContext, PropertyNewActivity.class);
                    intent1.putExtra("wuye_type", "house_invite");
                    startActivity(intent1);
                }
                break;
            case R.id.rel_gouwuche://购物车
                intent = new Intent(getActivity(), ShopCartActivityTwo.class);
                startActivity(intent);
                //KeepAliveService.connectServer(context, "99905901020101");
                break;
            case R.id.rel_xiaofejilu://消费记录
                intent = new Intent(getActivity(), CenterMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_house://我的房产
                intent = new Intent(mActivity, MyHousePropertyActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_message://消息
                intent = new Intent(mActivity, OldMessageActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void getLinshi() {
        preferencesLogin = getActivity().getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_new;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PersoninfoBean bean) {
        //1.绑定房屋后刷新
        //2.更换个人信息后刷新
        requestData();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
