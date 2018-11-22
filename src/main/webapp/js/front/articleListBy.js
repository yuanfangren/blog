layui.use(['layer','common'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	var common = layui.common;
 	$(function(){
 		var channel_id = common.getUrlParam("channel_id");
 		if(channel_id){
 			$(".type_desc_c").text("（栏目）");
 			$.ajax({
 				type:"post",
 				url:"../front/article/getArticleByChannelId",
 				data:{
 					channel_id:channel_id
 				},
 				success:function(data){
 					var articles = data.article;
 					console.log(articles);
 					var htm ="";
 					$.each(articles,function(ind,da){
 						if(ind == 0){
 							$(".title_c").text(da.channle_name);
 						}
 						var time = da.article_updatetime;
 						time = time.substring(0,10);
 						htm+='<div class="article_div_c"><a href="./article_show.html?article_id='+da.article_id+'"><span class="time_span_c">'+time+'</span><span class="title_span_c">'+da.article_title+'</span></a></div>'
 					});
 					$(".articles_div_c").empty();
 					$(".articles_div_c").append(htm);
 				},
 				error:function(){
 					layer.alert(common.error_msg)
 				}
 			});
 			return;
 		}
 		var tag_id = common.getUrlParam("tag_id");
 		if(tag_id){
 			$(".type_desc_c").text("（标签）");
 			$.ajax({
 				type:"post",
 				url:"../front/article/getArticleByTagId",
 				data:{
 					tag_id:tag_id
 				},
 				success:function(data){
 					var articles = data.article;
 					console.log(articles);
 					var htm ="";
 					$.each(articles,function(ind,da){
 						if(ind == 0){
 							$(".title_c").text(da.tag_name);
 						}
 						var time = da.article_updatetime;
 						time = time.substring(0,10);
 						htm+='<div class="article_div_c"><a href="./article_show.html?article_id='+da.article_id+'"><span class="time_span_c">'+time+'</span><span class="title_span_c">'+da.article_title+'</span></a></div>'
 					});
 					$(".articles_div_c").empty();
 					$(".articles_div_c").append(htm);
 				},
 				error:function(){
 					layer.alert(common.error_msg)
 				}
 			});
 		}
 	});
});