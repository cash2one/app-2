package com.jumper.angel.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017-1-12
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Util {
	
	/**
	 * 验证手机号码是否合法
	 * 合法格式：13、14、15、17、18开头
	 * @version 1.0
	 * @createTime 2017-1-12,上午10:20:18
	 * @updateTime 2017-1-12,上午10:20:18
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param telNum
	 * @return
	 */
	public static boolean isMobiPhoneNum(String telNum) {
		String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(telNum);  
        return m.matches();  
	} 
}
