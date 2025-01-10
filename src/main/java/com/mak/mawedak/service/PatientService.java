package com.mak.mawedak.service;

//ADD TO GITHUB
import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Session;
import com.mak.mawedak.mapper.PatientMapper;
import com.mak.mawedak.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PatientMapper patientMapper;

    @Autowired
    private SessionService sessionService;

    // Create patient
    public PatientDTO createPatient(Customer customer, PatientDTO patientDto) throws RuntimeException {
        if (patientDto.getPatientId() != null) {
            throw new RuntimeException("Creating Patient should not have a patientId");
        }
        return savePatient(customer, patientDto, null);
    }

    // Update patient
    public PatientDTO updatePatient(Customer customer, PatientDTO patientDto) throws RuntimeException {
        Patient patient = patientRepository
                        .findByCustomer_CustomerIdAndPatientIdAndIsActive(customer.getCustomerId(), patientDto.getPatientId(), true)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
        return savePatient(customer, patientDto, patient);
    }

    private PatientDTO savePatient(Customer customer, PatientDTO patientDto, Patient existingPatient) {
        Patient patient = patientMapper.toEntity(patientDto, existingPatient);
        patient.setCustomer(customer);
        patient = patientRepository.save(patient);
        return patientMapper.toDTO(patient);
    }

    // Get Page of patients by customer ID
    public Page<PatientDTO> getPatients(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        Page<Patient> patientsPage =
                patientRepository.findAllByCustomer_CustomerIdAndIsActive(customerId, true, pageable);

        // Map the entities to DTOs
        return patientsPage.map(patientMapper::toDTO);
    }

    // Set patient as inactive
    public Optional<PatientDTO> deactivatePatient(Long customerId, Long patientId) {
        Optional<PatientDTO> dto = getPatientById(customerId, patientId);
        patientRepository.setInactive(customerId, patientId);
        return dto;
    }

    // Get patient by ID
    public Optional<PatientDTO> getPatientById(Long customerId, Long patientId) {
        Patient patient =
                patientRepository.findByCustomer_CustomerIdAndPatientIdAndIsActive(customerId, patientId, true)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
        List<SessionDTO> sessions = sessionService.getSessionsByPatientId(patientId);
        double balance = calculatePatientBalance(sessions);
        return Optional.of(patientMapper.toDTO(patient, balance, sessions));
    }

    private double calculatePatientBalance(List<SessionDTO> sessions) {
        return sessions.stream()
                .mapToDouble(SessionDTO::getPaymentAmount)
                .sum();
    }

    public Page<PatientDTO> searchPatients(Customer customer, String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdDate")));
        String[] nameParts = searchTerm.split(" ");
        String firstPart = nameParts[0];
        String secondPart = nameParts.length > 1 ? nameParts[1] : firstPart;

        Patient examplePatient = new Patient();
        examplePatient.setFirstName(firstPart);
        examplePatient.setLastName(secondPart);
        examplePatient.setCustomer(customer);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // allows partial matching
                .withIgnoreCase(); // ignore case while matching
        Example<Patient> patientExample = Example.of(examplePatient, matcher);
        Page<Patient> patients = patientRepository.findAll(patientExample, pageable);
        return patients.map(patientMapper::toDTO);
    }

}

