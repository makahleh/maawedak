package com.mak.mawedak.service;

import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.mapper.PatientMapper;
import com.mak.mawedak.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    @Lazy // used to break cyclic dependency with session service, when we need PatientService in session service
    private SessionService sessionService;

    // Create patient
    public PatientDTO createPatient(Long customerId, PatientDTO patientDto) throws RuntimeException {
        if (patientDto.getPatientId() != null) {
            throw new RuntimeException("Creating Patient should not have a patientId");
        }
        return savePatient(customerId, patientDto, null);
    }

    // Update patient
    public PatientDTO updatePatient(Long customerId, PatientDTO patientDto) throws RuntimeException {
        Patient patient = patientRepository
                .findByCustomer_CustomerIdAndPatientIdAndIsActive(customerId, patientDto.getPatientId(), true)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
        return savePatient(customerId, patientDto, patient);
    }

    private PatientDTO savePatient(Long customerId, PatientDTO patientDto, Patient existingPatient) {
        Patient patient = PatientMapper.toEntity(patientDto, existingPatient);
        patient.setCustomer(new Customer(customerId));
        patient = patientRepository.save(patient);
        return PatientMapper.toDTO(patient, false);
    }

    // Get Page of patients by customer ID
    public Page<PatientDTO> getPatients(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        Page<Patient> patientsPage =
                patientRepository.findAllByCustomer_CustomerIdAndIsActive(customerId, true, pageable);

        return patientsPage.map(p -> PatientMapper.toDTO(p, false));
    }

    // Set patient as inactive
    public Optional<PatientDTO> deactivatePatient(Long customerId, Long patientId) {
        Optional<PatientDTO> dto = getPatientDetails(customerId, patientId);
        patientRepository.setInactive(customerId, patientId);
        return dto;
    }

    // Get patient by ID
    public Optional<PatientDTO> getPatientDetails(Long customerId, Long patientId) {
        Patient patient = getPatientById(customerId, patientId);
        return Optional.of(PatientMapper.toDTO(patient, true));
    }

    public Patient getPatientById(Long customerId, Long patientId) {
        return patientRepository.findByCustomer_CustomerIdAndPatientIdAndIsActive(customerId, patientId, true)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Page<PatientDTO> searchPatients(Long customerId, String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        // use specifications later
        Page<Patient> patients = patientRepository.findByNameContainingIgnoreCaseAndCustomer_CustomerIdAndIsActive(
                searchTerm,
                customerId,
                true,
                pageable);
        return patients.map(p -> PatientMapper.toDTO(p, false));
    }
}

