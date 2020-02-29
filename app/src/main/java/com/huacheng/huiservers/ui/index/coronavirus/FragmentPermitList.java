package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：电子通行证列表
 * 时间：2020/2/22 16:09
 * created by DFF
 */
public class FragmentPermitList extends BaseFragment {
    SharePrefrenceUtil prefrenceUtil;
    private int total_Page = 1;
    private int type;
    private String company_id;

    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ModelPermit> mData = new ArrayList<>();
    private int page = 1;
    private PermitListAdapter adapter;
    private boolean isInit = false;
    private String mType_back;
    private ImageView img_data;
    private TextView tv_text;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        company_id = arguments.getString("company_id");
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        img_data = view.findViewById(R.id.img_data);
        tv_text = view.findViewById(R.id.tv_text);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = view.findViewById(R.id.listview);

        adapter = new PermitListAdapter(mActivity, R.layout.layout_permit_item, mData);
        listView.setAdapter(adapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, PermitDetailActivity.class);
                intent.putExtra("company_id",company_id);
                intent.putExtra("id",mData.get(position).getId());
                startActivity(intent);

            }
        });
        //一开始先设置成false onScrollListener
        listView.setHasMoreItems(false);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                requestData();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //第一页做特殊处理
        if (type == 0) {
            isInit = true;
//            page = 1;
//            isRequesting = true;
//            showDialog(smallDialog);
//            requestData();
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

    public void setRefreh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        if (type == 0) {
            params.put("type", "ok");
        } else if (type == 1) {
            params.put("type", "ing");
        } else if (type == 2) {
            params.put("type", "refuse");
        } else if (type == 3) {
            params.put("type", "over");
        }
        params.put("company_id", company_id);
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.GET_PERMIT_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    ModelPermit modelPermit = (ModelPermit) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPermit.class);
                    List<ModelPermit> list = modelPermit.getPc_list();
                    if (modelPermit != null && modelPermit.getPc_list() != null && modelPermit.getPc_list().size() > 0) {
                        if (page == 1) {
                            mData.clear();
                            listView.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(0);
                                }
                            });
                        }
                        mData.addAll(list);
                        page++;
                        total_Page = modelPermit.getTotalPages();// 设置总页数
                        rel_no_data.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        if (page > total_Page) {

                            listView.setHasMoreItems(false);
                        } else {

                            listView.setHasMoreItems(true);
                        }
                    } else {
                        if (page == 1) {
                            mData.clear();
                            rel_no_data.setVisibility(View.VISIBLE);
//                            img_data.setBackgroundResource(R.mipmap.bg_no_shop_order_data);
//                            tv_text.setText("暂无订单");
                            adapter.notifyDataSetChanged();
                            total_Page = 0;
                        }
                        listView.setHasMoreItems(false);
                    }


                } else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vote_index;
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
