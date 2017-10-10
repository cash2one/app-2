package com.jumper.angel.app.subscription.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.subscription.entity.NewsCommentPraise;
import com.jumper.angel.app.subscription.entity.NewsInformationComments;
import com.jumper.angel.app.subscription.mapper.NewsCommentPraiseMapper;
import com.jumper.angel.app.subscription.mapper.NewsInformationCommentsMapper;
import com.jumper.angel.app.subscription.service.NewsInformationCommentsService;
import com.jumper.angel.app.subscription.vo.NewsInformationCommentsVo;
import com.jumper.angel.frame.util.DateUtil;
/**
 * 评论ServiceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class NewsInformationCommentsServiceImpl implements NewsInformationCommentsService {
	
	@Autowired
	private NewsInformationCommentsMapper newsInformationCommentsMapper;
	
	@Autowired
	private NewsCommentPraiseMapper newsCommentPraiseMapper;
	
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
	 * @return
	 */
	public List<NewsInformationCommentsVo> findCommentsByNewsInformationId(int newsInformationId, int beginIndex, int everyPage, int userId) {
		//返回
		List<NewsInformationCommentsVo> result = new ArrayList<NewsInformationCommentsVo>();
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newsId", newsInformationId);
		param.put("beginIndex", beginIndex);
		param.put("everyPage", everyPage);
		param.put("mobileSequence", userId);
		//评论信息
		List<NewsInformationComments> list = newsInformationCommentsMapper.findCommentsByNewsInformationId(param);
		for(int i=0; i<list.size(); i++) {
			NewsInformationComments comments = list.get(i);
			NewsInformationCommentsVo vo = new NewsInformationCommentsVo();
			vo.setId(comments.getId());
			vo.setParentId(comments.getParentId());
			vo.setContent(comments.getContent());
			vo.setIsFocusImage(comments.getIsFocusImage());
			vo.setAddTime(DateUtil.showTime(comments.getAddTime(), "yyyy年MM月dd日"));
			vo.setPraiseCount(comments.getPraise());
			vo.setUserId(comments.getUserId());
			param.put("commentId", comments.getId());
			//查询用户是否点赞了该评论
			List<NewsCommentPraise> praise = newsCommentPraiseMapper.findNewsCommentPraise(param);
			if(praise!=null && praise.size()>0) {
				vo.setPraiseState(false);
			} else {
				vo.setPraiseState(true);
			}
			result.add(vo);
		}
		return result;
	}
	
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
	public int findCount(int newsInformationId) {
		return newsInformationCommentsMapper.findCount(newsInformationId);
	}
	
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
	public void addComments(NewsInformationComments comments) {
		newsInformationCommentsMapper.addComments(comments);
	}
	
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
	public void updateComments(int id, int userId) {
		NewsCommentPraise praise = new NewsCommentPraise();
		praise.setMobileSequence(userId+"");
		praise.setCommentId(id);
		praise.setAddTime(new Date());
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobileSequence", praise.getMobileSequence());
		param.put("commentId", praise.getCommentId());
		//查询用户是否点赞
		List<NewsCommentPraise> list = newsCommentPraiseMapper.findNewsCommentPraise(param);
		//如果用户没点赞就添加点赞数据       该判断防止用户对评论点赞速度过快
		if(list == null) { 
			//添加用户点赞记录
			newsCommentPraiseMapper.saveNewsCommentPraise(praise);
		}
		//通评论ID查询评论信息
		NewsInformationComments comments = newsInformationCommentsMapper.findCommentsById(id);
		comments.setPraise(comments.getPraise()+1);
		//更新
		newsInformationCommentsMapper.updateComments(comments);
	}
}
