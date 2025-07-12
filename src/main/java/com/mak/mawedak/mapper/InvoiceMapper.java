package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.model.InvoiceItem;

import java.util.List;

public class InvoiceMapper {
    public static InvoiceDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        var subscriptionMethod = payment.getSubscription() != null ? payment.getSubscription().getSubscriptionMethod() : null;
        var insuranceName = payment.getSubscription() != null && payment.getSubscription().getInsurance() != null
                ? payment.getSubscription().getInsurance().getName() : null;
        var subInsuranceName = payment.getSubscription() != null && payment.getSubscription().getSubInsurance() != null
                ? payment.getSubscription().getSubInsurance().getName() : null;

        return new InvoiceDTO(
                payment.getPaymentId(),
                payment.getPatient().getName(),
                insuranceName,
                subInsuranceName,
                subscriptionMethod != null ? subscriptionMethod.getSubscriptionMethodId() : null,
                payment.getAmount(),
                payment.getPaymentMethod() != null ? payment.getPaymentMethod().getPaymentMethodId() : null,
                payment.getCreatedDate(),
                payment.getWasExportedToFawtara(),
                payment.getNotes()
        );
    }
//
//    public Invoice paymentToInvoice(Payment payment) {
//        if (payment == null) {
//            return null;
//        }
//        var patient = payment.getPatient();
//        var customer = patient.getCustomer();
//        var insurance = payment.getSubscription() != null ?
//                payment.getSubscription().getInsurance() != null ?
//                        payment.getSubscription().getInsurance() : null
//                : null;
//        var clientTaxId = insurance != null ? insurance.getTaxNumber() : patient.getNationalNumber();
//        var clientName = insurance != null ? insurance.getName() : patient.getName();
//
//        var invoiceItem = null;
//        List<InvoiceItem> invoiceItems = null;
//
//        return new Invoice(
//                customer.getCustomerPersonalInfo().getTaxNumber(),
//                customer.getCustomerPersonalInfo().getName(),
//                "", // TODO: to be fixed
//                clientTaxId,
//                clientName,
//                payment.getInvoiceNumber(),
//                payment.getPaymentId(),
//                payment.getCreatedDate().toLocalDate(),
//                payment.getAmount(),
//                null // Assuming invoiceItem is not needed here
//        );
//    }
}
