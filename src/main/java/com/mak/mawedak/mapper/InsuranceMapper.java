package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.InsuranceDTO;
import com.mak.mawedak.entity.Insurance;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper {
    public InsuranceDTO toDto(Insurance insurance) {
        if (insurance == null) {
            return null;
        }
        return new InsuranceDTO(
                insurance.getInsuranceId(),
                insurance.getName(),
                insurance.getPercentage()
        );
    }

    public Insurance toEntity(InsuranceDTO insuranceDTO, Insurance existinInsurance) {
        if (insuranceDTO == null) {
            return null;
        }
        Insurance insurance = existinInsurance != null ? existinInsurance : new Insurance();
        insurance.setInsuranceId(insuranceDTO.getInsuranceId());
        insurance.setName(insuranceDTO.getName());
        insurance.setPercentage(insuranceDTO.getPercentage());
        return insurance;
    }
}
