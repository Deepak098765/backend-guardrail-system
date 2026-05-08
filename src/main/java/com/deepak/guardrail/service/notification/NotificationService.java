package com.deepak.guardrail.service.notification;

public interface NotificationService {

    void handleBotInteractionNotification(
            Long userId,
            String message
    );
}