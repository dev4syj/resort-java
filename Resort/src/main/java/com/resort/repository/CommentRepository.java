package com.resort.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.Comment;
import com.resort.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> getCommentByRootIdOrderByCommentId(Post post);
}
