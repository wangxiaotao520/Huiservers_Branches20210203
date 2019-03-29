package com.huacheng.huiservers.db;

import android.content.Context;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.model.ModelUser;

import java.util.List;


/**
 * Description: 用户数据库类
 * created by wangxiaotao
 * 2018/6/21 0021 上午 11:30
 */
public class UserSql extends  BaseDao<ModelUser> {

    private volatile  static UserSql userSql;//多线程访问
    public UserSql(Context context) {
        super(context);
    }

    /**
     * 使用单例模式获得操作数据库的对象
     * @return
     */
    public  static UserSql getInstance(){
        UserSql instance = null;
        if (userSql==null){
            synchronized (UserSql.class){
                if (instance==null){
                    instance = new UserSql(BaseApplication.getContext());
                    userSql = instance;
                }
            }
        }

        return userSql;
    }

    /**
     * 清除数据库，退出登录时调用
     */
    public  void clear() {
        deleteAll(ModelUser.class);
    }

    /**
     * 判断是否存在登录用户
     * @return
     */
    public ModelUser hasLoginUser(){
        ModelUser user=null;
        List<ModelUser> users = QueryAll(ModelUser.class);
        if (users.size()>0){
            user=users.get(0);
            BaseApplication.setUser(user);
       //     ApiHttpClient.setTokenInfo(user.getToken(),user.getTokenSecret());
        }
        return user;
    }

}
