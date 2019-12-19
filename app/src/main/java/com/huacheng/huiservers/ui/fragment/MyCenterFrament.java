package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.ui.fragment.adapter.MyCenterAdapter;
import com.huacheng.huiservers.ui.fragment.indexcat.HouseHandBookActivity;
import com.huacheng.huiservers.ui.index.message.MessageIndexActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.shop.ShopZCListActivity;
import com.huacheng.huiservers.ui.shop.ShopZQListActivity;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.TDevice;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

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
    private List<PersoninfoBean> mDatas = new ArrayList<>();
    private MyCenterAdapter myCenterAdapter;

    @Override
    public void initView(View view) {
        ButterKnife.bind(mActivity);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        //状态栏
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));

        for (int i = 0; i < 8; i++) {
            mDatas.add(new PersoninfoBean());
        }

        myCenterAdapter = new MyCenterAdapter(mActivity, R.layout.item_my_center, mDatas);
        mGridCat.setAdapter(myCenterAdapter);


    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

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
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_set, R.id.iv_message, R.id.iv_head, R.id.ly_house, R.id.ly_workorder_daifuwu, R.id.ly_workorder_fuwuzhong, R.id.ly_workorder_daizhifu, R.id.ly_workorder_yiwancheng, R.id.ry_help, R.id.ry_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_set:
                startActivity(new Intent(mActivity, HouseHandBookActivity.class));
                break;
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageIndexActivity.class));
                break;
            case R.id.iv_head:
                startActivity(new Intent(mActivity, ShopZCListActivity.class));
                break;
            case R.id.ly_house:
                Intent intent = new Intent(mContext, ShopZQListActivity.class);
                intent.putExtra("id", "3");
                startActivity(intent);
                break;
            case R.id.ly_workorder_daifuwu:
                intent = new Intent(mActivity, WorkOrderListActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.ly_workorder_fuwuzhong:
                intent = new Intent(mActivity, WorkOrderListActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.ly_workorder_daizhifu:
                intent = new Intent(mActivity, WorkOrderListActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.ly_workorder_yiwancheng:
                intent = new Intent(mActivity, WorkOrderListActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.ry_help:
                break;
            case R.id.ry_about:
                break;
        }
    }
}
