package com.jumper.angel.app.encyclopedia.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 孕期百科问题列表entity
 * @author gyx
 * @time 2016年12月1日
 */
public class HelperQuestionClass implements Serializable {
	
	private static final long serialVersionUID = -8417400826980425678L;

	private Integer id;
	//类别名称
	private String name;
	//是否显示:0 显示  1 不显示
	private Integer isVisible;
	//添加时间
	private Timestamp addTime;

	public HelperQuestionClass() {
	}

	public HelperQuestionClass(String name) {
		this.name = name;
	}

	public HelperQuestionClass(String name, Integer isVisible,
			Timestamp addTime) {
		this.name = name;
		this.isVisible = isVisible;
		this.addTime = addTime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

}