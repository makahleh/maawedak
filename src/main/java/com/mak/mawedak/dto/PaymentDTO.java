package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private double amount;
    private Long paymentMethodId;
    private Long activeSubscriptionId;
    private String notes;
    private LocalDateTime createdDate;
}
