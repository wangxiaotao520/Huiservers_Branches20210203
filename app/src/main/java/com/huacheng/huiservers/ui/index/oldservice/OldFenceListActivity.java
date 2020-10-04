package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelFenceList;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterFenceList;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 新增围栏页面
 * created by wangxiaotao
 * 2020/10/2 0002 09:31
 */
public class OldFenceListActivity extends BaseActivity implements AdapterFenceList.OnClickItemIconListener {

    private GridView gridview_imgs;
    private AdapterFenceList adapterFenceList ;
    private List<ModelFenceList> mDatas = new ArrayList<>();
    private String par_uid ="";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("电子围栏");
        gridview_imgs = findViewById(R.id.gridview_imgs);
        adapterFenceList=new AdapterFenceList(this,mDatas);
        gridview_imgs.setAdapter(adapterFenceList);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        params.put("par_uid", par_uid + "");
        MyOkHttp.get().post(ApiHttpClient.ENCLOSURE_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);

                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelFenceList> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelFenceList.class);

                    if (data!= null && data.size() > 0) {
                        mDatas.clear();
                        mDatas.addAll(data);
                    }
                    adapterFenceList.notifyDataSetChanged();
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
    protected void initListener() {
        adapterFenceList.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_fence;
    }

    @Override
    protected void initIntentData() {
        par_uid=getIntent().getStringExtra("par_uid");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClickAdd(int position) {
        //新增围栏
        Intent intent = new Intent(this, NewAddFenceActivity.class);
        intent.putExtra("par_uid",par_uid);
        startActivityForResult(intent,111);
    }

    @Override
    public void onClickImage(int position) {
        Intent intent = new Intent(mContext, FenceDetailActivity.class);
        intent.putExtra("model",mDatas.get(position));
        intent.putExtra("par_uid",par_uid);
        startActivityForResult(intent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                initData();
            }
        }
    }
}
