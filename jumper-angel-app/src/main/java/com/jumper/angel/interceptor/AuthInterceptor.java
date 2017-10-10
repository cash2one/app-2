package com.jumper.angel.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.jumper.common.web.interceptor.AbstractAuthInterceptor;
import com.jumper.dubbo.service.AuthKeyService;
/**
 * 身份验证
 * @Description TODO
 * @author xunianchong
 * @date 2016-11-25
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class AuthInterceptor extends AbstractAuthInterceptor {
	
	@Autowired 
	private AuthKeyService authKeyService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	protected String getAccessKeyByUserId(Integer userId) {
		return authKeyService.getKeyByUserId(userId);
	}

	@Override
	protected String getAccessKeyByDoctorId(Integer doctorId) {
		return authKeyService.getKeyByDoctorId(doctorId);
	}

	@Override
	protected String getAccessKeyByProId(Integer proId) {
		return authKeyService.getKeyByProId(proId);
	}
}
