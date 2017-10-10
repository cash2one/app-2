package com.jumper.angel.app.subscription.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.subscription.entity.NewsChanels;
import com.jumper.angel.app.subscription.mapper.NewsChanelsMapper;
import com.jumper.angel.app.subscription.service.NewsChanelsService;
/**
 * 频道ServiceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class NewsChanelsServiceImpl implements NewsChanelsService {
	
	@Autowired
	private NewsChanelsMapper newsChanelsMapper;
	
	/**
	 * 通过频道id获取频道信息
	 * @param channelId
	 * @return
	 */
	public NewsChanels findNewChannelById(int channelId) {
		return newsChanelsMapper.findNewChannelById(channelId);
	}
}
