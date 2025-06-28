package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subInsuranceId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;

    public SubInsurance(Long id) {
        this.subInsuranceId = id;
    }
}
