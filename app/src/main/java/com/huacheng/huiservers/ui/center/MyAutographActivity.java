package com.huacheng.huiservers.ui.center;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.TextCheckUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：个性签名
 * 时间：2020/11/20 10:52
 * created by DFF
 */
public class MyAutographActivity extends BaseActivity {
    @BindView(R.id.right)
    TextView mTvRight;
    @BindView(R.id.et_live_content)
    EditText mEtLiveContent;
    @BindView(R.id.tv_text_count)
    TextView mTvTextCount;
    private String sign = "";

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("个性签名");
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("保存");
        mTvRight.setTextColor(getResources().getColor(R.color.title_third_color));

        if (!NullUtil.isStringEmpty(sign)){
            mEtLiveContent.setText(sign);
            mEtLiveContent.setSelection(sign.length());//将光标移至文字末尾
            mTvTextCount.setText(sign.length() + "");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //1、传入需要监听的EditText与TextView
        TextCheckUtils textCheckUtils = new TextCheckUtils(mEtLiveContent);
        //2、设置是否全部填写完成监听
        textCheckUtils.setOnCompleteListener(new TextCheckUtils.OnCompleteListener() {
            @Override
            public void isComplete(boolean isComplete) {
                if (isComplete) {
                    mTvRight.setEnabled(true);
                    mTvRight.setTextColor(getResources().getColor(R.color.orange));

                } else {
                    mTvRight.setEnabled(false);
                    mTvRight.setTextColor(getResources().getColor(R.color.title_third_color));
                }
            }
        });
        mEtLiveContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    int length = s.length();
                    mTvTextCount.setText(length + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NullUtil.isStringEmpty(mEtLiveContent.getText().toString().trim())) {
                    requestData();
                } else {
                    SmartToast.showInfo("个性签名为空");
                }
            }
        });
    }

    /**
     * 个性签名
     */
    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "setSignature");
        params.put("value", mEtLiveContent.getText().toString().trim());
        MyOkHttp.get().post(ApiHttpClient.MY_EDIT_CENTER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    PersoninfoBean personinfoBean=new PersoninfoBean();
                    personinfoBean.setSignature(mEtLiveContent.getText().toString().trim());
                    EventBus.getDefault().post(personinfoBean);
                    finish();

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
    protected int getLayoutId() {
        return R.layout.layout_customer_records;
    }

    @Override
    protected void initIntentData() {
        sign = this.getIntent().getStringExtra("sign");

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
        super.onCreate(savedInstanceState);

    }

    @Override
    public void finish() {
        new ToolUtils(mEtLiveContent, this).closeInputMethod();
        super.finish();
    }

}
