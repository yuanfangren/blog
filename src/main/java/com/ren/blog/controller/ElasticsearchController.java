package com.ren.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ren.blog.bean.TagBean;
import com.ren.blog.elasticsearch.ESUtils;

/**
 * 搜索控制器
 * @author RYF
 *
 */
@Controller
public class ElasticsearchController {
	private Logger logger = Logger.getLogger(ElasticsearchController.class);
	
	@ResponseBody
	@RequestMapping(value = "/front/elasticsearch/search",method=RequestMethod.POST)
	public JSONObject search(String query) {
		JSONObject jo = new JSONObject();
		jo.put("list", serachSpnews(query));
		return jo;
	}
	
	public ArrayList<Map<String,Object>>  serachSpnews(String query) {
		TransportClient client = ESUtils.getSingleClient();
		MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(query, "article_title","article_content");
		HighlightBuilder highlightBuilder = new HighlightBuilder().preTags("<span style='color:red'>")
										.postTags("</span>")
										.field("article_title")
										.field("article_content");
		SearchResponse searchResponse = client.prepareSearch("spnews")
									.setTypes("news")
									.setQuery(multiMatchQueryBuilder)
									.highlighter(highlightBuilder)
									.setFrom(0)
									.setSize(10)
									.execute()
									.actionGet();
		SearchHits hits = searchResponse.getHits();
		ArrayList<Map<String,Object>> newslist = new ArrayList<>();
		for (SearchHit hit : hits) {
			Map<String, Object> news = hit.getSourceAsMap();
			HighlightField hTitle = hit.getHighlightFields().get("article_title");
			if(hTitle != null) {
				Text[] fragments = hTitle.fragments();
				String hTitleStr = "";
				for(Text text : fragments) {
					hTitleStr += text;
				}
				news.put("article_title", hTitleStr);
			}
			
			HighlightField hContent = hit.getHighlightFields().get("article_content");
			if(hContent != null) {
				Text[] fragments = hContent.fragments();
				String hCoontentStr = "";
				for(Text text : fragments) {
					hCoontentStr += text;
				}
				news.put("article_content", hCoontentStr);
			}
			newslist.add(news);
		}
		return newslist;
	}

}
