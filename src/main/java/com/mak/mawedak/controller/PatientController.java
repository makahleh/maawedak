package com.mak.mawedak.controller;

import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // TODO to remove
    @Autowired
    private CustomerRepository customerRepository;

    // Create patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        Customer customer = customerRepository.findById(1L).get();
        PatientDTO savedPatient = patientService.createPatient(customer, patientDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    // Update patient
    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody PatientDTO patientDTO) {
        // TODO to remove
        Customer customer = customerRepository.findById(1L).get();
        PatientDTO updatedPatient = patientService.updatePatient(customer, patientDTO);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    // Get patients by customer ID with pagination
    @GetMapping("/list")
    public ResponseEntity<Page<PatientDTO>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long customerId = 1L;  // TODO to remove
        Page<PatientDTO> patientsPage = patientService.getPatients(customerId, page, size);
        return new ResponseEntity<>(patientsPage, HttpStatus.OK);
    }

    // Get patient by ID
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long patientId) {
        Long customerId = 1L;  // TODO to remove
        Optional<PatientDTO> patientDTO = patientService.getPatientById(customerId, patientId);
        return patientDTO
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PatientDTO>> search(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Customer customer = customerRepository.findById(1L).get();  // TODO to remove
        Page<PatientDTO> results = patientService.searchPatients(customer, searchTerm, size, page);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Deactivate patient
    @PutMapping("/{patientId}/deactivate")
    public ResponseEntity<PatientDTO> deactivatePatient(@PathVariable Long patientId) {
        Long customerId = 1L;  // TODO to remove
        Optional<PatientDTO> deactivatedPatient = patientService.deactivatePatient(customerId, patientId);
        return deactivatedPatient
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

