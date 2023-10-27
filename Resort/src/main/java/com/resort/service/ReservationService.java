package com.resort.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resort.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {

	@Autowired
	private final ReservationRepository reservationRepository;

	public List<Integer> findAvailableRoomIds(Date checkInDate, Date checkOutDate) {
		List<Integer> reservedRoomIds = reservationRepository.findReservedRoomIds(checkInDate, checkOutDate);
		List<Integer> availableRoomIds = reservationRepository.findAvailableRoomIds(reservedRoomIds);
		return availableRoomIds;
	}

	public boolean isDuplicateReservation(int roomId, Date checkInDate, Date checkOutDate) {
		// 중복 예약 확인
		return reservationRepository.existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(roomId, checkInDate, checkOutDate);
	}

}
