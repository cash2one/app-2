package com.jumper.angel.app.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 商铺控制器
 * @author gyx
 * @time 2016年12月5日
 */

@Controller
@RequestMapping("shop")
public class ShopController {
	private final static Logger logger = Logger.getLogger(ShopController.class);
	
	/**
	 * 获取妈咪go商铺URL
	 * @return
	 */
	@RequestMapping(value = "findShopUrl", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取商铺URL", httpMethod = "GET", notes = "获取商铺URL")
	public ResultMsg findShopUrl(){
		try {
			String shop_url = ConfigProUtils.get("shop_url");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shop_url", shop_url);
			List list = new ArrayList();
			list.add(map);
			logger.info("妈咪Go商铺shop_url："+shop_url);
			return new ResultMsg(Const.SUCCESS, "获取商铺URL成功！", list);
		} catch (Exception e) {
			logger.error("findShopUrl()"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取商铺URL失败！");
		}
	}
}
