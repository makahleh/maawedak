package com.mak.mawedak.service;

import com.mak.mawedak.dto.ExportToFawtaraRequestDTO;
import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.dto.InvoiceFilterDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.CustomerPersonalInfo;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.mapper.InvoiceMapper;
import com.mak.mawedak.model.InvoiceItem;
import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.repository.PaymentRepository;
import com.mak.mawedak.service.specification.PaymentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FawtaraService fawtaraService;

    public Page<InvoiceDTO> getInvoices(InvoiceFilterDTO filter, Pageable pageable, Long customerId) {
        PaymentSpecification paymentSpecification = new PaymentSpecification();
        Specification<Payment> spec = paymentSpecification.buildSpecification(filter, customerId);
        return paymentRepository.findAll(spec, pageable)
                .map(InvoiceMapper::toDTO);
    }

    public void exportToFawtara(ExportToFawtaraRequestDTO fawtaraRequestDTO, Long customerId) {
        List<Payment> payments = paymentRepository.findAllByPaymentIdIn(fawtaraRequestDTO.paymentIds());
        List<Payment> filteredPayments = filterPaymentsForExport(payments);

        if (filteredPayments.isEmpty()) {
            return;
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        boolean success = exportPaymentsToFawtara(filteredPayments, customer);

        if (success) {
            customerRepository.save(customer);
            paymentRepository.updateExportedToFawtara(filteredPayments.stream().map(Payment::getPaymentId).toList());
        }
    }

    private List<Payment> filterPaymentsForExport(List<Payment> payments) {
        return payments.stream()
                .filter(payment -> !payment.getWasExportedToFawtara()
                        && payment.getSubscription().getSubscriptionMethod().getSubscriptionMethodId() == 1)
                .toList();
    }

    public boolean exportPaymentsToFawtara(List<Payment> payments, Customer customer) {
        Map<Patient, List<Payment>> invoicByPatient = payments.stream()
                .collect(Collectors.groupingBy(Payment::getPatient));

        boolean allSuccess = true;
        CustomerPersonalInfo customerPersonalInfo = customer.getCustomerPersonalInfo();

        for (Map.Entry<Patient, List<Payment>> entry : invoicByPatient.entrySet()) {
            Patient patient = entry.getKey();
            List<Payment> patientPayments = entry.getValue();

            Invoice invoice = new Invoice();
            invoice.setCustomerTaxId(customerPersonalInfo.getTaxNumber());
            invoice.setCustomerName(customerPersonalInfo.getName());
            invoice.setCustomerIncomeSourceSequenceNumber(customerPersonalInfo.getIncomeSourceSequenceNumber());

            invoice.setClientTaxId(patient.getNationalNumber() != null ? patient.getNationalNumber() : "");
            invoice.setClientName(patient.getName());
            invoice.setClientPhoneNumber(patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "");

            Long invoiceSequence = customerPersonalInfo.getIncomeTaxInvoiceSequence();
            invoice.setInvoiceNumber("EIN" + String.format("%05d", invoiceSequence));
            invoice.setInvoiceUUID(UUID.randomUUID());
            invoice.setInvoiceIncrementalId(invoiceSequence.intValue());
            invoice.setIssueDate(LocalDate.now());

            List<InvoiceItem> items = patientPayments.stream().map(p -> {
                InvoiceItem item = new InvoiceItem();
                double quantity = 1.0;
                if (p.getSubscription() != null && p.getSubscription().getSubscriptionMethod() != null
                        && p.getSubscription().getSubscriptionMethod().getSubscriptionMethodId() == 2) {
                    quantity = p.getSubscription().getNumberOfTotalSessions();
                }
                item.setQuantity(quantity);
                item.setUnitPrice(p.getAmount() / quantity);
                item.setAmount(p.getAmount());
                return item;
            }).collect(Collectors.toList());

            invoice.setInvoiceItems(items);
            invoice.setInvoiceTotal(patientPayments.stream().mapToDouble(Payment::getAmount).sum());

            if (fawtaraService.sendInvoicesToFawtara(invoice, customer.getCustomerPersonalInfo().getSecretKey(),
                    customer.getCustomerPersonalInfo().getClientKey())) {
                // TODO: update IncomeTaxInvoiceSequence on DB
                // TODO: update payment status to exportedToFawtara on DB
                customerPersonalInfo.setIncomeTaxInvoiceSequence(invoiceSequence + 1);
            } else {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

}

    

    

        

            

            

            
                        

            


