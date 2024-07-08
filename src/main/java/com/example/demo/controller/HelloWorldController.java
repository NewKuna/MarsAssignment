package com.example.demo.controller;

import com.example.demo.common.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private Environment env;

	@Autowired
	public void setEnvironment(Environment env) {
		this.env = env;
	}
	
    @GetMapping("/")
	public String hello() {
		return "Hello, "+env.getProperty("my.name");
	}

	@PostMapping("/verify")
	public ResponseEntity<?> helloPost() {
		return ResponseEntity.ok(Status.SUCCESS);
	}
}
