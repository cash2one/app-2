package com.jumper.angel.app.bindhospital.vo;

import java.util.List;

@SuppressWarnings("serial")
public class VOProvinceInfo implements java.io.Serializable{	
	//省份id
	private int id;
	//省份名称
	private String province_name = "";
	//缩写
	private String abbrev = "";
	//省份包含的城市列表
	private List<VOCityInfo> city_list = null;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<VOCityInfo> getCity_list() {
		return city_list;
	}

	public void setCity_list(List<VOCityInfo> city_list) {
		this.city_list = city_list;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	
	
}
