package com.jumper.angel.app.subscription.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.subscription.entity.NewsChanels;
import com.jumper.angel.app.subscription.entity.NewsCollection;
import com.jumper.angel.app.subscription.entity.NewsInformation;
import com.jumper.angel.app.subscription.mapper.ImUserCollectionMapper;
import com.jumper.angel.app.subscription.mapper.NewsChanelsMapper;
import com.jumper.angel.app.subscription.mapper.NewsCollectionMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationMapper;
import com.jumper.angel.app.subscription.service.NewsCollectionService;
import com.jumper.angel.app.subscription.vo.DebatePostVo;
import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
/**
 * 收藏serviceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-5
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class NewsCollectionServiceImpl implements NewsCollectionService {

	@Autowired
	private NewsCollectionMapper newsCollectionMapper;
	
	@Autowired
	private NewsInformationMapper newsInformationMapper;
	
	@Autowired
	private NewsChanelsMapper newsChanelsMapper;
	
	@Autowired
	private ImUserCollectionMapper imUserCollectionMapper;
	
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
	public int collection(int userId, int newsInformationId, int flag) {
		int result = 0;
		try {
			//参数
			NewsCollection collection = new NewsCollection();
			collection.setUserId(userId);
			collection.setNewsInformationId(newsInformationId);
			//查询文章信息
			NewsInformation news = newsInformationMapper.findNewsInformationDetail(newsInformationId);
			//查询用户收藏过的文章
			NewsCollection newsCollection = newsCollectionMapper.findNewsCollection(collection);
			//有
			if(flag==1) {
				//收藏数-1
				news.setPraise(news.getPraise()-1);
				//更新收藏数
				newsInformationMapper.updateNewsInformation(news);
				//删除收藏
				newsCollectionMapper.deleteNewsCollection(collection);
				//取消收藏成功
				result = 1;
			} else if(flag==0) { //无
				//收藏数-1
				news.setPraise(news.getPraise()+1);
				//更新收藏数
				newsInformationMapper.updateNewsInformation(news);
				newsCollection = new NewsCollection();
				newsCollection.setUserId(userId);
				newsCollection.setNewsInformationId(newsInformationId);
				newsCollection.setAddTime(new Date());
				//添加收藏
				newsCollectionMapper.saveNewsCollection(newsCollection);
				//添加收藏成功
				result = 2;
			}
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	/**
	 * 获取收藏文章列表
	 */
	@Override
	public List<NewsCollectionVo> findNewsCollectionsByUserId(int user_id) {
		List<NewsCollection> newsCollections = newsCollectionMapper.findNewsCollectionsByUserId(user_id);
		List<NewsCollectionVo> voList = new ArrayList<NewsCollectionVo>();
		if(newsCollections != null && newsCollections.size()>0){
			voList = getNewsInformationVo(newsCollections);
		}
		return voList;
	}
	
	/**
	 * 组装收藏文章列表
	 * @param newsCollections
	 * @return
	 */
	private List<NewsCollectionVo> getNewsInformationVo(
			List<NewsCollection> newsCollections) {
		List<NewsCollectionVo> voList = new ArrayList<NewsCollectionVo>();
		if(newsCollections != null && newsCollections.size()>0){
			for (NewsCollection newsCollection : newsCollections) {
				NewsCollectionVo informationVo = new NewsCollectionVo();
				if(newsCollection.getNewsInformationId()!=null){
					//获取文章信息
					NewsInformation information = newsInformationMapper.findNewsInformationDetail(newsCollection.getNewsInformationId());
					if(information != null){
						//资讯文章id
						informationVo.setId(information.getId());
						//文章图片
						if(StringUtils.isNotEmpty(information.getImageUrl())){
							informationVo.setImageUrl(ConfigProUtils.get("file_path")+information.getImageUrl());							
						}
						//文章标题
						if(StringUtils.isNotEmpty(information.getTitle())){
							informationVo.setTitle(information.getTitle());
						}
						//文章简介
						if(StringUtils.isNotEmpty(information.getIntroduct())){
							informationVo.setKeywords(information.getIntroduct());
						}
						//添加收藏的时间
						if(newsCollection.getAddTime() != null){
							informationVo.setSortTime(DateUtil.converStringDate(newsCollection.getAddTime(), Const.YYYYMMDD_HHMMSS));
							informationVo.setAddTime(DateUtil.showTime(newsCollection.getAddTime(), Const.CN_YYYYMMDD));
						}
						//来源（频道）
						if(information.getChannelId() != null){
							int channelId = information.getChannelId();
							NewsChanels newsChanels = newsChanelsMapper.findNewChannelById(channelId);
							if(newsChanels != null && StringUtils.isNotEmpty(newsChanels.getChanelName())){
								informationVo.setSourceFrom(newsChanels.getChanelName());
							}
						}
						//文章的类型设置为1，方便前端区分
						informationVo.setType(1);
						//文章所在的频道id
						informationVo.setParentId(information.getChannelId());
						voList.add(informationVo);
					}
					
				}
				
			}
		}
		return voList;
	}

	/**
	 * 获取收藏的帖子列表
	 */
	@Override
	public List<NewsCollectionVo> findTopicCollectionsByUserId(int user_id) {
		List<NewsCollectionVo> voList = new ArrayList<NewsCollectionVo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user_id+"");
		List<DebatePostVo> topicCollectionList = imUserCollectionMapper.findTopicCollectionsByUserId(map);
		if(topicCollectionList != null && topicCollectionList.size() > 0){
			for (DebatePostVo debatePostVo : topicCollectionList) {
				NewsCollectionVo informationVo = new NewsCollectionVo();
				if(debatePostVo != null){
					//帖子id
					informationVo.setId((int)debatePostVo.getDEBATEPOST_ID());
					//帖子图片
					if(StringUtils.isNotEmpty(debatePostVo.getIMG())){
						if(debatePostVo.getIMG().contains("http")){
							informationVo.setImageUrl(debatePostVo.getIMG().split(";")[0]);							
						}else{
							informationVo.setImageUrl(ConfigProUtils.get("file_path")+debatePostVo.getIMG().split(";")[0]);
						}
					}
					//帖子标题
					if(StringUtils.isNotEmpty(debatePostVo.getDEBATEPOST_TITLE())){
						informationVo.setTitle(debatePostVo.getDEBATEPOST_TITLE());
					}
					//帖子简介
					if(StringUtils.isNotEmpty(debatePostVo.getDEBATEPOST_CONTENT())){
						informationVo.setKeywords(debatePostVo.getDEBATEPOST_CONTENT());
					}
					//添加收藏的时间
					if(StringUtils.isNotEmpty(debatePostVo.getCREDTE_DATE()+"")){
						String date = DateUtil.longToString(debatePostVo.getCREDTE_DATE(), Const.YYYYMMDD_HHMMSS);
						informationVo.setSortTime(DateUtil.longToString(debatePostVo.getCREDTE_DATE(), Const.YYYYMMDD_HHMMSS));
						informationVo.setAddTime(DateUtil.showTime(DateUtil.convertDate(date, Const.YYYYMMDD_HHMMSS), Const.CN_YYYYMMDD));
					}
					//来源（话题）
					if(StringUtils.isNotEmpty(debatePostVo.getTOPIC_NAME())){
						informationVo.setSourceFrom(debatePostVo.getTOPIC_NAME());
					}
					//帖子的类型设置为2，方便前端区分
					informationVo.setType(2);
					//帖子所在的话题id
					informationVo.setParentId((int)debatePostVo.getTOPIC_ID());
					voList.add(informationVo);
				}
			}
		}
		return voList;
	}
	
}
