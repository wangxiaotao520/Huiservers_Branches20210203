package com.huacheng.huiservers.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.statusbar.StatusBarUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.widget.photoview.PhotoView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 展示图片Viewpager,图片放大
 * created by wangxiaotao
 * 2018/11/23 0023 下午 4:24
 */
public class PhotoViewPagerAcitivity extends BaseActivity {

    private  List<String> mDatas_img = new ArrayList<>();
  //  private List<Boolean> mDatas_init_list = new ArrayList<>();
    private List<View> photo_views_list = new ArrayList<>();
    private ViewPager viewPager;
    private int current_position;
    private TextView tv_position;
    View mStatusBar;
    private boolean isShowDelete;
    private ImageView iv_delete;
    private SamplePagerAdapter samplePagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }
    @Override
    protected  void initScreenConfig(){
       //将状态栏的字体变成亮色
        screenManager.setFullScreen(false, this);
        screenManager.setScreenRoate(false, this);
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, false)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }
    @Override
    protected void initView() {
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(mDatas_img.size()-1);
        samplePagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(samplePagerAdapter);
        viewPager.setCurrentItem(current_position);
        tv_position = findViewById(R.id.tv_position);
        tv_position.setText((current_position+1)+"/"+mDatas_img.size());
        findViewById(R.id.lin_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_delete = findViewById(R.id.iv_delete);
        if (isShowDelete){
            iv_delete.setVisibility(View.VISIBLE);
        }else {
            iv_delete.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tv_position.setText((i+1)+"/"+mDatas_img.size());
                current_position=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eventbus
                ModelPhoto modelPhoto = new ModelPhoto();
                modelPhoto.setPosition(current_position);
                EventBus.getDefault().post(modelPhoto);
                //当前页刷新
                mDatas_img.remove(current_position);
                if (mDatas_img.size()==0){
                    finish();
                }
                tv_position.setText((current_position+1)+"/"+mDatas_img.size());
                samplePagerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photoviewpager;
    }

    @Override
    protected void initIntentData() {
        current_position = this.getIntent().getIntExtra("position", 0);
        isShowDelete=this.getIntent().getBooleanExtra("isShowDelete",false);
        List <String>img_list = (List<String>) this.getIntent().getSerializableExtra("img_list");
        if (img_list!=null){
            mDatas_img.addAll(img_list);
        }
        for (int i = 0; i < mDatas_img.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_photo_view_pager_item, null);
            photo_views_list.add(view);
        }
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
     class SamplePagerAdapter extends PagerAdapter {



        @Override
        public int getCount() {
            return mDatas_img.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {

            View view = photo_views_list.get(position);
            PhotoView photoView = view.findViewById(R.id.photo_view);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            GlideUtils.getInstance().glideLoad(PhotoViewPagerAcitivity.this,mDatas_img.get(position),photoView,R.color.transparent);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

         @Override
         public int getItemPosition(Object object) {
             return POSITION_NONE;
         }
     }
}
