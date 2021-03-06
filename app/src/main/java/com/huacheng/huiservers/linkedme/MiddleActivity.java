package com.huacheng.huiservers.linkedme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.index.vote.VoteDetailActivity;
import com.huacheng.huiservers.ui.index.vote.VoteVlogIndexActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.servicenew.ui.ServiceDetailActivity;
import com.huacheng.huiservers.ui.servicenew.ui.shop.ServiceStoreActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopZQListActivity;
import com.huacheng.huiservers.ui.shop.StoreIndexActivity;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;
import com.huacheng.huiservers.utils.LoginUtils;
import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.util.LinkProperties;

import java.util.HashMap;

/**
 * <p>中转页面</p>
 * <p>
 * Created by LinkedME06 on 16/11/17.
 */

public class MiddleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     Log.i(LinkedME.TAG, "onCreate: MiddleActivity is called.");
        //  Toast.makeText(this, "MiddleActivity 被调用了", Toast.LENGTH_SHORT).show();
        //获取与深度链接相关的值
        LinkProperties linkProperties = getIntent().getParcelableExtra(LinkedME.LM_LINKPROPERTIES);
        LMUniversalObject lmUniversalObject = getIntent().getParcelableExtra(LinkedME.LM_UNIVERSALOBJECT);
        //LinkedME SDK初始化成功，获取跳转参数，具体跳转参数在LinkProperties中，和创建深度链接时设置的参数相同；
        if (linkProperties != null) {
            Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
            Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());
            Log.i("LinkedME-Demo", "link(深度链接) " + linkProperties.getLMLink());
            //获取自定义参数封装成的HashMap对象
            HashMap<String, String> hashMap = linkProperties.getControlParams();

            //获取传入的参数
            String type = hashMap.get("type");
            String title = "";
            String shareContent = "";
            String url_path = "";
            if (type != null) {
                if (type.equals("goods_details")) {//商品详情
                    String shop_id = hashMap.get("id");
                    Intent intent = new Intent(this, ShopDetailActivityNew.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id", shop_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("service_shop")) {
                    String store_id = hashMap.get("id");
                    Intent intent = new Intent(this, ServiceStoreActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", store_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("service_detail")) {
                    String service_id = hashMap.get("id");
                    Intent intent = new Intent(this, ServiceDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("service_id", service_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("vote_details")) {
                    if (LoginUtils.hasLoginUser()) {
                        String id = hashMap.get("id");
                        String poll = hashMap.get("poll");
                        Intent intent = new Intent(this, VoteDetailActivity.class);
                        intent.putExtra("id", id + "");
                        intent.putExtra("poll", poll + "");
                        startActivity(intent);
                    }
                } else if (type.equals("store_details")) {
                    String id = hashMap.get("id");
                    Intent intent = new Intent(this, StoreIndexActivity.class);
                    intent.putExtra("store_id", id + "");
                    startActivity(intent);
                } else if (type.equals("prefecture_list")) {
                    String id = hashMap.get("id");
                    Intent intent = new Intent(this, ShopZQListActivity.class);
                    intent.putExtra("id", id + "");
                    startActivity(intent);
                } else if (type.equals("prefecture_details")) {
                    String id = hashMap.get("id");
                    String sub_type = hashMap.get("sub_type");
//                    Intent intent = new Intent(this, ShopZQWebActivity.class);
//                    intent.putExtra("id", id + "");
//                    intent.putExtra("sub_type", sub_type + "");
//                    startActivity(intent);

                    Intent intent=new Intent();
                    intent.setClass(this, WebViewDefaultActivity.class);
                    intent.putExtra("web_type",1);
                    int jump_type = ConstantWebView.CONSTANT_ZHUANQU;
                    if ("1".equals(sub_type)){
                        jump_type = ConstantWebView.CONSTANT_ZHUANQU;
                    }else {
                        jump_type = ConstantWebView.CONSTANT_ZHUANQU_HUODONG;
                    }
                    intent.putExtra("jump_type", jump_type);
                    intent.putExtra("id", id + "");
                    intent.putExtra("sub_type", sub_type + "");
                    startActivity(intent);

                } else if (type.equals("circle_details")) {
                    String id = hashMap.get("id");
                    String sub_type = hashMap.get("sub_type");
                    Intent intent = new Intent(this, CircleDetailsActivity.class);
                    intent.putExtra("id", id + "");
                    intent.putExtra("mPro", sub_type + "");
                    startActivity(intent);
                }else if (type.equals("vlog_vote_details")){
//                    Intent intent = new Intent(this, VoteVlogIndexActivity.class);
//                    startActivity(intent);
                }else if ("vote_common_details".equals(type)){
                    if (LoginUtils.hasLoginUser()){
                        String id = hashMap.get("id");
                        Intent intent = new Intent(this, VoteVlogIndexActivity.class);
                        intent.putExtra("id",id);
                        this.startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, LoginVerifyCodeActivity.class);
                        this.startActivity(intent);
                    }
                }
            }
            //清除跳转数据，该方法理论上不需要调用，因Android集成方式各种这样，若出现重复跳转的情况，可在跳转成功后调用该方法清除参数
            //LinkedME.getInstance().clearSessionParams();
        }

        if (lmUniversalObject != null) {
            Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
            Log.i("LinkedME-Demo", "control " + linkProperties.getControlParams());
            Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());
        }
        finish();
    }

}
