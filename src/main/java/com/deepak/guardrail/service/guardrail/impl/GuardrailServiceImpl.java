package com.deepak.guardrail.service.guardrail.impl;

import com.deepak.guardrail.exception.RateLimitExceededException;
import com.deepak.guardrail.service.guardrail.GuardrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GuardrailServiceImpl implements GuardrailService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void validateBotReplyLimit(Long postId) {

        String redisKey = "post:" + postId + ":bot_count";

        Long currentCount =
                redisTemplate.opsForValue().increment(redisKey);

        if (currentCount > 100) {

            redisTemplate.opsForValue().decrement(redisKey);

            throw new RateLimitExceededException(
                    "Bot reply limit exceeded"
            );
        }
    }

    @Override
    public void validateCooldown(
            Long botId,
            Long humanId
    ) {

        String redisKey =
                "cooldown:bot_" + botId +
                        ":human_" + humanId;

        Boolean exists =
                redisTemplate.hasKey(redisKey);

        if (Boolean.TRUE.equals(exists)) {

            throw new RuntimeException(
                    "Cooldown active"
            );
        }

        redisTemplate.opsForValue().set(
                redisKey,
                "ACTIVE",
                Duration.ofMinutes(10)
        );
    }
}