package com.mak.mawedak.repository;

import com.mak.mawedak.entity.Expense;
import com.mak.mawedak.repository.projection.SumByIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByCustomer_CustomerIdAndExpenseId(Long customerId, Long id);

    Page<Expense> findAllByCustomer_CustomerId(Long customerId, Pageable pageable);

    // For report
    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
                WHERE e.customer.id = :customerId
                AND e.creationDate BETWEEN :from AND :to
            """)
    BigDecimal getTotalExpenses(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            SELECT ec.id AS id, COALESCE(SUM(e.amount), 0) AS total
            FROM Expense e
            JOIN e.expenseCategory ec
            WHERE e.customer.id = :customerId
              AND e.creationDate BETWEEN :from AND :to
            GROUP BY ec.id
            """)
    List<SumByIdProjection> sumExpensesByCategory(
            @Param("customerId") Long customerId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );


}
