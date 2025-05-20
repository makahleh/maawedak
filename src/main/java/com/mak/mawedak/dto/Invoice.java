package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {
    private String invoiceId;
    private String issueDate;
    private String supplierName;
    private String customerName;
    private String itemName;
    private double itemPrice;
}

