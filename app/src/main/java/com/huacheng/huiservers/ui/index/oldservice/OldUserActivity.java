package com.huacheng.huiservers.ui.index.oldservice;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * 类描述：居家养老 关联账户/切换长者
 * 时间：2019/8/13 16:35
 * created by DFF
 */
public class OldUserActivity extends BaseActivity {
    private LinearLayout ly_user;
    private TextView tv_btn;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("切换用户");
        tv_right.setText("编辑");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.orange_bg));
        ly_user = findViewById(R.id.ly_user);
        tv_btn = findViewById(R.id.tv_btn);

        addview();

    }

    private void addview() {

        ly_user.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_old_user_item, null);
            SimpleDraweeView sdv_head = view.findViewById(R.id.sdv_head);
            TextView tv_tag = view.findViewById(R.id.tv_tag);
            TextView tv_name = view.findViewById(R.id.tv_name);
            LinearLayout ly_delete = view.findViewById(R.id.ly_delete);

            //FrescoUtils.getInstance().setImageUri(iv_repair_head, ApiHttpClient.IMG_URL + modelNewWorkOrder.getWork_user().get(i).getHead_img());

            ly_user.addView(view);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_user;
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
}
