package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InsuranceDTO;
import com.mak.mawedak.dto.SubInsuranceDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Insurance;
import com.mak.mawedak.entity.SubInsurance;
import com.mak.mawedak.utils.ContextHolderHelper;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper {
    public InsuranceDTO toDto(Insurance insurance) {
        if (insurance == null) {
            return null;
        }

        var subInsurancesDTO = insurance.getSubInsurances() != null
                ? insurance.getSubInsurances().stream()
                .map(subInsurance -> new SubInsuranceDTO(
                        subInsurance.getSubInsuranceId(),
                        subInsurance.getName()
                ))
                .collect(java.util.stream.Collectors.toList())
                : null;

        return new InsuranceDTO(
                insurance.getInsuranceId(),
                insurance.getName(),
                insurance.getTaxNumber(),
                insurance.getSessionPrice(),
                subInsurancesDTO
        );
    }

    public Insurance toEntity(InsuranceDTO insuranceDTO, Insurance existinInsurance) {
        if (insuranceDTO == null) {
            return null;
        }
        Insurance insurance = existinInsurance != null ? existinInsurance : new Insurance();
        insurance.setInsuranceId(insuranceDTO.getInsuranceId());
        insurance.setName(insuranceDTO.getName());
        insurance.setTaxNumber(insuranceDTO.getTaxNumber());
        insurance.setCustomer(new Customer(ContextHolderHelper.getCustomerId()));
        insurance.setSessionPrice(insuranceDTO.getSessionPrice());
        insurance.setSubInsurances(insuranceDTO.getSubInsurances() != null
                ? insuranceDTO.getSubInsurances().stream()
                .map(subInsuranceDTO -> new SubInsurance(
                        subInsuranceDTO.getSubInsuranceId(),
                        subInsuranceDTO.getName(),
                        insurance
                ))
                .collect(java.util.stream.Collectors.toList())
                : null);
        return insurance;
    }
}
