package com.resort.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.resort.domain.Comment;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostDto {

	// 게시글 등록과 수정 요청
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@Builder
	@Getter
	@Setter
	public static class Request {
		
		private Long postId;
		private LocalDateTime postDate;
		private LocalDateTime postModifiedDate;
		private int view;
		private ResortUser postUser;
		private List<Comment> comments;
		
		@NotEmpty(message="제목은 필수항목입니다.")
	    @Size(max=200)
		private String title;
				
		@NotEmpty(message="내용은 필수항목입니다.")
		private String content;
				
				
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
                    .comments(comments)
                    .build();

            return post;
        }

	}
	
	// 게시글 정보 반환
	@Getter
    public static class Response {
		
		private final Long postId;
		private final String title;
		private final LocalDateTime postDate;
		private final LocalDateTime postModifiedDate;
		private final String content;
		private final int view;
		private final ResortUser postUser;
		private List<Comment> comments;
		
		// Entity -> Dto
        public Response(Post post) {
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.postDate = post.getPostDate();
            this.postModifiedDate = post.getPostModifiedDate();
            this.content = post.getContent();
            this.view = post.getView();
            this.postUser = post.getPostUser();
            this.comments = post.getComments();
        }
	}

}
