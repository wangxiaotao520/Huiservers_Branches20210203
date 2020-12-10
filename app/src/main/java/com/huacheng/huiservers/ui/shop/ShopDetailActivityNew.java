package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.AddShopDialog;
import com.huacheng.huiservers.dialog.ChooseCouponDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.jpush.MyReceiver;
import com.huacheng.huiservers.model.ModelCouponNew;
import com.huacheng.huiservers.model.ModelEventShopCart;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.presenter.CollectPresenter;
import com.huacheng.huiservers.ui.center.coupon.CouponListAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.ui.shop.bean.ShopMainBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.statusbar.OSUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.huiservers.view.MyImageSpan;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.huiservers.view.ScrollChangedScrollView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.huacheng.libraryservice.utils.timer.CountDownTimer;
import com.microquation.linkedme.android.log.LMErrorCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 商品详情界面
 */
//second为mLink的key
public class ShopDetailActivityNew extends BaseActivity implements OnClickListener,CollectPresenter.CollectListener {

    private ScrollChangedScrollView sv_bodyContainer;
    LinearLayout ll_shop_tuijian;

    int istag;
    /**
     * 是否是ScrollView主动动作 false:是ScrollView主动动作 true:是TabLayout 主动动作
     */
    private boolean tagFlag = false;
    /**
     * 用于切换内容模块，相应的改变导航标签，表示当一个所处的位置
     */
    private int lastTagIndex = 0;
    /**
     * 用于在同一个内容模块内滑动，锁定导航标签，防止重复刷新标签 true: 锁定 false ; 没有锁定
     */
    private boolean content2NavigateFlagInnerLock = false;

    // /
    private LinearLayout lin_left, lin_add_ss, lin_img, lin_bottom, lin_title, lin_goumai, lin_yixuanze, lin_XS_bottom, lin_goodsTag;
    private RelativeLayout rel_cancel;
    private TextView title_name, txt_name, tv_tag_kangyang, txt_content, txt_price, txt_num, tag_name, txt_tag1, txt_tag2, right, txt_tag3, txt_tag4, txt_shop_num,
            txt_paisong, txt_yuan_price, txt_tuijian, tag_guige, tv_XS_type, goumaiTx;
    private GridView grid_view;
    private LinearLayout ly_gouwuche;
    private String shop_id, login_type, url;
    private ImageView img_title, iv_top, iv_img, img_1, img_2, img_3, img_4;
    List<ShopMainBean> beans = new ArrayList<ShopMainBean>();
    ShopProtocol protocol = new ShopProtocol();
    ShopDetailBean detailBean;
    List<ShopDetailBean> beanTag = new ArrayList<ShopDetailBean>();
    ShopDetailBean cartnum = new ShopDetailBean();
    SharedPreferences preferencesLogin;
    SharePrefrenceUtil prefrenceUtil;
    private String URL, txt_time_type;
    int height;
    private View contentView, view_title_line;
    private int scrollY = 0;// 标记上次滑动位置
    private String start, end;
    private Date data_start, data_now, data_end;
    private boolean isRun = true;

    LinearLayout lin_downcount, ly_store;
    TextView tv_downcount_day, tv_downcount_hour, tv_downcount_minute, tv_downcount_second, tv_time_tag, tv_time_type;


    RelativeLayout rel_shop_limit_bg;
    private SparseArray<CountDownTimer> countDownCounters;

    private LinearLayout ly_share;//分享按钮
    private String share_url;
    private String share_title;
    private String share_desc;
    private String share_icon;
    private SimpleDraweeView iv_store_head;
    private TextView tv_store_name, tv_store_address;
    long mDay, mHour, mMin, mSecond, mTotalHour;
    private View mStatusBar;
    private TextView tv_pingjia_num;
    private LinearLayout ly_onclck_pingjia;
    private LinearLayout ly_pingjia;
    private RelativeLayout title_rel;
    private ImageView left;
    private ImageView iv_share;
    private SimpleDraweeView sdv_user_head;
    private TextView tv_pingjia_name;
    private XLHRatingBar ratingBar;
    private TextView tv_pingjia_time;
    private TextView tv_pingjia_content;
    private TextView tv_pingjia_guige;
    private MyGridview gridView;
    private LinearLayout ly_pingjia_null;
    private TextView tv_seckill_num;
    private HorizontalScrollView hsv_view;
    private LinearLayout scroll_view;
    private RelativeLayout rel_coupon; //优惠券
    private TextView tv_coupon_tag1;    //优惠券tag1
    private TextView tv_coupon_tag2;//优惠券tag2
    private ChooseCouponDialog chooseCouponDialog; //选择优惠券对话框
    private LinearLayout ly_guanzhu;
    private TextView tv_guanzhu;
    private ImageView iv_guanzhu;

    private CollectPresenter mPresenter;

    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isStatusBar = true;
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {
        type = getIntent().getIntExtra("type", 2);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();

        title_name.setFocusable(true);
        title_name.setFocusableInTouchMode(true);
        title_name.requestFocus();
        getLinshi();
        if (!login_type.equals("")) {// 登陆之后获取数量
            getCartNum();
        }
        //清除角标（华为）
        if (OSUtils.getSystemBrand() == OSUtils.SYSTEM_HUAWEI) {
            JPushInterface.clearAllNotifications(this);
            MyReceiver.setBadgeOfHuaWei(this, 0);
        }
        // 暂时先隐藏轮播 //
        // mAdView.startImageCycle();
    }


    @Override
    protected void initView() {
        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 0);
        // 获取登陆值来判断是否登陆
        getLinshi();
        prefrenceUtil = new SharePrefrenceUtil(this);
        mPresenter=new CollectPresenter(this,this);
        // 初始化ids
        if (!TextUtils.isEmpty(this.getIntent().getExtras().getString("shop_id"))) {
            shop_id = this.getIntent().getExtras().getString("shop_id");
        } else if (!TextUtils.isEmpty(this.getIntent().getExtras().getString("url"))) {
            url = this.getIntent().getExtras().getString("url");
            shop_id = url.substring(url.lastIndexOf("/") + 1, url.length());
        } else {
            url = this.getIntent().getExtras().getString("params_url");
            shop_id = url.substring(url.lastIndexOf("/") + 1, url.length());
            Log.e("URL", url);
        }

        // 标题栏
        // setTitleLayout(findViewById(R.id.lin_title));
        lin_title = findViewById(R.id.lin_title);
        title_rel = findViewById(R.id.title_rel);
        lin_left = findViewById(R.id.lin_left);
        left = findViewById(R.id.left);
        lin_left.setOnClickListener(this);
        title_name = findViewById(R.id.title_name);
        sv_bodyContainer = findViewById(R.id.anchor_bodyContainer);
        //ll_shop_tuijian = (LinearLayout) findViewById(R.id.ll_shop_tuijian);
        view_title_line = findViewById(R.id.view_title_line);
        //关注
        ly_guanzhu = findViewById(R.id.ly_guanzhu);
        iv_guanzhu = findViewById(R.id.iv_guanzhu);
        tv_guanzhu = findViewById(R.id.tv_guanzhu);
        // 为您推荐
        grid_view = findViewById(R.id.grid_view);
        iv_top = findViewById(R.id.iv_top);
        iv_img = findViewById(R.id.iv_img);
        iv_top.setOnClickListener(this);
        //店铺信息
        ly_store = findViewById(R.id.ly_store);
        iv_store_head = findViewById(R.id.iv_store_head);
        tv_store_name = findViewById(R.id.tv_store_name);
        tv_store_address = findViewById(R.id.tv_store_address);
        // 商品详情id
        img_title = findViewById(R.id.img_title);
        // 底部按钮栏
        lin_bottom = findViewById(R.id.lin_bottom);//底部栏
        lin_XS_bottom = findViewById(R.id.lin_XS_bottom);//底部栏
        tv_XS_type = findViewById(R.id.tv_XS_type);
        ly_gouwuche = findViewById(R.id.ly_gouwuche);// 购物车
        lin_add_ss = findViewById(R.id.lin_add_ss);// 加入购物车
        lin_goumai = findViewById(R.id.lin_goumai);//立即够买
        goumaiTx = findViewById(R.id.goumai);//立即够买
        rel_cancel = findViewById(R.id.rel_cancel);// 选择商品类型
        //评价
        ly_onclck_pingjia = findViewById(R.id.ly_onclck_pingjia);
        ly_pingjia = findViewById(R.id.ly_pingjia);// 评价
        tv_pingjia_num = findViewById(R.id.tv_pingjia_num);
        sdv_user_head = findViewById(R.id.sdv_user_head);
        tv_pingjia_name = findViewById(R.id.tv_pingjia_name);
        ratingBar = findViewById(R.id.ratingBar);
        tv_pingjia_time = findViewById(R.id.tv_pingjia_time);
        tv_pingjia_content = findViewById(R.id.tv_pingjia_content);
        tv_pingjia_guige = findViewById(R.id.tv_pingjia_guige);
        gridView = findViewById(R.id.gridView);
        hsv_view = findViewById(R.id.hsv_view);
        scroll_view = findViewById(R.id.scroll_view);
        ly_pingjia_null = findViewById(R.id.ly_pingjia_null);

        txt_name = findViewById(R.id.txt_name);
        tv_tag_kangyang = findViewById(R.id.tv_tag_kangyang);
        tv_tag_kangyang.setVisibility(View.GONE);
        txt_content = findViewById(R.id.txt_content);
        txt_price = findViewById(R.id.txt_price);
        txt_yuan_price = findViewById(R.id.txt_yuan_price);
        txt_yuan_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
        //商品规格
        lin_yixuanze = findViewById(R.id.lin_yixuanze);
        tag_guige = findViewById(R.id.tag_guige);
        txt_num = findViewById(R.id.txt_num);
        tag_name = findViewById(R.id.tag_name);
        lin_goodsTag = findViewById(R.id.lin_goodsTag);

        txt_paisong = findViewById(R.id.txt_paisong);
        txt_shop_num = findViewById(R.id.txt_shop_num); // 商品图片买描述 //
        //  txt_tuijian = findViewById(R.id.txt_tuijian); // 商品描述图片列表id
        lin_img = findViewById(R.id.lin_img);
        tv_time_tag = findViewById(R.id.tv_time_tag);

        rel_shop_limit_bg = findViewById(R.id.rel_shop_limit_bg);
        tv_time_type = findViewById(R.id.tv_time_type); // 商品描述图片列表id
        lin_downcount = findViewById(R.id.lin_downcount);
        tv_seckill_num = findViewById(R.id.tv_seckill_num);

        tv_downcount_day = findViewById(R.id.tv_downcount_day);
        tv_downcount_hour = findViewById(R.id.tv_downcount_hour);
        tv_downcount_minute = findViewById(R.id.tv_downcount_minute);
        tv_downcount_second = findViewById(R.id.tv_downcount_second);

        rel_coupon = findViewById(R.id.rel_coupon);
        tv_coupon_tag1 = findViewById(R.id.tv_coupon_tag1);
        tv_coupon_tag2 = findViewById(R.id.tv_coupon_tag2);


        countDownCounters = new SparseArray<>();

        rel_cancel.setOnClickListener(this);
        ly_gouwuche.setOnClickListener(this);
        lin_add_ss.setOnClickListener(this);
        lin_goumai.setOnClickListener(this);
        ly_store.setOnClickListener(this);
        installListener();
        initListeners();
        ly_share = findViewById(R.id.ly_share);
        iv_share = findViewById(R.id.iv_share);
        ly_share.setOnClickListener(this);
        ly_onclck_pingjia.setOnClickListener(this);
        rel_coupon.setOnClickListener(this);
        ly_guanzhu.setOnClickListener(this);

        if (type == 2) {
            ly_gouwuche.setVisibility(View.GONE);
            lin_add_ss.setVisibility(View.GONE);
            goumaiTx.setText("立即兑换");
        }
    }

    @Override
    protected void initData() {
        getDetail();
        if (!login_type.equals("")) {// 登陆之后获取数量
            getCartNum();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_detail_new;
    }

    private void getDetail() {// 获取商品详情
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        if (!TextUtils.isEmpty(shop_id)) {
            url = info.goods_details + "id/" + shop_id;
        }
        HttpHelper hh = new HttpHelper(url, ShopDetailActivityNew.this) {
            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                if (!NullUtil.isStringEmpty(json)) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        String msg = jsonObject.getString("msg");
                        if ("1".equals(status)) {
                            detailBean = protocol.getDetail(json);
                            tag_guige.setVisibility(View.VISIBLE);
                            ly_store.setVisibility(View.VISIBLE);
                            GlideUtils.getInstance().glideLoad(ShopDetailActivityNew.this, MyCookieStore.URL + detailBean.getTitle_img(), img_title, R.color.default_img_color);

                            if ("2".equals(detailBean.getPension_display())) {
                                tv_tag_kangyang.setVisibility(View.VISIBLE);
                                //TODO vip标签
                                if ("1".equals(detailBean.getIs_vip())){
                                    String title = detailBean.getTitle();
                                    String addSpan = "\u3000\u3000"+" "+"VIP折扣";
                                    SpannableString spannableString=new SpannableString(addSpan+" "+title);
                                    Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
                                    d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
                                    ImageSpan span = new MyImageSpan(mContext,d);
                                    spannableString.setSpan(span, (addSpan+" "+title).indexOf("VIP折扣"), (addSpan+" "+title).indexOf("VIP折扣")+"VIP折扣".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                    txt_name.setText(spannableString);
                                }else {
                                    txt_name.setText("\u3000\u3000"+" "+detailBean.getTitle());
                                }
                            } else {
                                tv_tag_kangyang.setVisibility(View.GONE);
                                if ("1".equals(detailBean.getIs_vip())){
                                    //TODO vip标签
                                    String title = detailBean.getTitle()+"";
                                    String addSpan = "VIP折扣";
                                    SpannableString spannableString=new SpannableString(addSpan+" "+title);
                                    Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
                                    d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
                                    ImageSpan span = new MyImageSpan(mContext,d);
                                    spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                    txt_name.setText(spannableString);
                                }else {
                                    txt_name.setText(detailBean.getTitle());
                                }

                            }
                            txt_content.setText(detailBean.getDescription());
                            //Todo
                            if (type == 2){
                                txt_price.setText( detailBean.getPrice() + "积分");
                            }else txt_price.setText("¥" + detailBean.getPrice());

                            //判断是否是会员
                            if ("1".equals(detailBean.getIs_vip())){
                                txt_price.setText("¥" + detailBean.getVip_price());
                            }else {
                                txt_price.setText("¥" + detailBean.getPrice());
                            }
                            //判断是否已收藏
                            if ("1".equals(detailBean.getIs_collection())){
                                tv_guanzhu.setText("已关注");
                                iv_guanzhu.setBackgroundResource(R.mipmap.ic_collect_select);
                                ly_guanzhu.setClickable(false);
                            }else {
                                tv_guanzhu.setText("关注");
                                iv_guanzhu.setBackgroundResource(R.mipmap.ic_collect_noselect);
                            }
                            txt_yuan_price.setText("¥" + detailBean.getOriginal());
                            tv_seckill_num.setText("总计：" + detailBean.getInventory() + " " + detailBean.getUnit());
                            //txt_num.setText("已剩：" + detailBean.getInventory() + " " + detailBean.getUnit());
                            txt_num.setText("已售：" + detailBean.getOrder_num() + " " + detailBean.getUnit());
                            //tag_name.setText(detailBean.getTagname());//默认选择已选择的商品规格
                            if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                                txt_paisong.setVisibility(View.VISIBLE);
                            } else {
                                txt_paisong.setVisibility(View.GONE);
                            }
                            //txt_market.setText(detailBean.getSend_shop());
                            if (detailBean.getCart_num().equals("") || detailBean.getCart_num().equals("0")) {
                                txt_shop_num.setVisibility(View.GONE);
                            } else {
                                txt_shop_num.setVisibility(View.VISIBLE);
                                // txt_shop_num.setText(detailBean.getCart_num());
                            }

                            getAddGoodsTagView();//商品标签
                            getAddview();// 动态添加商品图片描述
                            getpingjia();// 获取商品详情中的评价数据
                            //店铺信息
                            if (detailBean.getMerchant() != null) {
                                tv_store_address.setText(detailBean.getMerchant().getAddress());
                                tv_store_name.setText(detailBean.getMerchant().getMerchant_name());
                                FrescoUtils.getInstance().setImageUri(iv_store_head, ApiHttpClient.IMG_URL + detailBean.getMerchant().getLogo());
                            }
                            if (detailBean.getDiscount().equals("1")) {//限购为1  否则为0
                                rel_shop_limit_bg.setVisibility(View.VISIBLE);
                                lin_bottom.setVisibility(View.VISIBLE);//购物车
                                gettime(detailBean);
                            } else {
                                rel_shop_limit_bg.setVisibility(View.GONE);
                                lin_XS_bottom.setVisibility(View.GONE);//底部限时抢购
                                lin_bottom.setVisibility(View.VISIBLE);//购物车
                            }
                            getCouponTag();

                        } else {
                            SmartToast.showInfo(msg + "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    /**
     * 商品详情的优惠券标签
     */
    private void getCouponTag() {
        if (detailBean.getCoupon() != null && detailBean.getCoupon().size() > 0) {
            for (int i = 0; i < detailBean.getCoupon().size(); i++) {
                if (i == 0) {
                    tv_coupon_tag1.setVisibility(View.VISIBLE);
                    tv_coupon_tag1.setText(detailBean.getCoupon().get(i).getName() + "");
                } else if (i == 1) {
                    tv_coupon_tag2.setVisibility(View.VISIBLE);
                    tv_coupon_tag2.setText(detailBean.getCoupon().get(i).getName() + "");
                }
            }
        } else {
            tv_coupon_tag1.setVisibility(View.GONE);
            tv_coupon_tag2.setVisibility(View.GONE);
        }
    }

    private void initListeners() {
        ViewTreeObserver vto = img_title.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                lin_title.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = img_title.getHeight() / 3;
                //sv_bodyContainer.setScrollViewListener(GoodsDetailActivity.this);
            }
        });
    }

    private void installListener() {
        sv_bodyContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 表明当前的动作是由 ScrollView 触发和主导
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tagFlag = true;
                }
                return false;
            }
        });
        sv_bodyContainer.setScrollViewListener(new ScrollChangedScrollView.ScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                float alpha = 0;
                if (y > 0) {
                    alpha = 1;//滑上去就一直显示
                } else {
                    alpha = 0;
                }
                mStatusBar.setAlpha(alpha);
                if (alpha == 0) {
                    left.setBackgroundResource(R.mipmap.ic_shop_left_white);
                    view_title_line.setVisibility(View.GONE);
                    iv_share.setBackgroundResource(R.mipmap.ic_shop_share_white);
                    lin_title.setBackground(null);
                    title_name.setText("");

                } else {
                    left.setBackgroundResource(R.mipmap.ic_shop_left_black);
                    view_title_line.setVisibility(View.VISIBLE);
                    lin_title.setBackgroundResource(R.color.white);
                    iv_share.setBackgroundResource(R.mipmap.ic_shop_share_black);
                    title_name.setText("商品详情");

                }
            }

            @Override
            public void onScrollStop(boolean isStop) {
                doOnBorderListener();
            }

        });

    }

    private void getLinshi() {
        preferencesLogin = ShopDetailActivityNew.this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
    }

    private void gettime(final ShopDetailBean data) {//限购时间
        String distanceStart = data.getDistance_start();
        String distanceEnd = data.getDistance_end();
        String distance_str = "";
        long distance_int = 0;
        int distance_tag = 0;
        long longDistanceStart;
        long longDistanceEnd;

        if (!NullUtil.isStringEmpty(distanceStart) && !NullUtil.isStringEmpty(distanceEnd)) {
            long distance_long = Long.parseLong(distanceStart);
            if (distance_long > 0) {
                distance_str = "距离活动开始";
                distance_tag = 1;
                distance_int = Long.parseLong(distanceStart);
                lin_XS_bottom.setVisibility(View.VISIBLE);//底部限时抢购
                lin_bottom.setVisibility(View.GONE);//购物车
            } else {
                distance_str = "距离活动结束";
                distance_tag = 0;
                distance_int = Long.parseLong(distanceEnd);
                lin_XS_bottom.setVisibility(View.GONE);//底部限时抢购
                lin_bottom.setVisibility(View.VISIBLE);//购物车
            }
            txt_time_type = distance_str;
            tv_time_tag.setText(txt_time_type + "还剩");

            if (data.getDiscount().equals("1")) {
                //结束时间-开始时间
                longDistanceStart = Long.parseLong(distanceStart);
                longDistanceEnd = Long.parseLong(distanceEnd);
                long longDistanceInterval = longDistanceEnd - longDistanceStart;

                long timer = 0;
                if (data.getCurrent_times() == 0) {
                    timer = distance_int;
                    data.setCurrent_times(System.currentTimeMillis());
                } else {
                    timer = distance_int - (System.currentTimeMillis() - data.getCurrent_times());
                }
                handlerTime(timer, distance_tag, longDistanceInterval);
            }
        } else {
            lin_XS_bottom.setVisibility(View.GONE);//底部限时抢购
            lin_bottom.setVisibility(View.VISIBLE);//购物车
        }

    }


    private void handlerTime(long timeTmp, final int dicountTag, final long longDistanceEnd) {
        CountDownTimer countDownTimer = countDownCounters.get(tv_downcount_hour.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }
        // 数据
        long timer = timeTmp * 1000;//

        //long timer = data.expirationTime;
        //timer = timer - System.currentTimeMillis();
        //expirationTime 与系统时间做比较，timer 小于零，则此时倒计时已经结束。
        if (timer > 0) {
            final CountDownTimer finalCountDownTimer = countDownTimer;
            countDownTimer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    String[] times = SetTime(millisUntilFinished);
                    mDay = Integer.parseInt(times[0]);

                    //tv_XS_type.setText("距开始 " + (times[0]) + "天" + (times[1]) + "时" + (times[2]) + "分" + (times[3]) + "秒");
                    tv_XS_type.setText("活动暂未开始");
                    lin_downcount.setVisibility(View.VISIBLE);

                    tv_downcount_day.setText(fillZero(times[0]));
                    tv_downcount_hour.setText(fillZero(times[1]));
                    tv_downcount_minute.setText(fillZero(times[2]));
                    tv_downcount_second.setText(fillZero(times[3]));
                }

                public void onFinish(String redpackage_id) {
                    //结束了该轮倒计时

                    //1表示活动已开始，0表示活动已结束
                    if (dicountTag == 1) {
                        lin_bottom.setVisibility(View.VISIBLE);//购物车
                        lin_XS_bottom.setVisibility(View.GONE);//底部限时抢购
                        txt_time_type = "距结束";
                        tv_time_tag.setText("距离活动结束还剩");

                        handlerEndTime(longDistanceEnd);

                    } else {
                        lin_bottom.setVisibility(View.GONE);//购物车
                        lin_XS_bottom.setVisibility(View.VISIBLE);//底部限时抢购
                        txt_time_type = "已结束";
                        tv_XS_type.setText("活动已结束");

                        tv_downcount_day.setText("00");
                        tv_downcount_hour.setText("00");
                        tv_downcount_minute.setText("00");
                        tv_downcount_second.setText("00");
                    }

//                    holder.statusTv.setText(data.name + ":结束");
                }
            }.start();
            //将此 countDownTimer 放入list.
            countDownCounters.put(tv_downcount_hour.hashCode(), countDownTimer);
        } else {
            //结束
        }
    }

    private void handlerEndTime(long timeTmp) {
        CountDownTimer countDownTimer = countDownCounters.get(tv_downcount_hour.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }
        long timer = timeTmp * 1000;
        if (timer > 0) {
            final CountDownTimer finalCountDownTimer = countDownTimer;
            countDownTimer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {

                    lin_downcount.setVisibility(View.VISIBLE);
                    String[] times = SetTime(millisUntilFinished);
                    tv_downcount_day.setText(fillZero(times[0]));
                    tv_downcount_hour.setText(fillZero(times[1]));
                    tv_downcount_minute.setText(fillZero(times[2]));
                    tv_downcount_second.setText(fillZero(times[3]));
                }

                public void onFinish(String redpackage_id) {
                    //结束了该轮倒计时
                    lin_bottom.setVisibility(View.GONE);//购物车
                    lin_XS_bottom.setVisibility(View.VISIBLE);//底部限时抢购

                    txt_time_type = "已结束";
                    tv_time_tag.setText("已结束");
                    tv_XS_type.setText("活动已结束");

                    tv_downcount_day.setText("00");
                    tv_downcount_hour.setText("00");
                    tv_downcount_minute.setText("00");
                    tv_downcount_second.setText("00");
                }
            }.start();
            //将此 countDownTimer 放入list.
            countDownCounters.put(tv_downcount_hour.hashCode(), countDownTimer);
        } else {
            //结束
            lin_bottom.setVisibility(View.GONE);//购物车
            lin_XS_bottom.setVisibility(View.VISIBLE);//底部限时抢购

            txt_time_type = "已结束";
            tv_time_tag.setText("已结束");
            tv_XS_type.setText("活动已结束");

            tv_downcount_day.setText("00");
            tv_downcount_hour.setText("00");
            tv_downcount_minute.setText("00");
            tv_downcount_second.setText("00");
        }
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
        if (time.length() == 1)
            return "0" + time;
        else
            return time;
    }

    private void getpingjia() {
        if (detailBean.getScore() != null && detailBean.getScore().size() > 0) {
            ly_pingjia.setVisibility(View.VISIBLE);
            ly_pingjia_null.setVisibility(View.GONE);
            tv_pingjia_num.setText("商品评价(" + detailBean.getScore_count() + ")");
            FrescoUtils.getInstance().setImageUri(sdv_user_head, StringUtils.getImgUrl(detailBean.getScore().get(0).getAvatars()));
            ratingBar.setCountSelected(Integer.valueOf(detailBean.getScore().get(0).getScore()));
            tv_pingjia_name.setText(detailBean.getScore().get(0).getUsername());
            tv_pingjia_content.setText(detailBean.getScore().get(0).getDescription());
            tv_pingjia_time.setText(StringUtils.getDateToString(detailBean.getScore().get(0).getAddtime(), "2"));
            tv_pingjia_guige.setText(detailBean.getScore().get(0).getP_tag_name());
            //评价图片
            gridView.setVisibility(View.GONE);
            scroll_view.removeAllViews();
            if (detailBean.getScore().get(0).getScore_img() != null && detailBean.getScore().get(0).getScore_img().size() > 0) {
                hsv_view.setVisibility(View.VISIBLE);
                final ArrayList<String> lists = new ArrayList<>();
                for (int i = 0; i < detailBean.getScore().get(0).getScore_img().size(); i++) {
                    lists.add(MyCookieStore.URL + detailBean.getScore().get(0).getScore_img().get(i).getImg());
                }
                for (int i = 0; i < detailBean.getScore().get(0).getScore_img().size(); i++) {
                    View view = LinearLayout.inflate(ShopDetailActivityNew.this, R.layout.circle_image_item, null);
                    ImageView img = view.findViewById(R.id.imageView);
                    img.setPadding(0, 0, 10, 0);
                    GlideUtils.getInstance().glideLoad(this, MyCookieStore.URL + detailBean.getScore().get(0).getScore_img().get(i).getImg(), img, R.drawable.icon_girdview);
                    final int finalI = i;
                    img.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShopDetailActivityNew.this, PhotoViewPagerAcitivity.class);
                            intent.putExtra("img_list", lists);
                            intent.putExtra("position", finalI);
                            startActivity(intent);
                        }
                    });
                    scroll_view.addView(view);
                }
            } else {
                hsv_view.setVisibility(View.GONE);
            }
          /*  CircleItemImageAdapter mGridViewAdapter = new CircleItemImageAdapter(this, detailBean.getScore().get(0).getScore_img());
            gridView.setAdapter(mGridViewAdapter);*/
            // istag = 1;
        } else {
            ly_pingjia.setVisibility(View.GONE);
            ly_pingjia_null.setVisibility(View.VISIBLE);
            // istag = 2;
        }
    }

    protected void getAddGoodsTagView() {
        if (detailBean.getGoods_tag() != null) {
            lin_goodsTag.removeAllViews();
            lin_goodsTag.setVisibility(View.VISIBLE);
            for (int i = 0; i < detailBean.getGoods_tag().size(); i++) {

                if (!StringUtils.isEmpty(detailBean.getGoods_tag().get(i).getC_img()) && !StringUtils.isEmpty(detailBean.getGoods_tag().get(i).getC_name())) {
                    View view = LinearLayout.inflate(ShopDetailActivityNew.this, R.layout.shop_detail_goodstag_item, null);
                    TextView tag1 = view.findViewById(R.id.txt_tag1);
                    tag1.setText(detailBean.getGoods_tag().get(i).getC_name());
                    lin_goodsTag.addView(view);
                }
            }
            // detailBean.getGoods_tag().clear();
        } else {
            lin_goodsTag.setVisibility(View.GONE);
        }
    }

    protected void getAddview() {
        if (detailBean.getImgs() != null) {
            for (int i = 0; i < detailBean.getImgs().size(); i++) {
                View view = LinearLayout.inflate(ShopDetailActivityNew.this, R.layout.shop_detail_imgdesc_item, null);
                final ImageView img = (ImageView) view.findViewById(R.id.img);
                img.setImageResource(R.drawable.ic_default_rectange);
                Glide.with(getApplicationContext()).load(MyCookieStore.URL + detailBean.getImgs().get(i).getImg()).placeholder(R.drawable.ic_default_rectange).error(R.drawable.ic_default_rectange).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        int width = resource.getIntrinsicWidth();
                        int height = resource.getIntrinsicHeight();
                        final int gridWidth = DeviceUtils.getWindowWidth(ShopDetailActivityNew.this);
                        int nWidth = gridWidth;
                        int nHeight = (int) (2 * nWidth);
                        float scale = (float) height / width;
                        if (scale < 2) {
                            nHeight = (int) (scale * nWidth);
                        }
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, nHeight);
                        //layoutParams.setMargins(0, 0, 0, 15);;
                        img.setLayoutParams(layoutParams);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                        img.setImageDrawable(resource);
                    }
                });

                lin_img.addView(view);
            }
            detailBean.getImgs().clear();
        }
    }

    int isClickOne = 0;

    @Override
    public void onClick(View arg0) {
        Intent intent;
        switch (arg0.getId()) {

            case R.id.lin_goumai:///立即购买
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(ShopDetailActivityNew.this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                    preferencesLogin = ShopDetailActivityNew.this.getSharedPreferences("login", 0);
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();

                } else if (login_type.equals("1")) {
                    if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                        SmartToast.showInfo("当前时间不在派送时间范围内");
                    } else {
                        if (type == 4) {
                            getTag("4");
                        } else {
                            getTag("1");
                        }

                    }

                } else {
                    SmartToast.showInfo("当前账号不是个人账号");
                }
                break;
            case R.id.lin_left:// 返回
                finish();
                break;
            case R.id.ly_gouwuche:// 购物车列表
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(ShopDetailActivityNew.this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                    preferencesLogin = ShopDetailActivityNew.this.getSharedPreferences("login", 0);
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();
                } else if (login_type.equals("1")) {
                    intent = new Intent(this, ShopCartActivityNew.class);
                    startActivityForResult(intent, 1);
                } else {
                    SmartToast.showInfo("当前账号不是个人账号");
                }
                break;
            case R.id.lin_add_ss:// 加入购物车
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(ShopDetailActivityNew.this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                    preferencesLogin = ShopDetailActivityNew.this.getSharedPreferences("login", 0);
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();
                } else if (login_type.equals("1")) {
                    if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                        SmartToast.showInfo("当前时间不在派送时间范围内");
                    } else {
                        getTag("2");
                    }

                } else {
                    SmartToast.showInfo("当前账号不是个人账号");
                }
                break;
            case R.id.ly_onclck_pingjia:// 查看全部评价、
                intent = new Intent(this, SeeAllPingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("shop_id", shop_id);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_top:
                sv_bodyContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        // scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
                        // scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
                        //
                        // 需要注意的是，该方法不能直接被调用
                        // 因为Android很多函数都是基于消息队列来同步，所以需要一部操作，
                        // addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll，
                        // view可能还没有显示出来，所以会失败
                        // 应该通过handler在新线程中更新
                        sv_bodyContainer.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                iv_top.setVisibility(View.GONE);
                break;
            case R.id.rel_cancel://已选 点击事件

                if (login_type.equals("1")) {
                /*AddShopDialog dialog = new AddShopDialog(this, shop_id, detailBean.getTitle(),
                        detailBean.getTitle_img(), tag_name, txt_shop_num,beanstag);
				dialog.show();*/
                    if (!TextUtils.isEmpty(txt_time_type)) {
                        if (txt_time_type.equals("距开始")) {
                            SmartToast.showInfo("活动即将开始");

                        } else if (txt_time_type.equals("已结束")) {
                            SmartToast.showInfo("活动已结束");

                        } else {
                            if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                                SmartToast.showInfo("当前时间不在派送时间范围内");
                            } else {
                                getTag("3");
                            }
                        }
                    } else {
                        if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                            SmartToast.showInfo("当前时间不在派送时间范围内");
                        } else {

                            getTag("3");
                        }
                    }


                } else if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(ShopDetailActivityNew.this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                    preferencesLogin = ShopDetailActivityNew.this.getSharedPreferences("login", 0);
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();
                } else {
                    SmartToast.showInfo("当前账号不是个人账号");
                }
                break;
            case R.id.ly_share://分享
                if (detailBean == null) {
                    return;
                }
                if (NullUtil.isStringEmpty(detailBean.getId())) {
                    return;
                }
                share_title = detailBean.getTitle() + "";
                share_desc = detailBean.getDescription() + "";
                share_icon = MyCookieStore.URL + detailBean.getTitle_img();
                share_url = ApiHttpClient.API_URL_SHARE + "home/shop/goods_details/id/" + detailBean.getId();
                HashMap<String, String> params = new HashMap<>();
                params.put("type", "goods_details");
                params.put("id", detailBean.getId());
                showDialog(smallDialog);
                LinkedMeUtils.getInstance().getLinkedUrl(this, share_url, share_title, params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
                    @Override
                    public void onGetUrl(String url, LMErrorCode error) {
                        hideDialog(smallDialog);
                        if (error == null) {
                            String share_url_new = share_url + "?linkedme=" + url;
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        } else {
                            //可以看报错
                            String share_url_new = share_url + "?linkedme=" + "";
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        }
                    }
                });
                break;
            case R.id.ly_store://店铺
                intent = new Intent(ShopDetailActivityNew.this, StoreIndexActivity.class);
                intent.putExtra("store_id", detailBean.getMerchant().getId());
                startActivity(intent);
                break;
            case R.id.rel_coupon://优惠券

                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                    requestCouponList();
                }
                // showCouponDialog();
                break;
            case R.id.ly_guanzhu://点击收藏关注
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                showDialog(smallDialog);
                mPresenter.getCollect(shop_id,"1");
                }
                break;
            default:
                break;
        }

    }

    /**
     * 请求优惠券dialog数据
     */
    private void requestCouponList() {
        // 根据接口请求数据
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", shop_id + "");
        MyOkHttp.get().get(ApiHttpClient.GOODS_COUPON_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelCouponNew> list = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCouponNew.class);
                    showCouponDialog(list);
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


    /**
     * 显示优惠券对话框
     */
    private void showCouponDialog(List<ModelCouponNew> mDatas) {
        //每次都要new
        chooseCouponDialog = new ChooseCouponDialog(this, mDatas, 4, new CouponListAdapter.OnClickRightBtnListener() {
            @Override
            public void onClickRightBtn(ModelCouponNew item, int position, int type) {
                if ("1".equals(item.getStatus())) {
                    couponAdd(item, position, type);
                } else {
                    SmartToast.showInfo("优惠券已领取");
                }

            }
        });
        chooseCouponDialog.show();
    }

    /**
     * 领取优惠券
     *
     * @param item
     * @param position
     * @param type
     */
    private void couponAdd(ModelCouponNew item, final int position, int type) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("coupon_id", item.getId() + "");
        MyOkHttp.get().post(ApiHttpClient.COUPON_ADD, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "领取成功"));
                    chooseCouponDialog.notifyOneItem(position);

                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "领取失败"));
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
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param share_icon
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String share_icon, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, share_icon, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(ly_share);
    }

    ShopProtocol protocoltag = new ShopProtocol();
    List<ShopDetailBean> beanstag = new ArrayList<ShopDetailBean>();

    private void getTag(final String isbooltag) {//获取商品标签
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", shop_id);
        HttpHelper hh = new HttpHelper(info.goods_tags, params, ShopDetailActivityNew.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                beanstag = protocoltag.getTag(json);
                //判断是否快速点击
                if (ToolUtils.isFastClick()) {
                    AddShopDialog dialog = new AddShopDialog(ShopDetailActivityNew.this, shop_id
                            , txt_shop_num, beanstag, detailBean, isbooltag, new AddShopDialog.PriorityListener() {
                        @Override
                        public void refreshPriorityUI(String string) {
                            lin_yixuanze.setVisibility(View.VISIBLE);
                            tag_guige.setVisibility(View.GONE);
                            tag_name.setVisibility(View.VISIBLE);
                            tag_name.setText(string);
                        }
                    });
                    dialog.show();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    private void getCartNum() {// 购物车商品数量
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        // params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(info.cart_num, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                cartnum = protocol.getCartNum(response);
                if (cartnum != null) {
                    if ("0".equals(cartnum.getCart_num())) {
                        txt_shop_num.setVisibility(View.GONE);
                    } else {
                        txt_shop_num.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    /**
     * ScrollView 的顶部，底部判断： http://blog.csdn.net/qq_21376985
     * <p/>
     * 其中getChildAt表示得到ScrollView的child View， 因为ScrollView只允许一个child
     * view，所以contentView.getMeasuredHeight()表示得到子View的高度,
     * getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。
     * 当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
     *
     * @param
     */
    private void doOnBorderListener() {
        // 底部判断
        if (contentView != null && contentView.getMeasuredHeight() <= sv_bodyContainer.getScrollY() + sv_bodyContainer.getHeight()) {
            iv_top.setVisibility(View.VISIBLE);

        }
        // 顶部判断
        else if (sv_bodyContainer.getScrollY() == 0) {
            iv_top.setVisibility(View.GONE);

        } else if (sv_bodyContainer.getScrollY() > 300) {
            iv_top.setVisibility(View.VISIBLE);

        }
    }

    /**
     * 销毁倒计时
     */
    private void cannelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    /**
     * 购物车数量变化
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShopCartChange(ModelEventShopCart model) {
        if (model != null) {
            getCartNum();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        this.cannelAllTimers();
        if (chooseCouponDialog != null) {
            chooseCouponDialog.dismiss();
        }

    }

    /**
     * 收藏关注商品
     * @param status
     * @param msg
     */
    @Override
    public void onCollect(int status, String msg) {
        hideDialog(smallDialog);
        if (status==1){
            tv_guanzhu.setText("已关注");
            iv_guanzhu.setBackgroundResource(R.mipmap.ic_collect_select);
            ly_guanzhu.setClickable(false);
            SmartToast.showInfo("已关注");
        }else {
            SmartToast.showInfo(msg);
        }
    }
}
