package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCircle;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.geren.adapter.CircleItemImageAdapter;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：我的圈子fragment
 * 时间：2020/1/2 09:39
 * created by DFF
 */
public class MyInfoCircleFragment extends BaseFragment {

    private RecyclerView recyclerview;
    private CommonAdapter<ModelCircle> adapter;
    //private CircleListAdapter adapter;
    //private List<ModelArticle> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private String par_uid = "";
    private int page = 1;
    private ImageView iv_no_data;
    private RelativeLayout ll_no_data;
    private int type;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private List<ModelCircle> mDatas = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        sharePrefrenceUtil = new SharePrefrenceUtil(mActivity);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

      /* adapter = new CircleListAdapter(mActivity, R.layout.item_circle_list, mDatas, this, 1);
        recyclerview.setAdapter(adapter);*/

        adapter = new CommonAdapter<ModelCircle>(mActivity, R.layout.item_circle_list, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, final ModelCircle item, int position) {
                if ("0".equals(item.getAdmin_id())) { //0为用户发布的
                    viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.GONE);
                    viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.tv_line).setVisibility(View.VISIBLE);

                    FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_user_head), StringUtils.getImgUrl(item.getAvatars()));
                    viewHolder.<TextView>getView(R.id.tv_username).setText(item.getNickname());
                    viewHolder.<TextView>getView(R.id.tv_usertime).setText(item.getAddtime());
                    if (BaseApplication.getUser() != null) {
                        if (String.valueOf(BaseApplication.getUser().getUid()).equals(item.getUid())) {
                            viewHolder.<ImageView>getView(R.id.iv_del).setVisibility(View.VISIBLE);
                            viewHolder.<TextView>getView(R.id.tv_my_reply).setText(item.getReply_num() + "条新评论 >");
                            viewHolder.<ImageView>getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                  /*  if (mOnItemDeleteListener != null) {
                                        mOnItemDeleteListener.onDeleteClick(item.getId());
                                    }*/
                                    onDeleteClick(item.getId());
                                }
                            });

                            viewHolder.<RelativeLayout>getView(R.id.ry_my).setVisibility(View.VISIBLE);
                            viewHolder.<RelativeLayout>getView(R.id.ry_my).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   /* if (mOnItemDeleteListener != null) {
                                        mOnItemDeleteListener.onJumpPinglun(item);
                                    }*/
                                    onJumpPinglun(item);
                                }
                            });


                        } else {

                            viewHolder.<ImageView>getView(R.id.iv_del).setVisibility(View.GONE);

                        }
                    }

                    byte[] bytes1 = Base64.decode(item.getContent(), Base64.DEFAULT);
                    viewHolder.<TextView>getView(R.id.tv_content).setText(new String(bytes1));
                    if (item.getImg_list() != null && item.getImg_list().size() > 0) {
                        viewHolder.<MyGridview>getView(R.id.gridView).setVisibility(View.VISIBLE);
                        CircleItemImageAdapter mGridViewAdapter = new CircleItemImageAdapter(mContext, item.getImg_list());
                        viewHolder.<MyGridview>getView(R.id.gridView).setAdapter(mGridViewAdapter);

                    } else {
                        viewHolder.<MyGridview>getView(R.id.gridView).setVisibility(View.GONE);
                    }
                    viewHolder.<TextView>getView(R.id.tv_tagname).setText("#" + item.getC_name() + "#");
                    viewHolder.<TextView>getView(R.id.tv_read).setText(item.getClick());
                    viewHolder.<TextView>getView(R.id.tv_reply).setText(item.getReply_num());

                } else {
                    viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.VISIBLE);
                    viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.GONE);
                    viewHolder.<TextView>getView(R.id.tv_line).setVisibility(View.GONE);

                    byte[] bytes1 = Base64.decode(item.getTitle(), Base64.DEFAULT);
                    viewHolder.<TextView>getView(R.id.tv_title).setText(new String(bytes1));
                    viewHolder.<TextView>getView(R.id.tv_catname).setText(item.getC_name());
                    viewHolder.<TextView>getView(R.id.tv_readnum).setText(item.getClick() + "阅读");
                    viewHolder.<TextView>getView(R.id.tv_addtime).setText(item.getAddtime());

                    if (item.getImg_list() != null && item.getImg_list().size() > 0) {
                        if (!TextUtils.isEmpty(item.getImg_list().get(0).getImg())) {
                            viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.VISIBLE);
                            FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), MyCookieStore.URL + item.getImg_list().get(0).getImg());
                        } else {
                            viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
                        }

                    } else {
                        viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
                    }

                }

            }
        };

        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(0);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData();
            }
        });

        recyclerview.setAdapter(mLoadMoreWrapper);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // Toast.makeText(mActivity, "pos = " + position, Toast.LENGTH_SHORT).show();
                //  mAdapter.notifyItemRemoved(position);

                Intent intent = new Intent();
                intent.setClass(mActivity, CircleDetailsActivity.class);
                intent.putExtra("id", mDatas.get(position).getId());
                intent.putExtra("mPro", mDatas.get(position).getIs_pro());
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        ll_no_data = view.findViewById(R.id.rel_no_data);
        ll_no_data.setVisibility(View.GONE);
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //isInit = true;
        page = 1;
        showDialog(smallDialog);
        requestData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_info_circle_list;
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())) {
            params.put("community_id", sharePrefrenceUtil.getXiaoQuId());
        }
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.GET_USER_SOCIAL, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelCircle> mlist = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCircle.class);
                    if (mlist != null && mlist.size() > 0) {
                        if (page == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(mlist);
                        page++;
                        if (page > mlist.get(0).getTotal_Pages()) {
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
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public void onDeleteClick(final String id) {
        new CommomDialog(mActivity, R.style.my_dialog_DimEnabled, "删除此信息吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    SocialDel(id);
                    dialog.dismiss();
                }

            }
        }).show();//.setTitle("提示")
    }

    /**
     * 跳转到评论位置
     */

    public void onJumpPinglun(ModelCircle item) {
        Intent intent = new Intent();
        intent.setClass(mActivity, CircleDetailsActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("mPro", item.getIs_pro());
        intent.putExtra("SCROLLtag", "1");//滑到评论位置
        startActivity(intent);

    }

    /**
     * 删除邻里
     */
    private void SocialDel(final String id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("social_id", id);
        MyOkHttp.get().post(ApiHttpClient.SOCIAL_DELETE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //删除
                    ModelCircle modelCircle_del = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(id)) {
                            modelCircle_del = modelCircle;
                        }
                    }
                    if (modelCircle_del != null) {
                        mDatas.remove(modelCircle_del);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
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
            }
        });
    }

    /**
     * 添加和删除评论的Eventbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateItem(CircleDetailBean mCirclebean) {
        try {
            if (mCirclebean != null) {
                int type = mCirclebean.getType();
                if (type == 0) {
                    //添加评论
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getReply_num());
                            modelCircle.setReply_num((i1 + 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else if (type == 1) {
                    //删除评论
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getReply_num());
                            modelCircle.setReply_num((i1 - 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else if (type == 2) {//阅读数
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getClick());
                            modelCircle.setClick((i1 + 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
