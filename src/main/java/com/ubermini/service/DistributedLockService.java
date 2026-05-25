package com.ubermini.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistributedLockService {
	
	private final StringRedisTemplate redisTemplate;
	
	public boolean tryLock(String key, Duration ttl) {
		Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "locked", ttl);
		return Boolean.TRUE.equals(success);
	}
	
	
	public void unlock(String key) {
		redisTemplate.delete(key);
	}
}
