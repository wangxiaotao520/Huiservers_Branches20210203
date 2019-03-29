package com.huacheng.huiservers.ui.index.workorder.commit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPersonalWorkCat;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 自用选择维修类型Activity
 * created by wangxiaotao
 * 2018/12/13 0013 上午 9:40
 */
public class PersonalWorkCatActivity extends BaseActivity {
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_pager)
    ViewPager mViewPager;
    List<PersonalWorkCatFragment> mFragmentList = new ArrayList<>();
    String[] mTitle = new String[0];
    private String community_id="";
    ArrayList<ModelPersonalWorkCat> datas_all= new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        tv_right = findViewById(R.id.txt_right1);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("订单中心");
        titleName.setText("家用报修");
    }

    @Override
    protected void initData() {
        getTabData();
    }



    @Override
    protected void initListener() {
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, WorkOrderListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_workcat;
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

    /**
     * 获取分类标签
     */
    private void getTabData() {

        SharePrefrenceUtil sharePrefrenceUtil = new SharePrefrenceUtil(this);
        community_id=sharePrefrenceUtil.getXiaoQuId();
        HashMap<String, String> params = new HashMap<>();
        params.put("community_id",community_id);
        MyOkHttp.get().post(ApiHttpClient.GET_PRIVATE_CATEGORY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取成功");
                    List<ModelPersonalWorkCat> datas_new = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelPersonalWorkCat.class);
                    datas_all.clear();
                    datas_all.addAll(datas_new);
                    inflateContent();
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "");
                    XToast.makeText(PersonalWorkCatActivity.this, msg, XToast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                XToast.makeText(PersonalWorkCatActivity.this, "网络异常，请检查网络设置", XToast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 填充Fragment
     */
    private void inflateContent() {
        mTitle=new String[datas_all.size()];
        for (int i = 0; i < datas_all.size(); i++) {
            mTitle[i]=""+datas_all.get(i).getType_name();
        }
        for (int i = 0; i < mTitle.length; i++) {
            PersonalWorkCatFragment fragmentCommon = new PersonalWorkCatFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            bundle.putSerializable("item_bean",datas_all.get(i).getList());
            fragmentCommon.setArguments(bundle);

            mFragmentList.add(fragmentCommon);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position % mTitle.length);
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        mViewPager.setOffscreenPageLimit(mTitle.length-1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(onPageChangeListener);
    }
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            PersonalWorkCatFragment fragmentCommon = (PersonalWorkCatFragment) mFragmentList.get(position);
            currentFragment = fragmentCommon;
            fragmentCommon.init();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
