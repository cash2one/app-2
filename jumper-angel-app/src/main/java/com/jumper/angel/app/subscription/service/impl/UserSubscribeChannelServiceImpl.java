package com.jumper.angel.app.subscription.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.subscription.entity.NewsChanels;
import com.jumper.angel.app.subscription.entity.NewsInformation;
import com.jumper.angel.app.subscription.entity.UserSubscribeChannel;
import com.jumper.angel.app.subscription.mapper.NewsChanelsMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationCommentsMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationMapper;
import com.jumper.angel.app.subscription.mapper.UserSubscribeChannelMapper;
import com.jumper.angel.app.subscription.service.UserSubscribeChannelService;
import com.jumper.angel.app.subscription.vo.UserSubscribeChannelVo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.DateUtil;
/**
 * 用户订阅频道Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class UserSubscribeChannelServiceImpl implements UserSubscribeChannelService {
	
	@Autowired
	private UserSubscribeChannelMapper userSubscribeChannelMapper;
	
	@Autowired
	private NewsInformationMapper newsInformationMapper;
	
	@Autowired
	private NewsInformationCommentsMapper newsInformationCommentsMapper;
	
	@Autowired
	private NewsChanelsMapper newsChanelsMapper;
	
	/**
	 * 查询用户已订阅的频道
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:32:54
	 * @updateTime 2016-12-1,下午3:32:54
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param period 周期阶段
	 * @return
	 */
	public List<Map<String, Object>> findUserSubscribeChannel(long userId, int period) {
		//用户已订阅的频道
		List<Map<String, Object>> list = userSubscribeChannelMapper.findUserSubscribeChannel(userId);
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			//参数
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("channelId", map.get("id"));
			param.put("beginIndex", 0);
			param.put("everyPage", 1);
			param.put("period", period);
			//资讯信息
			List<NewsInformation> listNews = newsInformationMapper.findNewsInformationByChannelId(param);
			//返回的参数
			Map<String, Object> result = new HashMap<String, Object>();
			if(listNews!=null && listNews.size()>0) {
				result.put("id", listNews.get(0).getId());
				result.put("imageUrl", ConfigProUtils.get("file_path")+listNews.get(0).getImageUrl());
				result.put("fromLogoUrl", ConfigProUtils.get("file_path")+listNews.get(0).getFromLogoUrl());
				result.put("keywords", StringUtils.isEmpty(listNews.get(0).getIntroduct())?"":listNews.get(0).getIntroduct());
				result.put("title", listNews.get(0).getTitle());
				result.put("praise", listNews.get(0).getPraise());//收藏数
				result.put("clickCount", listNews.get(0).getClicks());//阅读量
				result.put("commentCount", newsInformationCommentsMapper.findCount(listNews.get(0).getId()));//评论量
				result.put("addTime", DateUtil.showTime(listNews.get(0).getPublishTime(), "yyyy年MM月dd日"));
			}
			//拼装进map中
			map.put("newsInformation", result);
		}
		return list;
	}
	
	/**
	 * 查询所有频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午10:31:15
	 * @updateTime 2016-12-6,上午10:31:15
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param stage 孕期阶段
	 * @return
	 */
	public List<UserSubscribeChannelVo> findUserSubscribeChannelAll(long userId, int stage) {
		//返回
		List<UserSubscribeChannelVo> list = new ArrayList<UserSubscribeChannelVo>();
		//查询所有频道
		List<NewsChanels> channelList = newsChanelsMapper.findNewChannels();
		//查询用户已订阅的频道
		List<UserSubscribeChannel> userChannelList = userSubscribeChannelMapper.findUserSubscribeChannelByUserId(userId);
		for(int i=0; i<channelList.size(); i++) {
			NewsChanels channel = channelList.get(i);
			UserSubscribeChannelVo vo = new UserSubscribeChannelVo();
			vo.setChannelId(channel.getId());
			vo.setName(channel.getChanelName());
			vo.setChannelDesc(channel.getChannelDesc());
			vo.setImgUrl(ConfigProUtils.get("file_path")+channel.getImgUrl());
			for(int j=0; j<userChannelList.size(); j++) {
				UserSubscribeChannel userChannel = userChannelList.get(j);
				//转int在来比较
				if(userChannel.getChannelId().intValue() == channel.getId().intValue()) {
					vo.setIsSubscribe(true);
				}
			}
			//参数
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("channelId", channel.getId().intValue());
			param.put("beginIndex", 0);
			param.put("everyPage", 1);
			param.put("period", stage);
			//资讯信息
			List<NewsInformation> listNews = newsInformationMapper.findNewsInformationByChannelId(param);
			//如果频道下没有文章则不显示该频道
			if(listNews!=null && listNews.size()>0) {
				list.add(vo);
			}
		}
		return list;
	}
	
	/**
	 * 添加用户订阅频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:38:11
	 * @updateTime 2016-12-6,上午11:38:11
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channel
	 */
	public void saveUserSubscribeChannel(UserSubscribeChannel channel) {
		//通过频道ID查询频道信息
		NewsChanels news = newsChanelsMapper.findNewChannelById(channel.getChannelId());
		if(news != null) {
			news.setSubNum(news.getSubNum()+1);
			//更新订阅量
			newsChanelsMapper.updateNewChannel(news);
		}
		userSubscribeChannelMapper.saveUserSubscribeChannel(channel);
	}
	
	/**
	 * 删除用户频道
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:38:35
	 * @updateTime 2016-12-6,上午11:38:35
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id
	 */
	public void deleteUserSubscribeChannel(long userId, long channelId) {
		//通过频道ID查询频道信息
		NewsChanels news = newsChanelsMapper.findNewChannelById((int)channelId);
		if(news != null) {
			news.setSubNum(news.getSubNum()-1);
			//更新订阅量
			newsChanelsMapper.updateNewChannel(news);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("channelId", channelId);
		userSubscribeChannelMapper.deleteUserSubscribeChannel(param);
	}
}
