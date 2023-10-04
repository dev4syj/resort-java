package com.resort.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.ResortUser;

public interface ResortUserRepository extends JpaRepository<ResortUser, Long> {

	Optional<ResortUser> findByid(String id);
}