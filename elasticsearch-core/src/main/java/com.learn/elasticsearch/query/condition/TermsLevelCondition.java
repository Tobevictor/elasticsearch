package com.learn.elasticsearch.query.condition;

/**
 * Terms Level Query Condition
 * @date 2019/8/21 10:06
 * @author dshuyou
 */
public class TermsLevelCondition extends BaseCondition {
	private String field;
	private String value;
	private String[] values;
	private String[] ids;
	private String gte;
	private String lte;

	public TermsLevelCondition(){
		super();
	}

	public TermsLevelCondition(int from, int size){
		super(from, size);
	}

	public TermsLevelCondition(String field, String gte, String lte){
		super();
		this.field = field;
		this.gte = gte;
		this.lte = lte;
	}

	public TermsLevelCondition(int from, int size, String field, String gte, String lte){
		super(from,size);
		this.field = field;
		this.gte = gte;
		this.lte = lte;
	}

	public TermsLevelCondition(String field, String value){
		super();
		this.field = field;
		this.value = value;
	}

	public TermsLevelCondition(int from, int size, String field, String value){
		super(from,size);
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getGte() {
		return gte;
	}

	public String getLte() {
		return lte;
	}

	public String getValue() {
		return value;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
}

