package com.mak.mawedak.service;

import com.mak.mawedak.dto.ExportToFawtaraRequestDTO;
import com.mak.mawedak.dto.InvoiceByPatient;
import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.dto.InvoiceFilterDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.CustomerPersonalInfo;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.mapper.InvoiceMapper;
import com.mak.mawedak.model.InvoiceItem;
import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.repository.CustomerPersonalInfoRepository;
import com.mak.mawedak.repository.CustomerRepository;
import com.mak.mawedak.repository.PatientRepository;
import com.mak.mawedak.repository.PaymentRepository;
import com.mak.mawedak.service.specification.PaymentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomerPersonalInfoRepository customerPersonalInfoRepository;

    @Autowired
    private FawtaraServiceXML fawtaraService;

    public Page<InvoiceDTO> getInvoices(InvoiceFilterDTO filter, Pageable pageable, Long customerId) {
        PaymentSpecification paymentSpecification = new PaymentSpecification();
        Specification<Payment> spec = paymentSpecification.buildSpecification(filter, customerId);
        return paymentRepository.findAll(spec, pageable)
                .map(InvoiceMapper::toDTO);
    }

    public void exportToFawtara(ExportToFawtaraRequestDTO fawtaraRequestDTO, Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        exportPaymentsToFawtara(fawtaraRequestDTO, customer);
    }

    public boolean exportPaymentsToFawtara(ExportToFawtaraRequestDTO fawtaraRequestDTO, Customer customer) {

        boolean allSuccess = true;

        CustomerPersonalInfo customerPersonalInfo = customer.getCustomerPersonalInfo();

        var invoicesByPatient = fawtaraRequestDTO.patientInvoices();

        Long invoiceSequence = customerPersonalInfo.getIncomeTaxInvoiceSequence();
        for (InvoiceByPatient invoiceByPatient : invoicesByPatient) {
            Patient patient = patientRepository.findById(invoiceByPatient.patientId()).orElseThrow();

            Invoice invoice = new Invoice();
            invoice.setCustomerTaxId(customerPersonalInfo.getTaxNumber());
            invoice.setCustomerName(customerPersonalInfo.getName());
            invoice.setCustomerIncomeSourceSequenceNumber(customerPersonalInfo.getIncomeSourceSequenceNumber());

            invoice.setClientTaxId(patient.getNationalNumber() != null ? patient.getNationalNumber() : "");
            invoice.setClientName(patient.getName());
            String phoneNumber = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "";
            if (phoneNumber.length() > 9) {
                phoneNumber = phoneNumber.substring(phoneNumber.length() - 9);
            }
            invoice.setClientPhoneNumber(phoneNumber);

            invoice.setInvoiceNumber("EIN" + String.format("%05d", invoiceSequence));
            invoice.setInvoiceUUID(UUID.randomUUID());
            invoice.setInvoiceIncrementalId(invoiceSequence.intValue());
            invoice.setIssueDate(LocalDate.now());

            List<InvoiceItem> items = new ArrayList<>(1);
            var item = new InvoiceItem();
            item.setQuantity(invoiceByPatient.quantity());
            item.setUnitPrice(invoiceByPatient.pricePerSession());
            item.setAmount(invoiceByPatient.total());
            items.add(item);

            invoice.setInvoiceItems(items);
            invoice.setInvoiceTotal(items.stream().mapToDouble(InvoiceItem::getAmount).sum());

            if (fawtaraService.sendInvoicesToFawtara(invoice, customer.getCustomerPersonalInfo().getSecretKey(),
                    customer.getCustomerPersonalInfo().getClientKey())) {
                // TODO: update IncomeTaxInvoiceSequence on DB
                paymentRepository.updateExportedToFawtara(invoiceByPatient.paymentIds());
                customerPersonalInfo.setIncomeTaxInvoiceSequence(invoiceSequence++);
                customerPersonalInfoRepository.save(customerPersonalInfo);
            } else {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

}

    

    

        

            

            

            
                        

            


