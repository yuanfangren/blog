package com.ren.blog.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 主函数
 * @author RYF
 *
 */
public class Main{
	public static void main(String[] args) {
		//创建索引
		ESUtils.createIndex("spnews", 3, 0);
		//设置mapping
		try {
			XContentBuilder builder = jsonBuilder().startObject().startObject("properties")
									.startObject("article_id")
									.field("type","long").endObject()
									.startObject("article_title").field("type", "text").field("analyzer", "ik_max_word")
										.field("search_analyzer", "ik_max_word").endObject()
									.startObject("article_content").field("type", "text").field("analyzer", "ik_max_word")
										.field("search_analyzer", "ik_max_word").endObject()
									.endObject()
									.endObject();
			ESUtils.setMapping("spnews", "news", builder.string());
									
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//mysql读取
		Dao dao = new Dao();
		dao.getConnection();
		dao.mysqlToEs();
	}
}
