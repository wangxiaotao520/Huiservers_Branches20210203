package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：充电桩
 * 时间：2019/8/20 11:27
 * created by DFF
 */
public class ModelChargeDetail implements Serializable{
    //充电桩信息
    private boolean select;
    private String gtel;
        private String mc;
        private String wz;
        private String dz;
        private String zp;
        private String bz;
        private int gzt;
        private int gls;
        private int glzt1;
        private int glzt2;
        private int glzt3;
        private int glzt4;
        private int glzt5;
        private int glzt6;
        private int glzt7;
        private int glzt8;
        private int glzt9;
        private int glzt10;
        private String price;
        private List<PriceListBean> price_list;

    //充电详情进行中
    /**
     * id : 1
     * pay_time : 1566459000
     * endtime : 1566477000
     * order_price : 1.00
     * times : 5
     * sys_tel : 13393540239
     */
    private String id;
    private long start_time;
    private long endtime;
    private String order_price;
    private String times;
    private String sys_tel;

    private String td;

    //充电详情
    /**
     * order_number : yx-2399190822173205
     * serial_number :
     * uid : 2399
     * username : 15535406024
     * nickname : 121
     * status : 2
     * is_pay : 2
     * pay_time : 1566459000
     * pay_type :
     * cancel_price : 0.57
     * cancel_reason : 用户主动结束充电！
     * cancel_time : 0
     * company_id : 1
     * community_id : 1
     * gtel_cn : 商贸测试
     * endtime : 1566477000
     * reality_price : 0.43
     * reality_times : 2.16
     * reality_endtime : 1566466760
     * end_reason : 用户主动结束充电!
     * addtime : 1566466325
     */

    private String order_number;
    private String serial_number;
    private String uid;
    private String username;
    private String nickname;
    private String status;
    private String is_pay;
    private String pay_time;
    private String pay_type;
    private String cancel_price;
    private String cancel_reason;
    private String cancel_time;
    private String company_id;
    private String community_id;
    private String gtel_cn;
    private String reality_price;
    private String reality_times;
    private String reality_endtime;
    private String end_reason;
    private String addtime;


    public String getGtel() {
            return gtel;
        }

        public void setGtel(String gtel) {
            this.gtel = gtel;
        }

        public String getMc() {
            return mc;
        }

        public void setMc(String mc) {
            this.mc = mc;
        }

        public String getWz() {
            return wz;
        }

        public void setWz(String wz) {
            this.wz = wz;
        }

        public String getDz() {
            return dz;
        }

        public void setDz(String dz) {
            this.dz = dz;
        }

        public String getZp() {
            return zp;
        }

        public void setZp(String zp) {
            this.zp = zp;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public int getGzt() {
            return gzt;
        }

        public void setGzt(int gzt) {
            this.gzt = gzt;
        }

        public int getGls() {
            return gls;
        }

        public void setGls(int gls) {
            this.gls = gls;
        }

        public int getGlzt1() {
            return glzt1;
        }

        public void setGlzt1(int glzt1) {
            this.glzt1 = glzt1;
        }

        public int getGlzt2() {
            return glzt2;
        }

        public void setGlzt2(int glzt2) {
            this.glzt2 = glzt2;
        }

        public int getGlzt3() {
            return glzt3;
        }

        public void setGlzt3(int glzt3) {
            this.glzt3 = glzt3;
        }

        public int getGlzt4() {
            return glzt4;
        }

        public void setGlzt4(int glzt4) {
            this.glzt4 = glzt4;
        }

        public int getGlzt5() {
            return glzt5;
        }

        public void setGlzt5(int glzt5) {
            this.glzt5 = glzt5;
        }

        public int getGlzt6() {
            return glzt6;
        }

        public void setGlzt6(int glzt6) {
            this.glzt6 = glzt6;
        }

        public int getGlzt7() {
            return glzt7;
        }

        public void setGlzt7(int glzt7) {
            this.glzt7 = glzt7;
        }

        public int getGlzt8() {
            return glzt8;
        }

        public void setGlzt8(int glzt8) {
            this.glzt8 = glzt8;
        }

        public int getGlzt9() {
            return glzt9;
        }

        public void setGlzt9(int glzt9) {
            this.glzt9 = glzt9;
        }

        public int getGlzt10() {
            return glzt10;
        }

        public void setGlzt10(int glzt10) {
            this.glzt10 = glzt10;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<PriceListBean> getPrice_list() {
            return price_list;
        }

        public void setPrice_list(List<PriceListBean> price_list) {
            this.price_list = price_list;
        }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long pay_time) {
        this.start_time = pay_time;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getSys_tel() {
        return sys_tel;
    }

    public void setSys_tel(String sys_tel) {
        this.sys_tel = sys_tel;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCancel_price() {
        return cancel_price;
    }

    public void setCancel_price(String cancel_price) {
        this.cancel_price = cancel_price;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getGtel_cn() {
        return gtel_cn;
    }

    public void setGtel_cn(String gtel_cn) {
        this.gtel_cn = gtel_cn;
    }

    public String getReality_price() {
        return reality_price;
    }

    public void setReality_price(String reality_price) {
        this.reality_price = reality_price;
    }

    public String getReality_times() {
        return reality_times;
    }

    public void setReality_times(String reality_times) {
        this.reality_times = reality_times;
    }

    public String getReality_endtime() {
        return reality_endtime;
    }

    public void setReality_endtime(String reality_endtime) {
        this.reality_endtime = reality_endtime;
    }

    public String getEnd_reason() {
        return end_reason;
    }

    public void setEnd_reason(String end_reason) {
        this.end_reason = end_reason;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public static class PriceListBean  implements Serializable{

        /**
             * title : 充满自停
             * order_price : 2
             * times : 10
             */
            private boolean select;
            private String title;
            private String order_price;
            private String times;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

    }

}
