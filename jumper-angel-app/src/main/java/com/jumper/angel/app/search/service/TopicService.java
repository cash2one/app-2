package com.jumper.angel.app.search.service;

import java.util.List;

import com.jumper.angel.app.search.model.vo.TopicVo;


/**
* @ClassName: TopicService 
* @Description: 话题组服务
* @author gyx
* @date 2016年10月20日 上午9:14:56
 */
public interface TopicService {

	/**
	 * 查询满足条件的话题数量
	 * @param keyword
	 * @return
	 */
	Integer findTopicCount(String keyword);

	/**
	 * 
	 * @param keyword
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<TopicVo> searchTopicList(String keyword, int page_index, int page_size);
	
}
