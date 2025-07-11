package com.mak.mawedak.service;

import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Session;
import com.mak.mawedak.entity.Subscription;
import com.mak.mawedak.mapper.PaymentMapper;
import com.mak.mawedak.mapper.SessionMapper;
import com.mak.mawedak.repository.PaymentRepository;
import com.mak.mawedak.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // Create session
    public SessionDTO createSession(Long customerId, SessionDTO sessionDto) throws RuntimeException {
        if (sessionDto.getSessionId() != null) {
            throw new RuntimeException("Creating Session should not have a sessionId");
        }

        Session session = SessionMapper.toEntity(sessionDto);
        session.setCustomer(new Customer(customerId));
        session = sessionRepository.save(session);
        return SessionMapper.toDTO(session);
    }

    public SessionDTO updateSession(Long customerId, SessionDTO sessionDto) throws RuntimeException {
        if (sessionDto.getSessionId() == null) {
            throw new RuntimeException("Updating Session should have a sessionId");
        }

        Session session = SessionMapper.toEntity(sessionDto);
        session.setCustomer(new Customer(customerId));
        session = sessionRepository.save(session);
        return SessionMapper.toDTO(session);
    }

    // Get sessions for a specific patient with pagination
//    public List<SessionDTO> getCompletedSessionsByPatientId(Long patientId) {
//        List<Session> sessions = sessionRepository.findAllByPatient_PatientIdAndStatusOrderByStartDateTimeDesc(patientId, true);
//
//        return sessions.stream().map(SessionMapper::toDTO).toList();
//    }

    public List<SessionDTO> getSessionsForCalender(
            Long customerId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long therapistId,
            Long departmentId) {
        List<Session> sessions = sessionRepository.findSessions(
                customerId,
                startDate,
                endDate,
                therapistId,
                departmentId);

        return sessions.stream().map(SessionMapper::toDTO).toList();
    }

    // Delete session by ID
    public void deleteSession(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Session not found");
        }
        sessionRepository.deleteById(sessionId);
    }

}
