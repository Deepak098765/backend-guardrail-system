package com.deepak.guardrail.service;

import com.deepak.guardrail.dto.request.CreateCommentRequest;

public interface CommentService {

    void createComment(Long postId, CreateCommentRequest request);
}
