package com.jumper.angel.app.subscription.mapper;

import com.jumper.angel.app.subscription.entity.NewsClick;

/**
 * 阅读Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsClickMapper {
	
	/**
	 * 查询文章阅读量
	 * @version 1.0
	 * @createTime 2016-12-2,下午2:17:39
	 * @updateTime 2016-12-2,下午2:17:39
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsId 新闻ID
	 * @return
	 */
	public int findNewsClick(long newsId);
	
	/**
	 * 查询文章当天是否被阅读过
	 * @version 1.0
	 * @createTime 2016-12-5,上午11:38:26
	 * @updateTime 2016-12-5,上午11:38:26
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsId 文章ID
	 * @return
	 */
	public NewsClick findCurrentNewsClick(long newsId);
	
	/**
	 * 更新文章阅读量
	 * @version 1.0
	 * @createTime 2016-12-5,上午11:39:01
	 * @updateTime 2016-12-5,上午11:39:01
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsClick
	 */
	public void updateNewsClick(NewsClick newsClick);
	
	/**
	 * 新增当天的阅读量
	 * @version 1.0
	 * @createTime 2016-12-5,下午1:50:27
	 * @updateTime 2016-12-5,下午1:50:27
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsClick
	 */
	public void saveNewsClick(NewsClick newsClick);
}
