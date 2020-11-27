package com.huacheng.huiservers.ui.integral;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelIntegralRight;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 我的特权
 * created by wangxiaotao
 * 2020/11/27 0027 09:23
 */
public class MyIntegralRightActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.mListView_my)
    MyListView mListViewMy;
    @BindView(R.id.mListView_more)
    MyListView mListViewMore;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_my_right)
    TextView tv_my_right;
    @BindView(R.id.tv_more_right)
    TextView tv_more_right;
    CommonAdapter adapterMy;
    CommonAdapter adapterMore;
    private List<ModelIntegralRight> mDatasMy = new ArrayList<>();
    private List<ModelIntegralRight> mDatasMore = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("我的特权");
        llContent.setVisibility(View.INVISIBLE);
        adapterMy= new CommonAdapter<ModelIntegralRight>(this,R.layout.item_my_integral_right,mDatasMy) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelIntegralRight item, int position) {
                viewHolder.<TextView>getView(R.id.tv_title).setText(item.getName()+"");
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getContent()+"");
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL + item.getImg());
            }
        };
        mListViewMy.setAdapter(adapterMy);
        adapterMore= new CommonAdapter<ModelIntegralRight>(this,R.layout.item_my_integral_right,mDatasMore) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelIntegralRight item, int position) {
                viewHolder.<TextView>getView(R.id.tv_title).setText(item.getName()+"");
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getContent()+"");
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL + item.getImg());
            }
        };
        mListViewMore.setAdapter(adapterMore);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.MY_RIGHT, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelIntegralRight info = (ModelIntegralRight) JsonUtil.getInstance().parseJsonFromResponse(response, ModelIntegralRight.class);
                    if (info != null) {
                        llContent.setVisibility(View.VISIBLE);
                        if (info.getUser_right() != null && info.getUser_right().size() > 0) {

                            List<ModelIntegralRight> mDatas = info.getUser_right();
                            for (int i = 0; i < mDatas.size(); i++) {
                                if (mDatas.get(i).getIs_my()==1){
                                   mDatasMy.add(mDatas.get(i));
                                }else {
                                    mDatasMore.add(mDatas.get(i));
                                }
                            }
                            if (mDatasMy.size()>0){
                                tv_my_right.setVisibility(View.VISIBLE);
                                mListViewMy.setVisibility(View.VISIBLE);
                                adapterMy.notifyDataSetChanged();
                            }else {
                                tv_my_right.setVisibility(View.GONE);
                                mListViewMy.setVisibility(View.GONE);
                            }

                            if (mDatasMore.size()>0){
                                tv_more_right.setVisibility(View.VISIBLE);
                                mListViewMore.setVisibility(View.VISIBLE);
                                adapterMore.notifyDataSetChanged();
                            }else {
                                tv_more_right.setVisibility(View.GONE);
                                mListViewMore.setVisibility(View.GONE);
                            }

                        } else {
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "暂无数据");
                            SmartToast.showInfo(msg);
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
        return R.layout.activity_my_integral_right;
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

}
