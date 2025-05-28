package com.mak.mawedak.controller;

import com.mak.mawedak.dto.DashboardReportDto;
import com.mak.mawedak.service.DashboardReportService;
import com.mak.mawedak.utils.ContextHolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:8080")
public class DashboardReportController {

    @Autowired
    private DashboardReportService dashboardReportService;

    @GetMapping("/all")
    public ResponseEntity<DashboardReportDto> getAllReportsData(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        DashboardReportDto data = dashboardReportService.getDashboardReportsData(
                ContextHolderHelper.getCustomerId(),
                from,
                to
        );
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
