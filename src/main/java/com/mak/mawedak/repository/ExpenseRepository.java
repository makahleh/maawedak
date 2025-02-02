package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Expense;
import com.mak.mawedak.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByCustomer_CustomerIdAndExpenseId(Long customerId, Long id);

    Page<Expense> findAllByCustomer_CustomerId(Long customerId, Pageable pageable);

}
