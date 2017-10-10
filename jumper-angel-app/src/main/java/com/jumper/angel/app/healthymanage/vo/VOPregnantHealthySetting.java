package com.jumper.angel.app.healthymanage.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VOPregnantHealthySetting implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 项目名称 **/
	private String projectName="";
	/** 测试数值 **/
	private String recordData="";
	/** 异常消息 **/
	private String abnormalMsg="";
	/** 测试时间 **/
	private String testTime="";
	/** 数据类型1.胎心；2.血氧；3.心率；4.体温；5.体重；6.血压；7.血糖；8.胎动 ；9：尿液；10：心电**/
	private int project = 0;
	/** 测试数据集合 **/
//	private List<VOPregnantHealthyRecord> records = new ArrayList<VOPregnantHealthyRecord>();

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRecordData() {
		return recordData;
	}

	public void setRecordData(String recordData) {
		this.recordData = recordData;
	}

	public String getAbnormalMsg() {
		return abnormalMsg;
	}

	public void setAbnormalMsg(String abnormalMsg) {
		this.abnormalMsg = abnormalMsg;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}

//	public List<VOPregnantHealthyRecord> getRecords() {
//		return records;
//	}
//
//	public void setRecords(List<VOPregnantHealthyRecord> records) {
//		this.records = records;
//	}
}
