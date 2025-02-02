package com.mak.mawedak.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

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
    private LocalDate dueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate creationDate;

    @UpdateTimestamp
    private LocalDate updatedDate;
}