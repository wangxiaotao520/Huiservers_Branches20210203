package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCheckRecodDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 类描述：老人档案体检记录详情
 * 时间：2019/8/17 15:56
 * created by DFF
 */
public class OldFileDetailActivity extends BaseActivity {
    private TextView tv_tijian_type, tv_tijian_time, tv_xueya, tv_xueyang, tv_xuetang, tv_xinlv,tv_bingli_content;
    private ImageView iv_img;
    private String checkup_id;
    private String o_company_id;
    private LinearLayout ll_img_container;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("体检记录");

        tv_tijian_type = findViewById(R.id.tv_tijian_type);
        tv_tijian_time = findViewById(R.id.tv_tijian_time);
        tv_xueya = findViewById(R.id.tv_xueya);
        tv_xueyang = findViewById(R.id.tv_xueyang);
        tv_xuetang = findViewById(R.id.tv_xuetang);
        tv_xinlv = findViewById(R.id.tv_xinlv);
        tv_bingli_content = findViewById(R.id.tv_tijian_content);
        ll_img_container = findViewById(R.id.ll_img_container);

    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("o_company_id",o_company_id);
        params.put("checkup_id",checkup_id);
        MyOkHttp.get().post(ApiHttpClient.PENSION_CHECKUP_ONE_SEE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    final ModelCheckRecodDetail info = (ModelCheckRecodDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelCheckRecodDetail.class);
                    if (info!=null){
                        if ("1".equals(info.getType())){
                            tv_tijian_type.setText( " 常规体检");
                        }else if ("2".equals(info.getType())){
                            tv_tijian_type.setText(" 智能硬件体检");
                        }else if ("3".equals(info.getType())){
                            tv_tijian_type.setText( " 合作医院体检");
                        }
                        tv_tijian_time.setText(StringUtils.getDateToString(info.getChecktime(),"8"));
                        tv_xueya.setText(info.getBp_min()+" ~ "+info.getBp_max()+"mol/pa");
                        tv_xueyang.setText(info.getSao2()+"%");
                        tv_xuetang.setText(info.getGlu()+"mmol/l");
                        tv_xinlv.setText(info.getHr()+"次/分");
                        tv_bingli_content.setText(info.getDescribe()+"");

                        ll_img_container.removeAllViews();
                        if (info.getEcg_img()!=null&&info.getEcg_img().size()>0){
                            for (int i = 0; i < info.getEcg_img().size(); i++) {
                                ImageView imageView = new ImageView(OldFileDetailActivity.this);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(OldFileDetailActivity.this, 50), DeviceUtils.dip2px(OldFileDetailActivity.this, 50));
                                params1.setMargins(0,0,DeviceUtils.dip2px(OldFileDetailActivity.this, 15),0);
                                imageView.setLayoutParams(params1);
                                GlideUtils.getInstance().glideLoad(OldFileDetailActivity.this,ApiHttpClient.IMG_URL+info.getEcg_img().get(i).getImg(),imageView,R.drawable.ic_default_rectange);
                                final int finalI = i;
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ArrayList<String> imgs = new ArrayList<>();
                                        for (int i1 = 0; i1 < info.getEcg_img().size(); i1++) {
                                            imgs.add(ApiHttpClient.IMG_URL + info.getEcg_img().get(i1).getImg());
                                        }
                                        Intent intent = new Intent(mContext, PhotoViewPagerAcitivity.class);
                                        intent.putExtra("img_list",imgs);
                                        intent.putExtra("position", finalI);
                                        mContext.startActivity(intent);
                                    }
                                });
                                ll_img_container.addView(imageView);
                            }
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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_file_detail;
    }

    @Override
    protected void initIntentData() {
        checkup_id = this.getIntent().getStringExtra("checkup_id");
        o_company_id = this.getIntent().getStringExtra("o_company_id");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
