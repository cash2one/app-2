package com.jumper.angel.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码是否合法
 * @author 薛光敏
 * @date 2015-08-11
 */
public class PasswordVerificationUtils {
	public static boolean isPasswordValid(String password){
		Pattern pattern = Pattern.compile(Const.PASSWORD_FORMAT);
    	Matcher matcher = pattern.matcher(password);
    	if(matcher.matches()) {
    		return true;
    	}
    	return false;
	}
}
