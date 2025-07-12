package com.mak.mawedak.service;

import com.mak.mawedak.dto.AuthResponseDTO;
import com.mak.mawedak.dto.LoginDTO;
import com.mak.mawedak.dto.SystemSettingsDTO;
import com.mak.mawedak.entity.Customer;
import com.mak.mawedak.entity.Role;
import com.mak.mawedak.entity.Therapist;
import com.mak.mawedak.mapper.SystemSettingsMapper;
import com.mak.mawedak.provider.JwtTokenProvider;
import com.mak.mawedak.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TherapistRepository therapistRepository;

    public AuthResponseDTO login(LoginDTO loginDto) {

        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Get user details
        Therapist user = therapistRepository.findByUsernameAndIsActive(loginDto.getUsername(), true)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not exists by Username or Email"));

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        Customer customer = user.getCustomer();
        Map<String, Object> claims = new HashMap<>();
        claims.put("customerId", customer.getCustomerId());
        claims.put("roles", roles);

        String jwt = jwtTokenProvider.generateToken(authentication, claims);
        // 04 - Return the token to controller
        SystemSettingsDTO systemSettingsDTO = SystemSettingsMapper.mapToSystemSettingsDTO(customer);
        return new AuthResponseDTO(jwt, systemSettingsDTO);
    }
}
