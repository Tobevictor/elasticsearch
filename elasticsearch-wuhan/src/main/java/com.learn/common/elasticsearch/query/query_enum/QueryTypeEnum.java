package com.learn.common.elasticsearch.query.query_enum;

/**
 * @Date 2019/7/25 11:23
 * @Created by dshuyou
 */
public enum QueryTypeEnum {
	matchAllQuery(0,"matchAllQuery"),

	matchQuery(1,"matchQuery"),

	fuzzyQuery(2,"fuzzyQuery"),

	idsQuery(3,"idsQuery"),

	matchPhraseQuery(4,"matchPhraseQuery"),

	rangeQuery(5,"rangeQuery"),

	prefixQuery(6,"prefixQuery"),

	termQuery(7,"termQuery"),

	termsQuery(8,"termsQuery"),

	matchPhrasePrefixQuery(9,"matchPhrasePrefixQuery"),

	boundingBoxQuery(10,"boundingBoxQuery"),

	disjointQuery(11,"disjointQuery"),

	distanceQuery(12,"distanceQuery"),

	geoShapeQuery(13,"geoShapeQuery"),

	intersectionQuery(14,"intersectionQuery"),

	polygonQuery(15,"polygonQuery"),

	withinQuery(16,"withinQuery");

	public int index;
	public String name;

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	QueryTypeEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	@Override
	public String toString() {
		return "index: " + this.index + ", name: " + this.name;
	}

}
