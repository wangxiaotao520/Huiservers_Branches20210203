package com.huacheng.huiservers.ui.vip;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyListActivity;

/**
 * @author Created by changyadong on 2020/12/3
 * @description
 */
public class ArticalCollectActivity extends MyListActivity {


     ArticalCollectAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_artical_collect;
    }

    @Override
    protected void initView() {
        super.initView();
        back();
        title("文章收藏");
        adapter = new ArticalCollectAdapter();
        listView.setAdapter(adapter);

    }

    @Override
    public void getData(int page) {

    }

}
