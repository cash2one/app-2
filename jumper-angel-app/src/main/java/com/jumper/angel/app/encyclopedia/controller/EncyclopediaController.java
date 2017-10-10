package com.jumper.angel.app.encyclopedia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestionClass;
import com.jumper.angel.app.encyclopedia.service.HelperQuestionClassService;
import com.jumper.angel.app.encyclopedia.service.HelperQuestionsService;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionClass;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionInfo;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 孕期百科控制器
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-29
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("encyclopedia")
public class EncyclopediaController {
	
	private final static Logger logger = Logger.getLogger(EncyclopediaController.class);
	
	@Autowired
	private HelperQuestionClassService helperQuestionClassService;
	
	@Autowired
	private HelperQuestionsService helperQuestionsService;
	
	/**
	 * 获取孕期百科类型列表
	 * @return
	 */
	@RequestMapping(value="findPrenatalWikiTypes", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="孕期百科类型列表", httpMethod="GET", notes="获取孕期百科类型")
	public ResultMsg findPrenatalWikiTypes(){
		try{
			//获取父类型列表
			List<HelperQuestionClass> list = helperQuestionClassService.getAllQuestionClass();
			if(list == null || list.size() == 0){
				return new ResultMsg(Const.NODATA, "常见问题类型列表为空！", new ArrayList<HelperQuestionClass>());
			}
			//获取封装类型列表
			List<VOHelperQuestionClass> voList = helperQuestionClassService.getVOHelperQuestionClass(list);
	        return new ResultMsg(Const.SUCCESS, "获取常见问题类型列表成功！", voList);
		}catch(Exception e){
			logger.error("findPrenatalWikiTypes()", e);
			return new ResultMsg(Const.FAILED, "获取孕期百科类型列表失败！");
		}
	}
	/**
	 * 获取孕期百科问题分页数据
	 * @param type_id
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	@RequestMapping(value="findQuestionList/{type_id}/{page_index}/{page_size}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="孕期百科问题列表", httpMethod="GET", notes="获取孕期百科问题")
	public ResultMsg findQuestionList(
			@ApiParam(name="type_id", value="问题类型id", required = true) @PathVariable("type_id") int type_id, 
			@ApiParam(name="page_index", value="分页索引", required = true) @PathVariable("page_index") int page_index, 
			@ApiParam(name="page_size", value="分页大小", required = true) @PathVariable("page_size") int page_size){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			if(page_index <= 0){
				page_index = 1;
			}
			//起始记录索引
			int start = (page_index-1)*page_size;
			map.put("type_id", type_id);
			map.put("start", start);
			map.put("page_size", page_size);
			//获取分页封装数据
			List<VOHelperQuestionInfo> voList = helperQuestionsService.findQuestionList(map);
			if(voList == null || voList.size() == 0){
				return new ResultMsg(Const.NODATA, "孕期百科常见问题列表为空！", new ArrayList<VOHelperQuestionInfo>());
			}
			return new ResultMsg(Const.SUCCESS, "获取常见问题列表成功！", voList);
		}catch(Exception e){
			logger.error("findQuestionList()", e);
			return new ResultMsg(Const.FAILED, "获取孕期百科问题列表失败！");
		}
	}
	
	
	
}
