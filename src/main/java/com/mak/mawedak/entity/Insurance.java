package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double percentage;

    public Insurance(Long id) {
        this.insuranceId = id;
    }
}
