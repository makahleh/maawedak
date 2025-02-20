package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Insurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Page<Insurance> findAllByCustomer_CustomerId(Long customerId, Pageable pageable);
}
