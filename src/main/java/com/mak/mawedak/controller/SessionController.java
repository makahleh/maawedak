package com.mak.mawedak.controller;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.service.SessionService;
import com.mak.mawedak.utils.ContextHolderHelper;
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
@CrossOrigin(origins = "http://localhost:8080")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        SessionDTO createdSession = sessionService.createSession(ContextHolderHelper.getCustomerId(), sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @PutMapping
    public ResponseEntity<SessionDTO> updateSession(@RequestBody SessionDTO sessionDTO) {
        SessionDTO createdSession = sessionService.updateSession(ContextHolderHelper.getCustomerId(), sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SessionDTO>> getSessions(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Long therapistId,
            @RequestParam(required = false) Long departmentId) {
        List<SessionDTO> patientsPage = sessionService.getSessionsForCalender(
                ContextHolderHelper.getCustomerId(),
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

