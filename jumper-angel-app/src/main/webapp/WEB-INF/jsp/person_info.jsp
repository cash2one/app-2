<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>个人信息</title>
		<jsp:include page="../../top.jsp" />
		<style>
			.mui-table-view-cell:after{
				left:15px;
				right:15px;
			}
		</style>
	</head>
	<body>
		<input type="hidden" id="userId" value="${user.id}" />
		<input type="hidden" id="channel" value="${channel}" />
		<div class="mui-content">
			<ul class="mui-table-view" style="margin: 0;">
				<li class="mui-table-view-cell mui-media">
					<div class="ling-div">
						<img class="mui-media-object mui-pull-right" src="${userImg}">
						<div class="mui-media-object" style="height:70px;line-height: 70px;">
							${extraInfo.realName}
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell mui-media">
					<div>
						<span class="mui-media-object mui-pull-right rightcolor"><fmt:formatDate value="${user.expectedDateOfConfinement}" pattern="yyyy-MM-dd" /></span>
						<div class="mui-pull-left mui-media-object" >
							<label><img class="iconimg" src="${pageContext.request.contextPath}/assets/images/icon-yuchan.png" style="vertical-align:middle"/></label>
							<span>预产期</span>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell mui-media">
					<div>
						<span class="mui-pull-right mui-media-object rightcolor">${user.mobile}</span>
						<div class="mui-pull-left mui-media-object" style="min-width:160px;">
							<label><img class="iconimg" src="${pageContext.request.contextPath}/assets/images/icon-phone.png" style="vertical-align:middle"/></label>
							<span>手机号</span>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="mui-content-padded">
			<button id="exitBut" class="mui-btn mui-btn-block mui-btn-primary">退出登录</button>
		</div>
	</body>
	<jsp:include page="../../assets.jsp" />
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		//退出登录
		$("#exitBut").on("click", function() {
			var channel = $("#channel").val();
			if(channel == 1) {
				//支付宝 confirm
				AlipayJSBridge.call("confirm", {
				     title: "退出登录",
				     message: "是否确定退出登录？",
				     okButton: "确定",
				     cancelButton: "取消"
				}, function (result) {
					//确定
				    if(result.ok) {
				    	exitLogin();
				    }
				});
			} else if(channel == 2) {
				//微信 confirm
				mui.confirm("是否确定退出登录？", "退出登录", ["取消", "确定"], function(e) {
					if(e.index == 1) { //确认
						exitLogin();
					}
				});
			}
		});
	});
	
	//退出登录
	function exitLogin() {
		$.ajax({
			url: path + "/user/v4.1/exitLogin",
			type: "GET",
			contentType: "application/json;charset=utf-8",
			dataType: "json",//从服务器端返回的数据类型
			data:{"userId":$("#userId").val(), "channel":$("#channel").val()},
			beforeSend:function() { //请求中
				$("#exitBut").attr("disabled", true);
			},
			success: function(json) {
				var channel = $("#channel").val();
				if(json.msg == 1) {
					if(channel == 1) {
						//支付宝 退出当前H5应用
						AlipayJSBridge.call('exitApp');
					} else if(channel == 2) {
						//微信 退出当前H5应用
						wx.closeWindow();
					}
				} else {
					if(channel == 1) {
						//支付宝 toast
						AlipayJSBridge.call("toast", {
						     content: json.msgbox,
						     type: "fail",
						     duration: 2000
						}, function(){
						});
					} else if(channel == 2) {
						//微信 toast
						mui.toast(json.msgbox);
					}
				}
			},
			complete: function() { //请求完成
				$("#exitBut").attr("disabled", false);
			}
		});
	}
	</script>
</html>
