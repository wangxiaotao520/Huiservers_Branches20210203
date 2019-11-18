package com.huacheng.huiservers.ui.index.property;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.bean.HouseBean;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.ui.center.house.HouseInviteActivity;
import com.huacheng.huiservers.ui.index.openDoor.OpenLanActivity;
import com.huacheng.huiservers.ui.index.property.adapter.NewPropertyAdapter;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：新增缴费
 * 时间：2018/8/4 15:49
 * created by DFF
 */
public class PropertyNewActivity extends BaseActivity implements NewPropertyAdapter.OnDeleteClickListener {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.ry_add)
    RelativeLayout mRyAdd;
    NewPropertyAdapter propertyAdapter;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.lin_right)
    LinearLayout mLinRight;
    @BindView(R.id.list)
    MyListView mList;

    List<HouseBean> mdatas = new ArrayList<>();
    private String wuye_type = "";

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleName.setText("我的房屋");
        mIvRight.setBackground(getResources().getDrawable(R.drawable.ic_order5));
        mLinRight.setVisibility(View.VISIBLE);
        propertyAdapter = new NewPropertyAdapter(this, mdatas, this);
        mList.setAdapter(propertyAdapter);

    }

    @Override
    protected void initData() {
        getHouseList();

    }

    private void getHouseList() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(info.binding_community, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<HouseBean> mlist = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseBean.class);
                    if (mlist != null && mlist.size() > 0) {
                        mList.setVisibility(View.VISIBLE);
                        mdatas.clear();
                        mdatas.addAll(mlist);
                        propertyAdapter.notifyDataSetChanged();
                    } else {
                        mdatas.clear();
                        mList.setVisibility(View.GONE);
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

    @Override
    protected void initListener() {

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("property".equals(wuye_type)) {
                    Intent intent;
                    if (mdatas.get(position).getIs_ym().equals("0")) {
                        intent = new Intent(PropertyNewActivity.this, PropertyHomeListActivity.class);
                    } else {
                        intent = new Intent(PropertyNewActivity.this, PropertyHomeNewJFActivity.class);
                    }
                    intent.putExtra("room_id", mdatas.get(position).getRoom_id());
                    intent.putExtra("company_id", mdatas.get(position).getCompany_id());
                    startActivity(intent);
                } else if ("open_door".equals(wuye_type)) {
                    //一键开门
                    Intent intent;
                    intent = new Intent(mContext, OpenLanActivity.class);
                    intent.putExtra("room_id", mdatas.get(position).getRoom_id());
                    startActivity(intent);
                } else if ("house_invite".equals(wuye_type)) {
                    //访客邀请
                    checkHouseInvite(mdatas.get(position).getRoom_id());
                }
            }
        });
    }

    /**
     * 访客邀请权限
     */
    private void checkHouseInvite(final String room_id) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("room_id", room_id);
        HttpHelper hh = new HttpHelper(info.checkIsAjb, params, this) {


            @Override
            protected void setData(String json) {
                JSONObject jsonObject, jsonData;
                hideDialog(smallDialog);
                try {
                    jsonObject = new JSONObject(json);
                    String data = jsonObject.getString("data");
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        jsonData = new JSONObject(data);
                        String mobile = jsonData.getString("mobile");
                        String community = jsonData.getString("community");
                        String building = jsonData.getString("building");
                        String room_code = jsonData.getString("room_code");
                        Intent intent = new Intent(PropertyNewActivity.this, HouseInviteActivity.class);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("community", community);
                        intent.putExtra("building", building);
                        intent.putExtra("room_code", room_code);
                        intent.putExtra("room_id", room_id);
                        startActivity(intent);
                    } else {
                        SmartToast.showInfo(jsonObject.getString("msg"));
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
    protected int getLayoutId() {
        return R.layout.new_property;
    }

    @Override
    protected void initIntentData() {
        Intent intent = getIntent();
        wuye_type = intent.getStringExtra("wuye_type");
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
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //绑定成功刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BDCallback(HouseBean info) {
        HouseBean houseBean = new HouseBean();
        if (info != null) {
            if ("public_repair".equals(info.getWuye_type()) || "person_repair".equals(info.getWuye_type()) || "bind".equals(info.getWuye_type())) {
                finish();
            } else {
//                houseBean.setAddress(info.getAddress() + "");
//                houseBean.setCommunity_name(info.getCommunity_name());
//                houseBean.setCommunity_id(info.getCommunity_id());
//                houseBean.setIs_ym(info.getIs_ym());
//                houseBean.setRoom_id(info.getRoom_id());
//                boolean is_contain = false;
//                for (int i = 0; i < mdatas.size(); i++) {
//                    String community_id = mdatas.get(i).getCommunity_id();
//                    if (community_id.equals(houseBean.getCommunity_id())) {
//                        is_contain = true;
//                    }
//                }
//                if (!is_contain) {
//                    mdatas.add(0, houseBean);
//
//                }
//                propertyAdapter.notifyDataSetChanged();
                getHouseList();
            }
        }
    }

    @OnClick({R.id.lin_left, R.id.iv_right, R.id.ry_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.iv_right:

                startActivity(new Intent(PropertyNewActivity.this, PropertyPaymentActivity.class));

                break;
            case R.id.ry_add:
                Intent intent = new Intent();
                //    startActivity(new Intent(PropertyNewActivity.this, PropertyMyHomeActivity.class));
                intent = new Intent(PropertyNewActivity.this, PropertyBindHomeActivity.class);
                intent.putExtra("wuye_type", wuye_type + "");
                startActivity(intent);
                break;
        }
    }

    /**
     * 解除绑定
     *
     * @param item
     */
    String delete_id = "";

    @Override
    public void onDeleteClick(HouseBean item) {
        if (item != null) {
            delete_id = item.getId();
            new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确认解除已绑定的房屋？", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        getDelete(delete_id);
                        dialog.dismiss();
                    }
                }
            }).show();
        }
    }

    private void getDelete(final String id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.UNSETBINDING, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    HouseBean houseBean = null;
                    for (int i = 0; i < mdatas.size(); i++) {
                        if (mdatas.get(i).getId().equals(id)) {
                            houseBean= mdatas.get(i);
                        }
                    }
                    if (houseBean!=null){
                        mdatas.remove(houseBean);
                    }
                    propertyAdapter.notifyDataSetChanged();
                    if (mdatas.size()==0){
                        //刷新个人中心
                        EventBus.getDefault().post(new PersoninfoBean());
                    }
                }else {
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

}
