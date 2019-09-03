package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.XiaoquActivity;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.GerenProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.geren.bean.AddressBean;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类：新增收货地址
 * 时间：2017/10/14 15:11
 * 功能描述:Huiservers
 */
public class NewAddAddressActivity extends BaseActivityOld {
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_menpai)
    EditText mEdtMenpai;
    @BindView(R.id.edt_mobile)
    EditText mEdtMobile;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.right)
    TextView mRight;
    @BindView(R.id.txt_address)
    TextView mTxtAddress;

    private String id, tag, xiaoqu_id, a_id, a_name, xiaoqu_name;
    private GerenProtocol protocol = new GerenProtocol();
    private AddressBean beans = new AddressBean();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_add_address);
        ButterKnife.bind(this);
        //      SetTransStatus.GetStatus(this);//系统栏默认为黑色
        TextView mTitleName = (TextView) findViewById(R.id.title_name);
        TextView mRight = (TextView) findViewById(R.id.right);
        mRight.setTextColor(getResources().getColor(R.color.colorPrimary));
        mRight.setVisibility(View.VISIBLE);
        mRight.setText("保存");

        id = getIntent().getExtras().getString("id");
        tag = getIntent().getExtras().getString("tag");

        if (tag.equals("edit")) {
            mTitleName.setText("修改地址");
            getDetail();
        } else {
            mTitleName.setText("添加地址");
        }
        mRight.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mEdtName.getText().toString().equals("")) {
                    SmartToast.showInfo("请输入联系人");
                } else if (mTxtAddress.getText().toString().equals("")) {
                    SmartToast.showInfo("请选择您所在的小区");
                } else if (mEdtMenpai.getText().toString().equals("")) {
                    SmartToast.showInfo("请输入您的详细地址");
                } else if (mEdtMobile.getText().toString().equals("")) {
                    SmartToast.showInfo("请输入联系电话");
                } else if (!ToolUtils.isMobileNO(mEdtMobile.getText().toString())) {
                    SmartToast.showInfo("请输入正确的手机号");
                } else {
                    getdata();
                    NewAddAddressActivity.this.mRight.setClickable(false);
                }
            }
        });
    }

    @OnClick({R.id.lin_left, R.id.right, R.id.txt_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                UIUtils.closeInputMethod(this, mEdtName);
                finish();
                break;
            /*case R.id.right://保存

                break;*/
            case R.id.txt_address://选择小区
                Intent intent = new Intent(NewAddAddressActivity.this, XiaoquActivity.class);
                startActivityForResult(intent, 3);

                break;
        }
    }

    private void getdata() {// 添加修改
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        System.out.println("^^^^^^^^^^" + tag);
        RequestParams params = new RequestParams();
        params.addBodyParameter("region_id", "");
        params.addBodyParameter("region_cn", "");
        params.addBodyParameter("community_id", xiaoqu_id);
        params.addBodyParameter("community_cn", xiaoqu_name);
        params.addBodyParameter("consignee_name", mEdtName.getText().toString());
        params.addBodyParameter("consignee_mobile", mEdtMobile.getText().toString());
        params.addBodyParameter("doorplate", mEdtMenpai.getText().toString());
        System.out.println("consignee_name-----" + mEdtName.getText().toString());
        if (tag.equals("edit")) {
            params.addBodyParameter("id", id);
        }
        HttpHelper hh = new HttpHelper(info.add_user_address, params, NewAddAddressActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                str = protocol.addSuss(json);
                if (str.equals("1")) {
                    UIUtils.closeInputMethod(NewAddAddressActivity.this, mEdtName);
                    SmartToast.showInfo("成功");
                    finish();
                } else {
                    mRight.setClickable(true);
                    SmartToast.showInfo(str);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    private void getDetail() {// 修改获取详情
        showDialog(smallDialog);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        HttpHelper hh = new HttpHelper(MyCookieStore.SERVERADDRESS + "userCenter/address_details/", params, NewAddAddressActivity.this) {
            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    String msg = jsonObject.getString("msg");
                    if ("1".equals(status)) {
                        beans = protocol.addressDetail(json);
                        mEdtName.setText(beans.getConsignee_name());
                        mEdtMobile.setText(beans.getConsignee_mobile());
                        mTxtAddress.setText(beans.getRegion_cn() + beans.getCommunity_cn());
                        mEdtMenpai.setText(beans.getDoorplate());
                        a_id = beans.getRegion_id();
                        a_name = beans.getRegion_cn();
                        xiaoqu_id = beans.getCommunity_id();
                        xiaoqu_name = beans.getCommunity_cn();
                    } else {
                        SmartToast.showInfo(msg + "");
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name = data.getExtras().getString("name");
        String id = data.getExtras().getString("id");
        String all_id = data.getExtras().getString("all_id");
        String all_name = data.getExtras().getString("all_name");
        System.out.println("allid====" + all_id);
        System.out.println("allname====" + all_name);
        System.out.println("id====" + id);
        System.out.println("name====" + name);
        switch (resultCode) {
            case 1111:
                break;

            case 100:// 小区返回值
                mTxtAddress.setText(name);
                xiaoqu_id = id;
                xiaoqu_name = name;
                a_name = all_name;
                a_id = all_id;

                break;
            case 200:
                mTxtAddress.setText(name);
                xiaoqu_id = id;
                xiaoqu_name = name;
                a_name = all_name;
                a_id = all_id;
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
