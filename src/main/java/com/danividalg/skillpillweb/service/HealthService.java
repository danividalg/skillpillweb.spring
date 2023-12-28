package com.danividalg.skillpillweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/health")
public class HealthService {
	private static final Logger logger = LoggerFactory.getLogger(HealthService.class);
	
	private boolean isHealth = true;
	
	@GetMapping("/health")
	public ResponseEntity<String> health() {
		if (isHealth) {
			return ResponseEntity.ok("Healthy");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Healthy");
		}
	}

	@GetMapping("/crash")
	public ResponseEntity<String> crash() {
		logger.error("Crash");
		System.exit(1);
		return ResponseEntity.ok("Crash");
	}

	@GetMapping("/crash2")
	public ResponseEntity<String> crash2() {
		logger.error("Crash2");
		isHealth = false;
		return ResponseEntity.ok("Crash2");
	}
}
