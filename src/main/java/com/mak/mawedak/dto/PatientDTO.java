package com.mak.mawedak.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long patientId;

    @NotNull(message = "First name is required")
    private String name;

    private Long therapistId;

    private String therapistName;

    @NotNull(message = "department Id is required")
    private Long departmentId;

    private String departmentName;

    @NotNull(message = "paymentMethodId is required")
    private Long paymentMethodId;

    private Long insuranceId;

    private String insuranceName;

    @NotNull(message = "Session price is required")
    private double sessionPrice;

    @NotNull(message = "Total number of sessions is required")
    private int numberOfTotalSessions;

    private String notes;

    private String createdDate;

    private int numOfTookSessions;

    private double balance;

    private List<SessionDTO> sessions;
}

