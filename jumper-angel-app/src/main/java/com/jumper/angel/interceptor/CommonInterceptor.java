package com.jumper.angel.interceptor;

//import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公共拦截器，主要拦截参数
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-29
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class CommonInterceptor implements HandlerInterceptor {
	
//	private final static Logger logger = Logger.getLogger(CommonInterceptor.class);
	
	/**
	 * 请求到控制器之前执行该方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//请求参数
		String requestParam = "";
		//获取请求方式
		String method = request.getMethod();
		if(method.equals("GET")) {
			//获取get请求中的所有参数，适合rest方式的get请求
			if(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) != null) {
				requestParam = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE).toString();
			}
		} else if(method.equals("POST")) {
	    	//获取Body中的信息
//			BufferedReader br = request.getReader();
//			String str, body = "";
//			while((str = br.readLine()) != null){
//				body += str;
//			}
//			requestParam = body;
		}
		System.out.println("请求方式："+request.getMethod()+",输出参数==>"+requestParam);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
