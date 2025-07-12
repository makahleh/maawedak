package com.mak.mawedak.service;

import com.mak.mawedak.dto.ExportToFawtaraRequestDTO;
import com.mak.mawedak.dto.InvoiceDTO;
import com.mak.mawedak.dto.InvoiceFilterDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Patient;
import com.mak.mawedak.entity.Payment;
import com.mak.mawedak.mapper.InvoiceMapper;
import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.repository.PaymentRepository;
import com.mak.mawedak.service.specification.PaymentSpecification;
import com.mak.mawedak.utils.UblXmlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("jofawtara.url")
    private String jofawtaraURL;

    public Page<InvoiceDTO> getInvoices(InvoiceFilterDTO filter, Pageable pageable, Long customerId) {
        PaymentSpecification paymentSpecification = new PaymentSpecification();
        Specification<Payment> spec = paymentSpecification.buildSpecification(filter, customerId);
        return paymentRepository.findAll(spec, pageable)
                .map(InvoiceMapper::toDTO);
    }

    public void exportToFawtara(ExportToFawtaraRequestDTO fawtaraRequestDTO, Long customerId) {
        List<Payment> payments = paymentRepository.findAllByPaymentIdIn(fawtaraRequestDTO.paymentIds());


        paymentRepository.updateExportedToFawtara(fawtaraRequestDTO.paymentIds());
    }


    public void sendInvoice(Patient patient, Customer customer) {
    }

    private void sendInvoice(Invoice invoice) {
        String xml = UblXmlBuilder.buildInvoiceXml(invoice);
        String encoded = Base64.getEncoder().encodeToString(xml.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secret_key", "your_secret_key");
        headers.set("client_key", "your_client_key");

        Map<String, String> body = Map.of("invoice", encoded);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(jofawtaraURL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Invoice sent successfully.");
        } else {
            System.out.println("Failed to send invoice. Status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
        }
    }
}

