package com.learn.common.elastic.condition;

/**
 * @Date 2019/8/12 11:18
 * @Created by dshuyou
 */
public class FullTextCondition extends QueryCondition{

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
}
