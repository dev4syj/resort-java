package com.resort.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.resort.domain.Post;

@Controller
public class ResortController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	
	@GetMapping("/rooms")
	public String rooms() {
		return "rooms";
	}

	@GetMapping("/service")
	public String service() {
		return "service";
	}

}