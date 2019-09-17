package com.learn.elasticsearch.query.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/9/17 16:24
 */
public class BoolCondition extends BaseCondition {
	private String[] queryType;
	private BaseCondition[] conditions;

	public BoolCondition(){super();}

	public BoolCondition(int from, int size){
		super(from,size);
	}

	public BoolCondition(int from, int size, String[] queryType, BaseCondition[] conditions){
		super(from, size);
		this.queryType = queryType;
		this.conditions = conditions;
	}

	public Map<String,BaseCondition> setConditions(){
		Map<String, BaseCondition> map = new HashMap<>();
		for (int i = 0;i < queryType.length;i++){
			map.put(queryType[i],conditions[i]);
		}
		return map;
	}

	public String[] getQueryType() {
		return queryType;
	}

	public void setQueryType(String[] queryType) {
		this.queryType = queryType;
	}

	public BaseCondition[] getConditions() {
		return conditions;
	}

	public void setConditions(BaseCondition[] conditions) {
		this.conditions = conditions;
	}
}
