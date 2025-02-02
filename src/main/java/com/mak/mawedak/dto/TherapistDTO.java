package com.mak.mawedak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapistDTO {
    private Long therapistId;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "\\d{14}", message = "Phone number must be 14 digits")
    private String phoneNumber;

    private String hiringDate;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    private String departmentName;

    @NotNull(message = "Username is required")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
