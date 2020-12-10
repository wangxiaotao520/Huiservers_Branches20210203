package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventModelVip;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridCat;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridOpen;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipIndex;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * 类描述：vip特权首页
 * 时间：2020/11/30 08:35
 * created by DFF
 */
public class VipIndexActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ly_message)
    LinearLayout mLyMessage;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.ly_share)
    LinearLayout mLyShare;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;

    View headerView;
    HeaderViewHolder headerViewHolder;

    private AdapterVipIndex mAdapterVipIndex;
    private AdapterVipGridCat mAdapterVipGridCat;
    private AdapterVipGridOpen mAdapterVipGridOpen;
    private ModelVipIndex mIndex;
    private List<ModelVipIndex> mDatas = new ArrayList<>();
    private List<ModelVipIndex> mDatasCat = new ArrayList<>();
    private List<ModelVipIndex> mDatasOpen = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        addHeaderView();
        //底部商品
        mAdapterVipIndex = new AdapterVipIndex(this, R.layout.item_vip_index_list, mDatas);
        mListview.setAdapter(mAdapterVipIndex);

    }

    private void addHeaderView() {
        //添加list头布局
        headerView = LayoutInflater.from(this).inflate(R.layout.header_vip_index, null);
        mListview.addHeaderView(headerView);
        headerViewHolder = new HeaderViewHolder(headerView);

        headerViewHolder.mTvJilu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        //vip分类
        mAdapterVipGridCat = new AdapterVipGridCat(this, R.layout.item_vip_index_grid, mDatasCat);
        headerViewHolder.mGridVip.setAdapter(mAdapterVipGridCat);

        //vip开通方式
        mAdapterVipGridOpen = new AdapterVipGridOpen(this, R.layout.item_vip_index_open_style, mDatasOpen);
        headerViewHolder.mGridOpen.setAdapter(mAdapterVipGridOpen);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.VIP_INDEX, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelVipIndex modelVipIndex = (ModelVipIndex) JsonUtil.getInstance().parseJsonFromResponse(response, ModelVipIndex.class);
                    if (modelVipIndex != null) {
                        mIndex = modelVipIndex;
                        inflateContent();
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                mRefreshLayout.finishRefresh();
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void inflateContent() {
        //顶部信息
        headerViewHolder.mTvName.setText(mIndex.getNickname());
        FrescoUtils.getInstance().setImageUri(headerViewHolder.mSdvHead, StringUtils.getImgUrl(mIndex.getAvatars()));
        //判断是否是VIP
        if ("1".equals(mIndex.getIs_vip())) {//会员
            headerViewHolder.mTvOpenType.setText("已开通");
            headerViewHolder.mTvOpenNum.setText("距离权益到期还有" + mIndex.getVip_endtime() + "天");
            headerViewHolder.mTvBuy.setText("立即续费");
            headerViewHolder.mTvKesheng.setText("累计已省");
            headerViewHolder.mTvMoney.setText(mIndex.getSave_already());

            //开通方式不显示
            headerViewHolder.mLyVipList.setVisibility(View.GONE);
            headerViewHolder.mViewLine.setVisibility(View.GONE);

        } else {
            headerViewHolder.mTvOpenType.setText("未开通");
            headerViewHolder.mTvOpenNum.setText("您还不是VIP会员");
            headerViewHolder.mTvBuy.setText("立即开通");
            headerViewHolder.mTvKesheng.setText("开通预计可省");
            headerViewHolder.mTvMoney.setText(mIndex.getSave_plan());

            //开通方式显示
            headerViewHolder.mLyVipList.setVisibility(View.VISIBLE);
            headerViewHolder.mViewLine.setVisibility(View.VISIBLE);
        }

        //VIP特权分类
        if (mIndex.getVip_right() != null && mIndex.getVip_right().size() > 0) {
            mDatasCat.clear();
            mDatasCat.addAll(mIndex.getVip_right());
            mAdapterVipGridCat.notifyDataSetChanged();
        }
        //Vip开通
        if (mIndex.getVip_list() != null && mIndex.getVip_list().size() > 0) {
            mDatasOpen.clear();
            mDatasOpen.addAll(mIndex.getVip_list());
            mDatasOpen.get(0).setSelect(true);//默认第一条选中
            mAdapterVipGridOpen.notifyDataSetChanged();
        }
        //专属好券
        if (mIndex.getVip_coupon() != null && mIndex.getVip_coupon().size() > 0) {
            headerViewHolder.mLlCoupon.removeAllViews();
            for (int i = 0; i < mIndex.getVip_coupon().size(); i++) {
                View view_coupon = LayoutInflater.from(this).inflate(R.layout.item_vip_index_coupon, null);
                TextView tv_tag = view_coupon.findViewById(R.id.tv_tag);
                TextView tv_coupon_price = view_coupon.findViewById(R.id.tv_coupon_price);
                TextView tv_total_price = view_coupon.findViewById(R.id.tv_total_price);
                TextView tv_coupon_type = view_coupon.findViewById(R.id.tv_coupon_type);

                tv_coupon_price.setText(mIndex.getVip_coupon().get(i).getAmount());
                tv_total_price.setText("满" + mIndex.getVip_coupon().get(i).getFulfil_amount() + "可用");
                tv_coupon_type.setText(mIndex.getVip_coupon().get(i).getCategory_name());
                headerViewHolder.mLlCoupon.addView(view_coupon);
            }
        }
        //推荐店铺
        if (mIndex.getVip_shop() != null && mIndex.getVip_shop().size() > 0) {
            mDatas.clear();
            mDatas.addAll(mIndex.getVip_shop());
            mAdapterVipIndex.notifyDataSetChanged();
        }

    }

    @Override
    protected void initListener() {
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
        //立即开通/立即续费
        headerViewHolder.mTvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, VipBuyActivity.class);
                intent.putExtra("vip", mIndex);
                startActivity(intent);
            }
        });
        //省钱
        headerViewHolder.mLyShengqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, SaveMoneyListActivity.class);
                startActivity(intent);
            }
        });
        //开通记录
        headerViewHolder.mTvJilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, VipRecordActivity.class);
                startActivity(intent);
            }
        });
        //vip选中开通方式
        headerViewHolder.mGridOpen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mDatasOpen.size(); i++) {
                    if (position == i) {
                        mDatasOpen.get(i).setSelect(true);
                    } else {
                        mDatasOpen.get(i).setSelect(false);
                    }
                }
                mAdapterVipGridOpen.notifyDataSetChanged();
            }
        });
        //立即购买
        headerViewHolder.mTvBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, VipBuyActivity.class);
                intent.putExtra("vip", mIndex);
                startActivity(intent);
            }
        });
        //用户协议
        headerViewHolder.mTvXieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, WebViewDefaultActivity.class);
                intent.putExtra("web_type", 0);
                intent.putExtra("jump_type", ConstantWebView.CONSTANT_VIP_FUWU_XIEYI);
                startActivity(intent);
            }
        });
        //VIP特权查看更多
        headerViewHolder.mTvMoreVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, VipRightActivity.class);
                startActivity(intent);
            }
        });
        //优惠券查看更多
        headerViewHolder.mTvMoreVipCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipIndexActivity.this, VipCouponListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_vip_index;
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
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(EventModelVip info) {
        initData();
    }

    class HeaderViewHolder {

        @BindView(R.id.sdv_head)
        SimpleDraweeView mSdvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_open_type)
        TextView mTvOpenType;
        @BindView(R.id.tv_open_num)
        TextView mTvOpenNum;
        @BindView(R.id.tv_buy)
        TextView mTvBuy;
        @BindView(R.id.tv_kesheng)
        TextView mTvKesheng;
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_jilu)
        TextView mTvJilu;
        @BindView(R.id.tv_more_vip)
        TextView mTvMoreVip;
        @BindView(R.id.iv_more_vip)
        ImageView mIvMoreVip;
        @BindView(R.id.grid_vip)
        MyGridview mGridVip;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.grid_open)
        MyGridview mGridOpen;
        @BindView(R.id.tv_btn_buy)
        TextView mTvBtnBuy;
        @BindView(R.id.tv_xieyi)
        TextView mTvXieyi;
        @BindView(R.id.ll_coupon)
        LinearLayout mLlCoupon;
        @BindView(R.id.ly_shengqian)
        LinearLayout mLyShengqian;
        @BindView(R.id.tv_more_vip_coupon)
        TextView mTvMoreVipCoupon;
        @BindView(R.id.ly_vip_list)
        LinearLayout mLyVipList;
        @BindView(R.id.view_line)
        View mViewLine;


        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
