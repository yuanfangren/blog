layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	$(function(){
 		$.ajax({
 			type:"post",
 			url:"./front/article/getAllList",
 			success:function(data){
 				console.log(data);
 				$.each(data.list,function(ind,da){
 					var htm ='<div class="article_div_c">'+
 						'<div class="title_c">'+
 						'<a class="" href="article_show.html?article_id='+da.article_id+'">'+da.article_title+'</a>'+
 						'</div>'+
 						'<div class="msg_c">'+
 						'时间：<span class="updatetime_c">'+da.article_updatetime+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 栏目：<span class="channel_name_c">'+da.channel_name+'</span>'+
 						'</div>'+
 						'<div class="remark_c">'+
 						da.article_remark+
 						'</div>'+
 						'<div class="show_c">'+
 						'<a class="show_a_c" href="./front/article_show.html?article_id='+da.article_id+'">阅读全文</a>'+
 						'</div>'+
 						'</div>';
 					$(".main_c").append(htm);
 				});
 			}
 		});
 	});
});