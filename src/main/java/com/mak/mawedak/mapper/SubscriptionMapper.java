package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.SubscriptionDTO;
import com.mak.mawedak.entity.*;

public class SubscriptionMapper {

    public static SubscriptionDTO toDTO(Subscription subscription) {
        if (subscription == null) return null;

        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setSubscriptionId(subscription.getSubscriptionId());
        dto.setName(subscription.getName());
        dto.setSubscriptionMethodId(
                subscription.getSubscriptionMethod() != null ? subscription.getSubscriptionMethod().getSubscriptionMethodId() : null
        );
        if (subscription.getInsurance() != null) {
            dto.setInsuranceId(subscription.getInsurance().getInsuranceId());
            dto.setInsuranceName(subscription.getInsurance().getName());
        }
        dto.setInsuranceId(
                subscription.getInsurance() != null ? subscription.getInsurance().getInsuranceId() : null
        );
        dto.setNumberOfTotalSessions(subscription.getNumberOfTotalSessions());
        dto.setSessionPrice(subscription.getSessionPrice());
        dto.setPackagePrice(subscription.getPackagePrice());
        dto.setCoveragePercentage(subscription.getCoveragePercentage());
        dto.setExpiryDate(subscription.getExpiryDate());
        dto.setCreatedDate(subscription.getCreatedDate());
        // runtime fields
        var numberOfUsedSessions = subscription.getSessions() == null ? 0 :
                (int) subscription.getSessions().stream()
                        .filter(s -> s.getSessionStatus() != null && s.getSessionStatus().getSessionStatusId() == 2)
                        .count();
        dto.setNumberOfUsedSessions(numberOfUsedSessions);

        dto.setBalance(0.0);
        if (subscription.getPayments() == null) {
            return dto;
        }
        double totalPayments = subscription.getPayments().stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        Long methodId = dto.getSubscriptionMethodId();

        if (methodId != null) {
            if (methodId == 1L) {
                dto.setBalance(totalPayments - (numberOfUsedSessions * dto.getSessionPrice()));
            } else if (methodId == 2L) {
                double insuranceSessionPrice = subscription.getInsurance() != null ? subscription.getInsurance().getSessionPrice() : 0.0;
                dto.setBalance(totalPayments - (numberOfUsedSessions * dto.getCoveragePercentage() * insuranceSessionPrice));
            } else if (methodId == 3L) {
                dto.setBalance(totalPayments);
            }
        }

        return dto;
    }

    public static Subscription toEntity(SubscriptionDTO dto, Patient patient) {
        if (dto == null) return null;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(dto.getSubscriptionId());
        subscription.setName(dto.getName());

        if (patient != null) {
            subscription.setPatient(patient);
        }

        if (dto.getSubscriptionMethodId() != null) {
            subscription.setSubscriptionMethod(new SubscriptionMethod(dto.getSubscriptionMethodId()));
        }

        if (dto.getInsuranceId() != null) {
            subscription.setInsurance(new Insurance(dto.getInsuranceId()));
        }

        subscription.setNumberOfTotalSessions(dto.getNumberOfTotalSessions());
        subscription.setSessionPrice(dto.getSessionPrice());
        subscription.setPackagePrice(dto.getPackagePrice());
        subscription.setCoveragePercentage(dto.getCoveragePercentage());
        subscription.setExpiryDate(dto.getExpiryDate());
        return subscription;
    }
}