package com.deepak.guardrail.service;

import com.deepak.guardrail.dto.request.CreatePostRequest;

public interface PostService {

    void createPost(CreatePostRequest request);
}
