package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private String patientName;
    private Long subscriptionMethodId;
    private double amount;
    private Long paymentMethodId;
    private LocalDateTime createdDate;
    private String notes;
}
