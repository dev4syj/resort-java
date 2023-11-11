package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resort.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

}
