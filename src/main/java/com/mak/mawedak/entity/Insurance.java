package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    @ManyToOne
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "tax_number")
    private String taxNumber;

    private double sessionPrice;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<SubInsurance> subInsurances = new ArrayList<>();

    public Insurance(Long id) {
        this.insuranceId = id;
    }
}
