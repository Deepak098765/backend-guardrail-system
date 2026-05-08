package com.deepak.guardrail.service.impl;

import com.deepak.guardrail.dto.request.CreatePostRequest;
import com.deepak.guardrail.entity.Post;
import com.deepak.guardrail.repository.PostRepository;
import com.deepak.guardrail.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void createPost(CreatePostRequest request) {

        Post post = Post.builder()
                .authorId(request.getAuthorId())
                .authorType(request.getAuthorType())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);
    }
}
