<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ren.blog.util.GlobalParameter"%>
<%@page import="com.ren.blog.bean.UserBean"%>
<%
	String basePath = request.getContextPath();
	UserBean user = (UserBean)request.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
	String showname = "";
	int usertype = -1;
	if(user != null){
		String nickname = user.getUser_nickname();
		if(nickname!= null && !"".equals(nickname)){
			showname = nickname;
		}else{
			showname = user.getUser_username();
		}
		 usertype = user.getUser_type(); 
	}
	

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理列表</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/layui_config.js"></script>

<style type="text/css">
.tool_c{
	margin-top:10px;
	margin-left: 10px;
}
.layuiopen_c{
	display: none;
	text-align: center;
}
.addLogForm_c{
	padding-right: 10px;
	padding-top: 10px;
}
#logerror_id{
	width: 465px;
    height: 315px;
}
</style>
</head>
<body>
<div >
<%@ include file = "../home.jspt" %>
	<div class="tool_c">
		<button id="deleteLog_id" class="layui-btn layui-btn-primary layui-btn-sm">删除日志</button>
	</div>
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<td><input class='thead_checkbox_c' type="checkbox"></td>
					<td>日志ID</td>
					<td>日志简单描述</td>
					<td>操作模块</td>
					<td>操作类型</td>
					<td>操作用户</td>
					<td>操作时间</td>
					<td>操作IP</td>
					<td>操作结果</td>
					<td>异常</td>
					<td>操作</td>
				</tr>
			</thead>
			
			<tbody id="LogList_tbody_id">
			
			</tbody>
		</table>
		
		<div id="page"></div>
	</div>
	
	<div id="showLogErrorDialog_id" class="layuiopen_c">
		<form action="" class="layui-form addLogForm_c">
			<textarea rows="" cols="" id="logerror_id" ></textarea>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var showname ='<%=showname%>';
var pageNum=1;//当前页
var pageSize=10;//每页大小
layui.use(['element','layer','form','laydate','laypage','common'],function(){
	var layer = layui.layer;
	var form = layui.form;
	var laydate = layui.laydate;
	var laypage = layui.laypage;
 	var $ = layui.$;
 	var common = layui.common;
	common.ajaxSetUp();
 	$(".top_showname_c").append(showname);
 	
 	//表头checkbox点击事件
 	$(".thead_checkbox_c").on("click",function(){
 		if($(".thead_checkbox_c").is(":checked")){
 			$(".tbody_checkbox_c").prop("checked",true);
 		}else{
 			$(".tbody_checkbox_c").prop("checked",false);
 		}
 	});
 	
 	loadLogList();
 	
 	$(function(){
 		$(".layui-nav .layui-nav-item").removeClass("layui-this");
 		$("[name=logManager]").addClass("layui-this");
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
 		    		console.log("not first");
 		    		console.log(obj);
 		    		loadLogList();
 		    	}
 		    }
 		  });
 	}
 	
 	//加载日志
 	function loadLogList(){
 		//加载日志列表
 	 	$.ajax({
 	 		url:basePath+"/log/getLogPage",
 	 		type:"post",
 	 		datatype:"json",
 	 		data:{
 	 			pageSize:pageSize,
 	 			pageNum:pageNum
 	 		},
 	 		success:function(data){
 	 			console.log(data);
 	 			var htm ="";
 	 			$.each(data.list,function(ind,da){
 	 				var log_opermodule = da.log_opermodule;
 	 				if(1 == log_opermodule){
 	 					log_opermodule = "文章";
 	 				}else if(2 == log_opermodule){
 	 					log_opermodule = "栏目";
 	 				}else if(3 == log_opermodule){
 	 					log_opermodule = "标签";
 	 				}else if(4 == log_opermodule){
 	 					log_opermodule = "用户";
 	 				} 
 	 				
 	 				var log_opertype = da.log_opertype;
 	 				if(1 == log_opertype){
 	 					log_opertype = "新增";
 	 				}else if(2 == log_opertype){
 	 					log_opertype = "更新";
 	 				}else if(3 == log_opertype){
 	 					log_opertype = "删除";
 	 				}else if(4 == log_opertype){
 	 					log_opertype = "混合";
 	 				} else if(5 == log_opertype){
 	 					log_opertype = "登录";
 	 				} 
 	 				var czhtm ="<td></td>";
 	 				var log_result = da.log_result;
 	 				if(1 == log_result){
 	 					log_result = "成功";
 	 				}else if(2 == log_result){
 	 					log_result = "失败";
 	 					czhtm= '<td><button name="updateLog_n" class="layui-btn layui-btn-primary layui-btn-xs">查看异常详情</button></td>';
 	 				}
 	 				
 	 				htm+="<tr>";
 	 				htm+="<td><input class='tbody_checkbox_c' type='checkbox'></td>";
 	 				htm+="<td class='Log_id_td'>"+da.log_id+"</td>";
 	 				htm+="<td>"+da.log_desc+"</td>";
 	 				htm+="<td>"+log_opermodule+"</td>";
 	 				htm+="<td>"+log_opertype+"</td>";
 	 				htm+="<td>"+da.user_username+"</td>";
 	 				htm+="<td>"+da.log_operdate+"</td>";
 	 				htm+="<td>"+da.log_operip+"</td>";
 	 				htm+="<td>"+log_result+"</td>";
 	 				htm+="<td>"+da.log_resultdesc+"</td>";
 	 				htm+=czhtm;
 	 				htm+="</tr>"
 	 			});
 	 			$("#LogList_tbody_id").empty();
 	 			$("#LogList_tbody_id").append(htm);
 	 			
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
 	 			
 	 			//查看日期详情
 	 			$("button[name='updateLog_n']").on('click',function(){
 	 				var Log_id = $(this).parent().parent().find(".Log_id_td").text();
 	 				if(!Log_id){
 	 					layer.alert("日志ID为空");
 	 					return;
 	 				}
 	 				updateLogOpen = layer.open({
 	 					type:"1",
 	 					title:"查看日志情况",
 	 					area:['500px','400px'],
 	 					content:$("#showLogErrorDialog_id")
 	 				});
 	 				$.ajax({
 	 					url:basePath+"/log/getLogById",
 	 					type:"post",
 	 					datatype:"json",
 	 					data:{
 	 						log_id:Log_id
 	 					},
 	 					success:function(data){
 	 						$("#logerror_id").val(data.logBean.note);
 	 					}
 	 				});
 	 		 	});
 	 			page(data);
 	 		}
 	 	});
 	};
 	
 	$("#deleteLog_id").click(function(){
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
 					ids.push($(da).parent().parent().find(".Log_id_td").text());			
 	 			}
 	 		});
 	 		
 	 		$.ajax({
 				url:basePath+"/log/deleteLogByIds",
 				type:"post",
 				data:{
 					ids:ids
 				},
 				datatype:"json",
 				success:function(data){
 					if("ok" == data.status){
 						layer.msg(data.msg);
 						pageNum = 1;
						$(".thead_checkbox_c").attr("checked",false);
 						loadLogList();
 						return;
 					}else{
 						layer.alert(data.msg);
 					}
 				}
 			});
        });
 	});
 	
});

</script>
</html>