package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.ExpenseDTO;
import com.mak.mawedak.entity.Expense;
import com.mak.mawedak.entity.ExpenseCategory;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            return null;
        }
        return new ExpenseDTO(
                expense.getExpenseId(),
                expense.getExpenseCategory().getCategoryId(),
                expense.getExpenseCategory().getName(),
                expense.getDetails(),
                expense.getAmount(),
                expense.getDueDate()
        );
    }

    public Expense toEntity(ExpenseDTO expenseDto, Expense existingExpense) {
        if (expenseDto == null) {
            return null;
        }
        Expense expense = existingExpense != null ? existingExpense : new Expense();
        expense.setExpenseId(expenseDto.getExpenseId());
        expense.setDetails(expenseDto.getDetails());
        expense.setAmount(expenseDto.getAmount());
        expense.setDueDate(expenseDto.getDueDate());
        expense.setExpenseCategory(new ExpenseCategory(expenseDto.getCategoryId()));
        return expense;
    }
}
