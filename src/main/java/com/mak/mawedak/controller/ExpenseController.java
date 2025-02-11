package com.mak.mawedak.controller;

import com.mak.mawedak.dto.ExpenseDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.service.ExpenseService;
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

    // TODO: Mock customer repository
    @Autowired
    private CustomerRepository customerRepository;

    private Customer getMockCustomer() {
        return customerRepository.findById(1L).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Create expense
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        Customer customer = getMockCustomer();
        ExpenseDTO savedExpense = expenseService.createExpense(customer, expenseDTO);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // Update expense
    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseDTO expenseDTO) {
        Customer customer = getMockCustomer();
        expenseDTO.setExpenseId(expenseId);
        ExpenseDTO updatedExpense = expenseService.updateExpense(customer, expenseDTO);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // Get a list of expenses
    @GetMapping
    public ResponseEntity<Page<ExpenseDTO>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Customer customer = getMockCustomer();
        Page<ExpenseDTO> expenses = expenseService.getExpenses(customer.getCustomerId(), page, size);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Delete expense
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        Customer customer = getMockCustomer();
        expenseService.deleteExpense(customer.getCustomerId(), expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

