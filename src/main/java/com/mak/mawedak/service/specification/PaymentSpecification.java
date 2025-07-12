package com.mak.mawedak.service.specification;

import com.mak.mawedak.dto.InvoiceFilterDTO;
import com.mak.mawedak.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PaymentSpecification {
    public Specification<Payment> buildSpecification(InvoiceFilterDTO filter, Long customerId) {
        return Specification
                .where(hasCustomerId(customerId))
                .and(hasPatientName(filter.getPatientName()))
                .and(hasSubscriptionMethod(filter.getSubscriptionMethodId()))
                .and(hasInsurance(filter.getInsuranceId()))
                .and(hasSubInsurance(filter.getSubInsuranceId()))
                .and(hasMinAmount(filter.getMinAmount()))
                .and(hasMaxAmount(filter.getMaxAmount()))
                .and(hasPaymentMethodId(filter.getPaymentMethodId()))
                .and(hasFromDate(filter.getFromDate()))
                .and(hasToDate(filter.getToDate()))
                .and(hasWasExportedToFawtara(filter.getWasExportedToFawtara()))
                .and(hasNotes(filter.getNotes()));
    }

    private Specification<Payment> hasCustomerId(Long customerId) {
        return (root, query, cb) -> customerId == null ? null :
                cb.equal(root.get("patient").get("customer").get("customerId"), customerId);
    }

    private Specification<Payment> hasPatientName(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("patient").get("name")), "%" + name.toLowerCase() + "%");
    }

    private Specification<Payment> hasSubscriptionMethod(Long methodId) {
        return (root, query, cb) -> methodId == null ? null :
                cb.equal(root.get("subscription").get("subscriptionMethod").get("subscriptionMethodId"), methodId);
    }

    private Specification<Payment> hasInsurance(Long insuranceId) {
        return (root, query, cb) -> insuranceId == null ? null :
                cb.equal(root.get("subscription").get("insurance").get("insuranceId"), insuranceId);
    }

    private Specification<Payment> hasSubInsurance(Long subInsuranceId) {
        return (root, query, cb) -> subInsuranceId == null ? null :
                cb.equal(root.get("subscription").get("subInsurance").get("subInsuranceId"), subInsuranceId);
    }

    private Specification<Payment> hasMinAmount(Double minAmount) {
        return (root, query, cb) -> minAmount == null ? null :
                cb.ge(root.get("amount"), minAmount);
    }

    private Specification<Payment> hasMaxAmount(Double maxAmount) {
        return (root, query, cb) -> maxAmount == null ? null :
                cb.le(root.get("amount"), maxAmount);
    }

    private Specification<Payment> hasPaymentMethodId(Long methodId) {
        return (root, query, cb) -> methodId == null ? null :
                cb.equal(root.get("paymentMethod").get("paymentMethodId"), methodId);
    }

    private Specification<Payment> hasFromDate(LocalDateTime fromDate) {
        return (root, query, cb) -> fromDate == null ? null :
                cb.greaterThanOrEqualTo(root.get("createdDate"), fromDate);
    }

    private Specification<Payment> hasToDate(LocalDateTime toDate) {
        return (root, query, cb) -> toDate == null ? null :
                cb.lessThanOrEqualTo(root.get("createdDate"), toDate);
    }

    private Specification<Payment> hasWasExportedToFawtara(Boolean wasExportedToFawtara) {
        return (root, query, cb) -> wasExportedToFawtara == null ? null :
                cb.equal(root.get("wasExportedToFawtara"), wasExportedToFawtara);
    }

    private Specification<Payment> hasNotes(String notes) {
        return (root, query, cb) -> notes == null ? null :
                cb.like(cb.lower(root.get("notes")), "%" + notes.toLowerCase() + "%");
    }

}
