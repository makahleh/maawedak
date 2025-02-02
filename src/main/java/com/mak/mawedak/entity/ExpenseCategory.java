package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {

    public ExpenseCategory(Long id) {
        this.categoryId = id;
    }

    @Id
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;
}