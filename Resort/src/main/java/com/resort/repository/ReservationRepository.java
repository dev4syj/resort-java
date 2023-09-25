package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
