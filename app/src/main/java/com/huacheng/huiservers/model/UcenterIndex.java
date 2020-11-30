package com.huacheng.huiservers.model;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class UcenterIndex {


    /**
     * status : 1
     * msg : 成功获取个人信息
     * data : {"uid":"14661","username":"15340800612","fullname":"","nickname":"手机用户601680","avatars":"huacheng/center_avatars/2.jpg","signature":"","is_vip":"0","points":"0","rank":"0","old_type":"1","level":{"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"},"bind_num":"0","shop_collection":"10","merchant_collection":"10","social_collection":"20","shop_order_1":"0","shop_order_2":"0","shop_order_3":"0","shop_order_4":"0","service_order_1":"0","service_order_2":"0","service_order_3":"0","service_order_4":"0","sign_num":"0"}
     * dialog :
     */

    private int status;
    private String msg;
    private DataBean data;
    private String dialog;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public static class DataBean {
        /**
         * uid : 14661
         * username : 15340800612
         * fullname :
         * nickname : 手机用户601680
         * avatars : huacheng/center_avatars/2.jpg
         * signature :
         * is_vip : 0
         * points : 0
         * rank : 0
         * old_type : 1
         * level : {"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"}
         * bind_num : 0
         * shop_collection : 10
         * merchant_collection : 10
         * social_collection : 20
         * shop_order_1 : 0
         * shop_order_2 : 0
         * shop_order_3 : 0
         * shop_order_4 : 0
         * service_order_1 : 0
         * service_order_2 : 0
         * service_order_3 : 0
         * service_order_4 : 0
         * sign_num : 0
         */

        private String uid;
        private String username;
        private String fullname;
        private String nickname;
        private String avatars;
        private String signature;
        private String is_vip;
        private String points;
        private String rank;
        private String old_type;
        private LevelBean level;
        private String bind_num;
        private String shop_collection;
        private String merchant_collection;
        private String social_collection;
        private String shop_order_1;
        private String shop_order_2;
        private String shop_order_3;
        private String shop_order_4;
        private String service_order_1;
        private String service_order_2;
        private String service_order_3;
        private String service_order_4;
        private String sign_num;

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

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatars() {
            return avatars;
        }

        public void setAvatars(String avatars) {
            this.avatars = avatars;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getOld_type() {
            return old_type;
        }

        public void setOld_type(String old_type) {
            this.old_type = old_type;
        }

        public LevelBean getLevel() {
            return level;
        }

        public void setLevel(LevelBean level) {
            this.level = level;
        }

        public String getBind_num() {
            return bind_num;
        }

        public void setBind_num(String bind_num) {
            this.bind_num = bind_num;
        }

        public String getShop_collection() {
            return shop_collection;
        }

        public void setShop_collection(String shop_collection) {
            this.shop_collection = shop_collection;
        }

        public String getMerchant_collection() {
            return merchant_collection;
        }

        public void setMerchant_collection(String merchant_collection) {
            this.merchant_collection = merchant_collection;
        }

        public String getSocial_collection() {
            return social_collection;
        }

        public void setSocial_collection(String social_collection) {
            this.social_collection = social_collection;
        }

        public String getShop_order_1() {
            return shop_order_1;
        }

        public void setShop_order_1(String shop_order_1) {
            this.shop_order_1 = shop_order_1;
        }

        public String getShop_order_2() {
            return shop_order_2;
        }

        public void setShop_order_2(String shop_order_2) {
            this.shop_order_2 = shop_order_2;
        }

        public String getShop_order_3() {
            return shop_order_3;
        }

        public void setShop_order_3(String shop_order_3) {
            this.shop_order_3 = shop_order_3;
        }

        public String getShop_order_4() {
            return shop_order_4;
        }

        public void setShop_order_4(String shop_order_4) {
            this.shop_order_4 = shop_order_4;
        }

        public String getService_order_1() {
            return service_order_1;
        }

        public void setService_order_1(String service_order_1) {
            this.service_order_1 = service_order_1;
        }

        public String getService_order_2() {
            return service_order_2;
        }

        public void setService_order_2(String service_order_2) {
            this.service_order_2 = service_order_2;
        }

        public String getService_order_3() {
            return service_order_3;
        }

        public void setService_order_3(String service_order_3) {
            this.service_order_3 = service_order_3;
        }

        public String getService_order_4() {
            return service_order_4;
        }

        public void setService_order_4(String service_order_4) {
            this.service_order_4 = service_order_4;
        }

        public String getSign_num() {
            return sign_num;
        }

        public void setSign_num(String sign_num) {
            this.sign_num = sign_num;
        }

        public static class LevelBean {
            /**
             * id : 1
             * name : Level0
             * rank : 200
             * right : 1
             * img :
             * addtime : 1606270815
             */

            private String id;
            private String name;
            private String rank;
            private String right;
            private String img;
            private String addtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getRight() {
                return right;
            }

            public void setRight(String right) {
                this.right = right;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
