package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "therapists")
@NoArgsConstructor
@AllArgsConstructor
public class Therapist {

    public Therapist(Long id) {
        this.therapistId = id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long therapistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private LocalDate hiringDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDate updatedDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Double percentage = 0.10;
}

