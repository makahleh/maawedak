package com.mak.mawedak.service;

import com.mak.mawedak.dto.Invoice;
import com.mak.mawedak.utils.UblXmlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final UblXmlBuilder xmlBuilder;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String fawtaraURL = "https://backend.jofotara.gov.jo/core/invoices/";

    public void sendInvoice(Invoice invoice) {
        String xml = xmlBuilder.toUblXml(invoice);
        String encoded = Base64.getEncoder().encodeToString(xml.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secret_key", "your_secret_key");
        headers.set("client_key", "your_client_key");

        Map<String, String> body = Map.of("invoice", encoded);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(fawtaraURL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Invoice sent successfully.");
        } else {
            System.out.println("Failed to send invoice. Status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
        }
    }
}

