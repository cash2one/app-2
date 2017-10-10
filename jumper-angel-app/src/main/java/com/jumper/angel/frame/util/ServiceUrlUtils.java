package com.jumper.angel.frame.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 业务地址工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月24日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ServiceUrlUtils {
	
	/**
	 * 业务地址
	 * @version v.1.0
	 * @createTime 2017年4月24日,下午3:55:08
	 * @updateTime 2017年4月24日,下午3:55:08
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param serviceType 业务类型 (1:首页  2:预约挂号  3:缴费  4:侯诊查询  5:医院介绍  6:健康百科  7:孕妇学校  8:健康咨询  9:个人信息  10:我的就诊卡  11:预约记录  12:检查检验)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String serviceUrl(int serviceType) throws UnsupportedEncodingException {
		//业务地址
		String service_url = "";
		switch (serviceType) {
			case 1:
				service_url = URLEncoder.encode(ConfigProUtils.get("index_url"), "utf-8");
				break;
			case 2:
				service_url = URLEncoder.encode(ConfigProUtils.get("reg_url"), "utf-8");
				break;
			case 3:
				service_url = URLEncoder.encode(ConfigProUtils.get("payment_url"), "utf-8");
				break;
			case 4:
				service_url = URLEncoder.encode(ConfigProUtils.get("wait_query_url"), "utf-8");
				break;
			case 5:
				service_url = URLEncoder.encode(ConfigProUtils.get("hospital_url"), "utf-8");
				break;
			case 6:
				service_url = URLEncoder.encode(ConfigProUtils.get("health_url"), "utf-8");
				break;
			case 7:
				service_url = URLEncoder.encode(ConfigProUtils.get("school_url"), "utf-8");
				break;
			case 8:
				service_url = URLEncoder.encode(ConfigProUtils.get("health_consult_url"), "utf-8");
				break;
			case 9:
				service_url = URLEncoder.encode(ConfigProUtils.get("person_info_url"), "utf-8");
				break;
			case 10:
				service_url = URLEncoder.encode(ConfigProUtils.get("my_card_url"), "utf-8");
				break;
			case 11:
				service_url = URLEncoder.encode(ConfigProUtils.get("reg_record_url"), "utf-8");
				break;
			case 12:
				service_url = URLEncoder.encode(ConfigProUtils.get("checkout_url"), "utf-8");
				break;
			default:
				service_url = "";
				break;
		}
		return service_url;
	}
}
