package com.jumper.angel.app.home.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * PregnantAntenatalExaminationInfo entity.
 * 怀孕的产检信息entity
 */

public class PregnantAntenatalExaminationInfo implements Serializable {

	private static final long serialVersionUID = -2703212412444085694L;
	
	private Integer id;
	//产检次数
	private Integer examinationNumbers;
	//开始周
	private Integer startWeek;
	//结束周
	private Integer endWeek;
	//产检的温馨提醒
	private String remind;
	//添加时间
	private Timestamp addTime;
	//提醒周
	private Integer remindWeek;	


	/** default constructor */
	public PregnantAntenatalExaminationInfo() {
	}

	/** full constructor */
	public PregnantAntenatalExaminationInfo(Integer examinationNumbers,
			Integer startWeek, String remind, Timestamp addTime) {
		this.examinationNumbers = examinationNumbers;
		this.startWeek = startWeek;
		this.remind = remind;
		this.addTime = addTime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExaminationNumbers() {
		return this.examinationNumbers;
	}

	public void setExaminationNumbers(Integer examinationNumbers) {
		this.examinationNumbers = examinationNumbers;
	}

	public Integer getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	public Integer getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	public String getRemind() {
		return this.remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Integer getRemindWeek() {
		return remindWeek;
	}

	public void setRemindWeek(Integer remindWeek) {
		this.remindWeek = remindWeek;
	}

}