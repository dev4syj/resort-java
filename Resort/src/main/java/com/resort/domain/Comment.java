package com.resort.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String comment;

	@Column(nullable = false)
	private Date commentDate;
	
	@Column
	private Date commentModifiedDate;

	@ManyToOne
	@JoinColumn(name = "postId")
	private Post rootId; // 원글

	@ManyToOne
	@JoinColumn(name = "user_id")
	private ResortUser commentUser; // 작성자
}