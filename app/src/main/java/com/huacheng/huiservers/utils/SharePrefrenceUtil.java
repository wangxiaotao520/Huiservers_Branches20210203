package com.huacheng.huiservers.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefrenceUtil {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharePrefrenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("hui", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    //登录类型
    public String getLoginType() {
        return sharedPreferences.getString("loginType", "0");
    }

    public void setLoginType(String loginType) {
        editor.putString("loginType", loginType);
        editor.commit();
    }


    //cookie
    public String getCookie() {
        return sharedPreferences.getString("cookie", "");
    }

    public void setCookie(String cookie) {
        editor.putString("cookie", cookie);
        editor.commit();
    }

    public SharePrefrenceUtil() {
        sharedPreferences = context.getSharedPreferences("hui", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    //首页选择的小区id
    public String getXiaoQuId() {
        return sharedPreferences.getString("xiaoqu_id", "");
    }

    public void setXiaoQuId(String xiaoqu_id) {
        editor.putString("xiaoqu_id", xiaoqu_id);
        editor.commit();
    }
    //省
    public String getProvince_cn() {
        return sharedPreferences.getString("province_cn", "");
    }

    public void setProvince_cn(String name) {
        editor.putString("province_cn", name);
        editor.commit();
    }
    //市
    public String getCity_cn() {
        return sharedPreferences.getString("city_cn", "");
    }

    public void setCity_cn(String name) {
        editor.putString("city_cn", name);
        editor.commit();
    }
    //区
    public String getRegion_cn() {
        return sharedPreferences.getString("region_cn", "");
    }

    public void setRegion_cn(String name) {
        editor.putString("region_cn", name);
        editor.commit();
    }

    /**
     * 设置企业id
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        editor.putString("company_id", companyId);
        editor.commit();
    }

    /**
     * 获取企业id
     * @return
     */
    public String getCommanyId() {
        return sharedPreferences.getString("company_id", "0");
    }


    //首页选择的小区名字
    public String getXiaoQuName() {
        return sharedPreferences.getString("xiaoqu_name", "");
    }

    public void setXiaoQuName(String xiaoqu_name) {
        editor.putString("xiaoqu_name", xiaoqu_name);
        editor.commit();
    }

    //获取小区地址
    public String getAddressName() {
        return sharedPreferences.getString("xiaoqu_address", "");
    }
    //设置小区地址
    public void setAddressName(String xiaoqu_name) {
        editor.putString("xiaoqu_address", xiaoqu_name);
        editor.commit();
    }

    //免密accessToken
    public String getAcceessToken() {
        return sharedPreferences.getString("accessToken", "");
    }

    public void setAcceessToken(String accessToken) {
        editor.putString("accessToken", accessToken);
        editor.commit();
    }

    //免密refreshToken
    public String getRefreshToken() {
        return sharedPreferences.getString("refreshToken", "");
    }

    public void setRefreshToken(String refreshToken) {
        editor.putString("refreshToken", refreshToken);
        editor.commit();
    }

    //免密openId
    public String getOpenId() {
        return sharedPreferences.getString("openId", "");
    }

    public void setOpenId(String openId) {
        editor.putString("openId", openId);
        editor.commit();
    }

    //免密timeStamp
    public String getTimeStamp() {
        return sharedPreferences.getString("timeStamp", "");
    }

    public void setTimeStamp(String timeStamp) {
        editor.putString("timeStamp", timeStamp);
        editor.commit();
    }

    //免密tel
    public String getTel() {
        return sharedPreferences.getString("tel", "");
    }

    public void setTel(String tel) {
        editor.putString("tel", tel);
        editor.commit();
    }

    //选择小区
    public String getIsChooseXiaoqu() {
        return sharedPreferences.getString("isChooseXiaoqu", "0");
    }

    public void setIsChooseXiaoqu(String isChooseXiaoqu) {
        editor.putString("isChooseXiaoqu", isChooseXiaoqu);
        editor.commit();
    }

    //首页选择的小区id
    public String getIsNew() {
        return sharedPreferences.getString("is_new", "0");
    }

    public void setIsNew(String isNew) {
        editor.putString("is_new", isNew);
        editor.commit();
    }
    //清除sp的数据
    public  void clearPreference(Context context ) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //获取夜间模式
    public boolean getNightMode() {
        return sharedPreferences.getBoolean("night_mode", false);
    }
    //设置夜间模式
    public void setNightMode(boolean nightMode) {
        editor.putBoolean("night_mode", nightMode);
        editor.commit();
    }
}
