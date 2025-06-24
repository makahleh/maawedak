package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.*;
import com.mak.mawedak.entity.*;
import java.time.LocalDateTime;

public class SessionMapper {

    public static SessionDTO toDTO(Session session) {
        if (session == null) {
            return null;
        }
        SessionDTO dto = new SessionDTO();
        dto.setSessionId(session.getSessionId());

        // Map patient and therapist as IdNameDTO
        if (session.getPatient() != null) {
            dto.setPatient(new IdNameDTO(session.getPatient().getPatientId(), session.getPatient().getName()));
        }
        if (session.getTherapist() != null) {
            dto.setTherapist(new IdNameDTO(session.getTherapist().getTherapistId(), session.getTherapist().getName()));
        }

        if (session.getSubscription() != null) {
            dto.setActiveSubscription(SubscriptionMapper.toDTO(session.getSubscription()));
        }

        if (session.getPayment() != null) {
            dto.setPayment(PaymentMapper.toDTO(session.getPayment()));
        }

        dto.setStartDateTime(session.getStartDateTime() != null ? session.getStartDateTime().toString() : null);
        dto.setEndDateTime(session.getEndDateTime() != null ? session.getEndDateTime().toString() : null);
        dto.setNotes(session.getNotes());
        dto.setStatusId(session.getSessionStatus() != null ? session.getSessionStatus().getSessionStatusId() : null);

        return dto;
    }

    public static Session toEntity(SessionDTO dto) {
        if (dto == null) {
            return null;
        }
        Session session = new Session();
        session.setSessionId(dto.getSessionId());

        // Set patient and therapist by ID only
        Patient patient = null;
        if (dto.getPatient() != null && dto.getPatient().getId() != null) {
            patient = new Patient(dto.getPatient().getId());
            session.setPatient(patient);
        }
        if (dto.getTherapist() != null && dto.getTherapist().getId() != null) {
            session.setTherapist(new Therapist(dto.getTherapist().getId()));
        }
        if (dto.getActiveSubscription() != null && dto.getActiveSubscription().getSubscriptionId() != null) {
            var subscriptionId = dto.getActiveSubscription().getSubscriptionId();
            session.setSubscription(new Subscription(subscriptionId));

            // just to avoid setting subscriptionId again in Payment object when creating a new session
            if (dto.getPayment() != null) {
                session.setPayment(PaymentMapper.toEntity(dto.getPayment(), patient, null, subscriptionId));
            }
        }

        session.setStartDateTime(dto.getStartDateTime() != null ? LocalDateTime.parse(dto.getStartDateTime()) : null);
        session.setEndDateTime(dto.getEndDateTime() != null ? LocalDateTime.parse(dto.getEndDateTime()) : null);
        session.setNotes(dto.getNotes());

        // Set status by ID if needed (assuming SessionStatus has a constructor with ID)
        if (dto.getStatusId() != null) {
            session.setSessionStatus(new SessionStatus(dto.getStatusId()));
        }

        return session;
    }
}
