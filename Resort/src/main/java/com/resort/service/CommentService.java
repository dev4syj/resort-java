package com.resort.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.resort.domain.Comment;
import com.resort.domain.Post;
import com.resort.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public void create(Post post, String newComment) {
		Comment comment = new Comment();
		comment.setComment(newComment);
		comment.setCommentDate(LocalDateTime.now());
		comment.setRootId(post);
		this.commentRepository.save(comment);
	}
}
