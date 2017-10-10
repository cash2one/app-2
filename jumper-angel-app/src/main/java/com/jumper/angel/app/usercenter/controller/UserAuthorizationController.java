package com.jumper.angel.app.usercenter.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alipay.api.AlipayApiException;
import com.alipay.servlet.auth.LoginAuthServlet;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.HttpUtils;
import com.jumper.angel.frame.util.ServiceUrlUtils;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;

/**
 * 用户授权控制器
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月18日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("user/v4.1/auth")
public class UserAuthorizationController {
	
	private final static Logger logger = Logger.getLogger(UserAuthorizationController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 微信、支付宝用户授权，获取授权码
	 * @version v.1.0
	 * @createTime 2017年4月17日,下午5:00:29
	 * @updateTime 2017年4月17日,下午5:00:29
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param type 渠道(1:支付宝  2:微信)
	 * @param serviceType 业务类型(1:首页  2:预约挂号  3:缴费  4:侯诊查询  5:医院介绍  6:健康百科  7:孕妇学校  8:健康咨询  9:个人信息  10:我的就诊卡  11:预约记录  12:检查检验)
	 * @param response
	 */
	@RequestMapping("wxinAndAlipayUserAuthorization")
	public void wxinUserAuthorization(@RequestParam("type") int type, @RequestParam("serviceType") int serviceType, @RequestParam("hospitalId") String hospitalId, HttpServletRequest request, HttpServletResponse response) {
		//微信授权地址
		String weixin_url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		//支付宝授权地址
		String alipay_url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?";
		try {
			System.out.println("===================== 开始授权 =====================");
			//支付宝
			if(type == 1) {
				//公众号的唯一标识
				String appid = ConfigProUtils.get("alipay_appid");
				//不弹出授权页面，直接跳转，只能获取用户openid 固定为auth_base
				String scope = ConfigProUtils.get("alipay_scope");
				//授权后重定向的回调链接地址，请使用urlencode对链接进行处理
				String redirect_uri = URLEncoder.encode(ConfigProUtils.get("alipay_redirect_uri")+"?serviceType="+serviceType+"&channel="+type+"&hospitalId="+hospitalId+"&", "utf-8");
				//固定为false
				boolean auth_skip = false;
				//支付宝授权地址
				alipay_url = alipay_url + "app_id="+appid+"&auth_skip="+auth_skip+"&scope="+scope+"&redirect_uri="+redirect_uri;
				System.out.println("===================== 支付宝授权地址:"+alipay_url+" =====================");
				//重定向地址
				response.sendRedirect(alipay_url);
			} else if(type == 2) { //微信
				//公众号的唯一标识
				String appid = ConfigProUtils.get("wx_appid");
				//授权后重定向的回调链接地址，请使用urlencode对链接进行处理
				String redirect_uri = URLEncoder.encode(ConfigProUtils.get("wx_redirect_uri")+"?serviceType="+serviceType+"&channel="+type+"&hospitalId="+hospitalId+"&", "utf-8");
				//返回类型，请填写code
				String response_type = ConfigProUtils.get("wx_response_type");
				//不弹出授权页面，直接跳转，只能获取用户openid
				String scope = ConfigProUtils.get("wx_scope");
				//重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
				String state=ConfigProUtils.get("wx_state");
				//微信授权地址
				weixin_url = weixin_url + "appid="+appid+"&redirect_uri="+redirect_uri+"&response_type="+response_type+"&scope="+scope+"&state="+state+"#wechat_redirect";
				System.out.println("===================== 微信授权地址:"+weixin_url+" =====================");
				//重定向地址
				response.sendRedirect(weixin_url);
			}
		} catch(Exception e) {
			logger.error("授权异常："+e.getMessage());
		}
	}
	
	/**
	 * 获取微信用户openid
	 * @version v.1.0
	 * @createTime 2017年4月17日,下午6:29:09
	 * @updateTime 2017年4月17日,下午6:29:09
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param request
	 * @return
	 */
	@RequestMapping("getWeixinOpenId")
	public void getWeixinOpenId(HttpServletRequest request, HttpServletResponse response) {
		try {
			//appid
			String appid = ConfigProUtils.get("wx_appid");
			//公众号中secret
			String secret = ConfigProUtils.get("wx_secret");
			//获取code
			String code = request.getParameter("code");
			//业务类型(1:预约挂号  2:挂号记录  3:检查检验  4:缴费)
			int serviceType = Integer.parseInt(request.getParameter("serviceType"));
			//渠道(1:支付宝  2:微信)
			int channel = Integer.parseInt(request.getParameter("channel"));
			//医院ID
			String hospitalId =request.getParameter("hospitalId");
			//业务地址
			String serviceUrl = ServiceUrlUtils.serviceUrl(serviceType);
			//请求地址
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
			//拼接url
			url = url + "appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
			//请求用户接口
			String result = HttpUtils.get(url);
			//字符串转JSON对象
			JSONObject jsonObject = JSONObject.fromObject(result);
			//得到openid
			String openid = jsonObject.getString("openid");
			//token
			String token = jsonObject.getString("access_token");
			//判断是否注册
			loginAndRegister(channel, openid, serviceUrl, token, hospitalId, response);
		} catch(Exception e) {
			logger.error("获取微信用户openid异常："+e.getMessage());
		}
	}
	
	/**
	 * 获取支付宝用户openid
	 * @version v.1.0
	 * @createTime 2017年4月18日,上午11:22:09
	 * @updateTime 2017年4月18日,上午11:22:09
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws AlipayApiException 
	 * @throws ServletException 
	 */
	@RequestMapping("getAlipayOpenId")
	public void getAlipayOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, ServletException {
		try {
			//业务类型(1:预约挂号  2:挂号记录  3:检查检验  4:缴费)
			int serviceType = Integer.parseInt(request.getParameter("serviceType"));
			//渠道(1:支付宝  2:微信)
			int channel = Integer.parseInt(request.getParameter("channel"));
			//医院ID
			String hospitalId =request.getParameter("hospitalId");
			//业务地址
			String serviceUrl = ServiceUrlUtils.serviceUrl(serviceType);
			//获取支付宝用户信息
			String result = LoginAuthServlet.doGet(request, response);
			//字符串转JSON对象
			JSONObject jsonObject = JSONObject.fromObject(result);
			//得到节点
			String alipaySystemOauthTokenResponse = jsonObject.getString("alipay_system_oauth_token_response");
			JSONObject json = JSONObject.fromObject(alipaySystemOauthTokenResponse);
			//openid
			String openid = json.getString("user_id");
			//token
			String token = json.getString("access_token");
			//判断是否注册
			loginAndRegister(channel, openid, serviceUrl, token, hospitalId, response);
		} catch(Exception e) {
			logger.error("获取支付宝用户openid异常："+e.getMessage());
		}
	}
	
	/**
	 * 登录、注册
	 * @version v.1.0
	 * @createTime 2017年4月21日,上午9:33:05
	 * @updateTime 2017年4月21日,上午9:33:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channel 渠道(1:支付宝  2:微信)
	 * @param openid
	 * @param url 业务地址路径
	 * @param token
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public void loginAndRegister(int channel, String openid, String url, String token, String hospitalId, HttpServletResponse response) {
		try {
			//通过openid查询用户信息
			UserInfo userInfo = userService.officialAccountsLogIn(channel, openid);
			//未注册
			if(null == userInfo) {
				//注册页面
				response.sendRedirect(ConfigProUtils.get("redirect_url")+"?url="+url+"&openId="+openid+"&cardId="+channel+"&hospitalId="+hospitalId+"&token="+token);
			} else {
				//拓展表
				UserExtraInfo userExtraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
				//解码
				url = URLDecoder.decode(url, "utf-8");
				//拼接参数
				String param = "userid="+userInfo.getId()+"&mobile="+userInfo.getMobile()+"&channel="+channel+"&openid="+openid+"&lmp="+new SimpleDateFormat("yyyy-MM-dd").format(userExtraInfo.getLastPeriod());
				//判断该链接是否带参数
				if(url.contains("?")) { //带参数
					url = url + "&" + param;
				} else { //不带参数
					url = url + "?" + param;
				}
				//重定向到业务模块去
				response.sendRedirect(url);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
