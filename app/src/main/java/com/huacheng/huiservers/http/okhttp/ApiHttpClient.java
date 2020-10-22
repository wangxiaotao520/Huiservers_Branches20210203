package com.huacheng.huiservers.http.okhttp;

/**
 * Description: 接口
 * created by wangxiaotao
 * 谨记在下方定义完接口后要在下方invalidate中再写一遍
 */
public class ApiHttpClient {
    //    public static  String API_VERSION = "apk41/";
    public static String API_VERSION = "apk47/";
//    //测试
//    public static final String API_URL_FINAL = "http://com.hui-shenghuo.cn/";//固定域名
//    public static String API_URL = "http://test.hui-shenghuo.cn/";
//    public static String API_URL_SHARE = "http://test.hui-shenghuo.cn/";//分享
//    public static String API_SERVICE_URL = "http://test.hui-shenghuo.cn/service/";//服务

//    //测试new
//    public static final String API_URL_FINAL = "http://1.71.130.58:8081/";//固定域名
//    public static String API_URL = "http://1.71.130.58:8082/";
//    public static String API_URL_SHARE = "http://1.71.130.58:8082";//分享
//    public static String API_SERVICE_URL = "http://1.71.130.58:8082/service46/";//服务
//    //    //测试new
//    public static String IMG_URL = "http://1.71.130.58:8088/";
//    public static String IMG_SERVICE_URL = "http://1.71.130.58:8088/";

//    正式
    public static final String API_URL_FINAL = "http://common.hui-shenghuo.cn/";
    public static  String API_URL = "http://m.hui-shenghuo.cn/" ;
    public static  String API_URL_SHARE = "http://m.hui-shenghuo.cn/";
    public static  String API_SERVICE_URL = "http://m.hui-shenghuo.cn/service46/";
   //  正式
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
    //用户验证原手机号
    public static String VERITY_OLD_PHONE = API_URL + API_VERSION + "site/verify_old_phone";
    //用户验证新手机号
    public static String VERITY_NEW_PHONE = API_URL + API_VERSION + "site/verify_new_phone";
    //用户新手机号绑定
    public static String VERITY_OPERATION = API_URL + API_VERSION + "site/verify_operation";
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
    public static String GET_ADVERTISING = API_URL + API_VERSION + "site/get_Advertising";
    //首页商圈详情列表
    public static String GET_BUSINESS_DETAIL = API_URL + API_VERSION + "index/business_district_service_list";
    //首页商圈列表
    public static String GET_BUSINESS_LIST = API_URL + API_VERSION + "index/business_district_list";
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
    //服务区域判断有无
    public static String GET_SERVICEMATCHING = API_SERVICE_URL + "index/serviceMatching";
    //服务首页广告
    public static String INDEX_SERVICE_AD_TOP = API_SERVICE_URL + "index/serviceClassifTop";
    //确认订单购买须知
    public static String COMFIRM_ORDER_PURCHASE_NOTE = API_SERVICE_URL + "service/purchase";
    //退款页接口
    public static String REFUND_LIST = API_SERVICE_URL + "order/refundList";
    //退款提交
    public static String REFUND_COMMIT = API_SERVICE_URL + "order/refundQuerty";
    //无上门费请求这个接口
    public static String ORDER_CANCEL_NO_DOOR_COMMIT = API_SERVICE_URL + "order/order_cancel_service";
    //退款详情接口
    public static String ORDER_REFUND_DETAIL = API_SERVICE_URL + "order/OrderRefundDetail";
    //取消退款
    public static String CANCEL_REFUND = API_SERVICE_URL + "order/cancelRefund";
    //完成服务
    public static String FINISH_SERVICE = API_SERVICE_URL + "order/completeService";
    //删除订单
    public static String DELETE_SERVICE = API_SERVICE_URL + "order/orderDel";

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
    //商城店铺接口
    public static String SHOP_IMERCHANT_DETAILS = API_URL + API_VERSION + "/shop/merchant_details";
    //专区列表
    public static String SHOP_MARKIING_LIST = API_URL + API_VERSION + "/shop/area_marketing_activities";
    //专区banner详情界面
    public static String SHOP_MARKIING_DETAILS = API_URL + API_VERSION + "/shop/area_marketing_activities_info";
    //专区活动列表界面
    public static String SHOP_MARKIING_ARTICE = API_URL + API_VERSION + "/shop/area_marketing_article";
    //专区活动详情界面
    public static String SHOP_MARKIING_ARTICE_DETAILS = API_URL + API_VERSION + "/shop/area_marketing_article_info";
    //特卖专场
    public static String SHOP_MARKIING_ARTICE_LIST = API_URL + API_VERSION + "/shop/area_marketing_activities_List";

    //付款成功后极光推送
    public static String PAY_SERVICE_SUCCESS = API_URL + API_VERSION + "Jpush/service_order_toAmountWorker_push";
    //商城订单列表
    public static String GET_SHOP_ORDER_LIST = API_URL + API_VERSION + "userCenter/shopping_order";
    //商城订单详情
    public static String GET_SHOP_ORDER_DETAILS = API_URL + API_VERSION + "userCenter/shopping_order_details";
    //商城订单列表待付款删除
    public static String GET_SHOP_ORDER_DEL = API_URL + API_VERSION + "userCenter/del_shopping_order";
    // 购物订单操作页面(退款/收货/评价) 接口
    public static String GET_SHOP_ORDER_OPERTE = API_URL + API_VERSION + "userCenter/shopping_order_operate";

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

    public static String BINDING_COMMUNITY = API_URL + API_VERSION + "property/binding_community/";
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
    //扫描返回
    public static String SCAN_INDEX = API_URL + API_VERSION + "Scan/Scan_index";

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
    //企业列表
    public static String PENSION_COMPANY_LIST = API_URL + API_VERSION + "pension/company";
    //养老消息列表
    public static String PENSION_MSG_LIST = API_URL + API_VERSION + "pension/msgList";
    //养老消息同意拒绝
    public static String PENSION_MSG_AUDIT = API_URL + API_VERSION + "pension/msgAudit";
    //养老用药提醒
    public static String PENSION_DRUG_LIST = API_URL + API_VERSION + "pension/drugList";
    //亲情关怀列表
    public static String PENSION_CARE_LIST = API_URL + API_VERSION + "pension/careList";
    //
    public static String PENSION_OLDFILE_DETAIL = API_URL + API_VERSION + "pension/peopleSee";
    //体检记录
    public static String PENSION_CHECKUP_LIST = API_URL + API_VERSION + "pension/checkupList";
    //体检记录详情(常规记录)老人档案详情
    public static String PENSION_CHECKUP_ONE_SEE = API_URL + API_VERSION + "pension/checkupOneSee";
    //体检记录详情(智能硬件)
    public static String PENSION_BELTER_DETAILS = API_URL + API_VERSION + "/pension/belter_details";
    //资讯详情
    public static String PENSION_SOCIAL_DETAIL = API_URL + API_VERSION + "pension/pensionSocialdetails";
    //活动详情
    public static String PENSION_ACTIVITY_SEE = API_URL + API_VERSION + "pension/activitySee";
    //老人慧生活设置地址
    public static String PENSION_SET_ADDRESS = API_URL + API_VERSION + "pension/setAddress";
    //老人智能硬件列表信息
    public static String GET_HANRDWARE_INFO = API_URL + "Hardware/Fzd/hardwareInfo";
    //老人绑定
    public static String HANRDWARE_BINDING = API_URL + "/Hardware/Fzd/binding";
    //老人解除绑定
    public static String HANRDWARE_UNBINDING = API_URL + "/Hardware/Fzd/RemoveBinding ";
    //老人设备定位
    public static String DEVICE_LOCATION = API_URL + "/Hardware/Fzd/deviceLocation";
    //老人设备足迹查询
    public static String DEVICE_ZUJI = API_URL + "/Hardware/Fzd/deviceFootPrint";
    //老人健康计步
    public static String STEP_INFO = API_URL + "/Hardware/Fzd/deviceStepInfo";
    //获取远程测心率
    public static String DEVICE_HEART = API_URL + "/Hardware/Fzd/deviceHeartInfo";
    //远程测心率
    public static String DEVICE_HEART_COMMIT = API_URL + "/Hardware/Fzd/remoteHeartRate";
    //获取远程测血压
    public static String DEVICE_BIOOD = API_URL + "/Hardware/Fzd/deviceBlood";
    //远程测血压
    public static String DEVICE_BIOOD_COMMIT = API_URL + "/Hardware/Fzd/bldstart";
    //获取远程云测温
    public static String DEVICE_WD = API_URL + "/Hardware/Fzd/wdInfo";
    //远程云测温
    public static String DEVICE_WD_COMMIT = API_URL + "/Hardware/Fzd/wd";
    //老人围栏列表
    public static String ENCLOSURE_LIST = API_URL + "/Hardware/Fzd/enclosureList";
    //设置电子围栏
    public static String SET_ENCLOSURE = API_URL + "/Hardware/Fzd/setEnclosure";
    //删除电子围栏
    public static String DEL_ENCLOSURE = API_URL + "/Hardware/Fzd/delEnclosure";
    //SOS
    public static String DEVICE_SOS = API_URL + "/Hardware/Fzd/setSOS";
    //查找设备
    public static String GET_DEVICE= API_URL + "/Hardware/Fzd/findDevice";
    //监护号码
    public static String DEVICE_GUARDER= API_URL + "/Hardware/Fzd/setGuarder";
    //获取设备SOS和监护号码
    public static String DEVICE_GET_SOS_GUARDER= API_URL + "/Hardware/Fzd/getSOSAndGuarder";
    //老人是否有调查问卷的权限
    public static String OLD_QUESTION_PERMISION= API_URL+ API_VERSION + "/oldQuestion/is_display";
    //老人调查问卷问题
    public static String OLD_INVESTIGATION_QUESTION= API_URL+ API_VERSION + "/oldQuestion/question";
    //老人调查问卷问题
    public static String OLD_INVESTIGATION_QUESTION_COMMIT= API_URL+ API_VERSION + "/oldQuestion/record";
    //老干局公告详情
    public static String OLD_NEW_ZIXUN_LIST= API_URL+ API_VERSION + "/pension/informationList";
    //老干局公告详情
    public static String OLD_NEW_ZIXUN_DETAIL= API_URL+ API_VERSION + "/pension/informationdetails";

    /**
     * 隐私政策
     */
    //隐私政策
    public static String GET_PRIVARY = API_URL + API_VERSION + "userCenter/privacy";
    /**
     * 活动投票
     */
    //首页
    public static String FAMILY_INDEX = API_URL + API_VERSION + "family/family_index";
    //家庭详情
    public static String FAMILY_DETAIL = API_URL + API_VERSION + "family/family_see";
    //留言列表
    public static String FAMILY_MESSAGE_LIST = API_URL + API_VERSION + "family/family_message";
    //提交留言
    public static String FAMILY_MESSAGE_ADD = API_URL + API_VERSION + "family/family_message_add";
    //点赞
    public static String FAMILY_PRAISE = API_URL + API_VERSION + "family/family_praise";
    //投票
    public static String FAMILY_POLL = API_URL + API_VERSION + "family/family_poll";
    //活动首页分享
    public static String FAMILY_INDEX_SHARE = API_URL_SHARE + "home/index/beautiful_family";
    //VLOG投票活动分享
    public static String VLOG_HOME_INDEX_SHARE = API_URL_SHARE + "home/index/vlog_list";
    /**
     * Vlog活动投票
     */
    //首页
    public static String VLOG_INDEX = API_URL + API_VERSION + "/vote/vote_index";
    //VLOG 投票
    public static String VLOG_POLL = API_URL + API_VERSION + "vote/vote_poll";
    //留言列表
    public static String VLOG_MESSAGE_LIST = API_URL + API_VERSION + "vote/message";
    //提交留言
    public static String VLOG_MESSAGE_ADD = API_URL + API_VERSION + "vote/vote_message_add";
    //点赞
    public static String VLOG_PRAISE = API_URL + API_VERSION + "vote/vote_praise";
    //活动详情
    public static String VLOG_INDEX_SHARE = API_URL_SHARE + "home/index/beautiful_vlog";
    //排行榜
    public static String VLOG_RANK_LIST = API_URL + API_VERSION + "vote/rank";

    //投票搜索接口
    public static String VOTE_LIST = API_URL + API_VERSION + "vote/vote_list";


    //我的小区
    public static String GET_MY_DISTRICT = API_URL + API_VERSION + "site/myDistrict";
    //根据小区名称获取小区id
    public static String GET_COMMUNITY_ID = API_URL + API_VERSION + "site/siteCommunityId";
    //计算订单金额
    public static String SET_ORDER_AMOUNT = API_URL + API_VERSION + "/shop/set_order_amount";
    /**
     * 邻里
     */
    //邻里分类
    public static String GET_SOCIAL_CAT = API_URL + API_VERSION + "social/getSocialCategory/";
    //邻里列表
    public static String GET_SOCIAL_LIST = API_URL + API_VERSION + "social/get_social_list/";
    //邻里删除
    public static String SOCIAL_DELETE = API_URL + API_VERSION + "social/SocialDel/";
    //我的邻里
    public static String GET_USER_SOCIAL = API_URL + API_VERSION + "social/get_user_social_list/";
    //首页下方tab IMG
    public static String IMG_BOTTOM = API_URL + API_VERSION + "index/index_Bottom";

    //调查问卷页
    public static String GET_INVESTIGATE_INFORMATION = API_URL + API_VERSION + "investigate/question";
    //提交调查问卷
    public static String COMMIT_INVESTIGATE = API_URL + API_VERSION + "investigate/record";
    //调查问卷详情
    public static String GET_INVESTIGATE_DETAIL = API_URL + API_VERSION + "investigate/record_details";
    //调查问卷历史记录
    public static String GET_INVESTIGATE_HISTORY_LIST = API_URL + API_VERSION + "investigate/record_list";

    //启动页广告图
    public static String GET_GUIDE_IMG = API_URL + API_VERSION + "index/guide_img";
    //调查问卷房屋列表
    public static String INVESTIGATE_HOME_LIST = API_URL + API_VERSION + "investigate/home_list";
    /**
     * 通行证
     */
    //申请通行证列表
    public static String GET_PERMIT_SET_LIST = API_URL + API_VERSION + "propertyPass/pass_check_set_list";
    //通行证列表
    public static String GET_PERMIT_LIST = API_URL + API_VERSION + "propertyPass/pass_check_list";
    //通行证详情
    public static String GET_PERMIT_DETAIL = API_URL + API_VERSION + "propertyPass/pass_check_details";
    //申请通行证页面
    public static String PASS_CHECK_INFORMATION = API_URL + API_VERSION + "propertyPass/pass_check";
    //申请提交
    public static String PASS_CHECK_SUBMIT = API_URL + API_VERSION + "propertyPass/pass_check_submit";
    //请求分享参数
    public static String GET_SHARE_PARAM = API_URL + API_VERSION + "shares/share";
    //个人中心我的优惠券
    public static String MY_COUPON_LIST = API_URL + API_VERSION + "/userCenter/my_coupon_46";
    //历史记录
    public static String COUPON_HISTORY = API_URL + API_VERSION + "userCenter/coupon_over_46";
    //领取优惠券
    public static String COUPON_ADD = API_URL + API_VERSION + "userCenter/coupon_add/";
    //商品详情的优惠券列表
    public static String GOODS_COUPON_LIST = API_URL + API_VERSION + "shop/goods_coupon";
    //确认订单优惠券列表
    public static String COMFIRM_ORDER_COUPON_LIST = API_URL + API_VERSION + "userCenter/order_coupon_46";


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
        PENSION_OLDFILE_DETAIL = API_URL + API_VERSION + "pension/peopleSee";
        PENSION_CHECKUP_LIST = API_URL + API_VERSION + "pension/checkupList";
        PENSION_CHECKUP_ONE_SEE = API_URL + API_VERSION + "pension/checkupOneSee";
        PENSION_SOCIAL_DETAIL = API_URL + API_VERSION + "pension/pensionSocialdetails";
        PENSION_ACTIVITY_SEE = API_URL + API_VERSION + "pension/activitySee";
        SCAN_INDEX = API_URL + API_VERSION + "Scan/Scan_index";
        GET_PRIVARY = API_URL + API_VERSION + "userCenter/privacy";
        FAMILY_INDEX = API_URL + API_VERSION + "family/family_index";
        FAMILY_MESSAGE_LIST = API_URL + API_VERSION + "family/family_message";
        FAMILY_MESSAGE_ADD = API_URL + API_VERSION + "family/family_message_add";
        FAMILY_PRAISE = API_URL + API_VERSION + "family/family_praise";
        FAMILY_DETAIL = API_URL + API_VERSION + "family/family_see";
        FAMILY_POLL = API_URL + API_VERSION + "family/family_poll";
        FAMILY_INDEX_SHARE = API_URL + "home/index/beautiful_family";
        SHOP_IMERCHANT_DETAILS = API_URL + API_VERSION + "/shop/merchant_details";
        GET_MY_DISTRICT = API_URL + API_VERSION + "site/myDistrict";
        GET_COMMUNITY_ID = API_URL + API_VERSION + "site/siteCommunityId";
        SHOP_MARKIING_LIST = API_URL + API_VERSION + "/shop/area_marketing_activities";
        SHOP_MARKIING_DETAILS = API_URL + API_VERSION + "shop/area_marketing_activities_info";
        SHOP_MARKIING_ARTICE = API_URL + API_VERSION + "/shop/area_marketing_article";
        SHOP_MARKIING_ARTICE_DETAILS = API_URL + API_VERSION + "/shop/area_marketing_article_info";
        GET_SERVICEMATCHING = API_SERVICE_URL + "index/serviceMatching";
        SET_ORDER_AMOUNT = API_URL + API_VERSION + "/shop/set_order_amount";
        BINDING_COMMUNITY = API_URL + API_VERSION + "property/binding_community/";
        GET_SOCIAL_CAT = API_URL + API_VERSION + "social/getSocialCategory/";
        GET_SOCIAL_LIST = API_URL + API_VERSION + "social/get_social_list/";
        SOCIAL_DELETE = API_URL + API_VERSION + "social/SocialDel/";
        GET_USER_SOCIAL = API_URL + API_VERSION + "social/get_user_social_list/";
        VLOG_INDEX = API_URL + API_VERSION + "vote/vote_index";
        VLOG_POLL = API_URL + API_VERSION + "vote/vote_poll";
        VLOG_PRAISE = API_URL + API_VERSION + "vote/vote_praise";
        VLOG_INDEX_SHARE = API_URL_SHARE + "home/index/beautiful_vlog";
        VLOG_MESSAGE_ADD = API_URL + API_VERSION + "vote/vote_message_add";
        VLOG_MESSAGE_LIST = API_URL + API_VERSION + "vote/message";
        VLOG_RANK_LIST = API_URL_SHARE + "vote/rank";
        INDEX_SERVICE_AD_TOP = API_SERVICE_URL + "index/serviceClassifTop";
        IMG_BOTTOM = API_URL + API_VERSION + "index/index_Bottom";
        SHOP_MARKIING_ARTICE_LIST = API_URL + API_VERSION + "/shop/area_marketing_activities_List";
        PENSION_BELTER_DETAILS = API_URL + API_VERSION + "/pension/belter_details";
        VLOG_HOME_INDEX_SHARE = API_URL_SHARE + "home/index/vlog_list ";
        GET_SHOP_ORDER_DETAILS = API_URL + API_VERSION + "userCenter/shopping_order_details";
        GET_SHOP_ORDER_OPERTE = API_URL + API_VERSION + "userCenter/shopping_order_operate";
        GET_GUIDE_IMG = API_URL + API_VERSION + "index/guide_img";
        INVESTIGATE_HOME_LIST = API_URL + API_VERSION + "investigate/home_list";
        GET_INVESTIGATE_INFORMATION = API_URL + API_VERSION + "investigate/question";
        COMMIT_INVESTIGATE = API_URL + API_VERSION + "investigate/record";
        GET_INVESTIGATE_HISTORY_LIST = API_URL + API_VERSION + "investigate/record_list";
        GET_INVESTIGATE_DETAIL = API_URL + API_VERSION + "investigate/record_details";
        GET_PERMIT_SET_LIST = API_URL + API_VERSION + "propertyPass/pass_check_set_list";
        GET_PERMIT_DETAIL = API_URL + API_VERSION + "propertyPass/pass_check_details";
        GET_PERMIT_LIST = API_URL + API_VERSION + "propertyPass/pass_check_list";
        PASS_CHECK_INFORMATION = API_URL + API_VERSION + "propertyPass/pass_check";
        PASS_CHECK_SUBMIT = API_URL + API_VERSION + "propertyPass/pass_check_submit";
        VERITY_OLD_PHONE = API_URL + API_VERSION + "site/verify_old_phone";
        VERITY_NEW_PHONE = API_URL + API_VERSION + "site/verify_new_phone";
        VERITY_OPERATION = API_URL + API_VERSION + "site/verify_operation";
        GET_SHARE_PARAM = API_URL + API_VERSION + "shares/share";
        VOTE_LIST = API_URL + API_VERSION + "vote/vote_list";
        MY_COUPON_LIST = API_URL + API_VERSION + "/userCenter/my_coupon_46";
        COUPON_HISTORY = API_URL + API_VERSION + "userCenter/coupon_over_46";
        COUPON_ADD = API_URL + API_VERSION + "userCenter/coupon_add/";
        GOODS_COUPON_LIST = API_URL + API_VERSION + "shop/goods_coupon";
        COMFIRM_ORDER_COUPON_LIST = API_URL + API_VERSION + "userCenter/order_coupon_46";
        COMFIRM_ORDER_PURCHASE_NOTE = API_SERVICE_URL + "service/purchase";
        REFUND_LIST = API_SERVICE_URL + "order/refundList";
        REFUND_COMMIT = API_SERVICE_URL + "order/refundQuerty";
        ORDER_CANCEL_NO_DOOR_COMMIT = API_SERVICE_URL + "order/order_cancel_service";
        ORDER_REFUND_DETAIL = API_SERVICE_URL + "order/OrderRefundDetail";
        CANCEL_REFUND = API_SERVICE_URL + "order/cancelRefund";
        FINISH_SERVICE = API_SERVICE_URL + "order/completeService";
        DELETE_SERVICE = API_SERVICE_URL + "order/orderDel";
        GET_BUSINESS_DETAIL = API_URL + API_VERSION + "index/business_district_service_list";
        GET_BUSINESS_LIST = API_URL + API_VERSION + "index/business_district_list";
        PENSION_COMPANY_LIST = API_URL + API_VERSION + "pension/company";
        PENSION_SET_ADDRESS = API_URL + API_VERSION + "pension/setAddress";
        GET_HANRDWARE_INFO = API_URL + "Hardware/Fzd/hardwareInfo ";
        HANRDWARE_BINDING = API_URL + "/Hardware/Fzd/binding";
        DEVICE_LOCATION = API_URL + "/Hardware/Fzd/deviceLocation";
        HANRDWARE_UNBINDING = API_URL + "/Hardware/Fzd/RemoveBinding ";
        DEVICE_ZUJI = API_URL + "/Hardware/Fzd/deviceFootPrint";
        STEP_INFO = API_URL + "/Hardware/Fzd/deviceStepInfo";
        DEVICE_HEART = API_URL + "/Hardware/Fzd/deviceHeartInfo";
        DEVICE_BIOOD = API_URL + "/Hardware/Fzd/deviceBlood";
        DEVICE_WD = API_URL + "/Hardware/Fzd/wdInfo";
        ENCLOSURE_LIST = API_URL + "/Hardware/Fzd/enclosureList";
        SET_ENCLOSURE = API_URL + "/Hardware/Fzd/setEnclosure";
        DEL_ENCLOSURE = API_URL + "/Hardware/Fzd/delEnclosure";
        DEVICE_HEART_COMMIT = API_URL + "/Hardware/Fzd/remoteHeartRate";
        DEVICE_BIOOD_COMMIT = API_URL + "/Hardware/Fzd/bldstart";
        DEVICE_WD_COMMIT = API_URL + "/Hardware/Fzd/wd";
        DEVICE_SOS = API_URL + "/Hardware/Fzd/setSOS";
        DEVICE_GUARDER= API_URL + "/Hardware/Fzd/setGuarder";
        GET_DEVICE= API_URL + "/Hardware/Fzd/findDevice";
        OLD_QUESTION_PERMISION= API_URL+ API_VERSION + "/oldQuestion/is_display";
        OLD_INVESTIGATION_QUESTION= API_URL+ API_VERSION + "/oldQuestion/question";
        OLD_INVESTIGATION_QUESTION_COMMIT= API_URL+ API_VERSION + "/oldQuestion/record";
        DEVICE_GET_SOS_GUARDER= API_URL + "/Hardware/Fzd/getSOSAndGuarder";
        OLD_NEW_ZIXUN_LIST= API_URL+ API_VERSION + "/pension/informationList";
        OLD_NEW_ZIXUN_DETAIL= API_URL+ API_VERSION + "/pension/informationdetails";
    }
}
