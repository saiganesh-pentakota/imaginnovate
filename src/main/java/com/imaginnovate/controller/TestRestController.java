package com.imaginnovate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author saiganesh
 *
 */
@RestController
@RequestMapping("/api")
public class TestRestController {

	public TestRestController() {
		super();
	}

	@GetMapping("/resource")
	private String getStart(@RequestParam(value = "name", defaultValue = "imaginnovate") String name) {
		// http://localhost:8080/source/api/resource
		return String.format("Hello %s!", name);
	}
}
