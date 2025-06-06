package com.mak.mawedak.controller;

import com.mak.mawedak.dto.InsuranceDTO;
import com.mak.mawedak.service.InsuranceService;
import com.mak.mawedak.utils.ContextHolderHelper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurances")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class InsuranceController {


    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/list")
    public ResponseEntity<Page<InsuranceDTO>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Page<InsuranceDTO> patientsPage = insuranceService.getInsurances(
                ContextHolderHelper.getCustomerId(), page, size);
        return new ResponseEntity<>(patientsPage, HttpStatus.OK);
    }
}
