package com.jumper.angel.app.healthymanage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户监测首页需要展示的项目类型
 * @author gyx
 * @time 2016年12月9日
 */
@SuppressWarnings("serial")
public class PregnantHealthySetting implements Serializable{
  
    private Integer id;  //主键id
    private Integer userId;  //用户id
    private Integer project; //项目
    private Integer state;  //监控状态(0：开启，1：关闭)
    private Date addTime; //添加时间
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
		
	@Override
	public String toString() {
		return "PregnantHealthySetting [id=" + id + ", userId=" + userId
				+ ", project=" + project + ", state=" + state + ", addTime="
				+ addTime + "]";
	}
        
}