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

        boolean isDone = session.getStatus() != null && session.getStatus();

        Long paymentMethodId;

        if (!isDone) {
            paymentMethodId = session.getPatient().getPaymentMethod() != null
                    ? session.getPatient().getPaymentMethod().getPaymentMethodId()
                    : null;
        } else {
            paymentMethodId = session.getPaymentMethod() != null
                    ? session.getPaymentMethod().getPaymentMethodId()
                    : null;
        }

        // Use insurance from session if done, else from patient
        Insurance insurance = isDone ? session.getInsurance() : session.getPatient().getInsurance();
        Long insuranceId = insurance != null ? insurance.getInsuranceId() : null;
        String insuranceName = insurance != null ? insurance.getName() : null;

        // Use payment amount from session if done, else from patient sessionPrice
        double paymentAmount = isDone ? session.getPaymentAmount() : session.getPatient().getSessionPrice();

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
                paymentAmount,
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
        session.setSessionId(sessionDto.getSessionId());
        session.setCustomer(customer);
        session.setPatient(new Patient(sessionDto.getPatientId()));
        session.setTherapist(sessionDto.getTherapistId() != null ? new Therapist(sessionDto.getTherapistId()) : null);
        session.setPaymentMethod(sessionDto.getPaymentMethodId() != null ? new PaymentMethod(sessionDto.getPaymentMethodId()) : null);
        session.setInsurance(sessionDto.getInsuranceId() != null ? new Insurance(sessionDto.getInsuranceId()) : null);
        session.setStartDateTime(sessionDto.getStartDateTime() != null ? LocalDateTime.parse(sessionDto.getStartDateTime()) : null);
        session.setEndDateTime(sessionDto.getEndDateTime() != null ? LocalDateTime.parse(sessionDto.getEndDateTime()) : null);
        session.setNotes(sessionDto.getNotes());
        session.setPaymentAmount(sessionDto.getPaymentAmount());
        session.setStatus(sessionDto.getStatus() != null && sessionDto.getStatus());
        return session;
    }
}

