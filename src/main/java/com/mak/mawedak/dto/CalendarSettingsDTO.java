package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarSettingsDTO {
    private Long calendarSettingsId;
    private LocalTime calendarStartTime;
    private LocalTime calendarEndTime;
    private Integer timeSlotDurationInMinutes;
    private String activeDays;
}
