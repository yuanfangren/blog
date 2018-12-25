package com.ren.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ren.blog.bean.ArticleBean;
import com.ren.blog.bean.ArticleTagBean;
import com.ren.blog.service.ArticleService;
import com.ren.blog.util.CommonUtils;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.PageUtils;
import com.ren.blog.util.UnifyResultJsonUtils;

import sun.misc.BASE64Decoder;

/**
 * 文章管理控制器
 * @author RYF
 *
 */
@SuppressWarnings("restriction")
@Controller
public class ArticleController {
	
	private Logger logger = Logger.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 粘贴复制上传图片
	 * @param pasteImg
	 * @param req
	 * @param res https://www.jianshu.com/p/824fe2f034ef
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/editormdPastePic")
	public JSONObject editormdPastePic(String  pasteImg,
			HttpServletRequest req,HttpServletResponse res){
		String extName=pasteImg.substring(11, 14);//后缀名
		 String tmpName = System.currentTimeMillis()+"."+extName;
		 String path = req.getSession().getServletContext().getRealPath(File.separator+"articleImg"+File.separator);
		 File dirFile = new File(path, tmpName);
		 JSONObject json = new JSONObject();
		 try {
			 if(!dirFile.exists()){
				 dirFile.createNewFile();
			 }
			 BASE64Decoder decoder = new BASE64Decoder();
			 String[] imgurl=pasteImg.split(",");
			 byte[] b = decoder.decodeBuffer(imgurl[1]);   
			 OutputStream out = new FileOutputStream(dirFile);
			 out.write(b);  
	         out.flush();  
	         out.close();
	         json.put("url", req.getContextPath()+File.separator+"articleImg"+File.separator+tmpName);
			 json.put("success", 1);
			 json.put("message", "上传成功");
		} catch (Exception e) {
			json.put("url", "");
			json.put("success", 0);
			json.put("message", "上传失败");
		}
	     return json;
	}
	
	/**
	 * 上传文件-点击editormd
	 * @param file
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/editormdPic")
	public JSONObject editormdPic(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, 
			HttpServletRequest req,HttpServletResponse res){
		 String trueFileName = file.getOriginalFilename();  
		 String tmpName = System.currentTimeMillis()+trueFileName;
		 String path = req.getSession().getServletContext().getRealPath(File.separator+"articleImg"+File.separator);
		 File dirFile = new File(path, tmpName);
		 if(!dirFile.exists()){
			 dirFile.mkdirs();
		 }
		 JSONObject json = new JSONObject();
		 try {
			file.transferTo(dirFile);
			json.put("url", req.getContextPath()+File.separator+"articleImg"+File.separator+tmpName);
			json.put("success", 1);
			json.put("message", "上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("url", "");
			json.put("success", 0);
			json.put("message", "上传失败");
		}
	     return json;
	}
	
	/**
	 * 新增或更新文章 
	 * @param article 文章 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/addArticle",method=RequestMethod.POST)
	public JSONObject addArticle(HttpServletResponse res,HttpServletRequest req,
			ArticleBean article,@RequestParam(value ="tags[]",required=false) String[] tags){
		try {
			
			
			if(tags != null) {
				//标签非空验证是否重复
				boolean isRepeat = CommonUtils.cheakIsRepeat(tags);
				if(isRepeat) {
					return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,2, "标签有重复");
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			article.setArticle_createtime(date);
			article.setArticle_updatetime(date);
			String remark = article.getArticle_remark().replace("  ", "");
			if(remark.length()>200) {
				remark = remark.substring(0, 200);
			}
			article.setArticle_remark(remark);
			int count = 0;
			if(article.getArticle_id()>0){
				 count = articleService.updateArticle(article,tags);
					if(count > 0) {
						 JSONObject jo = new JSONObject();
						 jo.put("article_id", article.getArticle_id());
						 logger.info("更新文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title());
						return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "更新成功",jo);
					}else {
						 logger.info("更新文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title() +"未成功");
						return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "更新未成功");
					}
				
			}else{
				 count = articleService.addArticle(article,tags);
				 if(count > 0) {
					 JSONObject jo = new JSONObject();
					 jo.put("article_id", article.getArticle_id());
					 logger.info("新增文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title());
					return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "新增成功",jo);
				}else {
					logger.info("新增文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title()+"未成功");
					return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "新增未成功");
				}
			}
		} catch (Exception e) {
			logger.error("新增或者更新文章：【"+article.getArticle_title()+"】异常", e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "操作异常");
		}
	}
	
	
	/**
	 * 分页获取文章列表
	 * @param page 分页信息 pageSize每页大小 pageNum 当前页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/getListPage",method=RequestMethod.POST)
	public JSONObject getListPage(PageUtils page){
		JSONObject jo = new JSONObject();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<ArticleBean> list = articleService.getList();
		//获取文章个数
		int count = articleService.getArticleCount();
		page.setTotal(count);
		jo.put("list", list);
		jo.put("page", page);
		return jo;
	}
	
	/**
	 * 根据文章ID获取文章
	 * @param article_id 文章ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/getArticleById",method=RequestMethod.POST)
	public JSONObject getArticleById(int article_id){
		JSONObject jo = new JSONObject();
		ArticleBean article = articleService.getArticleById(article_id);
		List<ArticleTagBean> at = null;
		if(article != null) {
			at = articleService.getArticleTagByArticleId(article_id);
		}		jo.put("article", article);
		jo.put("tag", at);
		return jo;
	}
	
	/**
	 * 根据文章ID获取文章 只能查询到已发布文章，若该文章未发布，则查询不到
	 * @param article_id 文章ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/getArticleByIdAndPublic",method=RequestMethod.POST)
	public JSONObject getArticleByIdAndPublic(int article_id){
		JSONObject jo = new JSONObject();
		ArticleBean article = articleService.getArticleByIdAndPublic(article_id);
		List<ArticleTagBean> at = null;
		if(article != null) {
			at = articleService.getArticleTagByArticleId(article_id);
		}
		jo.put("article", article);
		jo.put("tag", at);
		return jo;
	}
	
	/**
	 * 根据栏目ID获取相关文章list
	 * @param channel_id 栏目ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/front/article/getArticleByChannelId",method=RequestMethod.POST)
	public JSONObject getArticleByChannelId(int channel_id){
		JSONObject jo = new JSONObject();
		List<ArticleBean> articles = articleService.getArticleByChannelId(channel_id);
		jo.put("article", articles);
		return jo;
	}
	
	/**
	 * 根据标签ID获取相关文章list
	 * @param tag_id 标签ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/front/article/getArticleByTagId",method=RequestMethod.POST)
	public JSONObject getArticleByTagId(int tag_id){
		JSONObject jo = new JSONObject();
		List<ArticleBean> articles = articleService.getArticleByTagId(tag_id);
		jo.put("article", articles);
		return jo;
	}
 
	/**
	 * 删除文章 
	 * @param ids 文章id数组[]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/deleteArticleByIds",method=RequestMethod.POST)
	public JSONObject deleteArticleByIds(@RequestParam(value = "ids[]") int[] ids){
		try {
			int count = articleService.deleteArticleByIds(ids);
			if(count > 0) {
				logger.info("删除文章：IDS="+ids);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "删除成功");
			}else {
				logger.info("删除文章：IDS="+ids+"未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "删除未成功");
			}
		} catch (Exception e) {
			logger.error("删除文章：IDS=【"+ids+"】异常", e);	
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "删除异常");
		}
	}
	
	/**
	 * 按照更新时间-发布时间的顺序 获取所有已发布的文章列表(不包括内容)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/front/article/getAllList",method=RequestMethod.POST)
	public JSONObject getList(){
		JSONObject jo = new JSONObject();
		List<ArticleBean> list = articleService.getAllList();
		jo.put("list", list);
		return jo;
	}
	
	/**
	 * 根据文章ID更新文章状态
	 * @param article 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/updateArticleStatus",method=RequestMethod.POST)
	public JSONObject updateArticleStatus(ArticleBean article){
		int count  = articleService.updateArticleStatus(article);
		if(count >0) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "更新成功");
		}else {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "更新失败");
		}
	}
	
}
