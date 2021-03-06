package com.huacheng.huiservers.model.protocol;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.utils.LogUtils;
import com.huacheng.huiservers.utils.StringUtils;

import org.json.JSONObject;
public class LoginProtocol {
	//登陆
	public ModelLogin DataState(String json) {
		ModelLogin loginbean=new ModelLogin();
		try {
			JSONObject jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			String data = jsonObject.getString("data");
			System.out.println("data--------"+data);
			String msg = jsonObject.getString("msg");
			if (data.equals("")) {
				loginbean.setUid(null);
				SmartToast.showInfo(msg);

			}else {
				if (StringUtils.isEquals(status, "1")) {
					System.out.println("000000000");
					JSONObject obj = new JSONObject(data);
					loginbean.setUid(obj.getString("uid"));
					loginbean.setUtype(obj.getString("utype"));
					loginbean.setUsername(obj.getString("username"));
					loginbean.setIs_bind_property(obj.getString("is_bind_property"));
					// 加入token
					loginbean.setToken(obj.getString("token"));
					loginbean.setTokenSecret(obj.getString("tokenSecret"));
					loginbean.setCommunity_id(obj.getString("community_id"));
				}else if(StringUtils.isEquals(msg, "0")) {
					System.out.println("----"+msg);
					SmartToast.showInfo(msg);
				}
			}
			
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return loginbean;
	}
	//注册填写信息1//找回密码第一步2
	public ModelLogin register(String json, String tag){
		ModelLogin info=new ModelLogin();
		try {
			JSONObject jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			String msg = jsonObject.getString("msg");
			String data = jsonObject.getString("data");
			if (StringUtils.isEquals(status, "1")) {
				JSONObject obj = new JSONObject(data);
				if (tag.equals("1")) {
					info.setUid(obj.getString("uid"));
					info.setAvatars(obj.getString("avatars"));
					info.setUtype(obj.getString("utype"));
				}else if(tag.equals("2")){
					info.setToken(obj.getString("token"));
				}
				info.setUsername(obj.getString("username"));
				info.setPwd(obj.getString("pwd"));
			}else {
				//JSONObject obj = new JSONObject(data);
			/*	
				info.setAvatars(msg);*/
				//info.setUid(null);
				info.setUsername(null);
			}

		} catch (Exception e) {
			LogUtils.e(e);
		}
		return info;
	}


}
