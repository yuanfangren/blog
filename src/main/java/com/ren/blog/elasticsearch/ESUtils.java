package com.ren.blog.elasticsearch;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * ES工具类
 * @author RYF
 *
 */
public class ESUtils {
	
	public static final String CLUSTER_NAME = "elasticsearch";
	
	public static final String HOST_IP ="127.0.0.1";
	
	public static final int TCP_PORT =9300;
	
	private static volatile TransportClient client;
	
	static Settings settings = Settings.builder().put("cluster.name",CLUSTER_NAME).build();
	
	public static TransportClient getSingleClient() {
		if(client == null) {
			synchronized (TransportClient.class) {
				if(client == null) {
					try {
						client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_IP), TCP_PORT));
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return client;
	}
	
	public static IndicesAdminClient getAdminClient() {
		return getSingleClient().admin().indices();
	}
	
	public static boolean createIndex(String indexName,int shards,int replicas) {
		Settings settings = Settings.builder().put("index.number_of_shards",shards)
					.put("index.number_of_replicas",replicas).build();
		CreateIndexResponse createIndexResponse = getAdminClient().prepareCreate(indexName.toLowerCase()) 
					.setSettings(settings)
					.execute().actionGet();
		boolean isIndexCreated = createIndexResponse.isAcknowledged();
		if(isIndexCreated) {
			System.out.println("索引" + indexName + "创建成功");
		}else {
			System.out.println("索引" + indexName + "创建失败");
		}
		return isIndexCreated;
	}
	
	public static boolean setMapping(String idnexName,String typeName,String mapping) {
		getAdminClient().preparePutMapping(idnexName)
			.setType(typeName)
			.setSource(mapping,XContentType.JSON)
			.get();
		return false;
	} 
	
}
