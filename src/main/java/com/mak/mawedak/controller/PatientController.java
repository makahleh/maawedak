package com.mak.mawedak.controller;

import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.service.PatientService;
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
@RequestMapping("/api/patients")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Create patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO savedPatient = patientService.createPatient(ContextHolderHelper.getCustomerId(), patientDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    // Update patient
    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(ContextHolderHelper.getCustomerId(), patientDTO);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    // Get patients by customer ID with pagination
    @GetMapping("/list")
    public ResponseEntity<Page<PatientDTO>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PatientDTO> patientsPage = patientService.getPatients(ContextHolderHelper.getCustomerId(), page, size);
        return new ResponseEntity<>(patientsPage, HttpStatus.OK);
    }

    // Get patient by ID
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long patientId) {
        Optional<PatientDTO> patientDTO = patientService.getPatientDetails(ContextHolderHelper.getCustomerId(), patientId);
        return patientDTO
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PatientDTO>> search(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PatientDTO> results = patientService.searchPatients(ContextHolderHelper.getCustomerId(), searchTerm, page, size);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Deactivate patient
    @PutMapping("/{patientId}/deactivate")
    public ResponseEntity<PatientDTO> deactivatePatient(@PathVariable Long patientId) {
        Optional<PatientDTO> deactivatedPatient = patientService.deactivatePatient(ContextHolderHelper.getCustomerId(), patientId);
        return deactivatedPatient
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

