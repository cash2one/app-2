package com.jumper.angel.app.usercenter.vo;

import java.io.Serializable;

/**
 * 完善用户基本信息参数封装类
 * @author gyx
 * @time 2016年12月6日
 */
@SuppressWarnings("serial")
public class UserInfoVo implements Serializable{	
	//用户id
	private int user_id;
	//用户真实姓名
	private String real_name;
	//用户年龄
	private int age;
	//当前身份(0：已怀孕，1：辣妈)
	private Byte current_identity;
	//预产期
	private String expect_date;
	//宝宝生日
	private String baby_birthday;
	//宝宝性别(0：女，1：男)
	private Byte baby_sex;
	//末次月经
	private String last_period;
	//手机类型（0:android,1:IOS）
	private Byte mobile_type;
	//关联医院id（非必填）
	private int hospital_id;
	//宝妈生日
	private String mom_birthday;
	// 版本
	private String version;
	//经度
	private double lng;
	//维度
	private double lat;
	// 手机序列号
	private String device_id;
	//是否首次绑定(0：非首次  1：首次)
	private int firstBind;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Byte getCurrent_identity() {
		return current_identity;
	}
	public void setCurrent_identity(Byte current_identity) {
		this.current_identity = current_identity;
	}
	public String getExpect_date() {
		return expect_date;
	}
	public void setExpect_date(String expect_date) {
		this.expect_date = expect_date;
	}
	public String getBaby_birthday() {
		return baby_birthday;
	}
	public void setBaby_birthday(String baby_birthday) {
		this.baby_birthday = baby_birthday;
	}
	public Byte getBaby_sex() {
		return baby_sex;
	}
	public void setBaby_sex(Byte baby_sex) {
		this.baby_sex = baby_sex;
	}
	public String getLast_period() {
		return last_period;
	}
	public void setLast_period(String last_period) {
		this.last_period = last_period;
	}
	public Byte getMobile_type() {
		return mobile_type;
	}
	public void setMobile_type(Byte mobile_type) {
		this.mobile_type = mobile_type;
	}
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getMom_birthday() {
		return mom_birthday;
	}
	public void setMom_birthday(String mom_birthday) {
		this.mom_birthday = mom_birthday;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public int getFirstBind() {
		return firstBind;
	}
	public void setFirstBind(int firstBind) {
		this.firstBind = firstBind;
	}
	
	
	
}
