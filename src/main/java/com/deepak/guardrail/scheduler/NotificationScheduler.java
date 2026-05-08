package com.deepak.guardrail.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final StringRedisTemplate redisTemplate;

    @Scheduled(fixedRate = 300000)
    public void processPendingNotifications() {

        Set<String> keys =
                redisTemplate.keys(
                        "user:*:pending_notifs"
                );

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {

            Long count =
                    redisTemplate.opsForList().size(key);

            String firstMessage =
                    redisTemplate.opsForList().leftPop(key);

            while (redisTemplate.opsForList()
                    .size(key) > 0) {

                redisTemplate.opsForList()
                        .leftPop(key);
            }

            log.info(
                    "Summarized Push Notification: {} and {} others interacted with your posts.",
                    firstMessage,
                    count - 1
            );
        }
    }
}