layui.use(['layer','common'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	var common = layui.common;
 	$(function(){
 		$.ajax({
 			url:"../front/tag/getListAritcle",
 			type:"post",
 			success:function(data){
 				var list = data.list;
 				var htm="";
 				for(var i=0;i<list.length;i++){
 					htm+='<div class="tag_div_c"><a href="../front/articleListBy.html?tag_id='+list[i].tag_id+'">'+list[i].tag_name+'</a></div>';
 				}
 				$(".tags_div_c").empty();
 				$(".tags_div_c").append(htm);
 			},
 			error:function(){
 				layer.alert(common.error_msg);
 			}
 		});
 		
 	})
});