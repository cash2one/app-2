package com.jumper.angel.app.healthymanage.vo;

import java.io.Serializable;

public class VOPregnantHealthyProject implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	/** 项目名称 **/
	private String name;
	/** 状态（0：开启，1关闭） **/
	private int state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
