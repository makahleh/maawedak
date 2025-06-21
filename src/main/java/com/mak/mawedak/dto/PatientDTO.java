package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long patientId;

    private String name;

    private Integer age;

    private String nationalNumber;

    private String phoneNumber;

    private IdNameDTO therapist;

    private Long departmentId;

    private List<Long> treatmentMethods;

    private String notes;

    private String createdDate;

    // subscriptions
    private List<SubscriptionDTO> subscriptions = new ArrayList<>();

    // payments
    private List<PaymentDTO> payments = new ArrayList<>();

    // sessions history
    private List<SessionDTO> sessions = new ArrayList<>();
}

