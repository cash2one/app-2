package com.jumper.angel.app.subscription.service.impl;

import java.text.SimpleDateFormat;
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
import com.jumper.angel.app.subscription.entity.NewsClick;
import com.jumper.angel.app.subscription.entity.NewsCollection;
import com.jumper.angel.app.subscription.entity.NewsInformation;
import com.jumper.angel.app.subscription.mapper.NewsChanelsMapper;
import com.jumper.angel.app.subscription.mapper.NewsClickMapper;
import com.jumper.angel.app.subscription.mapper.NewsCollectionMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationCommentsMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationMapper;
import com.jumper.angel.app.subscription.service.NewsInformationCommentsService;
import com.jumper.angel.app.subscription.service.NewsInformationService;
import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.app.subscription.vo.NewsInformationVo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
/**
 * 资讯信息ServiceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class NewsInformationServiceImpl implements NewsInformationService {
	
	@Autowired
	private NewsInformationMapper newsInformationMapper;
	
	@Autowired
	private NewsClickMapper newsClickMapper;
	
	@Autowired
	private NewsCollectionMapper newsCollectionMapper;
	
	@Autowired
	private NewsInformationCommentsService newsInformationCommentsService;
	
	@Autowired
	private NewsInformationCommentsMapper newsInformationCommentsMapper;
	
	@Autowired
	private NewsChanelsMapper newsChanelsMapper;
	
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
	 * @param period 周期阶段
	 * @return
	 */
	public List<NewsInformationVo> findNewsInformationByChannelId(long channelId, int beginIndex, int everyPage, int period) {
		//返回
		List<NewsInformationVo> resultList = new ArrayList<NewsInformationVo>();
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", channelId);
		param.put("beginIndex", beginIndex);
		param.put("everyPage", everyPage);
		param.put("period", period);
		//查询资讯
		List<NewsInformation> list = newsInformationMapper.findNewsInformationByChannelId(param);
		for(int i=0; i<list.size(); i++) {
			NewsInformation news = list.get(i);
			NewsInformationVo vo = new NewsInformationVo();
			vo.setId(news.getId());
			vo.setTitle(news.getTitle());
			vo.setKeywords(StringUtils.isEmpty(news.getIntroduct())?"":news.getIntroduct());
			vo.setImageUrl(ConfigProUtils.get("file_path")+news.getImageUrl());
			vo.setFromLogoUrl(ConfigProUtils.get("file_path")+news.getFromLogoUrl());
			vo.setPraise(news.getPraise());
			vo.setClickCount(news.getClicks());
			vo.setCommentCount(newsInformationCommentsMapper.findCount(news.getId()));
			vo.setAddTime(DateUtil.showTime(news.getPublishTime(), Const.CN_YYYYMMDD));
			resultList.add(vo);
		}
		//资讯信息
		return resultList;
	}
	
	/**
	 * 通过频道ID查询资讯总记录数
	 * @version 1.0
	 * @createTime 2016-12-1,下午3:42:00
	 * @updateTime 2016-12-1,下午3:42:00
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channelId 频道ID
	 * @param period 周期阶段
	 * @return
	 */
	public int findCount(long channelId, int period) {
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", channelId);
		param.put("period", period);
		return newsInformationMapper.findCount(param);
	}
	
	/**
	 * 通过ID查询资讯信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午5:50:39
	 * @updateTime 2016-12-1,下午5:50:39
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param id 资讯ID
	 * @param userId 用户ID
	 * @return
	 */
	public NewsInformationVo findNewsInformationDetail(int id, int userId) {
		//vo返回新闻详情信息
		NewsInformationVo vo = new NewsInformationVo();
		//查询文章当天是否被阅读过
//		NewsClick newsClick = newsClickMapper.findCurrentNewsClick(id);
//		if(newsClick != null) {	//有
//			newsClick.setClicks(newsClick.getClicks()+1);
//			//更新
//			newsClickMapper.updateNewsClick(newsClick);
//		} else {//无
//			newsClick = new NewsClick();
//			newsClick.setNewsId(id);
//			newsClick.setClicks(1);
//			newsClick.setAddDate(new Date());
//			//添加
//			newsClickMapper.saveNewsClick(newsClick);
//		}
		//新闻阅读量
//		int clickCount = newsClickMapper.findNewsClick(id);
		//查询用户收藏过的文章
		boolean isCollection = false;
		NewsCollection param = new NewsCollection();
		param.setUserId(userId);
		param.setNewsInformationId(id);
		NewsCollection collection = newsCollectionMapper.findNewsCollection(param);
		if(collection!=null) {
			isCollection = true;
		}
		//新闻详情
		NewsInformation news = newsInformationMapper.findNewsInformationDetail(id);
//		news.setClicks(news.getClicks()+1);
//		//更新点击量
//		newsInformationMapper.updateNewsInformationClick(news);
		vo.setId(news.getId());
		vo.setTitle(news.getTitle());
		vo.setKeywords(news.getKeywords());
		vo.setImageUrl(ConfigProUtils.get("file_path")+news.getImageUrl());
		vo.setFromLogoUrl(ConfigProUtils.get("file_path")+news.getFromLogoUrl());
		vo.setSourceFrom(news.getSourceFrom());
		vo.setNewsUrl(ConfigProUtils.get("file_path")+news.getNewsUrl());
		vo.setClickCount(news.getClicks());
		vo.setShareCount(news.getShareNum()==null?0:news.getShareNum());
		vo.setIsFocusImage(news.getIsFocusImage());
		vo.setCollection(isCollection);
		vo.setAddTime(news.getPublishTime()!=null?new SimpleDateFormat(Const.YYYYMMDD).format(news.getPublishTime()):"");
		vo.setCommentCount(newsInformationCommentsService.findCount(id));
		return vo;
	}

	@Override
	public List<NewsCollectionVo> searchNewsInformation(String keywords,
			int page_index, int page_size) {
		Map<String, Object> param = new HashMap<String, Object>();
		int start = (page_index-1)*page_size;
		param.put("keywords", keywords);
		param.put("beginIndex", start);
		param.put("everyPage", page_size);
		List<NewsInformation> informationList = newsInformationMapper.searchNewsInformation(param);
		List<NewsCollectionVo> voList = new ArrayList<NewsCollectionVo>();
		if(informationList != null && informationList.size() > 0){
			voList = getNewsInformationVo(informationList);
		}
		
		return voList;
	}
	
	/**
	 * 组装收藏文章列表
	 * @param newsCollections
	 * @return
	 */
	private List<NewsCollectionVo> getNewsInformationVo(
			List<NewsInformation> informationList) {
		List<NewsCollectionVo> voList = new ArrayList<NewsCollectionVo>();
		if(informationList != null && informationList.size()>0){
			for (NewsInformation newsInformation : informationList) {
				NewsCollectionVo informationVo = new NewsCollectionVo();
				if(newsInformation.getId()!=null){
					//获取文章信息
					NewsInformation information = newsInformationMapper.findNewsInformationDetail(newsInformation.getId());
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
						//文章关键字
						if(StringUtils.isNotEmpty(information.getIntroduct())){
							informationVo.setKeywords(information.getIntroduct());
						}
						//来源（频道）
						if(information.getChannelId() != null){
							int channelId = information.getChannelId();
							NewsChanels newsChanels = newsChanelsMapper.findNewChannelById(channelId);
							if(newsChanels != null && StringUtils.isNotEmpty(newsChanels.getChanelName())){
								informationVo.setSourceFrom(newsChanels.getChanelName());
							}
						}
						voList.add(informationVo);
					}
				}
			}
		}
		return voList;
	}

	/**
	 * 关键字查询资讯数量
	 */
	@Override
	public Integer findNewsCountByConditions(String keyword) {
		Integer count = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keywords", keyword);
		count = newsInformationMapper.findNewsCountByConditions(param);
		return count;
	}
}
