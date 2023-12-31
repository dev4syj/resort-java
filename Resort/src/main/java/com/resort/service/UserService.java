package com.resort.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resort.DataNotFoundException;
import com.resort.domain.ResortUser;
import com.resort.dto.ResortUserDto;
import com.resort.repository.ResortUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final ResortUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ResortUser create(Long userId, String id, String password, String name, String phone, String address,
			String email) {

		ResortUser user = new ResortUser();
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
	public void userJoin(ResortUserDto.Request userDto) {

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(userDto.toEntity());
	}
	
	public ResortUser getUser(String id) {
        Optional<ResortUser> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

}