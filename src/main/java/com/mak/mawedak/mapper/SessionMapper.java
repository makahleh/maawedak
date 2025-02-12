package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionMapper {

    // Convert Session entity to SessionDTO
    public SessionDTO toDTO(Session session) {
        if (session == null) {
            return null;
        }

        Long paymentMethodId = session.getPaymentMethod() != null ? session.getPaymentMethod().getPaymentMethodId() : null;
        Long insuranceId = session.getInsurance() != null ? session.getInsurance().getInsuranceId() : null;
        String insuranceName = session.getInsurance() != null ? session.getInsurance().getName() : null;


        return new SessionDTO(
                session.getSessionId(),
                session.getPatient().getPatientId(),
                session.getPatient().getName(),
                session.getTherapist().getTherapistId(),
                session.getTherapist().getName(),
                paymentMethodId,
                insuranceId,
                insuranceName,
                session.getStartDateTime().toString(),
                session.getEndDateTime().toString(),
                session.getNotes(),
                session.getPaymentAmount(),
                session.getStatus(),
                session.getPatient().getDepartment() != null ? session.getPatient().getDepartment().getDepartmentId() : null
        );
    }

    // Convert SessionDTO to Session entity
    public Session toEntity(SessionDTO sessionDto, Customer customer) {
        if (sessionDto == null) {
            return null;
        }
        Session session = new Session();
        session.setCustomer(customer);
        session.setPatient(new Patient(sessionDto.getPatientId()));
        session.setTherapist(sessionDto.getTherapistId() != null ? new Therapist(sessionDto.getTherapistId()) : null);
        session.setPaymentMethod(sessionDto.getPaymentMethodId() != null ? new PaymentMethod(sessionDto.getPaymentMethodId()) : null);
        session.setInsurance(sessionDto.getInsuranceId() != null ? new Insurance(sessionDto.getInsuranceId()) : null);
        session.setStartDateTime(sessionDto.getStartDateTime() != null ? LocalDateTime.parse(sessionDto.getStartDateTime()) : null);
        session.setEndDateTime(sessionDto.getEndDateTime() != null ? LocalDateTime.parse(sessionDto.getEndDateTime()) : null);
        session.setNotes(sessionDto.getNotes());
        session.setPaymentAmount(sessionDto.getPaymentAmount());
        session.setStatus(sessionDto.getStatus());
        return session;
    }
}

