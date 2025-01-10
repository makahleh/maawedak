package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Session;
import com.mak.mawedak.entity.Therapist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionMapper {

    // Convert Session entity to SessionDTO
    public SessionDTO toDTO(Session session) {
        if (session == null) {
            return null;
        }

        // Get patient and therapist names from their respective entities
        String patientName = session.getPatient() != null ? session.getPatient().getFirstName() : null;
        String therapistName = session.getTherapist() != null ? session.getTherapist().getFirstName() : null;

        return new SessionDTO(
                session.getSessionId(),
                session.getPatient().getPatientId(),
                patientName,
                session.getTherapist().getTherapistId(),
                therapistName,
                session.getSessionDateTime() != null ? session.getSessionDateTime().toString() : null,
                session.getNotes(),
                session.getPaymentAmount()
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
        session.setTherapist(new Therapist(sessionDto.getTherapistId()));  // Assuming Therapist ID is provided
        session.setSessionDateTime(sessionDto.getSessionDateTime() != null ? LocalDateTime.parse(sessionDto.getSessionDateTime()) : null);
        session.setNotes(sessionDto.getNotes());
        session.setPaymentAmount(sessionDto.getPaymentAmount());
        return session;
    }
}

