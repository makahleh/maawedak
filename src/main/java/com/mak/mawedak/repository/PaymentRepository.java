package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.repository.projection.CountByIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    @Query("""
                SELECT p.paymentMethod.paymentMethodId AS id, COUNT(DISTINCT p.patient.patientId) AS count
                FROM Payment p
                WHERE p.patient.customer.customerId = :customerId
                  AND p.createdDate BETWEEN :from AND :to
                GROUP BY p.paymentMethod.paymentMethodId
            """)
    List<CountByIdProjection> countPatientsByPaymentMethod(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

}
