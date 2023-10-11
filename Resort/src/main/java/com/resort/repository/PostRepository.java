package com.resort.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	Page<Post> findAll(Pageable pageable);
}