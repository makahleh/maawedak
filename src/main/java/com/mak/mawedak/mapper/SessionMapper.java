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
        String patientName = session.getPatient() != null ? session.getPatient().getName() : null;
        String therapistName = session.getTherapist() != null ? session.getTherapist().getName() : null;

        return new SessionDTO(
                session.getSessionId(),
                session.getPatient().getPatientId(),
                patientName,
                session.getTherapist().getTherapistId(),
                therapistName,
                session.getStartDateTime().toString(),
                session.getEndDateTime().toString(),
                session.getNotes(),
                session.getPaymentAmount(),
                session.getStatus()
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
        session.setStartDateTime(sessionDto.getStartDateTime() != null ? LocalDateTime.parse(sessionDto.getStartDateTime()) : null);
        session.setEndDateTime(sessionDto.getEndDateTime() != null ? LocalDateTime.parse(sessionDto.getEndDateTime()) : null);
        session.setNotes(sessionDto.getNotes());
        session.setPaymentAmount(sessionDto.getPaymentAmount());
        session.setStatus(sessionDto.getStatus());
        return session;
    }
}

