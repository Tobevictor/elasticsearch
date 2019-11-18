package com.learn.elasticsearch.query.condition;

import java.io.Serializable;

/**
 * Full Text Query Condition
 * @date 2019/8/21 10:05
 * @author dshuyou
 */
public class FullTextCondition extends BaseCondition implements Serializable {
	private String field;
	private String value;

	public FullTextCondition(){
		super();
	}

	public FullTextCondition(int from, int size){
		super(from, size);
	}

	public FullTextCondition(String field, String value){
		super();
		this.field = field;
		this.value = value;
	}

	public FullTextCondition(int from, int size, String field, String value){
		super(from, size);
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}


	@Override
	public String toString() {
		return "field:" + field + ",value:" + value + ",from:" + from + ",size:" + size;
	}


}
