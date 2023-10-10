package com.resort.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resort.domain.CommentForm;
import com.resort.domain.NoticeForm;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
import com.resort.dto.PostDto.Response;
import com.resort.service.PostService;
import com.resort.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	private final UserService userService;

	@GetMapping("/list")
	public String notice(Model model) {
		List<Post> list = this.postService.getList();
		model.addAttribute("list", list);
		return "notice";
	}

	@GetMapping(value = "/detail/{postId}")
	public String detail(Model model, @PathVariable("postId") int postId, CommentForm commentForm) {
		Response post = this.postService.getPost(postId);
		model.addAttribute("postDetail", post);
		return "notice_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String noticeCreate(NoticeForm noticeForm) {
		return "notice_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String noticeCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "notice_form";
		}
		ResortUser user = this.userService.getUser(principal.getName());
		this.postService.create(noticeForm.getTitle(), noticeForm.getContent(), user);
		return "redirect:/notice/list";
	}
}
