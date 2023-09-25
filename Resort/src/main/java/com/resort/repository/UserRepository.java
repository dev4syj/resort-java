package com.resort.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resort.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}