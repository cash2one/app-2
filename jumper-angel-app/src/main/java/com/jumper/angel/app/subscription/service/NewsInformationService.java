package com.jumper.angel.app.subscription.service;

import java.util.List;

import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.app.subscription.vo.NewsInformationVo;

/**
 * 资讯信息Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface NewsInformationService {
	
	/**
	 * 通过频道ID查询资讯信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:41:05
	 * @updateTime 2016-12-1,下午3:41:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channelId 频道ID
	 * @param beginIndex 页数
	 * @param everyPage 每页显示的记录数
	 * @param period 孕周
	 * @return
	 */
	public List<NewsInformationVo> findNewsInformationByChannelId(long channelId, int beginIndex, int everyPage, int period);
	
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
	public int findCount(long channelId, int period);
	
	/**
	 * 通过ID查询资讯信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午5:50:39
	 * @updateTime 2016-12-1,下午5:50:39
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id 资讯ID
	 * @param userId 用户Id
	 * @return
	 */
	public NewsInformationVo findNewsInformationDetail(int id, int userId);

	/**
	 * 
	 * @param keywords 标题关键字
	 * @param page_index 分页索引
	 * @param page_size 分页大小
	 * @return
	 */
	public List<NewsCollectionVo> searchNewsInformation(String keywords,
			int page_index, int page_size);

	/**
	 * 关键字查询资讯文章数量
	 * @param keyword
	 * @return
	 */
	public Integer findNewsCountByConditions(String keyword);
}
