package com.huacheng.huiservers.ui.vip;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridOpen;
import com.huacheng.huiservers.view.MyGridview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：Vip开通购买界面
 * 时间：2020/12/1 10:36
 * created by DFF
 */
public class VipBuyActivity extends BaseActivity {

    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_open_num)
    TextView mTvOpenNum;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.grid_open)
    MyGridview mGridOpen;
    @BindView(R.id.tv_btn_buy)
    TextView mTvBtnBuy;
    @BindView(R.id.tv_xieyi)
    TextView mTvXieyi;

    private AdapterVipGridOpen mVipGridOpen;
    private List<ModelVipIndex> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("立即开通");

        for (int i = 0; i < 3; i++) {
            mDatas.add(new ModelVipIndex());
        }
        //vip开通方式
        mVipGridOpen = new AdapterVipGridOpen(this, R.layout.item_vip_index_open_style, mDatas);
        mGridOpen.setAdapter(mVipGridOpen);
        mDatas.get(0).setSelect(true);//默认第一条选中
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //vip选中开通方式
        mGridOpen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mDatas.size(); i++) {
                    if (position == i) {
                        mDatas.get(i).setSelect(true);
                    } else {
                        mDatas.get(i).setSelect(false);
                    }
                }
                mVipGridOpen.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_vip_buy;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
