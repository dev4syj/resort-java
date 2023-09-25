package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.resort.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Modifying
	@Query("update Post p set p.view = p.view + 1 where p.postId = :postId")
	int updateView(Long postId);
}