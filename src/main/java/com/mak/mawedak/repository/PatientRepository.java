package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.repository.projection.CountByIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find all active patients for a specific customer
    Page<Patient> findAllByCustomer_CustomerIdAndIsActive(Long customerId, boolean isActive, Pageable pageable);

    // Find a specific active patient by ID and customer ID
    Optional<Patient> findByCustomer_CustomerIdAndPatientIdAndIsActive(Long customerId, Long id, boolean isActive);

    // Set patient as inactive
    @Modifying
    @Query("UPDATE Patient p SET p.isActive = false WHERE p.patientId = :patientId AND p.customer.id = :customerId")
    void setInactive(@Param("customerId") Long customerId, @Param("patientId") Long patientId);

    Page<Patient> findByNameContainingIgnoreCaseAndCustomer_CustomerIdAndIsActive(String searchTerm, Long customerId, boolean isActive, Pageable pageable);

    // For report:
    @Query("""
                SELECT COUNT(p) FROM Patient p
                WHERE p.customer.customerId = :customerId
                AND p.createdDate BETWEEN :from AND :to
            """)
    int countNewPatients(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            SELECT d.id AS departmentId, COUNT(p) AS patientCount
            FROM Patient p
            JOIN p.department d
            WHERE p.customer.id = :customerId
              AND p.createdDate BETWEEN :from AND :to
            GROUP BY d.id
            """)
    List<CountByIdProjection> countNewPatientsByDepartmentId(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            SELECT p.paymentMethod.id AS paymentMethodId, COUNT(p) AS patientCount
            FROM Patient p
            WHERE p.customer.id = :customerId AND p.createdDate BETWEEN :from AND :to
            GROUP BY p.paymentMethod.id
            """)
    List<CountByIdProjection> countNewPatientsByPaymentMethod(Long customerId, LocalDateTime from, LocalDateTime to);
}

