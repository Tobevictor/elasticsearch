package com.learn.elasticsearch.suggest;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author dshuyou
 * @Date 2019/9/24 14:47
 */
public class Suggestion {

	private final String FULL_PINYIN_SUGGEST = "FULL_PINYIN";
	private final String PREFIX_PINYIN_SUGGEST = "PREFIX_PINYIN";
	private final String TEXT_SUGGEST = "TEXT";
	private RestHighLevelClient client;

	public Suggestion(RestHighLevelClient client){
		this.client = client;
	}

	public List<String> suggest(String keyword, int size, String[] fields) throws IOException {
		LinkedHashSet<String> returnSet = new LinkedHashSet<>();
		SuggestBuilder suggestBuilder = new SuggestBuilder();

		if(checkIsChinese(keyword)){
			//汉字前缀匹配
			CompletionSuggestionBuilder suggestText = new CompletionSuggestionBuilder(fields[2])
					.text(keyword).size(size);
			suggestBuilder.addSuggestion(TEXT_SUGGEST, suggestText);
		}
		//全拼前缀匹配
		CompletionSuggestionBuilder fullPinyinSuggest = new CompletionSuggestionBuilder(fields[0])
				.text(keyword).size(size);
		//拼音搜字母前缀匹配
		CompletionSuggestionBuilder prefixPinyinSuggest = new CompletionSuggestionBuilder(fields[1])
				.text(keyword).size(size);


		suggestBuilder.addSuggestion(FULL_PINYIN_SUGGEST, fullPinyinSuggest)
				.addSuggestion(PREFIX_PINYIN_SUGGEST, prefixPinyinSuggest);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.suggest(suggestBuilder);

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.source(sourceBuilder);

		SearchResponse suggestResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		Suggest.Suggestion prefixPinyinSuggestion = suggestResponse.getSuggest().getSuggestion(FULL_PINYIN_SUGGEST);
		Suggest.Suggestion fullPinyinSuggestion = suggestResponse.getSuggest().getSuggestion(TEXT_SUGGEST);
		Suggest.Suggestion suggestTextsuggestion = suggestResponse.getSuggest().getSuggestion(PREFIX_PINYIN_SUGGEST);
		List<Suggest.Suggestion.Entry> entries = suggestTextsuggestion.getEntries();
		//汉字前缀匹配
		for (Suggest.Suggestion.Entry entry : entries) {
			List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
			for (Suggest.Suggestion.Entry.Option option : options) {
				returnSet.add(option.getText().toString());
			}
		}
		//全拼suggest补充
		if (returnSet.size() < 10) {
			List<Suggest.Suggestion.Entry> fullPinyinEntries = fullPinyinSuggestion.getEntries();
			for (Suggest.Suggestion.Entry entry : fullPinyinEntries) {
				List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
				for (Suggest.Suggestion.Entry.Option option : options) {
					if (returnSet.size() < 10) {
						returnSet.add(option.getText().toString());
					}
				}
			}
		}
		//首字母拼音suggest补充
		if (returnSet.size() == 0) {
			List<Suggest.Suggestion.Entry> prefixPinyinEntries = prefixPinyinSuggestion.getEntries();
			for (Suggest.Suggestion.Entry entry : prefixPinyinEntries) {
				List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
				for (Suggest.Suggestion.Entry.Option option : options) {
					returnSet.add(option.getText().toString());
				}
			}
		}
		return new ArrayList<>(returnSet);
	}

	private static boolean checkIsChinese(String keyword){
		return keyword.getBytes().length != keyword.length();
	}
}
