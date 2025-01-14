package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.TherapistDTO;
import com.mak.mawedak.entity.Department;
import com.mak.mawedak.entity.Role;
import com.mak.mawedak.entity.Therapist;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TherapistMapper {

    public TherapistDTO toDTO(Therapist therapist) {
        if (therapist == null) {
            return null;
        }
        return new TherapistDTO(
                therapist.getTherapistId(),
                therapist.getFirstName(),
                therapist.getLastName(),
                therapist.getPhoneNumber(),
                therapist.getHiringDate().toString(),
                therapist.getDepartment().getDepartmentId(),
                therapist.getDepartment().getDepartmentName(),
                therapist.getUsername(),
                therapist.getPassword()
        );
    }

    public Therapist toEntity(TherapistDTO therapistDto, Therapist existingTherapist) {
        if (therapistDto == null) {
            return null;
        }
        Therapist therapist = existingTherapist != null ? existingTherapist : new Therapist();
        therapist.setTherapistId(therapistDto.getTherapistId());
        therapist.setFirstName(therapistDto.getFirstName());
        therapist.setLastName(therapistDto.getLastName());
        therapist.setPhoneNumber(therapistDto.getPhoneNumber());
        therapist.setHiringDate(LocalDate.parse(therapistDto.getHiringDate()));
        therapist.setUsername(therapistDto.getUsername());
        therapist.setPassword(therapistDto.getPassword());
        therapist.setDepartment(new Department(therapistDto.getDepartmentId(), null));
        therapist.setRoles(List.of(new Role(2L)));
        // Add other fields as necessary
        return therapist;
    }
}
