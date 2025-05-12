package com.mak.mawedak.controller;

import com.mak.mawedak.dto.TherapistDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.TherapistService;
import com.mak.mawedak.utils.ContextHolderHelper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/therapists")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    // Create therapist
    @PostMapping
    public ResponseEntity<TherapistDTO> createTherapist(
            @Valid @RequestBody TherapistDTO therapistDTO) {
        TherapistDTO savedTherapist = therapistService.createTherapist(ContextHolderHelper.getCustomerId(), therapistDTO);
        return new ResponseEntity<>(savedTherapist, HttpStatus.CREATED);
    }

    // Update therapist
    @PutMapping
    public ResponseEntity<TherapistDTO> updateTherapist(
            @Valid @RequestBody TherapistDTO therapistDTO) {
        TherapistDTO savedTherapist = therapistService.updateTherapist(ContextHolderHelper.getCustomerId(), therapistDTO);
        return new ResponseEntity<>(savedTherapist, HttpStatus.CREATED);
    }

    // Get therapists by customer ID with pagination
    @GetMapping("/list")
    public ResponseEntity<Page<TherapistDTO>> getTherapists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TherapistDTO> therapistsPage = therapistService.getTherapists(ContextHolderHelper.getCustomerId(), page, size);
        return new ResponseEntity<>(therapistsPage, HttpStatus.OK);
    }

    // Get therapist by ID
    @GetMapping("/{therapistId}")
    public ResponseEntity<TherapistDTO> getTherapistById(
            @PathVariable Long therapistId) {
        Optional<TherapistDTO> therapistDTO = therapistService.getTherapistById(ContextHolderHelper.getCustomerId(), therapistId);
        return therapistDTO
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Deactivate therapist
    @PutMapping("/{therapistId}/deactivate")
    public ResponseEntity<TherapistDTO> deactivateTherapist(
            @PathVariable Long therapistId) {
        Long customerId = 1L;
        Optional<TherapistDTO> deactivatedTherapist = therapistService.deactivateTherapist(customerId, therapistId);
        return deactivatedTherapist
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
