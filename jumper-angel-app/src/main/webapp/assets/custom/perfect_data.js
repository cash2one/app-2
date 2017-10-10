/**
 * 完善资料
 */
var status = 0;
$(document).ready(function() {
	//怀孕中
	$("body").on("tap", "#pregnancyBut", function(){
		//姓名
		var name = $("#account").val();
		if(name == "") {
			$("#pregnancyTis").html("请输入姓名");
			return;
		}
		//预产期、末次月经
		var yutime = $("#yutime").val();
		//预产期
		var expect_date = "";
		//末次月经
		var last_period = "";
		if(status == 0) {
			if(yutime == "") {
				$("#pregnancyTis").html("请输入预产期");
				return;
			} else {
				expect_date = yutime;
			}
		} else if(status == 1) {
			if(yutime == "") {
				$("#pregnancyTis").html("请输入末次月经");
				return;
			} else {
				last_period = yutime;
			}
		}
		$("#pregnancyTis").html("");
		//第三方唯一标识
		var openid = $("#openId").val();
		//渠道
		var cardId = $("#cardId").val();
		//医院ID
		var hospitalId = $("#hospitalId").val();
		//手机号码
		var mobile = $("#mobile").val();
		//当前身份(0:已怀孕，1:辣妈)
		var currentIdentity = 0;
		//参数
		var param = {
			"real_name":name,
			"expect_date":expect_date,
			"last_period":last_period,
			"open_id":openid,
			"registerChannel":cardId,
			"hospital_id":hospitalId,
			"mobile":mobile,
			"password":"123456",
			"current_identity":currentIdentity,
			"lng":0,
			"lat":0,
			"device_id":"",
			"version":"",
			"firstBind":1
		};
		//转json串
		param = JSON.stringify(param);
		//完成
		$.ajax({
			url: path + "/user/registerUser",
			type: "POST",
			contentType: "application/json;charset=utf-8",
			dataType: "json",//从服务器端返回的数据类型
			data:param,
			beforeSend:function() { //请求中
				$("#pregnancyBut").attr("disabled", true);
			},
			success: function(json) {
				if(json.msg == 1) {
					var user = json.data;
					var url = $("#url").val()+"&userid="+user.id+"&mobile="+mobile+"&channel="+cardId+"&openid="+openid+"&lmp="+user.lastPeriod;
					window.location.href=url;
				} else {
					//渠道
					var channel = $("#cardId").val();
					if(channel == 1) {
						//支付宝 提示
						AlipayJSBridge.call("toast", {
						     content: json.msgbox,
						     type: "fail",
						     duration: 2000
						}, function(){
						});
					} else if(channel == 2) {
						//微信 提示
						mui.toast(json.msgbox);
					}
				}
			},
			complete: function() { //请求完成
				$("#pregnancyBut").attr("disabled", false);
			}
		});
	});
	//已生育
	$("body").on("tap", "#babyBut", function() {
		//姓名
		var name = $("#name").val();
		if(name == "") {
			$("#babyTis").html("请输入姓名");
			return;
		} else {
			$("#babyTis").html("");
		}
		//预产期
		var expectedDate = $("#expectedDate").val();
		if(expectedDate == "") {
			$("#babyTis").html("请选择预产期");
			return;
		} else {
			$("#babyTis").html("");
		}
		//宝宝生日
		var babyBirthday = $("#firstAsMamImpressionsTime").val();
		if(babyBirthday == "") {
			$("#babyTis").html("请选择宝宝生日");
			return;
		} else {
			$("#babyTis").html("");
		}
		//宝宝性别
        var chkRadio = $('input:radio[name="radio1"]:checked').val();
        //第三方唯一标识
		var openid = $("#openId").val();
		//渠道
		var cardId = $("#cardId").val();
		//医院ID
		var hospitalId = $("#hospitalId").val();
		//手机号码
		var mobile = $("#mobile").val();
		//当前身份(0:已怀孕，1:辣妈)
		var currentIdentity = 1;
		//参数
		var param = {
			"real_name":name,
			"expect_date":expectedDate,
			"last_period":"",
			"open_id":openid,
			"registerChannel":cardId,
			"hospital_id":hospitalId,
			"mobile":mobile,
			"password":"123456",
			"current_identity":currentIdentity,
			"baby_birthday":babyBirthday,
			"baby_sex":chkRadio,
			"lng":0,
			"lat":0,
			"device_id":"",
			"version":"",
			"firstBind":1
		};
		//转json串
		param = JSON.stringify(param);
		var code = 0;
		var msgbox = "";
		var user;
		//完成
		$.ajax({
			url: path + "/user/registerUser",
			type: "POST",
			contentType: "application/json;charset=utf-8",
			dataType: "json",//从服务器端返回的数据类型
			data:param,
			beforeSend:function() { //请求中
				$("#babyBut").attr("disabled", true);
			},
			success: function(json) {
				if(json.msg == 1) {
					var user = json.data;
					var url = $("#url").val()+"&userid="+user.id+"&mobile="+mobile+"&channel="+cardId+"&openid="+openid+"&lmp="+user.lastPeriod;
					window.location.href=url;
				} else {
					//渠道
					var channel = $("#cardId").val();
					if(channel == 1) {
						//支付宝 提示
						AlipayJSBridge.call("toast", {
						     content: json.msgbox,
						     type: "fail",
						     duration: 2000
						}, function(){
						});
					} else if(channel == 2) {
						//微信 提示
						mui.toast(json.msgbox);
					}
				}
			},
			complete: function() { //请求完成
				$("#babyBut").attr("disabled", false);
			}
		});
	});
});
var pStatus = 0;
//怀孕中
$("body").on("tap", "#pregnancy", function() {
	pStatus = 0;
});
//已生育
$("body").on("tap", "#baby", function() {
	pStatus = 1;
});

mui("body").on("tap",".clicktime",function(){
	var rel_id=$(this).attr("id");
	var result = $('#'+rel_id)[0];
	var btns = $('.'+rel_id);
	(function($, doc) {
	/* 	$.init(); */
		btns.each(function(i, btn) {
				var optionsJson = this.getAttribute('data-options') || '{}';
				var options = JSON.parse(optionsJson);
				var id = this.getAttribute('id');
				var picker = new $.DtPicker(options);
				picker.show(function(rs) {
					var date = new Date();
					//怀孕中
					if(pStatus == 0) {
						//预产期
						if(status == 0) {
							//当前时间
							var crrDate = date.toLocaleDateString().replace("/", "-").replace("/", "-");
							//选择的时间
							var selectDate = rs.text;
							var d1 = new Date(crrDate.replace(/\-/g, "\/"));  
							var d2 = new Date(selectDate.replace(/\-/g, "\/"));  
							if(d1 > d2) {
								mui.toast("预产期不能小于当前时间，请重新选择");
								return;
							}
							date.setDate(date.getDate()+280);
							//预产期最后一天
							var time = date.Format("yyyy-MM-dd");
							var d3 = new Date(time.replace(/\-/g, "\/"));
							if(d2 > d3) {
								mui.toast("选择的时间不能大于预产期，请重新选择");
								return;
							}
						} else if(status == 1) { //末次月经
							//当前时间
							var crrDate = date.toLocaleDateString().replace("/", "-").replace("/", "-");
							//选择的时间
							var selectDate = rs.text;
							var d1 = new Date(crrDate.replace(/\-/g, "\/"));  
							var d2 = new Date(selectDate.replace(/\-/g, "\/"));  
							if(d1 < d2) {
								mui.toast("末次月经不能大于当前时间，请重新选择");
								return;
							}
							date.setDate(date.getDate()-280);
							//末次月经最后一天
							var time = date.Format("yyyy-MM-dd");
							var d3 = new Date(time.replace(/\-/g, "\/"));
							if(d2 < d3) {
								mui.toast("选择的时间不能小于末次月经，请重新选择");
								return;
							}
						}
					} else if(pStatus) { //已生育
						//当前时间
						var crrDate = date.toLocaleDateString().replace("/", "-").replace("/", "-");
						//选择的时间
						var selectDate = rs.text;
						var d1 = new Date(crrDate.replace(/\-/g, "\/"));  
						var d2 = new Date(selectDate.replace(/\-/g, "\/"));  
						if(d1 < d2) {
							mui.toast("原预产期不能大于当前时间，请重新选择");
							return;
						}
					}
					//alert(date.toLocaleDateString().replace("/", "-").replace("/", "-"));
					result.value = rs.text;
				});
		});
	})(mui);
});

Date.prototype.Format = function (fmt) {  
    var o = {  
        "M+": this.getMonth() + 1, //月份   
        "d+": this.getDate(), //日   
        "h+": this.getHours(), //小时   
        "m+": this.getMinutes(), //分   
        "s+": this.getSeconds(), //秒   
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度   
        "S": this.getMilliseconds() //毫秒   
    };  
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o)  
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
    return fmt;  
}  

var valbutton=true;//切换末次月经选择的判断 
mui("body").on("tap",".yzbutton",function(){
	$("#yutime").val("");
	if(valbutton){
		status = 1;
		$(".timeTerm").attr("placeholder", "选择末次月经");
		$(".timetext").val("选择您的预产期");
		valbutton=!valbutton;
	}else{
		status = 0;
		$(".timeTerm").attr("placeholder", "选择您的预产期");
		$(".timetext").val("选择末次月经");
		valbutton=!valbutton;
	}
});