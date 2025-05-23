package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Session;
import com.mak.mawedak.repository.projection.CountByNameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    List<Session> findAllByPatient_PatientId(Long patientId);

    // Find all sessions for a specific therapist
    Page<Session> findAllByTherapist_TherapistId(Long therapistId, Pageable pageable);

    int countByPatient_PatientIdAndStatusTrue(Long patientId);

    // Find all sessions for a specific customer
    @Query("SELECT s FROM Session s " +
            "WHERE s.customer.customerId = :customerId " +
            "AND s.startDateTime <= :endDateTime " +
            "AND s.endDateTime >= :startDateTime " +
            "AND (:therapistId IS NULL OR s.therapist.therapistId = :therapistId) " +
            "AND (:departmentId IS NULL OR s.patient.department.departmentId = :departmentId)")
    List<Session> findSessions(
            @Param("customerId") Long customerId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("therapistId") Long therapistId,
            @Param("departmentId") Long departmentId);

    // For Reports:
    @Query("""
                SELECT COUNT(s) FROM Session s
                WHERE s.status = true
                AND s.customer.customerId = :customerId
                AND s.startDateTime BETWEEN :from AND :to
            """)
    int countCompletedSessions(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
                SELECT SUM(
                    CASE
                        WHEN s.paymentMethod.id = 1 THEN s.paymentAmount
                        WHEN s.paymentMethod.id = 2 THEN s.paymentAmount + (s.insurance.sessionPrice * s.insurance.percentage)
                        ELSE 0
                    END
                )
                FROM Session s
                WHERE s.customer.id = :customerId
                AND s.startDateTime BETWEEN :from AND :to
            """)
    BigDecimal calculateTotalRevenue(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            SELECT s.therapist.name AS therapistName, COUNT(s) AS sessionCount
            FROM Session s
            WHERE s.customer.id = :customerId AND s.startDateTime BETWEEN :from AND :to
            GROUP BY s.therapist.name
            """)
    List<CountByNameProjection> countSessionsByTherapist(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

