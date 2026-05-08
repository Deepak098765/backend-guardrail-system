package com.deepak.guardrail.service.redis;

import com.deepak.guardrail.enums.InteractionType;

public interface ViralityService {

    void updateViralityScore(
            Long postId,
            InteractionType interactionType
    );
}