package com.huacheng.huiservers.ui.index.oldservice.oldfragment;

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
import com.huacheng.huiservers.model.ModelEventOld;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.model.ModelOldIndexTop;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.oldservice.AddOldRZUserActivity;
import com.huacheng.huiservers.ui.index.oldservice.CalendarViewActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldFileActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldHardwareActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldInvestiagateActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldServiceWarmActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldUserActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.utils.StringUtils;
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

import static android.app.Activity.RESULT_OK;

/**
 * Description: 居家养老
 * created by wangxiaotao
 * 2019/8/13 0013 上午 10:58
 */
public class OldFragment extends BaseFragment implements View.OnClickListener {

    private TextView title_name;
    View mStatusBar;
    String[] mTitles=new String[]{"资讯","活动"};
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
    private ImageView iv_vip;  //性别
    private LinearLayout ll_age_address; //年龄地址
    private TextView tv_age;  //年龄
    private TextView tv_address; //地址
    private TextView tv_change_person;  //切换长者
    private LinearLayout ll_change_person;

    private String par_uid = "";
    private String par_uid_param = "";  //请求 par_uid_param

    private boolean isEventCallback = false;//登录  认证  切换老人
    private boolean is_Refresh = false;  //是否是刷新
    private int type;  //认证状态
    private int p_type;  //是否是老干局的老人
    private ModelOldIndexTop modelOldIndexTop;
    private LinearLayout lin_left;
    private ImageView iv_investigate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        //状态栏
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float) 1);
        lin_left = view.findViewById(R.id.lin_left);
        lin_left.setVisibility(View.VISIBLE);
        title_name = view.findViewById(R.id.title_name);
        title_name.setText("居家养老");

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

        appBarLayout = view.findViewById(R.id.appbarlayout);

        initHeader(view);

        mEnhanceTabLayout = view.findViewById(R.id.enhance_tab_layout);
        mViewPager = view.findViewById(R.id.vp_pager);


        iv_investigate = view.findViewById(R.id.iv_investigate);

        iv_investigate.setVisibility(View.GONE);
        //  initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        if (modelOldIndexTop != null) {
            if (modelOldIndexTop.getType()==0){
                mTitles = new String[]{"资讯", "活动"};
            }else {
                if (modelOldIndexTop.getP_type() == 1) {
                    //老干局老人
                    mTitles = new String[]{"公告", "活动"};
                } else {
                    mTitles = new String[]{"资讯", "活动"};
                }
            }
        }
        for (int i = 0; i < mTitles.length; i++) {
            mEnhanceTabLayout.addTab(mTitles[i]);
        }
        FragmentOldArticle fragmentOldArticle = new FragmentOldArticle();
        Bundle bundle = new Bundle();
        bundle.putString("par_uid", par_uid);
        if (modelOldIndexTop != null) {
            bundle.putInt("type", modelOldIndexTop.getType());
            bundle.putInt("p_type", modelOldIndexTop.getP_type());
        }
        fragmentOldArticle.setArguments(bundle);
        //初始化数据
        mFragments.add(fragmentOldArticle);

        FragmentOldHuodong fragmentOldHuodong = new FragmentOldHuodong();
        Bundle bundle1 = new Bundle();
        bundle1.putString("par_uid", par_uid);
        //传递o_company_id
        if (modelOldIndexTop != null && !NullUtil.isStringEmpty(modelOldIndexTop.getO_company_id())) {
            bundle1.putString("o_company_id", modelOldIndexTop.getO_company_id() + "");
        }
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
        iv_vip = view.findViewById(R.id.iv_vip);
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
        iv_vip.setVisibility(View.GONE);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset == 0) {
                    if (refreshLayout != null) {
                        refreshLayout.setEnableRefresh(true);
                    }
                } else {
                    if (refreshLayout != null) {
                        refreshLayout.setEnableRefresh(false);
                    }
                }
            }
        });
        mEnhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mFragments.size()) {
                    //在这里传入参数
                    FragmentOldCommonImp fragmentCommon = (FragmentOldCommonImp) mFragments.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    fragmentCommon.isRefresh(par_uid);
                }
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
                if (LoginUtils.hasLoginUser()) {
                    //登录时刷新访问
                    is_Refresh = true;
                    requestTopIndex();
                } else {
                    //不登录时
                    for (int i = 0; i < mFragments.size(); i++) {
                        mFragments.get(i).setInit(false);
                    }
                    if (currentFragment != null) {
                        currentFragment.refreshIndeed(par_uid);//当前页直接刷新 不显示smalldialog
                    }
                    is_Refresh = false;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            OldFragment.this.refreshLayout.finishRefresh();
                        }
                    }, 1000);
                }
            }
        });
        iv_investigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 问卷调查
                Intent intent = new Intent(mContext, OldInvestiagateActivity.class);
                intent.putExtra("model", modelOldIndexTop);
                intent.putExtra("par_uid", par_uid);
                startActivityForResult(intent, 222);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (!LoginUtils.hasLoginUser()) {
            //1.没登录 不访问接口
            initTabAndViewPager();
        } else {
            //2.登录了访问首页上方接口
            //3.type 除0之外 数据都是有值的
            showDialog(smallDialog);
            requestTopIndex();
        }
    }

    private void requestInvestigatePermmision() {
        if (NullUtil.isStringEmpty(modelOldIndexTop.getOld_id())) {
            return;
        }
        if ("0".equals(modelOldIndexTop.getOld_id())) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("old_id", modelOldIndexTop.getOld_id() + "");
        params.put("o_company_id", modelOldIndexTop.getO_company_id() + "");
        MyOkHttp.get().post(ApiHttpClient.OLD_QUESTION_PERMISION, params, new JsonResponseHandler() {


            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //   SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"成功"));
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String status = data.getString("status");
                        if ("1".equals(status)) {
                            iv_investigate.setVisibility(View.VISIBLE);
                        } else {
                            iv_investigate.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void requestTopIndex() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(par_uid_param)) {
            params.put("par_uid", par_uid_param + "");
        }

        MyOkHttp.get().post(ApiHttpClient.PENSION_INDEXTOP, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelOldIndexTop = (ModelOldIndexTop) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldIndexTop.class);
                    if (modelOldIndexTop != null) {
                        type = modelOldIndexTop.getType();
                        p_type = modelOldIndexTop.getP_type();
                        if (0 == type) {
                            //没有认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_orange);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_orange);
                            iv_sex.setVisibility(View.GONE);
                            tv_dad_mom.setVisibility(View.GONE);
                            ll_age_address.setVisibility(View.GONE);
                            tv_name.setText("暂未认证");
                            tv_change_person.setText("立即认证");
                            par_uid = "";
                            sdv_head.setImageResource(R.drawable.ic_default_head);

                            mTitles = new String[]{"资讯", "活动"};

                        } else if (1 == type) {
                            if (p_type == 1) {
                                //老干局老人
                                mTitles = new String[]{"公告", "活动"};
                            } else {
                                mTitles = new String[]{"资讯", "活动"};
                            }
                            //1.老人认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_red);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_red);
                            iv_sex.setVisibility(View.VISIBLE);
                            tv_dad_mom.setVisibility(View.GONE);
                            ll_age_address.setVisibility(View.VISIBLE);
                            tv_name.setText("" + modelOldIndexTop.getName() + "");
                            iv_sex.setBackgroundResource("1".equals(modelOldIndexTop.getSex()) ? R.mipmap.ic_man_white : R.mipmap.ic_woman_white); //性别
                            tv_age.setText("年龄  " + modelOldIndexTop.getBirthday());
                            tv_address.setText(modelOldIndexTop.getI_name() + "");
                            tv_change_person.setText("关联子女");
                            par_uid = modelOldIndexTop.getPar_uid() + "";
                            // 头像
                            //   FrescoUtils.getInstance().setImageUri(sdv_head,ApiHttpClient.IMG_URL+ modelOldIndexTop.getPhoto()+"");
                            if (NullUtil.isStringEmpty(StringUtils.getImgUrl(modelOldIndexTop.getPhoto()))) {
                                sdv_head.setImageResource(R.drawable.ic_default_head);
                            } else {
                                FrescoUtils.getInstance().setImageUri(sdv_head, StringUtils.getImgUrl(modelOldIndexTop.getPhoto() + ""));
                            }
                            iv_vip.setVisibility("1".equals(modelOldIndexTop.getIs_vip()) ? View.VISIBLE : View.GONE);
                        } else if (2 == type) {
                            //2.子女认证
                            rl_title_container.setBackgroundResource(R.mipmap.bg_old_blue);
                            ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_blue);
                            iv_sex.setVisibility(View.VISIBLE);
                            tv_dad_mom.setVisibility(View.VISIBLE);
                            ll_age_address.setVisibility(View.VISIBLE);
                            tv_name.setText("" + modelOldIndexTop.getName() + "");
                            iv_sex.setBackgroundResource("1".equals(modelOldIndexTop.getSex()) ? R.mipmap.ic_man_white : R.mipmap.ic_woman_white); //性别
                            tv_age.setText("年龄  " + modelOldIndexTop.getBirthday());
                            tv_address.setText(modelOldIndexTop.getI_name() + "");
                            tv_dad_mom.setText(modelOldIndexTop.getCall());
                            tv_change_person.setText("切换长者");
                            par_uid = modelOldIndexTop.getPar_uid() + "";
                            iv_vip.setVisibility("1".equals(modelOldIndexTop.getIs_vip()) ? View.VISIBLE : View.GONE);
                            // 头像
                            //   FrescoUtils.getInstance().setImageUri(sdv_head,ApiHttpClient.IMG_URL+ modelOldIndexTop.getPhoto()+"");
                            if (NullUtil.isStringEmpty(StringUtils.getImgUrl(modelOldIndexTop.getPhoto()))) {
                                sdv_head.setImageResource(R.drawable.ic_default_head);
                            } else {
                                FrescoUtils.getInstance().setImageUri(sdv_head, StringUtils.getImgUrl(modelOldIndexTop.getPhoto() + ""));
                            }
                            mTitles = new String[]{"资讯", "活动"};
                        }
                        //刷新
                        if (is_Refresh) {
                            //如果是刷新 或者从登录页返回
                            //刷新
                            //表明切换的时候要刷新

                            is_Refresh = false;
                            // 这里记得下一新版本的时候就不是第二个了
                            if (mFragments.size() > 0) {
//
                                mEnhanceTabLayout.getTabLayout().removeAllTabs();
                                for (int i = 0; i < mTitles.length; i++) {
                                    mEnhanceTabLayout.addTab(mTitles[i]);
                                }
                                if (!NullUtil.isStringEmpty(modelOldIndexTop.getO_company_id()))
                                    ((FragmentOldHuodong) mFragments.get(1)).setO_company_id(modelOldIndexTop.getO_company_id());

                                ((FragmentOldArticle) mFragments.get(0)).setP_type(modelOldIndexTop.getP_type());
                                ((FragmentOldArticle) mFragments.get(0)).setType(modelOldIndexTop.getType());
                                ((FragmentOldArticle) mFragments.get(0)).setPar_uid(par_uid);
                                for (int i = 0; i < mFragments.size(); i++) {
                                    mFragments.get(i).setInit(false);
                                }
                                if (currentFragment != null) {
                                    currentFragment.refreshIndeed(par_uid);//当前页直接刷新 不显示smalldialog
                                }
                            } else {
                                initTabAndViewPager();
                            }

                        } else if (isEventCallback) {//从别的页返回  认证  切换老人
                            isEventCallback = false;
                            // 这里记得下一新版本的时候就不是第二个了
                            if (mFragments.size() > 0) {

                                mEnhanceTabLayout.getTabLayout().removeAllTabs();
                                for (int i = 0; i < mTitles.length; i++) {
                                    mEnhanceTabLayout.addTab(mTitles[i]);
                                }
                                if (!NullUtil.isStringEmpty(modelOldIndexTop.getO_company_id()))
                                    ((FragmentOldHuodong) mFragments.get(1)).setO_company_id(modelOldIndexTop.getO_company_id());

                                ((FragmentOldArticle) mFragments.get(0)).setP_type(modelOldIndexTop.getP_type());
                                ((FragmentOldArticle) mFragments.get(0)).setType(modelOldIndexTop.getType());
                                ((FragmentOldArticle) mFragments.get(0)).setPar_uid(par_uid);
                                for (int i = 0; i < mFragments.size(); i++) {
                                    mFragments.get(i).setInit(false);
                                }
                                if (currentFragment != null) {
                                    currentFragment.refreshIndeed(par_uid);//当前页直接刷新 不显示smalldialog
                                }
                            } else {
                                initTabAndViewPager();
                            }
                        } else {
                            //第一次进来
                            initTabAndViewPager();
                        }
                        requestInvestigatePermmision();
                    } else {
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
                refreshLayout.finishRefresh();
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
        } else if (type == 0) {//没有认证
            Intent intent = new Intent(mActivity, AddOldRZUserActivity.class);
            startActivity(intent);
        } else {
            if (modelOldIndexTop == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.rl_healthy:
                    Intent intent_oldfile = new Intent(mActivity, OldFileActivity.class);
                    intent_oldfile.putExtra("model", modelOldIndexTop);
                    startActivity(intent_oldfile);
                    break;
                case R.id.rl_data:
                    Intent intent1 = new Intent(mActivity, OldHardwareActivity.class);
                    intent1.putExtra("model", modelOldIndexTop);
                    startActivity(intent1);
                    break;
                case R.id.rl_warm:
                    Intent intent_warm = new Intent(mActivity, OldServiceWarmActivity.class);
                    intent_warm.putExtra("model", modelOldIndexTop);
                    startActivity(intent_warm);
                    break;
                case R.id.rl_medicine:
                    Intent intent_medicine = new Intent(mActivity, CalendarViewActivity.class);
                    intent_medicine.putExtra("model", modelOldIndexTop);
                    startActivity(intent_medicine);
                    break;
                case R.id.ll_change_person:
                    if (type == 0) {//立即认证
                        Intent intent = new Intent(mActivity, AddOldRZUserActivity.class);
                        startActivity(intent);
                    } else if (type == 1) { //老人 关联子女列表
                        Intent intent = new Intent(mActivity, OldUserActivity.class);
                        intent.putExtra("type", type);
                        startActivityForResult(intent, 111);
                    } else if (type == 2) {//子女 关联老人列表
                        Intent intent = new Intent(mActivity, OldUserActivity.class);
                        intent.putExtra("type", type);
                        startActivityForResult(intent, 111);
                    }
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
            isEventCallback = true;
            showDialog(smallDialog);
            requestTopIndex();
        }
    }

    /**
     * 老人认证返回
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ModelEventOld model) {
        if (model != null) {
            if (model.getEvent_type() == 1) {
                //删除返回要清空
                this.par_uid_param = "";
            } else {

            }
            isEventCallback = true;
            showDialog(smallDialog);
            requestTopIndex();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                if (data != null) {
                    //切换老人返回
                    String par_uid = data.getStringExtra("par_uid");
                    this.par_uid_param = par_uid;
                    isEventCallback = true;
                    showDialog(smallDialog);
                    requestTopIndex();
                }
            } else if (requestCode == 222) {
                requestInvestigatePermmision();
            }
        }
    }
}
