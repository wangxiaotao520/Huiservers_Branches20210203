package com.huacheng.huiservers.ui.index.houserent;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.houserent.adapter.HouseRentListAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：房屋租赁详情
 * 时间：2018/11/6 15:03
 * created by DFF
 */
public class HouserentDetailActivity extends BaseActivity {
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.tv_braoker_name)
    TextView mTvBraokerName;
    @BindView(R.id.tv_btn_call)
    TextView mTvBtnCall;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;

    List<String> StoreCatedata = new ArrayList<>();
    List<HouseRentDetail> tj_mDatas = new ArrayList<>();
    private static final String[] strDatas = new String[]{
            "first", "second"};
    HeaderViewHolder headerViewHolder;
    HouseRentDetail houseRentInfo;
    int HouseRentType;
    String url_detail, id;
    private HouseRentListAdapter houseRentListAdapter;
    View headerView;
    private int nHeight;
    private int width1;
    private RelativeLayout rl_title;
    private TextView title_name;
    private View view_alpha_title;
    private ImageView iv_back;


    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha(0);

        rl_title = findViewById(R.id.rl_title);
        title_name = findViewById(R.id.title_name);
        view_alpha_title = findViewById(R.id.view_alpha_title);
        iv_back = findViewById(R.id.iv_back);
        title_name.setText("");
        view_alpha_title.setAlpha(0);
        iv_back.setBackgroundResource(R.mipmap.ic_arrow_left_white);

        //添加list头布局
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_houserentdetail_header, null);
        mList.addHeaderView(headerView);
        headerViewHolder = new HeaderViewHolder(headerView);

        WindowManager wm1 = this.getWindowManager();
        width1 = wm1.getDefaultDisplay().getWidth();
        nHeight = DeviceUtils.dip2px(this,250);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, nHeight);
        headerViewHolder.mBanner1.setLayoutParams(layoutParams);

    /*    mList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strDatas));*/
        houseRentListAdapter = new HouseRentListAdapter(this, R.layout.item_house_rent_list, tj_mDatas, this.HouseRentType);
        mList.setAdapter(houseRentListAdapter);

        headerView.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void initData() {

        getDetail();
    }

    private void getDetail() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("house_id", id);
        if (HouseRentType == 1) {
            url_detail = ApiHttpClient.GET_HOUSEEND_LEASE_DETIL;
        } else {
            url_detail = ApiHttpClient.GET_HOUSEEND_SELL_DETIL;
        }
        MyOkHttp.get().post(url_detail, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    houseRentInfo = (HouseRentDetail) JsonUtil.getInstance().parseJsonFromResponse(response, HouseRentDetail.class);
                    getinfo();

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mTvBtnCall.setEnabled(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void getinfo() {
        if (houseRentInfo != null) {
            headerView.setVisibility(View.VISIBLE);
            //经纪人信息
            FrescoUtils.getInstance().setImageUri(mSdvHead, ApiHttpClient.IMG_URL + houseRentInfo.getAdministrator_img());
            mTvBraokerName.setText(houseRentInfo.getName());
            //轮播
            if (houseRentInfo.getHouse_img() != null && houseRentInfo.getHouse_img().size() > 0) {
                // 为XBanner绑定数据
                headerViewHolder.mBanner1.setData(houseRentInfo.getHouse_img(), null);//第二个参数为提示文字资源集合
                // XBanner适配数据
                headerViewHolder.mBanner1.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        GlideUtils.getInstance().glideLoad(HouserentDetailActivity.this, ApiHttpClient.IMG_URL +
                                houseRentInfo.getHouse_img().get(position).getPath()+ApiHttpClient.THUMB_1080_1920_+
                                houseRentInfo.getHouse_img().get(position).getHouse_imgs_name(), (ImageView) view, R.drawable.ic_bg_houserent);
                    }
                });
                headerViewHolder.mBanner1.setPageTransformer(Transformer.Default);//横向移动
            } else {
                List<HouseRentDetail.HouseImgBean> list = new ArrayList<>();
                list.add(new HouseRentDetail.HouseImgBean());
                headerViewHolder.mBanner1.setData(list, null);//第二个参数为提示文字资源集合
                headerViewHolder.mBanner1.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        ((ImageView) view).setImageResource(R.drawable.ic_bg_houserent);
                    }
                });
            }
            headerViewHolder.mTvTitle.setText(houseRentInfo.getCommunity_name() + "-" + houseRentInfo.getRoom() + "室" +
                    houseRentInfo.getOffice() + "厅" + houseRentInfo.getKitchen() + "厨" + houseRentInfo.getGuard() + "卫"
                    + "面积" + houseRentInfo.getArea() + "平米" + houseRentInfo.getHouse_floor() + "/" + houseRentInfo.getFloor() + "层");
            //价格
            if (HouseRentType == 1) {
                headerViewHolder.tv_price.setText(houseRentInfo.getUnit_price() + "元/月");
            }else {
                headerViewHolder.tv_price.setText(houseRentInfo.getTotal_price() + "元");
            }
            headerViewHolder.tv_time_release.setText(StringUtils.getDateToString(houseRentInfo.getRelease_time(), "8")+"更新");

            //标签
            if (houseRentInfo.getLabel() != null && houseRentInfo.getLabel().size() > 0) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                StoreCatedata.clear();
                StoreCatedata.add("物业认证");
                for (int i = 0; i < houseRentInfo.getLabel().size(); i++) {
                    StoreCatedata.add(houseRentInfo.getLabel().get(i).getLabel_name());
                }
                TagAdapter adapter = new TagAdapter<String>(StoreCatedata) {

                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.activity_houserent_tag_item,
                                headerViewHolder.mFlowlayout, false);
                        tv.setText(StoreCatedata.get(position));
                        return tv;
                    }
                };
                //adapter.setSelectedList(list_selected_position);
                headerViewHolder.mFlowlayout.setAdapter(adapter);
            }
            headerViewHolder.mTvHouseArea.setText(houseRentInfo.getArea() + "㎡");
            headerViewHolder.mTvHouseType.setText(houseRentInfo.getRoom() + "室" +
                    houseRentInfo.getOffice() + "厅" + houseRentInfo.getKitchen() + "厨" + houseRentInfo.getGuard() + "卫");
            headerViewHolder.mTvFloor.setText(houseRentInfo.getHouse_floor() + "/" + houseRentInfo.getFloor() + "层");

            //判断显示数据是租房还是售房
            if (HouseRentType == 1) {

                //1为月付，2为季付，3为半年付，4为年付，5为可贷款，6为全款
                headerViewHolder.tv_fk_type_name.setText("付款方式");
                if (houseRentInfo.getPay_type().equals("1")) {
                    headerViewHolder.mTvFkType.setText("月付");
                } else if (houseRentInfo.getPay_type().equals("2")) {
                    headerViewHolder.mTvFkType.setText("季付");
                } else if (houseRentInfo.getPay_type().equals("3")) {
                    headerViewHolder.mTvFkType.setText("半年付");
                } else if (houseRentInfo.getPay_type().equals("4")) {
                    headerViewHolder.mTvFkType.setText("年付");
                } else if (houseRentInfo.getPay_type().equals("5")) {
                    headerViewHolder.mTvFkType.setText("可贷款");
                }else {
                    headerViewHolder.mTvFkType.setText("全款");
                }
                headerViewHolder.mTvRuzhu.setText(houseRentInfo.getCheck_in());
                headerViewHolder.tv_zq_time_name.setText("房屋租期");
                headerViewHolder.mTvZqTime.setText(houseRentInfo.getLease_term());
                if (houseRentInfo.getElevator().equals("1")) {
                    headerViewHolder.mTvElevator.setText("有");
                } else {
                    headerViewHolder.mTvElevator.setText("无");
                }

                //租房小贴士
                headerViewHolder.mTvType1.setText("租房小贴士");
                //
                headerViewHolder.mLyZq.setVisibility(View.VISIBLE);

            } else {
                headerViewHolder.tv_fk_type_name.setText("房屋单价");
                headerViewHolder.mTvFkType.setText(houseRentInfo.getUnit_price() + "元");
                headerViewHolder.mTvRuzhu.setText(houseRentInfo.getCheck_in());
                //1为月付，2为季付，3为半年付，4为年付，5为可贷款，6为全款
                headerViewHolder.tv_zq_time_name.setText("付款方式");
                if (houseRentInfo.getPay_type().equals("1")) {
                    headerViewHolder.mTvZqTime.setText("月付");
                } else if (houseRentInfo.getPay_type().equals("2")) {
                    headerViewHolder.mTvZqTime.setText("季付");
                } else if (houseRentInfo.getPay_type().equals("3")) {
                    headerViewHolder.mTvZqTime.setText("半年付");
                } else if (houseRentInfo.getPay_type().equals("4")) {
                    headerViewHolder.mTvZqTime.setText("年付");
                } else if (houseRentInfo.getPay_type().equals("5")) {
                    headerViewHolder.mTvZqTime.setText("可贷款");
                }else {
                    headerViewHolder.mTvZqTime.setText("全款");
                }
                if (houseRentInfo.getElevator().equals("1")) {
                    headerViewHolder.mTvElevator.setText("有");
                } else {
                    headerViewHolder.mTvElevator.setText("无");
                }
                //
                headerViewHolder.mLyZq.setVisibility(View.VISIBLE);

                //售房小贴士
                headerViewHolder.mTvType1.setText("售房小贴士");

            }
            //基本信息
            byte[] bytes = Base64.decode(houseRentInfo.getContent(), Base64.DEFAULT);
            headerViewHolder.mWebview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
            if (!"".equals(new String(bytes))) {
                String css = "<style type=\"text/css\"> " +
                        "img {" +
                        "max-width: 100% !important;" +//限定图片宽度填充屏幕
                        "height:auto !important;" +//限定图片高度自动
                        "}" +"body" +
                        "  {" +
                        "  color:#666666;" +
                        "  }"+
                        "</style>";
                String content1 = "<head>" + css + "</head><body>" + new String(bytes) + "</body></html>";
                headerViewHolder.mWebview.loadDataWithBaseURL(null, content1, "text/html", "utf-8", null);
            }
            //推荐房屋
            if (houseRentInfo.getRecommend() != null && houseRentInfo.getRecommend().size() > 0) {
//               tj_mDatas.clear();
//               tj_mDatas.addAll(houseRentInfo.getRecommend());
//                houseRentListAdapter.notifyDataSetChanged();
//                headerViewHolder.mView1.setVisibility(View.VISIBLE);
//                headerViewHolder.mLyTj.setVisibility(View.VISIBLE);
                headerViewHolder.hsv_recommand_container.setVisibility(View.VISIBLE);
                headerViewHolder.ll_recommond_container.removeAllViews();
                for (int i = 0; i < houseRentInfo.getRecommend().size(); i++) {
                    View view_reommand = LayoutInflater.from(this).inflate(R.layout.item_house_rent_detail_recommand, null);
                    SimpleDraweeView sdv_img = view_reommand.findViewById(R.id.sdv_img);
                    TextView tv_price = view_reommand.findViewById(R.id.tv_price);
                    TextView tv_name = view_reommand.findViewById(R.id.tv_name);
                    TextView tv_unit_price = view_reommand.findViewById(R.id.tv_unit_price);

                    HouseRentDetail item = houseRentInfo.getRecommend().get(i);
                    FrescoUtils.getInstance().setImageUri(sdv_img, StringUtils.getImgUrl(item.getHead_img()+""));
                    tv_name.setText(item.getCommunity_name()+"   "
                            +item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫");
                    if (HouseRentType==1){
                        tv_price.setText(item.getUnit_price()+"元/月");
                        tv_unit_price.setText("");
                    }else {
                        tv_price.setText(item.getTotal_price()+"元");
                        tv_unit_price.setText(item.getUnit_price()+"元/平");
                    }
                    final int finalI = i;
                    view_reommand.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            String id1 = houseRentInfo.getRecommend().get(finalI).getId();
                            Intent intent = new Intent(HouserentDetailActivity.this, HouserentDetailActivity.class);
                            intent.putExtra("jump_type", HouseRentType);
                            intent.putExtra("id", id1);
                            startActivity(intent);
                        }
                    });
                    headerViewHolder.ll_recommond_container.addView(view_reommand);
                }
            } else {
//                headerViewHolder.mView1.setVisibility(View.GONE);
//                headerViewHolder.mLyTj.setVisibility(View.GONE);

                headerViewHolder.hsv_recommand_container.setVisibility(View.GONE);
            }

        }

    }

    @Override
    protected void initListener() {
        headerViewHolder.mBanner1.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                //Toast.makeText(HouserentDetailActivity.this, "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
                ArrayList<String> imgs = new ArrayList<>();
                if (houseRentInfo!=null){
                    for (int i = 0; i < houseRentInfo.getHouse_img().size(); i++) {
                        imgs.add(ApiHttpClient.IMG_URL +houseRentInfo.getHouse_img().get(i).getPath()+houseRentInfo.getHouse_img().get(i).getHouse_imgs_name());
                    }
                    Intent intent = new Intent(HouserentDetailActivity.this, PhotoViewPagerAcitivity.class);
                    intent.putExtra("img_list",imgs);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            }
        });
        headerViewHolder.mLyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouserentDetailActivity.this, HouseRentTipsActivity.class);
                intent.putExtra("jump_type", HouseRentType);
                startActivity(intent);
            }
        });
        headerViewHolder.rl_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouserentDetailActivity.this, HouseRentTipsActivity.class);
                intent.putExtra("jump_type", HouseRentType);
                startActivity(intent);
            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id1 = tj_mDatas.get(position - 1).getId();
                Intent intent = new Intent(HouserentDetailActivity.this, HouserentDetailActivity.class);
                intent.putExtra("jump_type", HouseRentType);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                scroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }
    private void scroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (headerView != null) {
            //设置其透明度
            float alpha = 0;
            //向上滑动的距离
            int scollYHeight = -headerView.getTop();
            if (scollYHeight >= DeviceUtils.dip2px(this,100) ) {
                alpha = 1;//滑上去就一直显示
            } else {
                alpha = scollYHeight / ((DeviceUtils.dip2px(this,100) ) * 1.0f);
            }

            view_alpha_title.setAlpha(alpha);
            title_name.setAlpha(alpha);
            mStatusBar.setAlpha(alpha);

            if (alpha==0){
                title_name.setText("");
                iv_back.setBackgroundResource(R.mipmap.ic_arrow_left_white);
            }else {
                iv_back.setBackgroundResource(R.mipmap.ic_arrow_left_black);
                if (HouseRentType==1){
                    title_name.setText("租房详情");
                }else {
                    title_name.setText("售房详情");
                }
            }
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_houserentdetail_list;
    }

    @Override
    protected void initIntentData() {
        HouseRentType = this.getIntent().getExtras().getInt("jump_type");
        id = this.getIntent().getExtras().getString("id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        headerViewHolder.mBanner1.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        headerViewHolder.mBanner1.startAutoPlay();

    }

    @OnClick({R.id.lin_left, R.id.tv_btn_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.tv_btn_call://联系经纪人咨询
                if (NullUtil.isStringEmpty(houseRentInfo.getPhone())) {
                    return;
                }
                SharedPreferences preferencesLogin = this.getSharedPreferences("login", 0);
                String login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("1") && ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {

                    new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确认拨打电话？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                getCall();//拨打电话次数记录
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"
                                        + houseRentInfo.getPhone()));
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }
                    }).show();
                } else {
                    Intent intent = new Intent(this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    private void getCall() {
        HashMap<String, String> params = new HashMap<>();
        params.put("house_id", id);
        MyOkHttp.get().post(ApiHttpClient.GET_HOUSEEND_CALL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    class HeaderViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.flowlayout)
        TagFlowLayout mFlowlayout;
//        @BindView(R.id.tv_house_type_name)
//        TextView mTvHouseTypeName;

        @BindView(R.id.tv_house_type)
        TextView mTvHouseType;
        @BindView(R.id.tv_house_area)
        TextView mTvHouseArea;
        @BindView(R.id.tv_type1)
        TextView mTvType1;

        @BindView(R.id.ly_tips)
        LinearLayout mLyTips;
        @BindView(R.id.rl_tips)
        RelativeLayout rl_tips;
        @BindView(R.id.tv_fk_type)
        TextView mTvFkType;
        @BindView(R.id.tv_floor)
        TextView mTvFloor;
        @BindView(R.id.tv_ruzhu)
        TextView mTvRuzhu;
        @BindView(R.id.tv_elevator)
        TextView mTvElevator;
        @BindView(R.id.tv_zq_time_name)
        TextView tv_zq_time_name;
        @BindView(R.id.tv_fk_type_name)
        TextView tv_fk_type_name;
        @BindView(R.id.banner_1)
        XBanner mBanner1;
        @BindView(R.id.tv_zq_time)
        TextView mTvZqTime;
        @BindView(R.id.ly_zq)
        LinearLayout mLyZq;
        @BindView(R.id.webview)
        WebView mWebview;
        @BindView(R.id.view1)
        View mView1;
        @BindView(R.id.ly_tj)
        LinearLayout mLyTj;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_time_release)
        TextView tv_time_release;
        @BindView(R.id.hsv_recommand_container)
        HorizontalScrollView hsv_recommand_container;
        @BindView(R.id.ll_recommond_container)
        LinearLayout ll_recommond_container;


        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
