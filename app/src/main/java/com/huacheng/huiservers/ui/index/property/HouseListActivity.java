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
import com.huacheng.huiservers.dialog.PermitDialog;
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
import com.huacheng.huiservers.ui.index.coronavirus.PermitListActivity;
import com.huacheng.huiservers.ui.index.coronavirus.investigate.InvestHistoryListActivity;
import com.huacheng.huiservers.ui.index.coronavirus.investigate.InvestigateActivity;
import com.huacheng.huiservers.ui.index.openDoor.OpenLanActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.AdapterHouseList;
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

/**
 * Description:选择房屋列表
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:46
 */
public class HouseListActivity extends BaseActivity implements AdapterHouseList.OnClickDeleteback {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.right)
    TextView mRight;
    @BindView(R.id.listview)
    MyListView mListview;
    @BindView(R.id.ly_add)
    LinearLayout mLyAdd;
    @BindView(R.id.img_no_data)
    ImageView img_no_data;
    /* @BindView(R.id.refreshLayout)
     SmartRefreshLayout mRefreshLayout;*/
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;
    private int type = 0;//0投诉建议/报修工单 1物业缴费
    private String type_url = "";
    private String wuye_type = "";
    private List<HouseBean> mDatas = new ArrayList<>();
    AdapterHouseList mAdapter;

    String id = ""; //计划id 调查问卷

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (type == 1) {
            mTitleName.setText("我的房屋");
            mRight.setVisibility(View.VISIBLE);
            mRight.setText("缴费记录");
            mRight.setTextColor(getResources().getColor(R.color.orange_bg));
        } else {
            mRight.setVisibility(View.GONE);
            mTitleName.setText("选择房屋");
        }
      /*  mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);*/
        mAdapter = new AdapterHouseList(this, R.layout.item_house_list, mDatas, type, this);
        mListview.setAdapter(mAdapter);

        if ("property".equals(wuye_type)) {
            mRight.setVisibility(View.VISIBLE);
        } else if ("open_door".equals(wuye_type)) {
            //一键开门
            mRight.setVisibility(View.GONE);
        } else if ("house_invite".equals(wuye_type)) {
            //访客邀请
            mRight.setVisibility(View.GONE);
        } else if ("investigate".equals(wuye_type)) {
            //问卷调查
            mRight.setVisibility(View.VISIBLE);
            mRight.setText("历史记录");
            this.id = getIntent().getStringExtra("id");
        } else if ("permit".equals(wuye_type)) {
            //通行证
            mRight.setVisibility(View.GONE);
            mRight.setText("历史记录");
        }
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (type == 1) {
            if ("investigate".equals(wuye_type)) {
                type_url = ApiHttpClient.INVESTIGATE_HOME_LIST;
                //计划id
                params.put("id", id);
            } else {
                type_url = ApiHttpClient.BINDING_COMMUNITY;
            }

        } else {
            type_url = ApiHttpClient.GET_WORK_HOUSE_ADDRESS;
        }
        MyOkHttp.get().post(type_url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<HouseBean> data = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseBean.class);
                    mDatas.clear();
                    mDatas.addAll(data);
                    mAdapter.notifyDataSetChanged();
                    if (type == 0) {
                        mLyAdd.setVisibility(View.GONE);
                        if (mDatas.size() == 0) {
                            //  mRelNoData.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.VISIBLE);
                        } else {
                            img_no_data.setVisibility(View.GONE);
                        }
                    } else {
                        mLyAdd.setVisibility(View.VISIBLE);
                        if (mDatas.size() == 0) {
                            //  mRelNoData.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.VISIBLE);
                        } else {
                            img_no_data.setVisibility(View.GONE);
                        }
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
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
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //缴费记录
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("investigate".equals(wuye_type)) {
                    //调查问卷的历史记录
                    startActivity(new Intent(HouseListActivity.this, InvestHistoryListActivity.class));
                } else {
                    startActivity(new Intent(HouseListActivity.this, PropertyPaymentActivity.class));
                }
            }
        });
        //添加房屋
        mLyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    startActivity(new Intent(PropertyNewActivity.this, PropertyMyHomeActivity.class));
                Intent intent = new Intent(HouseListActivity.this, PropertyBindHomeActivity.class);
                intent.putExtra("wuye_type", wuye_type + "");
                startActivity(intent);
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("community", mDatas.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if ("property".equals(wuye_type)) {
                        Intent intent;
                        if (mDatas.get(position).getIs_ym().equals("0")) {
                            intent = new Intent(HouseListActivity.this, PropertyHomeListActivity.class);
                        } else {
                            intent = new Intent(HouseListActivity.this, PropertyHomeNewJFActivity.class);
                        }
                        intent.putExtra("room_id", mDatas.get(position).getRoom_id());
                        intent.putExtra("company_id", mDatas.get(position).getCompany_id());
                        startActivity(intent);
                    } else if ("open_door".equals(wuye_type)) {
                        //一键开门
                        Intent intent;
                        intent = new Intent(mContext, OpenLanActivity.class);
                        intent.putExtra("room_id", mDatas.get(position).getRoom_id());
                        startActivity(intent);
                    } else if ("house_invite".equals(wuye_type)) {
                        //访客邀请
                        checkHouseInvite(mDatas.get(position).getRoom_id());
                    } else if ("investigate".equals(wuye_type)) {
                        String status = mDatas.get(position).getStatus();
                        if ("1".equals(status)){//状态是1可以进去
                            //问卷调查
                            Intent intent;
                            intent = new Intent(mContext, InvestigateActivity.class);
                            intent.putExtra("jump_type", 1);
                            intent.putExtra("plan_id", mDatas.get(position).getPlan_id());
                            intent.putExtra("info_id", mDatas.get(position).getInfo_id());
                            intent.putExtra("address", mDatas.get(position).getAddress());
                            intent.putExtra("fullname", mDatas.get(position).getFullname());
                            intent.putExtra("mobile", mDatas.get(position).getMobile());
                            intent.putExtra("community_id", mDatas.get(position).getCommunity_id());
                            intent.putExtra("community_name", mDatas.get(position).getCommunity_name());
                            intent.putExtra("room_id", mDatas.get(position).getRoom_id());
                            startActivity(intent);
                            finish();
                        }else {
                            new PermitDialog(mContext,"您已提交调查问卷").show();
                        }
                    } else if ("permit".equals(wuye_type)) {
                        //通行证
                        //判断小区是否有通行证
                        isPermitData(position,mDatas.get(position).getCompany_id(), mDatas.get(position).getCommunity_id());

                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.house_list_layout;
    }

    @Override
    protected void initIntentData() {
        type = this.getIntent().getIntExtra("type", 0);
        if (type == 1) {
            wuye_type = this.getIntent().getStringExtra("wuye_type");
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
     * 删除住宅
     *
     * @param
     */
    private String delete_id = "";

    @Override
    public void onClickDelete(HouseBean item) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
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
                requestData();
            }
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
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).getId().equals(id)) {
                            houseBean = mDatas.get(i);
                        }
                    }
                    if (houseBean != null) {
                        mDatas.remove(houseBean);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (mDatas.size() == 0) {
                        //刷新个人中心
                        EventBus.getDefault().post(new PersoninfoBean());
                        img_no_data.setVisibility(View.VISIBLE);
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
                        Intent intent = new Intent(HouseListActivity.this, HouseInviteActivity.class);
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

    /**
     * 判断是否有通行证
     */
    protected void isPermitData(final int position,final String company_id, final String community_id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("company_id", company_id);
        params.put("community_id", community_id);
        MyOkHttp.get().post(ApiHttpClient.GET_PERMIT_SET_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent;
                    intent = new Intent(mContext, PermitListActivity.class);
                    //intent.putExtra("jump_type",1);
                    intent.putExtra("company_id", company_id);
                    intent.putExtra("community_id", community_id);
                    intent.putExtra("community_name",mDatas.get(position).getCommunity_name());
                    intent.putExtra("room_id",mDatas.get(position).getRoom_id());
                    intent.putExtra("room_info",mDatas.get(position).getAddress());
                    startActivity(intent);
                } else {
                    //该小区不在通行证适用范围
                   /* String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
                    SmartToast.showInfo(msg);*/
                    new PermitDialog(mContext,"该小区不在通行证适用范围").show();
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
