package com.huacheng.huiservers.ui.fragment.old;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelArticle;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 养老咨询
 * created by wangxiaotao
 * 2019/8/5 0005 上午 10:09
 */
public class FragmentOldArticle extends FragmentOldCommonImp {

    private RecyclerView recyclerview;
    private CommonAdapter<ModelArticle> mAdapter;
    private List<ModelArticle> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private String par_uid= "";
    private int page = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        par_uid = arguments.getString("par_uid");
    }
    @Override
    public void initView(View view) {
      //  SmartRefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
       // recyclerview.setAdapter(new RecyclerviewAdapter());


        mAdapter = new CommonAdapter<ModelArticle>(mActivity, R.layout.item_article, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, ModelArticle s, int position)
            {
              //  holder.setText(R.id.tv_name, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(0);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                requestData();
            }
        });

        recyclerview.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                Toast.makeText(mActivity, "pos = " + position, Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });

    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isInit = true;
        page=1;
        showDialog(smallDialog);
        requestData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void isRefresh(String par_uid) {
            // super.isRefresh(par_uid);
            if (!isInit){
                page=1;
                showDialog(smallDialog);
                isInit=true;
                requestData();
            }else {
                //资讯不需要判断参数,也没必要实时刷新，只需要在下拉刷新的时候刷新一下就可以了
            }

    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.PENSION_ZIXUN_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelArticle info = (ModelArticle) JsonUtil.getInstance().parseJsonFromResponse(response, ModelArticle.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                mLoadMoreWrapper.setLoadMoreView(0);
                            } else {
                                mLoadMoreWrapper.setLoadMoreView(R.layout.layout_refresh_footer);

                            }
                            mLoadMoreWrapper.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mLoadMoreWrapper.setLoadMoreView(0);
                            mLoadMoreWrapper.notifyDataSetChanged();
                        }
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mLoadMoreWrapper.setLoadMoreView(0);
                }
            }
        });
    }

    @Override
    public void refreshIndeed(String par_uid) {
        //直接刷新 不显示smallDialog
        isInit=true;
        page=1;
        requestData();
    }
}
