layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	$(function(){
 		$.ajax({
 			url:"../front/channel/getListPublic",
 			type:"post",
 			success:function(data){
 				var list = data.list;
 				var htm="";
 				for(var i=0;i<list.length;i++){
 					htm+="<li class='channel_li_c' channel_id="+list[i].channel_id+"><span class='ms_span_c'><span class='title_span_c'>"+list[i].channel_name+"</span>("+list[i].article_count+")</span></li>"
 				}
 				$(".channel_ul_c").empty();
 				$(".channel_ul_c").append(htm);
 				channel_li_click();
 			},
 			error:function(){
 				layer.alert("网站请求有问题啦");
 			}
 		});
 		
 		function channel_li_click(){
 			$(".channel_li_c").on("click",function(){
 				var channel_id = $(this).attr("channel_id");
 				window.location.href="../front/articleListBy.html?channel_id="+channel_id
 			});
 		}
 	})
});