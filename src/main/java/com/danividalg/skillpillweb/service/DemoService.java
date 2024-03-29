package com.danividalg.skillpillweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danividalg.skillpillweb.database.MongoDB;
import com.danividalg.skillpillweb.database.model.Hello;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api")
public class DemoService {
	@Autowired
	private MongoDB db;
	
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Hello> getHello() {
    	return ResponseEntity.ok(db.getRandomHello());
    }
}
