package com.jumper.angel.app.usercenter.vo;

import java.io.Serializable;
/**
 * 登录参数
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class UserVo implements Serializable {
	
	private static final long serialVersionUID = -7442123637377376838L;
	//手机号码
	private String mobile;
	//密码
	private String password;
	//手机类型  0:android, 1:IOS
	private int mobileType;
	//预产期
	private String expectDate;
	//验证码
	private String verfiedCode;
	//手机序列号
	private String deviceId;
	//openId授权成功返回的id
	private String openId;
	//1:新浪  2:QQ 3:微信
	private int userType;
	//昵称
	private String nickName;
	//头像
	private String userImg;
	//医院ID
	private String hospitalId;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMobileType() {
		return mobileType;
	}

	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}

	public String getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}

	public String getVerfiedCode() {
		return verfiedCode;
	}

	public void setVerfiedCode(String verfiedCode) {
		this.verfiedCode = verfiedCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
}
