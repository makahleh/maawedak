package com.mak.mawedak.controller;

import com.mak.mawedak.dto.ExpenseDTO;
import com.mak.mawedak.dto.SystemSettingsDTO;
import com.mak.mawedak.service.CustomerService;
import com.mak.mawedak.utils.ContextHolderHelper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PutMapping("/system-settings")
    public ResponseEntity<SystemSettingsDTO> updateSystemSettings(@RequestBody SystemSettingsDTO systemSettingsDTO) {
        SystemSettingsDTO updated = customerService.updateSystemSettings(ContextHolderHelper.getCustomerId(), systemSettingsDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
