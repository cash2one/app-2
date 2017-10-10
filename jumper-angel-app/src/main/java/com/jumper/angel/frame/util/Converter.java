package com.jumper.angel.frame.util;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * json转换类
 * 
 * @author 薛光敏
 * 
 * @date 2015-08-02
 *
 */
public class Converter<T>{
	
	public String convertObjToJson(T bean) {
		if(bean == null)
			return null;
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		serializer.write(bean);
		return sw.toString();
	}
	
	public static String convertListToString(String seperator, List<String> phraseList){
		StringBuilder sb = new StringBuilder();
		if(phraseList != null){
			for(String str:phraseList){
				if(!StringUtils.isBlank(str)){
					sb.append(str + seperator);
				}
			}
			return sb.toString().replaceAll("\\,$", "");
		}
		return null;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public List<T> jsonToBean(String json, Class<List> bean) {
		if (json == null)
			return null;
		List<T> result = null;
		try {
			JSONObject jo = new JSONObject(); 
			result = jo.parseObject(json, bean);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public String convertListToJson(List<T> beanList) {
		if(beanList == null || beanList.size() == 0){
			return null;
		}
		String result = null;
		try {
			result = JSON.toJSONString(beanList, true);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
