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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TherapistService implements UserDetailsService {

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private TherapistMapper therapistMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create therapist
    public TherapistDTO createTherapist(Customer customer, TherapistDTO therapistDto) throws RuntimeException{
        if(therapistDto.getTherapistId() != null) {
            throw new RuntimeException("Creating Therapist should not have an id");
        }
        therapistDto.setPassword(passwordEncoder.encode(therapistDto.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Therapist user = therapistRepository.findByUsernameAndIsActive(username, true)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not exists by Username or Email"));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities
        );
    }
}
