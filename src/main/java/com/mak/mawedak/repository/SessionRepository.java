package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    List<Session> findAllByPatient_PatientId(Long patientId);

    // Find all sessions for a specific therapist
    Page<Session> findAllByTherapist_TherapistId(Long therapistId, Pageable pageable);

    // Find all sessions for a specific customer
    Page<Session> findAllByCustomer_CustomerId(Long customerId, Pageable pageable);
}

