package com.huacheng.huiservers.ui.center;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.adapter.AdapterGoodsServiceFollow;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceItem;
import com.huacheng.huiservers.ui.servicenew.ui.adapter.MerchantServicexAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：商品服务关注Fragment
 * 时间：2020/12/2 09:28
 * created by DFF
 */
public class GoodsServiceFollowFragment extends BaseFragment {
    private int total_Page = 1;
    private int type;

    private View rel_no_data;
    //List<ModelCouponNew> mData = new ArrayList<>();
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private List<ModelOrderList> mData = new ArrayList();
    private List<ModelServiceItem> mDatas2 = new ArrayList();
    MerchantServicexAdapter adapter;
    AdapterGoodsServiceFollow mGoodsServiceFollow;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {
        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);

        if (type==0){
            for (int i = 0; i < 3; i++) {
                mData.add(new ModelOrderList());
            }
            //商品
            mGoodsServiceFollow=new AdapterGoodsServiceFollow(mActivity,R.layout.shop_list_item,mData);
            listview.setAdapter(mGoodsServiceFollow);
        }else {
            for (int i = 0; i < 3; i++) {
                mDatas2.add(new ModelServiceItem());
            }
            //服务
            adapter = new MerchantServicexAdapter(null, mDatas2, mActivity, 1);
            listview.setAdapter(adapter);
        }

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_listcommon;
    }
    /**
     * 选中请求
     */
    public void selected_init() {
        if (isInit) {

        } else {
            page = 1;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
            isInit = true;
        }

    }
}
