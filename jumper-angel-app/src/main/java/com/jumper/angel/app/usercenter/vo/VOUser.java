package com.jumper.angel.app.usercenter.vo;

import java.io.Serializable;

/**
 * 修改用户基本信息参数封装类
 * @author gyx
 * @time 2016年12月6日
 */
@SuppressWarnings("serial")
public class VOUser implements Serializable{	
	//用户id
	private int user_id;
	//用户昵称
	private String nick_name;
	//用户真实姓名
	private String real_name;
	//用户年龄
	private int age;
	//省份
	private int province;
	//城市
	private int city;
	//身份证
	private String id_card;
	//用户身高
	private String height;
	//用户孕前体重
	private double weight;
	//当前身份(0：已怀孕，1：辣妈)
	private Byte identity;
	//预产期
	private String pre_date;
	//宝宝生日
	private String baby_birthday;
	//用户生日
	private String birthday;
	//宝宝性别(0：女，1：男)
	private Byte baby_sex;
	//末次月经
	private String last_period;
	//保健号
	private String healthNum;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
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
	public Byte getIdentity() {
		return identity;
	}
	public void setIdentity(Byte identity) {
		this.identity = identity;
	}
	public String getPre_date() {
		return pre_date;
	}
	public void setPre_date(String pre_date) {
		this.pre_date = pre_date;
	}
	public String getBaby_birthday() {
		return baby_birthday;
	}
	public void setBaby_birthday(String baby_birthday) {
		this.baby_birthday = baby_birthday;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	public String getHealthNum() {
		return healthNum;
	}
	public void setHealthNum(String healthNum) {
		this.healthNum = healthNum;
	}
	
}
