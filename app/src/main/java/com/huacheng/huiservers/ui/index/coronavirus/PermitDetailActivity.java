package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ZXingUtils;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：电子通行证详情
 * 时间：2020/2/24 08:52
 * created by DFF
 */
public class PermitDetailActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_code)
    ImageView mIvCode;
    @BindView(R.id.tv_fangwu)
    TextView mTvFangwu;
    @BindView(R.id.tv_person)
    TextView mTvPerson;
    @BindView(R.id.tv_shenfenzheng)
    TextView mTvShenfenzheng;
    @BindView(R.id.ly_yezhu_ID)
    LinearLayout mLyYezhuID;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_car_num)
    TextView mTvCarNum;
    @BindView(R.id.tv_fangke_content)
    TextView mTvFangkeContent;
    @BindView(R.id.ly_fangke_content)
    LinearLayout mLyFangkeContent;
    @BindView(R.id.tv_yezhu_address)
    TextView mTvYezhuAddress;
    @BindView(R.id.ly_yezhu_address)
    LinearLayout mLyYezhuAddress;
    @BindView(R.id.tv_yezhu_content)
    TextView mTvYezhuContent;
    @BindView(R.id.ly_yezhu_content)
    LinearLayout mLyYezhuContent;
    @BindView(R.id.ly_all)
    LinearLayout mLyAll;
    @BindView(R.id.txt_btn)
    TextView mTxtBtn;
    @BindView(R.id.tv_shixiao)
    TextView mTvShixiao;
    private View headerView;
    protected PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private PermitDetailAdapter adapter;
    List<ModelPermit> mDatas = new ArrayList<>();
    private int page = 1;
    private int total_Page = 1;
    ModelPermit permitInfo;
    private String company_id;
    private String id;

    @Override
    protected void initView() {
        //  ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("电子通行证");
        listView = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_permit_detail_header, null);
        listView.addHeaderView(headerView);
        ButterKnife.bind(this, headerView);

        adapter = new PermitDetailAdapter(this, R.layout.layout_permit_detail_item, mDatas);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  if (id<0){
                    return;
                }
                Intent intent = new Intent(PermitListActivity.this, PermitDetailActivity.class);
                intent.putExtra("company_id",company_id);
                intent.putExtra("id",mDatas.get(position).getId());
                startActivity(intent);*/

            }
        });
        //一开始先设置成false onScrollListener
        listView.setHasMoreItems(false);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                requestData();
            }
        });
        mTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新提交
                Intent intent = new Intent(PermitDetailActivity.this, SubmitPermitActivity.class);
                intent.putExtra("company_id", company_id);
                intent.putExtra("id", id);
                intent.putExtra("status", "3");
                intent.putExtra("info", permitInfo);
                startActivity(intent);
                startActivity(intent);
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("pc_id", id + "");
        params.put("company_id", company_id + "");
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.GET_PERMIT_DETAIL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setIsLoading(false);
                mLyAll.setVisibility(View.VISIBLE);
                // ly_share.setClickable(true);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelPermit modelPermit = (ModelPermit) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPermit.class);
                    // List<ModelShopIndex> shopIndexList = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelShopIndex.class);
                    if (modelPermit != null) {
                        permitInfo = modelPermit;
                        inflateContent(permitInfo);
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
                listView.setHasMoreItems(false);
                listView.setIsLoading(false);
                // ly_share.setClickable(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    private void inflateContent(ModelPermit modelPermit) {
        //基本信息
        if (page == 1) {

            if ("1".equals(modelPermit.getStatus())) {//待审核
                mIvCode.setBackgroundResource(R.mipmap.ic_permit_shenhezhong);

            } else if ("2".equals(modelPermit.getStatus())) {//已通过
                Bitmap qrImage = ZXingUtils.createQRImage(modelPermit.getQr_code(), StringUtils.dip2px(200), StringUtils.dip2px(200));
                mIvCode.setImageBitmap(qrImage);

            } else if ("3".equals(modelPermit.getStatus())) {//已拒绝
                mIvCode.setBackgroundResource(R.mipmap.ic_permit_yijujue);
                mTxtBtn.setVisibility(View.VISIBLE);

            } else if ("4".equals(modelPermit.getStatus())) {//已失效
                mTvShixiao.setVisibility(View.VISIBLE);
                Bitmap qrImage = ZXingUtils.createQRImage(modelPermit.getQr_code(), StringUtils.dip2px(200), StringUtils.dip2px(200));
                mIvCode.setImageBitmap(qrImage);
            }
            if ("1".equals(modelPermit.getType())) {
                mTvStatus.setText("临时通行证");
                //没有来访事由
                mLyFangkeContent.setVisibility(View.GONE);

            } else if ("2".equals(modelPermit.getType())) {
                mTvStatus.setText("长期通行证");
                //没有外出事由 到达地址  来访事由
                mLyYezhuContent.setVisibility(View.GONE);
                mLyYezhuAddress.setVisibility(View.GONE);
                mLyFangkeContent.setVisibility(View.GONE);

            } else {
                mTvStatus.setText("访客通行证");
                //没有身份证号 到达地址  外出事由
                mLyYezhuID.setVisibility(View.GONE);
                mLyYezhuAddress.setVisibility(View.GONE);
                mLyFangkeContent.setVisibility(View.GONE);
            }
            mTvFangwu.setText(modelPermit.getCommunity_name());
            mTvPerson.setText(modelPermit.getOwner_name());
            mTvShenfenzheng.setText(modelPermit.getId_card());
            mTvPhone.setText(modelPermit.getPhone());
            if (!NullUtil.isStringEmpty(modelPermit.getCar_number())) {
                mTvCarNum.setText(modelPermit.getCar_number());
            } else {
                mTvCarNum.setText("--");
            }
            mTvYezhuAddress.setText(modelPermit.getAddress());
            mTvYezhuContent.setText(modelPermit.getNote());
            mTvFangkeContent.setText(modelPermit.getNote());

        }
        //出入信息
        if (modelPermit.getPca_list() != null && modelPermit.getPca_list().size() > 0) {
            if (page == 1) {
                mDatas.clear();
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(0);
                    }
                });
            }
            mDatas.addAll(modelPermit.getPca_list());
            page++;
            total_Page = modelPermit.getTotalPages();// 设置总页数
            mRelNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            if (page > total_Page) {

                listView.setHasMoreItems(false);
            } else {
                listView.setHasMoreItems(true);
            }
        } else {
            if (page == 1) {
                mDatas.clear();
                // mRelNoData.setVisibility(View.VISIBLE);
//                            img_data.setBackgroundResource(R.mipmap.bg_no_shop_order_data);
//                            tv_text.setText("暂无订单");
                adapter.notifyDataSetChanged();
                total_Page = 0;
            }
            listView.setHasMoreItems(false);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permit_detail;
    }

    @Override
    protected void initIntentData() {
        company_id = this.getIntent().getStringExtra("company_id");
        id = this.getIntent().getStringExtra("id");

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
        ButterKnife.bind(this);
    }
}
