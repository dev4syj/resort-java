package com.resort.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.resort.domain.User;
import com.resort.dto.UserDto;
import com.resort.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User create(Long userId, String id, String password, String name, String phone, String address,
			String email) {

		User user = new User();
		user.setUserId(userId);
		user.setId(id);
		user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		user.setPhone(phone);
		user.setAddress(address);
		user.setEmail(email);
		this.userRepository.save(user);

		return user;
	}

	// 회원가입
	@Transactional
	public void userJoin(UserDto.Request userDto) {

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(userDto.toEntity());
	}

	// 회원가입 -> 중복 체크, 유효성 검사
	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatedResult = new HashMap<>();
		for (FieldError error : errors.getFieldErrors()) {
			String validForm = String.format("valid_%s", error.getField());
			validatedResult.put(validForm, error.getDefaultMessage());
		}

		return validatedResult;
	}
}