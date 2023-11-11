package com.resort.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;
import com.resort.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {

	@Autowired
	private final ReservationRepository reservationRepository;

	public void newReservation(Reservation reservation, ResortUser user) {

		reservation.setReservationDate(LocalDate.now());
		reservation.setReservationUser(user);
		reservationRepository.save(reservation);
	}

	public Reservation reservationConfirmed(ResortUser user) {
		Reservation reservationConfirmed = reservationRepository
				.findTopByReservationUserOrderByReservationDateDesc(user);

		return reservationConfirmed;
	}

	public List<Map<String, Object>> getEventList() {
		List<Reservation> reservationList = reservationRepository.findAll();

		Map<String, Object> event = new HashMap<String, Object>();
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		// 이벤트 1
		event.put("start", LocalDate.now());
		event.put("title", "테스트");
		event.put("end", LocalDate.now().plusDays(2).atStartOfDay()); // 하루 끝으로 설정
		eventList.add(event);

		for (Reservation reservation : reservationList) {
			Map<String, Object> events = new HashMap<>();
			event.put("start", reservation.getCheckInDate());
			event.put("title", "Room " + reservation.getRoom().getRoomId());
			event.put("end", LocalDate.parse(reservation.getCheckInDate()).plusDays(1).atStartOfDay());
			eventList.add(events);
		}

		return eventList;
	}

}
