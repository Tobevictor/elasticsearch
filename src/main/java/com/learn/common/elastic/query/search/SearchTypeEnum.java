package com.learn.common.elastic.query.search;

/**
 * @Date 2019/7/25 11:23
 * @Created by dshuyou
 */
public enum SearchTypeEnum {
	matchAllSearch(0,"matchAllSearch"),

	matchSearch(1,"matchSearch"),

	fuzzySearch(2,"fuzzySearch"),

	idsSearch(3,"idsSearch"),

	matchPhraseSearch(4,"matchPhraseSearch"),

	rangeSearch(5,"rangeSearch"),

	prefixSearch(6,"prefixSearch"),

	termSearch(7,"termSearch"),

	matchPhrasePrefixSearch(8,"matchPhrasePrefixSearch");


	public int index;
	public String name;

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	SearchTypeEnum(int index,String name) {
		this.index = index;
		this.name = name;
	}

	@Override
	public String toString() {
		return "index: " + this.index + ", name: " + this.name;
	}

}
