package com.huacheng.huiservers.model.protocol;

import android.text.TextUtils;

import com.huacheng.huiservers.model.CircleDetailBean;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.LogUtils;
import com.huacheng.huiservers.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwh on 2017/11/3.
 */

public class CircleProtocol {

    /**
     * 圈子分类
     * @param json
     * @return
     */
    public List<BannerBean> getSocialCategory(String json) {
        List<BannerBean> info = new ArrayList<BannerBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            String data = jsonObject.getString("data");
            if (StringUtils.isEquals(status, "1")) {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    BannerBean adinfo = new BannerBean();
                    JSONObject obj = array.getJSONObject(i);
                    adinfo.setC_name(obj.getString("c_name"));
                    adinfo.setId(obj.getString("id"));
                    adinfo.setIs_pro(obj.getInt("is_pro"));
                    info.add(adinfo);
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return info;
    }

    // 圈子详情4.0接口
    public CircleDetailBean getSocialDetail(String json) {
        CircleDetailBean info = new CircleDetailBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            String data = jsonObject.getString("data");
            if (StringUtils.isEquals(status, "1")) {
                if (!TextUtils.isEmpty(data)) {
                    System.out.println("obj====" + jsonObject);
                    JSONObject obj = new JSONObject(data);
                    info.setId(obj.getString("id"));
                    info.setUid(obj.getString("uid"));
                    info.setTitle(obj.getString("title"));
                    info.setAdmin_id(obj.getString("admin_id"));
                    info.setC_name(obj.getString("c_name"));
                    info.setContent(obj.getString("content"));
                    info.setClick(obj.getString("click"));
                    info.setReply_num(obj.getString("reply_num"));
                    info.setAddtime(obj.getString("addtime"));
                    info.setAvatars(obj.getString("avatars"));
                    info.setNickname(obj.getString("nickname"));
                    info.setIs_observe(obj.getString("is_observe"));
                    info.setShare_content(obj.getString("share_content"));
                    info.setShare_img(obj.getString("share_img"));
                    String Img_list = obj.getString("img_list");
                    if (!TextUtils.isEmpty(Img_list)) {
                        JSONArray array_img = new JSONArray(Img_list);
                        List<CircleDetailBean> beans = new ArrayList<CircleDetailBean>();
                        for (int j = 0; j < array_img.length(); j++) {
                            JSONObject obj1 = array_img.getJSONObject(j);
                            CircleDetailBean bean = new CircleDetailBean();
                            bean.setImg(obj1.getString("img"));
                            bean.setImg_size(obj1.getString("img_size"));
                            beans.add(bean);
                        }
                        info.setImg_list(beans);
                    } else {
                        info.setImg_list(null);
                    }
                    String reply_list = obj.getString("reply_list");
                    if (!TextUtils.isEmpty(reply_list)) {
                        JSONArray array_img = new JSONArray(reply_list);
                        List<CircleDetailBean> beans = new ArrayList<CircleDetailBean>();
                        for (int j = 0; j < array_img.length(); j++) {
                            JSONObject obj1 = array_img.getJSONObject(j);
                            CircleDetailBean bean = new CircleDetailBean();
                            bean.setId(obj1.getString("id"));
                            bean.setUid(obj1.getString("uid"));
                            bean.setSocial_id(obj1.getString("social_id"));
                            bean.setContent(obj1.getString("content"));
                            bean.setAddtime(obj1.getString("addtime"));
                            bean.setR_avatars(obj1.getString("r_avatars"));
                            bean.setR_nickname(obj1.getString("r_nickname"));
                            beans.add(bean);
                        }
                        info.setReply_list(beans);
                    } else {
                        info.setReply_list(null);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return info;
    }

    // 获取我的圈子数量
    public CircleDetailBean getMyCircleNum(String json) {
        CircleDetailBean circleNum = new CircleDetailBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            String data = jsonObject.getString("data");
            if (StringUtils.isEquals(status, "1")) {
                JSONObject obj = new JSONObject(data);
                circleNum.setSocial_num(obj.getString("social_num"));
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return circleNum;
    }
}
