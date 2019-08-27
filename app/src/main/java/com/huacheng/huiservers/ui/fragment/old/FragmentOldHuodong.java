package com.huacheng.huiservers.ui.fragment.old;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldHuoDong;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
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
 * Description: 养老活动Fragment
 * created by wangxiaotao
 * 2019/8/14 0014 下午 5:00
 */
public class FragmentOldHuodong extends FragmentOldCommonImp {

    private RecyclerView recyclerview;
    private CommonAdapter<ModelOldHuoDong> mAdapter;
    private List<ModelOldHuoDong> mDatas = new ArrayList<>();
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


        mAdapter = new CommonAdapter<ModelOldHuoDong>(mActivity, R.layout.item_old_huodong, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, ModelOldHuoDong s, int position)
            {
              //  holder.setText(R.id.tv_name, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
                FrescoUtils.getInstance().setImageUri(holder.<SimpleDraweeView>getView(R.id.sdv_img),ApiHttpClient.IMG_URL+s.getTop_img());
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
        //活动不加载
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_recyclerview;
    }
    @Override
    public void isRefresh(String par_uid) {
        if (!LoginUtils.hasLoginUser()){
            return;
        }
        // super.isRefresh(par_uid);
        if (!isInit){
            if (!NullUtil.isStringEmpty(par_uid)){//认证后才有值
                this.par_uid = par_uid;
                page = 1;
                isInit=true;
                showDialog(smallDialog);
                requestData();
            }
        }else {
            //只要par_uid 不同就需要刷新
            if (!NullUtil.isStringEmpty(par_uid)&&!this.par_uid.equals(par_uid)){
                //par_uid不为空,
                this.par_uid = par_uid;
                page = 1;
                showDialog(smallDialog);
                requestData();
            }
        }
    }

    @Override
    public void refreshIndeed(String par_uid) {
        if (!LoginUtils.hasLoginUser()){
            return;
        }
        if (!NullUtil.isStringEmpty(par_uid)){//只有不为空的时候才有活动数据
            this.par_uid = par_uid;
            page=1;
            isInit=true;
            requestData();
        }

    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid",par_uid);
        }
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.PENSION_BOTTOM_ACTIVITY, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldHuoDong info = (ModelOldHuoDong) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldHuoDong.class);
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
}

