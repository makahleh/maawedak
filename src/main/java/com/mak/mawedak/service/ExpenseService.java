package com.mak.mawedak.service;

import com.mak.mawedak.dto.ExpenseDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Expense;
import com.mak.mawedak.mapper.ExpenseMapper;
import com.mak.mawedak.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    // Create expense
    public ExpenseDTO createExpense(Customer customer, ExpenseDTO expenseDto) {
        if (expenseDto.getExpenseId() != null) {
            throw new RuntimeException("Creating Expense should not have an expenseId");
        }
        return saveExpense(customer, expenseDto, null);
    }

    // Update expense
    public ExpenseDTO updateExpense(Customer customer, ExpenseDTO expenseDto) {
        Expense expense = expenseRepository.findById(expenseDto.getExpenseId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return saveExpense(customer, expenseDto, expense);
    }

    private ExpenseDTO saveExpense(Customer customer, ExpenseDTO expenseDto, Expense existingExpense) {
        Expense expense = expenseMapper.toEntity(expenseDto, existingExpense);
        expense.setCustomer(customer);
        expense = expenseRepository.save(expense);
        return expenseMapper.toDTO(expense);
    }

    // Get Page of expenses
    public Page<ExpenseDTO> getExpenses(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("creationDate")));
        Page<Expense> expensesPage = expenseRepository.findAllByCustomer_CustomerId(customerId, pageable);

        return expensesPage.map(expenseMapper::toDTO);
    }

    // Delete expense
    public void deleteExpense(Long customerId, Long expenseId) {
        Expense expense = expenseRepository.findByCustomer_CustomerIdAndExpenseId(customerId, expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }
}
