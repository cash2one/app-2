package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏帖子封装类
 * @author gyx
 * @time 2016年12月19日
 */
public class DebatePostVo implements Serializable{
	private static final long serialVersionUID = -1078538850877270017L;
	
	//帖子id
	private long DEBATEPOST_ID;
	//帖子标题
	private String DEBATEPOST_TITLE;
	//帖子内容
	private String DEBATEPOST_CONTENT;
	//主题图片
	private String IMG;
	//创建时间
	private long CREDTE_DATE;
	//话题id
	private long TOPIC_ID;
	//话题名称
	private String TOPIC_NAME;
	public long getDEBATEPOST_ID() {
		return DEBATEPOST_ID;
	}
	public void setDEBATEPOST_ID(long dEBATEPOST_ID) {
		DEBATEPOST_ID = dEBATEPOST_ID;
	}
	public String getDEBATEPOST_TITLE() {
		return DEBATEPOST_TITLE;
	}
	public void setDEBATEPOST_TITLE(String dEBATEPOST_TITLE) {
		DEBATEPOST_TITLE = dEBATEPOST_TITLE;
	}
	public String getDEBATEPOST_CONTENT() {
		return DEBATEPOST_CONTENT;
	}
	public void setDEBATEPOST_CONTENT(String dEBATEPOST_CONTENT) {
		DEBATEPOST_CONTENT = dEBATEPOST_CONTENT;
	}
	public String getIMG() {
		return IMG;
	}
	public void setIMG(String iMG) {
		IMG = iMG;
	}
	
	public long getCREDTE_DATE() {
		return CREDTE_DATE;
	}
	public void setCREDTE_DATE(long cREDTE_DATE) {
		CREDTE_DATE = cREDTE_DATE;
	}
	public long getTOPIC_ID() {
		return TOPIC_ID;
	}
	public void setTOPIC_ID(long tOPIC_ID) {
		TOPIC_ID = tOPIC_ID;
	}
	public String getTOPIC_NAME() {
		return TOPIC_NAME;
	}
	public void setTOPIC_NAME(String tOPIC_NAME) {
		TOPIC_NAME = tOPIC_NAME;
	}
	
	
	
	
}
