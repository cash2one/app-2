package com.jumper.angel.app.subscription.mapper;

import java.util.List;

import com.jumper.angel.app.subscription.entity.NewsCollection;

/**
 * 收藏Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsCollectionMapper {
	
	/**
	 * 查询用户收藏过的文章
	 * @version 1.0
	 * @createTime 2016-12-2,下午4:00:35
	 * @updateTime 2016-12-2,下午4:00:35
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param
	 * @return
	 */
	public NewsCollection findNewsCollection(NewsCollection param);
	
	/**
	 * 添加收藏
	 * @version 1.0
	 * @createTime 2016-12-5,下午6:14:24
	 * @updateTime 2016-12-5,下午6:14:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param collection
	 */
	public void saveNewsCollection(NewsCollection collection);
	
	/**
	 * 删除收藏
	 * @version 1.0
	 * @createTime 2016-12-5,下午6:14:24
	 * @updateTime 2016-12-5,下午6:14:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param collection
	 */
	public void deleteNewsCollection(NewsCollection collection);

	/**
	 * 获取收藏的文章列表
	 * @param user_id
	 * @return
	 */
	public List<NewsCollection> findNewsCollectionsByUserId(int user_id);
}
