package com.huacheng.huiservers.ui.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Created by changyadong on 2020/11/26
 * @description
 */
public class VipInfoActivity extends MyActivity {
    Unbinder unbinder;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.grow_value)
    TextView growValue;
    @BindView(R.id.avator)
    ImageView avator;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.need)
    TextView need;
    @BindView(R.id.vip_detail)
    TextView vipDetail;
    @BindView(R.id.privilege_detail)
    TextView privilegeDetail;
    @BindView(R.id.sign)
    LinearLayout sign;
    @BindView(R.id.identity)
    LinearLayout identity;
    @BindView(R.id.point_mall)
    LinearLayout pointMall;
    @BindView(R.id.open_vip)
    ImageView openVip;

    @BindView(R.id.levelbar)
    LinearLayout levelbar;

    @BindView(R.id.grow_current)
    TextView growCurrentTx;
    @BindView(R.id.grow_max)
    TextView growMaxTx;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        isStatusBar = true;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        back();
        initStatubar();
        unbinder = ButterKnife.bind(this);
        addLevel(1, 3);
    }


    public void addLevel(int level, int max) {

        for (int i = 0; i <= max; i++) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_levelbar, levelbar, false);
            ProgressBar progressBar = itemView.findViewById(R.id.progress);
            TextView levelTx = itemView.findViewById(R.id.level);
            levelTx.setText("LV" + i);
            if (i < level) {
                progressBar.setProgress(progressBar.getMax());
            }
            if (i == level){
                levelTx.setTextColor(getResources().getColor(R.color.orange));
            }
            if (i == 3) {
                progressBar.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.getLayoutParams();
                params.weight = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.weight = 0;
            }
            levelbar.addView(itemView);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vipinfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
