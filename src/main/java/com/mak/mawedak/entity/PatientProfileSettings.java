package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"defaultSubscription", "treatmentMethods"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientProfileSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long patientProfileSettingsId;

    // TODO : CHANGE TO ANOTHER ENTITY
    @OneToOne(cascade = CascadeType.ALL)
    private SubscriptionSettings defaultSubscription;

    @OneToMany(mappedBy = "patientProfileSettings", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreatmentMethod> treatmentMethods;
}
