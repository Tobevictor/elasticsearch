/*
package com.learn.common.elasticsearch.test;

import com.learn.common.elasticsearch.model.IndexQuery;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

*/
/**
 * @Date 2019/8/22 9:33
 * @Created by dshuyou
 *//*

public interface ElasticsearchOperations {

	*/
/**
	 * @return elasticsearch client
	 *//*

	RestHighLevelClient getClient();

	*/
/**
	 * Create an index for given indexName
	 *
	 * @param indexName
	 *//*

	boolean createIndex(String indexName) throws IOException;

	*/
/**
	 * Create an index for given indexName and Settings
	 *
	 * @param indexName
	 * @param settings
	 *//*

	boolean createIndexWithSettings(String indexName, Object settings) throws IOException;

	*/
/**
	 * Create mapping for a given indexName
	 *
	 * @param indexName
	 * @param mappings
	 *//*

	boolean putMapping(String indexName, Object mappings) throws IOException;

	*/
/**
	 * Get mapping for a given indexName
	 *
	 * @param indexName
	 *//*

	Map getMapping(String indexName);

	*/
/**
	 * Get settings for a given indexName
	 *
	 * @param indexName
	 *//*

	String getSetting(String indexName) throws IOException;

	*/
/**
	 * Execute the query against elasticsearch and return result as {@link Page}
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> Page<T> queryForPage(SearchQuery query, Class<T> clazz);

	*/
/**
	 * Execute the query against elasticsearch and return result as {@link Page} using custom mapper
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> Page<T> queryForPage(SearchQuery query, Class<T> clazz, SearchResultMapper mapper);

	*/
/**
	 * Execute the query against elasticsearch and return result as {@link Page}
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> Page<T> queryForPage(CriteriaQuery query, Class<T> clazz);

	*/
/**
	 * Execute the query against elasticsearch and return result as {@link Page}
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> Page<T> queryForPage(StringQuery query, Class<T> clazz);

	*/
/**
	 * Execute the query against elasticsearch and return result as {@link Page} using custom mapper
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> Page<T> queryForPage(StringQuery query, Class<T> clazz, SearchResultMapper mapper);

	*/
/**
	 * Execute the criteria query against elasticsearch and return result as {@link List}
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 *//*

	<T> List<T> queryForList(CriteriaQuery query, Class<T> clazz);

	*/
/**
	 * Execute the string query against elasticsearch and return result as {@link List}
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 *//*

	<T> List<T> queryForList(StringQuery query, Class<T> clazz);

	*/
/**
	 * Execute the search query against elasticsearch and return result as {@link List}
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 *//*

	<T> List<T> queryForList(SearchQuery query, Class<T> clazz);

	*/
/**
	 * Execute the query against elasticsearch and return ids
	 *
	 * @param query
	 * @return
	 *//*

	<T> List<String> queryForIds(SearchQuery query);

	*/
/**
	 * return number of elements found by given query
	 *
	 * @param query
	 * @param clazz
	 * @return
	 *//*

	<T> long count(CriteriaQuery query, Class<T> clazz);

	*/
/**
	 * return number of elements found by given query
	 *
	 * @param query
	 * @return
	 *//*

	<T> long count(CriteriaQuery query);


	*/
/**
	 * return number of elements found by given query
	 *
	 * @param query
	 * @return
	 *//*

	<T> long count(SearchQuery query);

	*/
/**
	 * Execute a get against elasticsearch for the given id
	 *
	 * @param indexname
	 * @param id
	 * @return
	 *//*

	<T> Map get(String indexname, String id) throws IOException;

	*/
/**
	 * Execute a multiGet against elasticsearch for the given ids
	 *
	 * @param ids
	 * @param indexname
	 * @return
	 *//*

	<T> LinkedList<T> multiGet(String indexname, String[] ids) throws IOException;

	*/
/**
	 * Index an object. Will do save or update
	 *
	 * @param indexname
	 * @param id
	 * @param source
	 * @return returns the document id
	 *//*

	Object index(String indexname, String id, Object source) throws IOException;

	*/
/**
	 * Bulk index all objects. Will do save or update
	 *
	 * @param queries
	 *//*

	long bulkIndex(List<IndexQuery> queries) throws IOException;

	*/
/**
	 * Bulk update all objects. Will do update
	 *
	 * @param queries
	 *//*

	long bulkUpdate(List<com.learn.common.elasticsearch.model.UpdateQuery> queries) throws IOException;

	*/
/**
	 * Delete the one object with provided id
	 *
	 * @param indexName
	 * @param id
	 * @return documentId of the document deleted
	 *//*

	String delete(String indexName, String id) throws IOException;

	*/
/**
	 * Deletes an index for given indexName
	 *
	 * @param indexName
	 * @return
	 *//*

	boolean deleteIndex(String indexName) throws IOException;

	*/
/**
	 * check if index is exists for given IndexName
	 *
	 * @param indexName
	 * @return
	 *//*

	boolean indexExists(String indexName) throws IOException;

	*/
/**
	 * refresh the index
	 *
	 * @param indexName
	 *
	 *//*

	void refresh(String indexName) throws IOException;

	*/
/**
	 * Returns scrolled page for given query
	 *
	 * @param query The search query.
	 * @param scrollTimeInMillis The time in millisecond for scroll feature
	 * {@link org.elasticsearch.action.search.SearchRequestBuilder#setScroll(org.elasticsearch.common.unit.TimeValue)}.
	 * @param mapper Custom impl to map result to entities
	 * @return The scan id for input query.
	 *//*

	<T> Page<T> startScroll(long scrollTimeInMillis, SearchQuery query, Class<T> clazz, SearchResultMapper mapper);


	*/
/**
	 * Clears the search contexts associated with specified scroll ids.
	 *
	 * @param scrollId
	 *
	 *//*

	<T> void clearScroll(String scrollId);

	*/
/**
	 * more like this query to search for documents that are "like" a specific document.
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 *//*

	<T> Page<T> moreLikeThis(MoreLikeThisQuery query, Class<T> clazz);

	*/
/**
	 * adding new alias
	 *
	 * @param indexname
	 * @param aliasname
	 * @return
	 *//*

	Boolean addAlias(String indexname,String aliasname) throws IOException;

}

*/
