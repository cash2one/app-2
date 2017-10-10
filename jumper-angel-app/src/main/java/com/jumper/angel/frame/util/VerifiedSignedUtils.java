package com.jumper.angel.frame.util;

import com.jumper.angel.frame.util.MD5EncryptUtils;

public class VerifiedSignedUtils {
	
	public static boolean verified(String method,String params,String signed)
	{
		String api_key=Const.SIGNED_KEYS;
		String combined_str = api_key+"method"+method+"params"+params+api_key;
		String tmp_signed = MD5EncryptUtils.getMd5Value(combined_str);
		if(tmp_signed.equals(signed))
		{
			return true;
		}
		return false;
	}
}
