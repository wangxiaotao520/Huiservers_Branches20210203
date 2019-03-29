package com.huacheng.huiservers.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class ModelUser {

    @Id
    private Long uid;
    private String username;
    private String fullname;
    private String nickname;
    private String utype;
    private String pwd;
    private String avatars;
    private String sex;
    private String birthday;
    private String email;
    private String company_id;
    private String community_id;
    private String is_bind_property;
    private String reg_time;
    private String last_login_time;
    private String last_login_ip;
    private String status;
    private String phone_name;
    private String phone_type;
    private String weixin_nick;
    private String weixin_openid;
    private String wx_bindingtime;
    private String recommended_uid;
    private String tokenTime;
    private String token;
    private String tokenSecret;
    private String source;
    @Generated(hash = 2131060770)
    public ModelUser(Long uid, String username, String fullname, String nickname, String utype, String pwd, String avatars, String sex, String birthday,
            String email, String company_id, String community_id, String is_bind_property, String reg_time, String last_login_time, String last_login_ip,
            String status, String phone_name, String phone_type, String weixin_nick, String weixin_openid, String wx_bindingtime, String recommended_uid,
            String tokenTime, String token, String tokenSecret, String source) {
        this.uid = uid;
        this.username = username;
        this.fullname = fullname;
        this.nickname = nickname;
        this.utype = utype;
        this.pwd = pwd;
        this.avatars = avatars;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.company_id = company_id;
        this.community_id = community_id;
        this.is_bind_property = is_bind_property;
        this.reg_time = reg_time;
        this.last_login_time = last_login_time;
        this.last_login_ip = last_login_ip;
        this.status = status;
        this.phone_name = phone_name;
        this.phone_type = phone_type;
        this.weixin_nick = weixin_nick;
        this.weixin_openid = weixin_openid;
        this.wx_bindingtime = wx_bindingtime;
        this.recommended_uid = recommended_uid;
        this.tokenTime = tokenTime;
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.source = source;
    }
    @Generated(hash = 1822526350)
    public ModelUser() {
    }
    public Long getUid() {
        return this.uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullname() {
        return this.fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getUtype() {
        return this.utype;
    }
    public void setUtype(String utype) {
        this.utype = utype;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getAvatars() {
        return this.avatars;
    }
    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCompany_id() {
        return this.company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    public String getCommunity_id() {
        return this.community_id;
    }
    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }
    public String getIs_bind_property() {
        return this.is_bind_property;
    }
    public void setIs_bind_property(String is_bind_property) {
        this.is_bind_property = is_bind_property;
    }
    public String getReg_time() {
        return this.reg_time;
    }
    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }
    public String getLast_login_time() {
        return this.last_login_time;
    }
    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }
    public String getLast_login_ip() {
        return this.last_login_ip;
    }
    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPhone_name() {
        return this.phone_name;
    }
    public void setPhone_name(String phone_name) {
        this.phone_name = phone_name;
    }
    public String getPhone_type() {
        return this.phone_type;
    }
    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }
    public String getWeixin_nick() {
        return this.weixin_nick;
    }
    public void setWeixin_nick(String weixin_nick) {
        this.weixin_nick = weixin_nick;
    }
    public String getWeixin_openid() {
        return this.weixin_openid;
    }
    public void setWeixin_openid(String weixin_openid) {
        this.weixin_openid = weixin_openid;
    }
    public String getWx_bindingtime() {
        return this.wx_bindingtime;
    }
    public void setWx_bindingtime(String wx_bindingtime) {
        this.wx_bindingtime = wx_bindingtime;
    }
    public String getRecommended_uid() {
        return this.recommended_uid;
    }
    public void setRecommended_uid(String recommended_uid) {
        this.recommended_uid = recommended_uid;
    }
    public String getTokenTime() {
        return this.tokenTime;
    }
    public void setTokenTime(String tokenTime) {
        this.tokenTime = tokenTime;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getTokenSecret() {
        return this.tokenSecret;
    }
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }


}
