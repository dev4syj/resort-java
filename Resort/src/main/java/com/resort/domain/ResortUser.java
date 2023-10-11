package com.resort.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class ResortUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	// 사용자 아이디
	@Column(length = 10, nullable = false, unique = true)
	private String id;

	// 비밀번호는 JSON 직렬화 무시하여 클라이언트에게 노출되지 않고 서버측에서만 사용하도록 설정
	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column(length = 15, nullable = false)
	private String name;

	@Column(length = 11, nullable = false)
	private String phone;

	@Column(length = 50, nullable = false)
	private String address;

	@Column(length = 50, nullable = false)
	private String email;

}