package com.atlacademy.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

	@GetMapping
	public String sayHello() {
		return "Hello!";
	}

	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello, World!";
	}

}
