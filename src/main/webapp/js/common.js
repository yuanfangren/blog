layui.define([], function(exports){
  var $ = layui.$;
  var param={};
  
  param.ajaxSetUp=function(){
	  $.ajaxSetup( {
			//设置ajax请求结束后的执行动作
		    complete :
		        function(XMLHttpRequest, textStatus) {
		    		// 通过XMLHttpRequest取得响应头，sessionstatus
		            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		            if (sessionstatus == "TIMEOUT") {
		                var win = window;
		                while (win != win.top){
		                    win = win.top;
		                }
		                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
		            }
		        }
		});
  }
  exports('common', param);
});  

