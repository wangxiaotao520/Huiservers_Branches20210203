package com.huacheng.huiservers.ui.vip;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.GsonCallback;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.model.ArticalCollect;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyListActivity;
import com.huacheng.huiservers.ui.center.presenter.CollectDeletePresenter;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.fragment.SocialCollectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by changyadong on 2020/12/3
 * @description
 */
public class ArticalCollectActivity extends MyActivity {


    TabLayout tabLayout;
    String[] tabTitle = {"邻里资讯", "物业公告"};
    String[] tabCate = {"5", "6"};

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_artical_collect;
    }

    @Override
    protected void initView() {
        super.initView();
        back();
        title("文章收藏");

    }

    @Override
    protected void initData() {
        tabLayout = findViewById(R.id.tab);
        for (String cate : tabTitle) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(cate);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showFrag(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        showFrag(0);

    }

    public Fragment[] fragmentArray = new Fragment[tabTitle.length];

    public void showFrag(int index) {


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        Fragment fragment = fragmentArray[index];

        if (fragment == null) {
            fragmentArray[index] = fragment = getFragment(tabCate[index]);
        }
        for (Fragment frag : fragmentArray) {
            if (frag != null && frag != fragment) {
                transaction.hide(frag);
            }
        }

        if (!fragment.isAdded()) transaction.add(R.id.content, fragment, tabTitle[index]);
        if (fragment.isHidden()) transaction.show(fragment);

        transaction.commit();

    }


    public SocialCollectFragment getFragment(String cate) {

        SocialCollectFragment fragment = SocialCollectFragment.newInstance(cate);


        return fragment;
    }


}
