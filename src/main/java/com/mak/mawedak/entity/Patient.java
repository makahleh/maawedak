package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private Integer age;

    @Column
    private String nationalNumber;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "patient_treatment_method",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_method_id")
    )
    private List<TreatmentMethod> treatmentMethods;

    @Column(length = 2000)
    private String notes;

    // Subscription details
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    // Payment details
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Payment> payments;

    // sessions history
    @OneToMany(mappedBy = "patient")
    private List<Session> sessions;

    private boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    public Patient(Long id) {
        this.patientId = id;
    }
}

