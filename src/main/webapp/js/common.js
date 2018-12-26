layui.define(['layer','form'], function(exports){
  var $ = layui.$;
  var layer = layui.layer;
  var form = layui.form;
  var param={
      error_msg:"网站请求出现问题啦"
  };
  
  //自定义验证规则
	form.verify({
	    username:[/[0-9a-zA-Z_]{5,14}/,'用户名需要5-14个字符，只能是数字、字母、下划线']
	    ,password: [/^[0-9A-Za-z_@]{6,14}$/, '用户密码 6-14个字符 ,只能是字母、数字、下划线、@']
		,password1:  [/^$|(^[0-9A-Za-z_@]{6,14}$)/, '用户密码 6-14个字符 ,只能是字母、数字、下划线、@']
	    ,email1:[/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,"邮箱格式不正确"]
	    ,samepas:function(value){
	    	var p1 = $("[name=password_r]").val();
	    	var p2 = $("[name=password_2_r]").val();
	    	if(p1 != p2){
	    		return '两次密码不一致'
	    	}
	    }
		,nickname:function(value){
			if(value.trim().length>15){
				return "昵称不能超过15个字符";
			}
		}
	    ,usernamerepeat:function(value){//校验用户名是否重复
	    	var msg = "";
	    	 $.ajax({
		    	  url:basePath+"/user/usernamerepeat",
		    	  type:"post",
		    	  async: false, 
		    	  data:{
		    		  user_username:value.trim()
		    	  },
		    	  success:function(result){
		    		 if(result.status == "ok" && result.code == 0){
		    			 msg = "用户名已存在";
		    		 } 
		    	  },
		    	  error:function(result){
		    		  msg =  "用户名存在校验失败";
		    	  }
		      });
	    	 if(msg != ""){
	    		 return msg;
	    	 }
	    }
	});
  
  //个人修改个人资料
  var editor_diaolog;
  $("#editor_person").click(function(){
	  editor_diaolog = layer.open({
			type:"1",
			title:"编辑用户",
			area:['400px','350px'],
			content:$("#common_usermsgdialog_div")
		});
	  $.ajax({
			url:basePath+"/user/getUserUser",
			type:"post",
			datatype:"json",
			data:{
			},
			success:function(data){
				$("#userusername").val(data.user.user_username);
				$("#usernickname").val(data.user.user_nickname);
				$("#useremail").val(data.user.user_email);
				$("#userpassword").val('');
				form.render('select','usertypeUFilter1'); 
				form.render('select','userstatusUFilter1'); 
			}
		});
  });
  form.on('submit(editor_update)', function(data){
		 var user_nickname = $("#usernickname").val().trim();
		 var user_email= $("#useremail").val().trim();
		 var user_password = $("#userpassword").val().trim();
		 $.ajax({
			url:basePath+"/user/updateUserEditor",
			type:"post",
			data:{
				user_nickname:user_nickname,
				user_email:user_email,
				user_password:user_password
			},
			datatype:"json",
			success:function(data){
				if("ok" ==data.status){
					layer.msg(data.msg);
					layer.close(editor_diaolog);
					return;
				}else{
					layer.alert(data.msg);
				}
			}
		});
		return false;
	  });
  
  param.backToTop=function(classname){
	  $(window).scroll(function () {
	         if ($(window).scrollTop() > 100) {
	             $("."+classname).fadeIn(500);
	         }
	         else {
	        	 $("."+classname).fadeOut(500);
	         }
	     });
	   $("."+classname).click(function(){
			$('body,html').animate({scrollTop: 0}, 500); 
		});
  }
  
  param.ajaxSetUp=function(){
	  $.ajaxSetup( {
			//设置ajax请求结束后的执行动作
		    complete :
		        function(XMLHttpRequest, textStatus) {
		    		// 通过XMLHttpRequest取得响应头，sessionstatus
		            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		            if (sessionstatus == "TIMEOUT") {
		            	layer.confirm('登录超时了，重新登录?', function(index){
		            		var win = window;
			                while (win != win.top){
			                    win = win.top;
			                }
			                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
		            		  
		            		  layer.close(index);
		            	});  
		            }
		        }
		});
  }
  
  param.getUrlParam = function(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
  }
  
  exports('common', param);
});  

