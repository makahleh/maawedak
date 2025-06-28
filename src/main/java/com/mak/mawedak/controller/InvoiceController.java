package com.mak.mawedak.controller;

import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.dto.InvoiceFilterDTO;
import com.mak.mawedak.service.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:8080")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/list")
    public ResponseEntity<Page<InvoiceDTO>> getInvoices(
            @PageableDefault Pageable pageable,
            @ModelAttribute InvoiceFilterDTO filter
    ) {
        Page<InvoiceDTO> result = invoiceService.getInvoices(filter, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/export-to-fawtara")
    public ResponseEntity<Void> exportToFawtara(@ModelAttribute InvoiceFilterDTO filter) {
        invoiceService.exportToFawtara(filter);
        return ResponseEntity.ok().build();
    }
}
