package com.huacheng.huiservers.model;

/**
 * @author Created by changyadong on 2021/1/18
 * @description
 */
public class IcbcData {

    /**
     * app_id : 10000000000003167139
     * sign_type : RSA2
     * charset : UTF-8
     * format : json
     * ca :
     * app_auth_token :
     * msg_id : 16157
     * timestamp : 2021-01-18 09:51:38
     * encrypt_type : AES
     * biz_content : DtgjR+yjgLDJ9MtQGK9q6G4lCiHecSxCdDs62yE/QwOaTwseeRvGLH5LCSsgcSjRPst5WtDaMm9bwKua2xzoD7HNb0rDR0NE48BXkUu+n8NWYwWK2h6Tm/KYJ4zGt6Nuuqo/+72flqXt8LFQVMWo1coEf8Z/D0eZmaJpExAZ7ZjGFOXC9mJxQit+DN1qxgB+wHfGANBze9G486nH6MCQgbE4ze0EJMalxvaOXKpcb+H38NOsPn8rJQd20VGHsVaFtT5G0SBU++4XPhJa8UjuhLbRxvgZX3kUlpD7LFyoLGoBDyPzwZdmbM4qxCwyOItTVSFnO7Hf9K8mkvXK+WTwTl8+L0MKje2pSms4rYElPNgapKJV19P34+QUX/bKSNUmBVF4Q8FX5vBSbjP90X56+wHO6artC7meGRWBQ7Bu2D0WL7FER8H4rCnX1j/dhgYDLTE6sYxm+gRazHzHZGpnShsM/xgYgAwVfhiqJmZImqfV1LQAc4xaGHqtZo8BmRrkNmwRcarGNQ39fT97ngj8ZcTDLBGyMYqiPU6Ug0tfQb8Q6/mIVNRC39ffp/nNKNWvAL/UktMrzMrS/DmVYcUevoU6OLKyQUQxg0dBQkok2nN895dWdOfBi5hyGeUX9e1YF2P7xqsY65AiWohLITe/utIroINejX13Ir4xjvPvKYE=
     * sign : I0BR9tWfqoB2EAyYDLcIpZW0R4Le0aBITDRE3Ic1fFniaFlXhdB6xBX3GR1QvnO2f2PGpqnS/WdBKQIAqtkFlEL8COwGmU3xiNpg5HUKCx5VEFFqVYg5I7FPYDFPGyCV+m8JanKsSw/GNHsuqoCVmw2uNmuYQf57b6/zCyTYqni9ywSHubOUCbDIn44pnzSxXDW7PbK5oLpOlvnqyXRu6y51kLg/jg3H61DPAwNLTH6ehceG5QU/BrINdprcYG2eW2+APkG5Y6WXTSMPIGdj2kVUnJkyPhs7PRyB8jljw2zScwvD/AIv8trPBu5n4mPO/FtM3TvvF+cK1Q6dPJqRqQ==
     */

    private String app_id;
    private String sign_type;
    private String charset;
    private String format;
    private String ca;
    private String app_auth_token;
    private String msg_id;
    private String timestamp;
    private String encrypt_type;
    private String biz_content;
    private String sign;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getApp_auth_token() {
        return app_auth_token;
    }

    public void setApp_auth_token(String app_auth_token) {
        this.app_auth_token = app_auth_token;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEncrypt_type() {
        return encrypt_type;
    }

    public void setEncrypt_type(String encrypt_type) {
        this.encrypt_type = encrypt_type;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
