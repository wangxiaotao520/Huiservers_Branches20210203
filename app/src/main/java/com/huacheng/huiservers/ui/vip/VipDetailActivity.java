package com.huacheng.huiservers.ui.vip;

import android.view.View;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyListActivity;
import com.huacheng.huiservers.util.DialogUtil;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class VipDetailActivity extends MyListActivity {




    PointDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.actvivity_vip_detail;
    }
    String rule = "每天最高获得1000点成长值，获取方式如下：\n" +
            "1.订单支付金额、物业缴费金额、物业服务支付金额等1：1转化为成长值；\n" +
            "2.完善个人信息、绑定房屋、发布租售房、发帖、评论、认证老人及子女身份可获得相应成长值；\n" +
            "3.每日签到可连续获得成长值。\n" +
            "注：购买第三方服务产品及理财产品是无法获得成长值的哦~";
    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.customMsgAlert(mContext,"",rule);
            }
        });

    }

    @Override
    protected void initData() {
         adapter = new PointDetailAdapter();
         listView.setAdapter(adapter);
    }

    @Override
    public void getData(int page) {



    }


}
