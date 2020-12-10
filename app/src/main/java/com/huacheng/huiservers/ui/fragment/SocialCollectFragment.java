package com.huacheng.huiservers.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.GsonCallback;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.model.ArticalCollect;
import com.huacheng.huiservers.ui.base.MyFragment;
import com.huacheng.huiservers.ui.base.MyListFragment;
import com.huacheng.huiservers.ui.center.presenter.CollectDeletePresenter;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.vip.ArticalCollectAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by changyadong on 2020/12/10
 * @description
 */
public class SocialCollectFragment extends MyListFragment {

    String mCate;

    public static SocialCollectFragment newInstance(String cate) {

        Bundle args = new Bundle();
        args.putString("cate", cate);
        SocialCollectFragment fragment = new SocialCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ArticalCollectAdapter adapter;
    CollectDeletePresenter mDeletePresenter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_list;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        adapter = new ArticalCollectAdapter();
        listView.setAdapter(adapter);
        refreshLayout.setEnableLoadMore(false);
        listView.setDividerHeight(2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // todo  缺少 isPro（物业公告） 缺少是否收藏过
                Intent it = new Intent();
                it.setClass(mContext, CircleDetailsActivity.class);
                it.putExtra("id", adapter.getItem(i).getP_id());
                startActivity(it);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确认要删除吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {

                            showDialog(smallDialog);
                            mDeletePresenter.getDeleteCollect(adapter.getItem(position).getCollection_id(), mCate);

                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();
                return true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mCate = getArguments().getString("cate");

        mDeletePresenter = new CollectDeletePresenter(mContext, new CollectDeletePresenter.CollectListener() {

            @Override
            public void onDeleteCollect(int status, String collect_id, String msg) {
                smallDialog.dismiss();
                if (status == 1) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (adapter.getItem(i).getCollection_id().equals(collect_id)) {
                            adapter.remove(adapter.getItem(i));
                            break;
                        }
                    }
                    setEmpty(adapter);
                } else {
                    SmartToast.showInfo(msg);
                }
            }
        });
        getData(mPage);
    }

    @Override
    public void getData(final int page) {

        smallDialog.show();
        String url = null;
        if (mCate.equals("5")) {
            url = ApiHttpClient.MY_COLLECT_SOCIAL;
        } else if (mCate.equals("6")) {
            url = ApiHttpClient.MY_COLLECT_SOCIAL_PRO;
        }
        Map<String, String> map = new HashMap<>();
        MyOkHttp.get().get(url, map, new GsonCallback<ArticalCollect>() {
            @Override
            public void onFailure(int code) {
                smallDialog.dismiss();
                loadComplete();
            }

            @Override
            public void onSuccess(ArticalCollect articalCollect) {
                smallDialog.dismiss();
                loadComplete();
                if (articalCollect.getStatus() == 1) {
                    if (page == 1) {
                        adapter.clearData();
                    }
                    adapter.addData(articalCollect.getData().getList());
                }
                setEmpty(adapter);

            }
        });
    }
}
