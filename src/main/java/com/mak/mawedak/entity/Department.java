package com.mak.mawedak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    public Department(Long id) {
        this.departmentId = id;
    }

    @Id
    @EqualsAndHashCode.Include
    private Long departmentId;

    @Column(nullable = false)
    private String departmentName;
}

