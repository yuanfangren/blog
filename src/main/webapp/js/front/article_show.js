layui.use(['layer','form','laydate','laypage'],function(){
	var layer = layui.layer;
});
$(function(){
		var article_id = getUrlParam("article_id");
		var article = Number(article_id);
		if(!isNaN(article)){//如果是数字
		//根据文章ID加载文章
		$.ajax({
			type:"post",
			url:"../article/getArticleById",
			data:{
				article_id:article
			},
			success:function(data){
				var article = data.article;
				console.log(article);
				$("#article_title_id").text(article.article_title);
				$("#channel_name_id").text(article.channel_name);
				$("#article_updatetime_id").text(article.article_updatetime);
				$("#article_contenttextarea_id").val(article.article_content);
				editormd.markdownToHTML("article_content_id", {
				    htmlDecode      : "style,script,iframe",  
				    emoji           : true,
				    taskList        : true,
				    tex             : true,  // 默认不解析
				    flowChart       : true,  // 默认不解析
				    sequenceDiagram : true  // 默认不解析
				}); 
			}
		});
		}
});
function getUrlParam(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
}