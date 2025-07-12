package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemSettingsDTO {
    private CustomerPersonalInfoDTO customerPersonalInfo;
    private PatientProfileSettingsDTO patientProfileSettings;
    private CalendarSettingsDTO calendarSettings;
    private List<InsuranceDTO> insuranceSettings;
    private NotificationSettingsDTO notificationSettings;
}
