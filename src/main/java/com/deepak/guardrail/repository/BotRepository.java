package com.deepak.guardrail.repository;

import com.deepak.guardrail.entity.Bot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository<Bot, Long> {
}

