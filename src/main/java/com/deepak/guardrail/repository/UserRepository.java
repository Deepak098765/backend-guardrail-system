package com.deepak.guardrail.repository;

import com.deepak.guardrail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
