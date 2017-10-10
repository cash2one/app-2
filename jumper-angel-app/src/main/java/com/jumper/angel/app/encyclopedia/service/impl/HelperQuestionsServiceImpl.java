package com.jumper.angel.app.encyclopedia.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestions;
import com.jumper.angel.app.encyclopedia.mapper.HelperQuestionsMapper;
import com.jumper.angel.app.encyclopedia.service.HelperQuestionsService;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionInfo;
import com.jumper.angel.frame.util.ConfigProUtils;
/**
 * 孕期百科问题Service
 * @author gyx
 * @time 2016年12月1日
 */
@Service
@Transactional
public class HelperQuestionsServiceImpl implements HelperQuestionsService {
	
	@Autowired
	private HelperQuestionsMapper helperQuestionsMapper;
	
	/**
	 * 通过问题类型获取问题分页数据
	 */
	@Override
	public List<VOHelperQuestionInfo> findQuestionList(Map<String, Object> map) {
		//获取百科问题数据
		List<HelperQuestions> questionsList = helperQuestionsMapper.findQuestionList(map);
		//组装百科问题数据
		if(questionsList!=null&&questionsList.size()>0){
			List<VOHelperQuestionInfo> voList = new ArrayList<VOHelperQuestionInfo>();
			for (HelperQuestions helperQuestions : questionsList) {
				VOHelperQuestionInfo vo = getVOHelperQuestionInfo(helperQuestions);
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	public VOHelperQuestionInfo getVOHelperQuestionInfo(HelperQuestions question){
		VOHelperQuestionInfo vo = new VOHelperQuestionInfo();
		vo.setId(question.getId());
		//设置问题标题
		if(question.getTitle() != null){
			vo.setTitle(question.getTitle());
		}else{
			vo.setTitle("");
		}
		//设置图片地址
		if(StringUtils.isNotEmpty(question.getImgUrl())){
			if(!question.getImgUrl().contains("http")){
				vo.setImgUrl(ConfigProUtils.get("file_path") + question.getImgUrl());
			}else{
				vo.setImgUrl(question.getImgUrl());
			}
		}else{
			vo.setImgUrl("");
		}
		//设置详情地址
		if(StringUtils.isNotEmpty(question.getDetailsUrl())){
			if(!question.getDetailsUrl().contains("http")){
				vo.setDetailsUrl(ConfigProUtils.get("file_path") + question.getDetailsUrl());
			}else{
				vo.setDetailsUrl(question.getDetailsUrl());
			}
		}else{
			vo.setDetailsUrl("");
		}
		//设置问题介绍
		if(StringUtils.isNotEmpty(question.getQuestionIntorduction())){
			vo.setIntroduction(question.getQuestionIntorduction());
		}else{
			vo.setIntroduction("");
		}
		
		return vo;
	}
	
}
