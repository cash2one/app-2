package com.jumper.angel.app.subscription.mapper;

import java.util.List;
import com.jumper.angel.app.subscription.entity.NewsChanels;

/**
 * 频道Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsChanelsMapper {
	
	/**
	 * 查询所有频道
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:34:55
	 * @updateTime 2016-12-1,下午3:34:55
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public List<NewsChanels> findNewChannels();

	/**
	 * 通过频道id获取频道信息
	 * @param channelId
	 * @return
	 */
	public NewsChanels findNewChannelById(int channelId);
	
	/**
	 * 查询所有频道
	 * @version 1.0
	 * @createTime 2017-1-6,上午11:47:21
	 * @updateTime 2017-1-6,上午11:47:21
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param news
	 */
	public void updateNewChannel(NewsChanels news);
}
