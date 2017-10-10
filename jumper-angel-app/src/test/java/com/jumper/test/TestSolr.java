package com.jumper.test;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr {
	
	@Test
	public void testQuery() throws SolrServerException{
		String baseURL = "http://192.168.2.124:9090/solr/jumper";
		HttpSolrServer server = new HttpSolrServer(baseURL);
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		QueryResponse response = server.query(query);
		SolrDocumentList results = response.getResults();
		long numFound =results.getNumFound();
		System.out.println("总数："+numFound);
		for (SolrDocument solrDocument : results) {
			Collection<String> fieldNames = solrDocument.getFieldNames();
			for (String field : fieldNames) {
				System.out.println(field+":"+solrDocument.get(field));
			}
			System.out.println("========================");
		}
 	}
	
	@Test
	public void testAdd() throws Exception{
		String baseURL = "http://192.168.2.124:9090/solr/jumper";
		HttpSolrServer server = new HttpSolrServer(baseURL);
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "1");
		doc.setField("name", "crxy");
		UpdateResponse response = server.add(doc);
		System.out.println(response);
		server.commit();
	}
	
	@Test
	public void testDelete() throws Exception{
		String baseURL = "http://192.168.2.124:9090/solr/jumper";
		HttpSolrServer server = new HttpSolrServer(baseURL);
		
		server.deleteById("1");
		server.commit();
	}
}
