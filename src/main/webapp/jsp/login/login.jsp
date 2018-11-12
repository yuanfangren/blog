<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台登录</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>/css/back/back_common.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js"></script>
<style type="text/css">
.login_register_c{
	width: 400px;
	height: 300px;
	position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -200px;
    margin-top: -150px;
}
.layui-tab-title{
	text-align: center;
}
</style>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var pageNum=1;//当前页
var pageSize=10;//每页大小
layui.use(['layer','form'],function(){
	var layer = layui.layer;
	var form = layui.form;
 	var $ = layui.$;
 	$(function(){
 		//自定义验证规则
	  	form.verify({
		    username:[/[0-9a-zA-Z_]{5,14}/,'用户名需要5-14个字符，只能是数字、字母、下划线']
		    ,password: [/^[0-9A-Za-z_@]{6,14}$/, '用户密码 6-14个字符 ,只能是字母、数字、下划线、@']
		    ,email1:[/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,"邮箱格式不正确"]
		    ,samepas:function(value){
		    	var p1 = $("[name=password_r]").val();
		    	var p2 = $("[name=password_2_r]").val();
		    	if(p1 != p2){
		    		return '两次密码不一致'
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
			    		 if(result.status == "ok"){
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
	  	//注册
	   	form.on('submit(register)', function(data){
		      
		      $.ajax({
		    	  url:basePath+"/user/register",
		    	  type:"post",
		    	  data:{
		    		  user_username:data.field.username_r.trim(),
		    		  user_password:data.field.password_r,
		    		  user_email:data.field.email_r
		    	  },
		    	  success:function(result){
		    		 if(result.status == "ok"){
		    			 layer.msg(result.result+" 请登录 ");
		    			 $("#login_li_id").trigger("click");
		    			 $("#register_reset_id").trigger("click");
		    		 }else{
			    		  layer.alert(result.result);
			    	  }
		    	  }
		      });
		      
		      return false;
	    });
 		
 		//登录
	    form.on('submit(login)', function(data){
		      $.ajax({
		    	  url:basePath+"/user/login",
		    	  type:"post",
		    	  data:{
		    		  user_username:data.field.username.trim(),
		    		  user_password:data.field.password
		    	  },
		    	  success:function(result){
		    		  if(result.status == "ok"){
		    			  window.location.href=basePath+"/jsp/article/articleList.jsp"
		    		  }else{
		    			  layer.alert(result.msg);
		    		  }
		    	  },
		    	  error:function(){
		    		  layer.alert("登录请求失败");
		    	  }
		      });
		      
		      return false;
	    });
 		
 	});
});
</script>
</head>
<body>
	<div class="layui-tab layui-tab-brief login_register_c" lay-filter="docDemoTabBrief">
	  <ul class="layui-tab-title">
	    <li class="layui-this" id="login_li_id">登录</li>
	    <li>注册</li>
	  </ul>
	  <div class="layui-tab-content">
	  	<div class="login_div_c layui-tab-item layui-show">
			<form class="layui-form" action="">
			  <div class="layui-form-item">
			    <label class="layui-form-label">用户名</label>
			    <div class="layui-input-inline">
			      <input type="text" name="username" required  lay-verify="required|username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">密码</label>
			    <div class="layui-input-inline">
			      <input type="password" name="password" required lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="login" >登录</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			  </div>
			</form>
		</div>	
		<div class="layui-tab-item">
			<form class="layui-form" action="">
			  <div class="layui-form-item">
			    <label class="layui-form-label">用户名</label>
			    <div class="layui-input-inline">
			      <input type="text" name="username_r" required  lay-verify="required|username|usernamerepeat" placeholder="请输入用户名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">密码</label>
			    <div class="layui-input-inline">
			      <input type="password" name="password_r" required lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">重复密码</label>
			    <div class="layui-input-inline">
			      <input type="password" name="password_2_r" required lay-verify="required|password|samepas" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">邮箱</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email_r" required  lay-verify="required|email1" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="register">注册</button>
			      <button type="reset" class="layui-btn layui-btn-primary" id="register_reset_id">重置</button>
			    </div>
			  </div>
			</form>
		</div>
	  </div>
	</div>   
	
	
	
	
</body>
</html>