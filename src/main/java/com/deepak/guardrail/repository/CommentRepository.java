package com.deepak.guardrail.repository;

import com.deepak.guardrail.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
