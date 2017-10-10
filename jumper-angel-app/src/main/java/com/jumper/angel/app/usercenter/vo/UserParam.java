package com.jumper.angel.app.usercenter.vo;

import java.io.Serializable;

/**
 * 用户注册参数
 * 
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class UserParam implements Serializable {

	private static final long serialVersionUID = 2641585641861313250L;
	// 手机序列号
	private String device_id;
	// 手机号码
	private String mobile;
	// 密码
	private String password;
	//验证码
	private String code;
	// 年龄
	private int age;
	// 宝宝生日
	private String baby_birthday;
	// 预产期
	private String expect_date;
	// 末次月经
	private String last_period;
	// 姓名
	private String real_name;
	// 手机类型(0：android 1：ios)
	private int mobile_type;
	// 当前身份 0:已怀孕，1:辣妈
	private int current_identity;
	// 宝宝性别
	private int baby_sex;
	//用户ID
	private int user_id;
	// 版本
	private String version;
	// 第三方ID
	private String open_id;
	// 用户类型
	private int user_type;
	// 昵称
	private String nick_name;
	//渠道
	private int registerChannel;
	//医院ID
	private int hospital_id;
	//经度
	private double lng;
	//维度
	private double lat;
	//是否首次绑定(0：非首次  1：首次)
	private int firstBind;
	//用户生日
	private String mom_birthday;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBaby_birthday() {
		return baby_birthday;
	}

	public void setBaby_birthday(String baby_birthday) {
		this.baby_birthday = baby_birthday;
	}

	public String getExpect_date() {
		return expect_date;
	}

	public void setExpect_date(String expect_date) {
		this.expect_date = expect_date;
	}

	public String getLast_period() {
		return last_period;
	}

	public void setLast_period(String last_period) {
		this.last_period = last_period;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public int getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(int mobile_type) {
		this.mobile_type = mobile_type;
	}

	public int getCurrent_identity() {
		return current_identity;
	}

	public void setCurrent_identity(int current_identity) {
		this.current_identity = current_identity;
	}

	public int getBaby_sex() {
		return baby_sex;
	}

	public void setBaby_sex(int baby_sex) {
		this.baby_sex = baby_sex;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public int getHospital_id() {
		return hospital_id;
	}

	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getFirstBind() {
		return firstBind;
	}

	public void setFirstBind(int firstBind) {
		this.firstBind = firstBind;
	}

	public int getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(int registerChannel) {
		this.registerChannel = registerChannel;
	}

	public String getMom_birthday() {
		return mom_birthday;
	}

	public void setMom_birthday(String mom_birthday) {
		this.mom_birthday = mom_birthday;
	}
}
