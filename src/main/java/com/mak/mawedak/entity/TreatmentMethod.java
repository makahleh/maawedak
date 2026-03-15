package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treatmentMethodId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private PatientProfileSettings patientProfileSettings;

    public TreatmentMethod(Long id) {
        this.treatmentMethodId = id;
    }
}
