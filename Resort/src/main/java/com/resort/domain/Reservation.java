package com.resort.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reservationId;

	@Column(nullable = false)
	private Date checkInDate;

	@Column(nullable = false)
	private Date checkOutDate;

	@Column(nullable = false)
	private int adult; // 성인 고객 수

	@Column
	private int children; // 미성년 고객 수

	@Column(nullable = false)
	private Date reservationDate; // 예약한 날짜

	@Column(nullable = false)
	private int roomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ResortUser reservationUser; // 예약자

}