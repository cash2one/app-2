package com.jumper.angel.app.subscription.service;

import java.util.List;

import com.jumper.angel.app.subscription.entity.NewsCollection;
import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.app.subscription.vo.NewsInformationVo;

/**
 * 收藏service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-5
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsCollectionService {
	
	/**
	 * 收藏
	 * @version 1.0
	 * @createTime 2016-12-5,下午6:18:12
	 * @updateTime 2016-12-5,下午6:18:12
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param newsInformationId 文章ID
	 * @return
	 */
	public int collection(int userId, int newsInformationId, int flag);

	/**
	 * 获取收藏文章列表
	 * @param user_id
	 * @return
	 */
	public List<NewsCollectionVo> findNewsCollectionsByUserId(int user_id);

	/**
	 * 获取收藏帖子列表
	 * @param user_id
	 * @return
	 */
	public List<NewsCollectionVo> findTopicCollectionsByUserId(int user_id);
}
