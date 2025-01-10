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
        return toDTO(patient, 0.0, Collections.emptyList());
    }
    public PatientDTO toDTO(Patient patient, double balance, List<SessionDTO> sessions) {
        return new PatientDTO(
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getTherapist().getTherapistId(),
                patient.getTherapist().getFirstName(),
                patient.getDepartment().getDepartmentId(),
                patient.getDepartment().getDepartmentName(),
                patient.getPaymentMethod().getPaymentMethodId(),
                patient.getInsurance().getInsuranceId(),
                patient.getInsurance().getName(),
                patient.getSessionPrice(),
                patient.getNumberOfTotalSessions(),
                patient.getNotes(),
                patient.getCreatedDate().toString(),
                patient.getSessions() != null ? patient.getSessions().size() : 0,
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
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setTherapist(new Therapist(patientDto.getTherapistId()));
        patient.setDepartment(new Department(patientDto.getDepartmentId()));
        patient.setPaymentMethod(new PaymentMethod(patientDto.getPaymentMethodId()));
        patient.setInsurance(patientDto.getInsuranceId() != null ? new Insurance(patientDto.getInsuranceId()) : null);
        patient.setSessionPrice(patientDto.getSessionPrice());
        patient.setNumberOfTotalSessions(patientDto.getNumberOfTotalSessions());
        patient.setNotes(patientDto.getNotes());
        return patient;
    }
}

