package com.mak.mawedak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private String customerTaxId;
    private String customerName;
    private String customerIncomeSourceSequence;
    private String clientTaxId;
    private String clientName;
    private String invoiceNumber;
    private UUID invoiceId;
    private LocalDate issueDate;
    private double invoiceTotal;
    private List<InvoiceItem> invoiceItem;
}
