package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private Long sessionId;

    private IdNameDTO patient;

    private IdNameDTO therapist;

    private SubscriptionDTO activeSubscription;

    private PaymentDTO payment;

    private String startDateTime;

    private String endDateTime;

    private String notes;

    private Long statusId;
}
