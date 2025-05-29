package com.mak.mawedak.utils;

import com.mak.mawedak.model.Invoice;
import com.mak.mawedak.model.InvoiceItem;
import org.springframework.stereotype.Component;

@Component
public class UblXmlBuilder {

    // https://www.storecove.com/blog/en/creating-your-own-ubl-invoice/
    // https://istd.gov.jo/ebv4.0/root_storage/ar/eb_list_page/%D8%AF%D9%84%D9%8A%D9%84_%D8%A5%D8%AC%D8%B1%D8%A1%D8%A7%D8%AA_%D8%A7%D9%84%D8%B1%D8%A8%D8%B7_%D8%B9%D9%84%D9%89_%D9%86%D8%B8%D8%A7%D9%85_%D8%A7%D9%84%D9%81%D9%88%D8%AA%D8%B1%D8%A9_%D8%A7%D9%84%D9%88%D8%B7%D9%86%D9%8A_%D8%A7%D9%84%D8%A7%D9%84%D9%83%D8%AA%D8%B1%D9%88%D9%86%D9%8A_%D8%A7%D9%84%D8%A7%D8%B1%D8%AF%D9%86%D9%8A.pdf
    // https://portal.jofotara.gov.jo/ccb81f8923f9894ae4aa.pdf
    public static String buildInvoiceXml(Invoice invoice) {
        // Header part
        String invoiceHeader = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                         xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                         xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                         xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2">
                  <cbc:ProfileID>reporting:1.0</cbc:ProfileID>
                  <cbc:ID>%s</cbc:ID>
                  <cbc:UUID>%s</cbc:UUID>
                  <cbc:IssueDate>%s</cbc:IssueDate>
                  <cbc:InvoiceTypeCode name="011">388</cbc:InvoiceTypeCode>
                  <cbc:Note>no notes</cbc:Note>
                  <cbc:DocumentCurrencyCode>JOD</cbc:DocumentCurrencyCode>
                  <cbc:TaxCurrencyCode>JOD</cbc:TaxCurrencyCode>
                """.formatted(
                invoice.getInvoiceNumber(),
                invoice.getInvoiceId(),
                invoice.getIssueDate()
        );

        // Accounting Supplier Party
        String accountingSupplierParty = """
                  <cac:AccountingSupplierParty>
                    <cac:Party>
                      <cac:PostalAddress>
                        <cac:Country>
                          <cbc:IdentificationCode>JO</cbc:IdentificationCode>
                        </cac:Country>
                      </cac:PostalAddress>
                      <cac:PartyTaxScheme>
                        <cbc:CompanyID>%s</cbc:CompanyID>
                        <cac:TaxScheme>
                          <cbc:ID>VAT</cbc:ID>
                        </cac:TaxScheme>
                      </cac:PartyTaxScheme>
                      <cac:PartyLegalEntity>
                        <cbc:RegistrationName>%s</cbc:RegistrationName>
                      </cac:PartyLegalEntity>
                    </cac:Party>
                  </cac:AccountingSupplierParty>
                """.formatted(
                invoice.getCustomerTaxId(),
                invoice.getCustomerName()
        );

        // Accounting Customer Party
        String accountingCustomerParty = """
                  <cac:AccountingCustomerParty>
                    <cac:Party>
                      <cac:PartyIdentification>
                        <cbc:ID schemeID="TN">%s</cbc:ID>
                      </cac:PartyIdentification>
                      <cac:PostalAddress>
                        <cbc:PostalZone></cbc:PostalZone>
                        <cac:Country>
                          <cbc:IdentificationCode>JO</cbc:IdentificationCode>
                        </cac:Country>
                      </cac:PostalAddress>
                      <cac:PartyTaxScheme>
                        <cac:TaxScheme>
                          <cbc:ID>VAT</cbc:ID>
                        </cac:TaxScheme>
                      </cac:PartyTaxScheme>
                      <cac:PartyLegalEntity>
                        <cbc:RegistrationName>%s</cbc:RegistrationName>
                      </cac:PartyLegalEntity>
                    </cac:Party>
                    <cac:AccountingContact>
                      <cbc:Telephone></cbc:Telephone>
                    </cac:AccountingContact>
                  </cac:AccountingCustomerParty>
                """.formatted(
                invoice.getClientTaxId(),
                invoice.getClientName()
        );

        // Seller Supplier Party
        String sellerSupplierParty = """
                  <cac:SellerSupplierParty>
                    <cac:Party>
                      <cac:PartyIdentification>
                        <cbc:ID>%s</cbc:ID>
                      </cac:PartyIdentification>
                    </cac:Party>
                  </cac:SellerSupplierParty>
                """.formatted(invoice.getCustomerIncomeSourceSequence());

        // Allowance Charge (fixed 0)
        String allowanceCharge = """
                  <cac:AllowanceCharge>
                    <cbc:ChargeIndicator>false</cbc:ChargeIndicator>
                    <cbc:AllowanceChargeReason>discount</cbc:AllowanceChargeReason>
                    <cbc:Amount currencyID="JOD">0</cbc:Amount>
                  </cac:AllowanceCharge>
                """;

        // Legal Monetary Total
        String legalMonetaryTotal = """
                  <cac:LegalMonetaryTotal>
                    <cbc:TaxExclusiveAmount currencyID="JOD">%.2f</cbc:TaxExclusiveAmount>
                    <cbc:TaxInclusiveAmount currencyID="JOD">%.2f</cbc:TaxInclusiveAmount>
                    <cbc:AllowanceTotalAmount currencyID="JOD">0</cbc:AllowanceTotalAmount>
                    <cbc:PayableAmount currencyID="JOD">%.2f</cbc:PayableAmount>
                  </cac:LegalMonetaryTotal>
                """.formatted(
                invoice.getInvoiceTotal(),
                invoice.getInvoiceTotal(),
                invoice.getInvoiceTotal()
        );

        // Invoice Lines
        StringBuilder linesBuilder = new StringBuilder();
        int idx = 1;
        for (InvoiceItem item : invoice.getInvoiceItem()) {
            double price = item.getRate() * item.getExRate();
            double total = item.getQuantity() * price;

            String line = """
                      <cac:InvoiceLine>
                        <cbc:ID>%d</cbc:ID>
                        <cbc:InvoicedQuantity unitCode="PCE">%.2f</cbc:InvoicedQuantity>
                        <cbc:LineExtensionAmount currencyID="JOD">%.2f</cbc:LineExtensionAmount>
                        <cac:Item>
                          <cbc:Name>%s</cbc:Name>
                        </cac:Item>
                        <cac:Price>
                          <cbc:PriceAmount currencyID="JOD">%.2f</cbc:PriceAmount>
                          <cac:AllowanceCharge>
                            <cbc:ChargeIndicator>false</cbc:ChargeIndicator>
                            <cbc:AllowanceChargeReason>DISCOUNT</cbc:AllowanceChargeReason>
                            <cbc:Amount currencyID="JOD">0.00</cbc:Amount>
                          </cac:AllowanceCharge>
                        </cac:Price>
                      </cac:InvoiceLine>
                    """.formatted(
                    idx++,
                    item.getQuantity(),
                    total,
                    item.getCharges(),
                    price
            );
            linesBuilder.append(line);
        }

        // Combine all parts
        String fullXml = invoiceHeader +
                accountingSupplierParty +
                accountingCustomerParty +
                sellerSupplierParty +
                allowanceCharge +
                legalMonetaryTotal +
                linesBuilder +
                "</Invoice>";

        return fullXml;
    }

}
