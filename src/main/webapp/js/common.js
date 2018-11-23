layui.define(['layer'], function(exports){
  var $ = layui.$;
  var layer = layui.layer;
  var param={
      error_msg:"网站请求出现问题啦"
  };
  
  param.backToTop=function(classname){
	  $(window).scroll(function () {
	         if ($(window).scrollTop() > 100) {
	             $("."+classname).fadeIn(1000);
	         }
	         else {
	        	 $("."+classname).fadeOut(1000);
	         }
	     });
	   $("."+classname).click(function(){
			$('body,html').animate({scrollTop: 0}, 600); //1000代表1秒时间回到顶点
		});
  }
  
  param.ajaxSetUp=function(){
	  $.ajaxSetup( {
			//设置ajax请求结束后的执行动作
		    complete :
		        function(XMLHttpRequest, textStatus) {
		    		// 通过XMLHttpRequest取得响应头，sessionstatus
		            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		            if (sessionstatus == "TIMEOUT") {
		            	layer.confirm('登录超时了，重新登录?', function(index){
		            		var win = window;
			                while (win != win.top){
			                    win = win.top;
			                }
			                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
		            		  
		            		  layer.close(index);
		            	});  
		            }
		        }
		});
  }
  
  param.getUrlParam = function(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
  }
  
  exports('common', param);
});  

