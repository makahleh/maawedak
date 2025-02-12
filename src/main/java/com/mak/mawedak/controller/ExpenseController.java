package com.mak.mawedak.controller;

import com.mak.mawedak.dto.ExpenseDTO;
import com.mak.mawedak.service.ExpenseService;
import com.mak.mawedak.utils.ContextHolderHelper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Create expense
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO savedExpense = expenseService.createExpense(ContextHolderHelper.getCustomerId(), expenseDTO);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // Update expense
    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(ContextHolderHelper.getCustomerId(), expenseDTO);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // Get a list of expenses
    @GetMapping
    public ResponseEntity<Page<ExpenseDTO>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ExpenseDTO> expenses = expenseService.getExpenses(ContextHolderHelper.getCustomerId(), page, size);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Delete expense
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(ContextHolderHelper.getCustomerId(), expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

