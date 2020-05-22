package com.huacheng.huiservers.ui.index.vote;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.VoteDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelIndexVoteItem;
import com.huacheng.huiservers.model.ModelVoteEvent;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.vote.adapter.IndexVoteAdapter;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.huacheng.libraryservice.utils.timer.CountDownTimer;
import com.microquation.linkedme.android.log.LMErrorCode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:2019年底 票选活动
 * created by wangxiaotao
 * 2019/12/31 0031 下午 2:49
 */
public class VoteVlogIndexActivity extends BaseActivity implements IndexVoteAdapter.OnClickItemListener, VotePresenter.OnGetDataListener {

    private ImageView iv_right;
    protected PagingListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    protected IndexVoteAdapter mAdapter;
    private View headerView;
    private List<ModelIndexVoteItem> mDatas = new ArrayList<>();//数据
    private ImageView iv_vote_vlog_top_bg;
    private LinearLayout ly_comment;
    private LinearLayout ly_vote_rank;
    private LinearLayout ly_vote_detail;

    private int page = 1;
    private TextView tv_day, tv_hour, tv_second, tv_minute;
    private TextView tv_time_type;
    VotePresenter presenter;
    ModelIndexVoteItem mInfo;
    long mDay, mHour, mMin, mSecond;
    private String share_url;
    private CountDownTimer countDownTimer;
    //  private SparseArray<CountDownTimer> countDownCounters;
    private CountDownTimer timer;
    private TextView tv_vote_person_num;
    private View mStatusBar;
    private TextView tv_title_center;
    private LinearLayout ly_search;
    String id = "";
    String color = "#F8F8F8";
    private String canvassing_color = "#F8F8F8";//为他拉票
    private String vote_color = "#F8F8F8";//投他一票
    private String poll_color = "#F8F8F8";//55票
    private ImageView iv_message;
    private ImageView iv_rank;
    private ImageView iv_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView() {
        presenter = new VotePresenter(this, this);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        LinearLayout lin_left = findViewById(com.huacheng.libraryservice.R.id.lin_left);
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleName = findViewById(com.huacheng.libraryservice.R.id.title_name);
        tv_right = findViewById(com.huacheng.libraryservice.R.id.tv_right);
        iv_right = findViewById(R.id.iv_right);
        titleName.setText("活动详情");

        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        //  countDownCounters = new SparseArray<>();
        headerView = LayoutInflater.from(this).inflate(R.layout.header_vote_vlog_index, null);
        initHeaderView();
        mListview.addHeaderView(headerView);

        mAdapter = new IndexVoteAdapter<>(this, mDatas, this,2);
        mListview.setAdapter(mAdapter);
        mListview.setHasMoreItems(false);
        headerView.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化headerview
     */
    private void initHeaderView() {
        iv_vote_vlog_top_bg = headerView.findViewById(R.id.iv_vote_vlog_top_bg);
        ly_comment = headerView.findViewById(R.id.ly_comment);
        iv_message = headerView.findViewById(R.id.iv_message);
        ly_vote_rank = headerView.findViewById(R.id.ly_vote_rank);
        iv_rank = headerView.findViewById(R.id.iv_rank);
        ly_vote_detail = headerView.findViewById(R.id.ly_vote_detail);
        iv_details = headerView.findViewById(R.id.iv_details);
        tv_vote_person_num = headerView.findViewById(R.id.tv_vote_person_num);

        tv_day = headerView.findViewById(R.id.tv_day);
        tv_hour = headerView.findViewById(R.id.tv_hour);
        tv_second = headerView.findViewById(R.id.tv_second);
        tv_minute = headerView.findViewById(R.id.tv_minute);
        tv_time_type = headerView.findViewById(R.id.tv_time_type);
        tv_title_center = headerView.findViewById(R.id.tv_title_center);
        ly_search = headerView.findViewById(R.id.ly_search);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
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
        mListview.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
//        ly_message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(VoteVlogIndexActivity.this, VoteMessageActivity.class);
//                startActivity(intent);
//            }
//        });
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                if (mInfo!=null){
                    share();
                }

            }
        });
        //点击活动评论
        ly_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 活动评论
                if (mInfo!=null){
                    Intent intent = new Intent(mContext, VoteVlogMessageActivity.class);
                    intent.putExtra("color",color);
                    intent.putExtra("id",id);
                    intent.putExtra("top_img",ApiHttpClient.IMG_URL+mInfo.getTop_img());
                    startActivity(intent);
                }

            }
        });
        ly_vote_rank.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 活动排名
                Intent intent = new Intent(mContext, VoteRankListActivity.class);
                intent.putExtra("color",color);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        ly_vote_detail.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 活动详情
              //  ShopZQWebActivity
                if (mInfo!=null&&mInfo.getDetails_link().contains("http")){
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(mInfo.getDetails_link());
                    intent.setData(content_url);
                    mContext.startActivity(intent);
                }
            }
        });
        ly_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInfo!=null){
                    Intent intent = new Intent(mContext, VoteVlogSearchActivity.class);
                    intent.putExtra("color",color);
                    intent.putExtra("vote_color",vote_color);
                    intent.putExtra("canvassing_color",canvassing_color);
                    intent.putExtra("poll_color",poll_color);
                    intent.putExtra("id",id+"");
                    intent.putExtra("share_link",mInfo.getShare_link()+"");
                    intent.putExtra("share_desc",mInfo.getShare_desc()+"");
                    startActivity(intent);
                }
            }
        });
    }

    //活动链接分享
    private void share() {
        share_url = ApiHttpClient.API_URL+mInfo.getShare_link();
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "vote_common_details");
        params.put("id", id+"");
        showDialog(smallDialog);
        LinkedMeUtils.getInstance().getLinkedUrl(this, share_url, "", params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
            @Override
            public void onGetUrl(String url, LMErrorCode error) {
                hideDialog(smallDialog);
                String bitmap = ApiHttpClient.IMG_URL+mInfo.getShare_img();
                if (error == null) {

                    String share_url_new = share_url + "?linkedme=" + url;
                    showSharePop(mInfo.getShare_title(), mInfo.getShare_desc(), bitmap, share_url_new);
                } else {
                    //可以看报错
                    String share_url_new = share_url + "?linkedme=" + "";
                    showSharePop(mInfo.getShare_title(), mInfo.getShare_desc(), bitmap, share_url_new);
                }
            }
        });
    }
    //活动拉票
    private void shareLaPiao(final ModelIndexVoteItem item) {
        share_url = ApiHttpClient.API_URL+mInfo.getShare_link();
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
                    showSharePop(item.getNumber()+" "+item.getTitle(), mInfo.getShare_desc(), ApiHttpClient.IMG_URL+item.getImg(), share_url_new);
                } else {
                    //可以看报错
                    String share_url_new = share_url + "?linkedme=" + "";
                    showSharePop(item.getNumber()+" "+item.getTitle(), mInfo.getShare_desc(), ApiHttpClient.IMG_URL+item.getImg(), share_url_new);
                }
            }
        });
    }


    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        params.put("id",id+"");
        MyOkHttp.get().get(ApiHttpClient.VLOG_INDEX, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                mListview.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelIndexVoteItem info = (ModelIndexVoteItem) JsonUtil.getInstance().parseJsonFromResponse(response, ModelIndexVoteItem.class);
                    if (info != null) {
                        headerView.setVisibility(View.VISIBLE);
                        mInfo = info;
                        GlideUtils.getInstance().glideLoad(mContext,ApiHttpClient.IMG_URL+info.getTop_img(),iv_vote_vlog_top_bg,R.color.default_img_color);
                        GlideUtils.getInstance().glideLoad(mContext,ApiHttpClient.IMG_URL+info.getRank_img(),iv_rank,R.color.default_img_color);
                        GlideUtils.getInstance().glideLoad(mContext,ApiHttpClient.IMG_URL+info.getMessage_img(),iv_message,R.color.default_img_color);
                        GlideUtils.getInstance().glideLoad(mContext,ApiHttpClient.IMG_URL+info.getDetails_img(),iv_details,R.color.default_img_color);
                        tv_vote_person_num.setText(info.getVote_count()+"");
                        tv_title_center.setText(info.getTitle()+"");

                        VoteVlogIndexActivity.this.color=info.getColor()+"";
                        VoteVlogIndexActivity.this.canvassing_color=info.getCanvassing_color()+"";
                        VoteVlogIndexActivity.this.vote_color=info.getVote_color()+"";
                        VoteVlogIndexActivity.this.poll_color=info.getPoll_color()+"";
                        mAdapter.setColor(color,canvassing_color,vote_color,poll_color);
                        if (page==1){
                            //刷新的时候处理时间
                            getTime();
                        }
                        if (info.getList() != null && info.getList().size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                //   mRefreshLayout.setEnableLoadMore(false);
                                mListview.setHasMoreItems(false);
                            } else {
                                //  mRefreshLayout.setEnableLoadMore(true);
                                mListview.setHasMoreItems(true);
                            }
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mListview.setHasMoreItems(false);
                            //   mRefreshLayout.setEnableLoadMore(false);
                        }
                        mAdapter.notifyDataSetChanged();

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
                mListview.setHasMoreItems(false);
                mListview.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
       /* for (int i = 0; i < 29; i++) {
            mDatas.add(new ModelIndexVoteItem());

        }
        mListview.setIsLoading(false);
        mAdapter.notifyDataSetChanged();*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_index;
    }

    @Override
    protected void initIntentData() {
         id = getIntent().getStringExtra("id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClickItem(View v, int position) {
        if (!NullUtil.isStringEmpty(mDatas.get(position).getLink())&&mDatas.get(position).getLink().contains("http")){
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
        presenter.getTouPiaoVlog(mDatas.get(position).getVote_id(),mDatas.get(position).getId());

    }

    @Override
    public void onClickLapiao(View v, int position) {
        // 拉票
        if (mInfo!=null){
            shareLaPiao(mDatas.get(position));
        }

    }

    /**
     * 活动留言返回,详情投票返回
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshIndex(ModelVoteEvent model) {
        //活动留言返回刷新留言数据
        if (model != null) {
            if (model.getType() == 0) {//留言条数刷新
                page=1;
                requestData();
            } else if (model.getType() == 1) {//详情返回首页刷新票数

                for (int i = 0; i < mDatas.size(); i++) {
                    if (model.getId().equals(mDatas.get(i).getId())) {
                        mDatas.get(i).setPoll(model.getIspoll());//当前家庭的票数
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        this.cannelAllTimers();
    }

    /**
     * 首页点击投票
     *
     * @param status
     * @param id  人员id
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
//                    ModelIndexVoteItem item=null;
//                    for (int i = 0; i < mDatas.size(); i++) {
//                        if (id.equals(mDatas.get(i).getId())) {
//                            item=mDatas.get(i);
//                        }
//                    }
//                    // 为他拉票
//                    if (item!=null){
//                        shareLaPiao(item);
//                    }
//                    dialog.dismiss();
                }
            },2);
            voteDialog.show();
        } else {
            SmartToast.showInfo(msg);
        }

    }

    /**
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param bitmap
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, Bitmap bitmap, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, bitmap, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(iv_right);
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
        popup.showBottom(iv_right);
    }


    private void getTime() {//限购时间
        String distanceStart = mInfo.getStartime();
        String distanceEnd = mInfo.getDjstime();
        String distance_str = "";
        long distance_int = 0;
        int distance_tag = 0;
        long longDistanceStart;
        long longDistanceEnd;

        if (!NullUtil.isStringEmpty(distanceStart) && !NullUtil.isStringEmpty(distanceEnd)) {
            long distance_long = Long.parseLong(distanceStart)*1000;
            if (distance_long > System.currentTimeMillis()) {
                tv_time_type.setText("距开始投票还剩");
                distance_tag = 1;
                distance_int = Long.parseLong(distanceStart)*1000-System.currentTimeMillis();

            } else {
                tv_time_type.setText("距结束投票还剩");
                distance_tag = 0;
                distance_int = Long.parseLong(distanceEnd)*1000-System.currentTimeMillis();

            }
            //结束时间-开始时间
            longDistanceStart = Long.parseLong(distanceStart)*1000;
            longDistanceEnd = Long.parseLong(distanceEnd)*1000;
            long longDistanceInterval = longDistanceEnd - longDistanceStart;

            /*long timer = 0;
            if (mInfo.getCurrent_times() == 0) {
                timer = distance_int;
                mInfo.setCurrent_times(System.currentTimeMillis());
            } else {
                timer = distance_int - (System.currentTimeMillis() - mInfo.getCurrent_times());
            }*/
            handlerTime(distance_int, distance_tag, longDistanceInterval);
        } else {

        }

    }


    private void handlerTime(long timeTmp, final int dicountTag, final long longDistanceEnd) {
        //  CountDownTimer countDownTimer = countDownCounters.get(tv_hour.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }
        // 数据
        //long timer = data.expirationTime;
        //timer = timer - System.currentTimeMillis();
        //expirationTime 与系统时间做比较，timer 小于零，则此时倒计时已经结束。
        countDownTimer = new CountDownTimer(timeTmp, 1000) {
            public void onTick(long millisUntilFinished) {
                String[] times = SetTime(millisUntilFinished);
                // mDay = Integer.parseInt(times[0]);

                //tv_XS_type.setText("距开始 " + (times[0]) + "天" + (times[1]) + "时" + (times[2]) + "分" + (times[3]) + "秒");
                tv_day.setText(fillZero(times[0]));
                tv_hour.setText(fillZero(times[1]));
                tv_minute.setText(fillZero(times[2]));
                tv_second.setText(fillZero(times[3]));
            }

            public void onFinish(String redpackage_id) {
                //结束了该轮倒计时
                //1表示活动已开始，0表示活动已结束
                if (dicountTag == 1) {
                    tv_time_type.setText("距结束投票还剩");
                    handlerTime(longDistanceEnd,0,0);

                } else {
                    tv_day.setText("00");
                    tv_hour.setText("00");
                    tv_minute.setText("00");
                    tv_second.setText("00");
                }
//                    holder.statusTv.setText(data.name + ":结束");
            }
        }.start();
        //将此 countDownTimer 放入list.
        //   countDownCounters.put(tv_hour.hashCode(), countDownTimer);

    }



    private String[] SetTime(long time) {
        mDay = time / (1000 * 60 * 60 * 24);
        mHour = (time - mDay * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        mMin = (time - mDay * (1000 * 60 * 60 * 24) - mHour
                * (1000 * 60 * 60))
                / (1000 * 60);
        mSecond = (time - mDay * (1000 * 60 * 60 * 24) - mHour
                * (1000 * 60 * 60) - mMin * (1000 * 60))
                / 1000;
        String[] str = new String[4];
        str[0] = mDay + "";
        str[1] = mHour + "";
        str[2] = mMin + "";
        str[3] = mSecond + "";
        return str;
    }

    private String fillZero(String time) {
        String timeStr = "";
        if (time.length() == 1)
            return "0" + time;
        else
            return time;
    }

    /**
     * 销毁倒计时
     */
    private void cannelAllTimers() {
        if (countDownTimer == null) {
            return;
        }else {
            countDownTimer.cancel();
        }

    }

}

