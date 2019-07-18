package com.huacheng.huiservers.pay.chinaums;

import android.content.Context;

import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.center.geren.bean.PayTypeBean;
import com.huacheng.huiservers.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description: 银联聚合支付的回调
 * created by wangxiaotao
 * 2019/7/9 0009 上午 11:24
 */
public class UnifyPayPresenter {
    Context mContext;
    OnUnifyPayListener listener;
    public UnifyPayPresenter(Context mContext,OnUnifyPayListener listener) {
        this.mContext = mContext;
        this.listener=listener;
    }

    /**
     * 获取支付方式
     */
    public void getPayType() {
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(Url_info.payment_list, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    List data = JsonUtil.getInstance().getDataArrayByName(response, "data", PayTypeBean.class);
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取成功");
                    if (listener!=null){
                        listener.onGetPayTypeDatas(1,msg,data);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取失败");
                    if (listener!=null){
                        listener.onGetPayTypeDatas(0,msg,null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetPayTypeDatas(-1,"网络异常,请检查网络设置",null);
                }
            }
        });


    }
    /**
     * 验证支付结果
     */
    public void confirmOrderPayment(String id , final String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("type",type);
        MyOkHttp.get().post(ApiHttpClient.CONFIRM_ORDER_PAYMENT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取成功");
                    if (listener!=null){
                        listener.onGetOrderResult(1,msg,null,type);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取失败");
                    if (listener!=null){
                        listener.onGetOrderResult(0,msg,null,type);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetOrderResult(-1,"网络异常,请检查网络设置",null,type);
                }
            }
        });


    }

    /**
     * 请求数据订单数据
     */
    public void getOrderInformation(String id , String typename, final int  typetag,String type) {
        String url = "";
        if (type.equals(CanstantPay.PAY_SHOP_CONFIRM_ORDER)||type.equals(CanstantPay.PAY_SHOP_ORDER_DETAIL)){
            url=ApiHttpClient.PAY_SHOPPING_ORDER;//商城
        }else if (type.equals(CanstantPay.PAY_WORKORDER)){//工单支付
            url=ApiHttpClient.PAY_WORK_ORDER_NEW;
        }else if (type.equals(CanstantPay.PAY_SERVICE)){//服务
            url=ApiHttpClient.PAY_SERVICE_ORDER_NEW;
        }else if (type.equals(CanstantPay.PAY_PROPERTY)){//物业
            url=ApiHttpClient.PAY_PROPERTY_ORDER_NEW;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("typename",typename);
        MyOkHttp.get().post(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){

                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取成功");
                    try {
                        String data = response.getString("data");
                        if (listener!=null){
                            listener.onGetOrderInformation(1,msg,data,typetag);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取失败");
                    if (listener!=null){
                        listener.onGetOrderInformation(0,msg,null,typetag);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetOrderInformation(1,"网络异常,请检查网络设置",null,typetag);
                }
            }
        });


    }
}
