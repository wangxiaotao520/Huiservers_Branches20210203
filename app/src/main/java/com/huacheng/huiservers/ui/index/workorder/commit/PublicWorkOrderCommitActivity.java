package com.huacheng.huiservers.ui.index.workorder.commit;

import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.dialog.AddPublicRepairTagDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCommonCat;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 公共保修发布
 * created by wangxiaotao
 * 2018/12/12 0012 上午 10:55
 */
public class PublicWorkOrderCommitActivity  extends BaseWorkOrderCommitActivity implements AddPublicRepairTagDialog.OnClickTagListener{

    AddPublicRepairTagDialog tagDialog ;
    List<ModelCommonCat> tag_lists = new ArrayList<>();


    @Override
    protected void initView() {

        super.initView();
        titleName.setText("公共报修");
        work_type=2;
    }


    @Override
    protected void initListener() {
        super.initListener();
        rel_select_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTagDialog();
            }
        });
    }


    /**
     * tag  显示tagdialog
     */
    private void showTagDialog() {
        if (tag_lists.size()>0){
            tagDialog= new AddPublicRepairTagDialog(tag_lists,this ,this);
            tagDialog.show();
        }else {
            // 请求接口
            requestCatData();

        }
    }
    //请求分类接口
    private void requestCatData() {
        showDialog(smallDialog);
        MyOkHttp.get().post(ApiHttpClient.GET_COMMON_CATEGORY, null, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelCommonCat>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCommonCat.class);
                    tag_lists.clear();
                    tag_lists.addAll(data);
                    tagDialog= new AddPublicRepairTagDialog(tag_lists, PublicWorkOrderCommitActivity.this,PublicWorkOrderCommitActivity.this);
                    tagDialog.show();
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

    @Override
    public void onEnSure(int position) {
        //点击tag 确定
        if (tag_lists.size()>0){
            for (int i = 0; i < tag_lists.size(); i++) {
                if (i==position){
                    tag_lists.get(i).setIs_selected(true);
                }else {
                    tag_lists.get(i).setIs_selected(false);
                }
            }
            tv_select_tag.setText(tag_lists.get(position).getLabel_name()+"");
            this.type_id=tag_lists.get(position).getId()+"";
            this.type_name=tag_lists.get(position).getLabel_name();
        }

    }
}
