package com.jumper.angel.app.subscription.service;

import com.jumper.angel.app.subscription.entity.NewsChanels;

/**
 * 频道Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsChanelsService {
	
	/**
	 * 通过频道id获取频道信息
	 * @param channelId
	 * @return
	 */
	public NewsChanels findNewChannelById(int channelId);
}
