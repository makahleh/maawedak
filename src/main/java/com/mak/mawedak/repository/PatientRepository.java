package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
}

