package com.mak.mawedak.service;

import com.mak.mawedak.dto.PatientDTO;
import com.mak.mawedak.dto.SessionDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Session;
import com.mak.mawedak.mapper.SessionMapper;
import com.mak.mawedak.mapper.SessionReviewMapper;
import com.mak.mawedak.repository.SessionRepository;
import com.mak.mawedak.repository.SessionReviewRepository;
import com.mak.mawedak.entity.SessionReview;
import com.mak.mawedak.dto.SessionReviewDTO;
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
    private PatientService patientService;

    @Autowired
    private SessionReviewRepository sessionReviewRepository;

    // Create session
    public SessionDTO createSession(Long customerId, SessionDTO sessionDto) throws RuntimeException {
        if (sessionDto.getSessionId() != null) {
            throw new RuntimeException("Creating Session should not have a sessionId");
        }

        if (sessionDto.getPatient() != null && sessionDto.getPatient().getPatientId() == null) {
            PatientDTO createdPatient = patientService.createPatient(customerId, sessionDto.getPatient());
            sessionDto.setPatient(createdPatient);
        }

        Session session = SessionMapper.toEntity(sessionDto);
        session.setCustomer(new Customer(customerId));
        session = sessionRepository.save(session);
        return SessionMapper.toDTO(session, true);
    }

    public SessionDTO updateSession(Long customerId, SessionDTO sessionDto) throws RuntimeException {
        if (sessionDto.getSessionId() == null) {
            throw new RuntimeException("Updating Session should have a sessionId");
        }

        if (sessionDto.getPatient() != null && sessionDto.getPatient().getPatientId() == null) {
            PatientDTO createdPatient = patientService.createPatient(customerId, sessionDto.getPatient());
            sessionDto.setPatient(createdPatient);
        }

        Session session = SessionMapper.toEntity(sessionDto);
        session.setCustomer(new Customer(customerId));
        session = sessionRepository.save(session);
        return SessionMapper.toDTO(session, true);
    }

    // Get sessions for a specific patient with pagination
    // public List<SessionDTO> getCompletedSessionsByPatientId(Long patientId) {
    // List<Session> sessions =
    // sessionRepository.findAllByPatient_PatientIdAndStatusOrderByStartDateTimeDesc(patientId,
    // true);
    //
    // return sessions.stream().map(SessionMapper::toDTO).toList();
    // }

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

        return sessions.stream().map(
                session -> SessionMapper.toDTO(session, true)).toList();
    }

    // Delete session by ID
    public void deleteSession(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Session not found");
        }
        sessionRepository.deleteById(sessionId);
    }

    public SessionDTO reviewSession(Long sessionId, SessionReviewDTO reviewDTO) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (reviewDTO.getReviewId() == null) {
            if (session.getSessionReview() != null) {
                throw new RuntimeException(
                        "A review already exists for this session. Please provide the review ID to update it.");
            }
        }

        SessionReview review = SessionReviewMapper.toEntity(reviewDTO);
        review.setSession(session);

        review = sessionReviewRepository.save(review);
        session.setSessionReview(review); // Make sure it's returned
        return SessionMapper.toDTO(session, true);
    }
}
