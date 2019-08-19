package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.ChargeHistoryActivity;

/**
 * 类描述：居家养老 关联账户/切换长者
 * 时间：2019/8/13 16:35
 * created by DFF
 */
public class OldUserActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ly_user;
    private TextView tv_btn;
    private TextView txt_right;
    private boolean iscancel = false;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("切换用户");
        txt_right = findViewById(R.id.txt_right);
        txt_right.setText("编辑");
        txt_right.setVisibility(View.VISIBLE);
        txt_right.setTextColor(getResources().getColor(R.color.orange_bg));
        //listview = findViewById(R.id.listview);
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
            RelativeLayout ry_yinying = view.findViewById(R.id.ry_yinying);

            //FrescoUtils.getInstance().setImageUri(iv_repair_head, ApiHttpClient.IMG_URL + modelNewWorkOrder.getWork_user().get(i).getHead_img());

            ly_user.addView(view);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(this);
        txt_right.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_right:
                if (iscancel) {
                    txt_right.setText("编辑");
                    tv_btn.setVisibility(View.VISIBLE);
                    iscancel = false;
                    for (int i = 0; i < ly_user.getChildCount(); i++) {
                        View childAt = ly_user.getChildAt(i);
                        LinearLayout ly_delete = childAt.findViewById(R.id.ly_delete);
                        RelativeLayout ry_yinying = childAt.findViewById(R.id.ry_yinying);
                        ly_delete.setVisibility(View.GONE);
                        ry_yinying.setBackground(getResources().getDrawable(R.drawable.allshape_white));
                    }
                } else {
                    txt_right.setText("完成");
                    tv_btn.setVisibility(View.GONE);
                    iscancel = true;
                    for (int i = 0; i < ly_user.getChildCount(); i++) {
                        View childAt = ly_user.getChildAt(i);
                        LinearLayout ly_delete = childAt.findViewById(R.id.ly_delete);
                        RelativeLayout ry_yinying = childAt.findViewById(R.id.ry_yinying);
                        ly_delete.setVisibility(View.VISIBLE);
                        ry_yinying.setBackground(getResources().getDrawable(R.drawable.layer_shadow));

                    }
                }
                break;
            case R.id.tv_btn:
               /* Intent intent = new Intent(OldUserActivity.this, AddOldUserActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);*/
                Intent intent = new Intent(OldUserActivity.this, ChargeHistoryActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
