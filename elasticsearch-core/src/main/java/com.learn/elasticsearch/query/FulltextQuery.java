package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @Date 2019/8/21 10:04
 * @author dshuyou
 */
public class FulltextQuery extends BaseQuery{
	private FulltextEnum queryType = FulltextEnum.matchAllQuery;

	public FulltextQuery(String index, RestHighLevelClient client){
		super(index,client);
	}

	public FulltextQuery(String index, RestHighLevelClient client, FulltextEnum type){
		super(index, client);
		if(type != null){
			queryType = type;
		}
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) throws IOException {

		sourceBuilder.query(queryBuilder(baseCondition));
		if(sourceBuilder == null){
			return Collections.emptyList();
		}
		return returnList(sourceBuilder,baseCondition);
	}

	@Override
	public QueryBuilder queryBuilder(BaseCondition baseCondition){
		if (!(baseCondition instanceof FullTextCondition)) {
			throw new IllegalArgumentException("fulltext query need fulltext query condition");
		}

		FullTextCondition condition = (FullTextCondition) baseCondition;
		QueryBuilder queryBuilder;
		if(queryType == null){
			return matchAllBuilder();
		}
		switch (queryType){
			case matchAllQuery :
				queryBuilder = matchAllBuilder();
				break;
			case matchPhraseQuery:
				queryBuilder = matchPhraseBuilder(condition);
				break;
			case matchPhrasePrefixQuery:
				queryBuilder = matchPhrasePrefixBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported FullText search type");
		}
		return queryBuilder;
	}

	private QueryBuilder matchAllBuilder(){
		return new MatchAllQueryBuilder();
	}

	private QueryBuilder matchPhraseBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new MatchPhraseQueryBuilder(field,value).analyzer("ik_smart");
	}

	private QueryBuilder matchPhrasePrefixBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();
		MatchPhrasePrefixQueryBuilder builder = new MatchPhrasePrefixQueryBuilder(field,value).analyzer("ik_smart");
		builder.maxExpansions(10);

		return builder;
	}
}
