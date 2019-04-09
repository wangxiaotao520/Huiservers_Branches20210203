package com.huacheng.huiservers.ui.index.workorder_second;

import android.widget.GridView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;

import java.util.ArrayList;

/**
 * 类描述：
 * 时间：2019/4/9 19:36
 * created by DFF
 */
public class WorkOrderPingjiaActivity extends BaseActivity {
    protected GridView gridview_imgs;
    SelectImgAdapter gridviewImgsAdapter;
    protected ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("评价工单");
        gridview_imgs = findViewById(R.id.gridview_imgs);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridviewImgsAdapter.setShowDelete(true);//不显示删除
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
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
