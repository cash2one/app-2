package com.jumper.angel.app.healthymanage.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VOPregnantHealthyIndex implements Serializable{

	private String userImg = "";
	private int age = 0;
	private String expectedDate = "";
	private int examinationNumbers = 0;
	private String babyBirthday = "";
	private List<VOPregnantHealthySetting> settings = new ArrayList<VOPregnantHealthySetting>();
	private int id = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public List<VOPregnantHealthySetting> getSettings() {
		return settings;
	}
	public void setSettings(List<VOPregnantHealthySetting> settings) {
		this.settings = settings;
	}
	public int getExaminationNumbers() {
		return examinationNumbers;
	}
	public void setExaminationNumbers(int examinationNumbers) {
		this.examinationNumbers = examinationNumbers;
	}
	public String getBabyBirthday() {
		return babyBirthday;
	}
	public void setBabyBirthday(String babyBirthday) {
		this.babyBirthday = babyBirthday;
	}
}
