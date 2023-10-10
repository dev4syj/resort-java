package com.resort.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Post getPost(Long id) {
		Optional<Post> post = this.postRepository.findById(id);
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
	public List<Post> getList() {
		return this.postRepository.findAll();
	}

	public Response getPost(long postId) {
		Optional<Post> post = this.postRepository.findById(postId);
		if (post.isPresent()) {
			return new PostDto.Response(post.get());
		} else {
			throw new DataNotFoundException("post not found");
		}
	}

	/* READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선 */
	@Transactional(readOnly = true)
	public PostDto.Response findById(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

		return new PostDto.Response(post);
	}

	/*
	 * UPDATE (dirty checking 영속성 컨텍스트) User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
	 * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다.
	 */
	@Transactional
	public void update(Long id, PostDto.Request dto) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

		post.update(dto.getTitle(), dto.getContent());
	}

	/* DELETE */
	@Transactional
	public void delete(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

		postRepository.delete(post);
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
