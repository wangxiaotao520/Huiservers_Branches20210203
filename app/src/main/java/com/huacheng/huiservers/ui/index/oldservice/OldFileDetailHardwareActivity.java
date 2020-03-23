package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCheckRecodDetail;
import com.huacheng.huiservers.model.ModelOldCheckRecord;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 体检记录()
 * created by wangxiaotao
 * 2020/1/9 0009 上午 10:40
 */
public class OldFileDetailHardwareActivity extends BaseActivity {
    private TextView tv_tijian_type, tv_tijian_time
            ,tv_tijian_bianhao,tv_height,tv_weight,tv_BM,tv_xuyang_baohe,tv_shousouya,tv_shuzhangya,tv_maibo
            ,tv_zhifanglv,tv_jiroulv,tv_ti_shuifen,tv_daixie,tv_guliang,tv_neizhifang;
    private String physicalID;
    private String p_id;
    private String o_company_id;
    private LinearLayout ll_normal_suggestion,ll_normal_suggestion_container,ll_sport_suggestion,ll_sport_suggestion_container,
    ll_eat_suggestion,ll_eat_suggestion_container,ll_zhongyi_suggestion,ll_zhongyi_suggestion_container;
    private ModelOldCheckRecord model;//前一个页面传过来的
    private ModelCheckRecodDetail model_info;
    private View ll_root;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("体检记录");
        tv_right=findViewById(R.id.txt_right);
        tv_right.setText("查看详情");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.orange));

        ll_root = findViewById(R.id.ll_root);
        ll_root.setVisibility(View.INVISIBLE);
        tv_tijian_type = findViewById(R.id.tv_tijian_type);
        tv_tijian_time = findViewById(R.id.tv_tijian_time);
        tv_tijian_bianhao = findViewById(R.id.tv_tijian_bianhao);
        if (model!=null){
            tv_tijian_type.setText("智能硬件体检");
            tv_tijian_time.setText(StringUtils.getDateToString(model.getChecktime(),"8"));
            tv_tijian_bianhao.setText(model.getPhysicalID());
        }

        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_BM = findViewById(R.id.tv_BM);
        tv_xuyang_baohe = findViewById(R.id.tv_xuyang_baohe);
        tv_shousouya = findViewById(R.id.tv_shousouya);
        tv_shuzhangya = findViewById(R.id.tv_shuzhangya);
        tv_maibo = findViewById(R.id.tv_maibo);
        tv_zhifanglv = findViewById(R.id.tv_zhifanglv);
        tv_jiroulv = findViewById(R.id.tv_jiroulv);
        tv_ti_shuifen = findViewById(R.id.tv_ti_shuifen);
        tv_daixie = findViewById(R.id.tv_daixie);
        tv_guliang = findViewById(R.id.tv_guliang);
        tv_neizhifang = findViewById(R.id.tv_neizhifang);

        ll_normal_suggestion= findViewById(R.id.ll_normal_suggestion);
        ll_normal_suggestion_container= findViewById(R.id.ll_normal_suggestion_container);
        ll_sport_suggestion= findViewById(R.id.ll_sport_suggestion);
        ll_sport_suggestion_container= findViewById(R.id.ll_sport_suggestion_container);
        ll_eat_suggestion= findViewById(R.id.ll_eat_suggestion);
        ll_eat_suggestion_container= findViewById(R.id.ll_eat_suggestion_container);
        ll_zhongyi_suggestion= findViewById(R.id.ll_zhongyi_suggestion);
        ll_zhongyi_suggestion_container= findViewById(R.id.ll_zhongyi_suggestion_container);
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("o_company_id",o_company_id);
        params.put("p_id",p_id);
        if (!NullUtil.isStringEmpty(physicalID)){
            params.put("physicalID",physicalID);
        }
        MyOkHttp.get().post(ApiHttpClient.PENSION_BELTER_DETAILS, params, new JsonResponseHandler() {



            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    model_info = (ModelCheckRecodDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelCheckRecodDetail.class);
                    if (model !=null){
                        ll_root.setVisibility(View.VISIBLE);


//                        if ("1".equals(info.getType())){
//                            tv_tijian_type.setText( " 常规体检");
//                        }else if ("2".equals(info.getType())){
//                            tv_tijian_type.setText(" 智能硬件体检");
//                        }else if ("3".equals(info.getType())){
//                            tv_tijian_type.setText( " 合作医院体检");
//                        }
//                        tv_tijian_time.setText(StringUtils.getDateToString(info.getChecktime(),"8"));

                     if (model_info.getInfo()!=null){
                         ModelCheckRecodDetail.InfoBean info = model_info.getInfo();
                         if (!NullUtil.isStringEmpty(info.getHeight())){
                             tv_height.setText(info.getHeight()+"cm");
                         }

                         if (!NullUtil.isStringEmpty(info.getWeight())){
                             tv_weight.setText(info.getWeight()+"kg");
                         }
                         if (!NullUtil.isStringEmpty(info.getBmi())){
                             tv_BM.setText(info.getBmi()+"");
                         }
                         if (!NullUtil.isStringEmpty(info.getBloodoxygen())){
                             tv_xuyang_baohe.setText(info.getBloodoxygen()+"%");
                         }
                         if (!NullUtil.isStringEmpty(info.getSystolic())){
                             tv_shousouya.setText(info.getSystolic()+"mmmhg");
                         }
                         if (!NullUtil.isStringEmpty(info.getDiastolic())){
                             tv_shuzhangya.setText(info.getDiastolic()+"mmmhg");
                         }
                         if (!NullUtil.isStringEmpty(info.getPulse())){
                             tv_maibo.setText(info.getPulse()+"次/分");
                         }
                         if (!NullUtil.isStringEmpty(info.getAdiposerate())){
                             tv_zhifanglv.setText(info.getAdiposerate()+"%");
                         }
                         if (!NullUtil.isStringEmpty(info.getMuscle())){
                             tv_jiroulv.setText(info.getMuscle()+"");
                         }
                         if (!NullUtil.isStringEmpty(info.getMoisturerate())){
                             tv_ti_shuifen.setText(info.getMoisturerate()+"%");
                         }
                         if (!NullUtil.isStringEmpty(info.getBasalMetabolism())){
                             tv_daixie.setText(info.getBasalMetabolism()+"kcal");
                         }
                         if (!NullUtil.isStringEmpty(info.getBone())){
                             tv_guliang.setText(info.getBone()+"kg");
                         }
                         if (!NullUtil.isStringEmpty(info.getVisceralfat())){
                             tv_neizhifang.setText(info.getVisceralfat()+"");
                         }
                     }
                        if (model_info.getSuggest()!=null){
                            ModelCheckRecodDetail.SuggestBean suggest = model_info.getSuggest();
                            if (suggest.getCommon().size()>0){
                                ll_normal_suggestion.setVisibility(View.VISIBLE);
                                ll_normal_suggestion_container.setVisibility(View.VISIBLE);
                                for (int i = 0; i < suggest.getCommon().size(); i++) {
                                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_old_file_suggestion_textview, null);
                                    TextView tv_str = view.findViewById(R.id.tv_str);
                                    tv_str.setText((i+1)+"、"+suggest.getCommon().get(i).getStr());
                                    ll_normal_suggestion_container.addView(view);
                                }
                            }else {
                                ll_normal_suggestion.setVisibility(View.GONE);
                                ll_normal_suggestion_container.setVisibility(View.GONE);
                            }
                            if (suggest.getSport().size()>0){
                                ll_sport_suggestion.setVisibility(View.VISIBLE);
                                ll_sport_suggestion_container.setVisibility(View.VISIBLE);
                                for (int i = 0; i < suggest.getSport().size(); i++) {
                                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_old_file_suggestion_textview, null);
                                    TextView tv_str = view.findViewById(R.id.tv_str);
                                    tv_str.setText((i+1)+"、"+suggest.getSport().get(i).getStr());
                                    ll_sport_suggestion_container.addView(view);
                                }
                            }else {
                                ll_sport_suggestion.setVisibility(View.GONE);
                                ll_sport_suggestion_container.setVisibility(View.GONE);
                            }
                            if (suggest.getFood().size()>0){
                                ll_eat_suggestion.setVisibility(View.VISIBLE);
                                ll_eat_suggestion_container.setVisibility(View.VISIBLE);
                                for (int i = 0; i < suggest.getFood().size(); i++) {
                                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_old_file_suggestion_textview, null);
                                    TextView tv_str = view.findViewById(R.id.tv_str);
                                    tv_str.setText((i+1)+"、"+suggest.getFood().get(i).getStr());
                                    ll_eat_suggestion_container.addView(view);
                                }
                            }else {
                                ll_eat_suggestion.setVisibility(View.GONE);
                                ll_eat_suggestion_container.setVisibility(View.GONE);
                            }
                            if (suggest.getDoctor().size()>0){
                                ll_zhongyi_suggestion.setVisibility(View.VISIBLE);
                                ll_zhongyi_suggestion_container.setVisibility(View.VISIBLE);
                                for (int i = 0; i < suggest.getDoctor().size(); i++) {
                                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_old_file_suggestion_textview, null);
                                    TextView tv_str = view.findViewById(R.id.tv_str);
                                    tv_str.setText((i+1)+"、"+suggest.getDoctor().get(i).getStr());
                                    ll_zhongyi_suggestion_container.addView(view);
                                }
                            }else {
                                ll_zhongyi_suggestion.setVisibility(View.GONE);
                                ll_zhongyi_suggestion_container.setVisibility(View.GONE);
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
        tv_right.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (model_info!=null){
                    if (((model_info.getInfo().getPdf_url())+"").contains("http")){
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(model_info.getInfo().getPdf_url());
                        intent.setData(content_url);
                        mContext.startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_file_detail_hardware;
    }

    @Override
    protected void initIntentData() {
        physicalID = this.getIntent().getStringExtra("physicalID");
        o_company_id = this.getIntent().getStringExtra("o_company_id");
        p_id = this.getIntent().getStringExtra("p_id");
        model = (ModelOldCheckRecord) this.getIntent().getSerializableExtra("model");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
