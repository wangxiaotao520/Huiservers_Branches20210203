package com.huacheng.huiservers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class UcenterIndex {

    /**
     * status : 1
     * msg : 成功获取个人信息
     * data : {"uid":"2399","username":"15535406024","fullname":"","nickname":"雨天","avatars":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJXCgJrj89jpFHtuN2lwapQXMf1WAx0xMgmbmof4iatV4mhIgiaLEXvFCnkQq3lF4IjhIjxoEwOXB6Q/132","is_vip":"0","points":"0","rank":"0","old_type":"0","level":[{"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"}],"bind_num":"4","shop_collection":"10","merchant_collection":"10","social_collection":"20","shop_order_1":"6","shop_order_2":"0","shop_order_3":"1","shop_order_4":"0","service_order_1":"0","service_order_2":"0","service_order_3":"0","service_order_4":"0","vip_list":{"8":{"id":"8","name":"2倍积分","img":""},"9":{"id":"9","name":"VIP特价商品","img":""},"10":{"id":"10","name":"VIP特价服务","img":""},"11":{"id":"11","name":"付费身份铭牌","img":""},"12":{"id":"12","name":"专享优惠劵","img":""},"13":{"id":"13","name":"专享折扣","img":""}}}
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
         * uid : 2399
         * username : 15535406024
         * fullname :
         * nickname : 雨天
         * avatars : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJXCgJrj89jpFHtuN2lwapQXMf1WAx0xMgmbmof4iatV4mhIgiaLEXvFCnkQq3lF4IjhIjxoEwOXB6Q/132
         * is_vip : 0
         * points : 0
         * rank : 0
         * old_type : 0
         * level : [{"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"}]
         * bind_num : 4
         * shop_collection : 10
         * merchant_collection : 10
         * social_collection : 20
         * shop_order_1 : 6
         * shop_order_2 : 0
         * shop_order_3 : 1
         * shop_order_4 : 0
         * service_order_1 : 0
         * service_order_2 : 0
         * service_order_3 : 0
         * service_order_4 : 0
         * vip_list : {"8":{"id":"8","name":"2倍积分","img":""},"9":{"id":"9","name":"VIP特价商品","img":""},"10":{"id":"10","name":"VIP特价服务","img":""},"11":{"id":"11","name":"付费身份铭牌","img":""},"12":{"id":"12","name":"专享优惠劵","img":""},"13":{"id":"13","name":"专享折扣","img":""}}
         */

        private String uid;
        private String username;
        private String fullname;
        private String nickname;
        private String avatars;
        private String is_vip;
        private String points;
        private String rank;
        private String old_type;
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
        private VipListBean vip_list;
        private List<LevelBean> level;

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

        public VipListBean getVip_list() {
            return vip_list;
        }

        public void setVip_list(VipListBean vip_list) {
            this.vip_list = vip_list;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public static class VipListBean {
            /**
             * 8 : {"id":"8","name":"2倍积分","img":""}
             * 9 : {"id":"9","name":"VIP特价商品","img":""}
             * 10 : {"id":"10","name":"VIP特价服务","img":""}
             * 11 : {"id":"11","name":"付费身份铭牌","img":""}
             * 12 : {"id":"12","name":"专享优惠劵","img":""}
             * 13 : {"id":"13","name":"专享折扣","img":""}
             */

            @SerializedName("8")
            private _$8Bean _$8;
            @SerializedName("9")
            private _$9Bean _$9;
            @SerializedName("10")
            private _$10Bean _$10;
            @SerializedName("11")
            private _$11Bean _$11;
            @SerializedName("12")
            private _$12Bean _$12;
            @SerializedName("13")
            private _$13Bean _$13;

            public _$8Bean get_$8() {
                return _$8;
            }

            public void set_$8(_$8Bean _$8) {
                this._$8 = _$8;
            }

            public _$9Bean get_$9() {
                return _$9;
            }

            public void set_$9(_$9Bean _$9) {
                this._$9 = _$9;
            }

            public _$10Bean get_$10() {
                return _$10;
            }

            public void set_$10(_$10Bean _$10) {
                this._$10 = _$10;
            }

            public _$11Bean get_$11() {
                return _$11;
            }

            public void set_$11(_$11Bean _$11) {
                this._$11 = _$11;
            }

            public _$12Bean get_$12() {
                return _$12;
            }

            public void set_$12(_$12Bean _$12) {
                this._$12 = _$12;
            }

            public _$13Bean get_$13() {
                return _$13;
            }

            public void set_$13(_$13Bean _$13) {
                this._$13 = _$13;
            }

            public static class _$8Bean {
                /**
                 * id : 8
                 * name : 2倍积分
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class _$9Bean {
                /**
                 * id : 9
                 * name : VIP特价商品
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class _$10Bean {
                /**
                 * id : 10
                 * name : VIP特价服务
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class _$11Bean {
                /**
                 * id : 11
                 * name : 付费身份铭牌
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class _$12Bean {
                /**
                 * id : 12
                 * name : 专享优惠劵
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class _$13Bean {
                /**
                 * id : 13
                 * name : 专享折扣
                 * img :
                 */

                private String id;
                private String name;
                private String img;

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
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
