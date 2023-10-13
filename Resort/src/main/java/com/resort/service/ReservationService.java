package com.resort.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.resort.domain.Reservation;
import com.resort.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {
	
	@Autowired
	private final ReservationRepository reservationRepository;

	@GetMapping("/status")
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = reservationRepository.findAll();
		return reservations;
	}

}
