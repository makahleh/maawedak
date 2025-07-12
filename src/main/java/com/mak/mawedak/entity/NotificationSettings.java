package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationSettingsId;

    @Column
    private String whatsAppReminderMessageTemplate;

    @Column
    private String whatsAppAfterSessionFeedbackTemplate;

    @Column
    private String whatsAppSessionNotAttendedTemplate;

    public NotificationSettings(Long id) {
        this.notificationSettingsId = id;
    }
}
