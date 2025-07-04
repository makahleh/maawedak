package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InsuranceDTO;
import com.mak.mawedak.dto.SubInsuranceDTO;
import com.mak.mawedak.entity.Insurance;
import com.mak.mawedak.entity.SubInsurance;
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
        return insurance;
    }
}
