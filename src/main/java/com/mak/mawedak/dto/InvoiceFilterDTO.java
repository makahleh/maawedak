package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceFilterDTO {
    private String patientName;
    private Long subscriptionMethodId;
    private Long insuranceId;
    private Double minAmount;
    private Double maxAmount;
    private Long paymentMethodId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String notes;
    private Boolean wasExportedToFawtara;
}
