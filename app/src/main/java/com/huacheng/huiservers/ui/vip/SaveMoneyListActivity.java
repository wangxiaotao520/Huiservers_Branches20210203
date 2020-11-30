package com.huacheng.huiservers.ui.vip;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipSaveMoney;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: vip累计省钱页面
 * created by wangxiaotao
 * 2020/11/30 0030 10:57
 */
public class SaveMoneyListActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_data)
    ImageView imgData;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rel_no_data)
    RelativeLayout relNoData;
    protected int page = 1;
    CommonAdapter mAdapter;
    protected List<ModelVipSaveMoney> mDatas = new ArrayList<>();
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("累计省钱");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new CommonAdapter<ModelVipSaveMoney>(this, R.layout.item_vip_save_money, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelVipSaveMoney item, int position) {

            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        hideDialog(smallDialog);
        for (int i = 0; i < 3; i++) {
            mDatas.add(new ModelVipSaveMoney());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_save_money_list;
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
