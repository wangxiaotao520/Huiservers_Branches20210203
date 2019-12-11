package com.huacheng.huiservers.http;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;


/**
 * 类：
 * 时间：2018/1/12 16:18
 * 功能描述:Huiservers
 */
public class Url_info {
    private Context context;

    public Url_info(Context mContext) {
        this.context = mContext;
    }

    /**
     * 个人中心
     */
    //访客邀请
    public static  String checkIsAjb = MyCookieStore.SERVERADDRESS + "property/checkIsAjb/" ;
    //我的装修
    public static  String my_renvation = MyCookieStore.SERVERADDRESS + "userCenter/my_decoration/";
    //意见反馈
    public static  String yijian = MyCookieStore.SERVERADDRESS + "userCenter/my_opinion/";
    //关于我们
    public static  String about = MyCookieStore.SERVERADDRESS + "userCenter/about_us/";
    //修改个人信息生日,头像,性别,昵称
    public static  String edit_center = MyCookieStore.SERVERADDRESS + "userCenter/edit_center/";
    //个人中心消费记录
    public static  String my_wallet = MyCookieStore.SERVERADDRESS + "userCenter/my_wallet/";
    //修改密码
    public static  String edit_pass = MyCookieStore.SERVERADDRESS + "userCenter/account/";

    //用户协议
    public static  String user_agreement = MyCookieStore.SERVERADDRESS + "userCenter/user_agreement/";
    //4.0优惠券-领取优惠券
    public static  String coupon_add = MyCookieStore.SERVERADDRESS + "userCenter/coupon_add/";
    //4.0个人中心优惠券详情
    public static  String coupon_details_40 = MyCookieStore.SERVERADDRESS + "userCenter/coupon_details_40/";
    //4.0个人中心已使用优惠券记录列表
    public static  String coupon_over_40 = MyCookieStore.SERVERADDRESS + "userCenter/coupon_over_40/";
    //个人中心优惠券详情页开启使用
    public static  String use_coupon = MyCookieStore.SERVERADDRESS + "userCenter/use_coupon/";


    //个人中心首页
    public static  String center_index = MyCookieStore.SERVERADDRESS + "userCenter/index_40/";
    //获取修改个人信息
    public static  String get_person_index = MyCookieStore.SERVERADDRESS + "userCenter/user_info/";
    //个人中心 添加/修改 收货地址
    public static  String add_user_address = MyCookieStore.SERVERADDRESS + "userCenter/add_user_address/";
    //个人中心获取收货地址列表
    public static  String get_user_address = MyCookieStore.SERVERADDRESS + "userCenter/get_user_address/";//apk_token/" + str_md5 + "/
    //个人中心删除收货地址
    public static  String del_user_address = MyCookieStore.SERVERADDRESS + "userCenter/del_user_address/";
    //个人中心购物订单详情
    public static  String shop_order_details = MyCookieStore.SERVERADDRESS + "userCenter/shopping_order_details/";
    //个人中心购物删除订单
    public static  String del_order = MyCookieStore.SERVERADDRESS + "userCenter/del_shopping_order/";
    //个人中心购物确认收货
    public static  String shop_order_accept = MyCookieStore.SERVERADDRESS + "userCenter/order_accept/";

    //个人中心服务订单导航栏数据
    public static  String service_order_menu = MyCookieStore.SERVERADDRESS + "userCenter/my_service_menu/";
    //支付  商城支付/一卡通发送验证码
    public static  String pay_shopping_order = MyCookieStore.SERVERADDRESS + "userCenter/pay_shopping_order/";
    //支付成功掉区回调接口验证是否成功
    public static  String confirm_order_payment = MyCookieStore.SERVERADDRESS + "userCenter/confirm_order_payment/";
    //支付  一卡通支付信息订单
    public static  String pay_shopping_check = MyCookieStore.SERVERADDRESS + "userCenter/pay_shopping_check/";
    //提交购物订单评价信息
    public static  String shopping_order_score = MyCookieStore.SERVERADDRESS + "userCenter/shopping_order_score/";
    //提交购物订单退款信息
    public static  String shop_refund = MyCookieStore.SERVERADDRESS + "userCenter/refund/";

    /**
     * 商城
     */
    //商城确认订单选择优惠券
    public static  String select_poupon = MyCookieStore.SERVERADDRESS + "shop/select_coupon/";
    //商城商品限购数量
    public static  String shop_limit = MyCookieStore.SERVERADDRESS + "shop/check_shop_limit/";
    //商城商品加入购物车
    public static  String add_shop_cart = MyCookieStore.SERVERADDRESS + "shop/add_shopping_cart/";
    //获取购物车商品数量
    public static  String cart_num = MyCookieStore.SERVERADDRESS + "shop/cart_num/";
    //商城首页数据（限时商品以及精选商品）3.2
    public static  String shop_index_new = MyCookieStore.SERVERADDRESS + "shop/shop_index_new/";
    //居家养老首页数据3.2
    public static  String old_index_new = MyCookieStore.SERVERADDRESS + "shop/old_index_new/";
    //商城列表限时商品正在抢购以及准备活动3.2
    public static  String pro_list = MyCookieStore.SERVERADDRESS + "shop/pro_list/";
    //确认订单信息
    public static  String submit_order_before = MyCookieStore.SERVERADDRESS + "shop/submit_order_before/";
    //提交订单信息
    public static  String submit_order = MyCookieStore.SERVERADDRESS + "shop/submit_order/";
    //确认订单信息查看商品信息
    public static  String order_info = MyCookieStore.SERVERADDRESS + "shop/order_info/";
    //查看商品全部评价
    public static  String goods_review = MyCookieStore.SERVERADDRESS + "shop/goods_review/";
    //搜索商品
    public static  String goods_search = MyCookieStore.SERVERADDRESS + "shop/goods_search/";
    //商城搜索，搜索关键字
    public static  String goods_search_keys = MyCookieStore.SERVERADDRESS + "shop/goods_search_keys/";
    //获取购物车列表
    public static  String shopping_cart = MyCookieStore.SERVERADDRESS + "shop/shopping_cart/";
    //删除购物车列表商品信息
    public static  String del_shopping_cart = MyCookieStore.SERVERADDRESS + "shop/del_shopping_cart/";
    //修改购物车数量校对
    public static  String edit_shopping_cart = MyCookieStore.SERVERADDRESS + "shop/edit_shopping_cart/";
    //一级分类
    public static  String area_topclass = MyCookieStore.SERVERADDRESS + "shop/area_topclass/";
    //3.2商城 商品列表分类数据 接口
    public static  String pro_list_cate = MyCookieStore.SERVERADDRESS + "shop/pro_list_cate/";
    //二三级分类
    public static  String area_category = MyCookieStore.SERVERADDRESS + "shop/area_category/";
    //商品详情
    public static  String goods_details = MyCookieStore.SERVERADDRESS + "shop/goods_details/";
    //商品标签
    public static  String goods_tags = MyCookieStore.SERVERADDRESS + "shop/goods_tags/";
    //限时抢购3.2
    public static  String pro_discount_list = MyCookieStore.SERVERADDRESS + "shop/pro_discount_list/";
    /**
     * 服务/物业
     */
    //个人中心维修订单详情
    public static  String repair_info = MyCookieStore.SERVERADDRESS + "property/my_property_repair_info/";
    //维修支付接口
    public static  String pay_property_order = MyCookieStore.SERVERADDRESS + "property/pay_property_order/";
    //物业绑定小区选择楼号
    public static  String get_pro_building = MyCookieStore.SERVERADDRESS + "property/get_pro_building/";
    //物业绑定小区选择单元
    public static  String get_pro_unit = MyCookieStore.SERVERADDRESS + "property/get_pro_unit/";
    //物业绑定小区选择房间
    public static  String get_pro_room = MyCookieStore.SERVERADDRESS + "property/get_pro_room/";
    //我的住宅
   // public static  String binding_community = MyCookieStore.SERVERADDRESS + "property/binding_community/";

    //确认物业支付订单
    public static  String make_property_order = MyCookieStore.SERVERADDRESS + "property/make_property_order/";
    //获取物业小区
    public static  String get_pro_com = MyCookieStore.SERVERADDRESS + "property/get_pro_com/";
    //获取我的房屋手册
    public static  String get_new_house = MyCookieStore.SERVERADDRESS + "memorandum/get_new_house/";
    //获取我的备忘录
    public static  String get_memorandum = MyCookieStore.SERVERADDRESS + "memorandum/get_memorandum/";
    //添加我的备忘录
    public static  String set_memorandum = MyCookieStore.SERVERADDRESS + "memorandum/set_memorandum/";
    //输入金额 支付 水 / 电表费用（初始订单）
    public static  String property_createorder = MyCookieStore.SERVERADDRESS + "property/create_order/";
    //当面付分类 接口
    public static  String getFacePayCate = MyCookieStore.SERVERADDRESS + "property/face_pay_cate/";
    //当面付 支付订单
    public static  String pay_face_order = MyCookieStore.SERVERADDRESS + "property/pay_face_order/";
    //生成当面付订单
    public static  String addFacePayOrder = MyCookieStore.SERVERADDRESS + "property/add_face_pay_order/";
    //当面付 订单列表(历史记录) 接口
    public static  String face_order_list = MyCookieStore.SERVERADDRESS + "property/face_order_list/";
    //房间号 获取业主信息  接口
    public static  String getRoomPersonalInfo = MyCookieStore.SERVERADDRESS + "property/get_room_personal_info/";
    //商户列表
    public static  String merchant_list = MyCookieStore.SERVERADDRESS + "property/merchant_list/";
    //有限电视提交
    public static  String add_wired_order = MyCookieStore.SERVERADDRESS + "property/add_wired_order/";
    //有限电视支付接口
    public static  String pay_wired_order = MyCookieStore.SERVERADDRESS + "property/pay_wired_order/";
    //有线缴费记录
    public static  String wired_order_list = MyCookieStore.SERVERADDRESS + "property/wired_order_list/";
    //3.3服务支付
    public static  String service_new_pay = ApiHttpClient.API_SERVICE_URL + "order/pay_service_order/";
    /**
     * activity
     */
    //社区活动支付接口
    public static  String pay_activity_order = MyCookieStore.SERVERADDRESS + "activity/pay_activity_order/";
    //社区活动详情
    public static  String activity_details = MyCookieStore.SERVERADDRESS + "activity/activity_details/";
    //社区活动报名
    public static  String activity_enroll = MyCookieStore.SERVERADDRESS + "activity/activity_enroll/";

    /**
     * 登录注册
     */
    //版本更新
    public static  String version_update = MyCookieStore.SERVERADDRESS + "site/version_update/";
    //退出登录  不需要传值  需finish缓存
    public static  String site_logout = MyCookieStore.SERVERADDRESS + "site/logout/";
    //免密登录
    public static  String free_login = MyCookieStore.SERVERADDRESS + "site/free_login/";
    //验证码登录
    public static  String login_verify = MyCookieStore.SERVERADDRESS + "site/login_verify/";
    //登录获取验证码登录
    public static  String reg_send_sms = MyCookieStore.SERVERADDRESS + "site/reg_send_sms/";
    //获取用户信息 -> 电信免密登录
    public static  String get_userInfo = MyCookieStore.SERVERADDRESS + "site/get_userInfo/";
    //注册
    public static  String register = MyCookieStore.SERVERADDRESS + "site/register/";
    //订单完成推送
    public static  String merchant_push = MyCookieStore.SERVERADDRESS + "site/merchant_push";
    //获取广告
    public static  String get_Advertising = MyCookieStore.SERVERADDRESS + "site/get_Advertising/";
    //获取支付类型列表
    public static  String payment_list = MyCookieStore.SERVERADDRESS + "site/payment_list/";
    //分享
    public static  String share_return = MyCookieStore.SERVERADDRESS + "site/share_return/";
    /**
     * 邻里
     */
    //邻里列表
    public static  String getSocialList = MyCookieStore.SERVERADDRESS + "social/get_social_list/";
    //我的邻里
    public static  String get_user_Social = MyCookieStore.SERVERADDRESS + "social/get_user_social_list/";
    //邻里首页右上角接口
    public static  String getSocialNum = MyCookieStore.SERVERADDRESS + "social/social_num/";
    //邻里分类
    public static  String getSocialCategorys = MyCookieStore.SERVERADDRESS + "social/getSocialCategory";
    //邻里详情4.0接口
    public static  String get_social = MyCookieStore.SERVERADDRESS + "social/get_social/";
    //邻里分类
    public static  String getSocialCategory = MyCookieStore.SERVERADDRESS + "social/getSocialCategory/";
    //邻里详情评论4.0
    public static  String social_reply = MyCookieStore.SERVERADDRESS + "social/social_reply/";
    //邻里删除4.0
    public static  String SocialDel = MyCookieStore.SERVERADDRESS + "social/SocialDel/";
    //邻里详情删除
    public static  String social_reply_del = MyCookieStore.SERVERADDRESS + "social/social_reply_del/";
    //物流配送
    public static  String distribution = MyCookieStore.SERVERADDRESS + "Jpush/distribution_push/";

    /**
     * 刷新
     */
    public static void invalidateApi(){
        MyCookieStore.SERVERADDRESS=ApiHttpClient.API_URL+ApiHttpClient.API_VERSION;
          checkIsAjb = MyCookieStore.SERVERADDRESS + "property/checkIsAjb/" ;

          my_renvation = MyCookieStore.SERVERADDRESS + "userCenter/my_decoration/";

          yijian = MyCookieStore.SERVERADDRESS + "userCenter/my_opinion/";

          about = MyCookieStore.SERVERADDRESS + "userCenter/about_us/";

          edit_center = MyCookieStore.SERVERADDRESS + "userCenter/edit_center/";

          my_wallet = MyCookieStore.SERVERADDRESS + "userCenter/my_wallet/";

          edit_pass = MyCookieStore.SERVERADDRESS + "userCenter/account/";

          user_agreement = MyCookieStore.SERVERADDRESS + "userCenter/user_agreement/";

          coupon_add = MyCookieStore.SERVERADDRESS + "userCenter/coupon_add/";

          coupon_details_40 = MyCookieStore.SERVERADDRESS + "userCenter/coupon_details_40/";

          coupon_over_40 = MyCookieStore.SERVERADDRESS + "userCenter/coupon_over_40/";

          use_coupon = MyCookieStore.SERVERADDRESS + "userCenter/use_coupon/";

          center_index = MyCookieStore.SERVERADDRESS + "userCenter/index_40/";

          get_person_index = MyCookieStore.SERVERADDRESS + "userCenter/user_info/";

          add_user_address = MyCookieStore.SERVERADDRESS + "userCenter/add_user_address/";

          get_user_address = MyCookieStore.SERVERADDRESS + "userCenter/get_user_address/";//apk_token/" + str_md5 + "/

          del_user_address = MyCookieStore.SERVERADDRESS + "userCenter/del_user_address/";

          shop_order_details = MyCookieStore.SERVERADDRESS + "userCenter/shopping_order_details/";

          del_order = MyCookieStore.SERVERADDRESS + "userCenter/del_shopping_order/";

          shop_order_accept = MyCookieStore.SERVERADDRESS + "userCenter/order_accept/";

          service_order_menu = MyCookieStore.SERVERADDRESS + "userCenter/my_service_menu/";

          pay_shopping_order = MyCookieStore.SERVERADDRESS + "userCenter/pay_shopping_order/";

          confirm_order_payment = MyCookieStore.SERVERADDRESS + "userCenter/confirm_order_payment/";

          pay_shopping_check = MyCookieStore.SERVERADDRESS + "userCenter/pay_shopping_check/";

          shopping_order_score = MyCookieStore.SERVERADDRESS + "userCenter/shopping_order_score/";

          shop_refund = MyCookieStore.SERVERADDRESS + "userCenter/refund/";

          select_poupon = MyCookieStore.SERVERADDRESS + "shop/select_coupon/";

          shop_limit = MyCookieStore.SERVERADDRESS + "shop/check_shop_limit/";

          add_shop_cart = MyCookieStore.SERVERADDRESS + "shop/add_shopping_cart/";

          cart_num = MyCookieStore.SERVERADDRESS + "shop/cart_num/";

          shop_index_new = MyCookieStore.SERVERADDRESS + "shop/shop_index_new/";

          old_index_new = MyCookieStore.SERVERADDRESS + "shop/old_index_new/";

          pro_list = MyCookieStore.SERVERADDRESS + "shop/pro_list/";

          submit_order_before = MyCookieStore.SERVERADDRESS + "shop/submit_order_before/";

          submit_order = MyCookieStore.SERVERADDRESS + "shop/submit_order/";

          order_info = MyCookieStore.SERVERADDRESS + "shop/order_info/";

          goods_review = MyCookieStore.SERVERADDRESS + "shop/goods_review/";

          goods_search = MyCookieStore.SERVERADDRESS + "shop/goods_search/";

          goods_search_keys = MyCookieStore.SERVERADDRESS + "shop/goods_search_keys/";

          shopping_cart = MyCookieStore.SERVERADDRESS + "shop/shopping_cart/";

          del_shopping_cart = MyCookieStore.SERVERADDRESS + "shop/del_shopping_cart/";

          edit_shopping_cart = MyCookieStore.SERVERADDRESS + "shop/edit_shopping_cart/";

          area_topclass = MyCookieStore.SERVERADDRESS + "shop/area_topclass/";

          pro_list_cate = MyCookieStore.SERVERADDRESS + "shop/pro_list_cate/";

          area_category = MyCookieStore.SERVERADDRESS + "shop/area_category/";

          goods_details = MyCookieStore.SERVERADDRESS + "shop/goods_details/";

          goods_tags = MyCookieStore.SERVERADDRESS + "shop/goods_tags/";

          pro_discount_list = MyCookieStore.SERVERADDRESS + "shop/pro_discount_list/";

          repair_info = MyCookieStore.SERVERADDRESS + "property/my_property_repair_info/";

          pay_property_order = MyCookieStore.SERVERADDRESS + "property/pay_property_order/";

          get_pro_building = MyCookieStore.SERVERADDRESS + "property/get_pro_building/";

          get_pro_unit = MyCookieStore.SERVERADDRESS + "property/get_pro_unit/";

          get_pro_room = MyCookieStore.SERVERADDRESS + "property/get_pro_room/";

        //  binding_community = MyCookieStore.SERVERADDRESS + "property/binding_community/";

          make_property_order = MyCookieStore.SERVERADDRESS + "property/make_property_order/";

          get_pro_com = MyCookieStore.SERVERADDRESS + "property/get_pro_com/";

          get_new_house = MyCookieStore.SERVERADDRESS + "memorandum/get_new_house/";

          get_memorandum = MyCookieStore.SERVERADDRESS + "memorandum/get_memorandum/";

          set_memorandum = MyCookieStore.SERVERADDRESS + "memorandum/set_memorandum/";

          property_createorder = MyCookieStore.SERVERADDRESS + "property/create_order/";

          getFacePayCate = MyCookieStore.SERVERADDRESS + "property/face_pay_cate/";

          pay_face_order = MyCookieStore.SERVERADDRESS + "property/pay_face_order/";

          addFacePayOrder = MyCookieStore.SERVERADDRESS + "property/add_face_pay_order/";

          face_order_list = MyCookieStore.SERVERADDRESS + "property/face_order_list/";

          getRoomPersonalInfo = MyCookieStore.SERVERADDRESS + "property/get_room_personal_info/";

          merchant_list = MyCookieStore.SERVERADDRESS + "property/merchant_list/";

          add_wired_order = MyCookieStore.SERVERADDRESS + "property/add_wired_order/";

          pay_wired_order = MyCookieStore.SERVERADDRESS + "property/pay_wired_order/";

          wired_order_list = MyCookieStore.SERVERADDRESS + "property/wired_order_list/";

          service_new_pay = ApiHttpClient.API_SERVICE_URL + "order/pay_service_order/";

          pay_activity_order = MyCookieStore.SERVERADDRESS + "activity/pay_activity_order/";

          activity_details = MyCookieStore.SERVERADDRESS + "activity/activity_details/";

          activity_enroll = MyCookieStore.SERVERADDRESS + "activity/activity_enroll/";

          version_update = MyCookieStore.SERVERADDRESS + "site/version_update/";

          site_logout = MyCookieStore.SERVERADDRESS + "site/logout/";

          free_login = MyCookieStore.SERVERADDRESS + "site/free_login/";

          login_verify = MyCookieStore.SERVERADDRESS + "site/login_verify/";

          reg_send_sms = MyCookieStore.SERVERADDRESS + "site/reg_send_sms/";

          get_userInfo = MyCookieStore.SERVERADDRESS + "site/get_userInfo/";

          register = MyCookieStore.SERVERADDRESS + "site/register/";

          merchant_push = MyCookieStore.SERVERADDRESS + "site/merchant_push";

          get_Advertising = MyCookieStore.SERVERADDRESS + "site/get_Advertising/";

          payment_list = MyCookieStore.SERVERADDRESS + "site/payment_list/";

          share_return = MyCookieStore.SERVERADDRESS + "site/share_return/";

          getSocialList = MyCookieStore.SERVERADDRESS + "social/get_social_list/";

          get_user_Social = MyCookieStore.SERVERADDRESS + "social/get_user_social_list/";

          getSocialNum = MyCookieStore.SERVERADDRESS + "social/social_num/";

          getSocialCategorys = MyCookieStore.SERVERADDRESS + "social/getSocialCategory";

          get_social = MyCookieStore.SERVERADDRESS + "social/get_social/";

          getSocialCategory = MyCookieStore.SERVERADDRESS + "social/getSocialCategory/";

          social_reply = MyCookieStore.SERVERADDRESS + "social/social_reply/";

          SocialDel = MyCookieStore.SERVERADDRESS + "social/SocialDel/";

          social_reply_del = MyCookieStore.SERVERADDRESS + "social/social_reply_del/";

          distribution = MyCookieStore.SERVERADDRESS + "Jpush/distribution_push/";
    }
}
