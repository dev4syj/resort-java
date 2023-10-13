package com.resort.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.resort.DataNotFoundException;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;
import com.resort.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	@Autowired
	private final PostRepository postRepository;

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
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.postRepository.findAll(pageable);
	}

	public Post getPost(long postId) {
		Optional<Post> _post = this.postRepository.findById(postId);
		if (_post.isPresent()) {
			Post post = _post.get();
			updateView(post);
			return post;
		} else {
			throw new DataNotFoundException("post not found");
		}
	}

	/* UPDATE */
	public void modify(Post post, String title, String content) {
		post.setTitle(title);
		post.setContent(content);
		post.setPostModifiedDate(LocalDateTime.now());
		this.postRepository.save(post);
	}

	public void updateView(Post post) {
		post.setView(post.getView() + 1);
		this.postRepository.save(post);
	}

	/* DELETE */
	public void delete(Post post) {
		this.postRepository.delete(post);
	}

}
