package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventModelVip;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridOpen;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：Vip开通购买界面
 * 时间：2020/12/1 10:36
 * created by DFF
 */
public class VipBuyActivity extends BaseActivity {

    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_open_num)
    TextView mTvOpenNum;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.grid_open)
    MyGridview mGridOpen;
    @BindView(R.id.tv_btn_buy)
    TextView mTvBtnBuy;
    @BindView(R.id.tv_xieyi)
    TextView mTvXieyi;

    private AdapterVipGridOpen mVipGridOpen;
    private List<ModelVipIndex> mDatas = new ArrayList<>();
    private String vip_id="";
    ModelVipIndex vip;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("立即开通");

        mTvName.setText(vip.getNickname());
        FrescoUtils.getInstance().setImageUri(mSdvHead, StringUtils.getImgUrl(vip.getAvatars()));
        //判断是否是VIP
        if ("1".equals(vip.getIs_vip())){//会员
            mTvTitle.setText("立即续费");
            mTvOpenNum.setText("距离权益到期还有"+vip.getVip_endtime()+"天");
        }else {
            mTvTitle.setText("立即开通");
            mTvOpenNum.setText("您还不是VIP会员");
        }

        //vip开通方式
        mVipGridOpen = new AdapterVipGridOpen(this, R.layout.item_vip_index_open_style, mDatas);
        mGridOpen.setAdapter(mVipGridOpen);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //vip选中开通方式
        mGridOpen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mDatas.size(); i++) {
                    if (position == i) {
                        mDatas.get(i).setSelect(true);
                        vip_id = mDatas.get(i).getId();
                    } else {
                        mDatas.get(i).setSelect(false);
                    }
                }
                mVipGridOpen.notifyDataSetChanged();
            }
        });
        mTvBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBuy();
            }
        });
    }
    //下单购买
    private void getBuy() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        params.put("vip_id",vip_id);
        MyOkHttp.get().post(ApiHttpClient.VIP_BUY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelVipIndex  modelVipIndex = (ModelVipIndex) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVipIndex.class);
                    if (modelVipIndex!=null){
                        Intent intent = new Intent(VipBuyActivity.this, UnifyPayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("o_id", modelVipIndex.getId());
                        bundle.putString("price", modelVipIndex.getPrice() + "");
                        bundle.putString("type", "vip");
                        intent.putExtras(bundle);
                        startActivity(intent);
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
    protected int getLayoutId() {
        return R.layout.layout_vip_buy;
    }

    @Override
    protected void initIntentData() {
        vip = (ModelVipIndex) this.getIntent().getSerializableExtra("vip");
        List<ModelVipIndex> vip_list=vip.getVip_list();
        mDatas.clear();
        mDatas.addAll(vip_list);
        mDatas.get(0).setSelect(true);//默认第一条选中
        vip_id = mDatas.get(0).getId();

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
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    /**
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(EventModelVip info) {
        finish();
    }
}
