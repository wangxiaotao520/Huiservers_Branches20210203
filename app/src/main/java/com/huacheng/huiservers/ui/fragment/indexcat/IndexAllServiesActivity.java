package com.huacheng.huiservers.ui.fragment.indexcat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelHomeIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.HomeGridViewCateAdapter;
import com.huacheng.libraryservice.widget.GridViewNoScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：首页全部服务
 * 时间：2019/10/17 16:24
 * created by DFF
 */
public class IndexAllServiesActivity extends BaseActivity implements View.OnClickListener, HomeGridViewCateAdapter.OnClickItemListener {

    private LinearLayout lin_left;
    private ImageView left;
    private TextView tv_cancel;
    private TextView txt_right;
    private GridViewNoScroll gridview_my;
    private GridViewNoScroll gridview_other;
    private boolean isSure = false;  //编辑
    private HomeGridViewCateAdapter myGridViewCateAdapter;
    private HomeGridViewCateAdapter otherGridViewCateAdapter;
    private List<ModelHomeIndex> myCatelist = new ArrayList<>();//我的服务
    private List<ModelHomeIndex> otherCatelist = new ArrayList<>();//更多服务


    @Override
    protected void initView() {

        lin_left = findViewById(R.id.lin_left);
        left = findViewById(R.id.left);
        tv_cancel = findViewById(R.id.tv_cancel);
        txt_right = findViewById(R.id.txt_right);
        gridview_my = findViewById(R.id.gridview_my);
        gridview_other = findViewById(R.id.gridview_other);
        myCatelist.clear();
        for (int i = 0; i < 5; i++) {
            myCatelist.add(new ModelHomeIndex());
        }
        otherCatelist.clear();
        for (int i = 0; i < 6; i++) {
            otherCatelist.add(new ModelHomeIndex());
        }
        myGridViewCateAdapter = new HomeGridViewCateAdapter(this, myCatelist, 2, this);
        gridview_my.setAdapter(myGridViewCateAdapter);

        otherGridViewCateAdapter = new HomeGridViewCateAdapter(this, otherCatelist, 3, this);
        gridview_other.setAdapter(otherGridViewCateAdapter);

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        if (!isSure) {
            lin_left.setOnClickListener(this);
        } else {
            gridview_my.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                }
            });
            gridview_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                }
            });
        }
        txt_right.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_index_all_service;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_left://返回、取消
                if (isSure) {
                    txt_right.setText("编辑");
                    left.setVisibility(View.VISIBLE);
                    tv_cancel.setVisibility(View.GONE);
                    isSure = false;
                    for (int i = 0; i < myCatelist.size(); i++) {
                        myCatelist.get(i).setIsselect(false);
                    }
                    myGridViewCateAdapter.notifyDataSetChanged();

                    for (int i = 0; i < otherCatelist.size(); i++) {
                        otherCatelist.get(i).setIsselect(false);
                    }
                    otherGridViewCateAdapter.notifyDataSetChanged();

                } else {
                    isSure = true;
                    finish();
                }
                break;
            case R.id.txt_right://编辑
                if (isSure) {
                    txt_right.setText("编辑");
                    left.setVisibility(View.VISIBLE);
                    tv_cancel.setVisibility(View.GONE);
                    isSure = false;
                    for (int i = 0; i < myCatelist.size(); i++) {
                        myCatelist.get(i).setIsselect(false);
                    }
                    myGridViewCateAdapter.notifyDataSetChanged();

                    for (int i = 0; i < otherCatelist.size(); i++) {
                        otherCatelist.get(i).setIsselect(false);
                    }
                    otherGridViewCateAdapter.notifyDataSetChanged();
                } else {
                    txt_right.setText("确定");
                    left.setVisibility(View.GONE);
                    tv_cancel.setVisibility(View.VISIBLE);
                    isSure = true;
                    for (int i = 0; i < myCatelist.size(); i++) {
                        myCatelist.get(i).setIsselect(true);
                    }
                    myGridViewCateAdapter.notifyDataSetChanged();

                    for (int i = 0; i < otherCatelist.size(); i++) {
                        otherCatelist.get(i).setIsselect(true);
                    }
                    otherGridViewCateAdapter.notifyDataSetChanged();
                }

                break;
            default:
                break;
        }

    }

    @Override
    public void onClickImg(View v, int position, int type) {
        if (type == 2) {//我的服务
            if (myCatelist.size() == 1) {
                SmartToast.showInfo("至少一个");
                return;
            }
            otherCatelist.add(myCatelist.get(position));
            myCatelist.remove(myCatelist.get(position));

            myGridViewCateAdapter.notifyDataSetChanged();
            otherGridViewCateAdapter.notifyDataSetChanged();
        } else if (type == 3) {//更多服务
            if (myCatelist.size() > 6) {
                SmartToast.showInfo("最多7个");
                return;
            }
            myCatelist.add(otherCatelist.get(position));
            otherCatelist.remove(otherCatelist.get(position));

            otherGridViewCateAdapter.notifyDataSetChanged();
            myGridViewCateAdapter.notifyDataSetChanged();

        }
    }
}
