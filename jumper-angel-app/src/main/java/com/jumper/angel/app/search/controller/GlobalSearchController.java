package com.jumper.angel.app.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.search.model.po.FunModulePo;
import com.jumper.angel.app.search.model.po.HospitalModuleHost;
import com.jumper.angel.app.search.model.vo.FunModuleVo;
import com.jumper.angel.app.search.model.vo.TopicVo;
import com.jumper.angel.app.search.service.FunModuleService;
import com.jumper.angel.app.search.service.GlobalSearchService;
import com.jumper.angel.app.search.service.TopicService;
import com.jumper.angel.app.subscription.service.NewsInformationService;
import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.dubbo.bean.bo.DocMajorBo;
import com.jumper.dubbo.service.DoctorService;
import com.jumper.dubbo.service.HospitalService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @Description 全局搜索接口实现
 * @author 徐运贤
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */

@Controller
@RequestMapping("/globalSearch")
public class GlobalSearchController {
	private final static Logger logger = Logger.getLogger(GlobalSearchController.class);

	@Autowired
	private GlobalSearchService globalSearchService;
	
	@Autowired
	private FunModuleService funModuleService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private NewsInformationService newsInformationService;
	
	@Autowired
	private TopicService topicService;

	/**
	 * 全局搜索功能、医生、话题、文章
	 * @param keywords 关键字
	 * @param hospitalId 医院名称
	 * @return
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)///{keywords}/{hospitalId}
	@ResponseBody
	@ApiOperation(value = "全局搜索医生、话题、文章", httpMethod = "GET", notes = "全局搜索")
	public ResultMsg getHelperQuestions(
			@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam("keywords") String keywords, 
			@ApiParam(name = "hospitalId", value = "医院id", required = false) @RequestParam("hospitalId") int hospitalId) {
		try {
			if(StringUtils.isEmpty(keywords)){
				return new ResultMsg(Const.FAILED, "关键字不能为空！");
			}
			if(StringUtils.isEmpty(hospitalId+"")){
				return new ResultMsg(Const.FAILED, "医院id不能为空！");
			}
			int page_size = 4;
			int page_index = 1;
			//中文乱码处理
//			keywords = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			Map<String, Object> mapList = new HashMap<String, Object>();
			//文章
			//孕期百科文章
//			Page<HelperQuestionsBo> poList=globalSearchService.findHelperQuestionsList(keyword);
//			mapList.put("articleList", poList.getContent());
			/*if(keywords.contains("%")){
				keywords = keywords.replaceAll("\u0025", "\u005b\u0025\u005d");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("\u005f", "\u005b\u005f\u005d");
			}*/
			if(keywords.contains("%")){
				keywords = keywords.replaceAll("%", "/%");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("_", "/_");
			}
			//订阅资讯文章
			Integer countArticle = newsInformationService.findNewsCountByConditions(keywords);
			boolean isArticleMore = false;
			List<NewsCollectionVo> newsList = new ArrayList<NewsCollectionVo>();
			//数量大于默认数量就显示更多，否则不显示
			if(countArticle > Const.SEARCH_ARTICLE_COUNT){
				isArticleMore = true;
			}
			page_size = Const.SEARCH_ARTICLE_COUNT;
			newsList = newsInformationService.searchNewsInformation(keywords, page_index, page_size);
			mapList.put("newsList", newsList);
			mapList.put("isArticleMore", isArticleMore);
			
			//医生
			//调用dubbo获取医生列表
			boolean isDoctorMore = false;
			//调接口获取满足条件的总医生数
			Integer countDoctor = doctorService.getDocMajorLikeCount(keywords, hospitalId);
			if(countDoctor > Const.SEARCH_DOCTOR_COUNT){
				isDoctorMore = true;
			}
			page_size = Const.SEARCH_DOCTOR_COUNT;
			List<DocMajorBo> doctorList = doctorService.getDocMajorLike(keywords, hospitalId, page_size, page_index);
			mapList.put("doctorList", doctorList);
			mapList.put("isDoctorMore", isDoctorMore);
			
			//功能
			/*boolean isFunModuleMore = false;
			List<FunModuleVo> voList = new ArrayList<FunModuleVo>();
			List<FunModulePo> funModuleList = funModuleService.searchFunModule(keywords);
			if(funModuleList != null && funModuleList.size() > Const.SEARCH_FUN_MODULE_COUNT){
				isFunModuleMore = true;
				funModuleList = funModuleList.subList(0, Const.SEARCH_FUN_MODULE_COUNT-1);
			}
			if(funModuleList != null && funModuleList.size()>0){
				for (FunModulePo funModulePo : funModuleList) {
					if(funModulePo.getOpenModule() != null && !"".equals(funModulePo.getOpenModule())){
						//根据功能和医院判断是否开通，开通就显示出来，没开通就不显示
						Object obj = hospitalService.getServiceIsOpen(funModulePo.getOpenModule(), hospitalId);
						if(obj != null && (int)obj == 1){//筛选开通状态
							voList.add(getFunModuleVo(funModulePo)); 
						}
					}else{//不涉及到开通和不开通的也要展示
						//预约挂号（6）、高危管理（2）、母子手册（5）
						int moduleNum = 0;
						if("预约挂号".equals(funModulePo.getFunName())){
							moduleNum = 6;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else if("高危管理".equals(funModulePo.getFunName())){
							moduleNum = 2;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else if("母子手册".equals(funModulePo.getFunName())){
							moduleNum = 5;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else{
							voList.add(getFunModuleVo(funModulePo));
						}
					}
				}
			}
			mapList.put("funModuleList", voList);
			mapList.put("isFunModuleMore", isFunModuleMore);*/
			
			//话题
			boolean isTopicMore = false;
			Integer countTopic = topicService.findTopicCount(keywords);
			if(countTopic > Const.SEARCH_TOPIC_COUNT){
				isTopicMore = true;
			}
			page_size = Const.SEARCH_TOPIC_COUNT;
			List<TopicVo> topicList = topicService.searchTopicList(keywords, page_index, page_size);
			mapList.put("topicList", topicList);
			mapList.put("isTopicMore", isTopicMore);
			
			return new ResultMsg(Const.SUCCESS, "全局搜索数据成功！", mapList);
		} catch (Exception e) {
			logger.error("getHelperQuestions()", e);
			return new ResultMsg(Const.FAILED, "全局搜索数据失败！");
		}
	}

	/**
	 * 获取更多文章信息
	 * @param keywords 关键字
	 * @param page_index 分页索引
	 * @param page_size 分页大小
	 * @return 文章信息列表
	 */
	@RequestMapping(value = "/findMoreNewsInforamtion", method = RequestMethod.GET)///{keywords}/{page_index}/{page_size}
	@ResponseBody
	@ApiOperation(value = "全局搜索获取更多文章", httpMethod = "GET", notes = "全局搜索获取更多文章")
	public ResultMsg findMoreNewsInforamtion(
			@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam("keywords") String keywords, 
			@ApiParam(name="page_index", value="分页索引", required = true) @RequestParam("page_index") int page_index, 
			@ApiParam(name="page_size", value="分页大小", required = true) @RequestParam("page_size") int page_size){
		try {
			if(StringUtils.isEmpty(keywords)){
				return new ResultMsg(Const.FAILED, "关键字不能为空！");
			}
			if(StringUtils.isEmpty(page_index+"")){
				return new ResultMsg(Const.FAILED, "分页索引不能为空！");
			}
			if(StringUtils.isEmpty(page_size+"")){
				return new ResultMsg(Const.FAILED, "分页大小不能为空！");
			}
			//中文乱码处理
//			String keyword = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			//分页索引不合法默认第一页
			if(page_index <= 0){
				page_index = 1;
			}
			if(keywords.contains("%")){
				keywords = keywords.replaceAll("%", "/%");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("_", "/_");
			}
			//条件搜索文章列表
			List<NewsCollectionVo> newsList = newsInformationService.searchNewsInformation(keywords, page_index, page_size);
			if(newsList != null && newsList.size() > 0){
				return new ResultMsg(Const.SUCCESS, "获取更多文章信息成功！", newsList);
			}else{
				return new ResultMsg(Const.NODATA, "更多文章信息列表为空！", new ArrayList<NewsCollectionVo>());
			}
		} catch (Exception e) {
			logger.error("findMoreNewsInforamtion()", e);
			return new ResultMsg(Const.FAILED, "获取更多文章信息失败！");
		}
	}
	
	/**
	 * 全局搜索获取更多医生
	 * @param keywords 关键字
	 * @param hospitalId 医院id
	 * @param page_index 分页索引
	 * @param page_size 分页大小
	 * @return 医生信息列表
	 */
	@RequestMapping(value = "/findMoreDoctor", method = RequestMethod.GET)///{keywords}/{hospitalId}/{page_index}/{page_size}
	@ResponseBody
	@ApiOperation(value = "全局搜索获取更多医生", httpMethod = "GET", notes = "全局搜索获取更多医生")
	public ResultMsg findMoreDoctor(
			@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam("keywords") String keywords, 
			@ApiParam(name = "hospitalId", value = "医院id", required = false) @RequestParam("hospitalId") int hospitalId,
			@ApiParam(name="page_index", value="分页索引", required = true) @RequestParam("page_index") int page_index, 
			@ApiParam(name="page_size", value="分页大小", required = true) @RequestParam("page_size") int page_size){
		
		try {
			if(StringUtils.isEmpty(keywords)){
				return new ResultMsg(Const.FAILED, "关键字不能为空！");
			}
			if(StringUtils.isEmpty(hospitalId+"")){
				return new ResultMsg(Const.FAILED, "医院id不能为空！");
			}
			if(StringUtils.isEmpty(page_index+"")){
				return new ResultMsg(Const.FAILED, "分页索引不能为空！");
			}
			if(StringUtils.isEmpty(page_size+"")){
				return new ResultMsg(Const.FAILED, "分页大小不能为空！");
			}
			//中文乱码处理
//			String keyword = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			//分页索引不合法默认第一页
			if(page_index <= 0){
				page_index = 1;
			}
			if(keywords.contains("%")){
				keywords = keywords.replaceAll("%", "/%");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("_", "/_");
			}
			//调dubbo服务获取条件搜索医生信息
			List<DocMajorBo> doctorList = doctorService.getDocMajorLike(keywords, hospitalId, page_size, page_index);
			if(doctorList != null && doctorList.size() > 0){
				return new ResultMsg(Const.SUCCESS, "获取更多医生信息成功！", doctorList);
			}else{
				return new ResultMsg(Const.NODATA, "更多医生信息列表为空！", new ArrayList<DocMajorBo>());
			}
		} catch (Exception e) {
			logger.error("findMoreDoctor()", e);
			return new ResultMsg(Const.FAILED, "获取更多医生信息失败！");
		}
		
	}
	
	/**
	 * 全局搜索获取更多功能模块
	 * @param keywords 关键字
	 * @param hospitalId 医院id
	 * @return 功能模块信息列表
	 */
	@RequestMapping(value = "/findMoreFunModule", method = RequestMethod.GET)///{keywords}/{hospitalId}
	@ResponseBody
	@ApiOperation(value = "全局搜索获取更多功能模块", httpMethod = "GET", notes = "全局搜索获取更多功能模块")
	public ResultMsg findMoreFunModule(
			@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam("keywords") String keywords, 
			@ApiParam(name = "hospitalId", value = "医院id", required = false) @RequestParam("hospitalId") int hospitalId){
		try {
			if(StringUtils.isEmpty(keywords)){
				return new ResultMsg(Const.FAILED, "关键字不能为空！");
			}
			if(StringUtils.isEmpty(hospitalId+"")){
				return new ResultMsg(Const.FAILED, "医院id不能为空！");
			}
			//中文乱码处理
//			keywords = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			if(keywords.contains("%")){
				keywords = keywords.replaceAll("%", "/%");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("_", "/_");
			}
			//条件搜索功能模块列表
			List<FunModuleVo> voList = new ArrayList<FunModuleVo>();
			List<FunModulePo> funModuleList = funModuleService.searchFunModule(keywords);
			if(funModuleList != null && funModuleList.size()>0){
				for (FunModulePo funModulePo : funModuleList) {
					if(funModulePo.getOpenModule() != null && !"".equals(funModulePo.getOpenModule())){
						//根据功能和医院判断是否开通，开通就显示出来，没开通就不显示
						Object obj = hospitalService.getServiceIsOpen(funModulePo.getOpenModule(), hospitalId);
						if(obj != null && (int)obj == 1){//筛选开通状态
							voList.add(getFunModuleVo(funModulePo)); 
						}
					}else{//不涉及到开通和不开通的也要展示
						//预约挂号（6）、高危管理（2）、母子手册（5）
						int moduleNum = 0;
						if("预约挂号".equals(funModulePo.getFunName())){
							moduleNum = 6;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else if("高危管理".equals(funModulePo.getFunName())){
							moduleNum = 2;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else if("母子手册".equals(funModulePo.getFunName())){
							moduleNum = 5;
							HospitalModuleHost moduleHost = funModuleService.findHospitalModuleHost(hospitalId,moduleNum);
							if(moduleHost != null){
								voList.add(getFunModuleVo(funModulePo));
							}
						}else{
							voList.add(getFunModuleVo(funModulePo));
						}
					}
				}
			}
			if(voList != null && voList.size() > 0){
				return new ResultMsg(Const.SUCCESS, "获取更多功能模块信息成功！", voList);
			}else{
				return new ResultMsg(Const.NODATA, "更多功能模块信息列表为空！", new ArrayList<FunModuleVo>());
			}
		} catch (Exception e) {
			logger.error("findMoreFunModule()", e);
			return new ResultMsg(Const.FAILED, "获取更多功能模块信息失败！");
		}
		
	}
	
	/**
	 * 全局搜索获取更多话题
	 * @param keywords 关键字
	 * @param page_index 分页索引
	 * @param page_size 分页大小
	 * @return
	 */
	@RequestMapping(value = "/findMoreTopic", method = RequestMethod.GET)///{keywords}/{page_index}/{page_size}
	@ResponseBody
	@ApiOperation(value = "全局搜索获取更多话题", httpMethod = "GET", notes = "全局搜索获取更多话题")
	public ResultMsg findMoreTopic(
			@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam("keywords") String keywords, 
			@ApiParam(name="page_index", value="分页索引", required = true) @RequestParam("page_index") int page_index, 
			@ApiParam(name="page_size", value="分页大小", required = true) @RequestParam("page_size") int page_size){
		try {
			if(StringUtils.isEmpty(keywords)){
				return new ResultMsg(Const.FAILED, "关键字不能为空！");
			}
			if(StringUtils.isEmpty(page_index+"")){
				return new ResultMsg(Const.FAILED, "分页索引不能为空！");
			}
			if(StringUtils.isEmpty(page_size+"")){
				return new ResultMsg(Const.FAILED, "分页大小不能为空！");
			}
			//中文乱码处理
//			String keyword = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			//分页索引不合法默认第一页
			if(page_index <= 0){
				page_index = 1;
			}
			if(keywords.contains("%")){
				keywords = keywords.replaceAll("%", "/%");
			}
			if(keywords.contains("_")){
				keywords = keywords.replaceAll("_", "/_");
			}
			//条件搜索话题列表
			List<TopicVo> topicList = topicService.searchTopicList(keywords, page_index, page_size);
			if(topicList != null && topicList.size() > 0){
				return new ResultMsg(Const.SUCCESS, "获取更多话题信息成功！", topicList);
			}else{
				return new ResultMsg(Const.NODATA, "更多话题信息列表为空！", new ArrayList<TopicVo>());
			}
		} catch (Exception e) {
			logger.error("findMoreTopic()", e);
			return new ResultMsg(Const.FAILED, "获取更多话题信息失败！");
		}
	}
	
	/**
	 * 组装功能模块实体
	 * @param funModulePo
	 * @return
	 */
	private FunModuleVo getFunModuleVo(FunModulePo funModulePo) {
		FunModuleVo funModuleVo = new FunModuleVo();
		if(funModulePo != null){
			//功能id
			if(StringUtils.isNotEmpty(funModulePo.getFunId()+"")){
				funModuleVo.setFunId(funModulePo.getFunId());
			}
			//功能名称
			if(StringUtils.isNotEmpty(funModulePo.getFunName())){
				funModuleVo.setFunName(funModulePo.getFunName());
			}
			//功能描述
			if(StringUtils.isNotEmpty(funModulePo.getDescription())){
				funModuleVo.setDescription(funModulePo.getDescription());
			}
		}
		return funModuleVo;
	}
}
