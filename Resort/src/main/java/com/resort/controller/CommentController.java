package com.resort.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.resort.domain.Comment;
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

	/* CREATE */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{postId}")
	public String createComment(Model model, @PathVariable("postId") long postId, @Valid CommentForm commentForm,
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

	/* UPDATE */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{commentId}")
	public String commentModify(CommentForm commentForm, @PathVariable("commentId") long commentId,
			Principal principal) {
		Comment comment = this.commentService.getcomment(commentId);
		if (!comment.getCommentUser().getId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		commentForm.setComment(comment.getComment());

		return "comment_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{commentId}")
	public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
			@PathVariable("commentId") long commentId, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "comment_form";
		}
		Comment comment = this.commentService.getcomment(commentId);
		if (!comment.getCommentUser().getId().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.commentService.modify(comment, commentForm.getComment());
		return String.format("redirect:/notice/detail/%s", comment.getRootId().getPostId());
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{commentId}")
    public String commentDelete(Principal principal, @PathVariable("commentId") long commentId) {
		Comment comment = this.commentService.getcomment(commentId);
        if (!comment.getCommentUser().getId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/notice/detail/%s", comment.getRootId().getPostId());
    }

}