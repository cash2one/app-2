package com.jumper.angel.app.thirdparty.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.Util;
import com.jumper.common.service.sms.SMSService;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.bean.po.UserVerifiedCode;
import com.jumper.dubbo.service.UserService;

/**
 * H5登录、注册控制器
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月19日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("user/v4.1")
public class H5UserController {
	
	private final static Logger logger = Logger.getLogger(H5UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SMSService sMSService;
	
	/**
	 * 跳转到绑定手机页面
	 * @version v.1.0
	 * @createTime 2017年4月19日,下午2:58:24
	 * @updateTime 2017年4月19日,下午2:58:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("forwordLoginJsp")
	public ModelAndView forwordLoginJsp(HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", URLEncoder.encode(request.getParameter("url"), "utf-8"));
		mv.addObject("openId", request.getParameter("openId"));
		mv.addObject("cardId", request.getParameter("cardId"));
		mv.addObject("hospitalId", request.getParameter("hospitalId"));
		mv.addObject("token", request.getParameter("token"));
		mv.addObject("redirectUrl", request.getParameter("url"));
		mv.setViewName("login");
		return mv;
	}
	
	/**
	 * 完善资料页面
	 * @version v.1.0
	 * @createTime 2017年4月19日,下午4:35:50
	 * @updateTime 2017年4月19日,下午4:35:50
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping("forwordPerfectDataJsp")
	public ModelAndView forwordPerfectDataJsp(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", request.getParameter("url"));
		mv.addObject("mobile", request.getParameter("mobile"));
		mv.addObject("openId", request.getParameter("openId"));
		mv.addObject("cardId", request.getParameter("cardId"));
		mv.addObject("hospitalId", request.getParameter("hospitalId"));
		mv.addObject("token", request.getParameter("token"));
		mv.setViewName("perfect_data");
		return mv;
	}
	
	/**
	 * 个人信息
	 * @version v.1.0
	 * @createTime 2017年4月25日,上午11:18:05
	 * @updateTime 2017年4月25日,上午11:18:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param request
	 * @return
	 */
	@RequestMapping("forwordPersonInfoJsp")
	public ModelAndView forwordPersonInfoJsp(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		//openid
		String openid = request.getParameter("openid");
		//渠道
		int channel = Integer.parseInt(request.getParameter("channel"));
		//通过openid查询用户信息
		UserInfo userInfo = userService.officialAccountsLogIn(channel, openid);
		//用户拓展数据
		UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
		mv.addObject("user", userInfo);
		mv.addObject("extraInfo", extraInfo);
		String userImg = "";
		if(null != userInfo.getUserImg()) {
			if(userInfo.getUserImg().contains("http")) {
				userImg = userInfo.getUserImg();
			} else {
				userImg = ConfigProUtils.get("file_path")+userInfo.getUserImg();
			}
		}
		mv.addObject("userImg", userImg);
		mv.addObject("channel", channel);
		mv.setViewName("person_info");
		return mv;
	}
	
	/**
	 * 退出登录
	 * @version v.1.0
	 * @createTime 2017年4月25日,下午6:09:19
	 * @updateTime 2017年4月25日,下午6:09:19
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value="exitLogin", method=RequestMethod.GET)
	@ResponseBody
	public ResultMsg exitLogin(@RequestParam("userId") String userId, @RequestParam("channel") int channel) {
		try {
			//通过用户ID查询用户信息
			UserInfo user = userService.getAllUserInfoByUserId(Integer.parseInt(userId));
			//支付宝
			if(channel == 1) {
				user.setAlipayOpenId("");
			} else if(channel == 2) { //微信
				user.setWxOpenId("");
			}
			//更新
			userService.updUserInfo(user);
			return new ResultMsg(Const.SUCCESS, "退出登录成功");
		} catch (Exception e) {
			logger.error("exitLogin()", e);
			return new ResultMsg(Const.FAILED, "退出登录失败");
		}
	}
	
	/**
	 * 登录
	 * @version v.1.0
	 * @createTime 2017年4月21日,下午12:00:04
	 * @updateTime 2017年4月21日,下午12:00:04
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value="verifedCode/{mobile}/{code}/{openid}/{channel}", method=RequestMethod.GET)
	@ResponseBody
	public ResultMsg verifedCode(@PathVariable("mobile") String mobile, @PathVariable("code") String code, 
			@PathVariable("openid") String openid, @PathVariable("channel") String channel) {
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ResultMsg(Const.FAILED, "手机号码不能为空！");
			}
			if(StringUtils.isEmpty(code)) {
				return new ResultMsg(Const.FAILED, "请先输入验证码！");
			}
			//通过手机号码查询验证码
			UserVerifiedCode userVerifiedCode = userService.findVerifiedCodeByMobileSQL(mobile);
			if(userVerifiedCode == null) {
				return new ResultMsg(Const.FAILED, "验证码已过期，请重新获取！");
			}
			if(!userVerifiedCode.getCode().equals(code)) {
				return new ResultMsg(Const.FAILED, "验证码错误，请重新输入！");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(mobile);
			//通过openid查询用户信息
			UserInfo userInfo = userService.officialAccountsLogIn(Integer.parseInt(channel), openid);
			//手机号码存在并且openid不存在
			if(null != user && null == userInfo) {
				user.setRegisterChannel(Integer.parseInt(channel));
				if(Integer.parseInt(channel) == 1) { //支付宝生活号
					user.setAlipayOpenId(openid);
				} else if(Integer.parseInt(channel) == 2) { //微信公众号
					user.setWxOpenId(openid);
				}
				//更新用户
				userService.updUserInfo(user);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user", user);
				map.put("userExtraInfo", new SimpleDateFormat("yyyy-MM-dd").format(userService.getExtraUserInfoByUserId(user.getId()).getLastPeriod()));
				//绑定openid
				return new ResultMsg(2, "成功", map);
			}
			return new ResultMsg(Const.SUCCESS, "成功");
		} catch (Exception e) {
			logger.error("verifedCode()", e);
			return new ResultMsg(Const.FAILED, "失败");
		}
	}
	
	/**
	 * 发送验证码
	 * @version 1.0
	 * @createTime 2016-12-8,下午3:10:01
	 * @updateTime 2016-12-8,下午3:10:01
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param mobile 手机号码
	 * @param type 验证码类型(0:默认注册验证码, 1:设置手机密码验证码, 2:绑定手机号获取验证码，后台判断该手机号未被其他账号绑定过时返回验证码) 
	 * @param hospId 医院ID
	 * @return
	 */
	@RequestMapping(value="getSmsCode/{mobile}/{type}/{hospId}", method=RequestMethod.GET)
	@ResponseBody
	public ResultMsg getSmsCode(@PathVariable("mobile") String mobile, 
			@PathVariable("type") int type, 
			@PathVariable("hospId") String hospId) {
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ResultMsg(Const.FAILED, "手机号码不能为空！");
			}
			if(!Util.isMobiPhoneNum(mobile)) {
				return new ResultMsg(Const.FAILED, "您输入的手机号码格式不正确，请重新输入！");
			}
			//生成6位数的随机验证码
			String code = RandomStringUtils.random(6, false, true);
			String msg = code+"是你申请注册天使医生(用户端)的验证码。(3分钟内有效，如非本人操作请忽略)";
			sMSService.sengSMSMsg(hospId, mobile, msg);
			logger.info("验证码："+code);
			UserVerifiedCode uv = new UserVerifiedCode();
			uv.setMobile(mobile);
			uv.setCode(code);
			//新增验证码
			userService.saveUserVerifiedCode(uv);
			return new ResultMsg(Const.SUCCESS, "验证码发送成功！");
		} catch (Exception e) {
			logger.error("getSmsCode()", e);
			return new ResultMsg(Const.FAILED, "验证码发送失败！");
		}
	}
}
