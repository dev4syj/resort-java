package com.resort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.resort.domain.Post;
import com.resort.service.CommentService;
import com.resort.service.PostService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class CommentController {
	
	private final PostService postService;
	private final CommentService commentService;
	
	@PostMapping("/create/{postId}")
    public String createAnswer(Model model, @PathVariable("postId") int postId, @RequestParam String newComment) {
        Post post = this.postService.getPost(postId);
        this.commentService.create(post, newComment);
        return String.format("redirect:/notice/detail/%s", postId);
    }

}
