package com.mak.mawedak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    private double amount;
    private double unitPrice;
    private double quantity; // if payment is for subscription methodId == 2, quantity should be the number of sessions, otherwise it should be 1.
}
