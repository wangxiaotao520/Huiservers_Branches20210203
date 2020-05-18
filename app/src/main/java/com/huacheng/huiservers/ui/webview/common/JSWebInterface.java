package com.huacheng.huiservers.ui.webview.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;

/**
 * @function 用来和原生进行交互，js代码中通过反射调用此类中的方法
 * Created by wangxiaotao
 */

public class JSWebInterface {

    private final WebDelegate DELEGATE;
    private Context mContext;
    private Activity mActivity;

    private JSWebInterface(WebDelegate DELEGATE) {
        mContext= DELEGATE.getContext();
        mActivity= DELEGATE.getActivity();
        this.DELEGATE = DELEGATE;
    }
    //简单工厂模式
    public static JSWebInterface create(WebDelegate delegate){
        return new JSWebInterface(delegate);
    }

    //js中会调用此方法
    /**
     * 跳转到商品详情
     * @param shop_id 商品详情id
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
    /**
     * 当前页finish
     *
     */
    @JavascriptInterface
    public void finishAll() {
     if (mActivity!=null){
         mActivity.finish();
     }
    }
    /**
     * 跳转到浏览器
     * @param url 跳转的链接 必须
     */
    @JavascriptInterface
    public void jumpToBrowser(String url) {
        if (mContext!=null){
            if (!NullUtil.isStringEmpty(url)&&(url.contains("http")||url.contains("https"))){
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
            }
        }
    }

    /**
     * 显示分享弹窗
     * @param share_title  分享的title
     * @param share_desc  分享的description
     * @param bitmap      分享的图片
     * @param share_url_new   分享的链接  (经过linkedme调用后的深度链接如:http://test.hui-shenghuo.cn/home/index/vlog_list?linkedme=https://lkme.cc/LQD/WmyqmueIO)
     */
    @JavascriptInterface
    public void share(String share_title, String share_desc, String bitmap, String share_url_new) {
        //显示分享弹窗
        PopupWindowShare popup = new PopupWindowShare(mContext, share_title, share_desc, bitmap, share_url_new, AppConstant.SHARE_COMMON);
        //注意这里使用DecorView
        popup.showBottom(mActivity.getWindow().getDecorView());
    }

}