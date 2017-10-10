package com.jumper.angel.app.bindhospital.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VOCityInfo implements Serializable{	
	//城市id
	private int id;
	//省份id
	private int province_id;
	//城市名称
	private String city_name = "";
	//缩写
	private String abbrev = "";
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	
	
}
