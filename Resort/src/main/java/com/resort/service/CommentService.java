package com.resort.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.resort.domain.Comment;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
import com.resort.dto.CommentDto;
import com.resort.repository.CommentRepository;
import com.resort.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public void create(Long postId, CommentDto.Request dto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + postId));

		dto.setRootId(post);
		dto.setCommentDate(LocalDateTime.now());

		Comment comment = dto.toEntity();
		commentRepository.save(comment);
	}

	public void create(Post post, String content, ResortUser user) {
		Comment comment = new Comment();
		comment.setComment(content);
		comment.setCommentDate(LocalDateTime.now());
		comment.setRootId(post);
		comment.setCommentUser(user);
		commentRepository.save(comment);
	}

}
