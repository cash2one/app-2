package com.jumper.angel.app.subscription.service;

import java.util.List;

import com.jumper.angel.app.subscription.entity.NewsInformationComments;
import com.jumper.angel.app.subscription.vo.NewsInformationCommentsVo;

/**
 * 评论Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsInformationCommentsService {

	/**
	 * 通过新闻ID查询评论信息
	 * @version 1.0
	 * @createTime 2016-12-2,下午5:24:11
	 * @updateTime 2016-12-2,下午5:24:11
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsInformationId 新闻ID
	 * @param beginIndex 页数
	 * @param everyPage 每页显示的记录数
	 * @param 用户ID
	 * @return
	 */
	public List<NewsInformationCommentsVo> findCommentsByNewsInformationId(int newsInformationId, int beginIndex, int everyPage, int userId);
	
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
	 * 更新评论
	 * @version 1.0
	 * @createTime 2016-12-5,下午5:22:47
	 * @updateTime 2016-12-5,下午5:22:47
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id 评论ID
	 * @param userId 用户ID
	 */
	public void updateComments(int Id, int userId);
}
