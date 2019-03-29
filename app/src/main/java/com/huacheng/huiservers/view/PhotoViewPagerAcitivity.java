package com.huacheng.huiservers.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.widget.photoview.PhotoView;

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

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(mDatas_img.size()-1);
        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(current_position);
        tv_position = findViewById(R.id.tv_position);
        tv_position.setText((current_position+1)+"/"+mDatas_img.size());
        findViewById(R.id.lin_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

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

    }
}
