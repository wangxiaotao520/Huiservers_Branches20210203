package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/12/3
 * @description
 */
public class ArticalCollect {


    /**
     * status : 1
     * msg : 操作成功！
     * data : {"list":[{"collection_id":"16","p_id":"1638","c_name":"社区公告","title":"56S+5Yy65oWn55Sf5rS75oub4oCc5oWn6ICB5p2/4oCd5LqG77yM5pyN5Yqh5p2/5Z2X562J5L2g5p2l77yB","click":"235","addtime":"1575426985","img":"huacheng/social/19/12/04/5de71ba94acbd.png"},{"collection_id":"17","p_id":"1627","c_name":"社区公告","title":"5oSf5oGp6IqCIHwg5L2g5Lul5Li65oiR5Y+q5Lya5ZiY5a+S6Zeu5pqW77yf5LiN77yM5bCP5oWn6KaB57uZ5L2g6YCB5Lic6KW/77yB","click":"67","addtime":"1574992193","img":"huacheng/social/19/11/29/5de0794132800.png"}],"total_Pages":1}
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
         * list : [{"collection_id":"16","p_id":"1638","c_name":"社区公告","title":"56S+5Yy65oWn55Sf5rS75oub4oCc5oWn6ICB5p2/4oCd5LqG77yM5pyN5Yqh5p2/5Z2X562J5L2g5p2l77yB","click":"235","addtime":"1575426985","img":"huacheng/social/19/12/04/5de71ba94acbd.png"},{"collection_id":"17","p_id":"1627","c_name":"社区公告","title":"5oSf5oGp6IqCIHwg5L2g5Lul5Li65oiR5Y+q5Lya5ZiY5a+S6Zeu5pqW77yf5LiN77yM5bCP5oWn6KaB57uZ5L2g6YCB5Lic6KW/77yB","click":"67","addtime":"1574992193","img":"huacheng/social/19/11/29/5de0794132800.png"}]
         * total_Pages : 1
         */

        private int total_Pages;
        private List<ListBean> list;

        public int getTotal_Pages() {
            return total_Pages;
        }

        public void setTotal_Pages(int total_Pages) {
            this.total_Pages = total_Pages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * collection_id : 16
             * p_id : 1638
             * c_name : 社区公告
             * title : 56S+5Yy65oWn55Sf5rS75oub4oCc5oWn6ICB5p2/4oCd5LqG77yM5pyN5Yqh5p2/5Z2X562J5L2g5p2l77yB
             * click : 235
             * addtime : 1575426985
             * img : huacheng/social/19/12/04/5de71ba94acbd.png
             */

            private String collection_id;
            private String p_id;
            private String c_name;
            private String title;
            private String click;
            private String addtime;
            private String img;

            public String getCollection_id() {
                return collection_id;
            }

            public void setCollection_id(String collection_id) {
                this.collection_id = collection_id;
            }

            public String getP_id() {
                return p_id;
            }

            public void setP_id(String p_id) {
                this.p_id = p_id;
            }

            public String getC_name() {
                return c_name;
            }

            public void setC_name(String c_name) {
                this.c_name = c_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
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
