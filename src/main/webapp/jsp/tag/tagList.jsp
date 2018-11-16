<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签管理列表</title>
<link rel="stylesheet" href="<%=basePath%>/plug/layui/css/layui.css">
<script type="text/javascript" src="<%=basePath%>/plug/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/layui_config.js"></script>

<style type="text/css">
.tool_c{
	margin-top:10px;
	margin-left: 10px;
}
.layuiopen_c{
	display: none;
}
.addTagForm_c{
	padding-right: 10px;
	padding-top: 10px;
}
</style>
</head>
<body>
<div >
<%@ include file = "../home.jspt" %>
	<div class="tool_c">
		<button id="addTag_id" class="layui-btn layui-btn-primary layui-btn-sm">新增标签</button>
		<button id="deleteTag_id" class="layui-btn layui-btn-primary layui-btn-sm">删除标签</button>
	</div>
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<td><input class='thead_checkbox_c' type="checkbox"></td>
					<td>标签ID</td>
					<td>标签名称</td>
					<td>操作</td>
				</tr>
			</thead>
			
			<tbody id="TagList_tbody_id">
			
			</tbody>
		</table>
		
		<div id="page"></div>
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
			      <button class="layui-btn" lay-submit lay-filter="addTagform">新增</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			 </div>
		</form>
	</div>
	
	<div id="updateTagDialog_id" class="layuiopen_c">
		<form action="" class="layui-form addTagForm_c">
			<input type="hidden" id="Tag_id_u">
			<div class="layui-form-item">
		    	<label class="layui-form-label">标签名称</label>
		    	<div class="layui-input-block">
		      		<input type="text" name="Tag_name_u" id="Tag_name_u" required  lay-verify="required|long15" placeholder="请输入标签名称" autocomplete="off" class="layui-input">
		      	</div>
		    </div>
		    
		     <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit lay-filter="updateTagform">编辑</button>
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
layui.use(['layer','form','laydate','laypage','common'],function(){
	var layer = layui.layer;
	var form = layui.form;
	var laydate = layui.laydate;
	var laypage = layui.laypage;
 	var $ = layui.$;
 	var common = layui.common;
	common.ajaxSetUp();
 	var addTagOpen;//新增标签的弹窗
 	var updateTagOpen;//编辑标签的弹窗
 	
 	form.verify({
 		long15:function(value){
  			if(value.trim().length>15){
  				return "名称不能超过15个字符";
  			}
  		}
 	}); 
 	
 	//表头checkbox点击事件
 	$(".thead_checkbox_c").on("click",function(){
 		if($(".thead_checkbox_c").is(":checked")){
 			$(".tbody_checkbox_c").prop("checked",true);
 		}else{
 			$(".tbody_checkbox_c").prop("checked",false);
 		}
 	});
 	
 	loadTagList();
 	
 	$(function(){
 		$(".layui-nav .layui-nav-item").removeClass("layui-this");
 		$("[name=tagManager]").addClass("layui-this");
 	});
 	
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
 		    		loadTagList();
 		    	}
 		    }
 		  });
 	}
 	
 	//加载标签
 	function loadTagList(){
 		//加载标签列表
 	 	$.ajax({
 	 		url:basePath+"/Tag/getListPage",
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
 	 				htm+="<td class='Tag_id_td'>"+da.tag_id+"</td>";
 	 				htm+="<td>"+da.tag_name+"</td>";
 	 				htm+='<td><button name="updateTag_n" class="layui-btn layui-btn-primary layui-btn-xs">编辑</button></td>';
 	 				htm+="</tr>"
 	 			});
 	 			$("#TagList_tbody_id").empty();
 	 			$("#TagList_tbody_id").append(htm);
 	 			
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
 	 			$("button[name='updateTag_n']").on('click',function(){
 	 				var Tag_id = $(this).parent().parent().find(".Tag_id_td").text();
 	 				if(!Tag_id){
 	 					layer.alert("标签ID为空");
 	 					return;
 	 				}
 	 				//编辑标签弹窗
 	 				updateTagOpen = layer.open({
 	 					type:"1",
 	 					title:"编辑标签",
 	 					area:['400px','200px'],
 	 					content:$("#updateTagDialog_id")
 	 				});
 	 				//根据标签ID获取标签信息
 	 				$.ajax({
 	 					url:basePath+"/Tag/getTagById",
 	 					type:"post",
 	 					datatype:"json",
 	 					data:{
 	 						Tag_id:Tag_id
 	 					},
 	 					success:function(data){
 	 						$("#Tag_id_u").val(data.Tag.tag_id);
 	 						$("#Tag_name_u").val(data.Tag.tag_name);
 	 					}
 	 				});
 	 		 	});
 	 			page(data);
 	 		}
 	 	});
 	};
 	
 	
 	
 	//更新标签 表单 监听提交
	  form.on('submit(updateTagform)', function(data){
		 var Tag_name = $("#Tag_name_u").val().trim();
		 var Tag_id = $("#Tag_id_u").val();
		 $.ajax({
			url:basePath+"/Tag/updateTag",
			type:"post",
			data:{
				Tag_name:Tag_name,
				Tag_id:Tag_id,
			},
			datatype:"json",
			success:function(data){
				if("ok" == data.status){
					layer.msg(data.msg);
					layer.close(updateTagOpen);
					loadTagList();
					return;
				}else{
					layer.alert(data.msg);
				}
				
			}
		});
		return false;
	  });
 	
 	//新增标签
 	$("#addTag_id").click(function(){
 		//清空原有内容
 		$("#Tag_name").val('');
 		$("#Tag_order").val('');
 		$("#Tag_desc").val('');
 		//打开弹窗
 		 addTagOpen = layer.open({
 			type:"1",
 			title:"新增标签",
 			area:['400px','200px'],
 			content:$("#addTagDialog_id")
 		});
 	});
 	
 	$("#deleteTag_id").click(function(){
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
 					ids.push($(da).parent().parent().find(".Tag_id_td").text());			
 	 			}
 	 		});
 	 		
 	 		$.ajax({
 				url:basePath+"/Tag/deleteTagByIds",
 				type:"post",
 				data:{
 					ids:ids
 				},
 				datatype:"json",
 				success:function(data){
 					if("ok" == data.status){
 						layer.msg(data.msg);
 						pageNum = 1;
						$(".thead_checkbox_c").attr("checked",false);
 						loadTagList();
 						return;
 					}else{
 						layer.alert(data.msg);
 					}
 				}
 			});
        });
 	});
 	
 	//新增标签 表单 监听提交
 	  form.on('submit(addTagform)', function(data){
 		 var Tag_name = $("#Tag_name").val().trim();
		 
		 $.ajax({
			url:basePath+"/Tag/addTag",
			type:"post",
			data:{
				Tag_name:Tag_name,
			},
			datatype:"json",
			success:function(data){
				if("ok" == data.status){
					layer.msg(data.msg);
					layer.close(addTagOpen);
					loadTagList();
					return;
				}else{
					layer.alert(data.msg);
				}
			}
		});
		return false;
 	  });
 	
 	
});

</script>
</html>