package com.resort.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.resort.domain.CommentForm;
import com.resort.domain.NoticeForm;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
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
	public String notice(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Post> list = this.postService.getList(page);
		model.addAttribute("list", list);
		return "notice";
	}

	@GetMapping(value = "/detail/{postId}")
	public String detail(Model model, @PathVariable("postId") long postId, CommentForm commentForm) {
		Post post = this.postService.getPost(postId);
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{postId}")
	public String questionModify(NoticeForm noticeForm, @PathVariable("postId") Long postId, Principal principal) {
		Post post = this.postService.getPost(postId);
		if (!post.getPostUser().getId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		noticeForm.setTitle(post.getTitle());
		noticeForm.setContent(post.getContent());
		return "notice_form";
	}
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{postId}")
    public String questionModify(@Valid NoticeForm noticeForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("postId") Long postId) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        Post post = this.postService.getPost(postId);
        
        if (!post.getPostUser().getId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post, noticeForm.getTitle(), noticeForm.getContent());
        return String.format("redirect:/notice/detail/%s", postId);
    }
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{postId}")
	public String questionDelete(Principal principal, @PathVariable("postId") long postId) {
		Post post = this.postService.getPost(postId);
		if (!post.getPostUser().getId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.postService.delete(post);
		return "redirect:/notice/list";
	}
}
