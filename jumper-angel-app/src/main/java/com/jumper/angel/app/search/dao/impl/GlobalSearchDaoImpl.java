package com.jumper.angel.app.search.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.stereotype.Repository;

import com.jumper.angel.app.search.dao.GlobalSearchDao;
import com.jumper.angel.app.search.model.bo.HelperQuestionsBo;

/**
 * @Description 全局搜索DAO接口实现
 * @author 徐运贤
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Repository
public class GlobalSearchDaoImpl extends SimpleSolrRepository<HelperQuestionsBo, String> implements GlobalSearchDao {

	@Autowired
	SolrTemplate solrTemplate;
	
	@Override
	public Page<HelperQuestionsBo> findHelperQuestionsList(String title) throws Exception {
		// TODO Auto-generated method stub
		Query query = new SimpleQuery(new Criteria("title").contains(title));
		return solrTemplate.queryForPage(query,  HelperQuestionsBo.class);
	}

}
