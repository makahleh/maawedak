package com.mak.mawedak.service;

import com.mak.mawedak.dto.SystemSettingsDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.mapper.SystemSettingsMapper;
import com.mak.mawedak.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public SystemSettingsDTO updateSystemSettings(Long customerId, SystemSettingsDTO systemSettingsDTO) {
        if (systemSettingsDTO == null || customerId == null) {
            throw new IllegalArgumentException("System settings or customer ID cannot be null");
        }

        // Fetch the existing customer
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Map the DTO to the entity
        Customer updatedCustomer = SystemSettingsMapper.mapToCustomerEntity(existingCustomer, systemSettingsDTO);

        // Save the updated customer
        customerRepository.save(updatedCustomer);

        // Map the updated entity back to DTO
        return SystemSettingsMapper.mapToSystemSettingsDTO(updatedCustomer);
    }

}
