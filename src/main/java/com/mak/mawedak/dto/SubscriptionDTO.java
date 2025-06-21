package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private Long subscriptionId;
    private String name;
    private Long subscriptionMethodId;
    private Long insuranceId;
    private String insuranceName;
    private int numberOfTotalSessions;
    private double sessionPrice;
    private double packagePrice;
    private double coveragePercentage;
    private LocalDateTime expiryDate;
    private LocalDateTime createdDate;

    // runtime fields
    private int numberOfUsedSessions;
    private double balance;
}
