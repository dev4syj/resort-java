package com.resort.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reservationCheck")
	public String reservationCheck(Model model, Principal principal) {
		ResortUser user = userService.getUser(principal.getName());
		Reservation reservation = new Reservation();
		reservation.setReservationUser(user);
		reservation.setAdult(1);
		model.addAttribute("reservationCheck", reservation);
		return "reservation_check";
	}

	@PostMapping("/reservationCheck")
	public String reserveRoom(@Valid Reservation reservation, BindingResult bindingResult, Principal principal,
			Model model) {

		ResortUser user = userService.getUser(principal.getName());
		// 예약이 가능한지 확인
		int duplicated = reservationService.checkDuplicateReservations(reservation);

		if (bindingResult.hasErrors()) {
			model.addAttribute("reservationCheck", reservation);
			return "reservation_check";
		}

		try {
			if (duplicated == 0) {
				reservationService.newReservation(reservation, user);

				return "redirect:/reservation_confirm";
			} else {
				// 예약 불가능한 경우 사용자에게 메시지 전달
				model.addAttribute("errorMessage", "해당 날짜에 예약 가능한 방이 없습니다.");
				model.addAttribute("reservationCheck", reservation);
				return "reservation_check";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "해당 날짜에 예약 가능한 방이 없습니다.");
			model.addAttribute("reservationCheck", reservation);
			return "reservation_check";
		}

	}

	@GetMapping("/status")
	public String status(Model model) {
		List<Reservation> reservations = reservationRepository.findAll();
		model.addAttribute("calendarEvents", reservations);
		return "status";
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