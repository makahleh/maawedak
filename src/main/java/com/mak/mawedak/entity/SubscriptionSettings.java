package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionSettingsId;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_method_id")
    private SubscriptionMethod subscriptionMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_insurance_id")
    private SubInsurance subInsurance;

    private int numberOfTotalSessions;

    private double sessionPrice;

    private double packagePrice;

    private double coveragePercentage;

    @Column
    private LocalDateTime expiryDate;
}
