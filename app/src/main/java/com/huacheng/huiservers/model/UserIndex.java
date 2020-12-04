package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by changyadong on 2020/11/28
 * @description
 */
public class UserIndex implements Serializable {


    /**
     * status : 1
     * msg : 获取数据成功！
     * data : {"nickname":"手机用户601680","avatars":"huacheng/center_avatars/2.jpg","is_vip":"0","rank":"0","points":"0","user_level":"Level0","next_rank":200,"next_level":"Level1","level":[{"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"},{"id":"2","name":"Level1","rank":"500","right":"1,2","img":"","addtime":"1606270815"},{"id":"3","name":"Level2","rank":"1000","right":"1,2,3","img":"","addtime":"1606270815"},{"id":"4","name":"Level3","rank":"2000","right":"1,2,3,4","img":"","addtime":"1606270815"},{"id":"5","name":"Level4","rank":"5000","right":"1,2,3,4,5","img":"","addtime":"1606270815"},{"id":"6","name":"Level5","rank":"8000","right":"1,2,3,4,5,6","img":"","addtime":"1606270815"},{"id":"7","name":"Level6","rank":"8000","right":"1,2,3,4,5,6,7","img":"","addtime":"1606270815"}],"user_right":[{"name":"签到送积分","img":""}]}
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
         * nickname : 手机用户601680
         * avatars : huacheng/center_avatars/2.jpg
         * is_vip : 0
         * rank : 0
         * points : 0
         * user_level : Level0
         * next_rank : 200
         * next_level : Level1
         * level : [{"id":"1","name":"Level0","rank":"200","right":"1","img":"","addtime":"1606270815"},{"id":"2","name":"Level1","rank":"500","right":"1,2","img":"","addtime":"1606270815"},{"id":"3","name":"Level2","rank":"1000","right":"1,2,3","img":"","addtime":"1606270815"},{"id":"4","name":"Level3","rank":"2000","right":"1,2,3,4","img":"","addtime":"1606270815"},{"id":"5","name":"Level4","rank":"5000","right":"1,2,3,4,5","img":"","addtime":"1606270815"},{"id":"6","name":"Level5","rank":"8000","right":"1,2,3,4,5,6","img":"","addtime":"1606270815"},{"id":"7","name":"Level6","rank":"8000","right":"1,2,3,4,5,6,7","img":"","addtime":"1606270815"}]
         * user_right : [{"name":"签到送积分","img":""}]
         */

        private String nickname;
        private String avatars;
        private String is_vip;
        private String rank;
        private String points;
        private String user_level;
        private int next_rank;
        private String next_level;
        private List<LevelBean> level;
        private List<UserRightBean> user_right;

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

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getUser_level() {
            return user_level;
        }

        public void setUser_level(String user_level) {
            this.user_level = user_level;
        }

        public int getNext_rank() {
            return next_rank;
        }

        public void setNext_rank(int next_rank) {
            this.next_rank = next_rank;
        }

        public String getNext_level() {
            return next_level;
        }

        public void setNext_level(String next_level) {
            this.next_level = next_level;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public List<UserRightBean> getUser_right() {
            return user_right;
        }

        public void setUser_right(List<UserRightBean> user_right) {
            this.user_right = user_right;
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

        public static class UserRightBean {
            /**
             * name : 签到送积分
             * img :
             */

            private String name;
            private String img;

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
}
