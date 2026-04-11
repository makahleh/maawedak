package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPersonalInfoId;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column
    private String taxNumber; // tax number for the customer

    @Column
    private String secretKey;

    @Column
    private String clientKey;

    @Column
    private String incomeSourceSequenceNumber; // the sequence of the income source, used in the invoice, get from jofotara portal.

    @Column
    private Long incomeTaxInvoiceSequence; // the sequence of the income tax invoice, incremental for the invoices (prefix: EIN000).

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    public CustomerPersonalInfo(Long customerPersonalInfoId) {
        this.customerPersonalInfoId = customerPersonalInfoId;
    }
}
