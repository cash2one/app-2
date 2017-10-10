package com.jumper.angel.app.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.search.mapper.TopicMapper;
import com.jumper.angel.app.search.model.po.Topic;
import com.jumper.angel.app.search.model.vo.TopicVo;
import com.jumper.angel.app.search.service.TopicService;
import com.jumper.angel.frame.util.ConfigProUtils;
/**
 * 
 * @ClassName: TopicServiceImpl 
 * @Description: 话题组服务
 * @author caishiming
 * @date 2016年10月20日 上午9:19:19
 */
@Service
public class TopicServiceImpl implements TopicService{

	private static Logger logger = Logger.getLogger(TopicServiceImpl.class);
	@Autowired
	private TopicMapper topicMapper;

	/**
	 * 条件查询获取话题数量
	 */
	@Override
	public Integer findTopicCount(String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("topicName", keyword);
		Integer count = 0;
		count = topicMapper.findTopicCount(map);
		return count;
	}

	@Override
	public List<TopicVo> searchTopicList(String keyword, int page_index,
			int page_size) {
		List<TopicVo> voList = new ArrayList<TopicVo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (page_index-1)*page_size;
		map.put("topicName", keyword);
		map.put("start", start);
		map.put("page_size", page_size);
		List<Topic> topicList = topicMapper.searchTopicList(map);
		if(topicList != null && topicList.size() > 0){
			voList = getTopicVo(topicList);
		}
		return voList;
	}

	private List<TopicVo> getTopicVo(List<Topic> topicList) {
		List<TopicVo> voList = new ArrayList<TopicVo>();
		if(topicList != null && topicList.size() > 0){
			for (Topic topic : topicList) {
				TopicVo topicVo = new TopicVo();
				//话题id
				if(StringUtils.isNotEmpty(topic.getTopicId()+"")){
					topicVo.setTopicId(topic.getTopicId());
				}
				//话题名称
				if(StringUtils.isNotEmpty(topic.getTopicName())){
					topicVo.setTopicName(topic.getTopicName());
				}
				//话题简介
				if(StringUtils.isNotEmpty(topic.getTopicProfile())){
					topicVo.setTopicProfile(topic.getTopicProfile());
				}
				//话题图片
				if(StringUtils.isNotEmpty(topic.getThematicImg())){
					if(topic.getThematicImg().contains("http")){
						topicVo.setThematicImg(topic.getThematicImg());
					}else{
						topicVo.setThematicImg(ConfigProUtils.get("file_path")+topic.getThematicImg());
					}
				}
				//参与人数
				if(StringUtils.isNotEmpty(topic.getTopicMembership()+"")){
					topicVo.setTopicMembership(topic.getTopicMembership());
				}
				voList.add(topicVo);
			}
		}
		return voList;
	}
	
	
	

}
