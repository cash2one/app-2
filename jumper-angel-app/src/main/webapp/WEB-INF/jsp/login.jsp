<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class="ui-page-login">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>用户登录</title>
		<jsp:include page="../../top.jsp" />
	</head>
	<body>
		<input type="hidden" id="url" value="${url}" />
		<input type="hidden" id="openId" value="${openId}" />
		<input type="hidden" id="cardId" value="${cardId}" />
		<input type="hidden" id="hospitalId" value="${hospitalId}" />
		<input type="hidden" id="token" value="${token}" />
		<input type="hidden" id="redirectUrl" value="${redirectUrl}" />
		<div id="bg" style="display: none;"></div>
		<div class="mui-content">
			<div id='login-form' class="mui-input-group">
				<div class="mui-input-row row-img">
					<label><img src="${pageContext.request.contextPath}/assets/images/phone@2x.png"></label>
					<input id='mobile' type="text" class="mui-input-clear mui-input" placeholder="请输入您的手机号" maxlength="11">
				</div>
				<!--提示-->
				<p class="tis" id="mobileTis"></p>
				<div class="mui-input-row row-img">
					<label><img src="${pageContext.request.contextPath}/assets/images/yanz.png"></label>
					<input  type="text" class="mui-input input-right" id="code" placeholder="请输入验证码" maxlength="6">
					<input type="button" class="yzbutton" value="获取验证码">
				</div>
				<!--提示-->
				<p class="tis" id="codeTis"></p>
			</div>
			<!--<div class="mui-input-group">
				<ul class="mui-table-view mui-table-view-chevron">
					<li class="mui-table-view-cell">
						自动登录
						<div id="autoLogin" data-options='{"type":"date","beginYear":1990,"endYear":2060}' class="firstAsMamImpressionsTime">
							<div class="" id="firstAsMamImpressionsTime">sss</div>
						</div>
					</li>
				</ul>
			</div>-->
			<div class="mui-content-padded">
				<button id='login' class="mui-btn mui-btn-block mui-btn-primary">登录</button>
				<!--<div class="link-area"><a id='reg'>注册账号</a> <span class="spliter">|</span> <a id='forgetPassword'>忘记密码</a>
				</div>-->
			</div>
			<div class="mui-content-padded oauth-area">

			</div>
		</div>
		<jsp:include page="../../assets.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/custom/login.js"></script>
	</body>
</html>