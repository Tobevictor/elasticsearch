package com.learn.service;

import java.util.Set;

/**
 * @author dshuyou
 * @date 2019/10/21 16:38
 */
public interface HotWordService {

	Boolean set(String key, String value, double score);

	Boolean hasKey(String key);

	Set<String> getHotWord(String key,long start, long end);

	Boolean remove(String key);

	Long removeRange(String key, long start, long end);

	Boolean expire(String key, long expire);

	Double increment(String key, String value, long delta);

}
