package com.resort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String join(@Valid @ModelAttribute("userDto") ResortUserDto.Request userDto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			//model.addAttribute("userDto", userDto);
			return "join";
		}
		this.userService.userJoin(userDto);

		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}