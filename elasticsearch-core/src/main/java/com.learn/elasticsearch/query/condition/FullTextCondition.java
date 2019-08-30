package com.learn.elasticsearch.query.condition;

/**
 * @Date 2019/8/21 10:05
 * @author dshuyou
 */
public class FullTextCondition extends BaseCondition {
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

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "field:" + field + ",value:" + value + ",from:" + from + ",size:" + size;
	}


}
