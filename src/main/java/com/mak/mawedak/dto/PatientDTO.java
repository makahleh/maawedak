package com.mak.mawedak.dto;

import com.mak.mawedak.entity.TreatmentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long patientId;

    @NotNull(message = "name is required")
    private String name;

    private Integer age;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "\\d{14}", message = "Phone number must be 14 digits")
    private String phoneNumber;

    private Long therapistId;

    private String therapistName;

    @NotNull(message = "department Id is required")
    private Long departmentId;

    private String departmentName;

    @NotNull(message = "paymentMethodId is required")
    private Long paymentMethodId;

    private List<TreatmentMethod> treatmentMethods;

    private Long insuranceId;

    private String insuranceName;

    private double sessionPrice;

    @NotNull(message = "Total number of sessions is required")
    private int numberOfTotalSessions;

    private String notes;

    private String createdDate;

    private int numOfTookSessions;

    private LocalDateTime expiryDate;

    private double balance;

    private List<SessionDTO> sessions;
}

