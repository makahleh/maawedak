package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.IdNameDTO;
import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientDTO toDTO(Patient patient, boolean getForProfile) {
        if (patient == null) {
            return null;
        }

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(patient.getPatientId());
        patientDTO.setName(patient.getName());
        patientDTO.setAge(patient.getAge());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setNationalNumber(patient.getNationalNumber());

        // Therapist mapping
        if (patient.getTherapist() != null) {
            patientDTO.setTherapist(
                    new IdNameDTO(
                            patient.getTherapist().getTherapistId(),
                            patient.getTherapist().getName()
                    )
            );
        }

        // Department mapping
        patientDTO.setDepartmentId(patient.getDepartment() != null ? patient.getDepartment().getDepartmentId() : null);

        // Treatment methods mapping
        if (patient.getTreatmentMethods() != null) {
            patientDTO.setTreatmentMethods(
                    patient.getTreatmentMethods().stream()
                            .map(TreatmentMethod::getTreatmentMethodId)
                            .toList()
            );
        }

        patientDTO.setNotes(patient.getNotes());
        patientDTO.setCreatedDate(patient.getCreatedDate().toString());

        if (patient.getSubscriptions() != null) {
            patientDTO.setSubscriptions(
                    patient.getSubscriptions().stream()
                            .map(SubscriptionMapper::toDTO)
                            .toList()
            );
        }

        if (patient.getPayments() != null && getForProfile) {
            patientDTO.setPayments(
                    patient.getPayments().stream()
                            .map(PaymentMapper::toDTO)
                            .toList()
            );
        }

        if (patient.getSessions() != null && getForProfile) {
            patientDTO.setSessions(
                    patient.getSessions().stream()
                            .map(SessionMapper::toDTO)
                            .toList()
            );
        }

        return patientDTO;
    }

    public static Patient toEntity(PatientDTO patientDto, Patient existingPatient) {
        if (patientDto == null) {
            return null;
        }
        Patient patient = existingPatient != null ? existingPatient : new Patient();

        patient.setPatientId(patientDto.getPatientId());
        patient.setName(patientDto.getName());
        patient.setNationalNumber(patientDto.getNationalNumber());
        patient.setAge(patientDto.getAge());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setNotes(patientDto.getNotes());

        // Therapist mapping (only id is available in DTO)
        if (patientDto.getTherapist() != null && patientDto.getTherapist().getId() != null) {
            patient.setTherapist(new Therapist(patientDto.getTherapist().getId()));
        }

        // Department mapping (only id is available in DTO)
        if (patientDto.getDepartmentId() != null) {
            patient.setDepartment(new Department(patientDto.getDepartmentId()));
        }

        // Treatment methods mapping (list of ids)
        if (patientDto.getTreatmentMethods() != null) {
            patient.setTreatmentMethods(
                    patientDto.getTreatmentMethods().stream()
                            .map(TreatmentMethod::new)
                            .collect(Collectors.toCollection(java.util.ArrayList::new))
            );
        }

        if (patientDto.getSubscriptions() != null) {
            patient.setSubscriptions(
                    patientDto.getSubscriptions().stream()
                            .map(dto -> SubscriptionMapper.toEntity(dto, patient))
                            .collect(Collectors.toCollection(java.util.ArrayList::new))
            );
        }

        if (patientDto.getPayments() != null) {
            patient.setPayments(
                    patientDto.getPayments().stream()
                            // Setting null because each payment will be linked to a subscription in PaymentDTO
                            .map(dto -> PaymentMapper.toEntity(dto, patient, null))
                            .collect(Collectors.toCollection(java.util.ArrayList::new))
            );
        }

        return patient;
    }
}

