package com.mak.mawedak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.model.xml.InvoiceXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FawtaraServiceXML {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${jofawtara.url}")
    private String jofawtaraURL;

    @Autowired
    public FawtaraServiceXML(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    }

    public boolean sendInvoicesToFawtara(Invoice invoice, String customerSecretKey, String customerClientKey) {
        try {
            InvoiceXML xmlDto = mapToXmlDto(invoice);
            String xml = xmlMapper.writeValueAsString(xmlDto);
            String encoded = Base64.getEncoder().encodeToString(xml.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Secret-Key", customerSecretKey);
            headers.set("Client-Id", customerClientKey);

            Map<String, String> body = Map.of("invoice", encoded);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(jofawtaraURL, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                System.err.println("Fawtara Service Error: Status " + response.getStatusCode());
                System.err.println("Response body: " + response.getBody());
                return false;
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Invoice to XML: " + e.getMessage());
            return false;
        } catch (RestClientException e) {
            System.err.println("Network error calling Fawtara service: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during Fawtara export: " + e.getMessage());
            return false;
        }
    }

    private InvoiceXML mapToXmlDto(Invoice invoice) {
        return InvoiceXML.builder()
                .id(invoice.getInvoiceNumber())
                .uuid(invoice.getInvoiceUUID().toString())
                .issueDate(invoice.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .invoiceTypeCode(new InvoiceXML.InvoiceTypeCode("011", "388"))
                .note("")
                .documentCurrencyCode("JOD")
                .taxCurrencyCode("JOD")
                .additionalDocumentReference(InvoiceXML.AdditionalDocumentReference.builder()
                        .id("ICV")
                        .uuid(String.valueOf(invoice.getInvoiceIncrementalId()))
                        .build())
                .accountingSupplierParty(InvoiceXML.AccountingSupplierParty.builder()
                        .party(InvoiceXML.Party.builder()
                                .postalAddress(new InvoiceXML.PostalAddress())
                                .partyTaxScheme(InvoiceXML.PartyTaxScheme.builder()
                                        .companyID(invoice.getCustomerTaxId())
                                        .taxScheme(new InvoiceXML.TaxScheme("VAT"))
                                        .build())
                                .partyLegalEntity(InvoiceXML.PartyLegalEntity.builder()
                                        .registrationName(invoice.getCustomerName())
                                        .build())
                                .build())
                        .build())
                .accountingCustomerParty(InvoiceXML.AccountingCustomerParty.builder()
                        .party(InvoiceXML.CustomerParty.builder()
                                .partyIdentification(InvoiceXML.PartyIdentification.builder()
                                        .id(new InvoiceXML.PartyId("NIN", invoice.getClientTaxId()))
                                        .build())
                                .postalAddress(new InvoiceXML.PostalAddress())
                                .partyTaxScheme(new InvoiceXML.PartyTaxScheme())
                                .partyLegalEntity(InvoiceXML.PartyLegalEntity.builder()
                                        .registrationName(invoice.getClientName())
                                        .build())
                                .build())
                        .accountingContact(InvoiceXML.AccountingContact.builder()
                                .telephone(invoice.getClientPhoneNumber())
                                .build())
                        .build())
                .sellerSupplierParty(InvoiceXML.SellerSupplierParty.builder()
                        .party(InvoiceXML.SellerParty.builder()
                                .partyIdentification(InvoiceXML.PartyIdentificationOnlyId.builder()
                                        .id(invoice.getCustomerIncomeSourceSequenceNumber())
                                        .build())
                                .build())
                        .build())
                .allowanceCharge(InvoiceXML.AllowanceCharge.builder()
                        .allowanceChargeReason("discount")
                        .amount(InvoiceXML.Amount.of(0))
                        .build())
                .legalMonetaryTotal(InvoiceXML.LegalMonetaryTotal.builder()
                        .taxExclusiveAmount(InvoiceXML.Amount.of(invoice.getInvoiceTotal()))
                        .taxInclusiveAmount(InvoiceXML.Amount.of(invoice.getInvoiceTotal()))
                        .allowanceTotalAmount(InvoiceXML.Amount.of(0))
                        .payableAmount(InvoiceXML.Amount.of(invoice.getInvoiceTotal()))
                        .build())
                .invoiceItems(IntStream.range(0, invoice.getInvoiceItems().size())
                        .mapToObj(i -> {
                            var item = invoice.getInvoiceItems().get(i);
                            return InvoiceXML.InvoiceLineXML.builder()
                                    .id(i + 1)
                                    .invoicedQuantity(new InvoiceXML.InvoicedQuantity("PCE", String.format("%.2f", item.getQuantity())))
                                    .lineExtensionAmount(InvoiceXML.Amount.of(item.getAmount()))
                                    .item(InvoiceXML.Item.builder().name("خدمات العلاج الطبيعي").build())
                                    .price(InvoiceXML.Price.builder()
                                            .priceAmount(InvoiceXML.Amount.of(item.getUnitPrice()))
                                            .allowanceCharge(new InvoiceXML.ItemAllowanceCharge(false, "discount", InvoiceXML.Amount.of(0)))
                                            .build())
                                    .build();
                        }).collect(Collectors.toList()))
                .build();
    }
}
