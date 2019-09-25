package com.learn.elasticsearch.aggregate;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author dshuyou
 * @Date 2019/9/25 11:44
 */
public class Aggregation {

	public List<String> aggregate(String field, String keyword){
		List<String> returnList = new ArrayList<>();
		AggregationBuilder aggregationBuilder = new MaxAggregationBuilder("").field("");









		return new ArrayList<>(returnList);
	}
}
