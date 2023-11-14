package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Reservation findTopByReservationUserOrderByReservationIdDesc(ResortUser reservationUser);

	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.roomId.id = :roomId AND " +
	        "r.checkInDate < :end AND r.checkOutDate > :start")
	int countConflictingReservations(@Param("roomId") long roomId,
	                                 @Param("start") String start,
	                                 @Param("end") String end);

}