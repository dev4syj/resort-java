package com.resort.dto;

import java.util.Date;

import com.resort.domain.Post;
import com.resort.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDto {

	// 게시글 등록과 수정 요청
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@Builder
	@Getter
	public static class Request {
		
		private Long postId;
		private String title;
		private Date postDate;
		private Date postModifiedDate;
		private String content;
		private int view;
		private User postUser;
		
		// Dto -> Entity
        public Post toEntity() {
            Post post = Post.builder()
                    .postId(postId)
                    .title(title)
                    .postDate(postDate)
                    .postModifiedDate(postModifiedDate)
                    .content(content)
                    .view(0)
                    .postUser(postUser)
                    .build();

            return post;
        }
	}
	
	// 게시글 정보 반환
	@Getter
    public static class Response {
		
		private final Long postId;
		private final String title;
		private final Date postDate;
		private final Date postModifiedDate;
		private final String content;
		private final int view;
		private final User postUser;
		
		// Entity -> Dto
        public Response(Post post) {
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.postDate = post.getPostDate();
            this.postModifiedDate = post.getPostModifiedDate();
            this.content = post.getContent();
            this.view = post.getView();
            this.postUser = post.getPostUser();
        }
	}

}
