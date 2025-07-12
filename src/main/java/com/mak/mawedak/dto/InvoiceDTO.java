package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long paymentId;
    private String patientName;
    private String insuranceName;
    private String subInsuranceName;
    private Long subscriptionMethodId;
    private double amount;
    private Long paymentMethodId;
    private LocalDateTime createdDate;
    private Boolean wasExportedToFawtara;
    private String notes;
}
