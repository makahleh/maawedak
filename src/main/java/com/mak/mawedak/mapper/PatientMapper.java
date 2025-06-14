package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PatientMapper {

    public PatientDTO toDTO(Patient patient) {
        return toDTO(patient, 0.0, 0);
    }

    public PatientDTO toDTO(Patient patient, double balance, int completedSessions) {
        return toDTO(patient, balance, completedSessions, Collections.emptyList());
    }

    public PatientDTO toDTO(Patient patient, double balance, int completedSessions, List<SessionDTO> sessions) {
        return new PatientDTO(
                patient.getPatientId(),
                patient.getName(),
                patient.getAge(),
                patient.getPhoneNumber(),
                patient.getTherapist().getTherapistId(),
                patient.getTherapist().getName(),
                patient.getDepartment().getDepartmentId(),
                patient.getDepartment().getDepartmentName(),
                patient.getPaymentMethod().getPaymentMethodId(),
                patient.getTreatmentMethods(),
                patient.getInsurance() != null ? patient.getInsurance().getInsuranceId() : null,
                patient.getInsurance() != null ? patient.getInsurance().getName() : null,
                patient.getSessionPrice(),
                patient.getNumberOfTotalSessions(),
                patient.getNotes(),
                patient.getCreatedDate().toString(),
                completedSessions,
                patient.getExpiryDate(),
                balance,
                sessions
        );
    }

    public Patient toEntity(PatientDTO patientDto, Patient existingPatient) {
        if (patientDto == null) {
            return null;
        }
        Patient patient = existingPatient != null ? existingPatient : new Patient();
        patient.setPatientId(patientDto.getPatientId());
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setTherapist(new Therapist(patientDto.getTherapistId()));
        patient.setDepartment(new Department(patientDto.getDepartmentId()));
        patient.setPaymentMethod(new PaymentMethod(patientDto.getPaymentMethodId()));
        patient.setExpiryDate(patientDto.getExpiryDate());
        patient.setTreatmentMethods(patientDto.getTreatmentMethods());
        patient.setInsurance(patientDto.getInsuranceId() != null ? new Insurance(patientDto.getInsuranceId()) : null);
        patient.setSessionPrice(patientDto.getSessionPrice());
        patient.setNumberOfTotalSessions(patientDto.getNumberOfTotalSessions());
        patient.setNotes(patientDto.getNotes());

        return patient;
    }
}

