package com.resort.dto;

import java.time.LocalDate;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;
import com.resort.domain.Room;

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
		private Room roomId;
		private ResortUser reservationUser;
		
		private String checkInDate;
		
		private String checkOutDate;
		
		private LocalDate reservationDate;
		
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
		private final Room roomId;
		private final ResortUser reservationUser;
		private final String checkInDate;
		private final String checkOutDate;
		private final LocalDate reservationDate;

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
