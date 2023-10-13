package com.resort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

//	@Autowired
//	private ReservationService reservationService;
	
	@GetMapping("/status")
	public String status() {
		return "status";
	}

//	@GetMapping("/status")
//	public ResponseEntity<List<Reservation>> getReservations() {
//		List<Reservation> reservations = reservationService.getAllReservations();
//		return new ResponseEntity<>(reservations, HttpStatus.OK);
//	}

}