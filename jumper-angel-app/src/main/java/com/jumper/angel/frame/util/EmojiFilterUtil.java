package com.jumper.angel.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情过滤工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月26日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class EmojiFilterUtil {

	/**
	 * 过滤表情符号
	 * @version v.1.0
	 * @createTime 2017年4月26日,上午9:47:38
	 * @updateTime 2017年4月26日,上午9:47:38
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static String filter(String str) {
		if (str.trim().isEmpty()) {
	        return str;
	    }
	    String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
	    String reStr = "";
	    Pattern emoji = Pattern.compile(pattern);
	    Matcher emojiMatcher = emoji.matcher(str);
	    str = emojiMatcher.replaceAll(reStr);
	    return str;
	}
}
