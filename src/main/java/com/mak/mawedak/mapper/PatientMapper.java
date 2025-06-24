package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.*;
import com.mak.mawedak.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        if (patientDto.getTherapist() != null && patientDto.getTherapist().getId() != null) {
            patient.setTherapist(new Therapist(patientDto.getTherapist().getId()));
        }

        if (patientDto.getDepartmentId() != null) {
            patient.setDepartment(new Department(patientDto.getDepartmentId()));
        }

        if (patientDto.getTreatmentMethods() != null) {
            patient.setTreatmentMethods(
                    patientDto.getTreatmentMethods().stream()
                            .map(TreatmentMethod::new)
                            .collect(Collectors.toList())
            );
        }

        // Update subscriptions without wiping existing ones
        if (patientDto.getSubscriptions() != null) {
            List<Subscription> updatedSubs = new ArrayList<>();

            for (SubscriptionDTO dto : patientDto.getSubscriptions()) {
                Subscription existing = existingPatient != null && existingPatient.getSubscriptions() != null
                        ? existingPatient.getSubscriptions().stream()
                        .filter(s -> s.getSubscriptionId().equals(dto.getSubscriptionId()))
                        .findFirst()
                        .orElse(null)
                        : null;

                updatedSubs.add(SubscriptionMapper.toEntity(dto, patient, existing));
            }

            if (patient.getSubscriptions() == null) {
                patient.setSubscriptions(new ArrayList<>());
            } else {
                patient.getSubscriptions().clear();
            }

            patient.getSubscriptions().addAll(updatedSubs);
        }

        // Update payments without wiping existing ones
        if (patientDto.getPayments() != null) {
            List<Payment> updatedPayments = new ArrayList<>();

            for (PaymentDTO dto : patientDto.getPayments()) {
                Payment existing = existingPatient != null && existingPatient.getPayments() != null
                        ? existingPatient.getPayments().stream()
                        .filter(p -> p.getPaymentId().equals(dto.getPaymentId()))
                        .findFirst()
                        .orElse(null)
                        : null;

                updatedPayments.add(PaymentMapper.toEntity(dto, patient, existing, null));
            }

            if (patient.getPayments() == null) {
                patient.setPayments(new ArrayList<>());
            } else {
                patient.getPayments().clear();
            }

            patient.getPayments().addAll(updatedPayments);
        }

        // Sessions are NOT modified — this prevents accidental deletion
        return patient;
    }

}

