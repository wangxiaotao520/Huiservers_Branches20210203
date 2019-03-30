package com.huacheng.huiservers.http;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 之前得网络框架，我后来换成里面用MyOkhttp(真的尽力了)
 * wangxiaotao
 */
public abstract class HttpHelper {
    public Context context;
    public String tag;
    public String str;
    public static String url="";


    public HttpHelper(String url, RequestParams params, Context context) {
        this.context = context;
        this.url = url;
        dopost(params,url);

    }
    public HttpHelper(String url, Context context) {
        this.context = context;
        this.url = url;
        dpGet(url);
    }


    private void dpGet(String url) {
        final String finalUrl = url;
        MyOkHttp.get().get(url, null, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    String msg = jsonObject.getString("msg");
                    if (status!=null){
                      if (status.equals("1")){
                            //我也没办法只能这样过滤，放过我吧，我尽力了 以后写的时候用Okhttp
                            setData(response);
                        } else if (finalUrl.contains(Url_info.my_wallet)){
                            setData(response);
                        }else if (finalUrl.contains(Url_info.my_renvation)){
                            setData(response);
                        }else if (finalUrl.contains(Url_info.pro_discount_list)){
                            setData(response);
                        }else if (finalUrl.contains(Url_info.goods_search)){
                            setData(response);
                        }else if (finalUrl.contains(Url_info.pro_list)){
                            setData(response);
                        }else if (finalUrl.contains("property/")||finalUrl.contains("activity/")){
                            setData(response);
                        }else if (finalUrl.contains(Url_info.getSocialList)){//邻里列表
                            setData(response);
                        }else if (finalUrl.contains(Url_info.goods_review)){//商品评价列表
                            setData(response);
                        }else {
                            UIUtils.showToastSafe(msg);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                requestFailure( null, error_msg);
            }
        });

    }

    private void dopost(RequestParams requestParams,String url) {
        HashMap<String, String> params = requestParams.getParams();

        MyOkHttp.get().post(url, params, new RawResponseHandler() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                requestFailure(null,error_msg);
            }

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                     if (status.equals("1")){
                            setData(response);
                        }else {

                            //我也没办法只能这样过滤，放过我吧，我尽力了 以后写的时候用Okhttp
                            if (HttpHelper.this.url.equals(Url_info.version_update)){//更新
                                setData(response);
                            }else if (HttpHelper.this.url.equals(Url_info.coupon_over_40)){//优惠券列表
                                setData(response);
                            }else if (HttpHelper.this.url.contains("property/")||HttpHelper.this.url.contains("activity/")){//服务订单列表
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.get_Advertising)){//我的装修
                                setData(response);
                            }else if (HttpHelper.this.url.contains("old_index_new")){//老年风采信息
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.get_user_Social)){//我的邻里
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.get_memorandum)){//新房手册备注
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.confirm_order_payment)){//支付成功返回
                                setData(response);
                            }else if (HttpHelper.this.url.contains("userCenter/get_user_address/")){//收货地址
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.pay_property_order)) {// 物业的支付
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.pay_shopping_order)) {// 是从购物流程一路付款成功的
                                setData(response); // 购物的

                            } else if (HttpHelper.this.url.contains(Url_info.pay_activity_order)) { // 活动的
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.pay_face_order) ){// 当面付
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.pay_wired_order) ) { // 有线缴费
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.service_new_pay) ){//服务支付
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.pay_shopping_check) ){//一卡通支付
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.shop_order_accept) ){//确认收货
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.del_order) ){//删除订单
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.shop_index_new) ){//商城首页
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.get_Advertising) ){//商城中部广告
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.get_social) ){//商城中部广告
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.submit_order) ){//提交商城订单
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.checkIsAjb) ){//访客邀请
                                setData(response);
                            } else if (HttpHelper.this.url.contains(Url_info.coupon_add) ){//领取优惠券
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.area_topclass) ){//获取商品分类
                                setData(response);
                            }else if (HttpHelper.this.url.contains(Url_info.submit_order_before) ){//确认订单信息
                                setData(response);
                            }else {
                                UIUtils.showToastSafe(msg);
                            }
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        })  ;

    }

    protected abstract void setData(String json);

    protected  abstract void requestFailure(Exception error, String msg);
}
