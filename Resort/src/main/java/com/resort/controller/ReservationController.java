package com.resort.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;
import com.resort.repository.ReservationRepository;
import com.resort.service.ReservationService;
import com.resort.service.UserService;

import jakarta.validation.Valid;

@Controller
public class ReservationController {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String status(Model model) {
		List<Reservation> reservations = reservationRepository.findAll();
		model.addAttribute("calendarEvents", reservations);
		return "status";
	}

	@GetMapping("/reservationList")
	public ResponseEntity<List<Reservation>> getEvents() {
		List<Reservation> reservationList = reservationRepository.findAll();
		reservationList.forEach(reservation -> {
			reservation.getRoom().getType(); // 이 부분이 Lazy Loading을 강제로 초기화하는 부분입니다.
		});
		return ResponseEntity.ok(reservationList);
	}

	@GetMapping("/event") // ajax 데이터 전송 URL
	public @ResponseBody List<Map<String, Object>> getEvent() {
		return reservationService.getEventList();
	}

	// CREATE
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reservation")
	public String Create(Model model, Principal principal) {
		ResortUser user = userService.getUser(principal.getName());
		Reservation reservation = new Reservation();
		reservation.setReservationUser(user);
		reservation.setAdult(1);
		model.addAttribute("reservation", reservation);

		return "reservation";
	}

	@PostMapping("/reservation")
	public String reservationCreate(@Valid Reservation reservation, BindingResult bindingResult, Principal principal,
			Model model) {
		ResortUser user = userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			model.addAttribute("reservation", reservation);
			return "reservation";
		}
		try {
			reservationService.newReservation(reservation, user);

			// POST 요청 후에는 PRG(POST-Redirect-GET) 패턴을 사용하는 것이 권장
			// 리다이렉트를 통해 GET 요청을 생성하여 중복된 데이터 전송을 방지
			return "redirect:/reservation_confirm";

		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("error.reservation.exists", "해당 날짜에 이미 예약된 ROOM 입니다.");
			model.addAttribute("reservation", reservation);
			return "reservation";
		}
	}

	@GetMapping("/reservation_confirm")
	public String reservationConfirm(Principal principal, Model model) {
		ResortUser user = userService.getUser(principal.getName());
		Reservation reservation = reservationService.reservationConfirmed(user);
		model.addAttribute("reservationConfirmed", reservation);
		return "reservation_confirm";
	}

}