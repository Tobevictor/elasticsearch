package com.learn.common.elastic.query.search.deprecated;

import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/17
 *
 */
public interface Search<T> {
	List<String> matchAllSearch();

	List<String> matchSearch(String field, String term, String indice);

	List<String> fuzzySearch(String field, String term, int size,String indice);

	List<String> idsSearch(String[] ids,String indice);

	List<String> multiSearch(String field, String[] terms, String indices);

	List<String> rangeSearch(String field, String gte, String lte, int size, String indice);

	List<String> prefixSearch(String field, String prefix,String indice);

	List<String> termSearch(String field, String term, String indices);

	List<String> termsSearch(String field, String[] terms, String indice);

}
