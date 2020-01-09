package com.huacheng.huiservers.ui.index.vote;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelVlogRankList;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description: 活动排行榜
 * created by wangxiaotao
 * 2019/12/31 0031 下午 6:10
 */
public class VoteRankListActivity extends BaseListActivity<ModelVlogRankList>{
    @Override
    protected void initView() {
        super.initView();
        titleName.setText("活动排行榜");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new CommonAdapter<ModelVlogRankList>(this, R.layout.item_vote_rank_list,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelVlogRankList item, int position) {
                if (position==0){
                    viewHolder.<ImageView>getView(R.id.iv_123).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.tv_rank).setVisibility(View.GONE);
                    viewHolder.<ImageView>getView(R.id.iv_123).setBackgroundResource(R.mipmap.ic_vote_rank1);
                }else if (position==1){
                    viewHolder.<ImageView>getView(R.id.iv_123).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.tv_rank).setVisibility(View.GONE);
                    viewHolder.<ImageView>getView(R.id.iv_123).setBackgroundResource(R.mipmap.ic_vote_rank2);
                }else if (position==2){
                    viewHolder.<ImageView>getView(R.id.iv_123).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.tv_rank).setVisibility(View.GONE);
                    viewHolder.<ImageView>getView(R.id.iv_123).setBackgroundResource(R.mipmap.ic_vote_rank3);
                }else {
                    viewHolder.<ImageView>getView(R.id.iv_123).setVisibility(View.GONE);
                    viewHolder.<TextView>getView(R.id.tv_rank).setVisibility(View.VISIBLE);
                }
                viewHolder.<TextView>getView(R.id.tv_rank).setText(item.getRanking()+"");
                viewHolder.<TextView>getView(R.id.tv_num).setText(item.getNumber()+"号");
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getTitle()+"");
                viewHolder.<TextView>getView(R.id.tv_piao_num).setText(item.getPoll()+"票");
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head),StringUtils.getImgUrl(item.getImg()));

            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        HashMap<java.lang.String, java.lang.String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.VLOG_RANK_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                   List<ModelVlogRankList>  list = (List<ModelVlogRankList>) JsonUtil.getInstance().getDataArrayByName(response, "data",ModelVlogRankList.class);
                    if (list != null) {
                        if (list != null && list.size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(list);
//                            page++;
//                            if (page > info.getTotalPages()) {
//                                mRefreshLayout.setEnableLoadMore(false);
//                            } else {
//                                mRefreshLayout.setEnableLoadMore(true);
//                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    java.lang.String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, java.lang.String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

    }
}
