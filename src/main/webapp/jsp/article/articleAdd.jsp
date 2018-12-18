<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ren.blog.util.GlobalParameter"%>
<%@page import="com.ren.blog.bean.UserBean"%>
<%
	String basePath = request.getContextPath();
	String article_id =request.getParameter("article_id");
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
<title>文章内容</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>/plug/editor/css/editormd.css"> 
<link rel="stylesheet" href="<%=basePath%>/plug/editor/css/style.css">
<link rel="stylesheet" href="<%=basePath%>/css/back/back_common.css">
<script type="text/javascript" src="<%=basePath%>/plug/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.js" ></script>
<script type="text/javascript" src="<%=basePath%>/plug/editor/editormd.js"></script>

<style type="text/css">
#article_title{
	width: 95%;
    height: 40px;
    margin: 10px 0;
}
#bar_div{
	margin-top: 10px;
	text-align: left;
    margin-left: 50px;
}
#showArticle_id{
	display: none;
}
.tag_show_c{
	height: 20px;
    font-size: 12px;
    text-align: left;
    margin-left: 50px;
    line-height: 20px;
    margin-top: 5px;
}
.tag_show_c span{
	color: blue;
    border-radius: 5px;
    border: 1px solid;
    padding: 0px 5px;
    cursor: pointer;
}
.tag_show_c span:hover{
	color:red;
	 padding: 1px 10px;
}
.layui-icon{
	cursor: pointer;
    font-size: 14px;
    color: red;
    margin-left: 10px;
}
.layuiopen_c{
	display: none;
}
</style>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var article_id = '<%=article_id%>';
var showname ='<%=showname%>';
var editormd; //编辑器
var layer;
var form;
$(function () {
	 var testEditor = editormd("editormd_id", {
		 placeholder:"",
	        width   : "95%",
	        height  : 640,
	        syncScrolling : "single",
	        path    : basePath+"/plug/editor/lib/",
      imageUpload : true,
      imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
      imageUploadURL : basePath+"/article/editormdPic",
      onload:function(){
	      }
	  });
	 
	 /**
      * 粘贴上传图片
      */
     $("#editormd_id").on('paste', function (ev) {
         var data = ev.clipboardData;
         var items = (event.clipboardData || event.originalEvent.clipboardData).items;
         for (var index in items) {
             var item = items[index];
             if (item.kind === 'file') {
                 var blob = item.getAsFile();
                 var reader = new FileReader();
                 reader.onload = function (event) {
                	 var image = new Image();
                	 image.src = event.target.result; 
                     //ajax上传图片
                     $.post(basePath+'/article/editormdPastePic',{
                    	 "pasteImg":image.src
                     }, function (ret) {
                         if (ret.success === 1) {
                             //新一行的图片显示
                             console.log(ret.url);
                             var qiniuUrl = '![](' + ret.url + ')';
                             testEditor.insertValue(qiniuUrl);
                         }
                     });
                 }; // data url!
                 var url = reader.readAsDataURL(blob);
             }
         }
     });
	 
    function getSelectedText(obj) {
         var userSelection;
         if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
           // 非IE浏览器
           // 获取选区的开始位置
           var startPos = obj.selectionStart,
                     // 获取选区的结束位置
               endPos = obj.selectionEnd;
           console.log("非IE：")
           console.log("选区开始点：" + startPos + '，选区结束点：' + endPos)

           userSelection = obj.value.substring(startPos, endPos)

         } else if (document.selection) {
           // IE浏览器
           console.log("IE：")
           userSelection = document.selection.createRange().text
         }
         return userSelection;
       }
});
layui.use(['element','layer','form'],function(){
	//layui.layer.alert(layui.jquery.fn.jquery);//1.12.3
	 layer = layui.layer;
	 form = layui.form;
	 form.verify({
	 		long15:function(value){
	  			if(value.trim().length>15){
	  				return "名称不能超过15个字符";
	  			}
	  		}
	 });
	 
	 $(function(){

			
			$(".top_showname_c").append(showname);
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
			
			
			showOrHidAdd();
			tag_span_click();
			
				/**editormd = editormd("editormd_id", {
					placeholder:"",
			        width   : "95%",
			        height  : 640,
			        syncScrolling : "single",
			        path    : basePath+"/plug/editor/lib/"
				});*/
				
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
							if(data.tag){
								$.each(data.tag,function(ind,da){
									 $(".add_tag_c").before("<span>"+da.tag_name+"</span>");
								});
								showOrHidAdd();
								tag_span_click();
							}
							
							//如果是发布状态 新增撤销按钮
							if(1 == data.article.article_status){
								$("#showArticle_id").after('<button id="cancleArticle_id" tid="'+article+'"  class="layui-btn layui-btn-primary layui-btn-sm">撤销</button>');
								cancleArticle_id_click();
							}
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
			 		
			 		var tags =[];
			 		$.each($(".tag_show_c span"),function(inx,da){
			 			tags.push($(da).text().trim());
			 		});
			 		
			 		var article_content = $("#article_content").val();
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
			 				article_remark:article_remark,
			 				tags:tags
			 			},
			 			success:function(data){
			 				
			 				if("ok" == data.status){
								layer.msg(data.msg);
								$("#showArticle_id").show();
								$("#hidden_id").val(data.result.article_id);
								$("#addArticle_id").text("修改文章");
								return;
			 				}
							layer.alert(data.msg);
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
			 		
			 		var tags =[];
			 		$.each($(".tag_show_c span"),function(inx,da){
			 			tags.push($(da).text().trim());
			 		});
			 		
			 		var article_content = $("#article_content").val();
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
			 				article_remark:article_remark,
			 				tags:tags
			 			},
			 			success:function(data){
			 				if("ok" == data.status){
			 					var msg = "发布成功";
								layer.msg(msg);
								window.location.href=basePath+"/jsp/article/articleList.jsp";
								return;
			 				}
			 				layer.alert("发布失败:"+data.msg);
							
			 			}
			 		});
			 	});
				
				$("#showArticle_id").on("click",function(){
					var article_id = $("#hidden_id").val();
					window.open(basePath+"/front/article_show.html?article_id="+article_id);
				});
				
				
				var addTagOpen;		
				add_tag_click();
				//新增标签 表单 监听提交
				/* $("#addTag_id").on("click",function(){
		 			
				}); */
			 	 form.on('submit(addTagform)', function(data){
			 			var tag_name = $("#Tag_name").val().trim();
			 		 
			 		 var flag = false;
			 		 $.each($(".tag_show_c span"),function(ind,da){
			 			 var tmp = $(da).text().trim();
			 			 if(tag_name == tmp){
			 				 flag = true;
			 				 return false;
			 			 }
			 		 });
			 		 
			 		 if(flag){
			 			 layer.alert("该标签已添加了");
			 			 return false;
			 		 }
			 		 
					 $(".add_tag_c").before("<span>"+tag_name+"</span>");
					 showOrHidAdd();
					 tag_span_click();
					 layer.close(addTagOpen);
					 $("#addTagDialog_id").hide();
					 return false;
			 	  });  
				
			 	//点击新增标签
			 	 function add_tag_click(){
			 	 	$(".add_tag_c").on("click",function(){
			 	 		addTagOpen = layer.open({
			 	  			type:"1",
			 	  			title:"新增标签",
			 	  			area:['400px','180px'],
			 	  			content:$("#addTagDialog_id")
			 	  		});
			 	 	});
			 	 }
			 	
			 	//判断显示还是隐藏新增标签按钮
			 	function showOrHidAdd(){
			 		if($(".tag_show_c span").length>=5){
			 			$(".tag_show_c").find(".add_tag_c").hide();
			 		}else{
			 			$(".tag_show_c").find(".add_tag_c").show();
			 		}
			 	}
			 	
			 	//标签点击移除
			 	function tag_span_click(){
			 		$(".tag_show_c span").on("click",function(){
			 			$(this).remove();
			 			showOrHidAdd();
			 		});
			 	}
				
		});


		//撤销事件
		function cancleArticle_id_click(){
			$("#cancleArticle_id").on("click",function(){
				var article_id = $(this).attr("tid");
				$.ajax({
					type:"post",
		 			url:basePath+"/article/updateArticleStatus",
		 			data:{
		 				article_id:article_id,
		 				article_status:0
		 			},
		 			success:function(data){
		 				if("ok"==data.status){
		 					var msg = "撤销成功";
							layer.msg(msg);
							window.location.href=basePath+"/jsp/article/articleAdd.jsp?article_id="+article_id;
							return;
						}
						layer.alert("撤销失败");
		 			}
				});
			});
		}
});
//editormd 依赖jQuery,layui隐藏了

</script>

</head>
<body>
	 <%@ include file = "../home.jspt" %>
	<div id="bar_div">
		<input type="hidden" id="hidden_id">
		<button id="addArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">新增文章</button>
		<button id="publicArticle_id" class="layui-btn layui-btn-primary layui-btn-sm">发布文章</button>
		<button id="showArticle_id"  class="layui-btn layui-btn-primary layui-btn-sm">预览</button>
		所属栏目：<select id="channel_id">
		</select>
	</div>
	<div class="tag_show_c">
		<i class="layui-icon layui-icon-add-circle-fine add_tag_c" title="新增标签"></i> 
	</div>
	<div id="title_div">
		<input name="article_title" id="article_title" placeholder="文章标题">
	</div>
	<div id="editormd_id">
		<textarea style="display:none;" id="article_content" ></textarea>
	</div>
	
	
	<div id="addTagDialog_id" class="layuiopen_c">
		<form action="" class="layui-form addTagForm_c">
			<div class="layui-form-item">
		    	<label class="layui-form-label">标签名称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="Tag_name" id="Tag_name" required  lay-verify="required|long15" placeholder="请输入标签名称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" id="addTag_id" lay-submit lay-filter="addTagform">新增</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			 </div>
		</form>
	</div>
</body>
</html>