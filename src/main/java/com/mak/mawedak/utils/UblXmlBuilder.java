package com.mak.mawedak.utils;

import com.mak.mawedak.dto.Invoice;
import org.springframework.stereotype.Component;

@Component
public class UblXmlBuilder {

    // https://www.storecove.com/blog/en/creating-your-own-ubl-invoice/
    // https://istd.gov.jo/ebv4.0/root_storage/ar/eb_list_page/%D8%AF%D9%84%D9%8A%D9%84_%D8%A5%D8%AC%D8%B1%D8%A1%D8%A7%D8%AA_%D8%A7%D9%84%D8%B1%D8%A8%D8%B7_%D8%B9%D9%84%D9%89_%D9%86%D8%B8%D8%A7%D9%85_%D8%A7%D9%84%D9%81%D9%88%D8%AA%D8%B1%D8%A9_%D8%A7%D9%84%D9%88%D8%B7%D9%86%D9%8A_%D8%A7%D9%84%D8%A7%D9%84%D9%83%D8%AA%D8%B1%D9%88%D9%86%D9%8A_%D8%A7%D9%84%D8%A7%D8%B1%D8%AF%D9%86%D9%8A.pdf
    // https://portal.jofotara.gov.jo/ccb81f8923f9894ae4aa.pdf
    public String toUblXml(Invoice invoice) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                         xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                         xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                  <cbc:ID>%s</cbc:ID>
                  <cbc:IssueDate>%s</cbc:IssueDate>
                  <cbc:InvoiceTypeCode>380</cbc:InvoiceTypeCode>
                  <cbc:DocumentCurrencyCode>JOD</cbc:DocumentCurrencyCode>
                
                  <cac:AccountingSupplierParty>
                    <cac:Party><cbc:Name>%s</cbc:Name></cac:Party>
                  </cac:AccountingSupplierParty>
                
                  <cac:AccountingCustomerParty>
                    <cac:Party><cbc:Name>%s</cbc:Name></cac:Party>
                  </cac:AccountingCustomerParty>
                
                  <cac:TaxTotal>
                    <cbc:TaxAmount currencyID="JOD">0.00</cbc:TaxAmount>
                  </cac:TaxTotal>
                
                  <cac:LegalMonetaryTotal>
                    <cbc:PayableAmount currencyID="JOD">%.2f</cbc:PayableAmount>
                  </cac:LegalMonetaryTotal>
                
                  <cac:InvoiceLine>
                    <cbc:ID>1</cbc:ID>
                    <cbc:InvoicedQuantity unitCode="EA">1</cbc:InvoicedQuantity>
                    <cbc:LineExtensionAmount currencyID="JOD">%.2f</cbc:LineExtensionAmount>
                    <cac:Item><cbc:Name>%s</cbc:Name></cac:Item>
                    <cac:Price><cbc:PriceAmount currencyID="JOD">%.2f</cbc:PriceAmount></cac:Price>
                  </cac:InvoiceLine>
                </Invoice>
                """.formatted(
                invoice.getInvoiceId(),
                invoice.getIssueDate(),
                invoice.getSupplierName(),
                invoice.getCustomerName(),
                invoice.getItemPrice(),
                invoice.getItemPrice(),
                invoice.getItemName(),
                invoice.getItemPrice()
        );
    }
}
