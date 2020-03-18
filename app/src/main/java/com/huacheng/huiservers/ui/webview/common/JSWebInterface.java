package com.huacheng.huiservers.ui.webview.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;

/**
 * @function 用来和原生进行交互，js代码中通过反射调用此类中的方法
 * Created by wangxiaotao
 */

public class JSWebInterface {

    private final WebDelegate DELEGATE;
    private Context mContext;

    private JSWebInterface(WebDelegate DELEGATE) {
        mContext= BaseApplication.getContext();
        this.DELEGATE = DELEGATE;
    }
    //简单工厂模式
    public static JSWebInterface create(WebDelegate delegate){
        return new JSWebInterface(delegate);
    }

    //js中会调用此方法
    /**
     * 跳转到商品详情
     * @param shop_id
     */
    @JavascriptInterface
    public void jumpToGoodsDetail(String shop_id) {
        if (mContext!=null){
            Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
            Bundle bundle = new Bundle();
            bundle.putString("shop_id", shop_id);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

}