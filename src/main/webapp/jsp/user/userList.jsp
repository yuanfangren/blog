<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理列表</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/layui_config.js"></script>
<style type="text/css">
.tool_c{
	margin-top:10px;
	margin-left: 10px;
}
.layuiopen_c{
	display: none;
}
.adduserForm_c{
	padding-right: 10px;
	padding-top: 10px;
}
</style>
</head>
<body>
<div >
<%@ include file = "../home.jspt" %>
	<div class="tool_c">
		<button id="adduser_id" class="layui-btn layui-btn-primary layui-btn-sm">新增用户</button>
		<button id="deleteuser_id" class="layui-btn layui-btn-primary layui-btn-sm">删除用户</button>
	</div>
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<td><input class='thead_checkbox_c' type="checkbox"></td>
					<td>用户ID</td>
					<td>用户名</td>
					<td>昵称</td>
					<td>邮箱</td>
					<td>用户创建时间</td>
					<td>用户更新时间</td>
					<td>操作</td>
				</tr>
			</thead>
			
			<tbody id="userList_tbody_id">
			
			</tbody>
		</table>
		
		<div id="page"></div>
	</div>
	
	<div id="adduserDialog_id" class="layuiopen_c">
		<form action="" class="layui-form adduserForm_c">
			<div class="layui-form-item">
		    	<label class="layui-form-label">用户名</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_username" id="user_username" required  lay-verify="required|username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">昵称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_nickname" id="user_nickname"   lay-verify="nickname" placeholder="请输入昵称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
		    	<label class="layui-form-label">密码</label>
		    	<div class="layui-input-block">
		      		<input type="password" name="user_password" id="user_password" required  lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">用户邮箱</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_email" id="user_email" required  lay-verify="required|email1" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="adduserform">新增</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			 </div>
		</form>
	</div>
	
	<div id="updateuserDialog_id" class="layuiopen_c">
		<form action="" class="layui-form adduserForm_c">
			<input type="hidden" id="user_id_u">
			<div class="layui-form-item">
		    	<label class="layui-form-label">用户名</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_username_u" id="user_username_u" required readonly="readonly" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">用户昵称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_nickname_u" id="user_nickname_u"   lay-verify="nickname" placeholder="请输入昵称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">密码</label>
		    	<div class="layui-input-block">
		      		<input type="password" name="user_password_u" id="user_password_u"   lay-verify="password1" placeholder="请输入密码" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">用户邮箱</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="user_email_u" id="user_email_u"  required lay-verify="required|email1" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="updateuserform">更新</button>
			    </div>
			 </div>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var pageNum=1;//当前页
var pageSize=10;//每页大小
layui.use(['layer','form','laydate','laypage','common'],function(){
	var layer = layui.layer;
	var form = layui.form;
	var laydate = layui.laydate;
	var laypage = layui.laypage;
 	var $ = layui.$;
	var common = layui.common;
	common.ajaxSetUp();
 	var adduserOpen;//新增用户的弹窗
 	var updateuserOpen;//编辑用户的弹窗
 	
 	//自定义验证规则
  	form.verify({
	    username:[/[0-9a-zA-Z_]{5,14}/,'用户名需要5-14个字符，只能是数字、字母、下划线']
	    ,password:  [/^[0-9A-Za-z_@]{6,14}$/, '用户密码 6-14个字符 ,只能是字母、数字、下划线、@']
  		,password1:  [/^$|(^[0-9A-Za-z_@]{6,14}$)/, '用户密码 6-14个字符 ,只能是字母、数字、下划线、@']
	    ,email1:[/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,"邮箱格式不正确"]
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
 	
 	//表头checkbox点击事件
 	$(".thead_checkbox_c").on("click",function(){
 		if($(".thead_checkbox_c").is(":checked")){
 			$(".tbody_checkbox_c").prop("checked",true);
 		}else{
 			$(".tbody_checkbox_c").prop("checked",false);
 		}
 	});
 	
 	loaduserList();
 	
 	$(function(){
 		$(".layui-nav .layui-nav-item").removeClass("layui-this");
 		$("[name=userManager]").addClass("layui-this");
 	});
 	
 	function page(data){
 		//执行一个laypage分页配置
 		laypage.render({
 		    elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
 		    ,count: data.page.total //数据总数，从服务端得到
 		    ,limit:pageSize
 		    ,curr:pageNum
 		    ,jump:function(obj,first){
 		    	pageNum = obj.curr;
 		    	if(!first){
 		    		loaduserList();
 		    	}
 		    }
 		  });
 	}
 	
 	//加载用户
 	function loaduserList(){
 		//加载用户列表
 	 	$.ajax({
 	 		url:basePath+"/user/getListPage",
 	 		type:"post",
 	 		datatype:"json",
 	 		data:{
 	 			pageSize:pageSize,
 	 			pageNum:pageNum
 	 		},
 	 		success:function(data){
 	 			var htm ="";
 	 			$.each(data.list,function(ind,da){
 	 				htm+="<tr>";
 	 				htm+="<td><input class='tbody_checkbox_c' type='checkbox'></td>";
 	 				htm+="<td class='user_id_td'>"+da.user_id+"</td>";
 	 				htm+="<td>"+da.user_username+"</td>";
 	 				htm+="<td>"+da.user_nickname+"</td>";
 	 				htm+="<td>"+da.user_email+"</td>";
 	 				htm+="<td>"+da.user_createtime+"</td>";
 	 				htm+="<td>"+da.user_updatetime+"</td>";
 	 				htm+='<td><button name="updateuser_n" class="layui-btn layui-btn-primary layui-btn-xs">编辑</button></td>';
 	 				htm+="</tr>"
 	 			});
 	 			$("#userList_tbody_id").empty();
 	 			$("#userList_tbody_id").append(htm);
 	 			
 	 			$(".tbody_checkbox_c").on("click",function(){
 	 				var flag = true;
 	 				 $.each($(".tbody_checkbox_c"),function(i,d){
 	 					 if(!$(d).is(":checked")){
 	 						flag = false; 
 	 						return false;
 	 					 }
 	 				 });
 	 				 if(flag){
 	 					$(".thead_checkbox_c").prop("checked",true);
 	 				 }else{
 	 					$(".thead_checkbox_c").prop("checked",false);
 	 				 }
 	 				
 	 			});
 	 			
 	 			//编辑按钮点击事件
 	 			$("button[name='updateuser_n']").on('click',function(){
 	 				var user_id = $(this).parent().parent().find(".user_id_td").text();
 	 				if(!user_id){
 	 					layer.alert("用户ID为空");
 	 					return;
 	 				}
 	 				//编辑用户弹窗
 	 				updateuserOpen = layer.open({
 	 					type:"1",
 	 					title:"编辑用户",
 	 					area:['400px','350px'],
 	 					content:$("#updateuserDialog_id")
 	 				});
 	 				//根据用户ID获取用户信息
 	 				$.ajax({
 	 					url:basePath+"/user/getUserById",
 	 					type:"post",
 	 					datatype:"json",
 	 					data:{
 	 						user_id:user_id
 	 					},
 	 					success:function(data){
 	 						$("#user_id_u").val(data.user.user_id);
 	 						$("#user_username_u").val(data.user.user_username);
 	 						$("#user_nickname_u").val(data.user.user_nickname);
 	 						$("#user_email_u").val(data.user.user_email);
 	 						$("#user_password_u").val('');
 	 					}
 	 				});
 	 		 	});
 	 			page(data);
 	 		}
 	 	});
 	};
 	
 	
 	
 	//更新用户 表单 监听提交
	  form.on('submit(updateuserform)', function(data){
		 var user_username = $("#user_username_u").val().trim();
		 var user_nickname = $("#user_nickname_u").val().trim();
		 var user_email= $("#user_email_u").val().trim();
		 var user_password= $("#user_password_u").val().trim();
		 var user_id = $("#user_id_u").val();
		 $.ajax({
			url:basePath+"/user/updateUser",
			type:"post",
			data:{
				user_username:user_username,
				user_nickname:user_nickname,
				user_email:user_email,
				user_password:user_password,
				user_id:user_id
			},
			datatype:"json",
			success:function(data){
				if("ok" ==data.status){
					layer.msg(data.result);
					layer.close(updateuserOpen);
					loaduserList();
					return;
				}else{
					layer.alert(data.result);
				}
			}
		});
		return false;
	  });
 	
 	//新增用户
 	$("#adduser_id").click(function(){
 		//清空原有内容
 		$("#user_username").val('');
 		$("#user_nickname").val('');
 		$("#user_email").val('');
 		$("#user_password").val('');
 		//打开弹窗
 		 adduserOpen = layer.open({
 			type:"1",
 			title:"新增用户",
 			area:['400px','350px'],
 			content:$("#adduserDialog_id")
 		});
 	});
 	
 	$("#deleteuser_id").click(function(){
 		var flag = true;
 		$.each($(".tbody_checkbox_c"),function(index,data){
 			if($(data).is(":checked")){
 				flag = false;
 				return false;
 			}
 		});
 		if(flag){
 			return;
 		}
 		
 		layer.confirm("确认删除！",{
 			btn: ['确定', '取消']
 		}, function (index, layero) {
 			var ids=[];
 	 		$.each($(".tbody_checkbox_c"),function(ind,da){
 	 			if($(da).is(":checked")){
 					ids.push($(da).parent().parent().find(".user_id_td").text());			
 	 			}
 	 		});
 	 		
 	 		$.ajax({
 				url:basePath+"/user/deleteUserByIds",
 				type:"post",
 				data:{
 					ids:ids
 				},
 				datatype:"json",
 				success:function(data){
 					if(data.count>0){
 						layer.msg("删除成功");
 						pageNum = 1;
						$(".thead_checkbox_c").attr("checked",false);
 						loaduserList();
 						return;
 					}
 					layer.alert("删除失败");
 				}
 			});
        });
 	});
 	
 	//新增用户 表单 监听提交
 	  form.on('submit(adduserform)', function(data){
 		 var user_username = $("#user_username").val().trim();
		 var user_nickname = $("#user_nickname").val().trim();
		 var user_password = $("#user_password").val().trim();
		 var user_email = $("#user_email").val().trim();
		 
		 $.ajax({
			url:basePath+"/user/addUser",
			type:"post",
			data:{
				user_username:user_username,
				user_nickname:user_nickname,
				user_password:user_password,
				user_email:user_email
			},
			datatype:"json",
			success:function(data){
				if("ok" ==data.status){
					layer.msg(data.result);
					layer.close(adduserOpen);
					loaduserList();
					return;
				}else{
					layer.alert(data.result);
				}
				
			}
		});
		return false;
 	  });
 	
 	
});

</script>
</html>