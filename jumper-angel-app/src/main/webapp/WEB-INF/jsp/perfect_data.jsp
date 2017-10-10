<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class="ui-page-login">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>完善资料</title>
		<jsp:include page="../../top.jsp" />
	</head>

	<body>
		<input type="hidden" id="url" value="${url}" />
		<input type="hidden" id="openId" value="${openId}" />
		<input type="hidden" id="cardId" value="${cardId}" />
		<input type="hidden" id="hospitalId" value="${hospitalId}" />
		<input type="hidden" id="mobile" value="${mobile}" />
		<input type="hidden" id="token" value="${token}" />
		<div class="mui-content">
			<div style="padding: 30px 10px 0 10px;">
				<div id="segmentedControl" class="mui-segmented-control">
					<a class="mui-control-item mui-active" href="#item1" id="pregnancy">怀孕中</a>
					<a class="mui-control-item" href="#item2" id="baby">已生育</a>
				</div>
			</div>
			<div>
				<div id="item1" class="mui-control-content mui-active">
					<div id='login-form' class="mui-input-group">
						<div class="mui-input-row row-img">
							<label><img src="${pageContext.request.contextPath}/assets/images/user.png"></label>
							<input id='account' type="text" class="mui-input-clear mui-input" placeholder="请输入您的姓名">
						</div>
						<!--提示-->
						<div data-options='{"type":"date","beginYear":1990,"endYear":2060}' class="yutime mui-input-row row-img">
							<label><img src="${pageContext.request.contextPath}/assets/images/date.png"></label>
							<input  type="text" class="mui-input input-right timeTerm clicktime" id="yutime" readonly placeholder="选择您的预产期">
							<input type="button" class="yzbutton timetext" value="选择末次月经">
						</div>
						<!--提示-->
						<p class="tis" id="pregnancyTis"></p>
					</div>
					<div class="mui-content-padded">
						<button id="pregnancyBut" class="mui-btn mui-btn-block mui-btn-primary">完成</button>
					</div>
					
				</div>
				<div id="item2" class="mui-control-content">
					<div id='login-form' class="mui-input-group">
						<div class="mui-input-row row-img">
							<label><img src="${pageContext.request.contextPath}/assets/images/user.png"></label>
							<input id='name' type="text" class="mui-input-clear mui-input" placeholder="请输入您的姓名">
						</div>
						<div data-options='{"type":"date","beginYear":1990,"endYear":2060}' class="expectedDate mui-input-row row-img">
							<label><img src="${pageContext.request.contextPath}/assets/images/date.png"></label>
							<input  type="text" class="mui-input input-right clicktime" id="expectedDate" placeholder="选择原定的预产期" readonly>
						</div>
						
						<div data-options='{"type":"date","beginYear":1990,"endYear":2060}' class="mui-input-row row-img firstAsMamImpressionsTime">
							<label><img src="${pageContext.request.contextPath}/assets/images/date.png"></label>
							<input  type="text" class="mui-input input-right clicktime" id="firstAsMamImpressionsTime"  placeholder="选择宝宝的生日" readonly> 
						</div>
						<!--提示-->
						<p class="tis" id="babyTis"></p>
						<div style="width: 100%;padding-left:16%;" class="radioAge">
							<div class="mui-input-row mui-radio mui-left" style="width:39%;display: inline-block;">
								<label>女</label>
								<input name="radio1" type="radio" value="0" checked="checked">
							</div> 
							<div class="mui-input-row mui-radio mui-left" style="width:39%;display: inline-block;">
								<label>男</label>
								<input name="radio1" type="radio" value="1">
							</div> 
						</div>
					</div>
					<div class="mui-content-padded">
						<button id="babyBut" class="mui-btn mui-btn-block mui-btn-primary">完成</button>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../../assets.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/custom/perfect_data.js"></script>
	</body>

</html>