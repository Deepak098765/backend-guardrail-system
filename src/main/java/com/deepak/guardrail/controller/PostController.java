package com.deepak.guardrail.controller;

import com.deepak.guardrail.dto.request.CreateCommentRequest;
import com.deepak.guardrail.dto.request.CreatePostRequest;
import com.deepak.guardrail.service.PostService;
import com.deepak.guardrail.service.CommentService;
import com.deepak.guardrail.service.redis.ViralityService;
import com.deepak.guardrail.enums.InteractionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final CommentService commentService;

    private final ViralityService viralityService;

    @PostMapping
    public ResponseEntity<String> createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {

        postService.createPost(request);

        return ResponseEntity.ok("Post created successfully");
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request
    ) {

        commentService.createComment(postId, request);

        return ResponseEntity.ok("Comment added successfully");
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId
    ) {

        viralityService.updateViralityScore(
                postId,
                InteractionType.HUMAN_LIKE
        );

        return ResponseEntity.ok("Post liked successfully");
    }
}
