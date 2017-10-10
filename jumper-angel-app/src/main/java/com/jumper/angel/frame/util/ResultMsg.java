package com.jumper.angel.frame.util;

import java.util.HashMap;

/**
 * 返回结果
 * @Description 
 * @author qinxiaowei
 * @date 2016-6-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ResultMsg {
	
	private int msg;
	
	private String msgbox;
	
	private Object data = new HashMap<String, Object>();
	
	public ResultMsg(){}
	
	public ResultMsg(int msg, String msgbox){
		this.msg = msg;
		this.msgbox = msgbox;
	}
	
	public ResultMsg(int msg, String msgbox, Object data){
		this(msg,msgbox);
		this.data =data;
	}
	
	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public String getMsgbox() {
		return msgbox;
	}

	public void setMsgbox(String msgbox) {
		this.msgbox = msgbox;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
