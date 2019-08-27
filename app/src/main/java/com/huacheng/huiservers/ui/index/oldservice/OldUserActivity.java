package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelEventOld;
import com.huacheng.huiservers.model.ModelOldRelationShip;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：居家养老 关联账户/切换长者
 * 时间：2019/8/13 16:35
 * created by DFF
 */
public class OldUserActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ly_user;
    private TextView tv_btn;
    private TextView txt_right;
    private boolean iscancel = false;
    private int type=1;  //1是关联的子女列表 2.关联的长者列表
    private List<ModelOldRelationShip> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        txt_right = findViewById(R.id.txt_right);
        txt_right.setText("编辑");
        txt_right.setVisibility(View.GONE);
        txt_right.setVisibility(View.VISIBLE);
        txt_right.setTextColor(getResources().getColor(R.color.orange_bg));
        //listview = findViewById(R.id.listview);
        ly_user = findViewById(R.id.ly_user);
        tv_btn = findViewById(R.id.tv_btn);
        tv_btn.setVisibility(View.INVISIBLE);
        if (type ==1){
            titleName.setText("关联账号");
            showDialog(smallDialog);
            requestRelationList();
            tv_btn.setText("添加关联");
        }else {
            titleName.setText("切换长者");
            showDialog(smallDialog);
            requestRelationList();
            tv_btn.setText("关联长者");
        }

    }

    /**
     * 关联列表
     */
    private void requestRelationList() {
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.PENSION_RELATION_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelOldRelationShip>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOldRelationShip.class);
                        if (data != null && data.size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(data);
                            addview();
                            txt_right.setVisibility(View.VISIBLE);
                        }else {
                            txt_right.setVisibility(View.GONE);
                        }

                    tv_btn.setVisibility(View.VISIBLE);

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

    private void addview() {

        ly_user.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_old_user_item, null);
            SimpleDraweeView sdv_head = view.findViewById(R.id.sdv_head);
            TextView tv_tag = view.findViewById(R.id.tv_tag);
            TextView tv_name = view.findViewById(R.id.tv_name);
            LinearLayout ly_delete = view.findViewById(R.id.ly_delete);
            RelativeLayout ry_yinying = view.findViewById(R.id.ry_yinying);

            ModelOldRelationShip item = mDatas.get(i);
            FrescoUtils.getInstance().setImageUri(sdv_head, ApiHttpClient.IMG_URL +item.getAvatars() );
            if (type ==1){
                tv_tag.setVisibility(View.GONE);
            }else {
                tv_tag.setVisibility(View.VISIBLE);
            }
            tv_tag.setText(item.getCall()+"");
            tv_name.setText(item.getNickname());
            ly_user.addView(view);

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==2&&!iscancel){
                        //切换长者
                        Intent intent = new Intent();
                        intent.putExtra("par_uid",mDatas.get(finalI).getPar_uid()+"");
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(this);
        txt_right.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_user;
    }

    @Override
    protected void initIntentData() {
        this.type = getIntent().getIntExtra("type",1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_right:
                if (iscancel) {
                    txt_right.setText("编辑");
                    tv_btn.setVisibility(View.VISIBLE);
                    iscancel = false;
                    for (int i = 0; i < ly_user.getChildCount(); i++) {
                        View childAt = ly_user.getChildAt(i);
                        LinearLayout ly_delete = childAt.findViewById(R.id.ly_delete);
                        RelativeLayout ry_yinying = childAt.findViewById(R.id.ry_yinying);
                        ly_delete.setVisibility(View.GONE);
                        ry_yinying.setBackground(getResources().getDrawable(R.drawable.allshape_white));
                    }
                } else {
                    txt_right.setText("完成");
                    tv_btn.setVisibility(View.GONE);
                    iscancel = true;
                    for (int i = 0; i < ly_user.getChildCount(); i++) {
                        View childAt = ly_user.getChildAt(i);
                        LinearLayout ly_delete = childAt.findViewById(R.id.ly_delete);
                        RelativeLayout ry_yinying = childAt.findViewById(R.id.ry_yinying);
                        ly_delete.setVisibility(View.VISIBLE);
                        final int finalI = i;
                        ly_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteRelationShip(v, finalI);
                            }
                        });
                        ry_yinying.setBackground(getResources().getDrawable(R.drawable.layer_shadow));

                    }
                }
                break;
            case R.id.tv_btn:
                Intent intent = new Intent(OldUserActivity.this, AddOldUserActivity.class);
                intent.putExtra("type", type);
                startActivityForResult(intent,101);
                //  Intent intent = new Intent(OldUserActivity.this, ChargeHistoryActivity.class);
                // Intent intent = new Intent(OldUserActivity.this, ChargeGridviewActivity.class);
                // startActivity(intent);
                break;
            default:
                break;
        }

    }

    /**
     * 删除关联
     * @param v
     * @param finalI
     */
    private void deleteRelationShip(View v, final int finalI) {
        HashMap<String, String> params = new HashMap<>();
        if (finalI>mDatas.size()){
            return;
        }else {
            params.put("r_id",mDatas.get(finalI).getId()+"");
        }

        showDialog(smallDialog);
        MyOkHttp.get().post(ApiHttpClient.PENSION_RELATION_DELETE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求成功");
                    SmartToast.showInfo(msg);
                    mDatas.remove(finalI);
                    ly_user.removeViewAt(finalI);
                    if (mDatas.size()==0){
                        txt_right.setText("编辑");
                        txt_right.setVisibility(View.GONE);
                        tv_btn.setVisibility(View.VISIBLE);
                        iscancel = false;
                        for (int i = 0; i < ly_user.getChildCount(); i++) {
                            View childAt = ly_user.getChildAt(i);
                            LinearLayout ly_delete = childAt.findViewById(R.id.ly_delete);
                            RelativeLayout ry_yinying = childAt.findViewById(R.id.ry_yinying);
                            ly_delete.setVisibility(View.GONE);
                            ry_yinying.setBackground(getResources().getDrawable(R.drawable.allshape_white));
                        }
                        if (type==2){
                            //关联的长者列表数变为0则刷新前一页变成暂未认证的状态
                            EventBus.getDefault().post(new ModelEventOld());
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
}
