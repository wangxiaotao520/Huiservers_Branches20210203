package com.huacheng.huiservers.ui.index.oldservice;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelItemServiceWarm;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterOldServiceWarm;

/**
 * Description: 亲情关怀
 * created by wangxiaotao
 * 2019/8/15 0015 下午 5:51
 */
public class OldServiceWarmActivity extends BaseListActivity<ModelItemServiceWarm>{


    @Override
    protected void initView() {
        super.initView();
        titleName.setText("亲情关怀");
        mAdapter = new AdapterOldServiceWarm(this,R.layout.item_service_warm,mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        //TODO 测试
        initListData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
    //TODO 测试

    private String[] mUrls = new String[]{"http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img3.fengniao.com/forum/attachpics/537/165/21472986.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};

    private void initListData() {
        ModelItemServiceWarm model1 = new ModelItemServiceWarm();
        model1.urlList.add(mUrls[0]);
        mDatas.add(model1);

        ModelItemServiceWarm model2 = new ModelItemServiceWarm();
        model2.urlList.add(mUrls[4]);
        mDatas.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        ModelItemServiceWarm model4 = new ModelItemServiceWarm();
        for (int i = 0; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        mDatas.add(model4);

        ModelItemServiceWarm model5 = new ModelItemServiceWarm();
        for (int i = 0; i < mUrls.length; i++) {
            model5.urlList.add(mUrls[i]);
        }
        mDatas.add(model5);

        ModelItemServiceWarm model6 = new ModelItemServiceWarm();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mDatas.add(model6);

        ModelItemServiceWarm model7 = new ModelItemServiceWarm();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mDatas.add(model7);

        ModelItemServiceWarm model8 = new ModelItemServiceWarm();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        mDatas.add(model8);
    }
}
