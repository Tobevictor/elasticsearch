package com.learn.service.impl;

import com.learn.service.HotWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author dshuyou
 * @date 2019/10/21 17:40
 */
@Service
public class HotWordServiceImpl implements HotWordService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public Boolean set(String key, String value, double score) {
		return stringRedisTemplate.opsForZSet().add(key,value,score);
	}

	@Override
	public Boolean hasKey(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	@Override
	public Set<String> getHotWord(String key, long start, long end) {
		return stringRedisTemplate.opsForZSet().reverseRange(key,start,end);
	}

	@Override
	public Boolean remove(String key) {
		return stringRedisTemplate.delete(key);
	}

	@Override
	public Long removeRange(String key, long start, long end) {
		return stringRedisTemplate.opsForZSet().removeRange(key,start,end);
	}

	@Override
	public Boolean expire(String key, long expire) {
		return stringRedisTemplate.expire(key,expire, TimeUnit.SECONDS);
	}

	@Override
	public Double increment(String key, String value, long delta) {
		return stringRedisTemplate.opsForZSet().incrementScore(key,value,delta);
	}
}
