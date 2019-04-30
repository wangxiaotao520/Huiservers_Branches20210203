package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：
 * 时间：2019/4/11 16:50
 * created by DFF
 */
public class ModelNewWorkOrder implements Serializable {

    /**
     * id : 17
     * work_type : 1
     * order_number : wo-83190410181924
     * appointime : 2019-04-10 12:13
     * cate_pid_cn : 清洗家居
     * degree : 1
     * content : 测试备注
     * username : 13513541302
     * nickname : 一羞哥
     * address : 安宁新村1-1-101
     * img_list : [{"id":"11","work_id":"17","log_id":"17","type":"0","img_path":"huacheng/work/19/04/10/","img_name":"5cadc32cf20b3.jpg","addtime":"1554891565"}]
     * work_user : [{"id":"61","head_img":"","name":"景贵桃","phone":"","attribute":"1","status":"1","acceptime":"2019-04-10 12:13"}]
     * work_log : [{"id":"21","work_id":"17","type":"9","operator":"一羞哥","role":"维修工","phone":"13513541302","explain":"完工了","addtime":"2019-04-10 19:04","log_img":[{"id":"15","work_id":"17","log_id":"21","type":"9","img_path":"huacheng/work/19/04/10/","img_name":"5cadcdbf3b5a5.jpg","addtime":"1554894271"}]},{"id":"17","work_id":"17","type":"1","operator":"一羞哥","role":"业主","phone":"13513541302","explain":"业主提交报修订单。","addtime":"2019-04-10 18:19","log_img":[]}]
     */

    private String id;
    private String work_type;
    private String work_stutas;
    private String order_number;
    private String appointime;
    private String cate_pid_cn;
    private String degree;
    private String content;
    private String username;
    private String nickname;
    private String address;
    private String total_fee;
    private String evaluate_status;
    private List<ImgListBean> img_list;
    private List<WorkUserBean> work_user;
    private List<WorkLogBean> work_log;
    /**
     * work_status : 1
     * community_cn : 安宁新村
     * work_status_cn : 待派单
     * address_cn : 安宁新村1-1-101
     */

    private String work_status;
    private String community_cn;
    private String work_status_cn;
    private String address_cn;
    private int totalPages;
    private List<ModelNewWorkOrder> list;

    public String getWork_stutas() {
        return work_stutas;
    }

    public void setWork_stutas(String work_stutas) {
        this.work_stutas = work_stutas;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getEvaluate_status() {
        return evaluate_status;
    }

    public void setEvaluate_status(String evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelNewWorkOrder> getList() {
        return list;
    }

    public void setList(List<ModelNewWorkOrder> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAppointime() {
        return appointime;
    }

    public void setAppointime(String appointime) {
        this.appointime = appointime;
    }

    public String getCate_pid_cn() {
        return cate_pid_cn;
    }

    public void setCate_pid_cn(String cate_pid_cn) {
        this.cate_pid_cn = cate_pid_cn;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ImgListBean> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImgListBean> img_list) {
        this.img_list = img_list;
    }

    public List<WorkUserBean> getWork_user() {
        return work_user;
    }

    public void setWork_user(List<WorkUserBean> work_user) {
        this.work_user = work_user;
    }

    public List<WorkLogBean> getWork_log() {
        return work_log;
    }

    public void setWork_log(List<WorkLogBean> work_log) {
        this.work_log = work_log;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getCommunity_cn() {
        return community_cn;
    }

    public void setCommunity_cn(String community_cn) {
        this.community_cn = community_cn;
    }

    public String getWork_status_cn() {
        return work_status_cn;
    }

    public void setWork_status_cn(String work_status_cn) {
        this.work_status_cn = work_status_cn;
    }

    public String getAddress_cn() {
        return address_cn;
    }

    public void setAddress_cn(String address_cn) {
        this.address_cn = address_cn;
    }

    public static class ImgListBean {
        /**
         * id : 11
         * work_id : 17
         * log_id : 17
         * type : 0
         * img_path : huacheng/work/19/04/10/
         * img_name : 5cadc32cf20b3.jpg
         * addtime : 1554891565
         */

        private String id;
        private String work_id;
        private String log_id;
        private String type;
        private String img_path;
        private String img_name;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }

    public static class WorkUserBean {
        /**
         * id : 61
         * head_img :
         * name : 景贵桃
         * phone :
         * attribute : 1
         * status : 1
         * acceptime : 2019-04-10 12:13
         */

        private String id;
        private String head_img;
        private String name;
        private String phone;
        private String attribute;
        private String status;
        private String acceptime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAcceptime() {
            return acceptime;
        }

        public void setAcceptime(String acceptime) {
            this.acceptime = acceptime;
        }
    }

    public static class WorkLogBean {
        /**
         * id : 21
         * work_id : 17
         * type : 9
         * operator : 一羞哥
         * role : 维修工
         * phone : 13513541302
         * explain : 完工了
         * addtime : 2019-04-10 19:04
         * log_img : [{"id":"15","work_id":"17","log_id":"21","type":"9","img_path":"huacheng/work/19/04/10/","img_name":"5cadcdbf3b5a5.jpg","addtime":"1554894271"}]
         */

        private String id;
        private String work_id;
        private String type;
        private String operator;
        private String role;
        private String phone;
        private String explain;
        private String addtime;
        private String type_cn;

        public String getType_cn() {
            return type_cn;
        }

        public void setType_cn(String type_cn) {
            this.type_cn = type_cn;
        }

        private List<ImgListBean> log_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public List<ImgListBean> getLog_img() {
            return log_img;
        }

        public void setLog_img(List<ImgListBean> log_img) {
            this.log_img = log_img;
        }
    }
}
