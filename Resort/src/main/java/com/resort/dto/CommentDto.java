package com.resort.dto;

import java.time.LocalDateTime;

import com.resort.domain.Comment;
import com.resort.domain.Post;
import com.resort.domain.ResortUser;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDto {

	// 댓글 서비스 요청
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	@Setter
	public static class Request {

		private Long commentId;
		private LocalDateTime commentDate;
		private LocalDateTime commentModifiedDate;
		private Post rootId;
		private ResortUser commentUser;
		
		@NotEmpty(message = "내용은 필수항목입니다.")
		private String comment;
		
		// Dto -> Entity
		public Comment toEntity() {
			Comment comments = Comment.builder()
					.commentId(commentId)
					.comment(comment)
					.commentDate(commentDate)
					.commentModifiedDate(commentModifiedDate)
					.rootId(rootId)
					.commentUser(commentUser)
					.build();
			
			return comments;
		}
	}

	// 댓글 정보 반환
    @Getter
    public static class Response {
    	
    	private Long commentId;
		private String comment;
		private LocalDateTime commentDate;
		private LocalDateTime commentModifiedDate;
		private Post rootId;
		private ResortUser commentUser;
		
		// Entity -> Dto
		public Response(Comment comment) {			
			this.commentId = comment.getCommentId();
			this.comment = comment.getComment();
			this.commentDate = comment.getCommentDate();
			this.commentModifiedDate = comment.getCommentModifiedDate();
			this.rootId = comment.getRootId();
			this.commentUser = comment.getCommentUser();
		}
    } 	
}