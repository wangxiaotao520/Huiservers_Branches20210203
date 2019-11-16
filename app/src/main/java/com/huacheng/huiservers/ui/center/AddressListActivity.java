package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.adapter.NewAddressAdapter;
import com.huacheng.huiservers.ui.center.bean.ModelAddressList;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AddressListActivity extends BaseActivityOld implements NewAddressAdapter.OnClickBianjiListener {
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

    ShopProtocol shopprotocol = new ShopProtocol();
    NewAddressAdapter new_addressAdapter;
    List<ModelAddressList> mDatas = new ArrayList<>();


    SharePrefrenceUtil prefrenceUtil;
    private int jump_type = 1;//1.从商城跳过来的 2.从服务调过来的
    private String service_id = "";//商户id

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address);
        ButterKnife.bind(this);
        prefrenceUtil = new SharePrefrenceUtil(this);

        this.jump_type= this.getIntent().getIntExtra("jump_type",1);
        if (jump_type==2){
            service_id=this.getIntent().getStringExtra("service_id")+"";
        }
        TextView mRight = (TextView) findViewById(R.id.right);
        mRight.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        mRight.setText("添加");
        mRight.setVisibility(View.VISIBLE);
        title_name.setText("收货地址");
        new_addressAdapter = new NewAddressAdapter(AddressListActivity.this, mDatas,this);
        mListAddress.setAdapter(new_addressAdapter);
        listonclick();
        mListAddress.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
//                    DeleteAddressDialog deleteAddressDialog = new DeleteAddressDialog(AddressListActivity.this, new DeleteAddressDialog.OnCustomDialogListener() {
//                        @Override
//                        public void back(String name) {
//                            if (name.equals("1")) {//编辑成功"
//
//                            } else if (name.equals("2")) {
//                                String id = mDatas.get(i).getId();
//                                getdelete(id, i);
//
//                            }
//                        }
//                    });
//                    deleteAddressDialog.show();
                    return true;
                }
            });

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                intent.putExtra("jump_type",jump_type);
                if (jump_type==2){
                    intent.putExtra("service_id",service_id);
                }
                startActivityForResult(intent,111);
            }
        });
        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void listonclick() {
        mListAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                if (mDatas.get(arg2).getIs_do()==0) {//0是不可用
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("model",mDatas.get(arg2));
                setResult(RESULT_OK,intent);
                finish();
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



    /**
     * 请求接口
     */
    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(service_id)){
            params.put("service_id",service_id);
        }
        MyOkHttp.get().post( Url_info.get_user_address, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelAddressList> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelAddressList.class);
                   if (data!=null&&data.size()>0){
                       mRelNoData.setVisibility(View.GONE);
                       mDatas.clear();
                       mDatas.addAll(data);
                       new_addressAdapter.notifyDataSetChanged();
                   }else {
                       mRelNoData.setVisibility(View.VISIBLE);
                   }


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

    private void getdelete(String id, final int arg0) {//删除地址
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        showDialog(smallDialog);
        HttpHelper hh = new HttpHelper(info.del_user_address, params, AddressListActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                str = shopprotocol.setShop(json);
                if (str.equals("1")) {
                    mDatas.remove(arg0);
                    new_addressAdapter.notifyDataSetChanged();
                    if (mDatas.size()==0){
                        mRelNoData.setVisibility(View.VISIBLE);
                    }
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

    @Override
    public void onClickBianji(int position) {
        Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
        ModelAddressList modelAddressList = mDatas.get(position);
        intent.putExtra("model",modelAddressList);
        intent.putExtra("jump_type",jump_type);
        if (jump_type==2){
            intent.putExtra("service_id",service_id);
        }
        startActivityForResult(intent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                requestData();
            }
        }
    }
}
