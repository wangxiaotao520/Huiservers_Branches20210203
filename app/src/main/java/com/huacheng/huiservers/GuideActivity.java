package com.huacheng.huiservers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 首次进入应用的欢迎页
 *
 * @author Demon
 */
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    private ViewPager viewPager;
    private List<View> imageViewList;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private String login_type;
    SharedPreferences preferencesLogin;

    private String token;
    private String tokenSecret;
    private int[] imageResIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
      //  isStatusBar = true;
        sharePrefrenceUtil = new SharePrefrenceUtil(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        preferencesLogin = this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
    }

    @Override
    protected void initData() {
        //
        imageResIds = new int[]{ R.drawable.guide1,R.drawable.guide2,R.drawable.guide3,R.drawable.guide4};
        imageViewList = new ArrayList<View>();

        for (int i = 0; i < imageResIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_image_guide, null);
            ImageView iv = view.findViewById(R.id.iv_image);
            iv.setImageResource(imageResIds[i]);
            imageViewList.add(view);
        }
        MyAdapter mAdapter = new MyAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (position == (imageViewList.size() - 1)) {
//            CacheUtils.putBoolean(this, SplashActivity.IS_FIRST_OPEN, false);
//            Timer timer = new Timer();
//            TimerTask tt = new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    if (sharePrefrenceUtil.getIsChooseXiaoqu().equals("1")) {
//                        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent();
//                        intent = new Intent(GuideActivity.this, XiaoquActivity.class);
//                        intent.putExtra("type", "splash");
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            };
//            timer.schedule(tt, 1000);
//        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.guide;
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

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageViewList.get(position);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            iv_image.setImageResource(imageResIds[position]);
            TextView tv_btn_call = view.findViewById(R.id.tv_btn_call);
            if (position==imageViewList.size()-1){
                tv_btn_call.setVisibility(View.VISIBLE);
                tv_btn_call.setOnClickListener(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                tv_btn_call.setVisibility(View.GONE);
            }
            container.addView(view);
            return view;
        }


    }

}

