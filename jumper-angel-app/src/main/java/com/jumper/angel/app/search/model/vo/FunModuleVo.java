package com.jumper.angel.app.search.model.vo;

import java.io.Serializable;

/**
 * 功能模块封装实体
 * @author gyx
 * @time 2016年12月13日
 */
public class FunModuleVo implements Serializable{
	
	private static final long serialVersionUID = -8417400826980425678L;
	
	//功能id
	private Integer funId;
	
	//功能名称
	private String funName;
	
	//描述
	private String description;
	
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

}
