package com.jake.sns.domain.post.controller;

import com.jake.sns.common.response.CommonResponse;
import com.jake.sns.domain.comment.dto.response.CommentResponse;
import com.jake.sns.domain.post.dto.request.PostCommentRequest;
import com.jake.sns.domain.post.dto.response.PostResponse;
import com.jake.sns.domain.post.dto.Post;
import com.jake.sns.domain.post.dto.request.PostCreateRequest;
import com.jake.sns.domain.post.dto.request.PostModifyRequest;
import com.jake.sns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping
    public CommonResponse<Page<PostResponse>> list(Pageable pageable, Authentication authentication) {
        return CommonResponse.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public CommonResponse<Page<PostResponse>> my(Pageable pageable, Authentication authentication) {
        return CommonResponse.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPost));
    }

    @PostMapping
    public CommonResponse<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return CommonResponse.success();
    }

    @PutMapping("/{postId}")
    public CommonResponse<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
        return CommonResponse.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return CommonResponse.success();
    }

    @GetMapping("/{postId}/likes")
    public CommonResponse<Integer> likeCount(@PathVariable Long postId) {
        return CommonResponse.success(postService.likeCount(postId));
    }

    @PostMapping("/{postId}/likes")
    public CommonResponse<Void> like(@PathVariable Long postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return CommonResponse.success();
    }

    @GetMapping("/{postId}/comments")
    public CommonResponse<Page<CommentResponse>> comment(@PathVariable Long postId, Pageable pageable) {
        return CommonResponse.success(postService.getComments(postId, pageable).map(CommentResponse::fromComment));
    }

    @PostMapping("/{postId}/comments")
    public CommonResponse<Void> comment(@PathVariable Long postId, @RequestBody PostCommentRequest request, Authentication authentication) {
        postService.comment(postId, authentication.getName(), request.getContext());
        return CommonResponse.success();
    }
}
