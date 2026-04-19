package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {

    public ExpenseCategory(Long id) {
        this.categoryId = id;
    }

    @Id
    @EqualsAndHashCode.Include
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;
}