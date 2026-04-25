package com.mak.mawedak.model.xml;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Invoice")
@JsonPropertyOrder({
        "xmlns", "xmlnsCac", "xmlnsCbc", "xmlnsExt",
        "profileID", "id", "uuid", "issueDate", "invoiceTypeCode", "note",
        "documentCurrencyCode", "taxCurrencyCode", "additionalDocumentReference",
        "accountingSupplierParty", "accountingCustomerParty", "sellerSupplierParty",
        "allowanceCharge", "legalMonetaryTotal", "invoiceItems"
})
public class InvoiceXML {

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
    private final String xmlns = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:cac")
    private final String xmlnsCac = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:cbc")
    private final String xmlnsCbc = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:ext")
    private final String xmlnsExt = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";

    @Builder.Default
    @JacksonXmlProperty(localName = "cbc:ProfileID")
    private String profileID = "reporting:1.0";

    @JacksonXmlProperty(localName = "cbc:ID")
    private String id;

    @JacksonXmlProperty(localName = "cbc:UUID")
    private String uuid;

    @JacksonXmlProperty(localName = "cbc:IssueDate")
    private String issueDate;

    @JacksonXmlProperty(localName = "cbc:InvoiceTypeCode")
    private InvoiceTypeCode invoiceTypeCode;

    @Builder.Default
    @JacksonXmlProperty(localName = "cbc:Note")
    private String note = "";

    @Builder.Default
    @JacksonXmlProperty(localName = "cbc:DocumentCurrencyCode")
    private String documentCurrencyCode = "JOD";

    @Builder.Default
    @JacksonXmlProperty(localName = "cbc:TaxCurrencyCode")
    private String taxCurrencyCode = "JOD";

    @JacksonXmlProperty(localName = "cac:AdditionalDocumentReference")
    private AdditionalDocumentReference additionalDocumentReference;

    @JacksonXmlProperty(localName = "cac:AccountingSupplierParty")
    private AccountingSupplierParty accountingSupplierParty;

    @JacksonXmlProperty(localName = "cac:AccountingCustomerParty")
    private AccountingCustomerParty accountingCustomerParty;

    @JacksonXmlProperty(localName = "cac:SellerSupplierParty")
    private SellerSupplierParty sellerSupplierParty;

    @JacksonXmlProperty(localName = "cac:AllowanceCharge")
    private AllowanceCharge allowanceCharge;

    @JacksonXmlProperty(localName = "cac:LegalMonetaryTotal")
    private LegalMonetaryTotal legalMonetaryTotal;

    @JacksonXmlProperty(localName = "cac:InvoiceLine")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<InvoiceLineXML> invoiceItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvoiceTypeCode {
        @JacksonXmlProperty(isAttribute = true)
        private String name;
        @JacksonXmlText
        private String value = "388";
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalDocumentReference {
        @Builder.Default
        @JacksonXmlProperty(localName = "cbc:ID")
        private String id = "ICV";
        @JacksonXmlProperty(localName = "cbc:UUID")
        private String uuid;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountingSupplierParty {
        @JacksonXmlProperty(localName = "cac:Party")
        private Party party;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountingCustomerParty {
        @JacksonXmlProperty(localName = "cac:Party")
        private CustomerParty party;
        @JacksonXmlProperty(localName = "cac:AccountingContact")
        private AccountingContact accountingContact;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerSupplierParty {
        @JacksonXmlProperty(localName = "cac:Party")
        private SellerParty party;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Party {
        @JacksonXmlProperty(localName = "cac:PostalAddress")
        private PostalAddress postalAddress;
        @JacksonXmlProperty(localName = "cac:PartyTaxScheme")
        private PartyTaxScheme partyTaxScheme;
        @JacksonXmlProperty(localName = "cac:PartyLegalEntity")
        private PartyLegalEntity partyLegalEntity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerParty {
        @JacksonXmlProperty(localName = "cac:PartyIdentification")
        private PartyIdentification partyIdentification;
        @JacksonXmlProperty(localName = "cac:PostalAddress")
        private PostalAddress postalAddress;
        @JacksonXmlProperty(localName = "cac:PartyTaxScheme")
        private PartyTaxScheme partyTaxScheme;
        @JacksonXmlProperty(localName = "cac:PartyLegalEntity")
        private PartyLegalEntity partyLegalEntity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerParty {
        @JacksonXmlProperty(localName = "cac:PartyIdentification")
        private PartyIdentificationOnlyId partyIdentification;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostalAddress {
        @Builder.Default
        @JacksonXmlProperty(localName = "cbc:PostalZone")
        private String postalZone = "";
        @Builder.Default
        @JacksonXmlProperty(localName = "cac:Country")
        private Country country = new Country();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Country {
        @JacksonXmlProperty(localName = "cbc:IdentificationCode")
        private String identificationCode = "JO";
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyTaxScheme {
        @JacksonXmlProperty(localName = "cbc:CompanyID")
        private String companyID;
        @Builder.Default
        @JacksonXmlProperty(localName = "cac:TaxScheme")
        private TaxScheme taxScheme = new TaxScheme();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaxScheme {
        @JacksonXmlProperty(localName = "cbc:ID")
        private String id = "VAT";
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyLegalEntity {
        @JacksonXmlProperty(localName = "cbc:RegistrationName")
        private String registrationName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyIdentification {
        @JacksonXmlProperty(localName = "cbc:ID")
        private PartyId id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyIdentificationOnlyId {
        @JacksonXmlProperty(localName = "cbc:ID")
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PartyId {
        @JacksonXmlProperty(isAttribute = true)
        private String schemeID;
        @JacksonXmlText
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountingContact {
        @JacksonXmlProperty(localName = "cbc:Telephone")
        private String telephone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllowanceCharge {
        @Builder.Default
        @JacksonXmlProperty(localName = "cbc:ChargeIndicator")
        private boolean chargeIndicator = false;
        @Builder.Default
        @JacksonXmlProperty(localName = "cbc:AllowanceChargeReason")
        private String allowanceChargeReason = "discount";
        @JacksonXmlProperty(localName = "cbc:Amount")
        private Amount amount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LegalMonetaryTotal {
        @JacksonXmlProperty(localName = "cbc:TaxExclusiveAmount")
        private Amount taxExclusiveAmount;
        @JacksonXmlProperty(localName = "cbc:TaxInclusiveAmount")
        private Amount taxInclusiveAmount;
        @JacksonXmlProperty(localName = "cbc:AllowanceTotalAmount")
        private Amount allowanceTotalAmount;
        @JacksonXmlProperty(localName = "cbc:PayableAmount")
        private Amount payableAmount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        @JacksonXmlProperty(isAttribute = true)
        private String currencyID = "JO";
        @JacksonXmlText
        private String value;

        public static Amount of(double val) {
            return new Amount("JO", String.format("%.2f", val));
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceLineXML {
        @JacksonXmlProperty(localName = "cbc:ID")
        private int id;
        @JacksonXmlProperty(localName = "cbc:InvoicedQuantity")
        private InvoicedQuantity invoicedQuantity;
        @JacksonXmlProperty(localName = "cbc:LineExtensionAmount")
        private Amount lineExtensionAmount;
        @JacksonXmlProperty(localName = "cac:Item")
        private Item item;
        @JacksonXmlProperty(localName = "cac:Price")
        private Price price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvoicedQuantity {
        @JacksonXmlProperty(isAttribute = true)
        private String unitCode = "PCE";
        @JacksonXmlText
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @JacksonXmlProperty(localName = "cbc:Name")
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Price {
        @JacksonXmlProperty(localName = "cbc:PriceAmount")
        private Amount priceAmount;
        @Builder.Default
        @JacksonXmlProperty(localName = "cac:AllowanceCharge")
        private ItemAllowanceCharge allowanceCharge = new ItemAllowanceCharge();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemAllowanceCharge {
        @JacksonXmlProperty(localName = "cbc:ChargeIndicator")
        private boolean chargeIndicator = false;
        @JacksonXmlProperty(localName = "cbc:AllowanceChargeReason")
        private String allowanceChargeReason = "DISCOUNT";
        @JacksonXmlProperty(localName = "cbc:Amount")
        private Amount amount = Amount.of(0);
    }
}
