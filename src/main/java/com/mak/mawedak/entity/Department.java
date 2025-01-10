package com.mak.mawedak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    public Department(Long id) {
        this.departmentId = id;
    }

    @Id
    private Long departmentId;

    @Column(nullable = false)
    private String departmentName;
}

