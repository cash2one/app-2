package com.jumper.angel.app.subscription.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.subscription.entity.NewsInformationComments;
import com.jumper.angel.app.subscription.service.NewsCollectionService;
import com.jumper.angel.app.subscription.service.NewsInformationCommentsService;
import com.jumper.angel.app.subscription.service.NewsInformationService;
import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.app.subscription.vo.NewsInformationCommentsVo;
import com.jumper.angel.app.subscription.vo.NewsInformationVo;
import com.jumper.angel.app.subscription.vo.NewsUserInfoVo;
import com.jumper.angel.frame.common.Page;
import com.jumper.angel.frame.common.PageUtil;
import com.jumper.angel.frame.common.Result;
import com.jumper.angel.frame.util.ComparatorList;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * 订阅Controller
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("subscribe")
public class SubscribeController {
	
	private final static Logger logger = Logger.getLogger(SubscribeController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewsInformationService newsInformationService;
	
	@Autowired
	private NewsInformationCommentsService newsInformationCommentsService;
	
	@Autowired
	private NewsCollectionService newsCollectionService;
	
	/**
	 * 新闻列表
	 * @version 1.0
	 * @createTime 2016-12-1,下午6:23:31
	 * @updateTime 2016-12-1,下午6:23:31
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param channelId 频道ID
	 * @param pageIndex 页数
	 * @param everyPage 每页显示的记录数
	 * @param period 孕期阶段
	 * @param hospitalId 医院ID
	 * @return
	 */
	@RequestMapping(value="findSubscribe/{channelId}/{period}/{pageIndex}/{everyPage}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="查询新闻列表信息", httpMethod="GET", notes="查询新闻列表信息")
	public ResultMsg findSubscribe(@ApiParam(name="channelId", value="频道ID", required = true) @PathVariable("channelId") long channelId,
			@ApiParam(name="period", value="孕期阶段", required = true) @PathVariable("period") int period,
			@ApiParam(name="pageIndex", value="页数", required = true) @PathVariable("pageIndex") int pageIndex,
			@ApiParam(name="everyPage", value="每页显示的记录数", required = true) @PathVariable("everyPage") int everyPage) {
		try {
			//实例化Page对象
			Page page = new Page();
			//设置第几页
			page.setCurrentPage(pageIndex);
			//每页显示的记录数
			page.setEveryPage(everyPage);
			//总记录数
			int count = newsInformationService.findCount(channelId, period);
			page = PageUtil.createPage(page, count);
			//频道信息
			List<NewsInformationVo> list = newsInformationService.findNewsInformationByChannelId(channelId, page.getBeginIndex(), page.getEveryPage(), period);
			//分页结果
			Result result = new Result(page, list);
			if(result.getContent()!=null && result.getContent().size()>0) {
				return new ResultMsg(Const.SUCCESS, "获取新闻信息成功！", result.getContent());
			} else {
				return new ResultMsg(Const.NODATA, "请求的数据为空！", new ArrayList<>());
			}
		} catch (Exception e) {
			logger.error("findSubscribe()", e);
			return new ResultMsg(Const.FAILED, "获取新闻信息失败！");
		}
	}
	
	/**
	 * 查询新闻详情
	 * @version 1.0
	 * @createTime 2016-12-2,下午2:01:20
	 * @updateTime 2016-12-2,下午2:01:20
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsId 新闻ID
	 * @return
	 */
	@RequestMapping(value="findSubscribeDetail/{newsId}/{userId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="查询新闻详情", httpMethod="GET", notes="查询新闻详情")
	public ResultMsg findSubscribeDetail(@ApiParam(name="newsId", value="新闻ID", required = true) @PathVariable("newsId") int newsId,
			@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") int userId) {
		try {
			//新闻详情
			NewsInformationVo vo = newsInformationService.findNewsInformationDetail(newsId, userId);
			return new ResultMsg(Const.SUCCESS, "获取新闻详情成功！", vo);
		} catch (Exception e) {
			logger.error("findSubscribeDetail()", e);
			return new ResultMsg(Const.FAILED, "获取新闻详情失败！");
		}
	}
	
	/**
	 * 查询评论列表
	 * @version 1.0
	 * @createTime 2016-12-2,下午5:53:12
	 * @updateTime 2016-12-2,下午5:53:12
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsId 新闻ID
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value="findComment/{newsId}/{userId}/{pageIndex}/{everyPage}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="查询评论列表", httpMethod="GET", notes="查询评论列表")
	public ResultMsg findComment(@ApiParam(name="newsId", value="新闻ID", required = true) @PathVariable("newsId") int newsId,
			@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") int userId,
			@ApiParam(name="pageIndex", value="页数", required = true) @PathVariable("pageIndex") int pageIndex,
			@ApiParam(name="everyPage", value="每页显示的记录数", required = true) @PathVariable("everyPage") int everyPage) {
		try {
			//实例化Page对象
			Page page = new Page();
			//设置第几页
			page.setCurrentPage(pageIndex);
			//每页显示的记录数
			page.setEveryPage(everyPage);
			//总记录数
			int count = newsInformationCommentsService.findCount(newsId);
			page = PageUtil.createPage(page, count);
			//评论信息
			List<NewsInformationCommentsVo> list = newsInformationCommentsService.findCommentsByNewsInformationId(newsId, page.getBeginIndex(), page.getEveryPage(), userId);
			//分页结果
			Result result = new Result(page, list);
			for(int i=0; i<result.getContent().size(); i++) {
				NewsInformationCommentsVo vo = (NewsInformationCommentsVo) result.getContent().get(i);
				//用户信息
				UserInfo user = userService.getAllUserInfoByUserId(vo.getUserId());
				NewsUserInfoVo uVo = new NewsUserInfoVo();
				uVo.setId(user.getId());
				uVo.setNickName(user.getNickName());
				uVo.setUserImg(ConfigProUtils.get("file_path")+user.getUserImg());
				vo.setUser(uVo);
				vo.setCommentCount(count);
			}
			return new ResultMsg(Const.SUCCESS, "获取评论列表成功！", list);
		} catch (Exception e) {
			logger.error("findComment()", e);
			return new ResultMsg(Const.FAILED, "获取评论列表失败！");
		}
	}
	
	/**
	 * 添加评论
	 * @version 1.0
	 * @createTime 2016-12-5,下午4:48:24
	 * @updateTime 2016-12-5,下午4:48:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="saveComments", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="添加评论", httpMethod="POST", notes="添加评论")
	public ResultMsg saveComments(@ApiParam(name="param", value="评论json数据", required = true) @RequestBody NewsInformationComments comments,
			HttpServletRequest request) {
		try {
			comments.setAddTime(new Date());
			comments.setPraise(0);
			//添加
			newsInformationCommentsService.addComments(comments);
			return new ResultMsg(Const.SUCCESS, "评论成功！");
		} catch (Exception e) {
			logger.error("saveComments()", e);
			return new ResultMsg(Const.FAILED, "评论失败！");
		}
	}
	
	/**
	 * 点赞
	 * @version 1.0
	 * @createTime 2016-12-5,下午5:28:48
	 * @updateTime 2016-12-5,下午5:28:48
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value="commentPraise/{commentId}/{userId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="点赞", httpMethod="GET", notes="点赞")
	public ResultMsg commentPraise(@ApiParam(name="commentId", value="评论ID", required = true) @PathVariable("commentId") int commentId,
			@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") int userId) {
		try {
			//点赞
			newsInformationCommentsService.updateComments(commentId, userId);
			return new ResultMsg(Const.SUCCESS, "点赞成功！");
		} catch (Exception e) {
			logger.error("commentPraise()", e);
			return new ResultMsg(Const.FAILED, "点赞失败！");
		}
	}
	
	/**
	 * 收藏
	 * @version 1.0
	 * @createTime 2016-12-5,下午6:34:28
	 * @updateTime 2016-12-5,下午6:34:28
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param newsId 文章ID
	 * @param userId 用户ID
	 * @param flag 0:添加收藏  1:取消收藏
	 * @return
	 */
	@RequestMapping(value="collection/{newsId}/{userId}/{flag}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="收藏", httpMethod="GET", notes="收藏")
	public ResultMsg collection(@ApiParam(name="newsId", value="新闻ID", required = true) @PathVariable("newsId") int newsId,
			@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") int userId,
			@ApiParam(name="flag", value="操作状态", required = true) @PathVariable("flag") int flag) {
		try {
			int result = newsCollectionService.collection(userId, newsId, flag);
			if(result == 1) {
				return new ResultMsg(Const.SUCCESS, "取消收藏成功！");
			} else if(result == 2) {
				return new ResultMsg(Const.SUCCESS, "收藏成功！");
			} else {
				return new ResultMsg(Const.FAILED, "收藏失败！");
			}
		} catch(Exception e) {
			logger.error("collection()", e);
			return new ResultMsg(Const.FAILED, "收藏失败！");
		}
	}
	
	/**
	 * 获取喜欢列表
	 * @param user_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "findNewsCollection/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value =  "获取喜欢列表",httpMethod = "GET", notes = "收藏的文章和话题")
	public ResultMsg findNewsCollection(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			//获取收藏文章列表
			List<NewsCollectionVo> newsCollections = newsCollectionService.findNewsCollectionsByUserId((int)user_id);
			//获取收藏的话题列表
			List<NewsCollectionVo> topicCollectionVo = newsCollectionService.findTopicCollectionsByUserId((int)user_id);
			
			//两个进行合并，并且按照时间排序
			newsCollections.addAll(topicCollectionVo);
			ComparatorList compares = new ComparatorList();
			Collections.sort(newsCollections, compares);
			return new ResultMsg(Const.SUCCESS, "获取喜欢列表成功！", newsCollections);
		} catch (Exception e) {
			logger.error("findNewsCollection()", e);
			return new ResultMsg(Const.FAILED, "获取喜欢列表失败！");
		}
	}
	
	
}
