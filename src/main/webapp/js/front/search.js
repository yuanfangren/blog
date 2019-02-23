layui.use(['layer','common'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	var common = layui.common;
 	$(function(){
 		$("#search").click(function(){
 			var query = $("#query_name").val().trim();
 			if(query == "" || query == null ){
 				return ;
 			}
 			
 			$.ajax({
 	 			url:"../front/elasticsearch/search",
 	 			type:"post",
 	 			data:{
 	 				"query":query
 	 			},
 	 			success:function(data){
 	 				console.log(data);
 	 				var htm;
 	 				$(".search_result_c").empty();
 	 				$.each(data.list,function(index,da){
 	 					var content = da.article_content.length > 200 ? da.article_content.substring(0,200) : da.article_content;
 	 					htm = "<h4>"+da.article_title+"</h4>"
 	 					   +"<p>"+content+"</p>";
 	 					$(".search_result_c").append(htm);
 	 				});
 	 			},
 	 			error:function(){
 	 				layer.alert(common.error_msg);
 	 			}
 	 		});
 		});
 		
 		
 	})
});