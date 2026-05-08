package com.deepak.guardrail.service.guardrail;

public interface GuardrailService {

    void validateBotReplyLimit(Long postId);

    void validateCooldown(
            Long botId,
            Long humanId
    );
}