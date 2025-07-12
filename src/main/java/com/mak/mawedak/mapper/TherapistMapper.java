package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.TherapistDTO;
import com.mak.mawedak.entity.Department;
import com.mak.mawedak.entity.Role;
import com.mak.mawedak.entity.Therapist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TherapistMapper {

    public TherapistDTO toDTO(Therapist therapist) {
        if (therapist == null) {
            return null;
        }
        return new TherapistDTO(
                therapist.getTherapistId(),
                therapist.getName(),
                therapist.getPhoneNumber(),
                therapist.getHiringDate().toString(),
                therapist.getDepartment().getDepartmentId(),
                therapist.getDepartment().getDepartmentName(),
                therapist.getUsername(),
                therapist.getPassword(),
                therapist.getIsActive()
        );
    }

    public Therapist toEntity(TherapistDTO therapistDto, Therapist existingTherapist) {
        if (therapistDto == null) {
            return null;
        }
        Therapist therapist = existingTherapist != null ? existingTherapist : new Therapist();
        therapist.setTherapistId(therapistDto.getTherapistId());
        therapist.setName(therapistDto.getName());
        therapist.setPhoneNumber(therapistDto.getPhoneNumber());
        therapist.setHiringDate(LocalDateTime.parse(therapistDto.getHiringDate()));
        therapist.setUsername(therapistDto.getUsername());
        therapist.setPassword(therapistDto.getPassword() == null ? therapist.getPassword() : therapistDto.getPassword());
        therapist.setDepartment(new Department(therapistDto.getDepartmentId(), null));
        therapist.setRoles(new ArrayList<>(List.of(new Role(2L))));
        // Add other fields as necessary
        return therapist;
    }
}
