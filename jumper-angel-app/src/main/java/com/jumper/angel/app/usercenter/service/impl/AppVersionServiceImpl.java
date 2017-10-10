package com.jumper.angel.app.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.usercenter.entity.AppVersion;
import com.jumper.angel.app.usercenter.mapper.AppVersionMapper;
import com.jumper.angel.app.usercenter.service.AppVersionService;
/**
 * APP版本serviceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-9
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
	
	@Autowired
	private AppVersionMapper appVersionMapper;
	
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
	public AppVersion findAppVersion(int appType) {
		return appVersionMapper.findAppVersion(appType);
	}
}
