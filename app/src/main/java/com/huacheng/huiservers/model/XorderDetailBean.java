package com.huacheng.huiservers.model;

import com.huacheng.huiservers.ui.shop.bean.BannerBean;

import java.util.List;

/**
 * 类：
 * 时间：2017/11/6 16:32
 * 功能描述:Huiservers
 */
public class XorderDetailBean {

    private String id;
    private String oid;
    private String status;
    private String p_id;
    private String price;
    private String number;
    private String pay_time;
    private String is_pay;
    private String pay_type;
    private String refundamount;
    private String p_m_id;
    private String addtime;
    private String sendtime;
    private String refundtime;
    private String overtime;
    private String title;
    private String title_img;
    private String title_thumb_img;
    private String m_c_id;
    private String m_c_name;
    private String m_c_amount;
    private String description;
    private String is_shop;
    private int back_type;//1删除 2.评价 3.退款 收货  4 支付成功 5收货
    private int tuikuan_type;
    private int shouhuo_type;
    private int pingjia_type;
    private String dis_fee;
    private String send_type;
    private String contact;
    private String mobile;
    private String address;
    private String express;
    private String order_number;
    private String address_id;
    private String amount;
    private double pro_amount;
    private double send_amount;
    private double coupon_amount;
    private List<XorderDetailBean> mer_list;
    private String p_m_name;
    private String price_num;
    private String pro_num;
    private String p_m_address;
    private List<BannerBean> img;

    public String getP_m_id() {
        return p_m_id;
    }

    public void setP_m_id(String p_m_id) {
        this.p_m_id = p_m_id;
    }

    public int getTuikuan_type() {
        return tuikuan_type;
    }

    public void setTuikuan_type(int tuikuan_type) {
        this.tuikuan_type = tuikuan_type;
    }

    public int getShouhuo_type() {
        return shouhuo_type;
    }

    public void setShouhuo_type(int shouhuo_type) {
        this.shouhuo_type = shouhuo_type;
    }

    public int getPingjia_type() {
        return pingjia_type;
    }

    public void setPingjia_type(int pingjia_type) {
        this.pingjia_type = pingjia_type;
    }

    public double getPro_amount() {
        return pro_amount;
    }

    public double getSend_amount() {
        return send_amount;
    }

    public void setSend_amount(double send_amount) {
        this.send_amount = send_amount;
    }

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setPro_amount(double pro_amount) {
        this.pro_amount = pro_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getP_m_address() {
        return p_m_address;
    }

    public void setP_m_address(String p_m_address) {
        this.p_m_address = p_m_address;
    }

    public List<XorderDetailBean> getMer_list() {
        return mer_list;
    }

    public void setMer_list(List<XorderDetailBean> mer_list) {
        this.mer_list = mer_list;
    }

    public String getP_m_name() {
        return p_m_name;
    }

    public void setP_m_name(String p_m_name) {
        this.p_m_name = p_m_name;
    }

    public String getPrice_num() {
        return price_num;
    }

    public void setPrice_num(String price_num) {
        this.price_num = price_num;
    }

    public String getPro_num() {
        return pro_num;
    }

    public void setPro_num(String pro_num) {
        this.pro_num = pro_num;
    }

    public List<BannerBean> getImg() {
        return img;
    }

    public void setImg(List<BannerBean> img) {
        this.img = img;
    }

    public String getDis_fee() {
        return dis_fee;
    }

    public void setDis_fee(String dis_fee) {
        this.dis_fee = dis_fee;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getBack_type() {
        return back_type;
    }

    public void setBack_type(int back_type) {
        this.back_type = back_type;
    }

    public String getIs_shop() {
        return is_shop;
    }

    public void setIs_shop(String is_shop) {
        this.is_shop = is_shop;
    }

    public String getM_c_amount() {
        return m_c_amount;
    }

    public void setM_c_amount(String m_c_amount) {
        this.m_c_amount = m_c_amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getM_c_id() {
        return m_c_id;
    }

    public void setM_c_id(String m_c_id) {
        this.m_c_id = m_c_id;
    }

    public String getM_c_name() {
        return m_c_name;
    }

    public void setM_c_name(String m_c_name) {
        this.m_c_name = m_c_name;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(String refundamount) {
        this.refundamount = refundamount;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getRefundtime() {
        return refundtime;
    }

    public void setRefundtime(String refundtime) {
        this.refundtime = refundtime;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getTitle_thumb_img() {
        return title_thumb_img;
    }

    public void setTitle_thumb_img(String title_thumb_img) {
        this.title_thumb_img = title_thumb_img;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
