package com.huacheng.huiservers.ui.index.workorder_second.commit;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.AdapterPriceList;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 工单价目表列表
 * created by wangxiaotao
 * 2019/4/10 0010 下午 3:08
 */
public class WorkPriceListActivity extends BaseActivity{
    protected int page = 1;
    protected ExpandableListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private AdapterPriceList adapterPriceList;
    List<ModelWorkPersonalCatItem> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("价目表");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        // 设置数据
        adapterPriceList = new AdapterPriceList(this,mDatas);
        mListview.setAdapter(adapterPriceList);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.MARKED_PRICE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    List <ModelWorkPersonalCatItem>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelWorkPersonalCatItem.class);
                    mDatas.clear();
                    mDatas.addAll(data);
                    adapterPriceList.notifyDataSetChanged();
                    //     第一次加载就展开所有的子类
                    for (int i = 0; i < adapterPriceList.getGroupCount(); i++) {
                        mListview.expandGroup(i);
                    }
                    if (mDatas.size()==0){
                        mRelNoData.setVisibility(View.VISIBLE);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取数据失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });



    }

    @Override
    protected void initListener() {
          //  不能点击收缩
            mListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_price_list;
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
