package com.jumper.angel.app.bindhospital.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumper.angel.app.bindhospital.entity.BindHospitalLog;
import com.jumper.angel.app.bindhospital.service.BindHospitalLogService;
import com.jumper.angel.app.bindhospital.vo.VOCityInfo;
import com.jumper.angel.app.bindhospital.vo.VOProvinceInfo;
import com.jumper.angel.app.bindhospital.vo.VOSimpleHospitalInfo;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.WebRequestUtils;
import com.jumper.core.service.impl.IdWorkerImpl;
import com.jumper.dubbo.bean.po.AddrCity;
import com.jumper.dubbo.bean.po.HospitalInfo;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.HospitalService;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 关联（切换）医院控制器
 * @author gyx
 * @time 2016年12月2日
 */
@Controller
@RequestMapping("hospital")
public class BindHospitalController {
	private final static Logger logger = Logger.getLogger(BindHospitalController.class);
	//天使医院id
	private static Integer ANGELSOUND_HOSPITAL_ID = 49;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IdWorkerImpl idWorkerImpl;
	
	@Autowired
	private BindHospitalLogService bindHospitalLogService;
	
	/**
	 * 根据用户id获取用户关联医院信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="findCommonHospital/{userId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="获取关联医院", httpMethod="GET", notes="获取关联医院")
	public ResultMsg findCommonHospital(@ApiParam(name="userId", value="用户id", required=true)@PathVariable("userId") long userId){
		try{
			if(StringUtils.isEmpty(userId+"") || !StringUtils.isNumeric(userId+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空!");
			}
			//用户扩展信息
			UserExtraInfo userExtraInfo = userService.getExtraUserInfoByUserId((int)userId);
			if(userExtraInfo == null || userExtraInfo.getCommonHospital() == null){
				return new ResultMsg(Const.SUCCESS, "没有关联医院!");
			}
			//医院信息
			HospitalInfo hospitalInfo = hospitalService.getHospitalInfoById(userExtraInfo.getCommonHospital());
			if(hospitalInfo == null){
				return new ResultMsg(Const.SUCCESS, "医院不存在！");
			}
			//封装医院信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", hospitalInfo.getId());
			if(StringUtils.isNotEmpty(hospitalInfo.getName())){
				map.put("name", hospitalInfo.getName());
			}
			return new ResultMsg(Const.SUCCESS, "获取用户关联医院成功！", map);
		}catch(Exception e){
			logger.error("findCommonHospital()", e);
			return new ResultMsg(Const.FAILED, "获取用户关联医院失败！");
		}
	}
	
	/**
	 * 根据城市获取医院列表
	 * @return
	 */
	@RequestMapping(value="findHospitalListByCity/{city_name}/{open_type}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据城市获取医院列表", httpMethod="GET", notes="根据城市获取医院列表")
	public ResultMsg findHospitalListByCity(@ApiParam(name="city_name", value="城市名称", required=true)@PathVariable("city_name") String city_name, 
			@ApiParam(name="open_type", value="是否开通(不传或者传0:全部医院,1:已开通的医院,2:已开通胎心监护的医院)", required=true)@PathVariable("open_type") int open_type){
		try{
			if(StringUtils.isEmpty(city_name)){
//				return new ResultMsg(Const.FAILED, "城市名称不能为空！");
				city_name = "深圳市";
			}
//			String cityName = new String(city_name.getBytes("ISO-8859-1"), "utf-8");
//			String cityName = URLDecoder.decode(city_name, "UTF-8");
			//通过城市名称查询城市信息
			AddrCity city = new AddrCity();
			city.setCityname(city_name);
			List<AddrCity> citys = userService.getCityInfoByCondition(city);
			if(citys == null || citys.size() == 0){
	        	return new ResultMsg(Const.FAILED, "城市不存在");
	        }
			//医院的开通类型
			if(StringUtils.isNotEmpty(open_type+"")){
				if(!StringUtils.isNumeric(open_type+"") || open_type<0 || open_type>2){
					return new ResultMsg(Const.FAILED, "非法的医院开通服务类型！");
				}
			}else{
				open_type = 0;//为空就默认为0 
			}
			//通过城市id和开通类型获取医院列表
			List<HospitalInfo> hospitalList = hospitalService.getInfoByProvinceAndCity(0, citys.get(0).getId(), open_type);
			//已开通医院列表加入天使医院
			if(open_type == 1){
	        	 HospitalInfo angelsoundHospital = hospitalService.getHospitalInfoById(ANGELSOUND_HOSPITAL_ID);
	 	        if(angelsoundHospital != null){
	 	        	hospitalList.add(angelsoundHospital);
	 	        }
	        }
			if(hospitalList == null || hospitalList.size() == 0){
				return new ResultMsg(Const.NODATA, "该城市的医院列表为空！", new ArrayList<VOSimpleHospitalInfo>());
			}
			//组装医院的信息
			List<VOSimpleHospitalInfo> voList = new ArrayList<VOSimpleHospitalInfo>();
			voList = getVOSimpleHospitalInfo(hospitalList);
			return new ResultMsg(Const.SUCCESS, "获取城市医院列表成功！", voList);
		}catch(Exception e){
			logger.error("findHospitalListByCity()", e);
			return new ResultMsg(Const.FAILED, "获取城市医院列表失败！");
		}
		
	}
	

	/**
	 * 查询所有的省市
	 * @param open_type
	 * @return
	 */
	@RequestMapping(value="findCountry/{open_type}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="获取所有省市列表", httpMethod="GET", notes="获取所有省市列表")
	public ResultMsg findCountry(@ApiParam(name="open_type", value="医院开通服务类型 （不传或者传0:全部医院,1:已开通的医院）", required=true)@PathVariable("open_type") int open_type){
		try{
			if(StringUtils.isNotEmpty(open_type+"")){
				if(!StringUtils.isNumeric(open_type+"") || open_type<0 || open_type>1){
					return new ResultMsg(0, "非法的医院开通服务类型！");
				}
			}else{
				open_type = 0;//为空就默认为0 
			}
			//读取省份城市json文件返回
			String path = servletContext.getRealPath("/");
			logger.info("the real context path:"+path);
			String cnJson = path + "json" + File.separator + "china.province.json";
			logger.info("the china province and city path:"+cnJson);
			File jsonFile = new File(cnJson);
			if(!jsonFile.exists()){
				return new ResultMsg(0, "get.china.province.fail");
			}
			ObjectMapper mapper = new ObjectMapper();
			List<VOProvinceInfo> proList = mapper.readValue(jsonFile, List.class);
			//获取热门城市
			List<HospitalInfo> list = hospitalService.findHotCityByHospital(open_type);
			//组装热门城市
			List<VOProvinceInfo> hotCityList = copy(list);
			//将热门城市作为全国城市列表的第一个元素返回
			hotCityList.addAll(proList);
			return new ResultMsg(1, "获取省市列表成功！", hotCityList);
		}catch(Exception e){
			logger.error("findCountry()", e);
			return new ResultMsg(0, "获取省市列表失败！");
		}
	}
	
	/**
	 * 根据关键字搜索医院
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="searchHospital", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据关键字搜索医院", httpMethod="GET", notes="根据关键字搜索医院")
	public ResultMsg searchHospital(@ApiParam(name="keyword", value="医院关键词（模糊匹配，全部返回，不分页）", required=true)@RequestParam("keyword") String keyword){
		try{
			if(StringUtils.isBlank(keyword)){
					return new ResultMsg(0, "医院关键字不能为空！");
			}
//			String decode = URLDecoder.decode(keyword);
//			String keyWords = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
			//根据关键字查询医院列表
			List<HospitalInfo> hospitalList = hospitalService.searchOpenHospital(keyword);
			//组装医院信息
			List<VOSimpleHospitalInfo> voList = new ArrayList<VOSimpleHospitalInfo>();
			voList = getVOSimpleHospitalInfo(hospitalList);
			return new ResultMsg(1, "搜索医院成功！", voList);
		}catch(Exception e){
			logger.error("searchHospital()", e);
			return new ResultMsg(0, "搜索医院失败！");
		}
	}
	/**
	 * 关联医院
	 * @updateTime 2016-12-13
	 * @updateAuthor qinxiaowei
	 * @updateInfo 新增绑定医院流水记录
	 * @param user_id
	 * @param hospital_id
	 * @param mobile_type
	 * @param device_id
	 * @param version
	 * @return
	 */
	@RequestMapping(value="bindHospital/{user_id}/{hospital_id}/{lng}/{lat}/{mac}/{version}/{mobileType}/{firstBind}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="关联医院", httpMethod="GET", notes="关联医院")
	public ResultMsg bindHospital(@ApiParam(name="user_id", value="用户id", required=true)@PathVariable("user_id") long user_id,
			@ApiParam(name="hospital_id", value="医院ID（传0默认关联天使医院）", required=true)@PathVariable("hospital_id") int hospital_id,
			@ApiParam(name="lng", value="经度", required=true)@PathVariable("lng") double lng,
			@ApiParam(name="lat", value="纬度", required=true)@PathVariable("lat") double lat,
			@ApiParam(name="mac", value="手机mac地址", required=true)@PathVariable("mac") String mac,
			@ApiParam(name="version", value="版本号名称", required=true)@PathVariable("version") String version,
			@ApiParam(name="mobileType", value="手机类型(0：android 1：ios)", required=true)@PathVariable("mobileType") int mobileType,
			@ApiParam(name="firstBind", value="是否首次绑定(0：非首次  1：首次)", required=true)@PathVariable("firstBind") int firstBind,
			HttpServletRequest request){
		try{
			if(StringUtils.isBlank(user_id+"")){
				return new ResultMsg(0, "请输入用户id");
			}
			if(StringUtils.isBlank(hospital_id+"")){
				return new ResultMsg(0, "请输入医院id");
			}
			//设备信息
			ObjectMapper mapper = new ObjectMapper();
			String deviceInfo = WebRequestUtils.getDeviceInformation("device_name", mapper, request);
			if(StringUtils.isBlank(deviceInfo)){
				deviceInfo = WebRequestUtils.getDeviceInformation("device-name", mapper, request);
			}
			//用户信息
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			//用户拓展信息
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId((int)user_id);
			if(userInfo == null){
				logger.info("user doesn't exist!");
				return new ResultMsg(0, "用户不存在！");
			}
			//医院id为0，默认绑定天使医院
			if(hospital_id == 0){
				hospital_id = ANGELSOUND_HOSPITAL_ID;
			}
			//绑定医院流水
			BindHospitalLog log = new BindHospitalLog();
			log.setUserId(user_id);
			log.setMobileIp(WebRequestUtils.getIpAddr(request));
			log.setLng(lng);
			log.setLat(lat);
			log.setMobileMac(mac);
			log.setVersionName(version);
			log.setMobileType(mobileType);
			log.setFirstBinding(firstBind);
			//添加绑定医院流水记录
			bindHospitalLogService.addBindHospitalLog(log, userExtra.getCommonHospital()!=null?userExtra.getCommonHospital():0, hospital_id);
			//调用绑定医院接口
			HospitalInfo hosInfo = new HospitalInfo();
			hosInfo.setId(hospital_id);
			hosInfo.addRequestParam("user_id", user_id);
			hosInfo.addRequestParam("deviceInfo", deviceInfo);
			boolean b = hospitalService.updateUserCommonHospital(hosInfo);
			if(b){
				//如果绑定成功，返回绑定医院信息
				HospitalInfo hospitalInfo = hospitalService.getHospitalInfoById(hospital_id);
				List<HospitalInfo> hospitalList = new ArrayList<HospitalInfo>();
				hospitalList.add(hospitalInfo);
				List<VOSimpleHospitalInfo> voHospitalList = getVOSimpleHospitalInfo(hospitalList);
				logger.info("bind hospital success,user_id:"+user_id + ",hosptial_id:"+hospital_id+",hospital_name:"+hospitalInfo.getName());
				return new ResultMsg(1, "关联医院成功！", voHospitalList);
			}else{
				logger.info("bind hospital fail,user_id:"+user_id + ",hosptial_id:"+hospital_id);
				return new ResultMsg(0, "关联医院失败！");
			}
		}catch(Exception e){
			logger.error("setCommonHospital()", e);
			return new ResultMsg(0, "关联医院失败！");
		}
	}
	
	
	
	/**
	 * 组装医院的id和name
	 * @param hospitalList
	 * @return
	 */
	private List<VOSimpleHospitalInfo> getVOSimpleHospitalInfo(
			List<HospitalInfo> hospitalList) {
		List<VOSimpleHospitalInfo> voList = new ArrayList<VOSimpleHospitalInfo>();
		for(HospitalInfo hospital : hospitalList){
        	VOSimpleHospitalInfo vo = new VOSimpleHospitalInfo();
        	vo.setId(hospital.getId());
        	if(hospital.getName() != null){
        		vo.setName(hospital.getName());
        	}else{
        		vo.setName("");
        	}
        	voList.add(vo);
        }
		return voList;
	}
	
	/**
	 * 组装热门城市
	 * @param list
	 * @return
	 */
	private List<VOProvinceInfo> copy(List<HospitalInfo> list) {
		VOProvinceInfo hotCity = new VOProvinceInfo();
		List<VOCityInfo> city_list = new ArrayList<VOCityInfo>();
		if(list != null && list.size()>0){
			for(HospitalInfo hospital:list){
				VOCityInfo info = new VOCityInfo();
				if(hospital.getCityId() != null){
					AddrCity citys = new AddrCity();
					citys.setId(hospital.getCityId());
					List<AddrCity> cityes = userService.getCityInfoByCondition(citys);
					if(cityes != null && cityes.size()>0){
						AddrCity city = cityes.get(0);
						info.setId(city.getId());
						info.setAbbrev(city.getAbbrev());
						info.setCity_name(city.getCityname());
					}
					
				}
				if(hospital.getProvinceId() != null){
					info.setProvince_id(hospital.getProvinceId());
				}
				city_list.add(info);
			}
		}
		hotCity.setId(0);
		hotCity.setAbbrev("R");
		hotCity.setProvince_name("热门城市");
		hotCity.setCity_list(city_list);
		List<VOProvinceInfo> hotCityList = new ArrayList<VOProvinceInfo>();
		hotCityList.add(hotCity);
		return hotCityList;
	}
}
