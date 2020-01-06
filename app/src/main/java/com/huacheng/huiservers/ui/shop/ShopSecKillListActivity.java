package com.huacheng.huiservers.ui.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.shop.bean.CateBean;
import com.huacheng.huiservers.ui.shop.bean.ModelSeckill;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.TDevice;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 秒杀列表
 * created by wangxiaotao
 * 2020/1/6 0006 上午 8:25
 */
public class ShopSecKillListActivity extends BaseActivity{
    View mStatusBar;
    EnhanceTabLayout tabLayout;
    ViewPager viewpager;
    List<ShopSecKillListFragment>mFragmentList = new ArrayList<>();
    private List<CateBean> cateBeanList=new ArrayList<>();
    private int total_Page=1;
    SharePrefrenceUtil prefrenceUtil;
    String[] tabTxt;
    private ModelSeckill modelSeckillBean;
    private View view_line;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 1);
        findViewById(R.id.lin_left).setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
        tabLayout= findViewById(R.id.tl_tab);
        tabLayout.setVisibility(View.INVISIBLE);
        viewpager=findViewById(R.id.vp_pager);
        view_line=findViewById(R.id.view_line);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        prefrenceUtil= new SharePrefrenceUtil(this);
        Url_info info = new Url_info(this);
        String class_id=0+"";
        String url=info.pro_discount_list  + "/is_star/" + "1" + "/p/" + total_Page+"/class_id/"+class_id+"/province_cn/"+prefrenceUtil.getProvince_cn()+"/city_cn/"+prefrenceUtil.getCity_cn()+"/region_cn/"+prefrenceUtil.getRegion_cn();
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    tabLayout.setVisibility(View.VISIBLE);
                    view_line.setVisibility(View.VISIBLE);
                    modelSeckillBean = (ModelSeckill) com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().parseJsonFromResponse(response, ModelSeckill.class);
                    if (modelSeckillBean !=null&& modelSeckillBean.getClass_name()!=null){
                        CateBean cateBean = new CateBean();
                        cateBean.setId("0");
                        cateBean.setCate_name("全部商品");
                        cateBeanList.add(cateBean);
                        cateBeanList.addAll(modelSeckillBean.getClass_name());
                        tabTxt=new String[cateBeanList.size()];
                        for (int i = 0; i < cateBeanList.size(); i++) {
                            tabTxt[i]= cateBeanList.get(i).getCate_name()+"";
                        }
                        initTab();
                    }
                }else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for(int i=0;i<tabTxt.length;i++){
            tabLayout.addTab(tabTxt[i]);
        }

        for (int i = 0; i < tabTxt.length; i++) {
            ShopSecKillListFragment tabFragment = new ShopSecKillListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("class_id", cateBeanList.get(i).getId()+"");
            bundle.putInt("type", i);
            tabFragment.setArguments(bundle);
            mFragmentList.add(tabFragment);
        }
        // updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTxt[position % tabTxt.length];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position % tabTxt.length);
            }

            @Override
            public int getCount() {
                return tabTxt.length;
            }
        });



        viewpager.setOffscreenPageLimit(tabTxt.length); //0 设置缓存页面，当前页面的相邻N各页面都会被缓存

        tabLayout.setupWithViewPager(viewpager);
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout.getTabLayout()));

        currentFragment = mFragmentList.get(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()<mFragmentList.size()){
                    //在这里传入参数
                    ShopSecKillListFragment fragmentCommon = (ShopSecKillListFragment) mFragmentList.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    ((ShopSecKillListFragment)currentFragment).selected_init();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sec_kill_list;
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
}
