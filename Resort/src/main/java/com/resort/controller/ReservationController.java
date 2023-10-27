package com.resort.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.resort.domain.Reservation;
import com.resort.repository.ReservationRepository;
import com.resort.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/status")
	public String status() {
		return "status";
	}

	@GetMapping("/reservationList")
	public String reservationList(Model model) {
		List<Reservation> reservationList = reservationRepository.findAll();
		model.addAttribute("reservationList", reservationList);
		return "reservation_list";
	}

	@PostMapping("/check-duplicate-reservation")
	public ResponseEntity<String> checkDuplicateReservation(@RequestBody Reservation reservation) {
		Date checkInDate = reservation.getCheckInDate();
		Date checkOutDate = reservation.getCheckOutDate();
		int roomId = reservation.getRoomId();

		if (reservationService.isDuplicateReservation(roomId, checkInDate, checkOutDate)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Reservation is duplicated.");
		} else {
			return ResponseEntity.ok("Reservation is available.");
		}
	}

}