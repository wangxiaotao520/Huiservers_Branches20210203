package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.shop.ConfirmOrderActivityNew;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.ui.shop.bean.SubmitOrderBean;
import com.huacheng.huiservers.ui.shop.bean.XGBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.widget.FlowTag.FlowTagLayout;
import com.huacheng.huiservers.view.widget.FlowTag.OnTagSelectListener;
import com.huacheng.huiservers.view.widget.FlowTag.TagAdapter;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddShopDialog extends Dialog implements OnClickListener {
    private FlowTagLayout mSizeFlowTagLayout;
    private TagAdapter<String> mSizeTagAdapter;
    private ImageView back;
    private Context context;
    private String id, morenPrice, tagname, tagid, unit, invenyory, strtag;
    private ImageView img_fu_jian, img_zheng_jia;
    private TextView txt_btn, txt_price, txt_yuan_price;
    ShopProtocol protocol = new ShopProtocol();
    private Boolean isbool = true;
    EditText edit_num;
    TextView txt_inv, txt_title;
    ShopDetailBean detailBean = new ShopDetailBean();
    TextView txt_shop_num;
    SharePrefrenceUtil prefrenceUtil;
    ShopDetailBean cartnum = new ShopDetailBean();
    PriorityListener listener;
    private SimpleDraweeView img_title;

    public interface OnCustomDialogListener {
        public void back(String name);
    }

    List<ShopDetailBean> beans = new ArrayList<ShopDetailBean>();
    //XGBean xgBean = new XGBean();
    XGBean CxgBean;
    private  int type= 0;//0 是+ ;1.立即购买 2.加入购物车


    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(String string);
    }

    private SmallDialog smallDialog = null;

    public AddShopDialog(Context context, String id, TextView txt_shop_num,
                         List<ShopDetailBean> beans,
                         ShopDetailBean detailBean, String strtag, PriorityListener listener) {
        super(context, R.style.Dialog_down);
        this.context = context;
        this.id = id;
        //this.CxgBean = CxgBean;
        this.txt_shop_num = txt_shop_num;
        this.beans = beans;
        this.detailBean = detailBean;
        this.strtag = strtag;

        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smallDialog = new SmallDialog(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_shop);
        prefrenceUtil = new SharePrefrenceUtil(context);
        back = findViewById(R.id.back);//取消
        txt_title = (TextView) findViewById(R.id.txt_title);//名称
        txt_inv = (TextView) findViewById(R.id.txt_inv);//库存
        img_title =  findViewById(R.id.img_title);//头图
        img_fu_jian = (ImageView) findViewById(R.id.img_fu_jian);//减号
        img_zheng_jia = (ImageView) findViewById(R.id.img_zheng_jia);//加号
        edit_num = (EditText) findViewById(R.id.edit_num);//选择数量
        edit_num.setOnClickListener(this);
        edit_num.setEnabled(false);
        /*edit_num.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        edit_num.setOnClickListener(this);*/
        txt_btn = (TextView) findViewById(R.id.txt_btn);//确定
        txt_price = (TextView) findViewById(R.id.txt_price);//价格
        txt_yuan_price = (TextView) findViewById(R.id.txt_yuan_price);
        txt_yuan_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
        back.setOnClickListener(this);
        txt_btn.setOnClickListener(this);
        img_fu_jian.setOnClickListener(this);
        img_zheng_jia.setOnClickListener(this);

        //尺寸
        mSizeFlowTagLayout = (FlowTagLayout) findViewById(R.id.size_flow_layout);
        mSizeTagAdapter = new TagAdapter<String>(context);
        mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
        mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);

        initTag();
        mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {

            @Override
            public void onItemSelect(FlowTagLayout parent,
                                     List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                        morenPrice = beans.get(i).getPrice();
                        tagid = beans.get(i).getId();
                        tagname = beans.get(i).getTagname();
                        listener.refreshPriorityUI(beans.get(i).getTagname());

                        unit = beans.get(i).getUnit();
                        invenyory = beans.get(i).getInventory();
                        txt_title.setText("已选：" + beans.get(i).getTagname());
                        txt_inv.setText("库存：" + invenyory + beans.get(i).getUnit());
                        if (invenyory.equals("0")) {
                            edit_num.setText("0");
                        }else {
                            edit_num.setText("1");
                        }
                        if (beans.get(i).getUnit().equals("")) {
                            txt_price.setText("¥" + morenPrice);//根据显示不同的标签价格会发生相应的变化
                            txt_yuan_price.setText("¥" + beans.get(i).getOriginal());
                        } else {
                            txt_price.setText("¥" + morenPrice + "/" + unit);//根据显示不同的标签价格会发生相应的变化
                            txt_yuan_price.setText("¥" + beans.get(i).getOriginal());
                        }
                    }
                    //    getXG();//获取限购商品数量

                    isbool = true;
                } else {
                    isbool = false;
                    txt_title.setText("已选：");
                    //没有选中便签 商品信息不展示
                    //Toast.makeText(context, "没有选择标签", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initSizeData();
        if (detailBean.getTitle_img() == null) {

        } else {
            FrescoUtils.getInstance().setImageUri(img_title,MyCookieStore.URL + detailBean.getTitle_img());
        }
        setCanceledOnTouchOutside(true);//
        getWindow().setGravity(Gravity.BOTTOM); //此处可以设置dialog显示的位置
        getWindow().setWindowAnimations(R.style.take_photo_anim);  //添加动画
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().getDecorView().setPadding(0, 0, 0, 0); //就能够水平占满了
        lp.width = (int) (display.getWidth()); //设置宽度
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setCanceledOnTouchOutside(false);
        edit_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(edit_num.getText().toString())) {
                    edit_num.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    /**
     * 初始化标签
     */
    private void initTag() {
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getTagname().equals(detailBean.getTagname())) {
                beans.get(i).setSelect(true);
                if (beans.get(i).getUnit().equals("")) {
                    txt_price.setText("¥" + beans.get(i).getPrice());//根据显示不同的标签价格会发生相应的变化
                    txt_yuan_price.setText("¥" + beans.get(i).getOriginal());

                } else {
                    txt_price.setText("¥" + beans.get(i).getPrice() + "/" + beans.get(i).getUnit());//根据显示不同的标签价格会发生相应的变化
                    txt_yuan_price.setText("¥" + beans.get(i).getOriginal());
                }
                txt_title.setText("已选：" + beans.get(i).getTagname());
                morenPrice = beans.get(i).getPrice();
                //txt_bianhao.setText("产品编号："+beans.get(i).getId());//显示产品编号
                tagid = beans.get(i).getId();//显示一个标签
                tagname = beans.get(i).getTagname();
                listener.refreshPriorityUI(beans.get(i).getTagname());
                invenyory = beans.get(i).getInventory();
                txt_title.setText("已选：" + beans.get(i).getTagname());
                txt_inv.setText("库存：" + beans.get(i).getInventory() + beans.get(i).getUnit());
                if ("0".equals(beans.get(i).getInventory())) {
                    edit_num.setText("0");
                }else {
                    edit_num.setText("1");
                }
            } else {
                beans.get(i).setSelect(false);
            }

        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.back://取消
                AddShopDialog.this.dismiss();
                break;
            case R.id.edit_num:
                edit_num.setFocusableInTouchMode(true);
                break;
            case R.id.img_fu_jian://减

                if (Integer.valueOf(edit_num.getText().toString()) <= Integer.valueOf("1")) {

                } else {
                    edit_num.setText(Integer.valueOf(edit_num.getText().toString()) - 1 + "");
                }
                break;
            case R.id.img_zheng_jia://加
                if (StringUtils.isEmpty(invenyory)) {
                    invenyory = "0";
                }
                if (("0").equals(invenyory)) {
                    edit_num.setText("0");
                    SmartToast.showInfo("已售罄");
                } else {
                    if (Integer.valueOf(invenyory) <= (Integer.valueOf(edit_num.getText().toString().trim()))) {
                        SmartToast.showInfo("已售罄");
                    } else {
                        type=0;
                        getShopLimit();
                    }
                }
                break;
            case R.id.txt_btn://加入购物车,立即购买
                if (isbool == true) {
                    if ("0".equals(invenyory)) {
                        SmartToast.showInfo("已售罄");
                    } else {
                        if (Integer.valueOf(invenyory) < (Integer.valueOf(edit_num.getText().toString().trim()))) {
                            SmartToast.showInfo("已售罄");
                        } else {

                            if (strtag.equals("1")) {//tag值为1是立即购买
                                type=1;
                            } else if (strtag.equals("2")) {//tag值为2是加入购物车
                                type=2;
                            }
                            getShopLimit();
                        }
                    }
                } else {
                    SmartToast.showInfo("请选择产品类型");
                }
                break;
            default:
                break;
        }

    }

    float totalPrice = (float) 0; //商品总价
    List<SubmitOrderBean> pro = new ArrayList<SubmitOrderBean>();
    String strExist_hours;

    private void getSubmiter() {//直接购买确认订单数据
        SubmitOrderBean info = new SubmitOrderBean();
        info.setTagid(tagid);
        info.setP_id(id);
        info.setP_title(detailBean.getTitle());
        info.setP_title_img(detailBean.getTitle_img());
        info.setPrice(String.valueOf(morenPrice));
        info.setNumber(String.valueOf(edit_num.getText().toString()));
        strExist_hours = detailBean.getExist_hours();
        pro.add(info);
        totalPrice += Float.parseFloat(edit_num.getText().toString()) * Float.parseFloat(morenPrice);
        setFloat();
        getex();//判断商品是否在派送范围时间
    }

    private void getex() {//判断商品是否在派送范围时间
        if (strExist_hours.equals("2")) {
            SmartToast.showInfo("当前时间不在派送时间范围内");
        } else {
            Intent intent = new Intent(context, ConfirmOrderActivityNew.class);
            Bundle bundle = new Bundle();
            bundle.putString("all", totalPrice + "");
            bundle.putSerializable("pro", (Serializable) pro);
            intent.putExtras(bundle);
            context.startActivity(intent);
            AddShopDialog.this.dismiss();
        }
    }

    private void getAddShop() {//加入购物车
        if (smallDialog != null&&!smallDialog.isShowing()) {
            smallDialog.show();
        }
        Url_info info = new Url_info(context);
        RequestParams params = new RequestParams();
        params.addBodyParameter("p_id", id);
        params.addBodyParameter("p_title", detailBean.getTitle());
        params.addBodyParameter("tagid", tagid);
        params.addBodyParameter("p_title_img", detailBean.getTitle_img());
        params.addBodyParameter("price", morenPrice);
        //params.addBodyParameter("inventory", invenyory);
        params.addBodyParameter("number", edit_num.getText().toString());
        params.addBodyParameter("tagname", tagname);

        HttpHelper hh = new HttpHelper(info.add_shop_cart, params, context) {

            @Override
            protected void setData(String json) {
                str = protocol.setShop(json);
                if (smallDialog != null) {
                    smallDialog.dismiss();
                }
                if ("1".equals(str)) {
                    SmartToast.showInfo("加入购物车成功");
                    AddShopDialog.this.dismiss();
                    getCartNum();
                } else {
                    SmartToast.showInfo(str);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                if (smallDialog != null) {
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    private void getCartNum() {//购物车商品数量
        Url_info info = new Url_info(context);
        RequestParams params = new RequestParams();
//        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
//            params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
//        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }
        HttpHelper hh = new HttpHelper(info.cart_num, params, context) {

            @Override
            protected void setData(String json) {
                cartnum = protocol.getCartNum(json);
                if (cartnum.getCart_num().equals("0")) {
                    txt_shop_num.setVisibility(View.GONE);
                } else {
                    txt_shop_num.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    /**
     * 获取商品限购数量(0.4.1)
     * wangxiaotao
     */
    private void getShopLimit() {
        if (smallDialog!=null){
            smallDialog.show();
        }
        HashMap<String ,String> params = new HashMap<String,String>();
        params.put("p_id", id);
        params.put("tagid", tagid);
        String num = edit_num.getText().toString().trim();
        if (type==0){//+1
            int num_int = Integer.parseInt(num);
            params.put("num",(num_int+1)+"");
        }else {
            params.put("num",num);
        }
        MyOkHttp.get().post(Url_info.shop_limit, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    // 允许购买
                    if (type==0){
                        if (smallDialog!=null){
                            smallDialog.dismiss();
                        }
                        String num = edit_num.getText().toString().trim();
                        int num_int = Integer.parseInt(num);
                        edit_num.setText((num_int+1)+"");
                    }else if (type==1){
                        //立即购买
                        if (smallDialog!=null){
                            smallDialog.dismiss();
                        }
                        getSubmiter();
                    }else if (type==2){//加入购物车
                        getAddShop();
                    }

                }else {
                    if (smallDialog!=null){
                        smallDialog.dismiss();
                    }
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"超出限购");
                    SmartToast.showInfo(msg);
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }


    /**
     * 初始化数据
     */
    private void initSizeData() {
        List<String> dataSource = new ArrayList<String>();
        for (int i = 0; i < beans.size(); i++) {
            dataSource.add(beans.get(i).getTagname());
        }
        mSizeTagAdapter.onlyAddAll(dataSource);

    }


    private void setFloat() {//保留两位小数点
        BigDecimal b = new BigDecimal(totalPrice);
        totalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
