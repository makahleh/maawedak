package com.mak.mawedak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentMethod {

    @Id
    private Long treatmentMethodId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private PatientProfileSettings patientProfileSettings;

    public TreatmentMethod(Long id) {
        this.treatmentMethodId = id;
    }
}
