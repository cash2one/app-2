package com.jumper.angel.app.order.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.jumper.anglesound.dubbo.BaseDataResult;
import cn.com.jumper.anglesound.dubbo.DubboPaymentService;

import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.dubbo.bean.po.HospitalInfo;
import com.jumper.dubbo.service.HospitalService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 我的订单控制器
 * @author gyx
 * @time 2016年12月8日
 */
@Controller
@RequestMapping("order")
public class OrderController {
	private final static Logger logger = Logger.getLogger(OrderController.class);
	
//	@Autowired
//	private NetworkOrderInfoService networkOrderInfoService;
	
	@Autowired
	private DubboPaymentService  dubboPaymentService;
	@Autowired
	private HospitalService hospitalService;
	
	/**
	 * 我的订单获取订单显示类型（哪些类型有订单）
	 * @param user_id 用户id
	 * @return 订单类型列表
	 */
	@RequestMapping(value = "isExistsOrder/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "判断有哪些订单类型需要显示", httpMethod = "GET", notes = "获取订单类型")
	public ResultMsg isExistOrder(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			List<Map<String, Object>> orderTypeMapList = new ArrayList<Map<String, Object>>();
			//调用冠男dubbo获取订单类型
			BaseDataResult result = dubboPaymentService.findOrderServiceType((int)user_id);
			orderTypeMapList = (List<Map<String, Object>>) result.getData();
			if(orderTypeMapList != null && orderTypeMapList.size() > 0){
				return new ResultMsg(Const.SUCCESS, "获取订单类型列表成功！", orderTypeMapList); 
			}
			//调用网络诊室dubbo获取订单类型，判断订单是否存在（网络诊室暂时不要）
			/*Integer count = networkOrderInfoService.countMyOrder((int)user_id);
			if(count > 0){
				Map<String, Object> orderTypeMap = new HashMap<String, Object>();
				orderTypeMap.put("type", OrderType.NETWORK_ORDER.getType());
				orderTypeMap.put("name", OrderType.NETWORK_ORDER.getName());
				orderTypeMapList.add(orderTypeMap);
			}*/
			return new ResultMsg(Const.NODATA, "订单类型列表为空！", new ArrayList<Map<String, Object>>());
		} catch (Exception e) {
			logger.error("isExistOrder():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取订单类型列表失败！");
		}
	}
	
	
	/**
	 * 获取网络诊室的订单（没有与支付系统对接，暂时不要）
	 * @param user_id 用户id
	 * @param page_index 分页索引
	 * @param page_size 分页大小
	 * @return
	 */
	/*@RequestMapping(value = "findNetinteOrders/{user_id}/{page_index}/{page_size}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取网络诊室的订单", httpMethod = "GET", notes = "获取网络诊室的订单列表")
	public ResultMsg findNetinteOrders(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id,
			@ApiParam(name = "page_index", value = "分页索引", required = true) @PathVariable("page_index") long page_index, 
			@ApiParam(name = "page_size", value = "分页大小", required = true) @PathVariable("page_size") long page_size){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			//查询订单参数封装
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userid", (int)user_id);
			param.put("page", page_index);
			param.put("limit", page_size);
			//调用网络诊室dubbo获取订单列表信息
			Map<String, Object> resultMap = networkOrderInfoService.selectMyOrderInfo(param);
			resultList.add(resultMap);
			return new ResultMsg(Const.SUCCESS, "获取网络诊室的订单列表成功！", resultList);
		} catch (Exception e) {
			logger.error("findNetinteOrders():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取网络诊室的订单列表失败！");
		}
	}*/
	
	/**
	 * 义乌妇幼订单类型列表
	 * @param user_id 用户id
	 * 
	 * @return
	 */
	@RequestMapping(value = "v1.01/isExistsOrder/{user_id}/{hospital_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "判断有哪些订单类型需要显示", httpMethod = "GET", notes = "获取订单类型")
	public ResultMsg isExistOrderV101(@ApiParam(name = "user_id", value = "用户id", required = true) @PathVariable("user_id") long user_id,
			@ApiParam(name = "hospital_id", value = "医院id", required = true) @PathVariable("hospital_id") int hospital_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			if(StringUtils.isEmpty(hospital_id+"")){
				return new ResultMsg(Const.FAILED, "医院id不能为空！");
			}
			HospitalInfo hospitalInfo = hospitalService.getHospitalInfoById(hospital_id);
			if(hospitalInfo == null){
				return new ResultMsg(Const.FAILED, "医院id错误！");
			}
			List<Map<String, Object>> orderTypeMapList = new ArrayList<Map<String, Object>>();
			//调用冠男dubbo获取订单类型
			BaseDataResult result = dubboPaymentService.findOrderServiceType((int)user_id);
			orderTypeMapList = (List<Map<String, Object>>) result.getData();
			List<Map<String, Object>> yiwuTypeMapList = new ArrayList<Map<String,Object>>();
			String hospitalKey = hospitalInfo.getHospitalKey();
			if((orderTypeMapList == null || orderTypeMapList.size() == 0) && 
					StringUtils.isNotEmpty(hospitalKey) && ConfigProUtils.get("yiwu_hospital_key").equals(hospitalKey)){
				Map<String, Object> leaseMap = new HashMap<String, Object>();
				leaseMap.put("serviceType", 7);
				leaseMap.put("serviceName", "设备租赁");
				leaseMap.put("leaseOrderUrl", ConfigProUtils.get("lease_order_url_yiwu")+"&userid="+user_id);
				orderTypeMapList.add(leaseMap);
			}
			if(orderTypeMapList != null && orderTypeMapList.size() > 0){
				//义乌妇幼
				if(StringUtils.isNotEmpty(hospitalKey) && ConfigProUtils.get("yiwu_hospital_key").equals(hospitalKey)){
					for (Map<String, Object> map : orderTypeMapList) {
						if("胎心监护".equals(map.get("serviceName"))){
							yiwuTypeMapList.add(map);
						}
						/*if("设备租赁".equals(map.get("serviceName"))){
							map.put("leaseOrderUrl", ConfigProUtils.get("lease_order_url_yiwu")+"&userid="+user_id);
							yiwuTypeMapList.add(map);
						}*/
					}
					//义乌妇幼默认写死设备租赁查询订单入口
					Map<String, Object> leaseMap = new HashMap<String, Object>();
					leaseMap.put("serviceType", 7);
					leaseMap.put("serviceName", "设备租赁");
					leaseMap.put("leaseOrderUrl", ConfigProUtils.get("lease_order_url_yiwu")+"&userid="+user_id);
					yiwuTypeMapList.add(leaseMap);
					if(yiwuTypeMapList != null && yiwuTypeMapList.size() > 0){
						return new ResultMsg(Const.SUCCESS, "获取订单类型列表成功！", yiwuTypeMapList); 
					}
				}else{
					for (Map<String, Object> map : orderTypeMapList) {
						if("设备租赁".equals(map.get("serviceName"))){
							map.put("leaseOrderUrl", ConfigProUtils.get("lease_order_url_tsys")+"&userId="+user_id);
						}
					}
					return new ResultMsg(Const.SUCCESS, "获取订单类型列表成功！", orderTypeMapList); 
				}
			}
			return new ResultMsg(Const.NODATA, "订单类型列表为空！", new ArrayList<Map<String, Object>>());
		} catch (Exception e) {
			logger.error("isExistOrder():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取订单类型列表失败！");
		}
	}
	
	
	
}
