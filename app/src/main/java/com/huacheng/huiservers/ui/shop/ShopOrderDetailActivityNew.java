package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.bean.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.adapter.ShopOrderDetailAdapter;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：商城订单详情界面
 * 时间：2020/1/8 17:58
 * created by DFF
 */
public class ShopOrderDetailActivityNew extends BaseActivity implements ShopOrderDetailAdapter.OnClickShopDetailListener {
    @BindView(R.id.iv_status)
    ImageView mIvStatus;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_status_info)
    TextView mTvStatusInfo;
    @BindView(R.id.tv_order_name)
    TextView mTvOrderName;
    @BindView(R.id.tv_order_phone)
    TextView mTvOrderPhone;
    @BindView(R.id.tv_order_address)
    TextView mTvOrderAddress;
    @BindView(R.id.listview)
    MyListView mListview;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_xiadan_time)
    TextView mTvXiadanTime;
    @BindView(R.id.tv_peisong_style)
    TextView mTvPeisongStyle;
    @BindView(R.id.tv_ziti_address)
    TextView mTvZitiAddress;
    @BindView(R.id.ly_ziti)
    LinearLayout mLyZiti;
    @BindView(R.id.tv_liuyan)
    TextView mTvLiuyan;
    @BindView(R.id.ly_liuyan)
    LinearLayout mLyLiuyan;
    @BindView(R.id.tv_pay_style)
    TextView mTvPayStyle;
    @BindView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.tv_all_shop_price)
    TextView mTvAllShopPrice;
    @BindView(R.id.tv_all_yunfei)
    TextView mTvAllYunfei;
    @BindView(R.id.tv_all_coupon)
    TextView mTvAllCoupon;
    @BindView(R.id.tv_pay_price)
    TextView mTvPayPrice;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.left)
    ImageView mLeft;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.view_title_line)
    View mViewTitleLine;
    @BindView(R.id.lin_title)
    LinearLayout mLinTitle;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_tuikuan)
    TextView mTvTuikuan;
    @BindView(R.id.tv_pingjia)
    TextView mTvPingjia;
    @BindView(R.id.tv_goumai)
    TextView mTvGoumai;
    List<XorderDetailBean> mDatas = new ArrayList<>();
    private ShopOrderDetailAdapter adapter;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //状态栏
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha(0);

        for (int i = 0; i < 5; i++) {
            mDatas.add(new XorderDetailBean());
        }
        adapter = new ShopOrderDetailAdapter(this, R.layout.item_shop_order_detail, mDatas, 0,this);
        mListview.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //设置其透明度
                float alpha = 0;
                //向上滑动的距离
                if (scrollY > 0) {
                    alpha = 1;//滑上去就一直显示
                } else {
                    alpha = 0;
                }
                mStatusBar.setAlpha(alpha);
                if (alpha == 0) {
                    mViewTitleLine.setVisibility(View.GONE);
                    mLeft.setBackgroundResource(R.mipmap.ic_arrow_left_white);
                    mLinTitle.setBackground(null);
                    mTitleName.setText("");

                } else {
                    mViewTitleLine.setVisibility(View.VISIBLE);
                    mLeft.setBackgroundResource(R.mipmap.ic_arrow_left_black);
                    mLinTitle.setBackgroundResource(R.color.white);
                    mTitleName.setText("商品详情");

                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_shop_order_detail;
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
        isStatusBar = true;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lin_left, R.id.tv_delete, R.id.tv_tuikuan, R.id.tv_pingjia, R.id.tv_goumai})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.tv_delete:

                break;
            case R.id.tv_tuikuan:
                intent = new Intent(this, ShopOrderNoPingjiaActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("type_name", "退款商品列表");
                startActivity(intent);
                break;
            case R.id.tv_pingjia:
                intent = new Intent(this, ShopOrderNoPingjiaActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("type_name", "评价商品列表");
                startActivity(intent);
                break;
            case R.id.tv_goumai://是再次购买也是确认收货

                break;
        }
    }

    /**
     * 加入购物车
     *
     * @param type
     * @param item
     */
    @Override
    public void onClickButton(int type, XorderDetailBean item) {

    }
}
