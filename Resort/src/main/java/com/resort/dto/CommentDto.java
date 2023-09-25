package com.resort.dto;

import java.util.Date;

import com.resort.domain.Comment;
import com.resort.domain.Post;
import com.resort.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

	// 댓글 서비스 요청
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private Long commentId;
		private String comment;
		private Date commentDate;
		private Date commentModifiedDate;
		private Post rootId;
		private User commentUser;
		
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
		private Date commentDate;
		private Date commentModifiedDate;
		private Post rootId;
		private User commentUser;
		
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