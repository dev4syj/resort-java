package com.resort.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.resort.domain.Reservation;
import com.resort.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationDto {
	
	// 예약 서비스 요청
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@Builder
	@Getter
	public static class Request {
		
		private long reservationId;
		private int adult;
		private int children;
		private int roomId;
		private User reservationUser;
		
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date checkInDate;
		
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date checkOutDate;
		
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date reservationDate;
		
		// Dto -> Entity
        public Reservation toEntity() {
        	Reservation reservation = Reservation.builder()
        							.reservationId(reservationId)
        							.adult(adult)
        							.children(children)
        							.roomId(roomId)
        							.reservationUser(reservationUser)
        							.checkInDate(checkInDate)
        							.checkOutDate(checkOutDate)
        							.reservationDate(reservationDate)
        							.build();

			return reservation;
		}
	}
	
	// 예약 정보 반환
	@Getter
	public static class Response {
		private final long reservationId;
		private final int adult;
		private final int children;
		private final int roomId;
		private final User reservationUser;
		private final Date checkInDate;
		private final Date checkOutDate;
		private final Date reservationDate;

		// Entity -> Dto
		public Response(Reservation reservation) {
			this.reservationId = reservation.getReservationId();
			this.adult = reservation.getAdult();
			this.children = reservation.getChildren();
			this.roomId = reservation.getRoomId();
			this.reservationUser = reservation.getReservationUser();
			this.checkInDate = reservation.getCheckInDate();
			this.checkOutDate = reservation.getCheckOutDate();
			this.reservationDate = reservation.getReservationDate();
		}
	}
}
