package com.learn.common.elastic.condition;

/**
 * @Date 2019/7/31 14:55
 * @Created by dshuyou
 */
public class TermLevelCondition extends QueryCondition {

	private String field;
	private String value;
	private String[] values;
	private String[] ids;
	private String gte;
	private String lte;

	public TermLevelCondition(){
		super();
	}

	public TermLevelCondition(int from, int size){
		super(from, size);
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

	public void setGte(String gte) {
		this.gte = gte;
	}

	public String getLte() {
		return lte;
	}

	public void setLte(String lte) {
		this.lte = lte;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
}
