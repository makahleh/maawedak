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
        dto.setSubInsuranceId(
                subscription.getSubInsurance() != null ? subscription.getSubInsurance().getSubInsuranceId() : null
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
        dto.setTotalPayedAmount(0.0);
        dto.setTotalDueAmount(0.0);
        if (subscription.getPayments() == null) {
            return dto;
        }
        double totalPayments = subscription.getPayments().stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        dto.setTotalPayedAmount(totalPayments);
        Long methodId = dto.getSubscriptionMethodId();

        if (methodId != null) {
            if (methodId == 1L) {
                dto.setTotalDueAmount(numberOfUsedSessions * dto.getSessionPrice());
            } else if (methodId == 2L) {
                double insuranceSessionPrice = subscription.getInsurance() != null ? subscription.getInsurance().getSessionPrice() : 0.0;
                dto.setTotalDueAmount(numberOfUsedSessions * (dto.getCoveragePercentage() / 100) * insuranceSessionPrice);
            } else if (methodId == 3L) {
                dto.setTotalDueAmount(subscription.getPackagePrice());
            }
        }

        dto.setBalance(dto.getTotalPayedAmount() - dto.getTotalDueAmount());

        return dto;
    }

    public static Subscription toEntity(SubscriptionDTO dto, Patient patient, Subscription existingSubscription) {
        if (dto == null) return null;

        Subscription subscription = existingSubscription != null ? existingSubscription : new Subscription();
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

        if (dto.getSubInsuranceId() != null) {
            subscription.setSubInsurance(new SubInsurance(dto.getSubInsuranceId()));
        }

        subscription.setNumberOfTotalSessions(dto.getNumberOfTotalSessions());
        subscription.setSessionPrice(dto.getSessionPrice());
        subscription.setPackagePrice(dto.getPackagePrice());
        subscription.setCoveragePercentage(dto.getCoveragePercentage());
        subscription.setExpiryDate(dto.getExpiryDate());
        return subscription;
    }
}