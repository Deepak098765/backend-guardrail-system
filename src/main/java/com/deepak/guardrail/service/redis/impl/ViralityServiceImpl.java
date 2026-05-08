package com.deepak.guardrail.service.redis.impl;

import com.deepak.guardrail.enums.InteractionType;
import com.deepak.guardrail.service.redis.RedisCounterService;
import com.deepak.guardrail.service.redis.ViralityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViralityServiceImpl implements ViralityService {

    private final RedisCounterService redisCounterService;

    @Override
    public void updateViralityScore(
            Long postId,
            InteractionType interactionType
    ) {

        String redisKey = "post:" + postId + ":virality_score";

        switch (interactionType) {

            case BOT_REPLY ->
                    redisCounterService.incrementBy(redisKey, 1);

            case HUMAN_LIKE ->
                    redisCounterService.incrementBy(redisKey, 20);

            case HUMAN_COMMENT ->
                    redisCounterService.incrementBy(redisKey, 50);
        }
    }
}