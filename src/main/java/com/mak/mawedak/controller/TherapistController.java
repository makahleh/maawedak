package com.mak.mawedak.controller;

import com.mak.mawedak.dto.TherapistDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.TherapistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/therapists")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    // TODO to remove
    @Autowired
    private CustomerRepository customerRepository;

    // Create therapist
    @PostMapping
    public ResponseEntity<TherapistDTO> createTherapist(
            @Valid @RequestBody TherapistDTO therapistDTO) {
        // TODO to remove
        Customer customer = customerRepository.findById(1L).get();
        TherapistDTO savedTherapist = therapistService.createTherapist(customer, therapistDTO);
        return new ResponseEntity<>(savedTherapist, HttpStatus.CREATED);
    }

    // Update therapist
    @PutMapping
    public ResponseEntity<TherapistDTO> updateTherapist(
            @Valid @RequestBody TherapistDTO therapistDTO) {
        // TODO to remove
        Customer customer = customerRepository.findById(1L).get();
        TherapistDTO savedTherapist = therapistService.updateTherapist(customer, therapistDTO);
        return new ResponseEntity<>(savedTherapist, HttpStatus.CREATED);
    }

    // Get therapists by customer ID with pagination
    @GetMapping("/list")
    public ResponseEntity<Page<TherapistDTO>> getTherapists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long customerId = 1L;
        Page<TherapistDTO> therapistsPage = therapistService.getTherapists(customerId, page, size);
        return new ResponseEntity<>(therapistsPage, HttpStatus.OK);
    }

    // Get therapist by ID
    @GetMapping("/{therapistId}")
    public ResponseEntity<TherapistDTO> getTherapistById(
            @PathVariable Long therapistId) {
        Long customerId = 1L;
        Optional<TherapistDTO> therapistDTO = therapistService.getTherapistById(customerId, therapistId);
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
