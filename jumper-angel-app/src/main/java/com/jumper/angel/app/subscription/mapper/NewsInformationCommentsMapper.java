package com.jumper.angel.app.subscription.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.subscription.entity.NewsInformationComments;

/**
 * 评论Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsInformationCommentsMapper {

	/**
	 * 通过新闻ID查询评论信息
	 * @version 1.0
	 * @createTime 2016-12-2,下午5:24:11
	 * @updateTime 2016-12-2,下午5:24:11
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param
	 * @return
	 */
	public List<NewsInformationComments> findCommentsByNewsInformationId(Map<String, Object> param);
	
	/**
	 * 通过新闻ID获取总记录数
	 * @version 1.0
	 * @createTime 2016-12-5,上午10:40:25
	 * @updateTime 2016-12-5,上午10:40:25
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsInformationId 新闻ID
	 * @return
	 */
	public int findCount(int newsInformationId);
	
	/**
	 * 新增评论
	 * @version 1.0
	 * @createTime 2016-12-2,下午5:24:56
	 * @updateTime 2016-12-2,下午5:24:56
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param comments
	 */
	public void addComments(NewsInformationComments comments);
	
	/**
	 * 通评论ID查询评论信息
	 * @version 1.0
	 * @createTime 2016-12-5,下午5:22:08
	 * @updateTime 2016-12-5,下午5:22:08
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id 评论ID
	 * @return
	 */
	public NewsInformationComments findCommentsById(int id);
	
	/**
	 * 更新评论
	 * @version 1.0
	 * @createTime 2016-12-5,下午5:22:47
	 * @updateTime 2016-12-5,下午5:22:47
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param comments
	 */
	public void updateComments(NewsInformationComments comments);
}
