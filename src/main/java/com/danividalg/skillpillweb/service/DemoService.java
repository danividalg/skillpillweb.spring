package com.danividalg.skillpillweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danividalg.skillpillweb.database.MongoDB;
import com.danividalg.skillpillweb.database.model.Hello;

@RestController
@RequestMapping("/api")
public class DemoService {
	@Autowired
	private MongoDB db;
	
    @GetMapping("/hello")
    public ResponseEntity<Hello> getHello() {
    	return ResponseEntity.ok(db.getRandomHello());
    }
}
