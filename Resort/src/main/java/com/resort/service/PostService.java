package com.resort.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resort.DataNotFoundException;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
import com.resort.dto.PostDto;
import com.resort.dto.PostDto.Response;
import com.resort.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;

	public Post getPost(long postId) {
		Optional<Post> post = this.postRepository.findById(postId);
		if (post.isPresent()) {
			return post.get();
		} else {
			throw new DataNotFoundException("post not found");
		}
	}

	/* CREATE */
	public void create(String title, String content, ResortUser user) {
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setPostDate(LocalDateTime.now());
		post.setPostUser(user);
		this.postRepository.save(post);
	}

	/* READ */
	public Page<Post> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("postDate"));
		Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
		return this.postRepository.findAll(pageable);
	}

	/* UPDATE */
	public void modify(Post post, String title, String content) {
		post.setTitle(title);
		post.setContent(content);
		post.setPostModifiedDate(LocalDateTime.now());
		this.postRepository.save(post);
	}

	public void delete(Post post) {
		this.postRepository.delete(post);
	}

	/* Views Counting */
	@Transactional
	public int updateView(Long id) {
		return postRepository.updateView(id);
	}

	/* Paging and Sort */
	@Transactional(readOnly = true)
	public Page<Post> pageList(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

}
