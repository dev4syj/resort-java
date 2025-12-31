package com.resort.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resort.domain.Reservation;
import com.resort.domain.ResortUser;
import com.resort.domain.Room;
import com.resort.repository.ReservationRepository;
import com.resort.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {

	@Autowired
	private final ReservationRepository reservationRepository;

	@Autowired
	private final RoomRepository roomRepository;

	public void newReservation(Reservation reservation, ResortUser user) {
		reservation.setReservationDate(LocalDate.now());
		reservation.setReservationUser(user);
		reservationRepository.save(reservation);
	}

	public Reservation reservationConfirmed(ResortUser user) {
		Reservation reservationConfirmed = reservationRepository
				.findTopByReservationUserOrderByReservationIdDesc(user);

		return reservationConfirmed;
	}
	
	public int checkDuplicateReservations(Reservation reservation) {
        return reservationRepository.countConflictingReservations(reservation.getRoom().getRoomId(), reservation.getCheckInDate(), reservation.getCheckOutDate());
    }
	
	public List<Map<String, Object>> getEventList() {
		List<Reservation> reservationList = reservationRepository.findAll();
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();

		for (Reservation reservation : reservationList) {
			Map<String, Object> events = new HashMap<>();

			events.put("id", reservation.getReservationId());
			events.put("start", reservation.getCheckInDate());
			events.put("title", reservation.getRoomType());
			events.put("end", reservation.getCheckOutDate());
			
			String color = "#cfd48d";
			if (reservation.getRoom().getRoomId() == 1) {
				color = "#4b6d49";
			} else if (reservation.getRoom().getRoomId() == 2) {
				color = "#809c62";
			} else if (reservation.getRoom().getRoomId() == 3) {
				color = "#c5d67c";
			}
			events.put("color", color);
			eventList.add(events);
		}

		return eventList;
	}

	public Optional<Reservation> getReservation(Long id) {
		return reservationRepository.findById(id);
	}

	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}

	public void updateReservation(Long id, String checkInDate, String checkOutDate, int adult, int children, Long roomId) {
		Optional<Reservation> optionalReservation = reservationRepository.findById(id);
		if (optionalReservation.isPresent()) {
			Reservation reservation = optionalReservation.get();
			reservation.setCheckInDate(checkInDate);
			reservation.setCheckOutDate(checkOutDate);
			reservation.setAdult(adult);
			reservation.setChildren(children);

			Optional<Room> room = roomRepository.findById(roomId);
            room.ifPresent(reservation::setRoomId);
			reservationRepository.save(reservation);
		}
	}

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

}
