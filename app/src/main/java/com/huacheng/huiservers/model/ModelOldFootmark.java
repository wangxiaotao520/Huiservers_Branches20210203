package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：
 * 时间：2020/10/3 11:35
 * created by DFF
 */
public class ModelOldFootmark implements Serializable {

    private String S;

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    /**
     * CityNum : 1
     * PosNum : 13
     * Pos : [{"City":"晋中市","Detail":[{"_id":"cede775f936448f65ae76ab9","Lon":112.7282221,"Lat":37.688951,"Radius":25,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:14:41","UT":"2020-10-03 10:14:42","Tag":0,"stopT":""},{"_id":"0adf775f936448f65ab46db9","Lon":112.72824,"Lat":37.6888829,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:15:42","UT":"2020-10-03 10:15:42","Tag":0,"stopT":""},{"_id":"46df775f9f0fc0005b3ce8dd","Lon":112.7282823,"Lat":37.6889016,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:16:41","UT":"2020-10-03 10:17:29","Tag":0,"stopT":""},{"_id":"82df775f9f0fc0005bd1eadd","Lon":112.7282737,"Lat":37.6889119,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:17:42","UT":"2020-10-03 10:17:42","Tag":0,"stopT":""},{"_id":"bedf775f9f0fc0005b4ceddd","Lon":112.728249,"Lat":37.6889263,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:18:42","UT":"2020-10-03 10:18:42","Tag":0,"stopT":""},{"_id":"fadf775f9f0fc0005b7cf0dd","Lon":112.7282376,"Lat":37.6889144,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:19:42","UT":"2020-10-03 10:19:42","Tag":0,"stopT":""},{"_id":"36e0775f9f0fc0005b70f3dd","Lon":112.7282511,"Lat":37.6889345,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:20:42","UT":"2020-10-03 10:20:42","Tag":0,"stopT":""},{"_id":"72e0775f9f0fc0005b34f6dd","Lon":112.7282696,"Lat":37.6889117,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:21:42","UT":"2020-10-03 10:22:29","Tag":0,"stopT":""},{"_id":"aee0775f9f0fc0005bd6f8dd","Lon":112.7282701,"Lat":37.6889065,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:22:42","UT":"2020-10-03 10:22:42","Tag":0,"stopT":""},{"_id":"eae0775f9f0fc0005b45fbdd","Lon":112.728259,"Lat":37.6889285,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:23:42","UT":"2020-10-03 10:23:42","Tag":0,"stopT":""},{"_id":"26e1775f9f0fc0005bb7fddd","Lon":112.7282484,"Lat":37.6889147,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:24:42","UT":"2020-10-03 10:24:42","Tag":0,"stopT":""},{"_id":"62e1775f9f0fc0005ba400de","Lon":112.7282465,"Lat":37.6889217,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:25:42","UT":"2020-10-03 10:25:42","Tag":0,"stopT":""},{"_id":"7890485f5fe8f89d067f6d78","Lon":112.728294,"Lat":37.6888821,"Radius":50,"Dist":"榆次区","Str":"新建街道汇通路辅路","CT":"2020-10-03 10:26:42","UT":"2020-10-03 11:32:30","Tag":1,"stopT":"停留1小时5分"}]}]
     */

    private int CityNum;
    private int PosNum;
    private List<PosBean> Pos;

    public int getCityNum() {
        return CityNum;
    }

    public void setCityNum(int CityNum) {
        this.CityNum = CityNum;
    }

    public int getPosNum() {
        return PosNum;
    }

    public void setPosNum(int PosNum) {
        this.PosNum = PosNum;
    }

    public List<PosBean> getPos() {
        return Pos;
    }

    public void setPos(List<PosBean> Pos) {
        this.Pos = Pos;
    }

    public static class PosBean {
        /**
         * City : 晋中市
         * Detail : [{"_id":"cede775f936448f65ae76ab9","Lon":112.7282221,"Lat":37.688951,"Radius":25,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:14:41","UT":"2020-10-03 10:14:42","Tag":0,"stopT":""},{"_id":"0adf775f936448f65ab46db9","Lon":112.72824,"Lat":37.6888829,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:15:42","UT":"2020-10-03 10:15:42","Tag":0,"stopT":""},{"_id":"46df775f9f0fc0005b3ce8dd","Lon":112.7282823,"Lat":37.6889016,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:16:41","UT":"2020-10-03 10:17:29","Tag":0,"stopT":""},{"_id":"82df775f9f0fc0005bd1eadd","Lon":112.7282737,"Lat":37.6889119,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:17:42","UT":"2020-10-03 10:17:42","Tag":0,"stopT":""},{"_id":"bedf775f9f0fc0005b4ceddd","Lon":112.728249,"Lat":37.6889263,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:18:42","UT":"2020-10-03 10:18:42","Tag":0,"stopT":""},{"_id":"fadf775f9f0fc0005b7cf0dd","Lon":112.7282376,"Lat":37.6889144,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:19:42","UT":"2020-10-03 10:19:42","Tag":0,"stopT":""},{"_id":"36e0775f9f0fc0005b70f3dd","Lon":112.7282511,"Lat":37.6889345,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:20:42","UT":"2020-10-03 10:20:42","Tag":0,"stopT":""},{"_id":"72e0775f9f0fc0005b34f6dd","Lon":112.7282696,"Lat":37.6889117,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:21:42","UT":"2020-10-03 10:22:29","Tag":0,"stopT":""},{"_id":"aee0775f9f0fc0005bd6f8dd","Lon":112.7282701,"Lat":37.6889065,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:22:42","UT":"2020-10-03 10:22:42","Tag":0,"stopT":""},{"_id":"eae0775f9f0fc0005b45fbdd","Lon":112.728259,"Lat":37.6889285,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:23:42","UT":"2020-10-03 10:23:42","Tag":0,"stopT":""},{"_id":"26e1775f9f0fc0005bb7fddd","Lon":112.7282484,"Lat":37.6889147,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:24:42","UT":"2020-10-03 10:24:42","Tag":0,"stopT":""},{"_id":"62e1775f9f0fc0005ba400de","Lon":112.7282465,"Lat":37.6889217,"Radius":50,"Dist":"榆次区","Str":"到新建街道汇通路辅路","CT":"2020-10-03 10:25:42","UT":"2020-10-03 10:25:42","Tag":0,"stopT":""},{"_id":"7890485f5fe8f89d067f6d78","Lon":112.728294,"Lat":37.6888821,"Radius":50,"Dist":"榆次区","Str":"新建街道汇通路辅路","CT":"2020-10-03 10:26:42","UT":"2020-10-03 11:32:30","Tag":1,"stopT":"停留1小时5分"}]
         */

        private String City;
        private List<DetailBean> Detail;

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public List<DetailBean> getDetail() {
            return Detail;
        }

        public void setDetail(List<DetailBean> Detail) {
            this.Detail = Detail;
        }

        public static class DetailBean {
            /**
             * _id : cede775f936448f65ae76ab9
             * Lon : 112.7282221
             * Lat : 37.688951
             * Radius : 25
             * Dist : 榆次区
             * Str : 到新建街道汇通路辅路
             * CT : 2020-10-03 10:14:41
             * UT : 2020-10-03 10:14:42
             * Tag : 0
             * stopT :
             */

            private String _id;
            private double Lon;
            private double Lat;
            private int Radius;
            private String Dist;
            private String Str;
            private String CT;
            private String UT;
            private int Tag;
            private String stopT;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public double getLon() {
                return Lon;
            }

            public void setLon(double Lon) {
                this.Lon = Lon;
            }

            public double getLat() {
                return Lat;
            }

            public void setLat(double Lat) {
                this.Lat = Lat;
            }

            public int getRadius() {
                return Radius;
            }

            public void setRadius(int Radius) {
                this.Radius = Radius;
            }

            public String getDist() {
                return Dist;
            }

            public void setDist(String Dist) {
                this.Dist = Dist;
            }

            public String getStr() {
                return Str;
            }

            public void setStr(String Str) {
                this.Str = Str;
            }

            public String getCT() {
                return CT;
            }

            public void setCT(String CT) {
                this.CT = CT;
            }

            public String getUT() {
                return UT;
            }

            public void setUT(String UT) {
                this.UT = UT;
            }

            public int getTag() {
                return Tag;
            }

            public void setTag(int Tag) {
                this.Tag = Tag;
            }

            public String getStopT() {
                return stopT;
            }

            public void setStopT(String stopT) {
                this.stopT = stopT;
            }
        }
    }
}
