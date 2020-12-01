package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.GsonResponseHandler;
import com.huacheng.huiservers.model.UserIndex;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.integral.MyIntegralRightActivity;
import com.huacheng.libraryservice.utils.AppConstant;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Created by changyadong on 2020/11/26
 * @description 我的主页
 */
public class MyDetailActivity extends MyActivity {
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

    @BindView(R.id.right_container)
    LinearLayout rightContainer;

    @BindView(R.id.open_vip)
    ImageView openVip;

    @BindView(R.id.levelbar)
    LinearLayout levelbar;

    @BindView(R.id.grow_current)
    TextView growCurrentTx;
    @BindView(R.id.grow_max)
    TextView growMaxTx;

    @BindView(R.id.share)
    ImageView shareIv;

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

    }


    public void addLevel(int point, List<UserIndex.DataBean.LevelBean> list) {
        boolean flag = true;
        int levelRank = 0;
        for (int i = 0; i < list.size(); i++) {
            UserIndex.DataBean.LevelBean levelBean = list.get(i);
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_levelbar, levelbar, false);
            ProgressBar progressBar = itemView.findViewById(R.id.progress);
            TextView levelTx = itemView.findViewById(R.id.level);
            levelTx.setText(levelBean.getName());
            int rank = Integer.valueOf(levelBean.getRank());
            if (point > rank) {
                progressBar.setProgress(progressBar.getMax());
                levelRank += Integer.valueOf(levelBean.getRank());
            } else {
                if (flag) {
                    levelTx.setTextColor(getResources().getColor(R.color.orange));
                    progressBar.setMax(rank - levelRank);
                    progressBar.setProgress(point - levelRank);
                    levelRank += Integer.valueOf(levelBean.getRank());
                    flag = false;
                }
            }


            if (i == list.size() - 1) {
                progressBar.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.getLayoutParams();
                params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.weight = 0;
            }
            levelbar.addView(itemView);
        }

    }

    public UserIndex.DataBean.LevelBean getLevel(int rank, List<UserIndex.DataBean.LevelBean> list) {

        for (UserIndex.DataBean.LevelBean bean : list) {
            if (Integer.valueOf(bean.getRank()) > rank) {

                return bean;
            }
        }
        return null;

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_vipinfo;
    }


    @Override
    protected void initData() {
        getData();
    }

    public void addRight(List<UserIndex.DataBean.UserRightBean> rightBeanList) {

        for (UserIndex.DataBean.UserRightBean rightBean : rightBeanList) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_vip_rights, levelbar, false);
            ImageView img = itemView.findViewById(R.id.img);
            TextView name = itemView.findViewById(R.id.name);
            Glide.with(mContext).load(ApiHttpClient.IMG_URL + rightBean.getImg()).centerCrop().into(img);
            name.setText(rightBean.getName());

            rightContainer.addView(itemView);


        }


    }

    public void getData() {
        smallDialog.show();
        MyOkHttp.get().post(ApiHttpClient.USER_INDEX, new HashMap<String, String>(), new GsonResponseHandler<UserIndex>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                smallDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, UserIndex response) {
                smallDialog.dismiss();
                UserIndex.DataBean userBean = response.getData();
                growValue.setText(userBean.getRank());
                point.setText(userBean.getPoints());
                name.setText(userBean.getNickname());
                level.setText(userBean.getUser_level());
                need.setText(String.format("还需%s成长值升级%s", userBean.getNext_rank(), userBean.getNext_level()));

                UserIndex.DataBean.LevelBean levelBean = getLevel(Integer.valueOf(userBean.getPoints()), userBean.getLevel());
                growCurrentTx.setText(userBean.getPoints() + "/");
                growMaxTx.setText(levelBean.getRank());

                addLevel(Integer.valueOf(userBean.getPoints()), userBean.getLevel());

                addRight(userBean.getUser_right());
                openVip.setVisibility(userBean.getIs_vip().equals("0") ? View.VISIBLE : View.GONE);


            }
        });
    }


    @OnClick({R.id.vip_history, R.id.point_history, R.id.vip_detail,R.id.privilege_detail,R.id.set,R.id.share})
    public void onViewClick(View view) {
        Intent it = new Intent();
        switch (view.getId()) {

            case R.id.vip_history:
                it.setClass(mContext, VipDetailActivity.class);
                startActivity(it);
                break;
            case R.id.point_history:
                it.setClass(mContext, PointDetailActivity.class);
                startActivity(it);

                break;
            case R.id.vip_detail:
                it.setClass(mContext, VipDetailActivity.class);
                startActivity(it);

                break;
            case R.id.privilege_detail:
                it.setClass(mContext, MyIntegralRightActivity.class);
                startActivity(it);

                break;
            case R.id.set:
                it.setClass(mContext,PersonalSettingActivity.class);
                startActivity(it);

                break;
            case R.id.share:

                 String shareTitle = "我的会员等级" + level.getText();
                 String shareDesc = "开通vip享受至尊特权";
                 String share_icon = "";
                 String share_url = ApiHttpClient.VLOG_INDEX_SHARE;
                 showSharePop(shareTitle,shareDesc,share_icon,share_url);
                break;

        }
    }

    /**
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param share_icon
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String share_icon, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, share_icon, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
