package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    List<Session> findAllByPatient_PatientId(Long patientId);

    // Find all sessions for a specific therapist
    Page<Session> findAllByTherapist_TherapistId(Long therapistId, Pageable pageable);

    // Find all sessions for a specific customer
    @Query("SELECT s FROM Session s " +
            "WHERE s.customer.customerId = :customerId " +
            "AND s.startDateTime <= :endDateTime " +
            "AND s.endDateTime >= :startDateTime " +
            "AND (:therapistId IS NULL OR s.therapist.therapistId = :therapistId) " +
            "AND (:departmentId IS NULL OR s.therapist.department.departmentId = :departmentId)")
    List<Session> findSessions(
            @Param("customerId") Long customerId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("therapistId") Long therapistId,
            @Param("departmentId") Long departmentId);


}

