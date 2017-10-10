package com.jumper.angel.frame.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.jumper.angel.frame.util.Converter;
import com.jumper.angel.frame.util.ReturnMsg;
import com.jumper.angel.frame.util.DesEncryptUtils;

import com.google.gson.Gson;

public class ResultMsgUtils {

	/** 出错输出JSON数据格式 **/ 
	public static String errorMsg(String error_msg) {
		try {
			ReturnMsg ret = new ReturnMsg();
			ret.setMsg(0);
			ret.setMsgbox(error_msg);
			List<Object> obj = new ArrayList<Object>();
			ret.setData(obj);

			Converter<ReturnMsg> c = new Converter<ReturnMsg>();
			String retJSON = c.convertObjToJson(ret);

			/** 加密返回的JSON **/
			String encodeString = URLEncoder.encode(retJSON, "UTF-8");
			return DesEncryptUtils.Encrypt(encodeString, Const.PARAMS_KEYS);
		} catch (Exception ex) {
			return ex.getMessage();
		}

	}

	/** 正确输出JSON数据格式 **/
	public static String successMsg(String tips, Object data) {
		try {
			ReturnMsg ret = new ReturnMsg();
			ret.setMsg(1);
			ret.setMsgbox(tips);
			ret.setData(data);
			Converter<ReturnMsg> c = new Converter<ReturnMsg>();
			String retJSON = c.convertObjToJson(ret);
			String encodeString = URLEncoder.encode(retJSON, "UTF-8");
			return DesEncryptUtils.Encrypt(encodeString, Const.PARAMS_KEYS);
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	/** 处理返回消息的MAP格式 **/
	public static String processMsg(Object data) {
		try {
			ReturnMsg ret = (ReturnMsg) data;
			if (ret.getData() == null) {
				List<Object> obj = new ArrayList<Object>();
				ret.setData(obj);
			}
			/** 先暂时处理掉无数据的时候显示暂无数据，原因：当更新成功的时候，也是返回暂无数据 */
			/*
			 * if((ret.getMsg()==Const.SUCCESS)) { if(ret.getData() instanceof
			 * Collection){ if(((Collection) ret.getData()).size()==0){
			 * ret.setMsgbox("暂无数据！"); } } }
			 */

			Converter<ReturnMsg> c = new Converter<ReturnMsg>();
			String retJSON = c.convertObjToJson(ret);
			String encodeString = URLEncoder.encode(retJSON, "UTF-8");
			return DesEncryptUtils.Encrypt(encodeString, Const.PARAMS_KEYS);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	/**
	 * 使用Gson转换list
	 * 
	 * @param data
	 * @return
	 */
	public static String processGsonToMsg(Object data) {
		try {
			ReturnMsg ret = (ReturnMsg) data;
			Gson json = new Gson();
			String encodeString = URLEncoder.encode(json.toJson(ret), "UTF-8");
			return DesEncryptUtils.Encrypt(encodeString, Const.PARAMS_KEYS);
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

}
