package com.resort.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.resort.dto.ResortUserDto;
import com.resort.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ResortUserController {

	private final UserService userService;

	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("userDto", new ResortUserDto.Request());
		return "join";
	}

	// 회원가입
	@PostMapping("/join")
	public String join(@Valid ResortUserDto.Request userDto, Errors errors, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("userDto", userDto);

			Map<String, String> validatedResult = userService.validateHandling(errors);
			for (String key : validatedResult.keySet()) {
				model.addAttribute(key, validatedResult.get(key));
			}

			return "join";
		}

		userService.userJoin(userDto);

		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}