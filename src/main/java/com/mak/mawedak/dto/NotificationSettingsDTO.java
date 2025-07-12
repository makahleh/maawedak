package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettingsDTO {
    private Long notificationSettingsId;
    private String whatsAppReminderMessageTemplate;
    private String whatsAppAfterSessionFeedbackTemplate;
    private String whatsAppSessionNotAttendedTemplate;
}
