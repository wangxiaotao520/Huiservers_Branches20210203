package com.huacheng.huiservers.ui.index.houserent;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelHouseDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.tv_select_community_name)
    TextView mTvSelectCommunityName;
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
    @BindView(R.id.rel_payment_type)
    RelativeLayout relPaymentType;
    //支付方式(租房)
    @BindView(R.id.ll_payment_container_rent)
    LinearLayout ll_payment_container_rent;
    @BindView(R.id.ll_yuefu)
    LinearLayout ll_yuefu;
    @BindView(R.id.iv_select_yue)
    ImageView iv_select_yue;
    @BindView(R.id.ll_jifu)
    LinearLayout ll_jifu;
    @BindView(R.id.iv_select_ji)
    ImageView iv_select_ji;
    @BindView(R.id.ll_nianfu)
    LinearLayout ll_nianfu;
    @BindView(R.id.iv_select_nian)
    ImageView iv_select_nian;
    @BindView(R.id.tv_refuse)
    TextView mTvRefuse;
    @BindView(R.id.ly_refuse)
    LinearLayout mLyRefuse;


    private int type = 0;
    private String contact = "";
    private String phoneNum = "";
    private String communityName = "";
    private String communityId = "";

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
    private int paymentStatus = 1;//租房： 1为月付，2为季付，3为年付
    SharePrefrenceUtil mPrefrenceUtil;
    private String tag = "";//判断是已拒绝房屋还是添加房屋
    private String house_id = "";
    ModelHouseDetail data;

    @Override
    protected void initView() {

        mPrefrenceUtil = new SharePrefrenceUtil(this);
        if (type == 1) {
            titleName.setText("发布租房信息");
        } else {
            titleName.setText("发布售房信息");
        }
        if (tag.equals("edit")) {//已拒绝的审核房屋
            mLyRefuse.setVisibility(View.VISIBLE);
        }else {
            mLyRefuse.setVisibility(View.GONE);
        }
//        TextPaint tp = titleName.getPaint();
//        tp.setFakeBoldText(true);
        //如果是小数，位数保留两个，位数限制在7位
        ToolUtils.filterDecimalDigits(etReleArea);
        ToolUtils.filterDecimalDigits(etReleSellUnitPrice);
        ToolUtils.filterDecimalDigits(etReleSentPrice);

        ToolUtils.filterDecimalDigitsText(etReleSellPrice);

        //租房支付方式
        for (int i = 0; i < ll_payment_container_rent.getChildCount(); i++) {
            final int finalI = i;
            ll_payment_container_rent.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_select_yue.setSelected(false);
                    iv_select_ji.setSelected(false);
                    //iv_select_ban_nian.setSelected(false);
                    iv_select_nian.setSelected(false);
                    if (finalI == 0) {
                        paymentStatus = 1;
                        iv_select_yue.setSelected(true);
                    } else if (finalI == 1) {
                        paymentStatus = 2;
                        iv_select_ji.setSelected(true);
                    }/*else if (finalI==2){
                        paymentStatus=3;
                        iv_select_ban_nian.setSelected(true);
                    }*/ else if (finalI == 2) {
                        paymentStatus = 3;
                        iv_select_nian.setSelected(true);
                    }
                }
            });
        }
    }


    @Override
    protected void initData() {
        et_building.requestFocus();
        if (tag.equals("edit")){
            showDialog(smallDialog);
            HashMap<String, String> params = new HashMap<>();
            params.put("id", house_id);
            params.put("property", "1");//除了和昌都需要传这个参数
            params.put("request", "1");//社区慧生活需要这个参数
            MyOkHttp.get().get(ApiHttpClient.GET_HOUST_DETAIL, params, new JsonResponseHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject response) {
                    hideDialog(smallDialog);
                    if (JsonUtil.getInstance().isSuccess(response)) {
                        //  ModelHouseSource data = (ModelHouseSource) JsonUtil.getInstance().parseJsonFromResponse(response, ModelHouseSource.class);
                        ModelHouseDetail mdata = (ModelHouseDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelHouseDetail.class);
                        if (mdata != null) {
                            data = mdata;
                            inflateContent();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, String error_msg) {
                    hideDialog(smallDialog);
                    //  ToastUtils.showShort(SelectCommunityActivity.this, "网络错误，请检查网络设置");
                    SmartToast.showInfo("网络错误，请检查网络设置");
                }
            });
        }

    }

    private void inflateContent() {

        mTvSelectCommunityName.setText(data.getList().getCommunity());
        communityId = data.getList().getCommunity_id();
        et_building.setText(data.getList().getFloor());
        et_unit.setText(data.getList().getUnit());
        et_room.setText(data.getList().getCode());

        etReleContant.setText(data.getList().getName());
        etRelePhoneNum.setText(data.getList().getMobile());

        etReleRoom.setText(data.getList().getRoom());
        etReleHall.setText(data.getList().getOffice());
        etReleKitchen.setText(data.getList().getKitchen());
        etReleGuard.setText(data.getList().getGuard());

        etReleFloor.setText(data.getList().getNumber());
        etReleFloorTotal.setText(data.getList().getNumber_count());
        etReleArea.setText(data.getList().getArea());
        mTvRefuse.setText(data.getList().getCause());

        if (type == 1) {
            //租金
            etReleSentPrice.setText(data.getList().getRent());
            //收租类型
            if ("1".equals(data.getList().getRents_state())) {
                paymentStatus = 1;
                iv_select_yue.setSelected(true);
            } else if ("2".equals(data.getList().getRents_state())) {
                paymentStatus = 2;
                iv_select_ji.setSelected(true);
            } else if ("3".equals(data.getList().getRents_state())) {
                paymentStatus = 3;
                iv_select_nian.setSelected(true);
            }
        } else if (type == 2) {

            etReleSellUnitPrice.setText(data.getList().getHouse_unit());

            String releArea = etReleArea.getText().toString();
            String unitPrice = etReleSellUnitPrice.getText().toString();
            if (!StringUtils.isEmpty(releArea) && !StringUtils.isEmpty(unitPrice)) {

                String total_price = data.getList().getSelling();
                if (!StringUtils.isEmpty(total_price)) {
                    //使用BigDecimal可以防止BigDecimal 丢失两位精度
//                    BigDecimal dSell = new BigDecimal(total_price);
//                    BigDecimal totalSell = dSell.divide(BigDecimal.valueOf(10000));

                    etReleSellPrice.setText(total_price + "");
                } else {
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
                }
            } else {
                etReleSellPrice.setText("");
            }
        }
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
        tag = getIntent().getStringExtra("tag");
        if (tag.equals("edit")) {
            house_id = getIntent().getStringExtra("house_id");
        }
        if (type == 1) {
            rlContainerSentPrice.setVisibility(View.VISIBLE);
            llContainerSell.setVisibility(View.GONE);
            relPaymentType.setVisibility(View.VISIBLE);

        } else if (type == 2) {
            llContainerSell.setVisibility(View.VISIBLE);
            rlContainerSentPrice.setVisibility(View.GONE);
            relPaymentType.setVisibility(View.GONE);
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
        communityName = mTvSelectCommunityName.getText().toString();

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


        if (StringUtils.isEmpty(contact)) {
            SmartToast.showInfo("请输入业主姓名");
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


    @OnClick({R.id.lin_left, R.id.tv_rele_confirm_release, R.id.tv_select_community_name})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_rele_confirm_release:
                if (checkReady()) {
                    postRelease();
                }
                break;
            case R.id.lin_left:
                new ToolUtils(etReleContant, this).closeInputMethod();
                finish();
                break;
            case R.id.tv_select_community_name://选择小区
                if (tag.equals("edit")){

                }else {
                    new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean isGranted) throws Exception {
                                    if (isGranted) {
                                        //权限同意 ,开始定位
                                        Intent intent1 = new Intent(mContext, CommunityListActivity.class);
                                        intent1.putExtra("jump_type", 3);
                                        startActivityForResult(intent1, 111);

                                    } else {
                                        //权限拒绝
                                    }
                                }
                            });
                    break;
                }

        }
    }

    /**
     * 发布信息
     */
    private void postRelease() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("property", "1");//为了区分是哪个平台用的接口\
        if (tag .equals("edit")) {
            params.put("id", house_id);
        }
        params.put("community", communityName);
        params.put("community_id", communityId);

        params.put("floor", buildsing_name + "");
        params.put("unit", unit + "");
        params.put("code", code + "");

        params.put("name", contact);
        params.put("mobile", phoneNum);

        params.put("room", strRoom);
        params.put("office", strHall);
        params.put("kitchen", strKitChen);
        params.put("guard", strGrard);

        params.put("number", floor);
        params.put("number_count", floor_total);

        params.put("area", area);
        params.put("add_state", "2");//添加方式  1和昌   2社区慧生活 3小和管家 4物业
        params.put("add_id", BaseApplication.getUser().getUid() + "");
        params.put("add_name", BaseApplication.getUser().getNickname() + "");
        params.put("add_mobile", BaseApplication.getUser().getUsername() + "");
        params.put("state", type + "");

        if (type == 1) {//租房
            params.put("rent", rentPrice);
            params.put("rents_state", paymentStatus + "");
            // post_rent = ApiHttpClient.HOUSESLEASEADDDO;

        } else if (type == 2) { //售房
            params.put("house_unit", sellUnitPrice);

            BigDecimal dSell = new BigDecimal(sellPrice);
            BigDecimal totalSell = dSell.multiply(BigDecimal.valueOf(10000));
            params.put("selling", totalSell + "");
            //  post_rent = ApiHttpClient.HOUSESADDDO;
        }

        MyOkHttp.get().post(ApiHttpClient.ADD_HOUST, params, new JsonResponseHandler() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                if (data != null) {
                    communityName = data.getStringExtra("name");
                    mTvSelectCommunityName.setText(communityName);
                    communityId = "";//没有小区id

                }
            }
        }
    }

}
