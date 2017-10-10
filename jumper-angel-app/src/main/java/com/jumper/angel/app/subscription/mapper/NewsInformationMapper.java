package com.jumper.angel.app.subscription.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.subscription.entity.NewsInformation;

/**
 * 资讯信息Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsInformationMapper {

	/**
	 * 通过频道ID查询资讯信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:41:05
	 * @updateTime 2016-12-1,下午3:41:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param
	 * @return
	 */
	public List<NewsInformation> findNewsInformationByChannelId(Map<String, Object> param);
	
	/**
	 * 通过频道ID查询资讯总记录数
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:42:00
	 * @updateTime 2016-12-1,下午3:42:00
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channelId 频道ID
	 * @return
	 */
	public int findCount(Map<String, Object> param);
	
	/**
	 * 通过ID查询资讯信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午5:50:39
	 * @updateTime 2016-12-1,下午5:50:39
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id 资讯ID
	 * @return
	 */
	public NewsInformation findNewsInformationDetail(long id);
	
	/**
	 * 更新文章信息
	 * @version 1.0
	 * @createTime 2016-12-5,下午6:29:36
	 * @updateTime 2016-12-5,下午6:29:36
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param news
	 */
	public void updateNewsInformation(NewsInformation news);

	/**
	 * 通过关键字查询资讯文章分页数据
	 * @param param
	 * @return
	 */
	public List<NewsInformation> searchNewsInformation(Map<String, Object> param);

	/**
	 * 关键字查询资讯文章数量
	 * @param param
	 * @return
	 */
	public Integer findNewsCountByConditions(Map<String, Object> param);
	
	/**
	 * 更新文章阅读量
	 * @version 1.0
	 * @createTime 2017-2-17,上午11:29:58
	 * @updateTime 2017-2-17,上午11:29:58
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param news
	 */
	public void updateNewsInformationClick(NewsInformation news);
}
