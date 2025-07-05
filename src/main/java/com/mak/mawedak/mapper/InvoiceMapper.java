package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.entity.Payment;

public class InvoiceMapper {
    public static InvoiceDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        var subscriptionMethod = payment.getSubscription() != null ? payment.getSubscription().getSubscriptionMethod() : null;
        return new InvoiceDTO(
                payment.getPaymentId(),
                payment.getPatient().getName(),
                subscriptionMethod != null ? subscriptionMethod.getSubscriptionMethodId() : null,
                payment.getAmount(),
                payment.getPaymentMethod() != null ? payment.getPaymentMethod().getPaymentMethodId() : null,
                payment.getCreatedDate(),
                payment.getWasExportedToFawtara(),
                payment.getNotes()
        );
    }
}
