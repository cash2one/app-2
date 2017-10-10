package com.jumper.angel.frame.util;

/**
 * 常量类
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-28
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Const {
	
	public static final String YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
	public static final String CN_YYYYMMDD_HHMM = "yyyy年MM月dd日 HH:mm";
	public static final String DATE_PATTERN_YYYYMMDD = "yyyy/MM/dd";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String DATE_PATTERN_YYYYMMDD_HHMMSS = "yyyy/MM/dd HH:mm:ss";
	public static final String CN_YYYYMMDD = "yyyy年MM月dd日";
	public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static String REAL_BASE_PATH;
	public static String BASE_PATH;
	public static String PROGECT_NAME;
	public static String COPY_RIGHT;
	public static int PAGE_SIZE;
	
	public static String PARAMS_KEYS = "*JUMPER*";		//用于加密传递参数据KEY
	public static String SIGNED_KEYS = "JUMPER2014API";	//用于MD5加密密钥的值
	
	public static String USER_IMAGE_DIR = "";			//用户头像上传图片目录
	public static String USER_RECORDS_DIR= "";			//用户胎心测试文件上传目录
	public static String MUSICS_DIR= "";					//存放Mp3文件
	
	
	public static String BASE_FILE_URL = "";			//用户上传图片文件的URL地址
	
	public static String UPLOAD_DIR = "";				//文件上传url
	
	public static String  SHOP_URL_ADDRESS= "";			//商铺的URL
	
	public static String ECGREPORTDATAURL="";//心电报告数据获取接口url
	public static String ECGREPORTURL="";//心电报告接口url
	
	public static String SMS_USER = "";					//发送短信网关的用户名
	public static String SMS_PASSWORD = "";				//发关短信网关的密码
	
	
	public static int TOTAL_WEEKS = 40;					//孕期时间40周
	public static int TOTAL_DAYS = 280;					//孕期的总天数是280天
	
	public static int SUCCESS = 1;						//接口请求处理成功
	public static int FAILED = 0;						//接口请求处理失败
	public static int NODATA = 10;						//数据列表为空
	
	public static String LOGIN_SESSION_USER_NAME = "LOGINUSER";//用户名
	public static String LOGIN_SESSION_USER_ID = "LOGINUSERID";//用户id
	
	/*********************** 短信 ************************/
//	public static int CODE_LIMIT_TIME =300;				//短信验证的时间默认限制5分钟
//	public static String REGISTER_STR = "你申请注册天使医生(用户端)的验证码是%s。(3分钟内有效，如非本人操作请忽略)"; 
//	public static String REGISTERDOCSTR = "你申请注册天使医生(医生端)的验证码是%s。(3分钟内有效，如非本人操作请忽略)"; 
//	public static String RESET_PWD_STR= "%s（天使医生重设密码验证码，3分钟内有效）"; 
//	public static String RESET_DOC_PWD = "天使医生重置密码成功，您的随机密码为%s"; 
//	public static String EMAIL_TITLE = "Angel Voices verification code email!";
//	public static String EN_REGISTER_STR = "You apply for registration Angel Voices user verification code is%s. (Valid within three minutes, if not I operate Ignore)";
//	public static String EN_RESET_PW_STR = "%s(Angel Voices to reset your password verification code within three minutes active)";
//	public static String APPOINTMENT_SUCCESS = " 您已成功预约%s的门诊！订单号:%s；就诊人：%s；时间：%s。请凭订单号提前30分钟到医院取号，如若不能按时就诊请提前一天于16:00之前取消。";
//	public static String CONSULTANTREFUSED = "很抱歉，由于医生忙碌无法为您解答，我们将问题转为免费咨询以便您更快得到答复。咨询费用将在1~3个工作日内退回。欢迎再次使用天使医生咨询服务。";
//	/**加号*/
//	public static String PLUS_SUCCESS = "恭喜亲成功预约%s医生！请亲于%s年%s月%s日%s时%s分到%s%s就诊。温馨提醒：%s医生可是非常守时的哟，亲不要迟到哦。";
//	public static String PLUS_FAILED= "太遗憾了！%s医生是个好大夫，他在%s年%s月%s日的时间已全部被预约了～不过没关系，亲，您还可以选其它时间预约%s医生或者预约其它医生。祝亲预约顺利！您的预约费用我们将在3个工作日内退到您的账户。";
//	
	/*********************** 短信 ************************/
	public static int CODE_LIMIT_TIME =300;				//短信验证的时间默认限制5分钟
	public static String REGISTER_STR = "%s是你申请注册天使医生(用户端)的验证码。(3分钟内有效，如非本人操作请忽略)"; 
	public static String REGISTERDOCSTR = "%s是你申请注册天使医生(医生端)的验证码。(3分钟内有效，如非本人操作请忽略)"; 
	public static String RESET_PWD_STR= "%s(天使医生重设密码验证码，3分钟内有效)"; 
	public static String RESET_DOC_PWD = "天使医生重置密码成功，%s是您的随机密码";
	public static String UPDATE_BASE_INFO_MOBILE = "%s(手机号绑定验证码，3分钟内有效)";
	public static String EMAIL_TITLE = "Angel Voices verification code email!";
	public static String EN_REGISTER_STR = "You apply for registration Angel Voices user verification code is%s. (Valid within three minutes, if not I operate Ignore)";
	public static String EN_RESET_PW_STR = "%s(Angel Voices to reset your password verification code within three minutes active)";
	public static String APPOINTMENT_SUCCESS = " 您已成功预约%s的门诊！订单号:%s；就诊人：%s；时间：%s。请凭订单号提前30分钟到医院取号，如若不能按时就诊请提前一天于16:00之前取消。";
	public static String CONSULTANTREFUSED = "很抱歉，由于医生忙碌无法为您解答，我们将问题转为免费咨询以便您更快得到答复。咨询费用将在1~3个工作日内退回。欢迎再次使用天使医生咨询服务。";
	/**加号*/
	public static String PLUS_SUCCESS = "恭喜亲成功预约%s医生！请亲于%s年%s月%s日%s时%s分到%s%s就诊。温馨提醒：%s医生可是非常守时的哟，亲不要迟到哦。";
	public static String PLUS_FAILED= "太遗憾了！%s医生是个好大夫，他在%s年%s月%s日的时间已全部被预约了～不过没关系，亲，您还可以选其它时间预约%s医生或者预约其它医生。祝亲预约顺利！您的预约费用我们将在3个工作日内退到您的账户。";
	
	
	/*********************** 国际化 ************************/
	public static String CN = "cn";						//中文国际化
	public static String EN = "en";						//英语国际化
	
	/*********************** 微信支付 ************************/
	public static String PAY_PARTNER_ID = "1232960901";		//发送邮件邮箱地址
	public static String PAY_NOTIFY_URL = "";		//支付结果通知链接
	public static String PAY_SPBILL_CAEATE_IP = "";
	public static String PAY_APP_KEY = "pAcdeh1HZ0MbF41qPOIrTkUW9qkfSeY6PmPkHmUfZ2LEVuqOwMmqgrO8EhDV7LZrn3AdupOgXTsb5Uu1a0OXK8EzxokhDJb9Tgo6XKFrLmTKYknGRGLD1VFzRDOADQUD";		
	public static String PAY_APP_ID = "wx6b4e648d236bdc9d";
	public static String PAY_APP_SECRET = "";
	public static String PAY_PARTNER_KEY = "";
	/*********************** 邮件 ************************/
	public static String SENDER_EMAIL_ADDRESS = "";		//发送邮件邮箱地址
	public static String SENDER_USER_NAME = "";			//发送邮件服务器用户
	public static String SENDER_PASS_WORD = "";			//发送邮件服务器密码
	public static String SMTP_SERVER_NAME = "";			//服务器地址
	public static String TRANSPORT_TYPE = "";			//类型
	
	public static String INDEX_DIC = "";				//资讯新闻分词文件存放路径

    public static String POST_INDEX_DIC = "";						//帖子分词文件存放目录
	
	/************************春雨医生*************************/
	public static String SPRING_APP_KEY = "";		//春雨医生的APP_KEY
	public static String SPRING_URL = "";		//春雨医生URL
	public static String DEVICE_TYPE = "";		//设备
	public static String ASK_URL = "";		//调出春雨医生咨询接口的URL
	public static String PROBLEM_LIST_URL = "";		//获取春雨咨询的问题列表
	public static String PROBLEM_DETAIL_URL = "";		//获取春雨咨询的问题详情
	
	/***************************jedis_id****************************/
	public static String JEDIS_IP = "";	
	
	public static String SELLER_ID = "";				//支付宝支付seller_id
	public static String PID = "";						//支付宝支付pid
	public static String KEY = "";						//支付宝支付key
	public static String NOTIFY_URL = "";				//支付宝支付回调
	public static String PRIVATE_KEY = "";				//支付宝支付私钥
	
	public static String BANK_PROPERTIES_FILE = "";		//银行卡类型图标
	
	public static String MONITOR_TEST_REPORT_DIR= "";		//远程监控测试报告存放路径
	
	public static String TEST_MOBILE = "";		//测试手机号
	
	/***************************缩略图属性****************************/
	public static String THUMBNAIL_PREFIX = "_small";         //缩略图后缀名
	public static Integer THUMBNAIL_WIDTH = 80 ;             //缩略图宽
	public static Integer THUMBNAIL_HEIGHT = 80 ;            //缩略图高
	
	/***************************密码格式****************************/
	public static String PASSWORD_FORMAT = "^[0-9a-zA-Z]{6,16}$";
	
	/***************************手机格式****************************/
	public static String MOBILE_FORMAT = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
	
	public static String PLAN_SHARE_URL = "http://admin.jumper-health.com/cms/s?id=";
	
	public static String BAIDU_SHORT_URL = "http://dwz.cn/create.php";
	
	/*************************相关文章缓存**************************/
	public static int RELEVANT_NEWS_CACHE_TIME = 60*60*24*7; //相关文章缓存时间 7天 60*60*24*7
	
	/*************************资讯路径***************************/
	public static String NEWS_PATH = "http://mobile.jumper-health.com/mobile/news/page.do?id=";
	/*************************医院资讯路径***************************/
	public static String HOSPITAL_NEWS_URL= "http://mobile.jumper-health.com/mobile/news/information.do?id=";
	
	/** 远程监控校验用户以后取得连接IP端口等信息请求地址 **/
	public static String GET_MONITOR_ADDRESS = "http://10.0.1.67:8081/JumperRemoteMonitor/httpApi";
	
	/**  默认关联医院ID  **/
	public static int DEFAULT_COMMON_HOSPITAL= 0;
	
	/*************************天使医院ID***************************/
	public static Integer ANGELSOUND_HOSPITAL_ID = 49;
	/** 测试类型1.胎心；2.血氧；3.心率；4.体温；5.体重；6.血压；7.血糖；8.胎动；9.尿液 **/
	public static String PREGNANT_HEALTHY_SETTING_ORDER = "";
	public static String BASE_MOBILE_URL="http://mobile.jumper-health.com/mobile";
	
	/*************************医院资讯详情url***************************/
	public static String HOSPITAL_NEWS_DETAIL_URL = "http://mobile.jumper-health.com/mobile/news/informationDetail.do?id=";
	
	/*************************chat域url***************************/
	public static String BASE_CHAT_URL = "http://chat.jumper-health.com/chat";
	public static String BASE_CHAT_WEIGHT_URL = "";
	
	/*************************网页版页面根地址********************/
	public static String BASE_WEB_APP = "http://app.jumper-health.com/appweb";
	
	/*************************天使医生网页版页面跟地址********************/
	public static String HOSPITAL_MORE_URL = "/more/list.do";
	/*************************天使医生体重 营养地址********************/
	public static String BASE_PREGNANCY_URL = "http://pregancy.jumper-health.com";
	public static String WEIGHT_URL = "?c=mobile&a=health_management";
	
	public static String FETAL_RATE_SHARE = "http://10.0.1.115:8086/appweb/share/heartrate.do?i=";
	public static String REMOTECOUNTS="http://10.0.1.115:8084/hospital/user/count/";
	
	/*************************天使医生检查报告地址********************/
	public static String REPORT_URL = "/weight/?c=mobile&a=report_page";
	
	/*************************新版本微信支付********************/
	/** 微信统一下单URL **/
	public static String WEIXIN_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String PAY_NOTIFY_URL_V2 = "http://angeldoctor.6655.la:30709/notify/notifyWXV2";
	
	/*************************医院建档页面URL********************/
	public static String ARCHIVE_URL = "/hospital/archive.do";
	/*************************医院挂号页面URL********************/
	public static String REGISTER_URL = "/hospital/register.do";
	/*************************出生证明页面URL********************/
	public static String BIRTH_CERT_URL = "/hospital/birthcert.do";
	/*************************接口对应的域名********************/
	public static String API_HOST="http://192.168.2.124:8081";
	/*************************退款页面URL********************/
	public static String REFUND_URL = "/order/refund.do";
	/*************************退款帮助页面URL********************/
	public static String REFUND_HELP = "/static/refund/html/refundHelp.html";
	
	public static String DOCTOR_REFUND_URL = "/order/doctorRefund.do";
	
	/*************************退款接口********************/
	public static String REFUND_REQUEST_URL = "http://192.168.2.67:8080/notify/handler/refund";

 
	/*************************家庭医生查看pdfurl***************************/
	public static String FAMILYDOCTOR_PDF_URL ="";
	
	public static String DOCTOR_REFUND_DETAIL_URL="/order/consulttantRefundDetail.do";
	
	/*******************   血糖小知識       ***********************/
	public static String SUGAR_KNOW="http://page.jumper-health.com/weight/sugar_know.html";
	
	public static String SHEN_FU_YOU_ECG_URL="http://58.62.17.235:702/mobile/ecg/getpushdata.do";
	/** 心电服务器类型 1：省妇幼 2：京柏 **/
	public static String ECG_SRVICE_TYPE="2";
	
	/*************************孕妇学校URL********************/
	public static String PREGNANT_OLD_SCHOOL_URL = "http://58.62.17.235:702/PregnantWomanSchool";
	public static String PREGNANT_SCHOOL_URL_2 = "http://192.168.2.115:9006/school/mobile/offlineApp?org=";
	/**胎心同步方法名*/
	public static String SYNC_HEART_DATA_METHOD = "";
	public static String MOBILE_FILE_URL = "";
	public static String GET_RECORD_NUM = "";
	
	public static int SEARCH_FUN_MODULE_COUNT = 4;//全局搜索的功能默认显示数量
	public static int SEARCH_DOCTOR_COUNT = 4;//全局搜索的医生默认显示数量
	public static int SEARCH_TOPIC_COUNT = 2;//全局搜索的话题默认显示数量
	public static int SEARCH_ARTICLE_COUNT = 2;//全局搜索的文章默认显示数量
	
	
	
}
