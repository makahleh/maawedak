package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Therapist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, Long> {

    // Find all therapists for a specific customer
    Page<Therapist> findAllByCustomer_CustomerIdAndIsActive(Long customerId, boolean isActive, Pageable pageable);

    // Find a specific therapist by ID and customer ID
    Optional<Therapist> findByCustomer_CustomerIdAndTherapistIdAndIsActive(Long customerId, Long id, boolean isActive);

    // Set therapist as inactive (this can be a custom query method if you don't want to manually update)
    @Modifying
    @Query("UPDATE Therapist t SET t.isActive = false WHERE t.id = :therapistId AND t.customer.id = :customerId")
    void setInactive(@Param("customerId") Long customerId, @Param("therapistId") Long therapistId);

    Optional<Therapist> findByUsernameAndIsActive(String username, boolean isActive);
}

