package com.jumper.angel.app.usercenter.vo;

import java.io.Serializable;

/**
 * 用户中心基本信息组装类
 * @author gyx
 * @time 2016年12月5日
 */
@SuppressWarnings("serial")
public class VOUserInfo implements Serializable{	
	private int id;
	private String mobile = "";
	private String password = "";	
	private String nick_name = "";
	private String user_img = "";
	private String reg_time = "";
	private int age;
	private int province;
	private int city;
	private int country;
	private int status;
	private int switch_push_msg;
	private String expected_confinement = "";
	private String provice_name = "";
	private String country_name = "";
	private String city_name = "";
	private String realname = "";
	private String height = "";
    private double weight;
    private String accessKey;
    /** 身份证号  **/
	private String identification = "";
	/** 个人信息中的手机号码 **/
	private String contactPhone = "";	
    /** 用户金币(3.0新增) **/
//    private int gold;
    /** 常用医院名称 **/
    private String hospitalName = "";
    /** 常用医院ID **/
    private int hospitalId = 0;
    /** 当前身份 **/
    private int currentIdentity;
    /** 宝宝生日 **/
    private String baby_birthday = "";
    /** 宝宝性别 **/
    private Byte baby_sex = 0;
	/** 孕期周或者宝宝多少岁 **/
	private int expected_week;
	/** 孕天或者宝宝多少月 **/
	private int expected_day;
	/** 孕期总天数 **/
	private int expected_days;
	/** 是否第一次登录 **/
	private boolean isFirstLogin = false;
	/** 个人中心审核状态*/
	private int checkstatus ;
	/**个人绑定医院*/
	private String bindHospitalName ;
	/**用户生日*/
	private String birthday = "";
	/**用户末次月经*/
	private String lastPeriod = "";
	/**用户保健号*/
	private String healthCardId = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getUser_img() {
		return user_img;
	}
	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public String getProvice_name() {
		return provice_name;
	}
	public void setProvice_name(String provice_name) {
		this.provice_name = provice_name;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSwitch_push_msg() {
		return switch_push_msg;
	}
	public void setSwitch_push_msg(int switch_push_msg) {
		this.switch_push_msg = switch_push_msg;
	}
	public String getExpected_confinement() {
		return expected_confinement;
	}
	public void setExpected_confinement(String expected_confinement) {
		this.expected_confinement = expected_confinement;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/*public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}*/
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public int getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public int getCurrentIdentity() {
		return currentIdentity;
	}
	public void setCurrentIdentity(int currentIdentity) {
		this.currentIdentity = currentIdentity;
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
	public int getExpected_week() {
		return expected_week;
	}
	public void setExpected_week(int expected_week) {
		this.expected_week = expected_week;
	}
	public int getExpected_day() {
		return expected_day;
	}
	public void setExpected_day(int expected_day) {
		this.expected_day = expected_day;
	}
	public int getExpected_days() {
		return expected_days;
	}
	public void setExpected_days(int expected_days) {
		this.expected_days = expected_days;
	}
	public boolean isFirstLogin() {
		return isFirstLogin;
	}
	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	public int getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(int checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String  getBindHospitalName() {
		return bindHospitalName;
	}
	public void setBindHospitalName(String  bindHospitalName) {
		this.bindHospitalName = bindHospitalName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birhtday) {
		this.birthday = birhtday;
	}
	public String getLastPeriod() {
		return lastPeriod;
	}
	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getHealthCardId() {
		return healthCardId;
	}
	public void setHealthCardId(String healthCardId) {
		this.healthCardId = healthCardId;
	}
	
	
	
}
