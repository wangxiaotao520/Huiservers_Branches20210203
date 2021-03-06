package com.huacheng.huiservers.utils;


import com.huacheng.huiservers.http.okhttp.ApiHttpClient;

/**
 * Description: 登录工具类
 * created by wangxiaotao
 * 2018/10/5 0005 下午 3:37
 */
public class LoginUtils {
    /**
     * 判断用户是否登录（启动页不可以用）
     * @return
     */
    public static boolean hasLoginUser(){
        if (ApiHttpClient.TOKEN_SECRET==null||ApiHttpClient.TOKEN==null){
            //只要token为空，就说明没有登录
            return false;
        }
        return  true;
    }
}
