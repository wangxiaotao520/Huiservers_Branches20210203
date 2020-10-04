package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldDevice;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterOldDevice;
import com.huacheng.huiservers.view.MyGridview;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：养老 查看更多
 *  时间：2020/9/30 16:06
 * created by DFF
 */
public class OldDeviceMoreActivity extends BaseActivity {

    private MyGridview mGridview1;
    private MyGridview mGridview2;

    private List<ModelOldDevice> mDatas1 = new ArrayList<>();//常用功能
    private List<ModelOldDevice> mDatas2 = new ArrayList<>();//功能设置

    private AdapterOldDevice mAdapterOldDevice1;
    private AdapterOldDevice mAdapterOldDevice2;
    private String par_uid ="";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("全部功能");
        mGridview1 = findViewById(R.id.grid_cat1);
        mGridview2 = findViewById(R.id.grid_cat2);

        String[] str = {"查看足迹", "健康计步", "远程测心率", "远程测血压", "云测温",  "电子围栏"};
        for (int i = 0; i < str.length; i++) {
            ModelOldDevice selectCommon = new ModelOldDevice();
            selectCommon.setName(str[i]);
            mDatas1.add(selectCommon);
        }
        String[] str1 = {"查找设备", "SOS", "监护号码"};
        for (int i = 0; i < str1.length; i++) {
            ModelOldDevice selectCommon = new ModelOldDevice();
            //selectCommon.setId((i + 1) + "");
            selectCommon.setName(str1[i]);
            mDatas2.add(selectCommon);
        }

        mAdapterOldDevice1 = new AdapterOldDevice(this, R.layout.item_old_device, mDatas1,1);
        mGridview1.setAdapter(mAdapterOldDevice1);

        mAdapterOldDevice2 = new AdapterOldDevice(this, R.layout.item_old_device, mDatas2,2);
        mGridview2.setAdapter(mAdapterOldDevice2);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mGridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//查看足迹

                    Intent intent = new Intent(OldDeviceMoreActivity.this, MyTrackActivity.class);
                    startActivity(intent);

                } else if (position == 1) {//健康计步
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldMyStepActivity.class);
                    startActivity(intent);

                } else if (position == 2) {//远程测心率

                } else if (position == 3) {//远程测血压

                } else if (position == 4) {//云测温

                } else if (position == 5) {//电子围栏
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldFenceListActivity.class);
                    intent.putExtra("par_uid",par_uid);
                    startActivity(intent);
                } else if (position == 6) {//电子围栏


                } else if (position == 7) {//立即定位

                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_old_device_more;
    }

    @Override
    protected void initIntentData() {
        par_uid=getIntent().getStringExtra("par_uid");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
