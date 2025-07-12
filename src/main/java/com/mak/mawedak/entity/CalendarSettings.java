package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarSettingsId;

    @Column
    private LocalTime calendarStartTime;

    @Column
    private LocalTime calendarEndTime;

    @Column
    private Integer timeSlotDurationInMinutes;

    @Column
    private String activeDays;
}
