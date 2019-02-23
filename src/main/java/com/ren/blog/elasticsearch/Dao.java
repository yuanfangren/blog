package com.ren.blog.elasticsearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;


/**
 * mysql数据写入es
 * @author RYF
 *
 */
public class Dao {
	private Connection conn;
	public void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String user ="root";
			String passwd = "root";
			String url = "jdbc:mysql://localhost:3306/blog";
			conn = DriverManager.getConnection(url, user, passwd);
			if(conn != null) {
				System.out.println("mysql连接成功");
			}else {
				System.out.println("mysql连接失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mysqlToEs() {
		String sql = "select * from blog_article";
		TransportClient client = ESUtils.getSingleClient();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String, Object> map = new HashMap<>();
			while(rs.next()) {
				int id = rs.getInt(1);
				map.put("article_id", id);
				map.put("article_title", rs.getString(2));
				map.put("article_content", rs.getString("article_content"));
				System.out.println(map);
				client.prepareIndex("spnews", "news", String.valueOf(id))
					.setSource(map)
					.execute()
					.actionGet();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
