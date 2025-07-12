package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionSettingsDTO {
    private Long subscriptionId;
    private String name;
    private Long subscriptionMethodId;
    private Long insuranceId;
    private Long subInsuranceId;
    private double sessionPrice;
    private double packagePrice;
    private double coveragePercentage;
    private LocalDateTime expiryDate;
}
