package com.jumper.angel.app.encyclopedia.vo;

import java.io.Serializable;
import java.util.List;

public class VOHelperQuestionClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private List<VOHelperQuestionType> typeList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<VOHelperQuestionType> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<VOHelperQuestionType> typeList) {
		this.typeList = typeList;
	}
}
