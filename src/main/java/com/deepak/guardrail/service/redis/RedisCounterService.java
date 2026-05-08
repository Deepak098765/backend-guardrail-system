package com.deepak.guardrail.service.redis;

public interface RedisCounterService {

    Long increment(String key);

    Long incrementBy(String key, long value);

    String get(String key);

    void set(String key, String value);

    Boolean exists(String key);
}