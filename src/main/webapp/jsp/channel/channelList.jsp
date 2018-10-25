<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>栏目管理列表</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/channel/channelList.js"></script>
<style type="text/css">
.tool_c{
	margin-top:10px;
	margin-left: 10px;
}
.layuiopen_c{
	display: none;
}
.addChannelForm_c{
	padding-right: 10px;
	padding-top: 10px;
}
</style>
</head>
<body>
<div >
	<div class="tool_c">
		<button id="addChannel_id" class="layui-btn layui-btn-primary layui-btn-sm">新增栏目</button>
		<button id="deleteChannel_id" class="layui-btn layui-btn-primary layui-btn-sm">删除栏目</button>
	</div>
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<td><input class='thead_checkbox_c' type="checkbox"></td>
					<td>栏目ID</td>
					<td>栏目名称</td>
					<td>栏目顺序</td>
					<td>栏目创建时间</td>
					<td>栏目更新时间</td>
					<td>操作</td>
				</tr>
			</thead>
			
			<tbody id="channelList_tbody_id">
			
			</tbody>
		</table>
		
		<div id="page"></div>
	</div>
	
	<div id="addChannelDialog_id" class="layuiopen_c">
		<form action="" class="layui-form addChannelForm_c">
			<div class="layui-form-item">
		    	<label class="layui-form-label">栏目名称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="channel_name" id="channel_name" required  lay-verify="required" placeholder="请输入栏目名称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">栏目顺序</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="channel_order" id="channel_order" required  lay-verify="required|number" placeholder="请输入栏目顺序" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">栏目描述</label>
		    	<div class="layui-input-block">
		      		<textarea class="layui-textarea" name="channel_desc" id="channel_desc" rows="" cols=""></textarea>
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="addChannelform">新增</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			 </div>
		</form>
	</div>
	
	<div id="updateChannelDialog_id" class="layuiopen_c">
		<form action="" class="layui-form addChannelForm_c">
			<input type="hidden" id="channel_id_u">
			<div class="layui-form-item">
		    	<label class="layui-form-label">栏目名称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="channel_name_u" id="channel_name_u" required  lay-verify="required" placeholder="请输入栏目名称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">栏目顺序</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="channel_order_u" id="channel_order_u" required  lay-verify="required|number" placeholder="请输入栏目顺序" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		    <div class="layui-form-item">
		    	<label class="layui-form-label">栏目描述</label>
		    	<div class="layui-input-block">
		      		<textarea class="layui-textarea" name="channel_desc_u" id="channel_desc_u" rows="" cols=""></textarea>
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="updateChannelform">编辑</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			 </div>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var pageNum=1;//当前页
var pageSize=10;//每页大小
layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
	var form = layui.form;
	var laydate = layui.laydate;
	var laypage = layui.laypage;
 	var $ = layui.$;
 	var addChannelOpen;//新增栏目的弹窗
 	var updateChannelOpen;//编辑栏目的弹窗
 	
 	 /* laydate.render({
 	    elem: '#channel_createtime_u',
 	    format:"yyyy-MM-dd HH:mm:ss",
 	  });
 	laydate.render({
 	    elem: '#channel_updatetime_u',
 	    format:"yyyy-MM-dd HH:mm:ss",
 	  }); */
 	
 	//表头checkbox点击事件
 	$(".thead_checkbox_c").on("click",function(){
 		if($(".thead_checkbox_c").is(":checked")){
 			$(".tbody_checkbox_c").prop("checked",true);
 		}else{
 			$(".tbody_checkbox_c").prop("checked",false);
 		}
 	});
 	
 	loadChannelList();
 	
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
 		    		console.log("not first");
 		    		console.log(obj);
 		    		loadChannelList();
 		    	}
 		    }
 		  });
 	}
 	
 	//加载栏目
 	function loadChannelList(){
 		//加载栏目列表
 	 	$.ajax({
 	 		url:basePath+"/channel/getListPage",
 	 		type:"post",
 	 		datatype:"json",
 	 		data:{
 	 			pageSize:pageSize,
 	 			pageNum:pageNum
 	 		},
 	 		success:function(data){
 	 			console.log(data);
 	 			var htm ="";
 	 			$.each(data.list,function(ind,da){
 	 				htm+="<tr>";
 	 				htm+="<td><input class='tbody_checkbox_c' type='checkbox'></td>";
 	 				htm+="<td class='channel_id_td'>"+da.channel_id+"</td>";
 	 				htm+="<td>"+da.channel_name+"</td>";
 	 				htm+="<td>"+da.channel_order+"</td>";
 	 				htm+="<td>"+da.channel_createtime+"</td>";
 	 				htm+="<td>"+da.channel_updatetime+"</td>";
 	 				htm+='<td><button name="updateChannel_n" class="layui-btn layui-btn-primary layui-btn-xs">编辑</button></td>';
 	 				htm+="</tr>"
 	 			});
 	 			$("#channelList_tbody_id").empty();
 	 			$("#channelList_tbody_id").append(htm);
 	 			
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
 	 			$("button[name='updateChannel_n']").on('click',function(){
 	 				var channel_id = $(this).parent().parent().find(".channel_id_td").text();
 	 				if(!channel_id){
 	 					layer.alert("栏目ID为空");
 	 					return;
 	 				}
 	 				//编辑栏目弹窗
 	 				updateChannelOpen = layer.open({
 	 					type:"1",
 	 					title:"编辑栏目",
 	 					area:['400px','350px'],
 	 					content:$("#updateChannelDialog_id")
 	 				});
 	 				//根据栏目ID获取栏目信息
 	 				$.ajax({
 	 					url:basePath+"/channel/getChannelById",
 	 					type:"post",
 	 					datatype:"json",
 	 					data:{
 	 						channel_id:channel_id
 	 					},
 	 					success:function(data){
 	 						$("#channel_id_u").val(data.channel.channel_id);
 	 						$("#channel_name_u").val(data.channel.channel_name);
 	 						$("#channel_order_u").val(data.channel.channel_order);
 	 						$("#channel_desc_u").val(data.channel.channel_desc);
 	 					}
 	 				});
 	 		 	});
 	 			page(data);
 	 		}
 	 	});
 	};
 	
 	
 	
 	//更新栏目 表单 监听提交
	  form.on('submit(updateChannelform)', function(data){
		 var channel_name = $("#channel_name_u").val().trim();
		 var channel_order = $("#channel_order_u").val().trim();
		 var channel_desc = $("#channel_desc_u").val().trim();
		 var channel_id = $("#channel_id_u").val();
		 $.ajax({
			url:basePath+"/channel/updateChannel",
			type:"post",
			data:{
				channel_name:channel_name,
				channel_order:channel_order,
				channel_desc:channel_desc,
				channel_id:channel_id,
			},
			datatype:"json",
			success:function(data){
				if(data.count>0){
					layer.msg("更新成功");
					layer.close(updateChannelOpen);
					loadChannelList();
					return;
				}
				layer.alert("更新失败");
			}
		});
		return false;
	  });
 	
 	//新增栏目
 	$("#addChannel_id").click(function(){
 		//清空原有内容
 		$("#channel_name").val('');
 		$("#channel_order").val('');
 		$("#channel_desc").val('');
 		//打开弹窗
 		 addChannelOpen = layer.open({
 			type:"1",
 			title:"新增栏目",
 			area:['400px','350px'],
 			content:$("#addChannelDialog_id")
 		});
 	});
 	
 	$("#deleteChannel_id").click(function(){
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
 					ids.push($(da).parent().parent().find(".channel_id_td").text());			
 	 			}
 	 		});
 	 		
 	 		$.ajax({
 				url:basePath+"/channel/deleteChannelByIds",
 				type:"post",
 				data:{
 					ids:ids
 				},
 				datatype:"json",
 				success:function(data){
 					if(data.count>0){
 						layer.msg("删除成功");
 						loadChannelList();
 						return;
 					}
 					layer.alert("删除失败");
 				}
 			});
        });
 	});
 	
 	//新增栏目 表单 监听提交
 	  form.on('submit(addChannelform)', function(data){
 		 var channel_name = $("#channel_name").val().trim();
		 var channel_order = $("#channel_order").val().trim();
		 var channel_desc = $("#channel_desc").val().trim();
		 
		 $.ajax({
			url:basePath+"/channel/addChannel",
			type:"post",
			data:{
				channel_name:channel_name,
				channel_order:channel_order,
				channel_desc:channel_desc
			},
			datatype:"json",
			success:function(data){
				if(data.count>0){
					layer.msg("新增成功");
					layer.close(addChannelOpen);
					loadChannelList();
					return;
				}
				layer.alert("新增失败");
			}
		});
		return false;
 	  });
 	
 	
});

</script>
</html>