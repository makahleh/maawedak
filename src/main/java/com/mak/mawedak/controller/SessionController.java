package com.mak.mawedak.controller;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.SessionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@SecurityRequirement(name = "bearerAuth")
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

    @PutMapping
    public ResponseEntity<SessionDTO> updateSession(@RequestBody SessionDTO sessionDTO) {
        // TODO to remove
        Customer customer = customerRepository.findById(1L).get();
        SessionDTO createdSession = sessionService.createSession(customer, sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SessionDTO>> getSessions(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Long therapistId,
            @RequestParam(required = false) Long departmentId) {
        Long customerId = 1L;  // TODO to remove
        List<SessionDTO> patientsPage = sessionService.getSessionsForCalender(
                customerId,
                startDate,
                endDate,
                therapistId,
                departmentId);
        return new ResponseEntity<>(patientsPage, HttpStatus.OK);
    }

    // Delete a session
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

