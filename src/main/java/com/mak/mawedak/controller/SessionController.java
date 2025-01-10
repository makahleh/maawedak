package com.mak.mawedak.controller;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    // TODO to remove
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        // TODO to remove
        Customer customer = customerRepository.findById(1L).get();
        SessionDTO createdSession = sessionService.createSession(customer, sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    // Delete a session
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

