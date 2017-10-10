package com.jumper.angel.app.thirdparty.controller;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;

/**
 * 供第三方调用
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月19日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("thirdParty")
public class ThirdPartyController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录、注册用户
	 * @version v.1.0
	 * @createTime 2017年4月19日,上午11:00:48
	 * @updateTime 2017年4月19日,上午11:00:48
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param openId 第三方唯一标识
	 * @param cardId 入口ID(1:微信公众号   2:支付宝生活号)
	 * @param hospitalId 医院ID
	 * @param url 注册成功或者登录成功要跳转的地址,请使用urlencode对链接进行处理
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("loginAndRegisterUser")
	public void loginAndRegisterUser(@RequestParam("openId") String openId, @RequestParam("cardId") String cardId, 
			@RequestParam("hospitalId") String hospitalId, @RequestParam("url") String url, @RequestParam("token") String token,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		//通过openid查询用户信息
		UserInfo userInfo = userService.officialAccountsLogIn(Integer.parseInt(cardId), openId);
		//未注册
		if(null == userInfo) {
			//注册页面
			response.sendRedirect(ConfigProUtils.get("redirect_url")+"?url="+URLDecoder.decode(url, "utf-8")+"&openId="+openId+"&cardId="+cardId+"&hospitalId="+hospitalId);
		} else {
			//解码
			url = URLDecoder.decode(url, "utf-8");
			//拼接参数
			String param = "user_id="+userInfo.getId()+"&mobile="+userInfo.getMobile()+"&channle="+1+"&open_id="+openId+"&expected_date_of_confinement="+userInfo.getExpectedDateOfConfinement();
			//判断该链接是否带参数
			if(url.contains("?")) { //带参数
				url = url + "&" + param;
			} else { //不带参数
				url = url + "?" + param;
			}
			//重定向到业务模块去
			response.sendRedirect(url);
		}
	}
}
