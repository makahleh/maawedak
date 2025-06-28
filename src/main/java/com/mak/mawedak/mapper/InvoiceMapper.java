package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.entity.Payment;

public class InvoiceMapper {
    public static InvoiceDTO toDTO(Payment payment) {
        return new InvoiceDTO(
                payment.getPatient().getName(),
                payment.getSubscription() != null ? payment.getSubscription().getSubscriptionId() : null,
                payment.getAmount(),
                payment.getPaymentMethod() != null ? payment.getPaymentMethod().getPaymentMethodId() : null,
                payment.getCreatedDate(),
                payment.getNotes()
        );
    }
}
