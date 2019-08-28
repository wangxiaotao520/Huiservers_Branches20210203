package com.huacheng.huiservers.http.okhttp;

/**
 * Description: 路径
 * created by wangxiaotao
 * 谨记在下方定义完接口后要在下方invalidate中再写一遍
 */
public class ApiHttpClient {
    //    public static  String API_VERSION = "apk41/";
    public static String API_VERSION = "apk43/";
    //测试
    public static final String API_URL_FINAL = "http://com.hui-shenghuo.cn/";//固定域名
    public static String API_URL = "http://test.hui-shenghuo.cn/";
    public static String API_URL_SHARE = "http://test.hui-shenghuo.cn/";//分享
    public static String API_SERVICE_URL = "http://test.hui-shenghuo.cn/service/";//服务


//    //正式
//   public static final String API_URL_FINAL = "http://common.hui-shenghuo.cn/";
//    public static  String API_URL = "http://m.hui-shenghuo.cn/" ;
//    public static  String API_URL_SHARE = "http://m.hui-shenghuo.cn/";
//    public static  String API_SERVICE_URL = "http://m.hui-shenghuo.cn/service/";


    public static String IMG_URL = "http://img.hui-shenghuo.cn/";
    public static String IMG_SERVICE_URL = "http://img.hui-shenghuo.cn/";

    public static String TOKEN;
    public static String TOKEN_SECRET;

    public static String THUMB_1080_1920_ = "thumb_1080_1920_";
    public static String THUMB_800_1280_ = "thumb_800_1280_";
    public static String THUMB_480_800_ = "thumb_480_800_";
    public static String THUMB__500_500_ = "thumb_500_500_";

    public static void setTokenInfo(String token, String tokenSecret) {
        TOKEN = token;
        TOKEN_SECRET = tokenSecret;
    }

    /**
     * 小区
     */
    //更新接口
    public static String APP_UPDATE = API_URL_FINAL + "Api/Version/version_update";
    //获取默认的地址
    public static String GET_CONFIG = API_URL_FINAL + "Api/Config/config";
    //获取默认的地址
    public static String DEFAULT_ADDRESS = API_URL + API_VERSION + "site/default_address";
    //推送接口
    public static String PUSH_INS = API_URL + API_VERSION + "Jpush/service_order_toIns_push";
    //4.1首页
    public static String INDEX = API_URL + API_VERSION + "index/index";
    //3.3
    //微信登录
    public static String WX_LOGIN = API_URL + API_VERSION + "site/wx_login";
    //登录用户绑定
    public static String WX_BIND = API_URL + API_VERSION + "site/wx_bind";
    //提交小区id
    public static String SELECT_COMMUNITY = API_URL + API_VERSION + "site/select_community";
    //定位获取小区
    public static String GET_COMMUNITY_BYCITY = API_URL_FINAL + "Api/Site/getCommunityByCity";
    //搜索关键字获取小区
    public static String SERCH_COMMUNITY = API_URL_FINAL + "Api/Site/searchCommunity";

    /**
     * 服务
     */
    //服务首页
    public static String GET_SERVICE_INDEX = API_SERVICE_URL + "index/serviceindex";
    //服务首页广告
    public static String GET_ADVERTISING = API_URL + API_VERSION + "site/get_Advertising/";
    // 订单获取统计接口
    public static String GET_ORDER_COUNT = API_SERVICE_URL + "order/myOrderCount";
    // 我的订单
    public static String GET_MY_ORDER = API_SERVICE_URL + "order/myorder";
    //店铺详情
    public static String GET_SHOP_DETAIL = API_SERVICE_URL + "institution/merchantDetails";
    //获取店铺服务列表
    public static String GET_SHOP_SERVICE_LIST = API_SERVICE_URL + "institution/merchantService";
    //获取店铺评论列表
    public static String GET_SHOP_COMMENT_LIST = API_SERVICE_URL + "institution/merchantComments";
    //服务详情
    public static String GET_SSERVICE_DETAIL = API_SERVICE_URL + "service/serviceDetails";
    //服务所有评论
    public static String GET_SSERVICE_SCORELIST = API_SERVICE_URL + "service/scoreList";
    //立即预约
    public static String GET_SSERVICE_RESERVE = API_SERVICE_URL + "service/serviceReserve";
    //获取订单详情
    public static String GET_ORDER_DETAIL = API_SERVICE_URL + "order/OrderDetail";
    //投诉，取消订单
    public static String ABORT_LIST = API_SERVICE_URL + "order/abortList";
    //提交评价
    public static String COMMIT_PINGJIA = API_SERVICE_URL + "order/critial";
    //提交取消服务订单
    public static String COMMIT_CANCEL = API_SERVICE_URL + "order/abortSave";
    //提交举报服务订单
    public static String COMMIT_JUBAO = API_SERVICE_URL + "order/reportSave";

    //服务首页-更多服务
    public static String GET_SERVICEMORE = API_SERVICE_URL + "index/serviceMore";
    //搜索,服务分类
    public static String SERVICE_CLASSIF = API_SERVICE_URL + "index/serviceClassif";
    //搜索关键字
    public static String GET_SERVICEKEYS = API_SERVICE_URL + "index/serviceKeys";
    //商家列表
    public static String GET_MERCHANTLIST = API_SERVICE_URL + "institution/merchantList";
    //服务列表
    public static String GET_SERVICELIST = API_SERVICE_URL + "service/serviceList";

    /**
     * 物业
     */
    //*************************************物业***************************************************************//
    //0.4.1物业
    //选择房产类型
    public static String GET_HOUSETYPE = API_URL + API_VERSION + "property/get_pro_housesType";
    //选择抄表类型 //useless
    public static String GET_CHAOBIAOTYPE = API_URL + "";
    //选择楼号
    public static String GET_FLOOR = API_URL + API_VERSION + "property/get_pro_building";
    //选择单元号
    public static String GET_UNIT = API_URL + API_VERSION + "property/get_pro_unit";
    //选择房间号
    public static String GET_ROOM = API_URL + API_VERSION + "property/get_pro_room";
    //选择商铺
    public static String GET_SHOPS = API_URL + API_VERSION + "property/get_pro_shop";

    //新版物业缴费流程 根据房间号验证并获取业主信息
    public static String PRO_PERSONAL_INFO = API_URL + API_VERSION + "property/getPersonalInfo";
    //新版物业缴费流程 根据房间号获物业账单
    public static String GET_ROOM_BILL = API_URL + API_VERSION + "property/get_room_bill";
    //新版物业缴费流程 完成绑定
    public static String PRO_BIND_USER = API_URL + API_VERSION + "property/pro_bind_user";
    //新版 缴费记录
    public static String GET_USER_BILL = API_URL + API_VERSION + "property/getUserBill";
    //新版 解除绑定
    public static String UNSETBINDING = API_URL + API_VERSION + "property/unsetBinding";

    //**************************************物业***************************************************************//

    /**
     * 租售房
     */
    //租售委托 背景图
    public static String GET_ENTRUST_BACKGROUND = API_URL + API_VERSION + "secondHouseType/secondHouseType";
    //租房详情
    public static String GET_HOUSEEND_LEASE_DETIL = API_URL + API_VERSION + "secondHouseType/getLeaseDetails";
    //售房详情
    public static String GET_HOUSEEND_SELL_DETIL = API_URL + API_VERSION + "secondHouseType/getSellListDetails";
    //拨打电话记录次数
    public static String GET_HOUSEEND_CALL = API_URL + API_VERSION + "personalHouse/housesCall";
    //小贴士
    public static String GET_CAREFUL = API_URL + API_VERSION + "secondHouseType/getCareful";
    //价格列表
    public static String GET_MONEY = API_URL + API_VERSION + "secondHouseType/getmoney";
    //面积列表
    public static String GET_ACREAGE = API_URL + API_VERSION + "secondHouseType/getacreage";
    //排序
    public static String GET_DEFAULT = API_URL + API_VERSION + "secondHouseType/getdefault";
    //房型列表
    public static String GET_HOUSE_TYPE_LIST = API_URL + API_VERSION + "secondHouseType/gethousetype";

    //租房列表
    public static String GET_LEASE_LIST = API_URL + API_VERSION + "secondHouseType/getLeaseList";
    //售房列表
    public static String GET_SELL_LIST = API_URL + API_VERSION + "secondHouseType/getSellList";
    //个人中心我的房产列表
    public static String PERSONALHOUSE = API_URL + API_VERSION + "personalHouse/personalHouse";

    //租房
    public static String HOUSESLEASEADDDO = API_URL + API_VERSION + "personalHouse/housesLeaseAddDo";
    //售房
    public static String HOUSESADDDO = API_URL + API_VERSION + "personalHouse/housesAddDo";
    //工单列表
    public static String GETWORKLIST = API_URL + API_VERSION + "propertyWork/getWorkList";

    /**
     * 物业工单
     */
    //公共部位维修分类接口
    public static String GET_COMMON_CATEGORY = API_URL + API_VERSION + "propertyWork/getCommonCategory";
    //选择地址
    public static String GET_WORK_ADDRESS = API_URL + API_VERSION + "propertyWork/getWorkAddress";
    //提交工单
    public static String WORK_SAVE = API_URL + API_VERSION + "propertyWork/workSave";
    //自用部位维修分类接口
    public static String GET_PRIVATE_CATEGORY = API_URL + API_VERSION + "propertyWork/getPrivateCategory";
    //工单预付
    public static String PAY_WORK_ORDER_ENTRY = API_URL + API_VERSION + "propertyWork/pay_work_order_entry";
    //工单支付
    public static String PAY_WORK_ORDER = API_URL + API_VERSION + "propertyWork/pay_work_order";
    //工单评价
    public static String WorkScore = API_URL + API_VERSION + "propertyWork/WorkScore";
    //取消工单
    public static String WorkCancel = API_URL + API_VERSION + "propertyWork/WorkCancel";
    //工单详情
    public static String WORK_DETAIL = API_URL + API_VERSION + "propertyWork/getWorkDetails";
    //商城首页1221
    public static String SHOP_INDEX = API_URL + API_VERSION + "shop/shop_index";

    //商城首页 加载更多
    public static String SHOP_INDEX_MORE = API_URL + API_VERSION + "shop/hotCateProlist";

    //付款成功后极光推送
    public static String PAY_SERVICE_SUCCESS = API_URL + API_VERSION + "Jpush/service_order_toAmountWorker_push";
    //商城订单列表
    public static String GET_SHOP_ORDER_LIST = API_URL + API_VERSION + "userCenter/shopping_order";
    //商城订单列表待付款删除
    public static String GET_SHOP_ORDER_DEL = API_URL + API_VERSION + "userCenter/del_shopping_order";

    //用户提交/取消工单推送接口
    public static String USERTOWORKERSUBMIT = API_URL + API_VERSION + "Jpush/userToWorkerSubmit";
    //检查是否绑定房屋
    public static String CHECK_BIND_PROPERTY = API_URL + API_VERSION + "index/check_binding";


    /**
     * 4.2.0 新物业工单
     */
    //工单列表
    public static String GET_WORK_LIST = API_URL + API_VERSION + "propertyWork/get_work_list";
    //工单详情
    public static String GET_WORK_DETAIL = API_URL + API_VERSION + "propertyWork/work_details";
    //工单评价
    public static String GET_WORK_SCORE = API_URL + API_VERSION + "propertyWork/WorkScore";
    //工单取消
    public static String GET_WORK_CANCEL = API_URL + API_VERSION + "propertyWork/work_cancel";
    //
    //获取工单服务分类接口
    public static String GET_WORK_TYPE_LIST = API_URL + API_VERSION + "propertyWork/get_work_type_list";
    //获取房屋地址接口
    public static String GET_WORK_HOUSE_ADDRESS = API_URL + API_VERSION + "propertyWork/getWorkAddress";
    //价目表列表接口
    public static String MARKED_PRICE = API_URL + API_VERSION + "propertyWork/marked_price";
    //提交工单
    public static String SBMMIT_WORK = API_URL + API_VERSION + "propertyWork/submit_work";
    //投诉建议提交
    public static String FEED_BACK_ADD = API_URL + API_VERSION + "feedback/feedbackAdd";
    //投诉建议列表
    public static String FEED_BACK_LIST = API_URL + API_VERSION + "feedback/feedbacklist";
    //投诉建议详情
    public static String FEED_BACK_DETAIL = API_URL + API_VERSION + "feedback/feedbackSee";
    /*
     * 推送
     *
     * */
    //推送接口 用户下单推送给管理
    public static String PUSH_TO_MANAGE = API_URL + API_VERSION + "Jpush/hui_jpush_guanli";
    //新支付
    //购物订单支付要素
    public static String PAY_SHOPPING_ORDER = API_URL + API_VERSION + "payment/pay_shopping_order";
    //工单订单支付要素
    public static String PAY_WORK_ORDER_NEW = API_URL + API_VERSION + "payment/pay_work_order";
    //物业账单支付要素
    public static String PAY_PROPERTY_ORDER_NEW = API_URL + API_VERSION + "payment/pay_property_order";
    //服务支付要素
    public static String PAY_SERVICE_ORDER_NEW = API_URL + API_VERSION + "payment/pay_service_order";
    //支付确认
    public static String CONFIRM_ORDER_PAYMENT = API_URL + API_VERSION + "payment/confirm_order_payment";

    //充电充电桩
    //获取 充电桩信息
    public static String GET_YX_INFO = API_URL + API_VERSION + "Yx/Yx_info";
    //充电下单
    public static String GET_CREAT_ORDER = API_URL + API_VERSION + "Yx/Yx_creat_order";
    //充电支付接口
    public static String PAY_YX_ORDER = API_URL + API_VERSION + "payment/pay_yx_order";
    //开启充电通道
    public static String PAY_CHARGE_START = API_URL + API_VERSION + "Yx/Yx_appcd_sta";
    //充电记录详情充电中状态
    public static String PAY_CHARGE_ING = API_URL + API_VERSION + "Yx/Yx_order_infoing";
    //充电记录列表
    public static String PAY_CHARGE_RECORD = API_URL + API_VERSION + "Yx/Yx_order_list";
    //充电消息
    public static String PAY_CHARGE_MESSAGE = API_URL + API_VERSION + "Yx/Yx_order_messge";
    //充电记录详情
    public static String PAY_CHARGE_RECORD_DETAIL = API_URL + API_VERSION + "Yx/Yx_order_info";
    //结束充电
    public static String PAY_CHARGE_END = API_URL + API_VERSION + "Yx/Yx_appcd_end";

    //慧生活养老
    //首页上部接口
    public static String PENSION_INDEXTOP = API_URL + API_VERSION + "pension/indexTop";
    //下方活动接口
    public static String PENSION_BOTTOM_ACTIVITY = API_URL + API_VERSION + "pension/indexBottomActivity";
    //下方资讯列表接口
    public static String PENSION_ZIXUN_LIST = API_URL + API_VERSION + "pension/pensionSocialList";
    //关联列表
    public static String PENSION_RELATION_LIST = API_URL + API_VERSION + "pension/relationList";
    //关联添加
    public static String PENSION_RELATION_ADD = API_URL + API_VERSION + "pension/relationAdd";
    //关联删除
    public static String PENSION_RELATION_DELETE = API_URL + API_VERSION + "pension/relationDel";
    //老人认证
    public static String PENSION_OLD_AUDIT = API_URL + API_VERSION + "pension/elderAudit";
    //机构列表
    public static String PENSION_INST_LIST = API_URL + API_VERSION + "pension/instList";
    //养老消息列表
    public static String PENSION_MSG_LIST = API_URL + API_VERSION + "pension/msgList";
    //养老消息同意拒绝
    public static String PENSION_MSG_AUDIT = API_URL + API_VERSION + "pension/msgAudit";
    //养老用药提醒
    public static String PENSION_DRUG_LIST = API_URL + API_VERSION + "pension/drugList";
    //亲情关怀列表
    public static String PENSION_CARE_LIST = API_URL + API_VERSION + "pension/careList";
    /**
     * 刷新接口
     * 谨记在上方定义完接口后要在下方再写一遍
     */
    public static void invalidateApi() {
        //获取默认的地址
        GET_CONFIG = API_URL_FINAL + "Api/Config/config";
        GET_COMMUNITY_BYCITY = API_URL_FINAL + "Api/Site/getCommunityByCity";
        SERCH_COMMUNITY = API_URL_FINAL + "Api/Site/searchCommunity";

        DEFAULT_ADDRESS = API_URL + API_VERSION + "site/default_address";
        APP_UPDATE = API_URL_FINAL + "Api/Version/version_update";
        PUSH_INS = API_URL + API_VERSION + "Jpush/service_order_toIns_push";
        INDEX = API_URL + API_VERSION + "index/index";
        WX_LOGIN = API_URL + API_VERSION + "site/wx_login";
        WX_BIND = API_URL + API_VERSION + "site/wx_bind";
        SELECT_COMMUNITY = API_URL + API_VERSION + "site/select_community";

        GET_SERVICE_INDEX = API_SERVICE_URL + "index/serviceindex";
        GET_ADVERTISING = API_URL + API_VERSION + "site/get_Advertising/";
        GET_ORDER_COUNT = API_SERVICE_URL + "order/myOrderCount";
        GET_MY_ORDER = API_SERVICE_URL + "order/myorder";
        GET_SHOP_DETAIL = API_SERVICE_URL + "institution/merchantDetails";
        GET_SHOP_SERVICE_LIST = API_SERVICE_URL + "institution/merchantService";
        GET_SHOP_COMMENT_LIST = API_SERVICE_URL + "institution/merchantComments";
        GET_SSERVICE_DETAIL = API_SERVICE_URL + "service/serviceDetails";
        GET_SSERVICE_SCORELIST = API_SERVICE_URL + "service/scoreList";
        GET_SSERVICE_RESERVE = API_SERVICE_URL + "service/serviceReserve";
        GET_ORDER_DETAIL = API_SERVICE_URL + "order/OrderDetail";
        ABORT_LIST = API_SERVICE_URL + "order/abortList";
        COMMIT_PINGJIA = API_SERVICE_URL + "order/critial";
        COMMIT_CANCEL = API_SERVICE_URL + "order/abortSave";
        COMMIT_JUBAO = API_SERVICE_URL + "order/reportSave";
        GET_SERVICEMORE = API_SERVICE_URL + "index/serviceMore";
        SERVICE_CLASSIF = API_SERVICE_URL + "index/serviceClassif";
        GET_SERVICEKEYS = API_SERVICE_URL + "index/serviceKeys";
        GET_MERCHANTLIST = API_SERVICE_URL + "institution/merchantList";
        GET_SERVICELIST = API_SERVICE_URL + "service/serviceList";
        GET_HOUSETYPE = API_URL + API_VERSION + "property/get_pro_housesType";
        GET_CHAOBIAOTYPE = API_URL + "";
        GET_FLOOR = API_URL + API_VERSION + "property/get_pro_building";
        GET_UNIT = API_URL + API_VERSION + "property/get_pro_unit";
        GET_ROOM = API_URL + API_VERSION + "property/get_pro_room";
        GET_SHOPS = API_URL + API_VERSION + "property/get_pro_shop";
        PRO_PERSONAL_INFO = API_URL + API_VERSION + "property/getPersonalInfo";
        GET_ROOM_BILL = API_URL + API_VERSION + "property/get_room_bill";
        PRO_BIND_USER = API_URL + API_VERSION + "property/pro_bind_user";
        GET_USER_BILL = API_URL + API_VERSION + "property/getUserBill";
        UNSETBINDING = API_URL + API_VERSION + "property/unsetBinding";
        GET_ENTRUST_BACKGROUND = API_URL + API_VERSION + "secondHouseType/secondHouseType";
        GET_HOUSEEND_LEASE_DETIL = API_URL + API_VERSION + "secondHouseType/getLeaseDetails";
        GET_HOUSEEND_SELL_DETIL = API_URL + API_VERSION + "secondHouseType/getSellListDetails";
        GET_HOUSEEND_CALL = API_URL + API_VERSION + "personalHouse/housesCall";
        GET_CAREFUL = API_URL + API_VERSION + "secondHouseType/getCareful";
        GET_MONEY = API_URL + API_VERSION + "secondHouseType/getmoney";
        GET_ACREAGE = API_URL + API_VERSION + "secondHouseType/getacreage";
        GET_DEFAULT = API_URL + API_VERSION + "secondHouseType/getdefault";
        GET_HOUSE_TYPE_LIST = API_URL + API_VERSION + "secondHouseType/gethousetype";
        GET_LEASE_LIST = API_URL + API_VERSION + "secondHouseType/getLeaseList";
        GET_SELL_LIST = API_URL + API_VERSION + "secondHouseType/getSellList";
        PERSONALHOUSE = API_URL + API_VERSION + "personalHouse/personalHouse";
        HOUSESLEASEADDDO = API_URL + API_VERSION + "personalHouse/housesLeaseAddDo";
        HOUSESADDDO = API_URL + API_VERSION + "personalHouse/housesAddDo";
        GETWORKLIST = API_URL + API_VERSION + "propertyWork/getWorkList";
        GET_COMMON_CATEGORY = API_URL + API_VERSION + "propertyWork/getCommonCategory";
        GET_WORK_ADDRESS = API_URL + API_VERSION + "propertyWork/getWorkAddress";
        WORK_SAVE = API_URL + API_VERSION + "propertyWork/workSave";
        GET_PRIVATE_CATEGORY = API_URL + API_VERSION + "propertyWork/getPrivateCategory";
        PAY_WORK_ORDER_ENTRY = API_URL + API_VERSION + "propertyWork/pay_work_order_entry";
        PAY_WORK_ORDER = API_URL + API_VERSION + "propertyWork/pay_work_order";
        WorkScore = API_URL + API_VERSION + "propertyWork/WorkScore";
        WorkCancel = API_URL + API_VERSION + "propertyWork/WorkCancel";
        WORK_DETAIL = API_URL + API_VERSION + "propertyWork/getWorkDetails";
        SHOP_INDEX = API_URL + API_VERSION + "shop/shop_index";
        SHOP_INDEX_MORE = API_URL + API_VERSION + "shop/hotCateProlist";
        PAY_SERVICE_SUCCESS = API_URL + API_VERSION + "Jpush/service_order_toAmountWorker_push";
        GET_SHOP_ORDER_LIST = API_URL + API_VERSION + "userCenter/shopping_order";
        GET_SHOP_ORDER_DEL = API_URL + API_VERSION + "userCenter/del_shopping_order";
        USERTOWORKERSUBMIT = API_URL + API_VERSION + "Jpush/userToWorkerSubmit";
        CHECK_BIND_PROPERTY = API_URL + API_VERSION + "index/check_binding";
        GET_WORK_DETAIL = API_URL + API_VERSION + "propertyWork/work_details";
        GET_WORK_TYPE_LIST = API_URL + API_VERSION + "propertyWork/get_work_type_list";
        GET_WORK_HOUSE_ADDRESS = API_URL + API_VERSION + "propertyWork/getWorkAddress";
        MARKED_PRICE = API_URL + API_VERSION + "propertyWork/marked_price";
        SBMMIT_WORK = API_URL + API_VERSION + "propertyWork/submit_work";
        GET_WORK_LIST = API_URL + API_VERSION + "propertyWork/get_work_list";
        GET_WORK_SCORE = API_URL + API_VERSION + "propertyWork/WorkScore";
        GET_WORK_CANCEL = API_URL + API_VERSION + "propertyWork/work_cancel";
        FEED_BACK_ADD = API_URL + API_VERSION + "feedback/feedbackAdd";
        FEED_BACK_LIST = API_URL + API_VERSION + "feedback/feedbacklist";
        FEED_BACK_DETAIL = API_URL + API_VERSION + "feedback/feedbackSee";
        PUSH_TO_MANAGE = API_URL + API_VERSION + "Jpush/hui_jpush_guanli";
        PAY_SHOPPING_ORDER = API_URL + API_VERSION + "payment/pay_shopping_order";
        CONFIRM_ORDER_PAYMENT = API_URL + API_VERSION + "payment/confirm_order_payment";
        PAY_WORK_ORDER_NEW = API_URL + API_VERSION + "payment/pay_work_order";
        PAY_PROPERTY_ORDER_NEW = API_URL + API_VERSION + "payment/pay_property_order";
        PAY_SERVICE_ORDER_NEW = API_URL + API_VERSION + "payment/pay_service_order";

        GET_YX_INFO = API_URL + API_VERSION + "Yx/Yx_info";
        GET_CREAT_ORDER = API_URL + API_VERSION + "Yx/Yx_creat_order";
        PAY_YX_ORDER = API_URL + API_VERSION + "payment/pay_yx_order";
        PAY_CHARGE_START = API_URL + API_VERSION + "Yx/Yx_appcd_sta";
        PAY_CHARGE_ING = API_URL + API_VERSION + "Yx/Yx_order_infoing";
        PAY_CHARGE_RECORD = API_URL + API_VERSION + "Yx/Yx_order_list";
        PAY_CHARGE_MESSAGE = API_URL + API_VERSION + "Yx/Yx_order_messge";
        PAY_CHARGE_RECORD_DETAIL = API_URL + API_VERSION + "Yx/Yx_order_info";
        PAY_CHARGE_END = API_URL + API_VERSION + "Yx/Yx_appcd_end";
        PENSION_INDEXTOP = API_URL + API_VERSION + "pension/indexTop";
        PENSION_BOTTOM_ACTIVITY = API_URL + API_VERSION + "pension/indexBottomActivity";
        PENSION_ZIXUN_LIST = API_URL + API_VERSION + "pension/pensionSocialList";
        PENSION_RELATION_LIST = API_URL + API_VERSION + "pension/relationList";
        PENSION_RELATION_ADD = API_URL + API_VERSION + "pension/relationAdd";
        PENSION_RELATION_DELETE = API_URL + API_VERSION + "pension/relationDel";
        PENSION_OLD_AUDIT = API_URL + API_VERSION + "pension/elderAudit";
        PENSION_INST_LIST = API_URL + API_VERSION + "pension/instList";
        PENSION_MSG_LIST = API_URL + API_VERSION + "pension/msgList";
        PENSION_MSG_AUDIT = API_URL + API_VERSION + "pension/msgAudit";
        PENSION_DRUG_LIST = API_URL + API_VERSION + "pension/drugList";
        PENSION_CARE_LIST = API_URL + API_VERSION + "pension/careList";
    }
}
