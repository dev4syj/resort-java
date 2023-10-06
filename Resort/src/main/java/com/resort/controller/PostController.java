package com.resort.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resort.domain.NoticeForm;
import com.resort.domain.Post;
import com.resort.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;

	@GetMapping("/list")
	public String notice(Model model) {
		List<Post> list = this.postService.getList();
		model.addAttribute("list", list);
		return "notice";
	}

	@GetMapping(value = "/detail/{postId}")
	public String detail(Model model, @PathVariable("postId") int postId) {
		Post post = this.postService.getPost(postId);
		model.addAttribute("postDetail", post);
		return "notice_detail";
	}

	@GetMapping("/create")
	public String noticeCreate(NoticeForm noticeForm) {
		return "notice_form";
	}

	@PostMapping("/create")
	public String noticeCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "notice_form";
		}
		this.postService.create(noticeForm.getTitle(), noticeForm.getContent());
		return "redirect:/notice/list";
	}
}
