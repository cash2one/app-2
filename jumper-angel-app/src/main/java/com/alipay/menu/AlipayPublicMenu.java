package com.alipay.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenPublicMenuCreateModel;
import com.alipay.api.domain.AlipayOpenPublicMenuModifyModel;
import com.alipay.api.domain.ButtonObject;
import com.alipay.api.request.AlipayOpenPublicMenuCreateRequest;
import com.alipay.api.request.AlipayOpenPublicMenuModifyRequest;
import com.alipay.api.request.AlipayOpenPublicMenuQueryRequest;
import com.alipay.api.response.AlipayOpenPublicMenuCreateResponse;
import com.alipay.api.response.AlipayOpenPublicMenuModifyResponse;
import com.alipay.api.response.AlipayOpenPublicMenuQueryResponse;
import com.alipay.factory.AlipayAPIClientFactory;

import net.sf.json.JSONObject;

/**
 * 服务窗菜单相关接口调用示例
 * 
 * @author liliang
 *
 */
public class AlipayPublicMenu {

	// 创建服务窗菜单信息
	public static void createMenu() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayOpenPublicMenuCreateRequest request = new AlipayOpenPublicMenuCreateRequest();
		AlipayOpenPublicMenuCreateModel model = new AlipayOpenPublicMenuCreateModel();
		List<ButtonObject> list = new ArrayList<ButtonObject>();
		ButtonObject button = new ButtonObject();
		button.setName("测试");
		button.setActionType("link");
		button.setActionParam("www.baidu.com");
		list.add(button);
		model.setButton(list);
		request.setBizModel(model);
		AlipayOpenPublicMenuCreateResponse response = null;
		try {
			 response = alipayClient.execute(request);
			 System.out.println(response.getBody());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 查询服务窗菜单信息
	public static void queryMenu() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayOpenPublicMenuQueryRequest request = new AlipayOpenPublicMenuQueryRequest();
		AlipayOpenPublicMenuQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
			System.out.println(response.getBody());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 修改服务窗菜单信息
	public static void modifyMenu() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayOpenPublicMenuModifyRequest request = new AlipayOpenPublicMenuModifyRequest();
		AlipayOpenPublicMenuModifyModel model = new AlipayOpenPublicMenuModifyModel();
		List<ButtonObject> list = new ArrayList<ButtonObject>();
		ButtonObject button = new ButtonObject();
		button.setName("测试");
		button.setActionType("link");
		button.setActionParam("www.baidu.com");
		list.add(button);
		model.setButton(list);
		request.setBizModel(model);
		AlipayOpenPublicMenuModifyResponse response = null;
		try {
			 response = alipayClient.execute(request);
			 System.out.println(response.getBody());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		createMenu();
		//queryMenu();
//		modifyMenu();
	}
}
