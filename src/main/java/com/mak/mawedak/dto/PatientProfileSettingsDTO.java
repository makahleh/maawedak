package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileSettingsDTO {
    private Long patientProfileSettingsId;
    private SubscriptionSettingsDTO defaultSubscription;
    private List<IdNameDTO> treatmentMethods;
}
