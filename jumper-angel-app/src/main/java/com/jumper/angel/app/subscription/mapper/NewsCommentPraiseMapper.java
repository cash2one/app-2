package com.jumper.angel.app.subscription.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.subscription.entity.NewsCommentPraise;

/**
 * 评论点赞
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsCommentPraiseMapper {
	
	/**
	 * 添加用户点赞记录
	 * @version 1.0
	 * @createTime 2016-12-6,下午3:58:11
	 * @updateTime 2016-12-6,下午3:58:11
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param praise
	 */
	public void saveNewsCommentPraise(NewsCommentPraise praise);
	
	/**
	 * 查询用户是否点赞了该评论
	 * @version 1.0
	 * @createTime 2016-12-6,下午3:59:10
	 * @updateTime 2016-12-6,下午3:59:10
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param
	 * @return
	 */
	public List<NewsCommentPraise> findNewsCommentPraise(Map<String, Object> param);
}
