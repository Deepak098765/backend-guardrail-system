package com.deepak.guardrail.repository;

import com.deepak.guardrail.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
