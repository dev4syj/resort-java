package com.resort.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resort.domain.CommentForm;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
import com.resort.service.CommentService;
import com.resort.service.PostService;
import com.resort.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentService commentService;
	private final PostService postService;
	private final UserService userService;
	
//	@PostMapping("/create/{postId}")
//	public String createAnswer(@PathVariable("postId") int postId, @RequestParam String newComment) {
//		Response post = this.postService.getPost(postId);
//		commentService.create(post, newComment);
//		return String.format("redirect:/notice/detail/%s", postId);
//	}

	/* CREATE */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{postId}")
	public String createComment(Model model, @PathVariable("postId") Long postId, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {
		Post post = this.postService.getPost(postId);
		ResortUser user = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("postDetail", post); // 댓글 작성 오류 시 해당 공지 내용 저장해서 유지
			return "notice_detail";
		}
		commentService.create(post, commentForm.getComment(), user);

		return String.format("redirect:/notice/detail/%s", postId);
	}

}