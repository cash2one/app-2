package com.jumper.angel.app.usercenter.service;

import com.jumper.angel.app.usercenter.entity.AppVersion;

/**
 * APP版本service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-9
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface AppVersionService {

	/**
	 * 查询最新app版本
	 * @version 1.0
	 * @createTime 2016-12-9,下午6:12:22
	 * @updateTime 2016-12-9,下午6:12:22
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param appType 0:android 1:ios
	 * @return
	 */
	public AppVersion findAppVersion(int appType);
}
