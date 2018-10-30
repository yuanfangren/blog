<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	String article_id =request.getParameter("article_id");
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章内容</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>/plug/editor/css/style.css">
<link rel="stylesheet" href="<%=basePath%>/plug/editor/css/editormd.css"> 
<script type="text/javascript" src="<%=basePath%>/plug/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js" ></script>
<script type="text/javascript" src="<%=basePath%>/plug/editor/editormd.js"></script>
<style type="text/css">
#article_title{
	width: 95%;
    height: 40px;
    margin: 10px 0;
}
#bar_div{
	margin-top: 10px;
}
#showArticle_id{
	display: none;
}
</style>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var article_id = '<%=article_id%>';
var editormd; //编辑器
var layer;
layui.use(['layer','form'],function(){
	//layui.layer.alert(layui.jquery.fn.jquery);//1.12.3
	 layer = layui.layer;
});
//editormd 依赖jQuery,layui隐藏了
$(function(){
		editormd = editormd("editormd_id", {
			placeholder:"",
	        width   : "95%",
	        height  : 640,
	        syncScrolling : "single",
	        path    : basePath+"/plug/editor/lib/"
		});
		
		//加载栏目列表
		$.ajax({
			type:"post",
			url:basePath+"/channel/getList",
			success:function(data){
				var htm="";
				$.each(data.list,function(ind,da){
					htm+="<option value='"+da.channel_id+"'>"+da.channel_name+"</option>"
				});
				$("#channel_id").empty();
				$("#channel_id").append(htm);
			}
		});
		
		var article = Number(article_id);
		if(!isNaN(article)){//如果是数字
			//根据文章ID加载文章
			$("#hidden_id").val(article);
			$.ajax({
				type:"post",
				url:basePath+"/article/getArticleById",
				data:{
					article_id:article
				},
				success:function(data){
					$("#showArticle_id").show();
					$("#hidden_id").val(article);
					$("#addArticle_id").text("修改文章");
					$("#channel_id").val(data.article.channel_id);
					$("#article_title").val(data.article.article_title);
					$("#article_content").val(data.article.article_content);
				}
			});
		}else{
			article = 0;
		}
		
		$("#addArticle_id").click(function(){
	 		var article_title =$("#article_title").val().trim();
	 		var hidden_id = $("#hidden_id").val();
	 		if(hidden_id != null && hidden_id !=""){
	 			article = hidden_id; 
	 		}
	 		var channel_id =$("#channel_id").val();
	 		if("" == article_title){
	 			layer.alert("文章标题不能为空");
	 			return;
	 		}
	 		if("" == channel_id){
	 			layer.alert("栏目不能为空");
	 			return;
	 		}
	 		var article_content = $("#article_content").html();
	 		var article_remark = $(".editormd-preview").text();
	 		$.ajax({
	 			type:"post",
	 			url:basePath+"/article/addArticle",
	 			data:{
	 				article_title:article_title,
	 				article_content:article_content,
	 				article_status:0,
	 				channel_id:channel_id,
	 				article_id:article,
	 				article_remark:article_remark
	 			},
	 			success:function(data){
	 				if(data.count>0){
	 					var msg = "保存成功";
	 					if(hidden_id != null && hidden_id !=""){
	 			 			msg ="修改成功";
	 			 		}
						layer.msg(msg);
						$("#showArticle_id").show();
						$("#hidden_id").val(data.article_id);
						$("#addArticle_id").text("修改文章");
						return;
					}
					layer.alert("保存失败");
	 			}
	 		});
	 	});
		
		$("#publicArticle_id").click(function(){
	 		var article_title =$("#article_title").val().trim();
	 		var channel_id =$("#channel_id").val();
	 		var hidden_id = $("#hidden_id").val();
	 		if(hidden_id != null && hidden_id !=""){
	 			article = hidden_id; 
	 		}
	 		if("" == article_title){
	 			layer.alert("文章标题不能为空");
	 			return;
	 		}
	 		if("" == channel_id){
	 			layer.alert("栏目不能为空");
	 			return;
	 		}
	 		var article_content = $("#article_content").html();
	 		var article_remark = $(".editormd-preview").text();
	 		$.ajax({
	 			type:"post",
	 			url:basePath+"/article/addArticle",
	 			data:{
	 				article_title:article_title,
	 				article_content:article_content,
	 				article_status:1,
	 				channel_id:channel_id,
	 				article_id:article,
	 				article_remark:article_remark
	 			},
	 			success:function(data){
	 				if(data.count>0){
	 					var msg = "发布成功";
						layer.msg(msg);
						return;
					}
					layer.alert("发布失败");
	 			}
	 		});
	 	});
		
		$("#showArticle_id").on("click",function(){
			var article_id = $("#hidden_id").val();
			window.open(basePath+"/front/article_show.html?article_id="+article_id);
		});
		
});
</script>

</head>
<body>
	<div id="bar_div">
		<input type="hidden" id="hidden_id">
		<button id="addArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">新增文章</button>
		<button id="publicArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">发布文章</button>
		<button id="showArticle_id"  class="layui-btn layui-btn-primary layui-btn-sm">预览</button>
		所属栏目：<select id="channel_id">
			
		</select>
	</div>
	<div id="title_div">
		<input name="article_title" id="article_title" placeholder="文章标题">
	</div>
	<div id="editormd_id">
		<textarea style="display:none;" id="article_content" ></textarea>
	</div>
</body>
</html>