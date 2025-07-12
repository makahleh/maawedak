package com.mak.mawedak.service;

import com.mak.mawedak.dto.InsuranceDTO;
import com.mak.mawedak.entity.Insurance;
import com.mak.mawedak.mapper.InsuranceMapper;
import com.mak.mawedak.repository.InsuranceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private InsuranceMapper insuranceMapper;

    public Page<InsuranceDTO> getInsurances(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Insurance> insurancesPage = insuranceRepository.findAllByCustomer_CustomerId(customerId, pageable);

        return insurancesPage.map(insuranceMapper::toDto);
    }
}
