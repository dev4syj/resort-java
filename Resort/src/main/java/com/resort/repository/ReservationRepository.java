package com.resort.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.resort.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT DISTINCT r.roomId FROM Reservation r WHERE (r.checkInDate <= :checkOutDate AND r.checkOutDate >= :checkInDate)")
	List<Integer> findReservedRoomIds(@Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);

	@Query("SELECT r.roomId FROM Reservation r WHERE r.roomId NOT IN :reservedRoomIds")
	List<Integer> findAvailableRoomIds(@Param("reservedRoomIds") List<Integer> reservedRoomIds);

	boolean existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(int roomId, Date checkOutDate,
			Date checkInDate);
}