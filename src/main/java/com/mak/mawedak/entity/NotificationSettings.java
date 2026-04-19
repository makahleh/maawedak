package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long notificationSettingsId;

    @Column(length = 2000)
    private String whatsAppReminderMessageTemplate;

    @Column(length = 2000)
    private String whatsAppAfterSessionFeedbackTemplate;

    @Column(length = 2000)
    private String whatsAppSessionNotAttendedTemplate;

    public NotificationSettings(Long id) {
        this.notificationSettingsId = id;
    }
}
