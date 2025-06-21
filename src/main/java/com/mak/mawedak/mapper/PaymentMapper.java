package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.PaymentDTO;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.entity.PaymentMethod;
import com.mak.mawedak.entity.Subscription;


public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        if (payment.getPaymentMethod() != null) {
            dto.setPaymentMethodId(payment.getPaymentMethod().getPaymentMethodId());
        }
        if (payment.getSubscription() != null) {
            dto.setActiveSubscriptionId(payment.getSubscription().getSubscriptionId());
        }
        dto.setNotes(payment.getNotes());
        dto.setCreatedDate(payment.getCreatedDate());
        return dto;
    }

    public static Payment toEntity(PaymentDTO dto, Patient patient, Long subscriptionId) {
        if (dto == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());

        if (patient != null) {
            payment.setPatient(patient);
        }

        if (dto.getActiveSubscriptionId() != null) {
            payment.setSubscription(new Subscription(dto.getActiveSubscriptionId()));
        }
        if (subscriptionId != null) {
            payment.setSubscription(new Subscription(subscriptionId));
        }
        payment.setAmount(dto.getAmount());
        if (dto.getPaymentMethodId() != null) {
            payment.setPaymentMethod(new PaymentMethod(dto.getPaymentMethodId()));
        }
        payment.setNotes(dto.getNotes());
        payment.setCreatedDate(dto.getCreatedDate());
        return payment;
    }
}