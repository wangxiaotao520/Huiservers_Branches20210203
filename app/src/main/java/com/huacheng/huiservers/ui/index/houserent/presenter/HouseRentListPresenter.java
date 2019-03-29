package com.huacheng.huiservers.ui.index.houserent.presenter;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.HouseListBean;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.model.HouseRentTagListBean;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description: 房屋租售列表presenter
 * created by wangxiaotao
 * 2018/11/7 0007 上午 9:11
 */
public class HouseRentListPresenter {
    Context mContext;
    HouseListInterface listener;
    public HouseRentListPresenter(Context mContext,HouseListInterface listener) {
        this.mContext = mContext;
        this.listener=listener;
    }

    /**
     * 获取价格列表（租售房）
     * @param houses_type 1 租房 2.售房
     */
    public void getPriceList(int houses_type,final int bean_type ){
            HashMap<String, String> params = new HashMap<>();
            params.put("houses_type",""+houses_type);
            MyOkHttp.get().post(ApiHttpClient.GET_MONEY, params, new JsonResponseHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject response) {
                    if (JsonUtil.getInstance().isSuccess(response)){
                        List data = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseRentTagListBean.class);
                        if (listener!=null){
                            listener.onGetSearchTagList(1,"请求成功",bean_type,data);
                        }
                    }else {
                        String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                        if (listener!=null){
                            listener.onGetSearchTagList(0,msg,bean_type,null);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, String error_msg) {
                    if (listener!=null){
                        listener.onGetSearchTagList(-1,"网络异常，请检查网络错误",bean_type,null);
                    }
                }
            });

    }
    /**
     * 获取面积列表（租售房）
     *
     */
    public void getAcreageList(final int bean_type ){
        MyOkHttp.get().post(ApiHttpClient.GET_ACREAGE, null, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    List data = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseRentTagListBean.class);
                    if (listener!=null){
                        listener.onGetSearchTagList(1,"请求成功",bean_type,data);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                    if (listener!=null){
                        listener.onGetSearchTagList(0,msg,bean_type,null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetSearchTagList(-1,"网络异常，请检查网络错误",bean_type,null);
                }
            }
        });
    }
    /**
     * 获取房型列表（租售房）
     *
     */
    public void getHouseTypeList(final int bean_type ){
        MyOkHttp.get().post(ApiHttpClient.GET_HOUSE_TYPE_LIST, null, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    List data = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseRentTagListBean.class);
                    if (listener!=null){
                        listener.onGetSearchTagList(1,"请求成功",bean_type,data);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                    if (listener!=null){
                        listener.onGetSearchTagList(0,msg,bean_type,null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetSearchTagList(-1,"网络异常，请检查网络错误",bean_type,null);
                }
            }
        });
    }
    /**
     * 获取排序列表（租售房）
     *
     */
    public void getDefultList(final int bean_type ){
        MyOkHttp.get().post(ApiHttpClient.GET_DEFAULT, null, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    List data = JsonUtil.getInstance().getDataArrayByName(response, "data", HouseRentTagListBean.class);
                    if (listener!=null){
                        listener.onGetSearchTagList(1,"请求成功",bean_type,data);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                    if (listener!=null){
                        listener.onGetSearchTagList(0,msg,bean_type,null);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetSearchTagList(-1,"网络异常，请检查网络错误",bean_type,null);
                }
            }
        });
    }

    /**
     * 获取租房列表（租售房）
     *
     */
    public void getLeaseList(final int page,  HashMap<String, String> params){
        params.put("page",""+page);
        MyOkHttp.get().post(ApiHttpClient.GET_LEASE_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    HouseListBean houseListBean = (HouseListBean) JsonUtil.getInstance().parseJsonFromResponse(response, HouseListBean.class);
                    List<HouseRentDetail> list=null;
                    if (houseListBean!=null){
                         list = houseListBean.getList();
                    }
                    listener.onGetHouseList(1,"请求成功",page,houseListBean.getCountPage(),list);

                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                    listener.onGetHouseList(0,msg,page,1,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetHouseList(-1,"网络异常，请检查网络错误",page,1,null);
                }
            }
        });
    }
    /**
     * 获取售房列表（租售房）
     *
     */
    public void getSellList(final int page,  HashMap<String, String> params){
        params.put("page",""+page);
        MyOkHttp.get().post(ApiHttpClient.GET_SELL_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    HouseListBean houseListBean = (HouseListBean) JsonUtil.getInstance().parseJsonFromResponse(response, HouseListBean.class);
                    List<HouseRentDetail> list=null;
                    if (houseListBean!=null){
                         list = houseListBean.getList();
                    }
                    listener.onGetHouseList(1,"请求成功",page,houseListBean.getCountPage(),list);

                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据异常");
                    listener.onGetHouseList(0,msg,page,1,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetHouseList(-1,"网络异常，请检查网络错误",page,1,null);
                }
            }
        });
    }

}
