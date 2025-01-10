package com.mak.mawedak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private Long sessionId;

    private Long patientId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String patientName;

    private Long therapistId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String therapistName;

    private String sessionDateTime;

    private String notes;

    private double paymentAmount;
}
