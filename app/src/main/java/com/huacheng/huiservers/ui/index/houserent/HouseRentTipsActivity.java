package com.huacheng.huiservers.ui.index.houserent;

import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：租售房小贴士
 * 时间：2018/11/10 11:04
 * created by DFF
 */
public class HouseRentTipsActivity extends BaseActivity {
    HouseRentDetail houseRentInfo;
    int HouseRentType;
    TextView tv_content;
    WebView webview;

    @Override
    protected void initView() {
        findTitleViews();
        HouseRentType = this.getIntent().getExtras().getInt("jump_type");

        webview = findViewById(R.id.webview);
        if (HouseRentType == 1) {
            titleName.setText("租房小贴士");
        } else {
            titleName.setText("售房小贴士");
        }

    }

    @Override
    protected void initData() {
        getData();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_housrent_tips;
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

    private void getData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("house_type", HouseRentType + "");

        MyOkHttp.get().post(ApiHttpClient.GET_CAREFUL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    houseRentInfo = (HouseRentDetail) JsonUtil.getInstance().parseJsonFromResponse(response, HouseRentDetail.class);
                    byte[] bytes = Base64.decode(houseRentInfo.getContent(), Base64.DEFAULT);
                    webview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
                    if (!"".equals(new String(bytes))) {
                        String css = "<style type=\"text/css\"> " +
                                "img {" +
                                "max-width: 100% !important;" +//限定图片宽度填充屏幕
                                "height:auto !important;" +//限定图片高度自动
                                "}" +
                                "</style>";
                        String content1 = "<head>" + css + "</head><body>" + new String(bytes) + "</body></html>";
                        webview.loadDataWithBaseURL(null, content1, "text/html", "utf-8", null);
                    }
                } else {
                    try {
                        XToast.makeText(HouseRentTipsActivity.this, response.getString("msg"), XToast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                XToast.makeText(HouseRentTipsActivity.this, "网络异常，请检查网络设置", XToast.LENGTH_SHORT).show();
            }
        });
    }

}
