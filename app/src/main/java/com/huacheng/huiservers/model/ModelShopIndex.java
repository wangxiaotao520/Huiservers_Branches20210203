package com.huacheng.huiservers.model;

import java.util.List;

/**
 * 类：
 * 时间：2018/5/28 20:12
 * 功能描述:Huiservers
 */
public class ModelShopIndex {

    private List<ModelShopIndex> cate_list;
    private List<ModelShopIndex> pro_list;
    private List<ModelVBaner> article_one;
    private ModelShopIndex pro_discount_list;
    private String id;
    private String parent_id;
    private String cate_name;
    private String cate_img;
    private String icon;
    private String title;
    private String content;
    private String title_img;
    private String title_thumb_img;
    private String cate_tag_id;
    private String send_shop_id;
    private String addtime;
    private String price;
    private String original;
    private String unit;
    private String exist_hours;
    private String hot;
    private String is_hot;
    private String is_new;
    private String is_limit;
    private String is_time;
    private String discount;
    private String distance_start;
    private String distance_end;
    private String tagname;
    private String tagid;
    private String inventory;
    private String shop_cate_stime;
    private String shop_cate_etime;
    private String order_num;
    private String address;
    private String merchant_name;
    private String logo;
    private String background;
    private List<ModelShopIndex> goods_tag;
    private List<ModelShopIndex> goods;
    private List<ModelShopIndex> list;
    private String c_img;
    private String c_name;
    private String total_Pages;
    private long  current_times;//倒计时时用来的时间
    private String  topclass;
    private String  banner;
    private String  is_article;

    private String description;//首页副标题


    private String icon_img;//特卖专场的图片
    private int  totalPages;


    private String pension_display;//是否是养老  1不是 2是

    public List<ModelVBaner> getArticle_one() {
        return article_one;
    }

    public void setArticle_one(List<ModelVBaner> article_one) {
        this.article_one = article_one;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIs_article() {
        return is_article;
    }

    public void setIs_article(String is_article) {
        this.is_article = is_article;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public List<ModelShopIndex> getGoods() {
        return goods;
    }

    public void setGoods(List<ModelShopIndex> goods) {
        this.goods = goods;
    }

    public String getIs_time() {
        return is_time;
    }

    public void setIs_time(String is_time) {
        this.is_time = is_time;
    }

    public String getTopclass() {
        return topclass;
    }

    public void setTopclass(String topclass) {
        this.topclass = topclass;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getDistance_start() {
        return distance_start;
    }

    public void setDistance_start(String distance_start) {
        this.distance_start = distance_start;
    }

    public String getDistance_end() {
        return distance_end;
    }

    public void setDistance_end(String distance_end) {
        this.distance_end = distance_end;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public ModelShopIndex getPro_discount_list() {
        return pro_discount_list;
    }
    public void setPro_discount_list(ModelShopIndex pro_discount_list) {
        this.pro_discount_list = pro_discount_list;
    }
    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public String getShop_cate_stime() {
        return shop_cate_stime;
    }

    public void setShop_cate_stime(String shop_cate_stime) {
        this.shop_cate_stime = shop_cate_stime;
    }

    public String getShop_cate_etime() {
        return shop_cate_etime;
    }

    public void setShop_cate_etime(String shop_cate_etime) {
        this.shop_cate_etime = shop_cate_etime;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getIs_limit() {
        return is_limit;
    }

    public void setIs_limit(String is_limit) {
        this.is_limit = is_limit;
    }

    public String getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(String total_Pages) {
        this.total_Pages = total_Pages;
    }

    public List<ModelShopIndex> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<ModelShopIndex> cate_list) {
        this.cate_list = cate_list;
    }

    public List<ModelShopIndex> getPro_list() {
        return pro_list;
    }

    public void setPro_list(List<ModelShopIndex> pro_list) {
        this.pro_list = pro_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getCate_tag_id() {
        return cate_tag_id;
    }

    public void setCate_tag_id(String cate_tag_id) {
        this.cate_tag_id = cate_tag_id;
    }

    public String getSend_shop_id() {
        return send_shop_id;
    }

    public void setSend_shop_id(String send_shop_id) {
        this.send_shop_id = send_shop_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExist_hours() {
        return exist_hours;
    }

    public void setExist_hours(String exist_hours) {
        this.exist_hours = exist_hours;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public List<ModelShopIndex> getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(List<ModelShopIndex> goods_tag) {
        this.goods_tag = goods_tag;
    }

    public List<ModelShopIndex> getList() {
        return list;
    }

    public void setList(List<ModelShopIndex> list) {
        this.list = list;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }


    public long getCurrent_times() {
        return current_times;
    }

    public void setCurrent_times(long current_times) {
        this.current_times = current_times;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }


    public String getPension_display() {
        return pension_display;
    }

    public void setPension_display(String pension_display) {
        this.pension_display = pension_display;
    }
}
