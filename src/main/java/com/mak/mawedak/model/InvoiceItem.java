package com.mak.mawedak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    private String charges;
    private double rate;
    private double exRate;
    private double quantity;
}
