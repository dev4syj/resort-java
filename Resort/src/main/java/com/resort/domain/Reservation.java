package com.resort.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "Reservation", uniqueConstraints = {
		@UniqueConstraint(name = "UniqueReservation", columnNames = { "room_id", "checkInDate" }) })
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reservationId;

	@Column(nullable = false, name = "checkInDate")
	private String checkInDate;

//	@Column(nullable = false)
//	private String checkOutDate;

	@Column(nullable = false)
	@NotNull(message = "성인 고객 1명 이상 선택해야 합니다.")
	private int adult; // 성인 고객 수

	@Column
	@Max(value = 4, message = "최대 4명 까지 가능합니다.")
	private int children; // 미성년 고객 수

	@Column(nullable = false)
	private LocalDate reservationDate; // 예약한 날짜

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room roomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ResortUser reservationUser; // 예약자

	public Room getRoom() {
		return roomId;
	}
	
	public ResortUser getUser() {
		return reservationUser;
	}

}