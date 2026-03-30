package com.example.jobservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveJob(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
    }

    public Object getJob(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}