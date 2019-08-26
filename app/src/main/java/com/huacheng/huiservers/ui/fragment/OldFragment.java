package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.model.ModelOldIndexTop;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldArticle;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldCommonImp;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldHuodong;
import com.huacheng.huiservers.ui.index.oldservice.CalendarViewActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldFileActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldHardwareActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldServiceWarmActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldUserActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 居家养老
 * created by wangxiaotao
 * 2019/8/13 0013 上午 10:58
 */
public class OldFragment extends BaseFragment implements View.OnClickListener {

    private TextView title_name;
    View mStatusBar;
    private String[] mTitles = {"资讯","活动"};
    EnhanceTabLayout mEnhanceTabLayout;
    ViewPager mViewPager;
    private SmartRefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;

    private List<FragmentOldCommonImp> mFragments = new ArrayList<>();
    private FragmentOldCommonImp currentFragment;

    private LinearLayout ll_header;
    private RelativeLayout rl_title_container;
    private RelativeLayout rl_healthy; //健康档案
    private RelativeLayout rl_data;  //智能数据
    private RelativeLayout rl_warm;  //亲情关怀
    private RelativeLayout rl_medicine;  //用药提醒
    private SimpleDraweeView sdv_head; //头像
    private TextView tv_dad_mom; //身份
    private TextView tv_name;  //姓名
    private ImageView iv_sex;  //性别
    private LinearLayout ll_age_address; //年龄地址
    private TextView tv_age;  //年龄
    private TextView tv_address; //地址
    private TextView tv_change_person;  //切换长者
    private LinearLayout ll_change_person;

    private String par_uid= "";
    private String par_uid_param= "";  //请求 par_uid_param

    private boolean isEventCallback=false;//登录  认证  切换老人
    private boolean is_Refresh = false;  //是否是刷新
    private int type;  //认证状态

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        //状态栏
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)1);
        view.findViewById(R.id.lin_left).setVisibility(View.GONE);
        title_name = view.findViewById(R.id.title_name);
        title_name.setText("居家养老");

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

        appBarLayout = view.findViewById(R.id.appbarlayout);

        initHeader(view);

        mEnhanceTabLayout = view.findViewById(R.id.enhance_tab_layout);
        mViewPager =view. findViewById(R.id.vp_pager);
        for(int i=0;i<mTitles.length;i++){
            mEnhanceTabLayout.addTab(mTitles[i]);
        }



      //  initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        FragmentOldArticle fragmentOldArticle = new FragmentOldArticle();
        Bundle bundle = new Bundle();
        bundle.putString("par_uid", par_uid);
        fragmentOldArticle.setArguments(bundle);
        //初始化数据
        mFragments.add(fragmentOldArticle);

        FragmentOldHuodong fragmentOldHuodong =  new FragmentOldHuodong();
        Bundle bundle1 = new Bundle();
        bundle1.putString("par_uid", par_uid);
        fragmentOldHuodong.setArguments(bundle1);
        mFragments.add(fragmentOldHuodong);

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position % mTitles.length];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position % mTitles.length);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        });
        mEnhanceTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mEnhanceTabLayout.getTabLayout()));

        currentFragment = mFragments.get(0);

    }


    /**
     * 初始化头布局
     */
    private void initHeader(View view) {
        ll_header = view.findViewById(R.id.ll_header);
        rl_title_container = view.findViewById(R.id.rl_title_container);
        sdv_head = view.findViewById(R.id.sdv_head);
        tv_dad_mom = view.findViewById(R.id.tv_dad_mom);
        tv_name = view.findViewById(R.id.tv_name);
        iv_sex = view.findViewById(R.id.iv_sex);
        ll_age_address = view.findViewById(R.id.ll_age_address);
        tv_age = view.findViewById(R.id.tv_age);
        tv_address = view.findViewById(R.id.tv_address);
        ll_change_person = view.findViewById(R.id.ll_change_person);
        tv_change_person = view.findViewById(R.id.tv_change_person);

//        rl_title_container.setBackgroundResource(R.mipmap.bg_old_orange);
//        ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_orange);
        iv_sex.setVisibility(View.GONE);
        tv_dad_mom.setVisibility(View.GONE);
        ll_age_address.setVisibility(View.GONE);
        tv_name.setText("暂未认证");
        tv_change_person.setText("立即认证");

        rl_healthy = view.findViewById(R.id.rl_healthy);
        rl_data = view.findViewById(R.id.rl_data);
        rl_warm = view.findViewById(R.id.rl_warm);
        rl_medicine = view.findViewById(R.id.rl_medicine);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset==0){
                    if (refreshLayout!=null){
                        refreshLayout.setEnableRefresh(true);
                    }
                }else {
                    if (refreshLayout!=null){
                        refreshLayout.setEnableRefresh(false);
                    }
                }
            }
        });
        mEnhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在这里传入参数
                FragmentOldCommonImp fragmentCommon = (FragmentOldCommonImp) mFragments.get(tab.getPosition());
                currentFragment = fragmentCommon;
                fragmentCommon.isRefresh(par_uid);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        rl_healthy.setOnClickListener(this);
        rl_data.setOnClickListener(this);
        rl_warm.setOnClickListener(this);
        rl_medicine.setOnClickListener(this);
        ll_change_person.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (LoginUtils.hasLoginUser()){
                    //登录时刷新访问
                    is_Refresh=true;
                    requestTopIndex();
                }else {
                   //不登录时
                    for (int i = 0; i < mFragments.size(); i++) {
                        mFragments.get(i).setInit(false);
                    }
                    if (currentFragment!=null){
                        currentFragment.refreshIndeed(par_uid);//当前页直接刷新 不显示smalldialog
                    }
                    is_Refresh= false;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            OldFragment.this.refreshLayout.finishRefresh();
                        }
                    },1000);
                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (!LoginUtils.hasLoginUser()){
            //1.没登录 不访问接口
            initTabAndViewPager();
        }else {
            //2.登录了访问首页上方接口
            //3.type 除0之外 数据都是有值的
            showDialog(smallDialog);
            requestTopIndex();
        }
    }

    private void requestTopIndex() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(par_uid_param)){
            params.put("par_uid", par_uid_param + "");
        }

        MyOkHttp.get().post(ApiHttpClient.PENSION_INDEXTOP, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldIndexTop modelOldIndexTop = (ModelOldIndexTop) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldIndexTop.class);
                    if (modelOldIndexTop!=null) {
                        type = modelOldIndexTop.getType();
                        if (0== type){
                            //没有认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_orange);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_orange);
                            iv_sex.setVisibility(View.GONE);
                            tv_dad_mom.setVisibility(View.GONE);
                            ll_age_address.setVisibility(View.GONE);
                            tv_name.setText("暂未认证");
                            tv_change_person.setText("立即认证");
                            par_uid= "";

                        }else if(1== type){
                            //1.老人认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_red);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_red);
                            iv_sex.setVisibility(View.VISIBLE);
                            tv_dad_mom.setVisibility(View.GONE);
                            ll_age_address.setVisibility(View.VISIBLE);
                            tv_name.setText(""+modelOldIndexTop.getName()+"");
                            iv_sex.setBackgroundResource(modelOldIndexTop.getSex()==1?R.mipmap.ic_man_white:R.mipmap.ic_woman_white); //性别
                            tv_age.setText("年龄  "+modelOldIndexTop.getBirthday());
                            tv_address.setText(modelOldIndexTop.getI_name()+"");
                            tv_change_person.setText("关联子女");
                            par_uid=modelOldIndexTop.getPar_uid()+"";
                            // 头像
                            FrescoUtils.getInstance().setImageUri(sdv_head,ApiHttpClient.IMG_URL+modelOldIndexTop.getPhoto()+"");
                        }else if (2== type){
                            //2.子女认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_red);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_red);
                            iv_sex.setVisibility(View.VISIBLE);
                            tv_dad_mom.setVisibility(View.VISIBLE);
                            ll_age_address.setVisibility(View.VISIBLE);
                            tv_name.setText(""+modelOldIndexTop.getName()+"");
                            //  iv_sex.setBackgroundResource();// 性别
                            tv_age.setText("年龄  "+modelOldIndexTop.getBirthday());
                            tv_address.setText(modelOldIndexTop.getI_name()+"");
                            tv_dad_mom.setText(modelOldIndexTop.getCall());
                            tv_change_person.setText("切换长者");
                            par_uid=modelOldIndexTop.getPar_uid()+"";
                            // 头像
                            FrescoUtils.getInstance().setImageUri(sdv_head,ApiHttpClient.IMG_URL+modelOldIndexTop.getPhoto()+"");
                        }
                        //刷新
                        if (is_Refresh){
                            //如果是刷新 或者从登录页返回则证明下方两个列表已经初始化了
                            //刷新
                            //表明切换的时候要刷新
                            for (int i = 0; i < mFragments.size(); i++) {
                                mFragments.get(i).setInit(false);
                            }
                            if (currentFragment!=null){
                                currentFragment.refreshIndeed(par_uid);//当前页直接刷新 不显示smalldialog
                            }
                            is_Refresh= false;
                        }else  if (isEventCallback){//从别的页返回  认证  切换老人
                            if (currentFragment!=null){
                                currentFragment.refreshIndeed(par_uid);//刷新fragment
                            }
                            isEventCallback=false;
                        }else {
                            //第一次进来
                            initTabAndViewPager();
                        }
                    }else {
                        SmartToast.showInfo("数据解析异常");
                    }

                } else {
                    hideDialog(smallDialog);
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
    public int getLayoutId() {
        return R.layout.fragment_old;
    }

    @Override
    public void onClick(View v) {
        //没有登录的时候先登录
        if (!LoginUtils.hasLoginUser()) {
            startActivity(new Intent(mActivity, LoginVerifyCodeActivity.class));
        }else {
            switch (v.getId()){

                case R.id.rl_healthy:
                    startActivity(new Intent(mActivity, OldFileActivity.class));
                    break;
                case R.id.rl_data:
                    startActivity(new Intent(mActivity, OldHardwareActivity.class));
                    break;
                case R.id.rl_warm:
                    startActivity(new Intent(mActivity, OldServiceWarmActivity.class));
                    break;
                case R.id.rl_medicine:
                    startActivity(new Intent(mActivity, CalendarViewActivity.class));
                    break;
                case R.id.ll_change_person:
                    startActivity(new Intent(mActivity, OldUserActivity.class));
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 登录返回
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ModelLogin model) {
        if (model != null) {
            isEventCallback=true;
            showDialog(smallDialog);
            requestTopIndex();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
