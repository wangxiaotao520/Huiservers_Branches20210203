package com.huacheng.huiservers.ui.index.workorder.commit;

import android.view.View;

/**
 * Description: 自用报修
 * created by wangxiaotao
 * 2018/12/12 0012 上午 10:57
 */
public class PersonalWorkOrderCommitActivity extends BaseWorkOrderCommitActivity{
    String type_price="";
    @Override
    protected void initView() {

        super.initView();
        titleName.setText("家用报修");
        iv_arrow.setVisibility(View.INVISIBLE);
        tv_yufu.setVisibility(View.VISIBLE);
        work_type=1;
        tv_yufu.setText("预付费用："+type_price+"元（此费用包含在订单总价内）");
        tv_select_tag.setText(type_name);
    }



    @Override
    protected void initIntentData() {
        super.initIntentData();
        type_id= this.getIntent().getStringExtra("type_id");
        type_pid= this.getIntent().getStringExtra("type_pid");
        type_price= this.getIntent().getStringExtra("type_price");
        type_name= this.getIntent().getStringExtra("type_name");

    }
}
