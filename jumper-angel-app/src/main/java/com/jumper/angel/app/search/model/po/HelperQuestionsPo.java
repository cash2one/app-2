package com.jumper.angel.app.search.model.po;
import com.jumper.core.model.po.BasePo;
/**
 * Title:HelperQuestionsPo
 * Description:IM聊天记录表Po类
 * Copyright: Copyright (c) 2016
 * Company: 京柏医疗-天使医生
 * @author 徐运贤 - xuyunxian@126.com
 * @version v1.0 2016-12-06
 */
public class HelperQuestionsPo extends BasePo implements java.io.Serializable {
	
	/**类的版本号*/
	private static final long serialVersionUID = 1954009000970240L;

		/** �������͵�ID�� */	private java.lang.Integer questionTypeId;	/**  */	private java.lang.String title;	/**  */	private java.lang.String answer;	/** ״̬,0:����,1:����ʾ */	private java.lang.Integer status;	/**  */	private java.util.Date addTime;	/**  */	private java.lang.String url;	/**  */	private java.lang.String detailsUrl;	/**  */	private java.lang.String imgUrl;	/**  */	private java.lang.String questionIntorduction;	public java.lang.Long getId() {	    return this.id;	}	public void setId(java.lang.Long id) {	    this.id=id;	}	public java.lang.Integer getQuestionTypeId() {	    return this.questionTypeId;	}	public void setQuestionTypeId(java.lang.Integer questionTypeId) {	    this.questionTypeId=questionTypeId;	}	public java.lang.String getTitle() {	    return this.title;	}	public void setTitle(java.lang.String title) {	    this.title=title;	}	public java.lang.String getAnswer() {	    return this.answer;	}	public void setAnswer(java.lang.String answer) {	    this.answer=answer;	}	public java.lang.Integer getStatus() {	    return this.status;	}	public void setStatus(java.lang.Integer status) {	    this.status=status;	}	public java.util.Date getAddTime() {	    return this.addTime;	}	public void setAddTime(java.util.Date addTime) {	    this.addTime=addTime;	}	public java.lang.String getUrl() {	    return this.url;	}	public void setUrl(java.lang.String url) {	    this.url=url;	}	public java.lang.String getDetailsUrl() {	    return this.detailsUrl;	}	public void setDetailsUrl(java.lang.String detailsUrl) {	    this.detailsUrl=detailsUrl;	}	public java.lang.String getImgUrl() {	    return this.imgUrl;	}	public void setImgUrl(java.lang.String imgUrl) {	    this.imgUrl=imgUrl;	}	public java.lang.String getQuestionIntorduction() {	    return this.questionIntorduction;	}	public void setQuestionIntorduction(java.lang.String questionIntorduction) {	    this.questionIntorduction=questionIntorduction;	}	public String toString() {	    return "[" + "id:" + getId() +"," + "questionTypeId:" + getQuestionTypeId() +"," + "title:" + getTitle() +"," + "answer:" + getAnswer() +"," + "status:" + getStatus() +"," + "addTime:" + getAddTime() +"," + "url:" + getUrl() +"," + "detailsUrl:" + getDetailsUrl() +"," + "imgUrl:" + getImgUrl() +"," + "questionIntorduction:" + getQuestionIntorduction() +"]";	}
}