package com.jumper.angel.app.subscription.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.subscription.entity.UserSubscribeChannel;

/**
 * 用户订阅频道Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface UserSubscribeChannelMapper {
	
	/**
	 * 查询用户已订阅的频道
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:32:54
	 * @updateTime 2016-12-1,下午3:32:54
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findUserSubscribeChannel(long userId);
	
	/**
	 * 查询用户订阅的频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午10:22:23
	 * @updateTime 2016-12-6,上午10:22:23
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 * @return
	 */
	public List<UserSubscribeChannel> findUserSubscribeChannelByUserId(long userId);
	
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
	 * @param param
	 */
	public void deleteUserSubscribeChannel(Map<String, Object> param);
}
