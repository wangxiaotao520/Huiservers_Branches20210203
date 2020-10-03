package com.huacheng.huiservers.ui.index.oldservice;

import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterMyTrak;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：查看足迹
 * 时间：2020/9/30 17:12
 * created by DFF
 */
public class MyTrackActivity extends BaseActivity {
    private String date;
    private ModelOldFootmark mOldFootmark;
    private List<ModelOldFootmark.PosBean> mDatas = new ArrayList<>();
    private AdapterMyTrak mAdapterMyTrak;
    MyListView mMyListView;
    private TextView tv_data;
    private TextView tv_city_num;
    private TextView tv_zuji_num;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("我的足迹");

        tv_data = findViewById(R.id.tv_data);
        tv_city_num = findViewById(R.id.tv_city_num);
        tv_zuji_num = findViewById(R.id.tv_zuji_num);
        mMyListView = findViewById(R.id.listview);

        //默认获取当天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date = sdf.format(System.currentTimeMillis());

        mAdapterMyTrak = new AdapterMyTrak(this, R.layout.item_my_track, mDatas);
        mMyListView.setAdapter(mAdapterMyTrak);

    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid", "105");// TODO: 2020/10/3  id记得更改
        params.put("date", date);

        MyOkHttp.get().post(ApiHttpClient.DEVICE_ZUJI, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                    if (mOldFootmark != null) {
                        tv_data.setText("您"+date+"跨越了");
                        tv_city_num.setText(mOldFootmark.getCityNum()+"");
                        tv_zuji_num.setText(mOldFootmark.getPosNum()+"");
                        if (mOldFootmark.getPos() != null && mOldFootmark.getPos().size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(mOldFootmark.getPos());
                            mAdapterMyTrak.notifyDataSetChanged();
                        }
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


    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_track;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
