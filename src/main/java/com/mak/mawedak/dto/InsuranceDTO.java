package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDTO {
    private Long insuranceId;
    private String name;
    private String taxNumber;
    private double sessionPrice;
    private List<SubInsuranceDTO> subInsurances;
}
