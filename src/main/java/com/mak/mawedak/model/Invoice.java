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
    private String customerIncomeSourceSequenceNumber;
    private String clientTaxId;
    private String clientName;
    private String clientPhoneNumber;
    private String invoiceNumber; // "EIN" + customer.incomeTaxInvoiceSequence.format to be 5 digits, e.g.
    // EIN00001
    private UUID invoiceUUID; // a unique identifier for the invoice, can be generated using UUID.randomUUID()
    private int invoiceIncrementalId; // should be customer.incomeTaxInvoiceSequence
    private LocalDate issueDate; // current date (formatted yyyy-mm-dd)
    private double invoiceTotal;
    private List<InvoiceItem> invoiceItems;
}
