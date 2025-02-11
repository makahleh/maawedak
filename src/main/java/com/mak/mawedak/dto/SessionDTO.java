package com.mak.mawedak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private Long sessionId;

    private Long patientId;

    private String patientName;

    private Long therapistId;

    private String therapistName;

    private Long paymentMethodId;

    private Long insuranceId;

    private String insuranceName;

    private String startDateTime;

    private String endDateTime;

    private String notes;

    private double paymentAmount;

    private Boolean status = false;

    private Long patientDepartmentId;
}
