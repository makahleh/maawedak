package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private Long expenseId;
    private Long categoryId;
    private String categoryName;
    private String details;
    private Double amount;
    private LocalDate dueDate;
}
