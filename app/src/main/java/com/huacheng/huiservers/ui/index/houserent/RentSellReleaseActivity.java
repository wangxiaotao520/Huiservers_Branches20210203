package com.huacheng.huiservers.ui.index.houserent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:租/售 发布
 * Author: badge
 * Create: 2018/11/6 17:17
 */
public class RentSellReleaseActivity extends BaseActivity {

    @BindView(R.id.et_rele_contant)
    EditText etReleContant;
    @BindView(R.id.et_rele_phone_num)
    EditText etRelePhoneNum;
    @BindView(R.id.et_rele_community_name)
    EditText etReleCommunityName;
    @BindView(R.id.et_rele_floor)
    EditText etReleFloor;
    @BindView(R.id.et_rele_area)
    EditText etReleArea;
    @BindView(R.id.et_rele_sell_unit_price)
    EditText etReleSellUnitPrice;

    @BindView(R.id.et_rele_sell_price)
    TextView etReleSellPrice;
    @BindView(R.id.et_rele_sent_price)
    EditText etReleSentPrice;

    @BindView(R.id.tv_rele_confirm_release)
    TextView tvReleConfirmRelease;
    @BindView(R.id.rl_rele_choose_house_type)
    RelativeLayout rlReleChooseHouseType;
    @BindView(R.id.ll_container_sell)
    LinearLayout llContainerSell;
    @BindView(R.id.rl_container_sent_price)
    RelativeLayout rlContainerSentPrice;

    @BindView(R.id.et_rele_room)
    EditText etReleRoom;
    @BindView(R.id.et_rele_hall)
    EditText etReleHall;
    @BindView(R.id.et_rele_kitchen)
    EditText etReleKitchen;
    @BindView(R.id.et_rele_guard)
    EditText etReleGuard;

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.et_rele_floor_total)
    EditText etReleFloorTotal;

    @BindView(R.id.et_building)
    EditText et_building;
    @BindView(R.id.et_unit)
    EditText et_unit;
    @BindView(R.id.et_room)
    EditText et_room;
    @BindView(R.id.ll_rent_time_container)
    LinearLayout ll_rent_time_container;
    @BindView(R.id.view_line_rent)
    View view_line_rent;
    @BindView(R.id.et_rent_time)
    EditText et_rent_time;

    private int type = 0;
    private String contact = "";
    private String phoneNum = "";
    private String communityName = "";

    private String strRoom = "";
    private String strHall = "";
    private String strKitChen = "";
    private String strGrard = "";

    private String floor = "";
    private String floor_total = "";
    private String area = "";

    private String rentPrice = "";

    private String sellUnitPrice = "";
    private String sellPrice = "";

    private String post_rent = "";

    private String buildsing_name = "";
    private String unit = "";
    private String code = "";
    private String lease_term = "";//租期
    @Override
    protected void initView() {
        if (type==1){
            titleName.setText("发布租房信息");
        }else {
            titleName.setText("发布售房信息");
        }
        TextPaint tp = titleName.getPaint();
        tp.setFakeBoldText(true);

        //如果是小数，位数保留两个，位数限制在7位
        ToolUtils.filterDecimalDigits(etReleArea);
        ToolUtils.filterDecimalDigits(etReleSellUnitPrice);
        ToolUtils.filterDecimalDigits(etReleSentPrice);

        ToolUtils.filterDecimalDigitsText(etReleSellPrice);
    }


    @Override
    protected void initData() {
        etReleCommunityName.requestFocus();
    }

    @Override
    protected void initListener() {
        //售房面积改变监控事件
        etReleArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                String releArea = etReleArea.getText().toString();
                String unitPrice = etReleSellUnitPrice.getText().toString();

                if (!StringUtils.isEmpty(releArea)) {
                    if (releArea.startsWith("0") || releArea.startsWith(".")) {//限制文本格式不是以“0”或“.”开头的
                        etReleArea.setText("");
                        return;
                    }
                }

                if (!StringUtils.isEmpty(releArea) && !StringUtils.isEmpty(unitPrice)) {
                    double d_area = Double.parseDouble(releArea);
                    double d_unitPrice = Double.parseDouble(unitPrice);

                    /*double 类型运算会出现精度问题
                      要先转换为字符串，后进行运算，可以写个方法做乘法运算

                      multiply为乘， divide为除
                    */
                    BigDecimal b_area = new BigDecimal(Double.toString(d_area));
                    BigDecimal b_unit = new BigDecimal(Double.toString(d_unitPrice));
                    BigDecimal total_big = b_area.multiply(b_unit);
                    BigDecimal total_1W = total_big.divide(BigDecimal.valueOf(10000));

                    etReleSellPrice.setText(total_1W + "");
                    ToolUtils.filterDecimalDigitsText(etReleSellPrice);//限制售价小数点位数
                } else {
                    etReleSellPrice.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //售房单价改变监控事件
        etReleSellUnitPrice.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String releArea = etReleArea.getText().toString();
                String unitPrice = etReleSellUnitPrice.getText().toString();

                if (!StringUtils.isEmpty(unitPrice)) {
                    if (unitPrice.startsWith("0") || unitPrice.startsWith(".")) {//限制文本格式不是以“0”或“.”开头的
                        etReleSellUnitPrice.setText("");
                        return;
                    }
                }

                if (!StringUtils.isEmpty(releArea) && !StringUtils.isEmpty(unitPrice)) {
                    double d_area = Double.parseDouble(releArea);
                    double d_unitPrice = Double.parseDouble(unitPrice);

                    /*double 类型运算会出现精度问题
                      要先转换为字符串，后进行运算，可以写个方法做乘法运算

                      multiply为乘， divide为除
                    */
                    BigDecimal b_area = new BigDecimal(Double.toString(d_area));
                    BigDecimal b_unit = new BigDecimal(Double.toString(d_unitPrice));
                    BigDecimal total_big = b_area.multiply(b_unit);
                    BigDecimal total_1W = total_big.divide(BigDecimal.valueOf(10000));

                    etReleSellPrice.setText(total_1W + "");
                    ToolUtils.filterDecimalDigitsText(etReleSellPrice);//限制售价小数点位数
                } else {
                    etReleSellPrice.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //租金改变监控事件
        etReleSentPrice.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sent = etReleSentPrice.getText().toString();
                if (!StringUtils.isEmpty(sent)) {
                    if (sent.startsWith("0") || sent.startsWith(".")) {//限制文本格式不是以“0”或“.”开头的
                        etReleSentPrice.setText("");
                        return;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sell_rent_release;
    }


    @Override
    protected void initIntentData() {
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            rlContainerSentPrice.setVisibility(View.VISIBLE);
            ll_rent_time_container.setVisibility(View.VISIBLE);
            view_line_rent.setVisibility(View.VISIBLE);
            llContainerSell.setVisibility(View.GONE);

        } else if (type == 2) {
            llContainerSell.setVisibility(View.VISIBLE);
            rlContainerSentPrice.setVisibility(View.GONE);
            ll_rent_time_container.setVisibility(View.GONE);
            view_line_rent.setVisibility(View.GONE);
        }


    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


    /**
     * 验证输入框
     *
     * @return
     */
    private boolean checkReady() {
        contact = etReleContant.getText().toString();
        phoneNum = etRelePhoneNum.getText().toString();
        communityName = etReleCommunityName.getText().toString();

        strRoom = etReleRoom.getText().toString();//室
        strHall = etReleHall.getText().toString(); //厅
        strKitChen = etReleKitchen.getText().toString();//厨
        strGrard = etReleGuard.getText().toString();//卫

        floor = etReleFloor.getText().toString();
        floor_total = etReleFloorTotal.getText().toString();
        area = etReleArea.getText().toString();

        rentPrice = etReleSentPrice.getText().toString();

        sellUnitPrice = etReleSellUnitPrice.getText().toString();
        sellPrice = etReleSellPrice.getText().toString();

        buildsing_name = et_building.getText().toString().trim();
        unit = et_unit.getText().toString().trim();
        code = et_room.getText().toString().trim();
        lease_term = et_rent_time.getText().toString().trim();


        if (StringUtils.isEmpty(contact)) {
            SmartToast.showInfo("请输入联系人姓名");
            return false;
        }
        if (StringUtils.isEmpty(phoneNum)) {
            SmartToast.showInfo("请输入手机号");
            return false;
        }
        if (!ToolUtils.isMobileNO(phoneNum)) {
            SmartToast.showInfo("请输入正确的手机号");
            return false;
        }

        if (StringUtils.isEmpty(buildsing_name)) {
            ToastUtils.showShort(this.getApplicationContext(), "请输入楼号");
            return false;
        }
        if (StringUtils.isEmpty(unit)) {
            ToastUtils.showShort(this.getApplicationContext(), "请输入单元号");
            return false;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtils.showShort(this.getApplicationContext(), "请输入房间号");
            return false;
        }
        if (StringUtils.isEmpty(communityName)) {
            SmartToast.showInfo("请输入小区名字");
            return false;
        }

        //室、厅、厨、卫
        if (StringUtils.isEmpty(strRoom)) {
            SmartToast.showInfo("请输入室");
            return false;
        }
        if (StringUtils.isEmpty(strHall)) {
            SmartToast.showInfo("请输入厅");
            return false;
        }
        if (StringUtils.isEmpty(strKitChen)) {
            SmartToast.showInfo("请输入厨");
            return false;
        }
        if (StringUtils.isEmpty(strGrard)) {
            SmartToast.showInfo("请输入卫");
            return false;
        }


        if (StringUtils.isEmpty(floor)) {
            SmartToast.showInfo("请输入楼层");
            return false;
        }
        if (StringUtils.isEmpty(floor_total)) {
            SmartToast.showInfo("请输入总楼层");
            return false;
        }

        if (StringUtils.isEmpty(area)) {
            SmartToast.showInfo("请输入房屋面积");
            return false;
        }

        if (type == 1) {
            if (StringUtils.isEmpty(rentPrice)) {
                SmartToast.showInfo("请输入租金");
                return false;
            }
            if (StringUtils.isEmpty(lease_term)) {
                SmartToast.showInfo("请输入租期");
                return false;
            }
        } else {
            if (StringUtils.isEmpty(sellUnitPrice)) {
                SmartToast.showInfo("请输入出售单价");
                return false;
            }
            if (StringUtils.isEmpty(sellPrice)) {
                SmartToast.showInfo("自动计算售价异常");
                return false;
            }

        }

        return true;
    }


    @OnClick({R.id.lin_left, R.id.tv_rele_confirm_release})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_rele_confirm_release:
                if (checkReady()) {
                    postRelease();
                }
                break;
            case R.id.lin_left:
               new  ToolUtils(etReleContant,this ).closeInputMethod();
                finish();
                break;
        }
    }

    /**
     * 发布信息
     */
    private void postRelease() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_name", contact);
        params.put("user_phone", phoneNum);
        params.put("community_name", communityName);
        params.put("room", strRoom);
        params.put("office", strHall);
        params.put("kitchen", strKitChen);
        params.put("guard", strGrard);
        params.put("house_floor", floor);//所在楼层
        params.put("floor", floor_total);//总楼层


        params.put("buildsing_name",buildsing_name );//楼号
        params.put("unit", unit);//单元号
        params.put("code", code);//房间号

        params.put("area", area);
        if (type == 1) {//租房
            params.put("unit_price", rentPrice);
            params.put("lease_term", lease_term);//租期
            post_rent = ApiHttpClient.HOUSESLEASEADDDO;

        } else if (type == 2) { //售房
            params.put("unit_price", sellUnitPrice);

            BigDecimal dSell = new BigDecimal(sellPrice);
            BigDecimal totalSell = dSell.multiply(BigDecimal.valueOf(10000));
            params.put("total_price", totalSell + "");
            post_rent = ApiHttpClient.HOUSESADDDO;
        }

        MyOkHttp.get().post(post_rent, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent();
                    intent.putExtra("jump_type", type);
                    setResult(RESULT_OK, intent);
                    finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}
