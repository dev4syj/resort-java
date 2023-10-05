package com.resort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("/notice")
	public String notice() {
		return "notice";
	}

}