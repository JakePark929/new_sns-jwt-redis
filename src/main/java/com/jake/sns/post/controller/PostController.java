package com.jake.sns.post.controller;

import com.jake.sns.common.response.CommonResponse;
import com.jake.sns.post.dto.Post;
import com.jake.sns.post.dto.request.PostCreateRequest;
import com.jake.sns.post.dto.request.PostModifyRequest;
import com.jake.sns.post.dto.response.PostResponse;
import com.jake.sns.post.service.PostService;
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
}
