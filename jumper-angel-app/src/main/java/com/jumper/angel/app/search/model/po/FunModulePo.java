package com.jumper.angel.app.search.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能模块实体
 * @author gyx
 * @time 2016年12月13日
 */
public class FunModulePo implements Serializable{
	
	private static final long serialVersionUID = -8417400826980425678L;
	//主键
	private long id;
	
	//功能id
	private Integer funId;
	
	//功能名称
	private String funName;
	
	//描述
	private String description;
	
	//功能使能
	private Integer isEnabled;
	
	//添加时间
	private Date addTime;
	
	//模块开通对应的字段
	private String openModule;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getFunId() {
		return funId;
	}

	public void setFunId(Integer funId) {
		this.funId = funId;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getOpenModule() {
		return openModule;
	}

	public void setOpenModule(String openModule) {
		this.openModule = openModule;
	}
	
	
	
}
