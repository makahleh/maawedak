package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    @ManyToMany
    @JoinTable(
            name = "insurance_customer",
            joinColumns = @JoinColumn(name = "insurance_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;


    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double percentage;

    private double sessionPrice;

    public Insurance(Long id) {
        this.insuranceId = id;
    }
}
