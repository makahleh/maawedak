package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientProfileSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientProfileSettingsId;

    // TODO : CHANGE TO ANOTHER ENTITY
    @OneToOne(cascade = CascadeType.ALL)
    private SubscriptionSettings defaultSubscription;

    @OneToMany(mappedBy = "patientProfileSettings", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreatmentMethod> treatmentMethods;
}
