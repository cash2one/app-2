/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.servlet.auth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.constants.AlipayServiceEnvConstants;
import com.alipay.factory.AlipayAPIClientFactory;
import com.alipay.util.RequestUtil;


/**
 * 用户信息共享（网页授权获取用户信息）
 * 
 * @author taixu.zqq
 * @version $Id: LoginAuthServlet.java, v 0.1 2014年7月25日 下午5:13:03 taixu.zqq Exp $
 */
public class LoginAuthServlet {

    /**
     * 业务处理
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
  
    public static String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String result = "";
        //1. 解析请求参数
        Map<String, String> params = RequestUtil.getRequestParams(request);
        //2. 获得authCode
        String authCode = params.get("auth_code");
//        ReturnMsg msg = new ReturnMsg();
        try {
            //3. 利用authCode获得authToken
            AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
            oauthTokenRequest.setCode(authCode);
            oauthTokenRequest.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
            AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient
                .execute(oauthTokenRequest);
            result = oauthTokenResponse.getBody();
        } catch (AlipayApiException alipayApiException) {
            //自行处理异常
            alipayApiException.printStackTrace();
        }
        return result;
    }
    /**
	 * 使用HttpServletResponse 输出响应结果
	 * @param res
	 * @param content
	 */
	public static void renderString(final HttpServletResponse res, final String content) {
		res.setHeader("ContentType", "text/json");  
		res.setContentType("text/plain;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(content);
			res.getWriter().flush();
			res.getWriter().close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
