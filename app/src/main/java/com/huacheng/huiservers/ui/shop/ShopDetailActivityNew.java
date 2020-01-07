package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
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
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.jpush.MyReceiver;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.ui.shop.bean.ShopMainBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.statusbar.OSUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.huiservers.view.ScrollChangedScrollView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.huacheng.libraryservice.utils.timer.CountDownTimer;
import com.microquation.linkedme.android.log.LMErrorCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 商品详情界面
 */
//second为mLink的key
public class ShopDetailActivityNew extends BaseActivityOld implements OnClickListener {

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
    private TextView title_name, txt_name, txt_content, txt_price, txt_num, tag_name, txt_tag1, txt_tag2, right, txt_tag3, txt_tag4, txt_shop_num,
            txt_paisong, txt_yuan_price, txt_tuijian, tag_guige, tv_XS_type;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
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
    protected void init() {
        super.init();
        setContentView(R.layout.shop_detail_new);
        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 0);
        // 获取登陆值来判断是否登陆
        getLinshi();
        prefrenceUtil = new SharePrefrenceUtil(this);
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
        title_name.setTextColor(getResources().getColor(R.color.orange_backg));
        sv_bodyContainer = findViewById(R.id.anchor_bodyContainer);
        //ll_shop_tuijian = (LinearLayout) findViewById(R.id.ll_shop_tuijian);
        view_title_line = findViewById(R.id.view_title_line);
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
        rel_cancel = findViewById(R.id.rel_cancel);// 选择商品类型
        //评价
        ly_onclck_pingjia = findViewById(R.id.ly_onclck_pingjia);
        ly_pingjia = findViewById(R.id.ly_pingjia);// 评价
        tv_pingjia_num = findViewById(R.id.tv_pingjia_num);
       /* sdv_head = findViewById(R.id.sdv_head);
        tv_user_name = findViewById(R.id.tv_user_name);
        ratingBar = findViewById(R.id.ratingBar);
        tv_time = findViewById(R.id.tv_time);
        tv_content = findViewById(R.id.tv_content);
        tv_reply = findViewById(R.id.tv_reply);*/
       sdv_user_head= findViewById(R.id.sdv_user_head);
        tv_pingjia_name= findViewById(R.id.tv_pingjia_name);
       ratingBar=findViewById(R.id.ratingBar);
        tv_pingjia_time=findViewById(R.id.tv_pingjia_time);
        tv_pingjia_content=findViewById(R.id.tv_pingjia_content);
        tv_pingjia_guige=findViewById(R.id.tv_pingjia_guige);
       gridView=findViewById(R.id.gridView);

        txt_name = findViewById(R.id.txt_name);
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
        txt_tuijian = findViewById(R.id.txt_tuijian); // 商品描述图片列表id
        lin_img = findViewById(R.id.lin_img);
        tv_time_tag = findViewById(R.id.tv_time_tag);

        rel_shop_limit_bg = findViewById(R.id.rel_shop_limit_bg);
        tv_time_type = findViewById(R.id.tv_time_type); // 商品描述图片列表id
        lin_downcount = findViewById(R.id.lin_downcount);

        tv_downcount_day = findViewById(R.id.tv_downcount_day);
        tv_downcount_hour = findViewById(R.id.tv_downcount_hour);
        tv_downcount_minute = findViewById(R.id.tv_downcount_minute);
        tv_downcount_second = findViewById(R.id.tv_downcount_second);

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


    }

    @Override
    protected void initData() {
        super.initData();
        getDetail();
        if (!login_type.equals("")) {// 登陆之后获取数量
            getCartNum();
        }
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

                detailBean = protocol.getDetail(json);
                tag_guige.setVisibility(View.VISIBLE);
                ly_store.setVisibility(View.VISIBLE);
                GlideUtils.getInstance().glideLoad(ShopDetailActivityNew.this, MyCookieStore.URL + detailBean.getTitle_img(), img_title, R.drawable.ic_default_rectange500);
                txt_name.setText(detailBean.getTitle());
                txt_content.setText(detailBean.getDescription());
                txt_price.setText("¥" + detailBean.getPrice());
                txt_yuan_price.setText("¥" + detailBean.getOriginal());
                txt_num.setText("剩余：" + detailBean.getInventory() + " " + detailBean.getUnit());
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

            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
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
                    lin_left.setBackgroundResource(R.drawable.allshape_gray_round);
                    left.setBackgroundResource(R.mipmap.ic_arrow_left_white);
                    view_title_line.setVisibility(View.GONE);
                    ly_share.setBackgroundResource(R.drawable.allshape_gray_round);
                    iv_share.setBackgroundResource(R.mipmap.ic_share_white);
                    lin_title.setBackground(null);
                    title_name.setText("");

                } else {
                    lin_left.setBackground(null);
                    ly_share.setBackground(null);
                    left.setBackgroundResource(R.mipmap.ic_arrow_left_black);
                    view_title_line.setVisibility(View.VISIBLE);
                    lin_title.setBackgroundResource(R.color.white);
                    iv_share.setBackgroundResource(R.mipmap.ic_share_black);
                    title_name.setText(detailBean.getTitle());

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
                distance_str = "距开始";
                distance_tag = 1;
                distance_int = Long.parseLong(distanceStart);
                lin_XS_bottom.setVisibility(View.VISIBLE);//底部限时抢购
                lin_bottom.setVisibility(View.GONE);//购物车
            } else {
                distance_str = "距结束";
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

                    tv_XS_type.setText("距开始 " + (times[0]) + "天" + (times[1]) + "时" + (times[2]) + "分" + (times[3]) + "秒");
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
                        tv_time_tag.setText("距结束还剩");

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
            tv_pingjia_num.setText("商品评价(" + detailBean.getScore().size() + ")");
            FrescoUtils.getInstance().setImageUri(sdv_user_head, StringUtils.getImgUrl(detailBean.getScore().get(0).getAvatars()));
            ratingBar.setCountSelected(Integer.valueOf(detailBean.getScore().get(0).getScore()));
            tv_pingjia_name.setText(detailBean.getScore().get(0).getUsername());
            tv_pingjia_content.setText(detailBean.getScore().get(0).getDescription());
            tv_pingjia_time.setText(StringUtils.getDateToString(detailBean.getScore().get(0).getAddtime(), "2"));
            // TODO: 2020/1/7 商品图片 规格
            tv_pingjia_guige.setText("");
            /*ShopDetailListAdapter listAdapter = new ShopDetailListAdapter(ShopDetailActivityNew.this, detailBean.getScore());
            list_pingjia.setAdapter(listAdapter);*/
            // istag = 1;
        } else {
            ly_pingjia.setVisibility(View.GONE);
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
                        getTag("1");
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
                                getTag("2");
                            }
                        }
                    } else {
                        if (detailBean.getExist_hours().equals("2")) {// 判断是否打烊
                            SmartToast.showInfo("当前时间不在派送时间范围内");
                        } else {

                            getTag("2");
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
            default:
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
        popup.showBottom(ly_share);
    }

    ShopProtocol protocoltag = new ShopProtocol();
    List<ShopDetailBean> beanstag = new ArrayList<ShopDetailBean>();
    AddShopDialog dialog;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.cannelAllTimers();
    }
}
