package com.resort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResortController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/about-us")
	public String about() {
		return "about-us";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/rooms")
	public String rooms() {
		return "rooms";
	}

	@GetMapping("/services")
	public String services() {
		return "services";
	}
	
	@GetMapping("/local-area")
	public String local() {
		return "local-area";
	}

}