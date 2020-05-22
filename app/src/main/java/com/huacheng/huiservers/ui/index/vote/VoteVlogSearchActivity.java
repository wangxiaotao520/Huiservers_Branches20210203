package com.huacheng.huiservers.ui.index.vote;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.VoteDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelIndexVoteItem;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.vote.adapter.IndexVoteAdapter;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 投票搜索
 * created by wangxiaotao
 * 2020/5/22 0022 09:13
 */
public class VoteVlogSearchActivity extends BaseActivity implements IndexVoteAdapter.OnClickItemListener, VotePresenter.OnGetDataListener {
    private ImageView search_back;
    private TextView txt_search;
    private EditText input;
    private PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private List<ModelIndexVoteItem> mDatas = new ArrayList<>();//数据
    private IndexVoteAdapter mAdapter;
    VotePresenter presenter;
    private String share_url;
    private String color = "";
    private String vote_color = "";
    private String canvassing_color = "";
    private String poll_color = "";
    private String id = "";
    private String share_link,share_desc;//分享的链接和话述


    @Override
    protected void initView() {
        presenter = new VotePresenter(this, this);
        search_back = findViewById(R.id.search_back);
        txt_search = findViewById(R.id.txt_search);
        input = findViewById(R.id.et_search);
        listView = findViewById(R.id.listView);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);
        //搜索按钮颜色设置
        GradientDrawable mm= (GradientDrawable)txt_search.getBackground();
        mm.setColor(Color.parseColor(color));

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new IndexVoteAdapter<>(this, mDatas, this, 2);
        listView.setAdapter(mAdapter);
        listView.setHasMoreItems(false);
        listView.setIsLoading(false);


    }

    @Override
    protected void initData() {

    }

    /**
     * 请求数据
     */
    private void requestData() {
        showDialog(smallDialog);
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("key", input.getText().toString().trim());
        MyOkHttp.get().get(ApiHttpClient.VOTE_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setHasMoreItems(false);
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelIndexVoteItem> info = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelIndexVoteItem.class);
                    if (info != null && info.size() > 0) {
                        mRelNoData.setVisibility(View.GONE);
                        mDatas.clear();
                        mDatas.addAll(info);
                        mAdapter.setColor(color, canvassing_color, vote_color, poll_color);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mRelNoData.setVisibility(View.VISIBLE);
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setHasMoreItems(false);
                listView.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                mRefreshLayout.setEnableLoadMore(false);
            }
        });
    }

    @Override
    protected void initListener() {
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NullUtil.isStringEmpty(input.getText().toString().trim())) {
                    requestData();
                } else {
                    SmartToast.showInfo("请输入要搜索的内容");
                }
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (input!=null){
                    new ToolUtils(input,VoteVlogSearchActivity.this).closeInputMethod();
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_vlog_search;
    }

    @Override
    protected void initIntentData() {

        color = this.getIntent().getStringExtra("color");
        vote_color = this.getIntent().getStringExtra("vote_color");
        canvassing_color = this.getIntent().getStringExtra("canvassing_color");
        poll_color = this.getIntent().getStringExtra("poll_color");
        id = this.getIntent().getStringExtra("id");
        share_link = this.getIntent().getStringExtra("share_link");
        share_desc = this.getIntent().getStringExtra("share_desc");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    /**
     * 首页点击投票
     *
     * @param status
     * @param id     人员id
     * @param msg
     */
    @Override
    public void onGetData(int status, final String id, String msg) {
        hideDialog(smallDialog);
        if (status == 1) {
            //投票成功
            VoteDialog voteDialog = new VoteDialog(this, new VoteDialog.OnCustomDialogListener() {
                @Override
                public void back(String tag, Dialog dialog) {
                    if (tag.equals("1")) {
                        //当前界面投票

                        for (int i = 0; i < mDatas.size(); i++) {
                            if (id.equals(mDatas.get(i).getId())) {
                                mDatas.get(i).setPoll(mDatas.get(i).getPoll() + 1);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }

                @Override
                public void lapaiao(Dialog dialog) {
                    //useless
//                    ModelIndexVoteItem item = null;
//                    for (int i = 0; i < mDatas.size(); i++) {
//                        if (id.equals(mDatas.get(i).getId())) {
//                            item = mDatas.get(i);
//                        }
//                    }
//                    // 为他拉票
//                    if (item != null) {
//                        shareLaPiao(item);
//                    }
//                    dialog.dismiss();
                }
            }, 2);
            voteDialog.show();
        } else {
            SmartToast.showInfo(msg);
        }

    }

    @Override
    public void onClickItem(View v, int position) {
        if (!NullUtil.isStringEmpty(mDatas.get(position).getLink()) && mDatas.get(position).getLink().contains("http")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(mDatas.get(position).getLink());
            intent.setData(content_url);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onClickVote(View v, int position) {
        // SmartToast.showInfo("点击vote" + position);
        showDialog(smallDialog);
        presenter.getTouPiaoVlog(mDatas.get(position).getVote_id(), mDatas.get(position).getId());

    }


    @Override
    public void onClickLapiao(View v, int position) {
        // 拉票
        if (!NullUtil.isStringEmpty(share_link)){
            shareLaPiao(mDatas.get(position));
        }

    }

    //活动拉票
    private void shareLaPiao(final ModelIndexVoteItem item) {
        share_url = ApiHttpClient.API_URL+share_link;
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "vote_common_details");
        params.put("id", id+"");
        showDialog(smallDialog);
        LinkedMeUtils.getInstance().getLinkedUrl(this, share_url, "", params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
            @Override
            public void onGetUrl(String url, LMErrorCode error) {
                hideDialog(smallDialog);

                if (error == null) {
                    String share_url_new = share_url + "?linkedme=" + url;
                    showSharePop(item.getNumber() + " " + item.getTitle(), share_desc, ApiHttpClient.IMG_URL + item.getImg(), share_url_new);
                } else {
                    //可以看报错
                    String share_url_new = share_url + "?linkedme=" + "";
                    showSharePop(item.getNumber() + " " + item.getTitle(), share_desc, ApiHttpClient.IMG_URL + item.getImg(), share_url_new);
                }
            }
        });
    }

    /**
     * 显示分享弹窗2
     *
     * @param share_title
     * @param share_desc
     * @param bitmap
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String bitmap, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, bitmap, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(txt_search);
    }


}
