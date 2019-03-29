package com.huacheng.huiservers.ui.index.workorder.commit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.workorder.adapter.AdapterModelCat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:自用报修分类Fragment
 * created by wangxiaotao
 * 2018/12/13 0013 上午 9:49
 */
public class PersonalWorkCatFragment extends BaseFragment {
    private SmartRefreshLayout refreshLayout;
    private ListView listview;
    private int page = 1;
    public boolean isInit = false;
    RelativeLayout rel_no_data;

    int type;

    private List<ModelWorkPersonalCatItem> mDatasList = new ArrayList<>();
    private AdapterModelCat adapterMyHouse;
    private int total_Pages = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        mDatasList.clear();
        mDatasList.addAll ((List<ModelWorkPersonalCatItem>) arguments.getSerializable("item_bean"));
    }

    @Override
    public void initView(View view) {

        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        adapterMyHouse = new AdapterModelCat(mActivity, R.layout.layout_workorder_cat_item, mDatasList);
        listview.setAdapter(adapterMyHouse);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;

                listview.post(new Runnable() {
                    @Override
                    public void run() {
                        listview.setSelection(0);
                    }
                });
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        requestData();
                    }
                }
        );

    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        if (type == 0) {
//            showDialog(smallDialog);
//            isInit=true;
//            requestData();
//        }

    }

    private void requestData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hideDialog(smallDialog);
//                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
//                for (int i = 0; i <15; i++) {
//                    mDatasList.add(new ModelWorkPersonalCatItem());
//                }
//                adapterMyHouse.notifyDataSetChanged();
//            }
//        },1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_workcat;
    }

    /**
     * 初始化
     */
    public void init() {
//        if (!isInit){
//            isInit=true;
//            page=1;
//            refreshLayout.autoRefresh();
//        }
    }

}