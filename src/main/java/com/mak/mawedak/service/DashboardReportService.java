package com.mak.mawedak.service;

import com.mak.mawedak.dto.ChartDataDTO;
import com.mak.mawedak.dto.DashboardReportDto;
import com.mak.mawedak.dto.ValuePercentageByIdDTO;
import com.mak.mawedak.repository.ExpenseRepository;
import com.mak.mawedak.repository.PatientRepository;
import com.mak.mawedak.repository.SessionRepository;
import com.mak.mawedak.repository.projection.CountByIdProjection;
import com.mak.mawedak.repository.projection.CountByNameProjection;
import com.mak.mawedak.repository.projection.SumByIdProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardReportService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public DashboardReportDto getDashboardReport(Long customerId, LocalDateTime from, LocalDateTime to) {
        int completedSessions = sessionRepository.countCompletedSessions(customerId, from, to);
        int newPatients = patientRepository.countNewPatients(customerId, from, to);
        BigDecimal totalRevenue = sessionRepository.calculateTotalRevenue(customerId, from, to);
        BigDecimal totalExpenses = expenseRepository.getTotalExpenses(customerId, from, to);

        List<ValuePercentageByIdDTO> patientsByDepartmentWithPercentage = getPatientsByDepartmentWithPercentage(customerId, from, to);
        List<ValuePercentageByIdDTO> expensesByCategoryWithPercentage = getExpensesByCategoryWithPercentage(customerId, from, to);
        List<ValuePercentageByIdDTO> newPatientsByPaymentMethodWithPercentage = getNewPatientsByPaymentMethodWithPercentage(customerId, from, to);
        List<ValuePercentageByIdDTO> sessionsByTherapist = getSessionsByTherapist(customerId, from, to);

        return new DashboardReportDto(
                completedSessions,
                newPatients,
                totalRevenue,
                totalExpenses,
                mapToChartDataWithPercentage(patientsByDepartmentWithPercentage),
                mapToChartDataWithPercentage(expensesByCategoryWithPercentage),
                mapToChartDataWithPercentage(newPatientsByPaymentMethodWithPercentage),
                mapToChartDataWithPercentage(sessionsByTherapist)
        );
    }

    public List<ValuePercentageByIdDTO> getPatientsByDepartmentWithPercentage(
            Long customerId, LocalDateTime from, LocalDateTime to
    ) {
        // get grouped count by department
        List<CountByIdProjection> patientsByDepartmentIds = patientRepository.countNewPatientsByDepartmentId(customerId, from, to);
        int totalPatients = patientsByDepartmentIds.stream().mapToInt(CountByIdProjection::getCount).sum();

        // map each with percentage
        return patientsByDepartmentIds.stream()
                .map(e -> new ValuePercentageByIdDTO(
                        e.getId(),
                        BigDecimal.valueOf(e.getCount()),
                        totalPatients == 0
                                ? 0.0
                                : (e.getCount() * 100.0) / totalPatients
                ))
                .collect(Collectors.toList());
    }


    public List<ValuePercentageByIdDTO> getExpensesByCategoryWithPercentage(
            Long customerId, LocalDateTime from, LocalDateTime to
    ) {
        List<SumByIdProjection> expensesByCategories = expenseRepository.sumExpensesByCategory(customerId, from, to);
        BigDecimal totalExpenses = expensesByCategories.stream()
                .map(SumByIdProjection::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return expensesByCategories.stream()
                .map(e -> new ValuePercentageByIdDTO(
                        e.getId(),
                        e.getTotal(),
                        (totalExpenses == null || totalExpenses.compareTo(BigDecimal.ZERO) == 0)
                                ? 0.0
                                : e.getTotal()
                                .multiply(BigDecimal.valueOf(100))
                                .divide(totalExpenses, 2, RoundingMode.HALF_UP)
                                .doubleValue()
                ))
                .collect(Collectors.toList());
    }

    public List<ValuePercentageByIdDTO> getNewPatientsByPaymentMethodWithPercentage(
            Long customerId, LocalDateTime from, LocalDateTime to
    ) {
        List<CountByIdProjection> patientsByPaymentMethod = patientRepository.countNewPatientsByPaymentMethod(customerId, from, to);
        int totalPatients = patientsByPaymentMethod.stream().mapToInt(CountByIdProjection::getCount).sum();

        return patientsByPaymentMethod.stream()
                .map(e -> new ValuePercentageByIdDTO(
                        e.getId(),
                        BigDecimal.valueOf(e.getCount()),
                        totalPatients == 0
                                ? 0.0
                                : (e.getCount() * 100.0) / totalPatients
                ))
                .collect(Collectors.toList());
    }

    public List<ValuePercentageByIdDTO> getSessionsByTherapist(Long customerId, LocalDateTime from, LocalDateTime to) {
        List<CountByNameProjection> data = sessionRepository.countSessionsByTherapist(customerId, from, to);
        int total = data.stream().mapToInt(CountByNameProjection::getCount).sum();

        return data.stream()
                .map(d -> new ValuePercentageByIdDTO(
                        d.getName(),
                        BigDecimal.valueOf(d.getCount()),
                        total == 0 ? 0.0 :
                                BigDecimal.valueOf(d.getCount())
                                        .multiply(BigDecimal.valueOf(100))
                                        .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
                                        .doubleValue()
                ))
                .collect(Collectors.toList());
    }


    List<ChartDataDTO> mapToChartDataWithPercentage(List<ValuePercentageByIdDTO> data) {
        return data.stream()
                .map(d -> {
                    String label = d.id().toString();
                    String yValue = d.value().intValue() + " (" + d.percentage() + "%)";
                    return new ChartDataDTO(label, yValue);
                })
                .collect(Collectors.toList());
    }

}

