package com.jumper.angel.app.subscription.service;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.subscription.entity.UserSubscribeChannel;
import com.jumper.angel.app.subscription.vo.UserSubscribeChannelVo;

/**
 * 用户订阅频道Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface UserSubscribeChannelService {
	
	/**
	 * 查询用户已订阅的频道
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:32:54
	 * @updateTime 2016-12-1,下午3:32:54
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param period 周期阶段
	 * @return
	 */
	public List<Map<String, Object>> findUserSubscribeChannel(long userId, int period);
	
	/**
	 * 查询所有频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午10:31:15
	 * @updateTime 2016-12-6,上午10:31:15
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param stage 孕期阶段
	 * @return
	 */
	public List<UserSubscribeChannelVo> findUserSubscribeChannelAll(long userId, int stage);
	
	/**
	 * 添加用户订阅频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:38:11
	 * @updateTime 2016-12-6,上午11:38:11
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channel
	 */
	public void saveUserSubscribeChannel(UserSubscribeChannel channel);
	
	/**
	 * 删除用户频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:38:35
	 * @updateTime 2016-12-6,上午11:38:35
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 */
	public void deleteUserSubscribeChannel(long userId, long channelId);
}
