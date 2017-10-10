package com.jumper.angel.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ReturnMsg {
	
	private final static Logger logger = Logger.getLogger(ReturnMsg.class);
	
	private int msg;
	
	private String msgbox;
	
	private Object data;
	
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
	
	public static ReturnMsg setReturnMsg(String lang, int flag, String msgKey){
		Properties props = getPropertiesFromResource(lang);
		ReturnMsg msg = new ReturnMsg();
		msg.setMsg(flag);
		msg.setMsgbox(props.getProperty(msgKey));
		return msg;
	}
	
	public static ReturnMsg setReturnMsg(int flag, String message){
		ReturnMsg msg = new ReturnMsg();
		msg.setMsg(flag);
		msg.setMsgbox(message);
		return msg;
	}
	
	public static Properties getPropertiesFromResource(String language){
		String lang = StringUtils.lowerCase(language);
		String path = ReturnMsg.class.getClassLoader().getResource("").getPath();
		File file = new File(path+"cn/com/jumper/anglesound/message/messages_"+lang+".properties");
		Properties props = new Properties();
		try {
			InputStream inputStream = new FileInputStream(file);
			//InputStream inputStream = ReturnMsg.class.getClassLoader().getResourceAsStream(path+"/cn/com/jumper/anglesound/message/messages_"+language+".properties");
			props.load(inputStream);
		} catch (Exception e) {
			logger.error("load properties file error!");
			e.printStackTrace();
			return null;
		}
		return props;
	}
	public static String getMessageFromProps(String language, String key){
		Properties props = getPropertiesFromResource(language);
		if(props != null){
			return props.getProperty(key);
		}
		logger.error("get properties message error!");
		return "";
	}
	
	public static void main(String[] args) {
		ReturnMsg msg = ReturnMsg.setReturnMsg("cn", 0, "user.login.username.none");
	}
}
