package com.huacheng.huiservers.ui.index.request;

import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelRequest;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 类描述：投诉建议详情
 * 时间：2019/5/8 08:55
 * created by DFF
 */
public class RequesDetailActivity extends BaseActivity {

    private LinearLayout ly_all;
    private LinearLayout mlinear_repair_photo;
    private LinearLayout mlinear_photo;
    private LinearLayout ly_detail;
    private LinearLayout ly_reply;

    private TextView tv_status;
    private TextView tv_danhao;
    private TextView tv_time;
    private TextView tv_user_name;
    private TextView tv_user_address;
    private TextView tv_none;
    private TextView tv_user_phone;
    private TextView tv_detail_content;
    private TextView tv_reply_content;

    private String id = "";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("详情");

        ly_all = findViewById(R.id.ly_all);
        tv_danhao = findViewById(R.id.tv_danhao);
        tv_status = findViewById(R.id.tv_status);
        tv_time = findViewById(R.id.tv_time);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_phone = findViewById(R.id.tv_user_phone);
        mlinear_repair_photo = findViewById(R.id.linear_repair_photo);
        mlinear_photo = findViewById(R.id.linear_photo);
        tv_none = findViewById(R.id.tv_none);
        ly_detail = findViewById(R.id.ly_detail);
        tv_detail_content = findViewById(R.id.tv_detail_content);
        ly_reply = findViewById(R.id.ly_reply);
        tv_reply_content = findViewById(R.id.tv_reply_content);


    }

    @Override
    protected void initData() {
        getRequest();
    }

    @Override
    protected void initListener() {


    }

    /**
     * 请求数据
     */
    private void getRequest() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.FEED_BACK_DETAIL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelRequest modelRequest = (ModelRequest) JsonUtil.getInstance().parseJsonFromResponse(response, ModelRequest.class);
                    inflateContent(modelRequest);

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
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 填充数据
     */
    private void inflateContent(final ModelRequest modelRequest) {
        if (modelRequest != null) {
            tv_danhao.setText(modelRequest.getComplaint_number());
            tv_time.setText(StringUtils.getDateToString(modelRequest.getAddtime(), "7"));
            tv_user_name.setText(modelRequest.getNickname());
            tv_user_phone.setText(modelRequest.getPhone());
            tv_user_address.setText(modelRequest.getAddress());
            //对详情内容进行解密
          //  byte[] bytes = Base64.decode(modelRequest.getContent(), Base64.DEFAULT);
            tv_detail_content.setText(modelRequest.getContent());
            if (!NullUtil.isStringEmpty(modelRequest.getReply_content())) {
                ly_reply.setVisibility(View.VISIBLE);
                byte[] bytes1 = Base64.decode(modelRequest.getReply_content(), Base64.DEFAULT);
                tv_reply_content.setText(new String(bytes1));
            } else {
                ly_reply.setVisibility(View.GONE);
            }
            if (modelRequest.getStatus() == 1) {
                tv_status.setText("未处理");
                tv_status.setTextColor(this.getResources().getColor(R.color.red_bg));
            } else {
                tv_status.setText("已处理");
                tv_status.setTextColor(this.getResources().getColor(R.color.grey));
            }
            //故障照片
            if (modelRequest.getImg_list() != null && modelRequest.getImg_list().size() > 0) {
                tv_none.setVisibility(View.GONE);
                mlinear_repair_photo.setVisibility(View.VISIBLE);

                mlinear_repair_photo.removeAllViews();
                for (int i = 0; i < modelRequest.getImg_list().size(); i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
                            100);//两个50分别为添加图片的大小
                    // imageView.setImageResource(R.drawable.ic_launcher);
                    GlideUtils.getInstance().glideLoad(RequesDetailActivity.this, ApiHttpClient.IMG_URL
                            + modelRequest.getImg_list().get(i).getImg(), imageView, R.drawable.ic_default_head);
                    params.setMargins(0, 0, 20, 0);
                    imageView.setLayoutParams(params);
                    //点击图片放大
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击图片
                            ArrayList<String> imgs = new ArrayList<>();
                            for (int i = 0; i < modelRequest.getImg_list().size(); i++) {
                                //只要localpath不为空则说明是刚选上的
                                imgs.add(ApiHttpClient.IMG_URL
                                        + modelRequest.getImg_list().get(i).getImg());

                            }
                            Intent intent = new Intent(RequesDetailActivity.this, PhotoViewPagerAcitivity.class);
                            intent.putExtra("img_list", imgs);
                            intent.putExtra("position", finalI);
                            intent.putExtra("isShowDelete", false);
                            startActivity(intent);
                        }
                    });
                    mlinear_repair_photo.addView(imageView);
                }
            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_request_detail;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

}
