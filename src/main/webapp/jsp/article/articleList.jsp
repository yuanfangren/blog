<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章管理列表</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>/css/back/back_common.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/layui_config.js"></script>

<style type="text/css">

.tool_c{
	margin-top:10px;
	margin-left: 10px;
}
.layuiopen_c{
	display: none;
}
.addArticleForm_c{
	padding-right: 10px;
	padding-top: 10px;
}
.channel_id_td{
	display: none;
}
</style>
</head>
<body>
<div >
	 <%@ include file = "../home.jspt" %>
	<div class="tool_c">
		<button id="addArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">新增文章</button>
		<button id="deleteArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">删除文章</button>
	</div>
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<td><input class='thead_checkbox_c' type="checkbox"></td>
					<td>文章ID</td>
					<td>文章名称</td>
					<td>文章状态</td>
					<td>文章所属栏目</td>
					<td>文章创建时间</td>
					<td>文章更新时间</td>
					<td>操作</td>
				</tr>
			</thead>
			
			<tbody id="ArticleList_tbody_id">
			
			</tbody>
		</table>
		
		<div id="page"></div>
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
 	var addArticleOpen;//新增文章的弹窗
 	var updateArticleOpen;//编辑文章的弹窗
 	
 	//表头checkbox点击事件
 	$(".thead_checkbox_c").on("click",function(){
 		if($(".thead_checkbox_c").is(":checked")){
 			$(".tbody_checkbox_c").prop("checked",true);
 		}else{
 			$(".tbody_checkbox_c").prop("checked",false);
 		}
 	});
 	
	loadArticleList();
 	
 	$(function(){
 		$(".layui-nav .layui-nav-item").removeClass("layui-this");
 		$("[name=articleManager]").addClass("layui-this");
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
 		    		loadArticleList();
 		    	}
 		    }
 		  });
 	}
 	
 	//加载文章
 	function loadArticleList(){
 		//加载文章列表
 	 	$.ajax({
 	 		url:basePath+"/article/getListPage",
 	 		type:"post",
 	 		datatype:"json",
 	 		data:{
 	 			pageSize:pageSize,
 	 			pageNum:pageNum
 	 		},
 	 		success:function(data){
 	 			var htm ="";
 	 			$.each(data.list,function(ind,da){
 	 				var status = "";
 	 				if(0 == da.article_status){
 	 					status = "已保存";
 	 				}else if(1 == da.article_status){
 	 					status = "已发布";
 	 				}
 	 				htm+="<tr>";
 	 				htm+="<td><input class='tbody_checkbox_c' type='checkbox'></td>";
 	 				htm+="<td class='article_id_td'>"+da.article_id+"</td>";
 	 				htm+="<td class='channel_id_td'>"+da.channel_id+"</td>";
 	 				htm+="<td>"+da.article_title+"</td>";
 	 				htm+="<td>"+status+"</td>";
 	 				htm+="<td>"+da.channle_name+"</td>";
 	 				htm+="<td>"+da.article_createtime+"</td>";
 	 				htm+="<td>"+da.article_updatetime+"</td>";
 	 				htm+='<td><button name="updateArticle_n" class="layui-btn layui-btn-primary layui-btn-xs">编辑</button><button name="showArticle_n" class="layui-btn layui-btn-primary layui-btn-xs">预览</button></td>';
 	 				htm+="</tr>"
 	 			});
 	 			$("#ArticleList_tbody_id").empty();
 	 			$("#ArticleList_tbody_id").append(htm);
 	 			
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
 	 			$("button[name='updateArticle_n']").on('click',function(){
 	 				var article_id = $(this).parent().parent().find(".article_id_td").text();
 	 				var channel_id = $(this).parent().parent().find(".channel_id_td").text();
 	 				window.location.href=basePath+"/jsp/article/articleAdd.jsp?article_id="+article_id;
 	 		 	});
 	 			
 	 			//预览按钮点击事件
 	 			$("button[name='showArticle_n']").on('click',function(){
 	 				var article_id = $(this).parent().parent().find(".article_id_td").text();
 	 				var channel_id = $(this).parent().parent().find(".channel_id_td").text();
 	 				window.open(basePath+"/front/article_show.html?article_id="+article_id);
 	 		 	});
 	 			page(data);
 	 		}
 	 	});
 	};
 	
 	//新增文章
 	$("#addArticle_id").click(function(){
 		window.location.href=basePath+"/jsp/article/articleAdd.jsp";
 	});
 	
 	$("#deleteArticle_id").click(function(){
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
 					ids.push($(da).parent().parent().find(".Article_id_td").text());			
 	 			}
 	 		});
 	 		$.ajax({
 				url:basePath+"/article/deleteArticleByIds",
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
 						loadArticleList();
 						return;
 					}
 					layer.alert("删除失败");
 				}
 			});
        });
 	});
 	
 	
 	
});
</script>
</html>