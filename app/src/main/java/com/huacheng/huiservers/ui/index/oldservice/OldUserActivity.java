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
import com.huacheng.huiservers.utils.StringUtils;
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
    private boolean iscancel = false;  //删除状态
    private int type=1;  //1是关联的子女列表 2.关联的长者列表
    private List<ModelOldRelationShip> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        txt_right = findViewById(R.id.txt_right);
        txt_right.setText("编辑");
        txt_right.setVisibility(View.GONE);
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
                            if (type==2){
                                //子女关联老人列表是空，说明老人主动删了关联的子女，
                                //刷新前一页
                                ModelEventOld modelEventOld = new ModelEventOld();
                                modelEventOld.setEvent_type(1);
                                EventBus.getDefault().post(modelEventOld);
                            }
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
          //  FrescoUtils.getInstance().setImageUri(sdv_head, ApiHttpClient.IMG_URL +item.getAvatars() );
            FrescoUtils.getInstance().setImageUri(sdv_head, StringUtils.getImgUrl(item.getAvatars()));
            if (type ==1){
                tv_tag.setVisibility(View.GONE);
            }else {
                tv_tag.setVisibility(View.VISIBLE);
            }
            tv_tag.setText(item.getCall()+"");
            tv_name.setText(item.getNickname());
            ly_user.addView(view);

            view.setTag(mDatas.get(i).getPar_uid()+"");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==2&&!iscancel){
                        //切换长者
                        String id = (String) v.getTag();
                        Intent intent = new Intent();
                            intent.putExtra("par_uid",id);
                            setResult(RESULT_OK,intent);
                            finish();

                    }
                }
            });
            ly_delete.setTag(mDatas.get(i).getId());
            ly_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  id = (String) v.getTag();
                    deleteRelationShip(v, id);
                }
            });
            if (!iscancel){//不是删除状态
                ly_delete.setVisibility(View.GONE);
                ry_yinying.setBackground(getResources().getDrawable(R.drawable.allshape_white));
            }else {//删除状态
                ly_delete.setVisibility(View.VISIBLE);
                ry_yinying.setBackground(getResources().getDrawable(R.drawable.layer_shadow));
            }
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
     * @param
     */
    private void deleteRelationShip(View v, final String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("r_id",id);
        showDialog(smallDialog);
        smallDialog.setCanceledOnTouchOutside(false);
        MyOkHttp.get().post(ApiHttpClient.PENSION_RELATION_DELETE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求成功");
                    SmartToast.showInfo(msg);
                    ModelOldRelationShip model_remove=null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).getId().equals(id)){
                            model_remove=mDatas.get(i);
                        }
                    }
                    if (model_remove!=null){
                        mDatas.remove(model_remove);
                    }
                    addview();

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

                    }
                    if (type==2){
                        //刷新前一页
                        ModelEventOld modelEventOld = new ModelEventOld();
                        modelEventOld.setEvent_type(1);
                        EventBus.getDefault().post(modelEventOld);
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
