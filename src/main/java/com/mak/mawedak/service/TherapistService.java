package com.mak.mawedak.service;

import com.mak.mawedak.dto.TherapistDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Therapist;
import com.mak.mawedak.mapper.TherapistMapper;
import com.mak.mawedak.repository.TherapistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TherapistService {

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private TherapistMapper therapistMapper;

    // Create therapist
    public TherapistDTO createTherapist(Customer customer, TherapistDTO therapistDto) throws RuntimeException{
        if(therapistDto.getTherapistId() != null) {
            throw new RuntimeException("Creating Therapist should not have an id");
        }
        return saveTherapist(customer, therapistDto, null);
    }

    // Update therapist
    public TherapistDTO updateTherapist(Customer customer, TherapistDTO therapistDto) throws RuntimeException{
        Therapist therapist = therapistRepository.
                findByCustomer_CustomerIdAndTherapistIdAndIsActive(customer.getCustomerId(), therapistDto.getTherapistId(), true)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));
        return saveTherapist(customer, therapistDto, therapist);
    }

    private TherapistDTO saveTherapist(Customer customer, TherapistDTO therapistDto, Therapist existingTherapist) {
        Therapist therapist = therapistMapper.toEntity(therapistDto, existingTherapist);
        therapist.setCustomer(customer);
        therapist = therapistRepository.save(therapist);
        return therapistMapper.toDTO(therapist);
    }

    // Get Page of therapists by customer ID
    public Page<TherapistDTO> getTherapists(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Therapist> therapistsPage =
                therapistRepository.findAllByCustomer_CustomerIdAndIsActive(customerId, true, pageable);

        // Map the entities to DTOs
        return therapistsPage.map(therapistMapper::toDTO);
    }

    // Get therapist by ID
    public Optional<TherapistDTO> getTherapistById(Long customerId, Long therapistId) {
        Therapist therapist =
                therapistRepository.findByCustomer_CustomerIdAndTherapistIdAndIsActive(customerId, therapistId, true)
                .orElseThrow(() -> new RuntimeException("Therapist not found"));
        return Optional.of(therapistMapper.toDTO(therapist));
    }

    // Set therapist as inactive
    public Optional<TherapistDTO> deactivateTherapist(Long customerId, Long therapistId) {
        Optional<Therapist> therapistOptional =
                therapistRepository.findByCustomer_CustomerIdAndTherapistIdAndIsActive(customerId, therapistId, true);

        return therapistOptional.map(therapist -> {
            therapistRepository.setInactive(customerId, therapist.getTherapistId());
            return therapistMapper.toDTO(therapist);
        });
    }

}
