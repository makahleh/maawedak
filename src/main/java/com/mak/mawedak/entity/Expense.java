package com.mak.mawedak.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    public Expense(Long id) {
        this.expenseId = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ExpenseCategory expenseCategory;

    @Column
    private String details;

    @Column(nullable = false)
    private Double amount;

    @Column
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;
}