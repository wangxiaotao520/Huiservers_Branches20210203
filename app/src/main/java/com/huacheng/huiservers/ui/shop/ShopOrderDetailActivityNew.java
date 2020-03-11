package com.huacheng.huiservers.ui.shop;

import android.app.Dialog;
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

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.presenter.ShopOrderCaoZuoPrester;
import com.huacheng.huiservers.ui.shop.presenter.ShopOrderDetetePrester;
import com.huacheng.huiservers.ui.shop.adapter.ShopOrderDetailAdapter;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.timer.CountDownTimer;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：商城订单详情界面
 * 时间：2020/1/8 17:58
 * created by DFF
 */
public class ShopOrderDetailActivityNew extends BaseActivity implements ShopOrderDetailAdapter.OnClickShopDetailListener,
        ShopOrderDetetePrester.ShopOrderListener, ShopOrderCaoZuoPrester.ShopOrderListener {
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
    @BindView(R.id.ly_daifukuan_bg)
    LinearLayout mLyDaifukuanBg;
    @BindView(R.id.ly_user_address)
    LinearLayout mLyUserAddress;
    @BindView(R.id.tv_ziti_tag)
    TextView mTvZitiTag;
    @BindView(R.id.ly_bottom)
    LinearLayout mLyBottom;
    @BindView(R.id.ly_other_title)
    LinearLayout mLyOtherTitle;
    @BindView(R.id.ly_yizhifu_price)
    LinearLayout mLyYizhifuPrice;
    @BindView(R.id.left1)
    ImageView mLeft1;
    @BindView(R.id.status_bar1)
    View mStatusBar1;
    @BindView(R.id.view_title_line1)
    View mViewTitleLine1;
    @BindView(R.id.ly_bottom_price)
    LinearLayout mLyBottomPrice;
    private ShopOrderDetailAdapter adapter;
    String order_id;
    String p_m_id;
    String list_status;
    XorderDetailBean mXorderDetailBean;
    List<XorderDetailBean> mAllDatas = new ArrayList<>();
    private ShopOrderDetetePrester mPresenter;
    private ShopOrderCaoZuoPrester mCaozuoPresenter;
    private CountDownTimer countDownTimer;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mPresenter = new ShopOrderDetetePrester(this, this);
        mCaozuoPresenter = new ShopOrderCaoZuoPrester(this, this);
        //状态栏
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha(0);
        mStatusBar1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar1.setAlpha(0);

        adapter = new ShopOrderDetailAdapter(this, R.layout.item_shop_order_detail, mAllDatas, this, list_status);
        mListview.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("id", order_id);
        if (!"1".equals(list_status)) {
            params.put("p_m_id", p_m_id);
        }
        params.put("status", list_status);
        MyOkHttp.get().get(ApiHttpClient.GET_SHOP_ORDER_DETAILS, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    XorderDetailBean xorderDetailBean = (XorderDetailBean) JsonUtil.getInstance().parseJsonFromResponse(response, XorderDetailBean.class);
                    //List<XorderDetailBean> list = xorderDetailBean.getList();
                    mXorderDetailBean = xorderDetailBean;
                    //除商品列表外的其他数据
                    if (mXorderDetailBean != null) {
                        //商品列表数据显示
                        if (mXorderDetailBean.getMer_list() != null && mXorderDetailBean.getMer_list().size() > 0) {
                            mAllDatas.clear();
                            mAllDatas.addAll(mXorderDetailBean.getMer_list());
                            adapter.notifyDataSetChanged();
                            setInfo();
                        }
                    }

                } else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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

    private void setInfo() {
        mLyBottom.setVisibility(View.VISIBLE);
        mLyUserAddress.setVisibility(View.VISIBLE);
        mTvOrderName.setText(mXorderDetailBean.getContact());
        mTvOrderPhone.setText(mXorderDetailBean.getMobile());
        mTvOrderAddress.setText(mXorderDetailBean.getAddress());
        mTvOrderNum.setText("订单编号：" + mXorderDetailBean.getOrder_number());
        mTvXiadanTime.setText("下单时间：" + StringUtils.getDateToString(mXorderDetailBean.getAddtime(), "1"));
        //价格显示
        mLyBottomPrice.setVisibility(View.VISIBLE);//价格布局显示
        mTvAllShopPrice.setText("¥ " + mXorderDetailBean.getPro_amount());
        mTvAllYunfei.setText("＋ ¥ " + mXorderDetailBean.getSend_amount());
        mTvAllCoupon.setText("－ ¥ " + mXorderDetailBean.getCoupon_amount());

        if (!NullUtil.isStringEmpty(mXorderDetailBean.getDescription())) {
            mLyLiuyan.setVisibility(View.VISIBLE);
            mTvLiuyan.setText(mXorderDetailBean.getDescription());
        } else {
            mLyLiuyan.setVisibility(View.GONE);
        }
        if ("1".equals(list_status)) {//待付款
            mLyYizhifuPrice.setVisibility(View.GONE);
            mLyOtherTitle.setVisibility(View.GONE);
            mLyDaifukuanBg.setVisibility(View.VISIBLE);
            mLyDaifukuanBg.setBackgroundResource(R.mipmap.bg_shop_order_detail);
            mIvStatus.setBackgroundResource(R.mipmap.ic_shop_order_daifukuan);
            mTvStatusInfo.setVisibility(View.VISIBLE);
            //剩余下单时间
            setTime();

            mTvStatus.setText("等待付款");
            //支付以及配送信息底部不显示
            mTvPeisongStyle.setVisibility(View.GONE);
            mLyZiti.setVisibility(View.GONE);
            mTvPayStyle.setVisibility(View.GONE);
            mTvPayTime.setVisibility(View.GONE);
            //底部按钮显示删除以及支付
            mTvTuikuan.setVisibility(View.GONE);
            mTvPingjia.setVisibility(View.GONE);
            mTvDelete.setVisibility(View.VISIBLE);
            mTvGoumai.setVisibility(View.VISIBLE);
            mTvGoumai.setText("去支付");

        } else if ("2".equals(list_status)) {//待收货
            //顶部信息不显示
            mLyOtherTitle.setVisibility(View.VISIBLE);
            mLyDaifukuanBg.setVisibility(View.GONE);
            //底部按钮显示删除以及支付
            mTvTuikuan.setVisibility(View.VISIBLE);
            mTvPingjia.setVisibility(View.GONE);
            mTvDelete.setVisibility(View.GONE);
            //支付以及配送信息显示
            isPay();
            //判断商品是否全部是待发货,确认收货按钮显示不显示
            if (isEqual(mXorderDetailBean.getMer_list().get(0).getImg(), "2")) {
                mTvGoumai.setVisibility(View.GONE);
            } else {
                mTvGoumai.setVisibility(View.VISIBLE);
                mTvGoumai.setText("确认收货");
            }
        } else if ("3".equals(list_status)) {//已完成
            //顶部信息不显示
            mLyOtherTitle.setVisibility(View.VISIBLE);
            mLyDaifukuanBg.setVisibility(View.GONE);
            //底部按钮显示退款以及评价
            mTvTuikuan.setVisibility(View.VISIBLE);
            mTvDelete.setVisibility(View.GONE);
            mTvGoumai.setVisibility(View.GONE);
            //支付以及配送信息显示
            isPay();
            //判断商品是否全部是已完成,评价按钮显示不显示
            if (isEqual(mXorderDetailBean.getMer_list().get(0).getImg(), "7")) {
                mTvPingjia.setVisibility(View.GONE);
            } else {
                mTvPingjia.setVisibility(View.VISIBLE);
            }
        } else if ("4".equals(list_status)) {//退款/售后
            //顶部信息不显示
            mLyOtherTitle.setVisibility(View.VISIBLE);
            mLyDaifukuanBg.setVisibility(View.GONE);
            //底部按钮全部不显示
            mLyBottom.setVisibility(View.GONE);
            //支付以及配送信息显示
            isPay();
        }
    }

    /**
     * 获取时间
     */
    private void setTime() {
        //下单时间
        long pay_time = Long.parseLong(mXorderDetailBean.getAddtime()) * 1000;
        //获取当前时间
        long now_time = System.currentTimeMillis();
        //下单时间加上30分钟 减去当前的时间就是剩余的时间
        long three_time = 30 * 60 * 1000;
        long time = pay_time + three_time - now_time;

        //  CountDownTimer countDownTimer = countDownCounters.get(tv_hour.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }
        // 数据
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {

                long time = millisUntilFinished / (1000 * 60);

                mTvStatusInfo.setText("需支付：¥ " + mXorderDetailBean.getAmount() + "    剩余：" + time + "分钟");

            }

            public void onFinish(String redpackage_id) {
                mTvStatusInfo.setText("已过期");
                //结束了该轮倒计时
//              holder.statusTv.setText(data.name + ":结束");
            }
        }.start();
        //将此 countDownTimer 放入list.
        //   countDownCounters.put(tv_hour.hashCode(), countDownTimer);
    }

    /**
     * 销毁倒计时
     */
    private void cannelAllTimers() {
        if (countDownTimer == null) {
            return;
        } else {
            countDownTimer.cancel();
        }

    }

    /**
     * 判断状态是否是2 待发货
     * 判断状态是否是7 待发货
     *
     * @param list
     * @return
     */
    public boolean isEqual(List<BannerBean> list, String is_status) {
        List<String> listid = new ArrayList<String>();
        for (BannerBean goods : list) {
            String spid = goods.getStatus();
            listid.add(spid);
        }
        if (listid != null && listid.size() > 0) {
            String firstspid = is_status;
            for (String spid : listid) {
                if (!spid.equals(firstspid)) {
                    //值一样 才返回true,其余情况false,只要有一个不是2 ,返回false
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 除待付款以外的状态下的支付信息及配送方式信息
     */
    private void isPay() {
        // 已支付的金额
        mLyYizhifuPrice.setVisibility(View.VISIBLE);
        mTvPayPrice.setText("¥ " + mXorderDetailBean.getAmount());
        //配送方式显示
        mTvPeisongStyle.setVisibility(View.VISIBLE);
        mLyZiti.setVisibility(View.VISIBLE);
        mTvPayStyle.setVisibility(View.VISIBLE);
        mTvPayTime.setVisibility(View.VISIBLE);
        // 因为不是待付款，肯定是一个商户所以直接取第一条
        if (mAllDatas != null && mAllDatas.size() > 0) {
            if ("1".equals(mAllDatas.get(0).getSend_type())) {
                mTvPeisongStyle.setText("配送方式：送货上门 ");
                mTvZitiTag.setText("送货地址：");
                mTvZitiAddress.setText(mXorderDetailBean.getAddress());
            } else if ("2".equals(mAllDatas.get(0).getSend_type())) {
                mTvPeisongStyle.setText("配送方式：自提");
                mTvZitiTag.setText("自提地址：");
                mTvZitiAddress.setText(mAllDatas.get(0).getP_m_address());
            } else if ("3".equals(mAllDatas.get(0).getSend_type())) {
                mTvPeisongStyle.setText("配送方式：快递物流");
                mTvZitiTag.setText("物流单号：");
                mTvZitiAddress.setText(mAllDatas.get(0).getExpress());
            }
        }
        String pay = "支付方式：";
        mTvPayStyle.setText(pay + mXorderDetailBean.getPay_type());//支付方式
        mTvPayTime.setText("支付日期：" + StringUtils.getDateToString(mXorderDetailBean.getPay_time(), "1"));//支付时间

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ("1".equals(list_status)) {
                    mLyDaifukuanBg.setVisibility(View.VISIBLE);
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
                        mTitleName.setText("订单详情");

                    }
                }
            }
        });
        //退款点击
        mTvTuikuan.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", order_id);
                params.put("type", "1");
                params.put("p_m_id", p_m_id);
                params.put("status", list_status);
                mCaozuoPresenter.getCaoZuoShopInfo(params, "1");
            }
        });
        //评价
        mTvPingjia.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", order_id);
                params.put("type", "3");
                params.put("p_m_id", p_m_id);
                params.put("status", list_status);
                mCaozuoPresenter.getCaoZuoShopInfo(params, "3");
            }
        });

        //是支付也是收货
        mTvGoumai.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if ("1".equals(list_status)) {
                    Intent intent = new Intent(ShopOrderDetailActivityNew.this, UnifyPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("o_id", mXorderDetailBean.getId());
                    bundle.putString("price", mXorderDetailBean.getAmount() + "");
                    bundle.putString("type", "shop");
                    bundle.putString("order_type", "gw");
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else if ("2".equals(list_status)) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", order_id);
                    params.put("type", "2");
                    params.put("p_m_id", p_m_id);
                    params.put("status", list_status);
                    mCaozuoPresenter.getCaoZuoShopInfo(params, "2");
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
        order_id = this.getIntent().getStringExtra("id");
        p_m_id = this.getIntent().getStringExtra("p_m_id");
        list_status = this.getIntent().getStringExtra("status");
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
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        this.cannelAllTimers();
    }

    @OnClick({R.id.lin_left, R.id.tv_delete, R.id.left1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.left1:
                finish();
                break;
            case R.id.tv_delete:
                //删除接口
                new CommomDialog(this, R.style.my_dialog_DimEnabled, "确认要删除吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            mPresenter.getOrderDetete(order_id);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();//.setTitle("提示")

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
    public void onClickButton(int type, BannerBean item) {

    }

    /**
     * 删除订单
     *
     * @param status
     * @param msg
     */
    @Override
    public void onOrderDelete(int status, String id, String msg) {
        if (status == 1) {
            XorderDetailBean XorderDetail = new XorderDetailBean();
            XorderDetail.setId(id);
            XorderDetail.setBack_type(1);
            EventBus.getDefault().post(XorderDetail);
            finish();
        } else {
            SmartToast.showInfo(msg);
        }

    }

    /**
     * 操作界面数据 判断跳转列表还是提交界面
     *
     * @param status
     * @param msg
     * @param data
     */

    @Override
    public void onGetCaoZuoInfo(int status, String msg, final List<BannerBean> data, String type) {
        if (status == 1) {
            if (data != null && data.size() > 0) {
                if (data.size() == 1) {
                    if ("1".equals(type)) {//退款
                        Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
                        intent.putExtra("is_type", 1);
                        intent.putExtra("data_info", data.get(0));
                        startActivity(intent);
                    } else if ("2".equals(type)) {//收货
                        new CommomDialog(this, R.style.my_dialog_DimEnabled, "是否确认收货？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    mCaozuoPresenter.getShouHuo(data.get(0).getId());
                                    dialog.dismiss();
                                }

                            }
                        }).show();//.setTitle("提示")

                    } else if ("3".equals(type)) {//评价
                        Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
                        intent.putExtra("is_type", 2);
                        intent.putExtra("data_info", data.get(0));
                        startActivity(intent);
                    }

                } else {
                    //多条数据跳转
                    if ("1".equals(type)) {//退款
                        Intent intent = new Intent(this, ShopOrderNoPingjiaActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("type_name", "退款");
                        intent.putExtra("id", order_id);
                        intent.putExtra("p_m_id", p_m_id);
                        intent.putExtra("status", list_status);
                        startActivity(intent);
                    } else if ("2".equals(type)) {//收货
                        Intent intent = new Intent(this, ShopOrderNoPingjiaActivity.class);
                        intent.putExtra("type", 2);
                        intent.putExtra("type_name", "确认收货");
                        intent.putExtra("id", order_id);
                        intent.putExtra("p_m_id", p_m_id);
                        intent.putExtra("status", list_status);
                        startActivity(intent);
                    } else if ("3".equals(type)) {//评价
                        Intent intent = new Intent(this, ShopOrderNoPingjiaActivity.class);
                        intent.putExtra("type", 3);
                        intent.putExtra("type_name", "评价");
                        intent.putExtra("id", order_id);
                        intent.putExtra("p_m_id", p_m_id);
                        intent.putExtra("status", list_status);
                        startActivity(intent);
                    }
                }
            }
        } else {
            SmartToast.showInfo(msg);
        }
    }

    /**
     * 确认收货
     *
     * @param status
     * @param msg
     */
    @Override
    public void onGetShouHuo(int status, String msg, String id) {
        hideDialog(smallDialog);
        if (status == 1) {
            XorderDetailBean XorderDetail = new XorderDetailBean();
            XorderDetail.setId(id);
            XorderDetail.setBack_type(5);
            XorderDetail.setShouhuo_type(1);
            EventBus.getDefault().post(XorderDetail);
            finish();
        } else {
            SmartToast.showInfo(msg);
        }

    }

    /**
     * evens回调退款 评价,收货
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(XorderDetailBean info) {
        if (info != null) {
            if (info.getBack_type() == 2) {//评价
                //评价不需要判断 直接刷新数据 然后返回列表
              /*  if (info.getPingjia_type() == 1) {//直接返回列表
                    finish();
                } else {//刷新详情界面
                    requestData();
                }*/
                requestData();
            } else if (info.getBack_type() == 3) {//退款
                if (info.getTuikuan_type() == 1) {//直接返回列表
                    finish();
                } else {//刷新详情界面
                    requestData();
                }

            } else if (info.getBack_type() == 5) {//收货
                if (info.getShouhuo_type() == 1) {//直接返回列表
                    finish();
                } else {//刷新详情界面
                    requestData();
                }
            } else if (info.getBack_type() == 4) {//收货
                finish();
            }
        }
    }

}
