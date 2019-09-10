package com.huacheng.huiservers.ui.index.vote;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.VoteDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelVote;
import com.huacheng.huiservers.model.ModelVoteEvent;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.referral.LMError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：参赛家庭详情
 * 时间：2019/9/5 15:17
 * created by DFF
 */
public class VoteDetailActivity extends BaseActivity implements VotePresenter.OnGetDataListener {

    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_piao)
    TextView mTvPiao;
    @BindView(R.id.tv_lapiao)
    TextView mTvLapiao;
    @BindView(R.id.tv_toupiao)
    TextView mTvToupiao;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_family_bg)
    ImageView mIvFamilyBg;
    private String id;
    private String piao = "";//投票的次数
    VotePresenter presenter;
    ModelVote info;
    private String share_url;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("参赛作品");
        presenter = new VotePresenter(this, this);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();

    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");
        MyOkHttp.get().post(ApiHttpClient.FAMILY_DETAIL, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    info = (ModelVote) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVote.class);
                    if (info != null) {
                        mTvNum.setText(info.getNumber());
                        mTvName.setText(info.getTitle());
                        mTvPiao.setText(info.getPoll() + "");
                        mTvContent.setText(info.getContent());
                        GlideUtils.getInstance().glideLoad(VoteDetailActivity.this, ApiHttpClient.IMG_URL + info.getImg(), mIvFamilyBg, R.drawable.ic_default_15);

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

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");
        piao = this.getIntent().getStringExtra("poll");

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

    @OnClick({R.id.tv_lapiao, R.id.tv_toupiao, R.id.iv_family_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lapiao://分享拉票
                if (info == null) {
                    return;
                }
                if (NullUtil.isStringEmpty(info.getId())) {
                    return;
                }
                share_url = ApiHttpClient.API_URL_SHARE + ApiHttpClient.API_VERSION + "family/familyView/id/" + id;
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                showDialog(smallDialog);
                LinkedMeUtils.getInstance().getLinkedUrl(this, share_url, "", params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
                    @Override
                    public void onGetUrl(String url, LMError error) {
                        hideDialog(smallDialog);
                        if (error == null) {
                            String share_url_new = share_url + "?linkedme=" + url;
                            showSharePop(info.getTitle(), info.getContent(), ApiHttpClient.IMG_URL + info.getImg(), share_url_new);
                        } else {
                            //可以看报错
                            String share_url_new = share_url + "?linkedme=" + "";
                            showSharePop(info.getTitle(), info.getContent(), ApiHttpClient.IMG_URL + info.getImg(), share_url_new);

                        }
                    }
                });

                break;
            case R.id.iv_family_bg://家庭图片放大
                if (!NullUtil.isStringEmpty(info.getImg())) {
                    ArrayList<String> imgs = new ArrayList<>();
                    imgs.add(ApiHttpClient.IMG_URL + info.getImg());

                    Intent intent = new Intent(this, PhotoViewPagerAcitivity.class);
                    intent.putExtra("img_list", imgs);
                    intent.putExtra("position", 0);
                    intent.putExtra("isShowDelete", false);
                    startActivity(intent);

                }
                break;
            case R.id.tv_toupiao://投票
                showDialog(smallDialog);
                presenter.getTouPiao(id);
                break;
        }
    }

    /**
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param share_icon
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String share_icon, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, share_icon, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(mTvLapiao);
    }

    @Override
    public void onGetData(int status, String family_id, String msg) {
        hideDialog(smallDialog);
        if (status == 1) {
            //投票成功 首页投票次数刷新  当前家庭票数增加
            VoteDialog voteDialog = new VoteDialog(this, new VoteDialog.OnCustomDialogListener() {
                @Override
                public void back(String tag, Dialog dialog) {
                    if (tag.equals("1")) {
                        info.setPoll(info.getPoll() + 1);
                        mTvPiao.setText(info.getPoll() + "");//当前界面票数增加
                        piao = (Integer.valueOf(piao) - 1) + "";//投票次数
                        //返回首页刷新
                        ModelVoteEvent event = new ModelVoteEvent();
                        event.setType(1);
                        event.setIspoll(info.getPoll());
                        event.setId(id);
                        event.setIspiao(Integer.valueOf(piao));
                        EventBus.getDefault().post(event);
                        dialog.dismiss();
                    }
                }
            });
            voteDialog.show();

        } else {
            SmartToast.showInfo(msg);
        }

    }
}
