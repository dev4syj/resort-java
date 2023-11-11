package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Reservation findTopByReservationUserOrderByReservationDateDesc(ResortUser reservationUser);
}