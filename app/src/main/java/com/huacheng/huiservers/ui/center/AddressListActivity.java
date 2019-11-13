package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.DeleteAddressDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.GerenProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.adapter.NewAddressAdapter;
import com.huacheng.huiservers.ui.center.geren.bean.GerenBean;
import com.huacheng.huiservers.ui.servicenew.ui.ServiceConfirmOrderActivity;
import com.huacheng.huiservers.ui.shop.ConfirmOrderActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.huacheng.huiservers.R.id.list_address;

/**
 * 类：选择收货地址
 * 时间：2017/10/14 15:11
 * 功能描述:Huiservers
 */
public class AddressListActivity extends BaseActivityOld {
    @BindView(R.id.lin_btn)
    LinearLayout mLinBtn;
    @BindView(R.id.left)
    ImageView mLeft;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(list_address)
    ListView mListAddress;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;
    private String edit_id, person_address, person_name, person_mobile;
    private String address, tag_id, type;
    private GerenProtocol protocol = new GerenProtocol();
    GerenBean beans = new GerenBean();
    ShopProtocol shopprotocol = new ShopProtocol();
    NewAddressAdapter new_addressAdapter;
    List<String> ids;
    SharePrefrenceUtil prefrenceUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address);
        ButterKnife.bind(this);
        prefrenceUtil = new SharePrefrenceUtil(this);
        TextView mRight = (TextView) findViewById(R.id.right);
        mRight.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        mRight.setText("添加");
        mRight.setVisibility(View.VISIBLE);
        //       SetTransStatus.GetStatus(this);//系统栏默认为黑色
        address = this.getIntent().getExtras().getString("address");
        type = this.getIntent().getExtras().getString("type");
        title_name.setText("收货地址");
        /*String itag = this.getIntent().getExtras().getString("itag");
        if(!StringUtils.isEmpty(itag)){
            title_name.setText("收货地址");
        }else{
            title_name.setText("我的地址");
        }*/
        if (address.equals("shopyes")) {
            // lin_peisong.setVisibility(View.VISIBLE);
            ids = this.getIntent().getExtras().getStringArrayList("list_id");
            //shop_id=this.getIntent().getExtras().getString("shop_id");
            StringBuilder sbs = new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                if (i == 0) {
                    sbs.append(String.valueOf(ids.get(i)));
                } else {
                    sbs.append("," + String.valueOf(ids.get(i)));
                }
            }
            tag_id = new String(sbs);
        }
        getdata();
        listonclick();
        mListAddress.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                    final String addressid = beans.getCom_list().get(i).getId();
                    DeleteAddressDialog deleteAddressDialog = new DeleteAddressDialog(AddressListActivity.this, new DeleteAddressDialog.OnCustomDialogListener() {
                        @Override
                        public void back(String name) {
                            if (name.equals("1")) {//编辑成功"
                                Intent intent = new Intent(AddressListActivity.this, NewAddAddressActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("id", addressid);
                                bundle.putString("tag", "edit");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else if (name.equals("2")) {
                                getdelete(addressid, i);

                            }
                        }
                    });
                    deleteAddressDialog.show();
                    return true;
                }
            });

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, NewAddAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "");
                bundle.putString("tag", "add");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata();
    }

    private void listonclick() {
        mListAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                edit_id = beans.getCom_list().get(arg2).getId();
                person_name = beans.getCom_list().get(arg2).getConsignee_name();
                person_mobile = beans.getCom_list().get(arg2).getConsignee_mobile();
                String str = beans.getCom_list().get(arg2).getRegion_cn().replaceAll(",", "");
                person_address = str + beans.getCom_list().get(arg2).getCommunity_cn()
                        + beans.getCom_list().get(arg2).getDoorplate();

                if ("shopyes".equals(address)) {
                    //判断当前地址是否可选
                    if (beans.getCom_list().get(arg2).getIs_select().equals("0")) {
                        SmartToast.showInfo("您选择的地址不在当前小区配送范围内，请返回APP首页选择和您地址相符合的小区");
                    } else {
                        Intent intent = new Intent(AddressListActivity.this, ConfirmOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("person_name", person_name);
                        bundle.putString("person_mobile", person_mobile);
                        bundle.putString("address", person_address);
                        bundle.putString("address_id", edit_id);
                        intent.putExtras(bundle);
                        setResult(200, intent);
                        finish();
                    }
                }else if ("serviceyes".equals(address)){
                    Intent intent = new Intent(AddressListActivity.this, ServiceConfirmOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("person_name", person_name);
                    bundle.putString("person_mobile", person_mobile);
                    bundle.putString("address", person_address);
                    bundle.putString("address_id", edit_id);
                    intent.putExtras(bundle);
                    setResult(200, intent);
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.lin_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
        }
    }

    private void getdata() {//地址列表
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        if (address.equals("shopyes")) {
            params.addBodyParameter("m_id_arr", tag_id);
        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        }
        HttpHelper hh = new HttpHelper(info.get_user_address, params, AddressListActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                beans = protocol.addressList(json);
                System.out.println("-=---" + beans);
                if (beans.getCommunity_cn() != null) {
                    mRelNoData.setVisibility(View.GONE);
                    new_addressAdapter = new NewAddressAdapter(AddressListActivity.this, beans.getCom_list(), address);
                    mListAddress.setAdapter(new_addressAdapter);
                   /* adapter=new AddressListAdapter(AddressListActivity.this,beans.getCom_list(),address);
                    list_address.setAdapter(adapter);*/
                } else {
                    mRelNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    private void getdelete(String id, final int arg0) {//删除地址
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        System.out.println("p_id====" + id);
        showDialog(smallDialog);
        HttpHelper hh = new HttpHelper(info.del_user_address, params, AddressListActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                str = shopprotocol.setShop(json);
                if (str.equals("1")) {
                    beans.getCom_list().remove(arg0);
                    new_addressAdapter.notifyDataSetChanged();
                    SmartToast.showInfo("删除成功");
                } else {
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

}
