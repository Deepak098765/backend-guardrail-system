package com.deepak.guardrail.service.notification.impl;

import com.deepak.guardrail.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl
        implements NotificationService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void handleBotInteractionNotification(
            Long userId,
            String message
    ) {

        String cooldownKey =
                "user:" + userId + ":notif_cooldown";

        Boolean exists =
                redisTemplate.hasKey(cooldownKey);

        if (Boolean.TRUE.equals(exists)) {

            String pendingListKey =
                    "user:" + userId +
                            ":pending_notifs";

            redisTemplate.opsForList()
                    .rightPush(pendingListKey, message);

            log.info(
                    "Notification batched for user {}",
                    userId
            );

        } else {

            log.info(
                    "Push Notification Sent to User {} : {}",
                    userId,
                    message
            );

            redisTemplate.opsForValue().set(
                    cooldownKey,
                    "ACTIVE",
                    Duration.ofMinutes(15)
            );
        }
    }
}