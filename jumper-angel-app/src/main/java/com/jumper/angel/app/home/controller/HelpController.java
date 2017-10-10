package com.jumper.angel.app.home.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * 帮助模块控制器
 * @author gyx
 * @time 2017年4月6日
 */
@Controller
@RequestMapping("help")
public class HelpController {
	
	@RequestMapping(value="getPushUrl", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取推送URL", httpMethod = "GET", notes = "获取推送URL")
	public ResultMsg getPushUrl(
			@ApiParam(name = "type", value = "推送类型（16：心电；17：体重营养）", required = true)@RequestParam("type")int type, 
			@ApiParam(name = "id", value = "id（类型为体重营养时则为用户id）", required = true)@RequestParam("id")int id, 
			@ApiParam(name = "hospitalId", value = "医院id（类型为体重营养时必填）")@RequestParam(value="hospitalId", required=false)Integer hospitalId, 
			@ApiParam(name = "userName", value = "用户名（类型为体重营养时必填）")@RequestParam(value="userName", required=false)String userName){
		if(StringUtils.isEmpty(type+"")){
			return new ResultMsg(Const.FAILED, "类型不能为空!");
		}
		if(StringUtils.isEmpty(id+"")){
			return new ResultMsg(Const.FAILED, "id不能为空!");
		}
		String url = "";
		switch(type){
		case 16:
			String dataUrl = URLEncoder.encode(ConfigProUtils.get("ecgreport_data_url")+"?id="+id);
			url = ConfigProUtils.get("ecgreport_url")+"?url="+dataUrl;
			break;
		case 17:
			if(StringUtils.isEmpty(hospitalId+"")){
				return new ResultMsg(Const.FAILED, "医院id不能为空!");
			}
			if(StringUtils.isEmpty(userName)){
				return new ResultMsg(Const.FAILED, "用户名不能为空!");
			}
			url = ConfigProUtils.get("chat_weight_url")+"/chat/weight/0/"+hospitalId+"/"+id+"/"+userName+"/1";
			break;
		default:
			break;
		}
		if(StringUtils.isEmpty(url)){
			return new ResultMsg(Const.FAILED, "无法获取推送URL，参数值有误！");
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("url", url);
		return new ResultMsg(Const.SUCCESS, "获取推送URL成功！", map);
	}
}
