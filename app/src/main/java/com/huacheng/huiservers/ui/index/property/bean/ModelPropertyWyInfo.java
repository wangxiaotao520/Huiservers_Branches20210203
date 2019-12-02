package com.huacheng.huiservers.ui.index.property.bean;

import com.huacheng.huiservers.ui.center.bean.HouseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：
 * 时间：2018/8/11 17:04
 * created by DFF
 */
public class ModelPropertyWyInfo {

    private RoomInfoBean room_info;
    private int is_available;
    private String is_available_cn;
    private int is_property;
    private String is_property_cn;
    private SFaddDFBean shuifei;
    private SFaddDFBean dianfei;
    //获取确认账单信息
    private String nickname;
    private String order_num;
    private String address;
    private String addtime;
    private ModelPropertyWyInfo wuye;
    private List<List<ModelWuye>> list;
    private double tot_sumvalue;
    private int is_android_electric; //是否开启电充值：0为开启，1为关闭

    private int is_android_water;    //是否开启水充值：0为开启，1为关闭


    private int totalPages;

    public int getIs_property() {
        return is_property;
    }

    public void setIs_property(int is_property) {
        this.is_property = is_property;
    }

    public String getIs_property_cn() {
        return is_property_cn;
    }

    public void setIs_property_cn(String is_property_cn) {
        this.is_property_cn = is_property_cn;
    }

    //缴费记录
    private RoomInfoBean info;

    public RoomInfoBean getInfo() {
        return info;
    }

    public void setInfo(RoomInfoBean info) {
        this.info = info;
    }

    public ModelPropertyWyInfo getWuye() {
        return wuye;
    }

    public void setWuye(ModelPropertyWyInfo wuye) {
        this.wuye = wuye;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public List<List<ModelWuye>> getList() {
        return list;
    }

    public void setList(List<List<ModelWuye>> list) {
        this.list = list;
    }

    public double getTot_sumvalue() {
        return tot_sumvalue;
    }

    public void setTot_sumvalue(double tot_sumvalue) {
        this.tot_sumvalue = tot_sumvalue;
    }

    public RoomInfoBean getRoom_info() {
        return room_info;
    }

    public void setRoom_info(RoomInfoBean room_info) {
        this.room_info = room_info;
    }

    public int getIs_available() {
        return is_available;
    }

    public void setIs_available(int is_available) {
        this.is_available = is_available;
    }

    public String getIs_available_cn() {
        return is_available_cn;
    }

    public void setIs_available_cn(String is_available_cn) {
        this.is_available_cn = is_available_cn;
    }

    public SFaddDFBean getShuifei() {
        return shuifei;
    }

    public void setShuifei(SFaddDFBean shuifei) {
        this.shuifei = shuifei;
    }

    public SFaddDFBean getDianfei() {
        return dianfei;
    }

    public void setDianfei(SFaddDFBean dianfei) {
        this.dianfei = dianfei;
    }

    public static class RoomInfoBean implements Serializable {

        private String building_name;
        private String unit;
        private String community_name;
        private String address;
        private String room_id;
        private String code;
        private String community_id;
        private String fullname;
        private String name;
        private String order_num;
        private String addtime;
        private String tot_sumvalue;
        private String pay_time;
        private String count;//

        private String tot_refund;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTot_sumvalue() {
            return tot_sumvalue;
        }

        public void setTot_sumvalue(String tot_sumvalue) {
            this.tot_sumvalue = tot_sumvalue;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getBuilding_name() {
            return building_name;
        }

        public void setBuilding_name(String building_name) {
            this.building_name = building_name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getTot_refund() {
            return tot_refund;
        }

        public void setTot_refund(String tot_refund) {
            this.tot_refund = tot_refund;
        }

    }
    public static class SFaddDFBean {


        private String type;
        private String type_cn;
        private HouseBean info;
        private Double upper_limit;

        public String getType_cn() {
            return type_cn;
        }

        public void setType_cn(String type_cn) {
            this.type_cn = type_cn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public HouseBean getInfo() {
            return info;
        }

        public void setInfo(HouseBean info) {
            this.info = info;
        }

        public Double getUpper_limit() {
            return upper_limit;
        }

        public void setUpper_limit(Double upper_limit) {
            this.upper_limit = upper_limit;
        }
    }

    public int getIs_android_electric() {
        return is_android_electric;
    }

    public void setIs_android_electric(int is_android_electric) {
        this.is_android_electric = is_android_electric;
    }

    public int getIs_android_water() {
        return is_android_water;
    }

    public void setIs_android_water(int is_android_water) {
        this.is_android_water = is_android_water;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
