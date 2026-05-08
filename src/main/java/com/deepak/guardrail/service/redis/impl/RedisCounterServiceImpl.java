package com.deepak.guardrail.service.redis.impl;

import com.deepak.guardrail.service.redis.RedisCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCounterServiceImpl implements RedisCounterService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public Long increment(String key) {

        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long incrementBy(String key, long value) {

        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public String get(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Boolean exists(String key) {

        return redisTemplate.hasKey(key);
    }
}