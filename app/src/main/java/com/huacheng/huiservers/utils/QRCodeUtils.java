package com.huacheng.huiservers.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.dialog.SmallDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.model.ModelQRCode;
import com.huacheng.huiservers.pay.chinaums.CanstantPay;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.index.charge.ChargeGridviewActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



/**
 * Description:二维码工具类
 * created by wangxiaotao
 * 2019/8/23 0023 下午 7:24
 */
public class QRCodeUtils {
    private static QRCodeUtils instance;
    private SmallDialog smallDialog;

    private void QRCodeUtils(){

    }
    /**
     * 获取句柄
     *
     * @return
     */
    public static QRCodeUtils getInstance() {
        if (instance == null) {
            instance = new QRCodeUtils();
        }

        return instance;
    }
    /**
     * 解析二维码
     * param type = 1 //是充电桩
     */
    public void parseQrCode(SmallDialog smallDialog,Context context,int type , ModelQRCode modelQRCode){
        this.smallDialog=smallDialog;
        if (modelQRCode!=null){
            if (type==1){ //充电桩
                requestChargeData(context,modelQRCode.getGtel()+"");
            }else if (type==2){
                Intent intent = new Intent(context, UnifyPayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("o_id", modelQRCode.getO_id()+"");
                bundle.putString("price", modelQRCode.getPrice()+ "");
                bundle.putString("type", CanstantPay.PAY_SERVICE);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
    }

    private void requestChargeData(final Context context, String equipment_code) {
        if (smallDialog!=null){
            smallDialog.show();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");//充电模块传1
        params.put("gtel", equipment_code + "");

        MyOkHttp.get().post(ApiHttpClient.GET_YX_INFO, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelChargeDetail model = (ModelChargeDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelChargeDetail.class);
                    if (model!=null){
                        if (context!=null){
                            Intent intent = new Intent(context, ChargeGridviewActivity.class);
                            intent.putExtra("model",model);
                            intent.putExtra("response",response.toString());
                            context.startActivity(intent);
                        }
                    }else {
                        SmartToast.showInfo("获取充电信息异常");
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
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
}
