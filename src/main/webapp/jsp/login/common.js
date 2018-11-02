layui.define(['layer','form'], function(exports) {
	var $ = layui.$;
	var MOD_NAME = 'common';	//模块名
	
	var mod_obj = {
	};	
	
	//用户名校验
	mod_obj.username ={
			rule:/[0-9a-zA-Z_]{5,14}/,
			desc:"用户名需要5-14个字符，只能是数字、字母、下划线"
	};
	
	//密码校验
	mod_obj.password ={
			rule:/^[0-9A-Za-z_@]{6,14}$/,
			desc:"密码需要6-14个字符 ,只能是字母、数字、下划线、@"
	}
	//邮箱校验
	mod_obj.email1 ={
			rule:/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
			desc:"邮箱格式不正确"
	}
	
	//密码一致性校验
	mod_obj.samepas=function(p1,p2){
    	if(p1 != p2){
    		return '两次密码不一致'
    	}
	}
	
	//用户名重复校验
	mod_obj.usernamerepeat=function(value){
		var msg = "";
	   	 $.ajax({
		    	  url:basePath+"/user/usernamerepeat",
		    	  type:"post",
		    	  async: false, 
		    	  data:{
		    		  user_username:value.trim()
		    	  },
		    	  success:function(result){
		    		 if(result.status == "ok"){
		    			 msg = "用户名已存在";
		    		 } 
		    	  },
		    	  error:function(result){
		    		  msg =  "用户名存在校验失败";
		    	  }
		      });
	   	 if(msg != ""){
	   		 return msg;
	   	 }
	}
	exports(MOD_NAME, mod_obj);
	
});
