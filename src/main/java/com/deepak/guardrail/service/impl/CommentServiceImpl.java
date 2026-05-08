package com.deepak.guardrail.service.impl;

import com.deepak.guardrail.dto.request.CreateCommentRequest;
import com.deepak.guardrail.entity.Comment;
import com.deepak.guardrail.entity.Post;
import com.deepak.guardrail.enums.InteractionType;
import com.deepak.guardrail.repository.CommentRepository;
import com.deepak.guardrail.repository.PostRepository;
import com.deepak.guardrail.service.CommentService;
import com.deepak.guardrail.service.guardrail.GuardrailService;
import com.deepak.guardrail.service.notification.NotificationService;
import com.deepak.guardrail.service.redis.ViralityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ViralityService viralityService;

    private final GuardrailService guardrailService;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public void createComment(Long postId, CreateCommentRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (request.getAuthorType().name().equals("BOT")) {

            guardrailService.validateBotReplyLimit(postId);
        }

        if (request.getAuthorType().name().equals("BOT")) {

            guardrailService.validateBotReplyLimit(postId);

            guardrailService.validateCooldown(
                    request.getAuthorId(),
                    1L
            );
        }

        Comment comment = Comment.builder()
                .post(post)
                .authorId(request.getAuthorId())
                .authorType(request.getAuthorType())
                .content(request.getContent())
                .depthLevel(request.getDepthLevel())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        if (request.getAuthorType().name().equals("BOT")) {

            viralityService.updateViralityScore(
                    postId,
                    InteractionType.BOT_REPLY
            );

            notificationService.handleBotInteractionNotification(
                    1L,
                    "Bot replied to your post"
            );

        } else {

            viralityService.updateViralityScore(
                    postId,
                    InteractionType.HUMAN_COMMENT
            );
        }
    }
}
