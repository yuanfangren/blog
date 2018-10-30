package com.ren.blog.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ren.blog.bean.Article;
import com.ren.blog.service.ArticleService;
import com.ren.blog.util.PageUtils;

/**
 * 文章管理控制器
 * @author RYF
 *
 */
@Controller
public class ArticleController {
	
	private Logger logger = Logger.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 新增文章 
	 * @param article 文章
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/addArticle",method=RequestMethod.POST)
	public JSONObject addArticle(HttpServletResponse res,HttpServletRequest req,
			Article article){
		JSONObject jo = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			article.setArticle_createtime(date);
			article.setArticle_updatetime(date);
			
			String remark = article.getArticle_remark().replace(" ", "").substring(0, 200);
			article.setArticle_remark(remark);
			int count = 0;
			if(article.getArticle_id()>0){
				 count = articleService.updateArticle(article);
				 logger.info("更新文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title());
			}else{
				 count = articleService.addArticle(article);
				 logger.info("新增文章：ID="+article.getArticle_id()+" 标题："+article.getArticle_title());
			}
			jo.put("count", count);
			jo.put("article_id",article.getArticle_id());
		} catch (Exception e) {
			logger.error("新增文章：【"+article.getArticle_title()+"】异常", e);
		}
		return jo;
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
		List<Article> list = articleService.getList();
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
		Article article = articleService.getArticleById(article_id);
		jo.put("article", article);
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
		JSONObject jo = new JSONObject();
		try {
			int count = articleService.deleteArticleByIds(ids);
			jo.put("count", count);
			logger.info("删除文章：IDS="+ids);
		} catch (Exception e) {
			logger.error("删除文章：IDS=【"+ids+"】异常", e);
		}
		return jo;
	}
	
	/**
	 * 按照更新时间-发布时间的顺序 获取所有已发布的文章列表(不包括内容)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/article/getAllList",method=RequestMethod.POST)
	public JSONObject getList(){
		JSONObject jo = new JSONObject();
		List<Article> list = articleService.getAllList();
		jo.put("list", list);
		return jo;
	}
}
