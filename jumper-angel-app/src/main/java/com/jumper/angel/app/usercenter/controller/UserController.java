package com.jumper.angel.app.usercenter.controller;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.jumper.anglesound.service.HealthBaseService;

import com.jumper.angel.app.bindhospital.entity.BindHospitalLog;
import com.jumper.angel.app.bindhospital.service.BindHospitalLogService;
import com.jumper.angel.app.usercenter.entity.UserHealthNumber;
import com.jumper.angel.app.usercenter.service.UserHealthNumberService;
import com.jumper.angel.app.usercenter.vo.UserInfoVo;
import com.jumper.angel.app.usercenter.vo.UserParam;
import com.jumper.angel.app.usercenter.vo.UserVo;
import com.jumper.angel.app.usercenter.vo.VOUserInfo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
import com.jumper.angel.frame.util.EmojiFilterUtil;
import com.jumper.angel.frame.util.IntervalUtils;
import com.jumper.angel.frame.util.MD5EncryptUtils;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.angel.frame.util.Util;
import com.jumper.angel.frame.util.WebRequestUtils;
import com.jumper.common.service.im.ImAccountsService;
import com.jumper.common.service.sms.SMSService;
import com.jumper.common.utils.CommonReturnMsg;
import com.jumper.dubbo.bean.po.AddrCity;
import com.jumper.dubbo.bean.po.AddrCountry;
import com.jumper.dubbo.bean.po.AddrProvince;
import com.jumper.dubbo.bean.po.HospitalInfo;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.bean.po.UserVerifiedCode;
import com.jumper.dubbo.bean.vo.UserSubscribeChannelVO;
import com.jumper.dubbo.service.AuthKeyService;
import com.jumper.dubbo.service.HospitalService;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * 用户controller
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	private final static Logger logger = Logger.getLogger(UserController.class);
	
	//天使医院id
	private static Integer ANGELSOUND_HOSPITAL_ID = 49;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthKeyService authKeyService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private UserHealthNumberService userHealthNumberService;
	
	@Autowired
	private HealthBaseService healthBaseService;
	
	@Autowired
	private SMSService sMSService;
	
	@Autowired
	private ImAccountsService imAccountsService;
	
	@Autowired
	private BindHospitalLogService bindHospitalLogService;
	
	/**
	 * 用户登录
	 * @version 1.0
	 * @createTime 2016-12-6,下午4:30:24
	 * @updateTime 2016-12-6,下午4:30:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param 手机号码和密码参数
	 * @return
	 */
	@RequestMapping(value="userLogin", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="用户登录", httpMethod="POST", notes="用户登录")
	public ResultMsg userLogin(@ApiParam(name="param", value="用户登录参数", required = true) @RequestBody UserVo userInfo, HttpServletRequest request) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isEmpty(userInfo.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入您的手机号");
			}
			if(userInfo.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(userInfo.getMobile())==null) {
				return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
			}
			if(StringUtils.isEmpty(userInfo.getPassword())) {
				return new ResultMsg(Const.FAILED, "密码不能为空");
			}
			if(userInfo.getPassword().length()<6) {
				return new ResultMsg(Const.FAILED, "密码不得少于6位");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(userInfo.getMobile());
			if(user != null) {
				//提示密码错误次数
				if(null!=user.getErrorCount()) {
					if(user.getErrorCount().intValue()>=6 && user.getErrorDate().equals(format.format(new Date()))) {
						return new ResultMsg(Const.FAILED, "当日密码错误次数过多，账号已经被禁用；请使用找回密码功能");
					}
				}
				//该用户是否被禁用
				if(user.getStatus() == 0) {
					return new ResultMsg(Const.FAILED, "登录失败，该用户被禁用请联系管理员");
				}
				//判断密码是否匹配
				if(user.getPassword().equals(MD5EncryptUtils.getMd5Value(userInfo.getPassword()))) {
					//更新预产期  注册成功登录才执行该判断
					if(user.getExpectedDateOfConfinement() == null) {
						//判断传递过来的预产期是否为空
						if(!StringUtils.isEmpty(userInfo.getExpectDate())) {
							Date date = TimeUtils.convertToDate(userInfo.getExpectDate());
							user.setExpectedDateOfConfinement(date);
							userService.updUserInfo(user);
						}
					}
					//记录当前用户登录的IP地址,登录时间,及手机的类型
					UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(user.getId());
					if(userExtra!=null) {
						//更新
						userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
						userExtra.setLoginTime(new Date());
						userExtra.setMobileType(userInfo.getMobileType());
						userService.updUserExtraInfo(userExtra);
					} else {
						//添加
						userExtra = new UserExtraInfo();
						userExtra.setLoginTime(new Date());
						userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
						userExtra.setMobileType(userInfo.getMobileType());
						userExtra.setIsChinaUser(1);
						userExtra.setCurrentIdentity((byte)0);
						userService.saveUserExtraInfo(userExtra);
					}
					//查询用户是否订阅了频道
					UserSubscribeChannelVO uvo = new UserSubscribeChannelVO();
					uvo.setUserId(user.getId());
					List<UserSubscribeChannelVO> list = userService.findUserSubscribeChannel(uvo);
					if(list == null) {
						
					}
					//拼装返回数据
					VOUserInfo vo = getVOUserInfo(user, userExtra);
					user.setErrorCount(0);
					user.setErrorDate("");
					//更新
					userService.updUserInfo(user);
					return new ResultMsg(Const.SUCCESS, "登录成功！", vo);
				} else {
					//时间为空
					if(StringUtils.isEmpty(user.getErrorDate())) {
						user.setErrorCount(1);
					} else if(!user.getErrorDate().equals(format.format(new Date()))) { //时间不相同
						user.setErrorCount(1);
					} else {
						user.setErrorCount(null==user.getErrorCount()?0+1:user.getErrorCount().intValue()+1);
					}
					user.setErrorDate(format.format(new Date()));
					//更新
					userService.updUserInfo(user);
					return new ResultMsg(Const.FAILED, "密码错误，请重新输入");
				}
			} else {
				return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
			}
		} catch (Exception e) {
			logger.error("userLogin()", e);
			return new ResultMsg(Const.FAILED, "登录失败！");
		}
	}
	
	/**
	 * v1.01义乌妇幼用户登录
	 * @version 1.0
	 * @createTime 2017-07-13,下午16:30:24
	 * @updateTime 2017-07-13,下午16:30:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param 手机号码和密码参数
	 * @return
	 */
	@RequestMapping(value="v1.01/userLogin", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="v1.01义乌妇幼用户登录", httpMethod="POST", notes="v1.01义乌妇幼用户登录")
	public ResultMsg userLoginV101(@ApiParam(name="param", value="用户登录参数", required = true) @RequestBody UserVo userInfo, HttpServletRequest request) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isEmpty(userInfo.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入您的手机号");
			}
			if(userInfo.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(userInfo.getMobile())==null) {
				return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
			}
			if(StringUtils.isEmpty(userInfo.getPassword())) {
				return new ResultMsg(Const.FAILED, "密码不能为空");
			}
			if(userInfo.getPassword().length()<6) {
				return new ResultMsg(Const.FAILED, "密码不得少于6位");
			}
			if(StringUtils.isEmpty(userInfo.getHospitalId())) {
				return new ResultMsg(Const.FAILED, "医院ID不能为空");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(userInfo.getMobile());
			if(user != null) {
				//提示密码错误次数
				if(null!=user.getErrorCount()) {
					if(user.getErrorCount().intValue()>=6 && user.getErrorDate().equals(format.format(new Date()))) {
						return new ResultMsg(Const.FAILED, "当日密码错误次数过多，账号已经被禁用；请使用找回密码功能");
					}
				}
				//该用户是否被禁用
				if(user.getStatus() == 0) {
					return new ResultMsg(Const.FAILED, "登录失败，该用户被禁用请联系管理员");
				}
				//判断密码是否匹配
				if(user.getPassword().equals(MD5EncryptUtils.getMd5Value(userInfo.getPassword()))) {
					//更新预产期  注册成功登录才执行该判断
					if(user.getExpectedDateOfConfinement() == null) {
						//判断传递过来的预产期是否为空
						if(!StringUtils.isEmpty(userInfo.getExpectDate())) {
							Date date = TimeUtils.convertToDate(userInfo.getExpectDate());
							user.setExpectedDateOfConfinement(date);
							userService.updUserInfo(user);
						}
					}
					//记录当前用户登录的IP地址,登录时间,及手机的类型
					UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(user.getId());
					if(userExtra!=null) {
						//更新
						userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
						userExtra.setLoginTime(new Date());
						userExtra.setMobileType(userInfo.getMobileType());
						userExtra.setCommonHospital(Integer.parseInt(userInfo.getHospitalId()));
						userService.updUserExtraInfo(userExtra);
					} else {
						//添加
						userExtra = new UserExtraInfo();
						userExtra.setUserId(user.getId());
						userExtra.setLoginTime(new Date());
						userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
						userExtra.setMobileType(userInfo.getMobileType());
						userExtra.setIsChinaUser(1);
						userExtra.setCurrentIdentity((byte)0);
						userExtra.setCheckStatus(0);
						userExtra.setCommonHospital(Integer.parseInt(userInfo.getHospitalId()));
						userService.saveUserExtraInfo(userExtra);
					}
					//拼装返回数据
					VOUserInfo vo = getVOUserInfo(user, userExtra);
					user.setErrorCount(0);
					user.setErrorDate("");
					//更新
					userService.updUserInfo(user);
					return new ResultMsg(Const.SUCCESS, "登录成功", vo);
				} else {
					//时间为空
					if(StringUtils.isEmpty(user.getErrorDate())) {
						user.setErrorCount(1);
					} else if(!user.getErrorDate().equals(format.format(new Date()))) { //时间不相同
						user.setErrorCount(1);
					} else {
						user.setErrorCount(null==user.getErrorCount()?0+1:user.getErrorCount().intValue()+1);
					}
					user.setErrorDate(format.format(new Date()));
					//更新
					userService.updUserInfo(user);
					return new ResultMsg(Const.FAILED, "密码错误，请重新输入");
				}
			} else {
				return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
			}
		} catch (Exception e) {
			logger.error("userLogin()", e);
			return new ResultMsg(Const.FAILED, "登录失败");
		}
	}
	
	/**
	 * 第三方登录
	 * @version 1.0
	 * @createTime 2016-12-21,下午6:04:29
	 * @updateTime 2016-12-21,下午6:04:29
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value="thirdPartyLogin", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="第三方登录", httpMethod="POST", notes="第三方登录")
	public ResultMsg thirdPartyLogin(@ApiParam(name="param", value="用户登录参数", required = true) @RequestBody UserVo user, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(user.getOpenId())) {
				return new ResultMsg(Const.FAILED, "openId不能为空");
			}
			//查询字段
			String field = "";
			if(user.getUserType() == 1) { //新浪
				field = "sina_open_id";
			} else if(user.getUserType() == 2) { //QQ
				field = "qq_open_id";
			} else if(user.getUserType() == 3) { //微信
				field = "weixin_open_id";
			} else {
				return new ResultMsg(Const.FAILED, "非法用户类型");
			}
			//通过openId查询用户信息
			UserInfo userInfo = userService.getUserInfoBytypeOpenId(field, user.getOpenId(), user.getUserType());
			//用户未注册
			if(null == userInfo) {
				return new ResultMsg(Const.SUCCESS, "未注册第三方帐号");
			}
			//判断用户是否被禁用
			if(userInfo.getStatus() == 0) {
				return new ResultMsg(Const.FAILED, "登录失败，该用户被禁用请联系管理员");
			}
			//通过用户ID查询用户拓展表
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(userInfo.getId());
			if(null != userExtra) {
				//更新
				userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
				userExtra.setLoginTime(new Date());
				userExtra.setMobileType(user.getMobileType());
				userService.updUserExtraInfo(userExtra);
			}
			//组装数据
			VOUserInfo vo = getVOUserInfo(userInfo, userExtra);
			return new ResultMsg(Const.SUCCESS, "第三方登录成功", vo);
		} catch (Exception e) {
			logger.error("thirdPartyLogin()", e);
			return new ResultMsg(Const.FAILED, "第三方登录失败");
		}
	}
	
	/**
	 * 账号合并
	 * @version v.1.0
	 * @createTime 2017年4月13日,下午4:04:03
	 * @updateTime 2017年4月13日,下午4:04:03
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="multiAccountMerge", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="合并账号", httpMethod="POST", notes="合并账号")
	public ResultMsg multiAccountMerge(@ApiParam(name="param", value="用户登录参数", required = true) @RequestBody UserVo user, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(user.getOpenId())) {
				return new ResultMsg(Const.FAILED, "openId不能为空");
			}
			if(StringUtils.isEmpty(user.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入手机号码");
			}
			if(!Util.isMobiPhoneNum(user.getMobile())) {
				return new ResultMsg(Const.FAILED, "您输入的手机号码格式不正确，请重新输入");
			}
			if(StringUtils.isEmpty(user.getVerfiedCode())) {
				return new ResultMsg(Const.FAILED, "请输入验证码");
			}
			//通过手机号码查询验证码
			UserVerifiedCode userVerifiedCode = userService.findVerifiedCodeByMobileSQL(user.getMobile());
			if(null == userVerifiedCode) {
				return new ResultMsg(Const.FAILED, "验证码已过期，请重新获取");
			}
			if(!userVerifiedCode.getCode().equals(user.getVerfiedCode())) {
				return new ResultMsg(Const.FAILED, "验证码错误，请重新输入");
			}
			/*if(StringUtils.isEmpty(user.getPassword())) {
				return new ResultMsg(Const.FAILED, "请输入密码");
			}*/
			//查询字段
			String field = "";
			if(user.getUserType() == 1) { //新浪
				field = "sina_open_id";
			} else if(user.getUserType() == 2) { //QQ
				field = "qq_open_id";
			} else if(user.getUserType() == 3) { //微信
				field = "weixin_open_id";
			} else {
				return new ResultMsg(Const.FAILED, "非法用户类型");
			}
			//通过openId查询用户信息
			UserInfo thirdUserInfo = userService.getUserInfoBytypeOpenId(field, user.getOpenId(), user.getUserType());
			//通过手机号码查询用户信息
			UserInfo mobileUserInfo = userService.findUserInfoByMobile(user.getMobile());
			if(null != mobileUserInfo) {
				//判断用户是否被禁用
				if(mobileUserInfo.getStatus() == 0) {
					return new ResultMsg(Const.FAILED, "登录失败，该用户被禁用请联系管理员");
				}
				if(user.getUserType() == 1 && !StringUtils.isEmpty(mobileUserInfo.getSinaOpenId())) { //新浪
					return new ResultMsg(Const.FAILED, "已有微博绑定该手机，请更换手机绑定");
				} else if(user.getUserType() == 2 && !StringUtils.isEmpty(mobileUserInfo.getQqOpenId())) { //QQ
					return new ResultMsg(Const.FAILED, "已有QQ绑定该手机，请更换手机绑定");
				} else if(user.getUserType() == 3 && !StringUtils.isEmpty(mobileUserInfo.getWeixinOpenId())) { //微信
					return new ResultMsg(Const.FAILED, "已有微信绑定该手机，请更换手机绑定");
				}
			}
			//第三方用户未注册
			if(null == thirdUserInfo) {
				//手机用户未注册
				if(null == mobileUserInfo) {
					return new ResultMsg(Const.SUCCESS, "请完善资料");
				} else { //手机已注册
					mobileUserInfo.setOpenId(user.getOpenId());
					//mobileUserInfo.setPassword(MD5EncryptUtils.getMd5Value(user.getPassword()));
					if (user.getUserType() == 1) {
						mobileUserInfo.setSinaOpenId(user.getOpenId());
					} else if (user.getUserType() == 2) {
						mobileUserInfo.setQqOpenId(user.getOpenId());
					} else if (user.getUserType() == 3) {
						mobileUserInfo.setWeixinOpenId(user.getOpenId());
					}
					mobileUserInfo.setNickName(user.getNickName());
					mobileUserInfo.setUserImg(user.getUserImg());
					//更新
					userService.updUserInfo(mobileUserInfo);
				}
			} else { //第三方用户已注册
				//手机用户未注册
				if(null == mobileUserInfo) {
					thirdUserInfo.setMobile(user.getMobile());
					//thirdUserInfo.setPassword(MD5EncryptUtils.getMd5Value(user.getPassword()));
					//更新
					userService.updUserInfo(thirdUserInfo);
				}
			}
			//通过用户ID查询用户拓展表
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(mobileUserInfo.getId());
			if(null != userExtra) {
				//更新
				userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
				userExtra.setLoginTime(new Date());
//				userExtra.setMobileType(user.getMobileType());
				userService.updUserExtraInfo(userExtra);
			}
			//组装数据
			VOUserInfo vo = getVOUserInfo(mobileUserInfo, userExtra);
			return new ResultMsg(Const.SUCCESS, "绑定成功", vo);
		} catch(Exception e) {
			logger.error("multiAccountMerge()", e);
			return new ResultMsg(Const.FAILED, "绑定失败");
		}
	}
	
	/**
	 * 第三方注册
	 * @version v.1.0
	 * @createTime 2017年4月13日,上午11:40:24
	 * @updateTime 2017年4月13日,上午11:40:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="thirdPartyRegister", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="第三方注册", httpMethod="POST", notes="第三方注册")
	public ResultMsg thirdPartyRegister(@ApiParam(name="param", value="第三方注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getOpen_id())) {
				return new ResultMsg(Const.FAILED, "openId不能为空");
			}
			//查询字段
			String field = "";
			if(param.getUser_type() == 1) { //新浪
				field = "sina_open_id";
			} else if(param.getUser_type() == 2) { //QQ
				field = "qq_open_id";
			} else if(param.getUser_type() == 3) { //微信
				field = "weixin_open_id";
			} else {
				return new ResultMsg(Const.FAILED, "非法用户类型");
			}
			//通过openId查询用户信息
			UserInfo thirdUserInfo = userService.getUserInfoBytypeOpenId(field, param.getOpen_id(), param.getUser_type());
			if(null != thirdUserInfo) {
				return new ResultMsg(Const.FAILED, "该第三方帐号已注册过，请返回登录");
			}
			if(StringUtils.isEmpty(param.getNick_name())) {
				return new ResultMsg(Const.FAILED, "昵称不能为空");
			}
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入手机号码");
			}
			if(!Util.isMobiPhoneNum(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "您输入的手机号码格式不正确，请重新输入");
			}
			/*if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "密码不能为空");
			}*/
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			if(null != userInfo) {
				return new ResultMsg(Const.FAILED, "该手机号已注册过，请先合并");
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//应用版本
			//String version = param.getVersion();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
//			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(param.getUser_type());
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):EmojiFilterUtil.filter(param.getNick_name()));
			user.setOpenId(param.getOpen_id());
			if(param.getUser_type() == 1) { //新浪
				user.setSinaOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 2) { //QQ
				user.setQqOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 3) { //微信
				user.setWeixinOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//是否是第三方登录
			if(param.getUser_type()>=1 && param.getUser_type()<=3) {
				userExtra.setIsChinaUser(1);
			} else {
				userExtra.setIsChinaUser(0);
			}
			//关联医院
			if(param.getMobile_type() == 0) {
				String version = param.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
						if(param.getHospital_id()==0){
							userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
						}else{
							userExtra.setCommonHospital(param.getHospital_id());
						}
					}
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			if(param.getMobile_type() == 0) {
				String version = param.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
						//医院id为0，默认绑定天使医院
						if(param.getHospital_id()==0){
							userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
							param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
						}else{
							userExtra.setCommonHospital(param.getHospital_id());
						}
					}
					//绑定医院流水
					BindHospitalLog log = new BindHospitalLog();
					log.setUserId((long)u.getId());
					log.setMobileIp(WebRequestUtils.getIpAddr(request));
					log.setLng(param.getLng());
					log.setLat(param.getLat());
					log.setMobileMac(param.getDevice_id());
					log.setVersionName(param.getVersion());
					log.setMobileType(param.getMobile_type());
					log.setFirstBinding(param.getFirstBind());
					//添加绑定医院流水记录
					bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
				}
			}
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			VOUserInfo vo = getVOUserInfo(u, userExtra);
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(u.getId(), babyBirthday, babySex);
			}
			return new ResultMsg(Const.SUCCESS, "第三方登录成功", vo);
		} catch (Exception e) {
			logger.error("thirdPartyRegister()", e);
			return new ResultMsg(Const.FAILED, "第三方注册失败");
		}
	}
	
	/**
	 * 重置密码
	 * @version 1.0
	 * @createTime 2016-12-7,上午11:46:33
	 * @updateTime 2016-12-7,上午11:46:33
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="resetPassword", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="重置密码", httpMethod="POST", notes="重置密码")
	public ResultMsg resetPassword(@ApiParam(name="param", value="重置密码参数", required = true) @RequestBody UserVo userInfo, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(userInfo.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入手机号码");
			}
			if(!Util.isMobiPhoneNum(userInfo.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号码");
			}
			if(userService.findUserInfoByMobile(userInfo.getMobile())==null) {
				return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
			}
			if(StringUtils.isEmpty(userInfo.getVerfiedCode())) {
				return new ResultMsg(Const.FAILED, "请先输入验证码");
			}
			//通过手机号码查询验证码
			UserVerifiedCode code = userService.findVerifiedCodeByMobileSQL(userInfo.getMobile());
			if(code == null) {
				return new ResultMsg(Const.FAILED, "该验证码已失效，请获取新的验证码");
			}
			if(!code.getCode().equals(userInfo.getVerfiedCode())) {
				return new ResultMsg(Const.FAILED, "验证码错误，请重新输入");
			}
			if(StringUtils.isEmpty(userInfo.getPassword())) {
				return new ResultMsg(Const.FAILED, "请输入密码");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(userInfo.getMobile());
			if(user != null) {
				//判断密码是否合法
//				if(!PasswordVerificationUtils.isPasswordValid(userInfo.getPassword())) {
//					return new ResultMsg(Const.FAILED, "密码重置失败，密码不合法！");
//				}
				user.setErrorCount(1);
				user.setPassword(MD5EncryptUtils.getMd5Value(userInfo.getPassword()));
				//更新密码
				userService.updUserInfo(user);
				return new ResultMsg(Const.SUCCESS, "密码重置成功！");
			} else {
				return new ResultMsg(Const.FAILED, "密码重置失败，手机号码不存在！");
			}
		} catch (Exception e) {
			logger.error("resetPassword()", e);
			return new ResultMsg(Const.FAILED, "密码重置失败！");
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
	@ApiOperation(value="发送验证码", httpMethod="GET", notes="发送验证码")
	public ResultMsg getSmsCode(@ApiParam(name="mobile", value="手机号码", required = true) @PathVariable("mobile") String mobile, 
			@ApiParam(name="type", value="验证码类型(0:默认注册验证码, 1:设置手机密码验证码, 2:绑定手机号获取验证码，后台判断该手机号未被其他账号绑定过时返回验证码)", required = true) @PathVariable("type") int type, 
			@ApiParam(name="hospId", value="医院ID", required = true) @PathVariable("hospId") String hospId) {
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ResultMsg(Const.FAILED, "请先输入手机号");
			}
			if(!Util.isMobiPhoneNum(mobile)) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号码");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(mobile);
			if(type == 0) {
				if(user != null) {
					return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
				}
			} else if(type == 1) {
				if(user == null) {
					return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
				}
			}
			//生成6位数的随机验证码
			String code = RandomStringUtils.random(6, false, true);
			String msg = "";
			if(type == 0) {
				msg = code+"是你申请注册天使医生(用户端)的验证码。(15分钟内有效，如非本人操作请忽略)";
			} else if(type == 1) {
				msg = "天使医生重置密码，"+code+"是您的验证码。(15分钟内有效，如非本人操作请忽略)";
			} else {
				msg = code+"是你申请天使医生(用户端)的验证码。(15分钟内有效，如非本人操作请忽略)";
			}
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
	
	/**
	 * v1.01独立APP发送短信接口
	 * @version v.1.0
	 * @createTime 2017年7月21日,上午11:09:10
	 * @updateTime 2017年7月21日,上午11:09:10
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param mobile 手机号码
	 * @param type 验证码类型(0:默认注册验证码, 1:设置手机密码验证码, 2:绑定手机号获取验证码，后台判断该手机号未被其他账号绑定过时返回验证码)
	 * @param hospId 医院ID
	 * @return
	 */
	@RequestMapping(value="v1.01/getSmsCode/{mobile}/{type}/{hospId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="v1.01独立APP发送短信接口", httpMethod="GET", notes="v1.01独立APP发送短信接口")
	public ResultMsg getSmsCode101(@ApiParam(name="mobile", value="手机号码", required = true) @PathVariable("mobile") String mobile, 
			@ApiParam(name="type", value="验证码类型(0:默认注册验证码, 1:设置手机密码验证码, 2:绑定手机号获取验证码，后台判断该手机号未被其他账号绑定过时返回验证码)", required = true) @PathVariable("type") int type, 
			@ApiParam(name="hospId", value="医院ID", required = true) @PathVariable("hospId") String hospId) {
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ResultMsg(Const.FAILED, "请输入手机号");
			}
			if(!Util.isMobiPhoneNum(mobile)) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号码");
			}
			if(StringUtils.isEmpty(hospId)) {
				return new ResultMsg(Const.FAILED, "请输入医院ID");
			}
			//医院信息
			HospitalInfo hospital = hospitalService.getHospitalInfoById(Integer.parseInt(hospId));
			if(null == hospital) {
				return new ResultMsg(Const.FAILED, "请输入正确的医院ID");
			}
			//通过手机号码查询用户信息
			UserInfo user = userService.findUserInfoByMobile(mobile);
			if(type == 0) {
				if(user != null) {
					return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
				}
			} else if(type == 1) {
				if(user == null) {
					return new ResultMsg(Const.FAILED, "该账号不存在，请先注册");
				}
			}
			//生成6位数的随机验证码
			String code = RandomStringUtils.random(6, false, true);
			String msg = "";
			//注册消息
			String registerMsg = "尊敬的用户{0}是你申请注册{1}(用户端)的验证码。(15分钟内有效，如非本人操作请忽略)";
			//重置密码消息
			String resetPwdMsg = "尊敬的用户{0}重置密码，{1}是您的验证码。(15分钟内有效，如非本人操作请忽略)";
			//其他消息
			String otherMsg = "{0}是你申请{1}(用户端)的验证码。(15分钟内有效，如非本人操作请忽略)";
			if(type == 0) {
				msg = MessageFormat.format(registerMsg, code, hospital.getName());
			} else if(type == 1) {
				msg = MessageFormat.format(resetPwdMsg, hospital.getName(), code);
			} else {
				msg = MessageFormat.format(otherMsg, code, hospital.getName());
			}
			//发送短信(浩博)
			CommonReturnMsg result = sMSService.sendSMSByHbWithIdAndName(Long.parseLong(hospId), hospital.getName(), Long.parseLong(mobile), msg, "001");
			if(result.getMsg() == 1) {
				logger.info("验证码："+code);
				UserVerifiedCode uv = new UserVerifiedCode();
				uv.setMobile(mobile);
				uv.setCode(code);
				//新增验证码
				userService.saveUserVerifiedCode(uv);
				return new ResultMsg(Const.SUCCESS, "验证码发送成功");
			} else {
				return new ResultMsg(Const.FAILED, "验证码发送失败");
			}
		} catch (Exception e) {
			logger.error("getSmsCode()", e);
			return new ResultMsg(Const.FAILED, "验证码发送失败");
		}
	}
	
	/**
	 * 验证码校验
	 * @version 1.0
	 * @createTime 2016-12-8,下午3:16:09
	 * @updateTime 2016-12-8,下午3:16:09
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="verifedCode/{mobile}/{code}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="验证码校验", httpMethod="GET", notes="验证码校验")
	public ResultMsg verifedCode(@ApiParam(name="mobile", value="手机号码", required = true) @PathVariable("mobile") String mobile,
			@ApiParam(name="code", value="验证码", required = true) @PathVariable("code") String code) {
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ResultMsg(Const.FAILED, "手机号码不能为空！");
			}
			if(!Util.isMobiPhoneNum(mobile)) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号码");
			}
			if(StringUtils.isEmpty(code)) {
				return new ResultMsg(Const.FAILED, "请先输入验证码");
			}
			//通过手机号码查询验证码
			UserVerifiedCode userVerifiedCode = userService.findVerifiedCodeByMobileSQL(mobile);
			if(userVerifiedCode == null) {
				return new ResultMsg(Const.FAILED, "该验证码已失效，请获取新的验证码");
			}
			if(!userVerifiedCode.getCode().equals(code)) {
				return new ResultMsg(Const.FAILED, "验证码错误，请重新输入");
			}
			return new ResultMsg(Const.SUCCESS, "验证码校验成功！");
		} catch (Exception e) {
			logger.error("verifedCode()", e);
			return new ResultMsg(Const.FAILED, "验证码校验失败！");
		}
	}
	
	/**
	 * 用户注册
	 * @version 1.0
	 * @createTime 2016-12-7,下午3:08:21
	 * @updateTime 2016-12-7,下午3:08:21
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="registerUser", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="用户注册", httpMethod="POST", notes="用户注册")
	public ResultMsg registerUser(@ApiParam(name="param", value="用户注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			System.out.println("headre:"+request.getHeader("DEVICE_INFORMATION"));
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请先输入手机号");
			}
			if(param.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(param.getMobile())!=null) {
				return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
			}
			if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "请设置密码");
			}
			//怀孕中
			if(param.getCurrent_identity()==0) { 
				if(StringUtils.isEmpty(param.getExpect_date()) && StringUtils.isEmpty(param.getLast_period())) {
					return new ResultMsg(Const.FAILED, "请设置预产期或者末次月经");
				}
			} else if(param.getCurrent_identity()==1) {
				if(param.getMobile_type() == 0) {
					String version = param.getVersion();
					String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
					if(Integer.parseInt(vNum)>1055) {
						if(StringUtils.isEmpty(param.getExpect_date())) {
							return new ResultMsg(Const.FAILED, "请提供原定的预产期");
						}
					}
				}
				if(StringUtils.isEmpty(param.getBaby_birthday())) {
					return new ResultMsg(Const.FAILED, "请提供宝宝生日");
				}
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//应用版本
			//String version = param.getVersion();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(0);
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):param.getNick_name());
			user.setRegisterChannel(param.getRegisterChannel());
			if(param.getRegisterChannel() == 1) { //支付宝生活号
				user.setAlipayOpenId(param.getOpen_id());
			} else if(param.getRegisterChannel() == 2) { //微信公众号
				user.setWxOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//关联医院
			if(param.getMobile_type() == 0) {
				String version = param.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
						if(param.getHospital_id()==0){
							userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
						}else{
							userExtra.setCommonHospital(param.getHospital_id());
						}
					}
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			if(param.getMobile_type() == 0) {
				String version = param.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
						//医院id为0，默认绑定天使医院
						if(param.getHospital_id()==0){
							userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
							param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
						}else{
							userExtra.setCommonHospital(param.getHospital_id());
						}
					}
					//绑定医院流水
					BindHospitalLog log = new BindHospitalLog();
					log.setUserId((long)u.getId());
					log.setMobileIp(WebRequestUtils.getIpAddr(request));
					log.setLng(param.getLng());
					log.setLat(param.getLat());
					log.setMobileMac(param.getDevice_id());
					log.setVersionName(param.getVersion());
					log.setMobileType(param.getMobile_type());
					log.setFirstBinding(param.getFirstBind());
					//添加绑定医院流水记录
					bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
				}
			}
			//监测im中是否存在该用户
//			imAccountsService.edit(u.getId().toString(), param.getMobile());
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			//用户拓展数据
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
			VOUserInfo vo = getVOUserInfo(userInfo, extraInfo);
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(userInfo.getId(), babyBirthday, babySex);
			}
			return new ResultMsg(Const.SUCCESS, "注册成功！", vo);
		} catch (Exception e) {
			logger.error("registerUser()", e);
			return new ResultMsg(Const.FAILED, "注册失败！");
		}
	}
		
	/**
	 * 组装返回用户数据
	 * @version 1.0
	 * @createTime 2016-12-7,上午11:16:23
	 * @updateTime 2016-12-7,上午11:16:23
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param user 用户信息
	 * @param userExtra 用户拓展信息
	 * @return
	 * @throws ParseException 
	 */
	public VOUserInfo getVOUserInfo(UserInfo user, UserExtraInfo userExtra) throws ParseException {
		VOUserInfo vo = new VOUserInfo();
		vo.setId(user.getId());
		vo.setMobile(user.getMobile());
		vo.setPassword(user.getPassword());
		vo.setNick_name(user.getNickName());
		if(!StringUtils.isEmpty(user.getUserImg())) {
			//第三方登录,不需要加头
			if(user.getUserImg().contains("http")) {
				vo.setUser_img(user.getUserImg());
			} else {
				String path = ConfigProUtils.get("file_path");
				vo.setUser_img(path + user.getUserImg());
			}
		}
		vo.setReg_time(TimeUtils.converStringDate(user.getRegTime(), "yyyy/MM/dd HH:mm:ss"));
		//年龄
		vo.setAge(userExtra.getAge()!=null?userExtra.getAge():0);
		if(user.getProvince()!=null) {
			vo.setProvince(user.getProvince());
			AddrProvince pro = new AddrProvince();
			pro.setId(user.getProvince());
			vo.setProvice_name(userService.getProvinceInfoByCondition(pro).get(0).getProname());
		}
		if(user.getCity()!=null) {
			vo.setCity(user.getCity());
			AddrCity city = new AddrCity();
			city.setId(user.getCity());
			vo.setCity_name(userService.getCityInfoByCondition(city).get(0).getCityname());
		}
		if(user.getCountry()!=null) {
			vo.setCountry(user.getCountry());
			AddrCountry country = new AddrCountry();
			country.setId(user.getCountry());
			vo.setCountry_name(userService.getCountryInfoByCondition(country).get(0).getCountry());
		}
		vo.setStatus(user.getStatus());
		vo.setSwitch_push_msg(user.getIsSwitchPushMsg());
		if(user.getExpectedDateOfConfinement() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int[] data = DateUtil.getPregnantWeek(user.getExpectedDateOfConfinement(), new Date());
			vo.setExpected_week(data[0]);
			vo.setExpected_days(data[1]);
			vo.setExpected_confinement(sdf.format(user.getExpectedDateOfConfinement()));
		}
		vo.setRealname(userExtra.getRealName());
		vo.setHeight(userExtra.getHeight()!=null?userExtra.getHeight():"");
		vo.setWeight(userExtra.getWeight()!=null?userExtra.getWeight():0);
		vo.setLastPeriod(null!=userExtra.getLastPeriod()?new SimpleDateFormat("yyyy-MM-dd").format(userExtra.getLastPeriod()):"");
		//获取用户的accessKey
		vo.setAccessKey(authKeyService.getKeyByUserId(user.getId()));
		vo.setIdentification(userExtra.getIdentification());
		vo.setContactPhone(userExtra.getContactPhone());
		if(userExtra.getCommonHospital()!=null && userExtra.getCommonHospital()!=0) {
			vo.setHospitalName(hospitalService.getHospitalInfoById(userExtra.getCommonHospital()).getName());
		}
		vo.setHospitalId(userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0);
		vo.setCurrentIdentity(userExtra.getCurrentIdentity()!=null?userExtra.getCurrentIdentity():0);
		if(userExtra.getBabyBirthday()!=null) {
			vo.setBaby_birthday(TimeUtils.converStringDate(userExtra.getBabyBirthday(), "yyyy-MM-dd"));
		}
		vo.setBaby_sex(userExtra.getBabySex());
		if(user.getExpectedDateOfConfinement() != null && userExtra.getCurrentIdentity() == 0) {
			//怀孕状态
			vo.setCurrentIdentity(0);
			// 计算预产期,280天,40周
			Date expDate = user.getExpectedDateOfConfinement();
			if (expDate != null) {
				int[] expInfo = DateUtil.getPregnantWeek(expDate, new Date());
				vo.setExpected_week(expInfo[0]);
				vo.setExpected_day(expInfo[1]);
				vo.setExpected_days(expInfo[2]);
				vo.setExpected_confinement(TimeUtils.getDateFormat(expDate));
			} else {
				vo.setExpected_confinement("");
			}
		} else if (userExtra.getBabyBirthday() != null && userExtra.getCurrentIdentity() == 1) {
			/** 已有宝宝 **/
			vo.setCurrentIdentity(1);
			Date babyBirth = userExtra.getBabyBirthday();
			int[] baby = IntervalUtils.getBabyAgeAndMonth(babyBirth);
			vo.setExpected_week(baby[0]);
			vo.setExpected_day(baby[1]);
			vo.setExpected_days(0);
		} else if(userExtra.getCurrentIdentity() == 2) {
			vo.setExpected_week(-1);
			vo.setExpected_day(-1);
		}
		if (userExtra.getCurrentIdentity() == 1) {// 已怀孕
			if (userExtra.getBabyBirthday() != null) {
				vo.setBaby_birthday(TimeUtils.converStringDate(userExtra.getBabyBirthday(), "yyyy-MM-dd"));
			}
			vo.setBaby_sex(userExtra.getBabySex());
		}
		if(null != userExtra.getBirthday()){/**处理用户生日*/
			Date birthday = userExtra.getBirthday();
			vo.setBirthday(TimeUtils.converStringDate(birthday, "yyyy-MM-dd"));
		}
		//用户保健号
		if(userExtra.getCommonHospital() != null && userExtra.getCommonHospital() != 0){
			UserHealthNumber healthNumber = userHealthNumberService.findUserHealthNumber(user.getId(), userExtra.getCommonHospital());
			if(healthNumber != null){
				vo.setHealthCardId(healthNumber.getHealthNum());
			}else{
				vo.setHealthCardId("");
			}
		}
		return vo;
	}
	
	/**
	 * 完善用户信息
	 * @param voUserInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "completeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "v4.0完善用户基本信息", httpMethod = "POST", notes = "完善用户基本信息")
	public ResultMsg completeUserInfo(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody UserInfoVo voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			//真实姓名
			if(StringUtils.isNotEmpty(voUserInfo.getReal_name())){
				extraInfo.setRealName(voUserInfo.getReal_name());
			}
			//用户年龄
			if(StringUtils.isNotEmpty(voUserInfo.getAge()+"") && StringUtils.isNumeric(voUserInfo.getAge()+"")){
				extraInfo.setAge(voUserInfo.getAge());
			}
			//关联医院
			if(voUserInfo.getMobile_type() == 0) {
				String version = voUserInfo.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(voUserInfo.getHospital_id()+"")){
						if(voUserInfo.getHospital_id()==0){
							extraInfo.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
						}else{
							extraInfo.setCommonHospital(voUserInfo.getHospital_id());
						}
					}
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(voUserInfo.getMom_birthday())){
				String birthday = voUserInfo.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					extraInfo.setBirthday(sdfs.parse(birthday));
				}else{
					extraInfo.setBirthday(null);
				}
			}
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getCurrent_identity()+"") || voUserInfo.getCurrent_identity()<0 || voUserInfo.getCurrent_identity()>1){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空！");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getCurrent_identity());
			if(voUserInfo.getCurrent_identity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getExpect_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
						String pre_date = voUserInfo.getExpect_date();
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getCurrent_identity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				
				//APP端用户输入原定预产期
				if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getExpect_date()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getExpect_date());
					extraInfo.setLastPeriod(lastPeriod);
				}else if(userInfo.getExpectedDateOfConfinement() == null){
					//已有宝宝时，预产期设置为宝宝生日那一天
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getBaby_birthday()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getBaby_birthday());
					extraInfo.setLastPeriod(lastPeriod);
				}
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			
			//手机类型
			if(StringUtils.isNotEmpty(voUserInfo.getMobile_type()+"")){
				extraInfo.setMobileType((int)voUserInfo.getMobile_type());
			}
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			userHealthNumberService.doChangeUserState(userInfo, extraInfo);
			//调用health接口
			if (voUserInfo.getCurrent_identity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//修改成功以后返回用户基本信息
//			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = userHealthNumberService.getVOUserInfo(userInfo,"v4.0");
			if(voUserInfo.getMobile_type() == 0) {
				String version = voUserInfo.getVersion();
				String vNum = version.substring(version.lastIndexOf("Build")+5, version.length());
				if(Integer.parseInt(vNum)>1055) {
					//关联医院
					if(StringUtils.isNotEmpty(voUserInfo.getHospital_id()+"")){
						if(voUserInfo.getHospital_id()==0){
							extraInfo.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
							voUserInfo.setHospital_id(ANGELSOUND_HOSPITAL_ID);
						}else{
							extraInfo.setCommonHospital(voUserInfo.getHospital_id());
						}
					}
					//绑定医院流水
					BindHospitalLog log = new BindHospitalLog();
					log.setUserId((long)userInfo.getId());
					log.setMobileIp(WebRequestUtils.getIpAddr(request));
					log.setLng(voUserInfo.getLng());
					log.setLat(voUserInfo.getLat());
					log.setMobileMac(voUserInfo.getDevice_id());
					log.setVersionName(voUserInfo.getVersion());
					log.setMobileType((int)voUserInfo.getMobile_type());
					log.setFirstBinding(voUserInfo.getFirstBind());
					//添加绑定医院流水记录
					bindHospitalLogService.addBindHospitalLog(log, extraInfo.getCommonHospital()!=null?extraInfo.getCommonHospital():0, voUserInfo.getHospital_id());
				}
			}
//			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "完善用户基本信息成功！", voUserInfo2);
		} catch (Exception e) {
			logger.error("updateUserInfo():"+e.getMessage());
			e.printStackTrace();
			return new ResultMsg(Const.FAILED, "完善用户基本信息失败！");
		}
	}
	
	/**
	 * 帮助与反馈中的账号与安全h5页面
	 * @return
	 */
	@RequestMapping("showAccountSafe")
	public ModelAndView showAccountSafe(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("accountSafe");
		return mv;
	}
	
	/**
	 * 帮助与反馈中的功能与服务h5页面
	 * @return
	 */
	@RequestMapping("showFunctionService")
	public ModelAndView showFunctionService(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("functionService");
		return mv;
	}
	
	/**
	 * 帮助与反馈中的其他常见问题h5页面
	 * @return
	 */
	@RequestMapping("showOtherQuestion")
	public ModelAndView showOtherQuestion(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("otherQuestions");
		return mv;
	}
	
	/**
	 * 用户协议
	 * @version v.1.0
	 * @createTime 2017年4月20日,上午10:50:06
	 * @updateTime 2017年4月20日,上午10:50:06
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping("userAgreement")
	public ModelAndView userAgreement(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user_agreement");
		return mv;
	}
	
	/**
	 * 义乌妇幼用户协议
	 * @version v.1.0
	 * @createTime 2017年7月24日,下午6:02:49
	 * @updateTime 2017年7月24日,下午6:02:49
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping("yiwuAgreement")
	public ModelAndView yiwuUserAgreement(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("yiwu_agreements");
		return mv;
	}
	
	/**
	 * v4.1用户注册
	 * @version 1.0
	 * @createTime 2016-12-7,下午3:08:21
	 * @updateTime 2016-12-7,下午3:08:21
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="v4.1/registerUser", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="4.1版本 用户注册", httpMethod="POST", notes="4.1版本 用户注册")
	public ResultMsg registerUserV41(@ApiParam(name="param", value="用户注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请先输入手机号");
			}
			if(param.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(param.getMobile())!=null) {
				return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
			}
			if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "请设置密码");
			}
			//怀孕中
			if(param.getCurrent_identity()==0) { 
				if(StringUtils.isEmpty(param.getExpect_date()) && StringUtils.isEmpty(param.getLast_period())) {
					return new ResultMsg(Const.FAILED, "请设置预产期或者末次月经");
				}
			} else if(param.getCurrent_identity()==1) {
				if(StringUtils.isEmpty(param.getExpect_date())) {
					return new ResultMsg(Const.FAILED, "请提供原定的预产期");
				}
				if(StringUtils.isEmpty(param.getBaby_birthday())) {
					return new ResultMsg(Const.FAILED, "请提供宝宝生日");
				}
			} else if(param.getCurrent_identity()==2) { //备孕期
				if(StringUtils.isEmpty(param.getReal_name())) {
					return new ResultMsg(Const.FAILED, "请输入真实姓名");
				}
				if(StringUtils.isEmpty(param.getMom_birthday())) {
					return new ResultMsg(Const.FAILED, "请选择您的出生日期");
				}
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(0);
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):param.getNick_name());
			user.setRegisterChannel(param.getRegisterChannel());
			if(param.getRegisterChannel() == 1) { //支付宝生活号
				user.setAlipayOpenId(param.getOpen_id());
			} else if(param.getRegisterChannel() == 2) { //微信公众号
				user.setWxOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//关联医院
			if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
				if(param.getHospital_id()==0){
					userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					userExtra.setCommonHospital(param.getHospital_id());
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			//用户拓展数据
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
			VOUserInfo vo = getVOUserInfo(userInfo, extraInfo);
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(userInfo.getId(), babyBirthday, babySex);
			}
			//医院id为0，默认绑定天使医院
			if(param.getHospital_id() == 0){
				param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)userInfo.getId());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(param.getLng());
			log.setLat(param.getLat());
			log.setMobileMac(param.getDevice_id());
			log.setVersionName(param.getVersion());
			log.setMobileType(param.getMobile_type());
			log.setFirstBinding(param.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
			return new ResultMsg(Const.SUCCESS, "注册成功！", vo);
		} catch (Exception e) {
			logger.error("registerUser()", e);
			return new ResultMsg(Const.FAILED, "注册失败！");
		}
	}
	
	/**
	 * v4.1.5用户注册
	 * @version 1.0
	 * @createTime 2016-12-7,下午3:08:21
	 * @updateTime 2016-12-7,下午3:08:21
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="v4.1.5/registerUser", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="4.1.5版本 用户注册", httpMethod="POST", notes="4.1.5版本 用户注册")
	public ResultMsg registerUserV415(@ApiParam(name="param", value="用户注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请先输入手机号");
			}
			if(param.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(param.getMobile())!=null) {
				return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
			}
			if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "请设置密码");
			}
			//怀孕中
			if(param.getCurrent_identity()==0) { 
				if(StringUtils.isEmpty(param.getExpect_date()) && StringUtils.isEmpty(param.getLast_period())) {
					return new ResultMsg(Const.FAILED, "请设置预产期或者末次月经");
				}
			} else if(param.getCurrent_identity()==1) {
				if(StringUtils.isEmpty(param.getExpect_date())) {
					return new ResultMsg(Const.FAILED, "请提供原定的预产期");
				}
				if(StringUtils.isEmpty(param.getBaby_birthday())) {
					return new ResultMsg(Const.FAILED, "请提供宝宝生日");
				}
			} else if(param.getCurrent_identity()==2) { //备孕期
				if(StringUtils.isEmpty(param.getReal_name())) {
					return new ResultMsg(Const.FAILED, "请输入真实姓名");
				}
				if(StringUtils.isEmpty(param.getMom_birthday())) {
					return new ResultMsg(Const.FAILED, "请选择您的出生日期");
				}
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(0);
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):param.getNick_name());
			user.setRegisterChannel(param.getRegisterChannel());
			if(param.getRegisterChannel() == 1) { //支付宝生活号
				user.setAlipayOpenId(param.getOpen_id());
			} else if(param.getRegisterChannel() == 2) { //微信公众号
				user.setWxOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//关联医院
			if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
				if(param.getHospital_id()==0){
					userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					userExtra.setCommonHospital(param.getHospital_id());
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			//用户拓展数据
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
//			VOUserInfo vo = getVOUserInfo(userInfo, extraInfo);
			VOUserInfo vo = userHealthNumberService.getVOUserInfo(userInfo, "v4.1.5");
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(userInfo.getId(), babyBirthday, babySex);
			}
			//医院id为0，默认绑定天使医院
			if(param.getHospital_id() == 0){
				param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)userInfo.getId());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(param.getLng());
			log.setLat(param.getLat());
			log.setMobileMac(param.getDevice_id());
			log.setVersionName(param.getVersion());
			log.setMobileType(param.getMobile_type());
			log.setFirstBinding(param.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
			return new ResultMsg(Const.SUCCESS, "注册成功！", vo);
		} catch (Exception e) {
			logger.error("registerUser()", e);
			return new ResultMsg(Const.FAILED, "注册失败！");
		}
	}
	
	/**
	 * v4.1第三方注册
	 * @version v.1.0
	 * @createTime 2017年4月13日,上午11:40:24
	 * @updateTime 2017年4月13日,上午11:40:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="v4.1/thirdPartyRegister", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="4.1版本 第三方注册", httpMethod="POST", notes="4.1版本 第三方注册")
	public ResultMsg thirdPartyRegisterV41(@ApiParam(name="param", value="第三方注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getOpen_id())) {
				return new ResultMsg(Const.FAILED, "openId不能为空");
			}
			//查询字段
			String field = "";
			if(param.getUser_type() == 1) { //新浪
				field = "sina_open_id";
			} else if(param.getUser_type() == 2) { //QQ
				field = "qq_open_id";
			} else if(param.getUser_type() == 3) { //微信
				field = "weixin_open_id";
			} else {
				return new ResultMsg(Const.FAILED, "非法用户类型");
			}
			//通过openId查询用户信息
			UserInfo thirdUserInfo = userService.getUserInfoBytypeOpenId(field, param.getOpen_id(), param.getUser_type());
			if(null != thirdUserInfo) {
				return new ResultMsg(Const.FAILED, "该第三方帐号已注册过，请返回登录");
			}
			if(StringUtils.isEmpty(param.getNick_name())) {
				return new ResultMsg(Const.FAILED, "昵称不能为空");
			}
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入手机号码");
			}
			if(!Util.isMobiPhoneNum(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "您输入的手机号码格式不正确，请重新输入");
			}
			/*if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "密码不能为空");
			}*/
			//怀孕中
			if(param.getCurrent_identity()==0) { 
				if(StringUtils.isEmpty(param.getExpect_date()) && StringUtils.isEmpty(param.getLast_period())) {
					return new ResultMsg(Const.FAILED, "请设置预产期或者末次月经");
				}
			} else if(param.getCurrent_identity()==1) {
				if(StringUtils.isEmpty(param.getExpect_date())) {
					return new ResultMsg(Const.FAILED, "请提供原定的预产期");
				}
				if(StringUtils.isEmpty(param.getBaby_birthday())) {
					return new ResultMsg(Const.FAILED, "请提供宝宝生日");
				}
			} else if(param.getCurrent_identity()==2) { //备孕期
				if(StringUtils.isEmpty(param.getReal_name())) {
					return new ResultMsg(Const.FAILED, "请输入真实姓名");
				}
				if(StringUtils.isEmpty(param.getMom_birthday())) {
					return new ResultMsg(Const.FAILED, "请选择您的出生日期");
				}
			}
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			if(null != userInfo) {
				return new ResultMsg(Const.FAILED, "该手机号已注册过，请先合并");
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//应用版本
			//String version = param.getVersion();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
//			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(param.getUser_type());
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):EmojiFilterUtil.filter(param.getNick_name()));
			user.setOpenId(param.getOpen_id());
			if(param.getUser_type() == 1) { //新浪
				user.setSinaOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 2) { //QQ
				user.setQqOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 3) { //微信
				user.setWeixinOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//是否是第三方登录
			if(param.getUser_type()>=1 && param.getUser_type()<=3) {
				userExtra.setIsChinaUser(1);
			} else {
				userExtra.setIsChinaUser(0);
			}
			//关联医院
			if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
				if(param.getHospital_id()==0){
					userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					userExtra.setCommonHospital(param.getHospital_id());
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			VOUserInfo vo = getVOUserInfo(u, userExtra);
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(u.getId(), babyBirthday, babySex);
			}
			//医院id为0，默认绑定天使医院
			if(param.getHospital_id() == 0){
				param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)u.getId());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(param.getLng());
			log.setLat(param.getLat());
			log.setMobileMac(param.getDevice_id());
			log.setVersionName(param.getVersion());
			log.setMobileType(param.getMobile_type());
			log.setFirstBinding(param.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
			return new ResultMsg(Const.SUCCESS, "第三方登录成功", vo);
		} catch (Exception e) {
			logger.error("thirdPartyRegister()", e);
			return new ResultMsg(Const.FAILED, "第三方注册失败");
		}
	}
	
	/**
	 * v4.1.5第三方注册
	 * @version v.1.0
	 * @createTime 2017年4月13日,上午11:40:24
	 * @updateTime 2017年4月13日,上午11:40:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="v4.1.5/thirdPartyRegister", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="4.1.5版本 第三方注册", httpMethod="POST", notes="4.1.5版本 第三方注册")
	public ResultMsg thirdPartyRegisterV415(@ApiParam(name="param", value="第三方注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getOpen_id())) {
				return new ResultMsg(Const.FAILED, "openId不能为空");
			}
			//查询字段
			String field = "";
			if(param.getUser_type() == 1) { //新浪
				field = "sina_open_id";
			} else if(param.getUser_type() == 2) { //QQ
				field = "qq_open_id";
			} else if(param.getUser_type() == 3) { //微信
				field = "weixin_open_id";
			} else {
				return new ResultMsg(Const.FAILED, "非法用户类型");
			}
			//通过openId查询用户信息
			UserInfo thirdUserInfo = userService.getUserInfoBytypeOpenId(field, param.getOpen_id(), param.getUser_type());
			if(null != thirdUserInfo) {
				return new ResultMsg(Const.FAILED, "该第三方帐号已注册过，请返回登录");
			}
			if(StringUtils.isEmpty(param.getNick_name())) {
				return new ResultMsg(Const.FAILED, "昵称不能为空");
			}
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请输入手机号码");
			}
			if(!Util.isMobiPhoneNum(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "您输入的手机号码格式不正确，请重新输入");
			}
			/*if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "密码不能为空");
			}*/
			//怀孕中
			if(param.getCurrent_identity()==0) { 
				if(StringUtils.isEmpty(param.getExpect_date()) && StringUtils.isEmpty(param.getLast_period())) {
					return new ResultMsg(Const.FAILED, "请设置预产期或者末次月经");
				}
			} else if(param.getCurrent_identity()==1) {
				if(StringUtils.isEmpty(param.getExpect_date())) {
					return new ResultMsg(Const.FAILED, "请提供原定的预产期");
				}
				if(StringUtils.isEmpty(param.getBaby_birthday())) {
					return new ResultMsg(Const.FAILED, "请提供宝宝生日");
				}
			} else if(param.getCurrent_identity()==2) { //备孕期
				if(StringUtils.isEmpty(param.getReal_name())) {
					return new ResultMsg(Const.FAILED, "请输入真实姓名");
				}
				if(StringUtils.isEmpty(param.getMom_birthday())) {
					return new ResultMsg(Const.FAILED, "请选择您的出生日期");
				}
			}
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			if(null != userInfo) {
				return new ResultMsg(Const.FAILED, "该手机号已注册过，请先合并");
			}
			//用户
			UserInfo user = new UserInfo();
			//预产期
			String expectDate = param.getExpect_date();
			//当前身份(0:已怀孕，1:辣妈)
			int currentIdentity = param.getCurrent_identity(); 
			//末次月经 如果不知道预产期则根据末次月经时间计算出来
			String lastPeriod = param.getLast_period();
			//宝宝生日
			String babyBirthday = param.getBaby_birthday();
			//宝宝性别(0:男、1:女)
			int babySex = param.getBaby_sex();
			//应用版本
			//String version = param.getVersion();
			//赋值
			user.setMobile(param.getMobile());
			if(!StringUtils.isEmpty(expectDate)) {
				user.setExpectedDateOfConfinement(TimeUtils.convertToDate(expectDate));
			}
			user.setStatus(1);
			user.setRegTime(new Date());
//			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(param.getUser_type());
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name())?"天使用户"+RandomStringUtils.random(8, false, true):EmojiFilterUtil.filter(param.getNick_name()));
			user.setOpenId(param.getOpen_id());
			if(param.getUser_type() == 1) { //新浪
				user.setSinaOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 2) { //QQ
				user.setQqOpenId(param.getOpen_id());
			} else if(param.getUser_type() == 3) { //微信
				user.setWeixinOpenId(param.getOpen_id());
			}
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setAge(param.getAge());
			userExtra.setRealName(param.getReal_name());
			userExtra.setContactPhone(param.getMobile());
			if(!StringUtils.isEmpty(babyBirthday)) {
				userExtra.setBabyBirthday(TimeUtils.convertToDate(babyBirthday));
			}
			userExtra.setBabySex((byte)babySex);
			userExtra.setCurrentIdentity((byte)currentIdentity);
			if(!StringUtils.isEmpty(lastPeriod)) {
				userExtra.setLastPeriod(TimeUtils.convertToDate(lastPeriod));
			}
			userExtra.setCheckStatus(0);
			//是否是第三方登录
			if(param.getUser_type()>=1 && param.getUser_type()<=3) {
				userExtra.setIsChinaUser(1);
			} else {
				userExtra.setIsChinaUser(0);
			}
			//关联医院
			if(StringUtils.isNotEmpty(param.getHospital_id()+"")){
				if(param.getHospital_id()==0){
					userExtra.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					userExtra.setCommonHospital(param.getHospital_id());
				}
			}
			//宝妈生日
			if(StringUtils.isNotEmpty(param.getMom_birthday())){
				String birthday = param.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					userExtra.setBirthday(sdfs.parse(birthday));
				}else{
					userExtra.setBirthday(null);
				}
			}
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
//			VOUserInfo vo = getVOUserInfo(u, userExtra);
			VOUserInfo vo = userHealthNumberService.getVOUserInfo(u, "v4.1.5");
			//如果为已有宝宝状态，调用用户分娩结局信息接口
			if(currentIdentity == 1) {
				healthBaseService.setUserChildbirthEnd(u.getId(), babyBirthday, babySex);
			}
			//医院id为0，默认绑定天使医院
			if(param.getHospital_id() == 0){
				param.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)u.getId());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(param.getLng());
			log.setLat(param.getLat());
			log.setMobileMac(param.getDevice_id());
			log.setVersionName(param.getVersion());
			log.setMobileType(param.getMobile_type());
			log.setFirstBinding(param.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, param.getHospital_id());
			return new ResultMsg(Const.SUCCESS, "第三方登录成功", vo);
		} catch (Exception e) {
			logger.error("thirdPartyRegister()", e);
			return new ResultMsg(Const.FAILED, "第三方注册失败");
		}
	}
	
	/**
	 * v4.1.5义乌妇幼用户注册
	 * @version 1.0
	 * @createTime 2017-07-12,下午17:29:21
	 * @updateTime 2017-07-12,下午17:29:21
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="v1.01/registerUser", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="v1.01版本义乌妇幼 用户注册", httpMethod="POST", notes="v1.01版本义乌妇幼 用户注册")
	public ResultMsg registerUserV101(@ApiParam(name="param", value="用户注册参数", required = true) @RequestBody UserParam param, HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(param.getMobile())) {
				return new ResultMsg(Const.FAILED, "请先输入手机号");
			}
			if(param.getMobile().length()<11) {
				return new ResultMsg(Const.FAILED, "请输入正确的手机号");
			}
			if(userService.findUserInfoByMobile(param.getMobile())!=null) {
				return new ResultMsg(Const.FAILED, "该手机号已注册，请直接登录");
			}
			if(StringUtils.isEmpty(param.getPassword())) {
				return new ResultMsg(Const.FAILED, "请设置密码");
			}
			if(StringUtils.isEmpty(param.getCode())) {
				return new ResultMsg(Const.FAILED, "请先输入验证码");
			}
			//通过手机号码查询验证码
			UserVerifiedCode userVerifiedCode = userService.findVerifiedCodeByMobileSQL(param.getMobile());
			if(userVerifiedCode == null) {
				return new ResultMsg(Const.FAILED, "该验证码已失效，请获取新的验证码");
			}
			if(!userVerifiedCode.getCode().equals(param.getCode())) {
				return new ResultMsg(Const.FAILED, "验证码错误，请重新输入");
			}
			//医院信息
			HospitalInfo hospital = hospitalService.getHospitalInfoById(param.getHospital_id());
			if(hospital == null) {
				return new ResultMsg(Const.FAILED, "医院ID不正确");
			}
			String name = "义乌妇幼"+RandomStringUtils.random(8, false, true);
			//用户
			UserInfo user = new UserInfo();
			user.setMobile(param.getMobile());
			user.setStatus(1);
			user.setRegTime(new Date());
			user.setPassword(MD5EncryptUtils.getMd5Value(param.getPassword()));
			user.setIsSwitchPushMsg(1);
			user.setUserType(0);
			user.setGold(0);
			user.setNickName(StringUtils.isEmpty(param.getNick_name()) ? name : param.getNick_name());
			//义乌妇幼注册
			user.setRegisterChannel(3);
			//用户拓展表
			UserExtraInfo userExtra = new UserExtraInfo();
			userExtra.setLoginIp(WebRequestUtils.getIpAddr(request));
			userExtra.setLoginTime(new Date());
			userExtra.setMobileType(param.getMobile_type());
			userExtra.setContactPhone(param.getMobile());
			userExtra.setCheckStatus(0);
			//255表示当前身份未定义
			userExtra.setCurrentIdentity((byte)255);
			//关联医院
			userExtra.setCommonHospital(param.getHospital_id());
			//添加用户信息
			userService.registerUser(user, userExtra, "");
			//查询用户信息
			UserInfo u = userService.findUserInfoByMobile(param.getMobile());
			//监测im中是否存在该用户
			imAccountsService.add("101", "yh_"+u.getId().toString(), u.getNickName(), u.getUserImg());
			//通过手机号码查询用户信息
			UserInfo userInfo = userService.findUserInfoByMobile(param.getMobile());
			//绑定数据
			VOUserInfo vo = userHealthNumberService.getVOUserInfo(userInfo, "v4.1.5");
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)userInfo.getId());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(param.getLng());
			log.setLat(param.getLat());
			log.setHospitalId((long)param.getHospital_id());
			log.setMobileMac(param.getDevice_id());
			log.setVersionName(param.getVersion());
			log.setMobileType(param.getMobile_type());
			log.setFirstBinding(param.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, param.getHospital_id(), param.getHospital_id());
			return new ResultMsg(Const.SUCCESS, "注册成功", vo);
		} catch (Exception e) {
			logger.error("registerUser()", e);
			return new ResultMsg(Const.FAILED, "注册失败");
		}
	}
	
	/**
	 * v4.1完善用户信息
	 * @param voUserInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "v4.1/completeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "4.1版本 完善用户基本信息", httpMethod = "POST", notes = "4.1版本 完善用户基本信息")
	public ResultMsg completeUserInfoV41(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody UserInfoVo voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			
			//真实姓名
			if(StringUtils.isNotEmpty(voUserInfo.getReal_name())){
				extraInfo.setRealName(voUserInfo.getReal_name());
			}

			//用户年龄
			if(StringUtils.isNotEmpty(voUserInfo.getAge()+"") && StringUtils.isNumeric(voUserInfo.getAge()+"")){
				extraInfo.setAge(voUserInfo.getAge());
			}
			
			//关联医院
			if(StringUtils.isNotEmpty(voUserInfo.getHospital_id()+"")){
				if(voUserInfo.getHospital_id()==0){
					extraInfo.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					extraInfo.setCommonHospital(voUserInfo.getHospital_id());
				}
			}
			
			//宝妈生日
			if(StringUtils.isNotEmpty(voUserInfo.getMom_birthday())){
				String birthday = voUserInfo.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					extraInfo.setBirthday(sdfs.parse(birthday));
				}else{
					extraInfo.setBirthday(null);
				}
			}
			
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getCurrent_identity()+"") || voUserInfo.getCurrent_identity()<0 || voUserInfo.getCurrent_identity()>1){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空！");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getCurrent_identity());
			if(voUserInfo.getCurrent_identity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getExpect_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
						String pre_date = voUserInfo.getExpect_date();
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getCurrent_identity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				
				//APP端用户输入原定预产期
				if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getExpect_date()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getExpect_date());
					extraInfo.setLastPeriod(lastPeriod);
				}else if(userInfo.getExpectedDateOfConfinement() == null){
					//已有宝宝时，预产期设置为宝宝生日那一天
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getBaby_birthday()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getBaby_birthday());
					extraInfo.setLastPeriod(lastPeriod);
				}
				
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			//手机类型
			if(StringUtils.isNotEmpty(voUserInfo.getMobile_type()+"")){
				extraInfo.setMobileType((int)voUserInfo.getMobile_type());
			}
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			userHealthNumberService.doChangeUserState(userInfo, extraInfo);
			//调用health接口
			if (voUserInfo.getCurrent_identity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//医院id为0，默认绑定天使医院
			if(voUserInfo.getHospital_id() == 0){
				voUserInfo.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)voUserInfo.getUser_id());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(voUserInfo.getLng());
			log.setLat(voUserInfo.getLat());
			log.setMobileMac(voUserInfo.getDevice_id());
			log.setVersionName(voUserInfo.getVersion());
			log.setMobileType(Integer.valueOf(voUserInfo.getMobile_type()));
			log.setFirstBinding(voUserInfo.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, extraInfo.getCommonHospital()!=null?extraInfo.getCommonHospital():0, voUserInfo.getHospital_id());
			//修改成功以后返回用户基本信息
//			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = userHealthNumberService.getVOUserInfo(userInfo,"v4.0");
//			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "完善用户基本信息成功！", voUserInfo2);
		} catch (Exception e) {
			logger.error("updateUserInfo():"+e.getMessage());
			e.printStackTrace();
			return new ResultMsg(Const.FAILED, "完善用户基本信息失败！");
		}
	}
	
	/**
	 * v4.1.5完善用户信息
	 * @param voUserInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "v4.1.5/completeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "4.1.5版本 完善用户基本信息", httpMethod = "POST", notes = "4.1.5版本 完善用户基本信息")
	public ResultMsg completeUserInfoV415(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody UserInfoVo voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			
			//真实姓名
			if(StringUtils.isNotEmpty(voUserInfo.getReal_name())){
				extraInfo.setRealName(voUserInfo.getReal_name());
			}

			//用户年龄
			if(StringUtils.isNotEmpty(voUserInfo.getAge()+"") && StringUtils.isNumeric(voUserInfo.getAge()+"")){
				extraInfo.setAge(voUserInfo.getAge());
			}
			
			//关联医院
			if(StringUtils.isNotEmpty(voUserInfo.getHospital_id()+"")){
				if(voUserInfo.getHospital_id()==0){
					extraInfo.setCommonHospital(ANGELSOUND_HOSPITAL_ID);
				}else{
					extraInfo.setCommonHospital(voUserInfo.getHospital_id());
				}
			}
			
			//宝妈生日
			if(StringUtils.isNotEmpty(voUserInfo.getMom_birthday())){
				String birthday = voUserInfo.getMom_birthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					extraInfo.setBirthday(sdfs.parse(birthday));
				}else{
					extraInfo.setBirthday(null);
				}
			}
			
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getCurrent_identity()+"")){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getCurrent_identity());
			if(voUserInfo.getCurrent_identity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getExpect_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
						String pre_date = voUserInfo.getExpect_date();
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getCurrent_identity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				
				//APP端用户输入原定预产期
				if(StringUtils.isNotEmpty(voUserInfo.getExpect_date())){
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getExpect_date()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getExpect_date());
					extraInfo.setLastPeriod(lastPeriod);
				}else if(userInfo.getExpectedDateOfConfinement() == null){
					//已有宝宝时，预产期设置为宝宝生日那一天
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getBaby_birthday()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getBaby_birthday());
					extraInfo.setLastPeriod(lastPeriod);
				}
				
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			//手机类型
			if(StringUtils.isNotEmpty(voUserInfo.getMobile_type()+"")){
				extraInfo.setMobileType((int)voUserInfo.getMobile_type());
			}
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			userHealthNumberService.doChangeUserState(userInfo, extraInfo);
			//调用health接口
			if (voUserInfo.getCurrent_identity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//医院id为0，默认绑定天使医院
			if(voUserInfo.getHospital_id() == 0){
				voUserInfo.setHospital_id(ANGELSOUND_HOSPITAL_ID);
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId((long)voUserInfo.getUser_id());
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(voUserInfo.getLng());
			log.setLat(voUserInfo.getLat());
			log.setMobileMac(voUserInfo.getDevice_id());
			log.setVersionName(voUserInfo.getVersion());
			log.setMobileType(Integer.valueOf(voUserInfo.getMobile_type()));
			log.setFirstBinding(voUserInfo.getFirstBind());
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, extraInfo.getCommonHospital()!=null?extraInfo.getCommonHospital():0, voUserInfo.getHospital_id());
			//修改成功以后返回用户基本信息
//			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = userHealthNumberService.getVOUserInfo(userInfo,"v4.1.5");
//			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "完善用户基本信息成功！", voUserInfo2);
		} catch (Exception e) {
			logger.error("updateUserInfo():");
			e.printStackTrace();
			return new ResultMsg(Const.FAILED, "完善用户基本信息失败！");
		}
	}
}
