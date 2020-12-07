package com.huacheng.huiservers.ui.vip;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelVipSaveMoney;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: vip累计省钱页面
 * created by wangxiaotao
 * 2020/11/30 0030 10:57
 */
public class SaveMoneyListActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_data)
    ImageView imgData;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rel_no_data)
    RelativeLayout relNoData;
    protected int page = 1;
    private int total_Page = 1;
    CommonAdapter mAdapter;
    protected List<ModelVipSaveMoney> mDatas = new ArrayList<>();
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("累计省钱");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new CommonAdapter<ModelVipSaveMoney>(this, R.layout.item_vip_save_money, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelVipSaveMoney item, int position) {

            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }
    /**
     * 请求数据
     */
    private void requestData() {
        String url = ApiHttpClient.VIP_SAVE_MONEY;
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        MyOkHttp.get().get(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelVipSaveMoney model = (ModelVipSaveMoney) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVipSaveMoney.class);
                    tvScore.setText(model.getSave_pice()+"");
                    if (model!=null&&model.getSave_pice_log() != null &&model.getSave_pice_log().size() > 0) {
                        if (page == 1) {
                            mDatas.clear();
                            mListview.post(new Runnable() {
                                @Override
                                public void run() {
                                    mListview.setSelection(0);
                                }
                            });
                        }
                        mDatas.addAll(model.getSave_pice_log());
                        page++;
                        total_Page = model.getTotal_Pages();// 设置总页数
                        relNoData.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                        if (page > total_Page) {
                          //  listView.setHasMoreItems(false);
                            mRefreshLayout.setEnableLoadMore(false);
                        } else {
                          //  listView.setHasMoreItems(true);
                            mRefreshLayout.setEnableLoadMore(true);
                        }
                    } else {
                        if (page == 1) {
                            mDatas.clear();
                            relNoData.setVisibility(View.VISIBLE);
//                            img_data.setBackgroundResource(R.mipmap.bg_no_shop_order_data);
//                            tv_text.setText("暂无订单");
                            mAdapter.notifyDataSetChanged();
                            total_Page = 0;
                        }
                       // listView.setHasMoreItems(false);
                        mRefreshLayout.setEnableLoadMore(false);
                    }
                } else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }
    @Override
    protected void initListener() {
        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_save_money_list;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
