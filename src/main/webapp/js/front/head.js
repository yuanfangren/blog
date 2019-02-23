layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	$(function(){
 		 $(".blog_name_c").click(function(){
 			window.parent.location.href="home.html";
 		 });
 		
 		 $(".type_div_c").on("click",function(){
 			 if($(this).hasClass("timeline_c")){//归档
 				 window.parent.location.href="timeline.html";
 			 }
 			 
 			 if($(this).hasClass("home_c")){//首页
 				window.parent.location.href="../home.html";
 			 }
 			 
 			 if($(this).hasClass("channel_c")){//栏目
 				window.parent.location.href="channel.html";
 			 }
 			 if($(this).hasClass("tag_c")){//标签
  				window.parent.location.href="tag.html";
  			 }
 			if($(this).hasClass("search_c")){//搜索
  				window.parent.location.href="search.html";
  			 }
 		 });
 	});
});