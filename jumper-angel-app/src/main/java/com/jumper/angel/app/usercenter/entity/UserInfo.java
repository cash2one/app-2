package com.jumper.angel.app.usercenter.entity;

import java.io.Serializable;
/**
 * 第三方登录用户entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月12日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 463205033972549300L;
	
	//昵称
	private String nick_name;
	
	//第三方openId
	private String open_id;
	
	//用户头像
	private String user_img;
	
	//用户类型  1:新浪  2:QQ 3:微信
	private int user_type;
	
	//验证码
	private String verified_code;
	
	//设备ID
	private String device_id;
	
	//设备ids
	private String device_ids;
	
	//电话号码
	private String mobile;
	
	//密码
	private String password;
	
	//年龄
	private int age;
	
	//宝宝性别 0:男   1:女
	private int baby_sex;
	
	//宝宝生日
	private String baby_birthday;
	
	//怀孕状态 0:怀孕中  1:有宝宝
	private int current_identity;
	
	//预产期
	private String expect_date;
	
	//末次月经
	private String last_period;
	
	//手机类型 0:android 1:ios
	private int mobile_type;
	
	//真实姓名
	private String real_name;
	
	//用户ID
	private int user_id;
	
	//APP版本号
	private String version;

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public String getVerified_code() {
		return verified_code;
	}

	public void setVerified_code(String verified_code) {
		this.verified_code = verified_code;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDevice_ids() {
		return device_ids;
	}

	public void setDevice_ids(String device_ids) {
		this.device_ids = device_ids;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getBaby_sex() {
		return baby_sex;
	}

	public void setBaby_sex(int baby_sex) {
		this.baby_sex = baby_sex;
	}

	public String getBaby_birthday() {
		return baby_birthday;
	}

	public void setBaby_birthday(String baby_birthday) {
		this.baby_birthday = baby_birthday;
	}

	public int getCurrent_identity() {
		return current_identity;
	}

	public void setCurrent_identity(int current_identity) {
		this.current_identity = current_identity;
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

	public int getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(int mobile_type) {
		this.mobile_type = mobile_type;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
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
}
