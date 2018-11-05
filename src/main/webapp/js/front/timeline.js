layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
 	var $ = layui.$;
 	$(function(){
 		$.ajax({
 			type:"post",
 			url:"../front/article/getAllList",
 			success:function(data){
 				var list = data.list;
 				var length = list.length;
 				var year = "";
 				var months = "";
 				var htm="";
 				$("#timeline_ul_id").empty();
 				for(var i=0;i<length;i++){
 					htm="";
 					var article_year = list[i].article_year;
 					var article_months = list[i].article_months;
 					if(i == 0){
 						year = article_year;
 						months = article_months;
 						htm+='<li class="layui-timeline-item">'+
 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 						'<div class="layui-timeline-content layui-text">'+
 						'<h3 class="layui-timeline-title">'+year+'年</h3>'+
 						'</div></li>'+
 						'<li class="layui-timeline-item months_libottom_c">'+
 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 						'<div class="layui-timeline-content layui-text">'+
 						'<h3 class="layui-timeline-title months_timeline_c">'+months+'月</h3>'+
 						'</div></li>'+
 						'<li class="layui-timeline-item title_libottom_c">'+
 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 						'<div class="layui-timeline-content layui-text">'+
 						'<h5 class="layui-timeline-title title_timeline_c">'+
 						'<span class="timeline_monthsday">'+list[i].article_monthsday+'</span>'+
 						'<a href="./article_show.html?article_id='+list[i].article_id+'" >'+list[i].article_title+'</a>'+
 						'</span> <span class="channel_span_c">'+list[i].channel_name+'</span></h5>'+
 						'</div></li>';
 					}else{
 						if(year != article_year){
 							year = article_year;
 							htm+='<li class="layui-timeline-item">'+
 	 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 	 						'<div class="layui-timeline-content layui-text">'+
 	 						'<h3 class="layui-timeline-title">'+year+'年</h3>'+
 	 						'</div></li>';
 						}
 						if(months != article_months){
 							months = article_months;
 							htm+='<li class="layui-timeline-item months_libottom_c">'+
 	 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 	 						'<div class="layui-timeline-content layui-text">'+
 	 						'<h3 class="layui-timeline-title months_timeline_c">'+months+'月</h3>'+
 	 						'</div></li>';
 						}
 						htm+='<li class="layui-timeline-item title_libottom_c">'+
 						'<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
 						'<div class="layui-timeline-content layui-text">'+
 						'<h5 class="layui-timeline-title title_timeline_c">'+
 						'<span class="timeline_monthsday">'+list[i].article_monthsday+'</span>'+
 						'<a href="./article_show.html?article_id='+list[i].article_id+'" >'+list[i].article_title+'</a>'+
 						'</span> <span class="channel_span_c">'+list[i].channel_name+'</span></h5>'+
 						'</div></li>';
 					}
 					
 					$("#timeline_ul_id").append(htm);
 				}
 			},
 			error:function(){
 				layer.alert("网站请求有问题啦");
 			}
 		});
 	});
});