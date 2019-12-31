package com.huacheng.huiservers.ui.index.vote;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelVote;
import com.huacheng.huiservers.model.ModelVoteEvent;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.vote.adapter.VoteMessageAdapter;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.view.CircularImage;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: vlog活动留言
 * created by wangxiaotao
 * 2019/12/31 0031 下午 5:50
 */
public class VoteVlogMessageActivity extends BaseActivity implements VoteMessageAdapter.OnClickItemListener {
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;
    @BindView(R.id.iv_photo_bootom)
    CircularImage mIvPhotoBootom;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.lin_comment)
    LinearLayout mLinComment;

    List<ModelVote> mDatas = new ArrayList<>();
    VoteMessageAdapter messageAdapter;
    View headerView;
    private int page = 1;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("活动留言");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        mEtInput.addTextChangedListener(mTextWatcher);
        initHeaderView();

        messageAdapter = new VoteMessageAdapter(this, R.layout.activity_vote_message_item, mDatas, this);
        mListview.setAdapter(messageAdapter);

    }

    private void initHeaderView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_vote_message_header, null);
        mListview.addHeaderView(headerView);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.FAMILY_MESSAGE_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelVote info = (ModelVote) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVote.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                mRefreshLayout.setEnableLoadMore(false);
                            } else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                        } else {
                            if (page == 1) {
                                //mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                        }
                        messageAdapter.notifyDataSetChanged();

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
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                requestData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NullUtil.isStringEmpty(mEtInput.getText().toString().trim())) {
                    SmartToast.showInfo("请输入留言");
                    return;
                }
                Submit();

            }
        });
        mListview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mEtInput!=null){
                    new ToolUtils(mEtInput,VoteVlogMessageActivity.this).closeInputMethod();
                }
                return false;
            }
        });
    }

    /**
     * 提交留言
     */
    private void Submit() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("message", mEtInput.getText().toString().trim());
        MyOkHttp.get().post(ApiHttpClient.FAMILY_MESSAGE_ADD, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //   mRefreshLayout.autoRefresh();
                    mEtInput.setText("");
                    //首页留言数刷新
                    ModelVoteEvent event = new ModelVoteEvent();
                    event.setType(0);
                    EventBus.getDefault().post(event);

                    ModelVote modelVote = (ModelVote) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVote.class);
                    if (modelVote!=null){
                        mDatas.add(0,modelVote);
                    }
                    messageAdapter.notifyDataSetChanged();
                    mListview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListview.setSelection(0);
                        }
                    },200);

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_message_list;
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

    /**
     * 点赞
     *
     * @param v
     * @param position
     */
    @Override
    public void onClickZan(final ModelVote v, int position) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("msg_id", v.getId());
        MyOkHttp.get().post(ApiHttpClient.FAMILY_PRAISE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).getId().equals(v.getId())) {
                            if (v.getType() == 0) {//为0说明没有点赞
                                mDatas.get(i).setPraise(v.getPraise() + 1);
                                mDatas.get(i).setType(1);

                            } else {
                                mDatas.get(i).setPraise(v.getPraise() - 1);
                                mDatas.get(i).setType(0);
                            }
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
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

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = mEtInput.getSelectionStart();
            editEnd = mEtInput.getSelectionEnd();
            if (temp.length() > 300) {
                s.delete(editStart - 1, editEnd);
                mEtInput.setText(s);
                //mTvNum.setText(s.length() + "/150");
                mEtInput.setSelection(s.length());
                SmartToast.showInfo("超出字数范围");
            }
        }
    };

}

