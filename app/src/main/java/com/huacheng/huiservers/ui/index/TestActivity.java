package com.huacheng.huiservers.ui.index;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.TestAdapter;
import com.huacheng.huiservers.ui.index.houserent.MyHousePropertyActivity;
import com.huacheng.huiservers.ui.index.houserent.RentSellCommissionActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkEvaluateSuccessActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.PersonalWorkCatActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.PublicWorkOrderCommitActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:测试 Activity
 * Author: badge
 * Create: 2018/12/20 17:00
 */
public class TestActivity extends BaseActivity {

    private GridView gridView;
    private LinearLayout linLeft;
    private TextView titleName;

    private TestAdapter testAdapter;
    private List<String> listDatas;

    @Override
    protected void initView() {
        gridView = findViewById(R.id.gridView);
        linLeft = findViewById(R.id.lin_left);
        titleName = findViewById(R.id.title_name);

        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        titleName.setText("测试用");
        listDatas = new ArrayList<>();

        listDatas.add("工单列表");
        listDatas.add("自用部位");
        listDatas.add("公用部位");
        listDatas.add("工单评价");

        listDatas.add("租售委托");
        listDatas.add("我的房产");

        testAdapter = new TestAdapter(this, listDatas);
        gridView.setAdapter(testAdapter);
    }

    @Override
    protected void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(TestActivity.this, WorkOrderListActivity.class);//工单列表
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(TestActivity.this, PersonalWorkCatActivity.class);//自用部位
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(TestActivity.this, PublicWorkOrderCommitActivity.class);//公用部位
                        startActivity(intent);
                        break;
                    case 3:

//                        WorkEvaluateActivity
                        intent.setClass(TestActivity.this, WorkEvaluateSuccessActivity.class);//工单评价
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(TestActivity.this, RentSellCommissionActivity.class);//租售委托
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(TestActivity.this, MyHousePropertyActivity.class);//我的房产
                        startActivity(intent);
                        break;


                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
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
