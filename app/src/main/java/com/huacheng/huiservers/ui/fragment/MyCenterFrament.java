package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.CenterMoneyActivity;
import com.huacheng.huiservers.ui.center.CouponListActivity;
import com.huacheng.huiservers.ui.center.HeZuoActivity;
import com.huacheng.huiservers.ui.center.MyAboutActivity;
import com.huacheng.huiservers.ui.center.MyInfoCircleActivity;
import com.huacheng.huiservers.ui.center.SetActivity;
import com.huacheng.huiservers.ui.center.ShopOrderListActivity;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.ui.fragment.adapter.MyCenterAdapter;
import com.huacheng.huiservers.ui.index.houserent.MyHousePropertyActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldMessageActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.HouseListActivity;
import com.huacheng.huiservers.ui.servicenew.ui.order.FragmentOrderListActivity;
import com.huacheng.huiservers.ui.shop.ShopCartActivityNew;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 类描述：个人中心
 * 时间：2019/12/9 16:13
 * created by DFF
 */
public class MyCenterFrament extends BaseFragment {
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.iv_set)
    ImageView mIvSet;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.iv_head)
    SimpleDraweeView mIvHead;
    @BindView(R.id.tv_login_status)
    TextView mTvLoginStatus;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_house)
    TextView mTvHouse;
    @BindView(R.id.ly_house)
    LinearLayout mLyHouse;
    @BindView(R.id.ly_workorder_daifuwu)
    LinearLayout mLyWorkorderDaifuwu;
    @BindView(R.id.ly_workorder_fuwuzhong)
    LinearLayout mLyWorkorderFuwuzhong;
    @BindView(R.id.ly_workorder_daizhifu)
    LinearLayout mLyWorkorderDaizhifu;
    @BindView(R.id.ly_workorder_yiwancheng)
    LinearLayout mLyWorkorderYiwancheng;
    @BindView(R.id.grid_cat)
    MyGridview mGridCat;
    @BindView(R.id.ry_help)
    RelativeLayout mRyHelp;
    @BindView(R.id.ry_about)
    RelativeLayout mRyAbout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.ly_scroll)
    LinearLayout mLyScroll;
    @BindView(R.id.ry_bg)
    LinearLayout mRyBg;
    @BindView(R.id.scroll)
    ScrollView mScroll;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_house)
    ImageView mIvHouse;
    @BindView(R.id.iv_title_line)
    View mIvTitleLine;
    private List<PersoninfoBean> mDatas = new ArrayList<>();
    private MyCenterAdapter myCenterAdapter;
    SharePrefrenceUtil prefrenceUtil;
    PersoninfoBean bean;

    @Override
    public void initView(View view) {
        ButterKnife.bind(mActivity);
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        //状态栏
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha(0);

        String[] str = {"购物车", "商城订单", "服务订单", "生活账单", "租售房", "优惠券", "访客邀请"};
        for (int i = 0; i < str.length; i++) {
            PersoninfoBean selectCommon = new PersoninfoBean();
            //selectCommon.setId((i + 1) + "");
            selectCommon.setFullname(str[i]);
            mDatas.add(selectCommon);
        }

        myCenterAdapter = new MyCenterAdapter(mActivity, R.layout.item_my_center, mDatas);
        mGridCat.setAdapter(myCenterAdapter);


    }

    @Override
    public void initIntentData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initListener() {
        mGridCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {  //"购物车"
                    Intent intent = new Intent(mActivity, ShopCartActivityNew.class);
                    startActivityForResult(intent, 1);
                } else if (position == 1) { //"商城订单"
                    Intent intent = new Intent(getActivity(), ShopOrderListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "1111");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (position == 2) {//"服务订单"
                    Intent intent = new Intent(mContext, FragmentOrderListActivity.class);
                    startActivity(intent);
                } else if (position == 3) {//"生活账单"
                    Intent intent = new Intent(mActivity, CenterMoneyActivity.class);
                    startActivity(intent);
                } else if (position == 4) {//"租售房"
                    Intent intent = new Intent(mActivity, MyHousePropertyActivity.class);
                    startActivity(intent);

                } else if (position == 5) {//"优惠券"
                    Intent intent = new Intent(getActivity(), CouponListActivity.class);//CouponListActivity
                    Bundle bundle = new Bundle();
                    bundle.putString("tag", "center");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (position == 6) { //"访客邀请"
                    if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                        //Intent intent1 = new Intent(mContext, PropertyNewActivity.class);
                        Intent intent1 = new Intent(mContext, HouseListActivity.class);
                        intent1.putExtra("type", 1);
                        intent1.putExtra("wuye_type", "house_invite");
                        startActivity(intent1);
                    } else {
                        SmartToast.showInfo("该小区暂未开启此功能");
                    }
                }
            }
        });
        mScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //设置其透明度
                float alpha = 0;
                //向上滑动的距离
                // int scollYHeight = -mLyScroll.getTop();
                if (scrollY > 0) {
                    alpha = 1;//滑上去就一直显示
                } else {
                    alpha = 0;
                }
                mStatusBar.setAlpha(alpha);
                if (alpha == 0) {
                    mIvTitleLine.setVisibility(View.GONE);
                    mIvSet.setBackgroundResource(R.mipmap.ic_set_white);
                    mIvMessage.setBackgroundResource(R.color.white);
                    mLyScroll.setBackground(null);
                    mTvTitle.setText("");

                } else {
                    mIvTitleLine.setVisibility(View.VISIBLE);
                    mLyScroll.setBackgroundResource(R.color.white);
                    mIvSet.setBackgroundResource(R.mipmap.ic_set_black);
                    mIvMessage.setBackgroundResource(R.color.orange_bg);
                    mTvTitle.setText("个人中心");

                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showDialog(smallDialog);
        requestData();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
            params.put("c_id", prefrenceUtil.getXiaoQuId());
        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(Url_info.center_index, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
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
                mRefreshLayout.finishRefresh();
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void inflateContent(PersoninfoBean bean) {
        if (bean != null) {

            mTvUserPhone.setText(bean.getUsername());
            mTvLoginStatus.setText(bean.getNickname());
            //头像显示
            if (!StringUtils.isEmpty(bean.getAvatars())) {
                mIvHead.setVisibility(View.VISIBLE);
                FrescoUtils.getInstance().setImageUri(mIvHead, StringUtils.getImgUrl(bean.getAvatars()));
            } else {
                mIvHead.setVisibility(View.INVISIBLE);
            }
            mLyHouse.setVisibility(View.VISIBLE);
            if ("1".equals(bean.getIs_bind_property())) {//未绑定
                mTvHouse.setText("认证房屋");
                mIvHouse.setVisibility(View.VISIBLE);
            } else {
                mTvHouse.setText("已认证");
                mIvHouse.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_center;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PersoninfoBean bean) {
        //1.绑定房屋后刷新
        //2.更换个人信息后刷新
        requestData();
    }

    @OnClick({R.id.iv_set, R.id.iv_message, R.id.iv_head, R.id.ly_house, R.id.ly_workorder_daifuwu, R.id.ly_workorder_fuwuzhong, R.id.ly_workorder_daizhifu, R.id.ly_workorder_yiwancheng, R.id.ry_help, R.id.ry_about})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                // startActivity(new Intent(mActivity, HouseHandBookActivity.class));//交房手册
                break;
            case R.id.iv_message:
                //startActivity(new Intent(mActivity, MessageIndexActivity.class));//消息大厅
                startActivity(new Intent(mActivity, OldMessageActivity.class));
                break;
            case R.id.iv_head:
                intent = new Intent(mContext, MyInfoCircleActivity.class);
                intent.putExtra("infoBean", bean);
                startActivity(intent);
                //startActivity(new Intent(mActivity, MyInfoActivity.class));
                //startActivity(new Intent(mActivity, ShopZCListActivity.class));//特卖专场
                break;
            case R.id.ly_house:  //判断是否是业主 物业住宅绑定

                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    // Intent intent = new Intent(getActivity(), PropertyNewActivity.class);
                    intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "bind");
                    startActivity(intent);

                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
              /*  Intent intent = new Intent(mContext, ShopZQListActivity.class);
                intent.putExtra("id", "3");
                startActivity(intent);*/
                break;
            case R.id.ly_workorder_daifuwu:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    intent = new Intent(mActivity, WorkOrderListActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            case R.id.ly_workorder_fuwuzhong:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    intent = new Intent(mActivity, WorkOrderListActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            case R.id.ly_workorder_daizhifu:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    intent = new Intent(mActivity, WorkOrderListActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            case R.id.ly_workorder_yiwancheng:
                if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
                    intent = new Intent(mActivity, WorkOrderListActivity.class);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                } else {
                    SmartToast.showInfo("该小区暂未开通服务");
                }
                break;
            case R.id.ry_help:
                startActivity(new Intent(mActivity, HeZuoActivity.class));
                break;
            case R.id.ry_about:
               /* intent = new Intent(mActivity, AboutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tag", "about");
                intent.putExtras(bundle);
                startActivity(intent);*/
                startActivity(new Intent(mActivity, MyAboutActivity.class));
                break;
        }
    }
}
