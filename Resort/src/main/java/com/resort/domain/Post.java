package com.resort.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDateTime postDate;

	@Column
	private LocalDateTime postModifiedDate;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private int view;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ResortUser postUser;

	@OneToMany(mappedBy = "rootId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("comment_date asc")
	private List<Comment> comments; // 게시글 삭제되면 댓글도 삭제되도록 설정
	
	/* 게시글 수정 */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}