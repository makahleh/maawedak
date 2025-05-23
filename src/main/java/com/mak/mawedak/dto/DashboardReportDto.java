package com.mak.mawedak.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardReportDto(
        int completedSessions,
        int newPatients,
        BigDecimal totalRevenue,
        BigDecimal totalExpenses,
        List<ChartDataDTO> patientsByDepartment,
        List<ChartDataDTO> expensesByCategory,
        List<ChartDataDTO> patientsByPaymentMethod,
        List<ChartDataDTO> sessionsByTherapist
) {
}

