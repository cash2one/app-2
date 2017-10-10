package com.jumper.angel.app.healthymanage.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.healthymanage.service.PregnantHealthySettingService;
import com.jumper.angel.app.healthymanage.vo.VOPregnantHealthyProject;
import com.jumper.angel.app.healthymanage.vo.VOPregnantHealthySetting;
import com.jumper.angel.frame.common.MonitorProject;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.dubbo.bean.bo.PregnantHealthySettingBo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 健康管理控制器
 * @author gyx
 * @time 2016年12月9日
 */
@Controller
@RequestMapping("healthymanage")
public class HealthyManageController {
	private final static Logger logger = Logger.getLogger(HealthyManageController.class);
	
	@Autowired
	private PregnantHealthySettingService pregnantHealthySettingService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 获取健康管理首页数据
	 * @return
	 */
	@RequestMapping(value = "getHomePage/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取健康管理首页数据", httpMethod = "GET", notes = "获取健康管理首页数据")
	public ResultMsg getHomePage(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			//用户信息，判断用户是否存在
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户不存在！");
			}
			//获取用户管理工具数据
			List<PregnantHealthySettingBo> settingList = pregnantHealthySettingService.findUserPregnantHealthySetting((int)user_id);
			if(settingList == null || settingList.size() == 0){
				return new ResultMsg(Const.NODATA, "健康管理首页数据为空！", new ArrayList<VOPregnantHealthySetting>());
			}
			//调用监测的接口获取剩余的数据
			List<PregnantHealthySettingBo> voSettingsList = userService.getHomePage((int)user_id, settingList);
			
			return new ResultMsg(Const.SUCCESS, "获取健康管理首页数据成功！", voSettingsList);
		} catch (Exception e) {
			logger.error("getHomePage()", e);
			return new ResultMsg(Const.FAILED, "获取健康管理首页数据失败！");
		}
	}
	
	/**
	 * 获取健康工具列表
	 * @param user_id
	 * @param is_contain_urine
	 * @return
	 */
	@RequestMapping(value = "findTools/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取健康工具列表", httpMethod = "GET", notes = "获取健康工具列表")
	public ResultMsg findTools(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			//用户信息，判断用户是否存在
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户不存在！");
			}
			//获取用户管理工具数据
			List<PregnantHealthySettingBo> settingList = pregnantHealthySettingService.findUserPregnantHealthySetting((int)user_id);
			//封装所有监测类型，并默认为关闭状态
			List<VOPregnantHealthyProject> projectList = getVOPregnantHealthyProject();
			if(settingList != null && settingList.size() > 0){
				for (VOPregnantHealthyProject voPregnantHealthyProject : projectList) {
					for (PregnantHealthySettingBo voPregnantHealthySetting : settingList) {
						//如果用户添加了该项目，就标记为启用状态
						if(voPregnantHealthyProject.getId() == voPregnantHealthySetting.getProject()){
							voPregnantHealthyProject.setState(0);
						}
					}
				}
			}
			return new ResultMsg(Const.SUCCESS, "获取健康工具列表成功！", projectList);
		} catch (Exception e) {
			logger.error("getHomePage()", e);
			return new ResultMsg(Const.FAILED, "获取健康工具列表失败！");
		}
	}
	
	/**
	 * 添加或减少健康工具
	 * @param user_id
	 * @param project
	 * @return
	 */
	@RequestMapping(value = "updateTool/{user_id}/{project}/{flag}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "添加或减少健康工具", httpMethod = "GET", notes = "添加或减少健康工具")
	public ResultMsg updateTool(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id,
			@ApiParam(name = "project", value = "项目id(测试类型1.胎心；2.血氧；3.心率；4.体温；5.体重；6.血压；7.血糖；8.胎动,9.尿液,10.心电，11.血脂)", required = true) 
				@PathVariable("project") int project, 
			@ApiParam(name = "flag", value = "添加或减少监测数据(0:开启，1：禁用)", required = true) @PathVariable("flag") int flag){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			//用户信息，判断用户是否存在
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户不存在！");
			}
			if(StringUtils.isEmpty(project+"")){
				return new ResultMsg(Const.FAILED, "请输入项目！");
			}
			//修改数据
			boolean b = pregnantHealthySettingService.updateTool((int)user_id, project, flag);
			if(b){
				if(flag == 0){
					return new ResultMsg(Const.SUCCESS, "添加健康工具成功！");
				}else{
					return new ResultMsg(Const.SUCCESS, "删除健康工具成功！");
				}
			}else{
				if(flag == 0){
					return new ResultMsg(Const.SUCCESS, "添加健康工具失败！");
				}else{
					return new ResultMsg(Const.SUCCESS, "删除健康工具失败！");
				}
			}
		} catch (Exception e) {
			logger.error("getHomePage()", e);
			return new ResultMsg(Const.FAILED, "添加或减少健康工具失败！");
		}
	}
	
	/**
	 * 从枚举获取数据组装健康工具列表
	 * @param is_contain_urine
	 * @return
	 */
	private List<VOPregnantHealthyProject> getVOPregnantHealthyProject(){
		List<VOPregnantHealthyProject> projectList = new ArrayList<VOPregnantHealthyProject>();
		//获取枚举列表
		MonitorProject[] projectLists = MonitorProject.values();
		for (int i = 0; i < projectLists.length; i++) {
			//不需要尿液则跳过
			VOPregnantHealthyProject voProject = new VOPregnantHealthyProject();
			//项目id
			voProject.setId(projectLists[i].getType());
			//项目名称
			voProject.setName(projectLists[i].getName());
			//监控状态(0：开启，1：关闭)，默认关闭
			voProject.setState(1);
			projectList.add(voProject);
		}
		return projectList;
	}
	
}
