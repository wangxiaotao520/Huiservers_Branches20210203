package com.huacheng.huiservers.ui.index.workorder_second;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.WorkOrderDetailAdapter;
import com.huacheng.huiservers.utils.HiddenAnimUtils;
import com.huacheng.huiservers.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 时间：2019/4/9 09:03
 * created by DFF
 */
public class WorkOrderDetailActivity extends BaseActivity {
    LinearLayout mlinear_repair_person, mlinear_repair_photo, mlinear_photo, linear_visibility, ly_btn, linear_other_info;
    WorkOrderDetailAdapter mWorkOrderDetailAdapter;
    List<ModelWorkOrderList> mDatas = new ArrayList<>();
    MyListView mListView;
    ImageView iv_up;
    TextView tv_bianhao, tv_repair_time, tv_baoxiu_type, tv_jinji, tv_user_name, tv_baoxiu_content, tv_user_address, tv_user_photo, tv_up_name;
    int type;
    private int height = 0;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("工单详情");

        mlinear_repair_person = findViewById(R.id.linear_repair_person);
        mlinear_repair_photo = findViewById(R.id.linear_repair_photo);
        linear_other_info = findViewById(R.id.linear_other_info);
        mlinear_photo = findViewById(R.id.linear_photo);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_repair_time = findViewById(R.id.tv_repair_time);
        tv_baoxiu_type = findViewById(R.id.tv_baoxiu_type);
        tv_jinji = findViewById(R.id.tv_jinji);
        tv_baoxiu_content = findViewById(R.id.tv_baoxiu_content);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_photo = findViewById(R.id.tv_user_photo);
        linear_visibility = findViewById(R.id.linear_visibility);
        tv_up_name = findViewById(R.id.tv_up_name);
        iv_up = findViewById(R.id.iv_up);
        ly_btn = findViewById(R.id.ly_btn);
        mListView = findViewById(R.id.mListView);
        tv_user_photo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        repairAddView();

        mDatas.add(new ModelWorkOrderList());
        mDatas.add(new ModelWorkOrderList());
        mDatas.add(new ModelWorkOrderList());
        mWorkOrderDetailAdapter = new WorkOrderDetailAdapter(this, R.layout.activity_workorder_detail_item_list, mDatas);
        mListView.setAdapter(mWorkOrderDetailAdapter);
    }

    /**
     * 维修人员view
     */
    private void repairAddView() {
        //维修人员信息
        mlinear_repair_person.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_workorder_detail_item_view, null);
            SimpleDraweeView iv_repair_head = view.findViewById(R.id.iv_repair_head);
            TextView tv_repair_person = view.findViewById(R.id.tv_repair_person);
            TextView tv_repair = view.findViewById(R.id.tv_repair);
            TextView tv_repair_time = view.findViewById(R.id.tv_repair_time);
            ImageView iv_call = view.findViewById(R.id.iv_call);
            mlinear_repair_person.addView(view);
        }
        //维修人员不为空
        mlinear_photo.setVisibility(View.VISIBLE);
        mlinear_repair_photo.removeAllViews();
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
                    100);//两个50分别为添加图片的大小
            imageView.setImageResource(R.drawable.ic_launcher);
            imageView.setPadding(0, 0, 30, 0);
            imageView.setLayoutParams(params);
            mlinear_repair_photo.addView(imageView);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取平铺布局的高度
        height = linear_other_info.getHeight();
        linear_other_info.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        //待服务：待派单、待增派、待服务，
        //服务中：服务中，
        //待支付：待支付，
        //已完成：已完成 待评价、已完成 已评价、已取消

        if (type == 0) { //待服务 待派单  待增派
            //待派单 无维修师傅信息
            //待增派 有维修师傅信息
            //下方有取消工单按钮
        } else if (type == 1) {//服务中  待服务
            //无按钮 有派修人员

        }
        //有表单信息

    }

    @Override
    protected void initListener() {
        //缩放按钮
        linear_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear_other_info.getVisibility() == View.VISIBLE) {
                    iv_up.setBackgroundResource(R.mipmap.icon_workorder_detail_down);
                    tv_up_name.setText("展开");

                } else {
                    iv_up.setBackgroundResource(R.mipmap.icon_workorder_detail_up);
                    tv_up_name.setText("收起");
                }

                HiddenAnimUtils.newInstance(WorkOrderDetailActivity.this, linear_other_info, linear_visibility, height / 2).toggle();

            }
        });

        ly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkOrderDetailActivity.this, WorkOrderCancelActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_workorder_detail;
    }

    @Override
    protected void initIntentData() {
        //请求到数据删除type
        type = getIntent().getIntExtra("type", 0);

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
